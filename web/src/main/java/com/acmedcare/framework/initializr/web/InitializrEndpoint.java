package com.acmedcare.framework.initializr.web;

import com.acmedcare.framework.exception.defined.InvalidRequestParamException;
import com.acmedcare.framework.initializr.core.InitializrBean;
import com.acmedcare.framework.initializr.core.InitializrResult;
import com.acmedcare.framework.initializr.core.TemplateFileProcessor;
import com.acmedcare.framework.initializr.web.config.InitializrProperties;
import com.acmedcare.framework.initializr.web.exception.InitializrException;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

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

  private final InitializrProperties properties;

  public InitializrEndpoint(TemplateFileProcessor fileProcessor, InitializrProperties properties) {
    this.fileProcessor = fileProcessor;
    this.properties = properties;
  }

  @GetMapping("/")
  public ModelAndView index() {
    return new ModelAndView("index");
  }

  /**
   * 生成并下载项目模板
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
      @RequestParam(defaultValue = "") String version,
      @RequestParam(defaultValue = "demo") String name,
      @RequestParam(defaultValue = "com.acmedcare.framework.microservices.demo") String packageName,
      @RequestParam(required = false, defaultValue = "") String description,
      @RequestParam(required = false, defaultValue = "jar") String packaging,
      @RequestParam(required = false, defaultValue = "1.8") String javaVersion) {

    if (StringUtils.isAnyBlank(groupId, artifactId, version, name, packageName)) {
      throw new InvalidRequestParamException(
          "请求参数[groupId, artifactId, version, name, packageName]不能为空");
    }

    InitializrBean bean =
        InitializrBean.builder()
            .groupId(groupId)
            .artifactId(artifactId)
            .version(version)
            .name(name)
            .packageName(packageName)
            .description(description)
            .packaging(packaging)
            .javaVersion(javaVersion)
            .build();

    log.info("Request initializr bean info : {}", JSON.toJSONString(bean));

    // process
    InitializrResult result =
        this.fileProcessor.process(
            this.properties.getTemplateDir(), this.properties.getCreatedZipDir(), bean);

    if (result == null || result.getCode() != 0) {
      throw new InitializrException("项目模板初始化失败");
    }

    File file = new File(result.getDestZipFile());
    Resource resource = new FileSystemResource(file);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment", resource.getFilename());
    headers.setContentLength(file.length());

    // return
    return new ResponseEntity<>(resource, headers, HttpStatus.CREATED);
  }
}
