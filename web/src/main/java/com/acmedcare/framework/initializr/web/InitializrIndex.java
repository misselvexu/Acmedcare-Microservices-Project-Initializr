package com.acmedcare.framework.initializr.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@link InitializrIndex}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
@RestController
public class InitializrIndex {

  private static final Logger log = LoggerFactory.getLogger(InitializrIndex.class);

  @GetMapping("/")
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  @PostMapping("/starter.zip")
  public ResponseEntity<Resource> initializr(
      @RequestParam(defaultValue = "com.acmedcare.framework") String groupId,
      @RequestParam(defaultValue = "") String artifactId,
      @RequestParam(defaultValue = "demo") String name,
      @RequestParam(defaultValue = "com.acmedcare.framework.microservices.demo") String packageName,
      @RequestParam(required = false, defaultValue = "") String description,
      @RequestParam(required = false, defaultValue = "jar") String packaging,
      @RequestParam(required = false, defaultValue = "1.8") String javaVersion) {

    //


    return null;
  }
}
