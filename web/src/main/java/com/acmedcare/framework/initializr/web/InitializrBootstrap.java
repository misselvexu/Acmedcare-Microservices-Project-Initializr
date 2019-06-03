package com.acmedcare.framework.initializr.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * {@link InitializrBootstrap}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
@SpringBootApplication
public class InitializrBootstrap {

  public static void main(String[] args) {
    SpringApplication.run(InitializrBootstrap.class, args);
  }
}
