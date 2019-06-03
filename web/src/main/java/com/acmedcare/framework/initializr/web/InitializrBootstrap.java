package com.acmedcare.framework.initializr.web;

import com.acmedcare.framework.initializr.core.TemplateFileProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * {@link InitializrBootstrap}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
@SpringBootApplication
@ComponentScans(@ComponentScan(basePackageClasses = TemplateFileProcessor.class))
public class InitializrBootstrap {

  public static void main(String[] args) {
    SpringApplication.run(InitializrBootstrap.class, args);
  }
}
