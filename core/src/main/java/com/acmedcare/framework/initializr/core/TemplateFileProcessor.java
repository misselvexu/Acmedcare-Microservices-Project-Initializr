package com.acmedcare.framework.initializr.core;

import com.acmedcare.framework.initializr.exception.InitializrException;
import com.acmedcare.framework.kits.compress.CompressKits;
import com.acmedcare.framework.kits.struct.ConcurrentHashSet;
import com.acmedcare.framework.kits.struct.NamedThreadFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static com.acmedcare.framework.initializr.core.InitializrConstants.*;

/**
 * {@link TemplateFileProcessor}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
@Component
@SuppressWarnings("AlibabaMethodTooLong")
public class TemplateFileProcessor {

  private static final Logger log = LoggerFactory.getLogger(TemplateFileProcessor.class);

  private static ExecutorService processExecutor;

  private static Map<String, List<InitializrBean>> executeSemaphore = new ConcurrentHashMap<>();

  private static Set<String> executingRecords = new ConcurrentHashSet<>();

  private final InitializrProcessorProperties processorProperties;

  public TemplateFileProcessor(InitializrProcessorProperties processorProperties) {
    this.processorProperties = processorProperties;

    if (processExecutor == null) {
      processExecutor =
          new ThreadPoolExecutor(
              this.processorProperties.getProcessorThreadNums(),
              this.processorProperties.getProcessorThreadNums() * 2,
              5,
              TimeUnit.SECONDS,
              new LinkedBlockingQueue<>(64),
              new NamedThreadFactory("initializr"));
    }
  }

  /**
   * Process Method
   *
   * @param templateDir template dir
   * @param createdZipDir created zip dir
   * @param bean init bean instance
   * @return result
   */
  public InitializrResult process(
      final String templateDir, final String createdZipDir, final InitializrBean bean) {

    if (bean == null) {
      throw new InitializrException("处理参数异常");
    }

    // check file is already exist
    String expectFilePath = bean.renderPath(createdZipDir);

    String projectFilePath = bean.renderPath(createdZipDir);

    String fileName = bean.getName().concat(PACKAGE_SUFFIX);

    String projectFileFullPath =
        projectFilePath.concat(File.separator).concat(fileName);

    Path expectProjectFilePath = Paths.get(projectFileFullPath);

    String sha1 = bean.sha1();

    if (executingRecords.contains(sha1)) {
      // wait
      bean.setWaitLatch(new CountDownLatch(ONCE));
      executeSemaphore.get(sha1).add(bean);

      // waiting
      try {
        bean.getWaitLatch().await();

        if (Files.exists(expectProjectFilePath)) {
          return InitializrResult.builder()
              .code(0)
              .destZipFile(projectFileFullPath)
              .fileName(fileName)
              .build();
        } else {
          throw new InitializrException("INVALID DATA STATUS");
        }
      } catch (InterruptedException e) {
        throw new InitializrException("INTERRUPT EXCEPTION");
      }

    } else {
      // no-ing.
      if (Files.exists(expectProjectFilePath)) {
        return InitializrResult.builder()
            .code(0)
            .destZipFile(projectFileFullPath)
            .fileName(fileName)
            .build();
      } else {
        // submit
        // Assert added is true
        boolean added = executingRecords.add(sha1);

        // added
        if (added) {

          bean.setWaitLatch(new CountDownLatch(ONCE));

          if (executeSemaphore.containsKey(sha1)) {
            executeSemaphore.get(sha1).add(bean);
          } else {
            List<InitializrBean> beans = new ArrayList<>();
            beans.add(bean);
            executeSemaphore.put(sha1, beans);
          }

          // add execute task
          CountDownLatch executingLatch = new CountDownLatch(ONCE);
          try {

            InitializrResult.InitializrResultBuilder builder = InitializrResult.builder();

            processExecutor.submit(
                () -> {
                  // task run
                  try {
                    // copy directories
                    Path sourceDirectory = Paths.get(templateDir);
                    Path destDirectory = Paths.get(expectFilePath);

                    FileUtils.copyDirectory(sourceDirectory.toFile(), destDirectory.toFile());

                    // list all files
                    Stream<File> paths =
                        FileUtils.listFiles(
                            destDirectory.toFile(),
                            TrueFileFilter.INSTANCE,
                            DirectoryFileFilter.INSTANCE)
                            .stream();

                    paths
                        .parallel()
                        .forEach(
                            file -> {
                              // multi-process
                              try {
                                String fileContent =
                                    FileUtils.readFileToString(file, Charset.defaultCharset());

                                fileContent = bean.rebuild(fileContent);

                                FileUtils.writeStringToFile(
                                    file, fileContent, Charset.defaultCharset(), false);

                                String originFilePath = file.getAbsolutePath();

                                if (isSourceFile(originFilePath)) {

                                  String rightFilePath =
                                      buildRightSourceFilePath(
                                          originFilePath, bean.getPackageName());

                                  if (rightFilePath != null
                                      && !originFilePath.equals(rightFilePath)) {
                                    FileUtils.moveFile(file, new File(rightFilePath));
                                  }
                                }

                              } catch (IOException e) {
                                log.warn(
                                    "[INITIALIZR] template file:{} rebuild failed .",
                                    file.getAbsolutePath());
                              }
                            });

                    log.info(
                        "[INITIALIZR] project :{} processed . dir: {}",
                        bean.getName(),
                        expectFilePath);

                    CompressKits.compressZip(projectFileFullPath, expectFilePath);

                    // build result
                    builder.fileName(fileName).code(0).destZipFile(projectFileFullPath);

                    // release all wait thread
                    List<InitializrBean> beans = executeSemaphore.get(sha1);
                    if (beans != null && !beans.isEmpty()) {
                      for (InitializrBean initializrBean : beans) {
                        initializrBean.getWaitLatch().countDown();
                      }
                    }

                  } catch (Exception e) {
                    log.info(
                        "[INITIALIZR] task threads:[{}] execute failed .",
                        Thread.currentThread().getName(),
                        e);
                    // set failed code
                    builder.code(-1).fileName(fileName);
                  } finally {
                    executingLatch.countDown();
                  }
                });

            // finished
            executingLatch.await();

            // remove processing record cache
            executingRecords.remove(sha1);

            // build result
            return builder.build();
          } catch (InterruptedException ignored) {
          }

          throw new InitializrException("UNKNOWN EXCEPTION");
        } else {
          throw new InitializrException("INVALID DATA STATUS");
        }
      }
    }
  }
}
