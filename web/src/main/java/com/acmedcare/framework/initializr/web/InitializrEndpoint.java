package com.acmedcare.framework.initializr.web;

import com.acmedcare.framework.initializr.core.TemplateFileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@link InitializrEndpoint}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
@RestController
public class InitializrEndpoint {

  private static final Logger log = LoggerFactory.getLogger(InitializrEndpoint.class);

  private final TemplateFileProcessor fileProcessor;

  public InitializrEndpoint(TemplateFileProcessor fileProcessor) {
    this.fileProcessor = fileProcessor;
  }

  @GetMapping("/")
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  /**
   * 生成并下载项目末班
   *
   * @param groupId groupId
   * @param artifactId artifactId
   * @param name name
   * @param packageName package
   * @param description description
   * @param packaging packing type
   * @param javaVersion java version
   * @return zip file stream
   */
  @RequestMapping("/starter.zip")
  public ResponseEntity<Resource> initializr(
      @RequestParam(defaultValue = "com.acmedcare.framework") String groupId,
      @RequestParam(defaultValue = "") String artifactId,
      @RequestParam(defaultValue = "demo") String name,
      @RequestParam(defaultValue = "com.acmedcare.framework.microservices.demo") String packageName,
      @RequestParam(required = false, defaultValue = "") String description,
      @RequestParam(required = false, defaultValue = "jar") String packaging,
      @RequestParam(required = false, defaultValue = "1.8") String javaVersion) {

    return null;
  }
}
