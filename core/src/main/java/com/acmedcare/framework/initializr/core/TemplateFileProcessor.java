package com.acmedcare.framework.initializr.core;

import com.acmedcare.framework.initializr.exception.InitializrException;
import com.acmedcare.framework.kits.compress.CompressKits;
import com.acmedcare.framework.kits.struct.ConcurrentHashSet;
import com.acmedcare.framework.kits.struct.NamedThreadFactory;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static com.acmedcare.framework.initializr.core.InitializrConstants.PACKAGE_SUFFIX;

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
              processorProperties.getProcessorThreadNums(),
              processorProperties.getProcessorThreadNums() * 2,
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

    String fileName = bean.getName().concat(PACKAGE_SUFFIX);

    String expectZipFileFullPath = expectFilePath.concat(PACKAGE_SUFFIX);

    Path expectZipFilePath = Paths.get(expectZipFileFullPath);

    String sha1 = bean.sha1();

    // if file is exist & file is already process done , just return resource
    if (Files.exists(expectZipFilePath) && !executingRecords.contains(sha1)) {

      return InitializrResult.builder()
          .code(0)
          .destZipFile(expectZipFileFullPath)
          .fileName(fileName)
          .build();
    }

    // Assert added is true
    boolean added = executingRecords.add(sha1);

    // added
    if (added) {

      bean.setWaitLatch(new CountDownLatch(1));

      if (executeSemaphore.containsKey(sha1)) {
        executeSemaphore.get(sha1).add(bean);
      } else {
        List<InitializrBean> beans = new ArrayList<>();
        beans.add(bean);
        executeSemaphore.put(sha1, beans);
      }

      // add execute task
      CountDownLatch executingLatch = new CountDownLatch(1);
      try {

        InitializrResult.InitializrResultBuilder builder = InitializrResult.builder();

        processExecutor.submit(
            () -> {
              // task run
              try {
                // copy directories
                Path sourceDirectory = Paths.get(templateDir);
                Path destDirectory = Paths.get(expectFilePath);

                Files.copy(
                    sourceDirectory,
                    destDirectory,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES);

                // list all files
                Stream<Path> paths = Files.list(destDirectory);
                paths
                    .parallel()
                    .forEach(
                        path -> {
                          // multi-process
                          try {
                            String fileContent =
                                FileUtils.readFileToString(path.toFile(), Charset.defaultCharset());

                            fileContent = bean.rebuild(fileContent);

                            FileUtils.writeStringToFile(
                                path.toFile(), fileContent, Charset.defaultCharset(), false);

                          } catch (IOException e) {
                            log.warn(
                                "[INITIALIZR] template file:{} rebuild failed .",
                                path.toFile().getAbsolutePath());
                          }
                        });

                log.info(
                    "[INITIALIZR] project :{} processed . dir: {}", bean.getName(), expectFilePath);

                CompressKits.compressZip(expectZipFileFullPath, expectFilePath);

                // build result
                builder.fileName(fileName).code(0).destZipFile(expectZipFileFullPath);

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

        executingLatch.await();

        // release all wait thread
        List<InitializrBean> beans = executeSemaphore.get(sha1);
        if (beans != null && !beans.isEmpty()) {
          for (InitializrBean initializrBean : beans) {
            initializrBean.getWaitLatch().countDown();
          }
        }

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
