package com.acmedcare.framework.initializr.web.config;

import com.acmedcare.framework.initializr.core.InitializrConstants;
import com.acmedcare.framework.initializr.core.InitializrProcessorProperties;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.acmedcare.framework.initializr.core.InitializrConstants.CREATED_DIR;

/**
 * {@link InitializrProperties}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "acmedcare.micro-services.initializr")
public class InitializrProperties extends InitializrProcessorProperties
    implements Serializable, InitializingBean {

  private static final Logger log = LoggerFactory.getLogger(InitializrProperties.class);

  /**
   * Created Zip Dir
   *
   * <p>
   */
  private String createdZipDir = System.getProperty("user.dir") + File.separator + CREATED_DIR;

  /**
   * Micro Service Project Template Dir ,[Optional]
   *
   * <p>if you not set , will get jvm system properties
   * [-Dacmedcare.initializr.home=/opt/initializr-app] value
   */
  private String templateDir;

  /**
   * Invoked by the containing {@code BeanFactory} after it has set all bean properties and
   * satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
   *
   * <p>This method allows the bean instance to perform validation of its overall configuration and
   * final initialization when all bean properties have been set.
   *
   * @throws Exception in the event of misconfiguration (such as failure to set an essential
   *     property) or if initialization fails for any other reason
   */
  @Override
  public void afterPropertiesSet() throws Exception {

    log.info(">>> Origin Initializr Properties : {} ", JSON.toJSONString(this));

    if (!StringUtils.hasText(this.createdZipDir)) {
      this.createdZipDir = System.getProperty("user.dir") + File.separator + CREATED_DIR;
    }

    if (!StringUtils.hasText(templateDir)) {
      this.templateDir = InitializrConstants.home;
    }

    if (!StringUtils.hasText(templateDir) || !new File(this.templateDir).exists()) {
      throw new IllegalArgumentException(
          "[INITIALIZR] Acmedcare+ Micro-Service Project Template Dir must not be exist.");
    }

    // check
    Path createdZipDirInstance = Paths.get(this.createdZipDir);
    if (!Files.exists(createdZipDirInstance)) {
      Files.createDirectory(createdZipDirInstance);
    }

    log.info(">>> Final Initializr Properties : {} ", JSON.toJSONString(this));
  }
}
