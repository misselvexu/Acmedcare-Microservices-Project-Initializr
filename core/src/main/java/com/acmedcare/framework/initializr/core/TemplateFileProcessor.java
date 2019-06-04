package com.acmedcare.framework.initializr.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * {@link TemplateFileProcessor}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
@Component
public class TemplateFileProcessor {

  private static final Logger log = LoggerFactory.getLogger(TemplateFileProcessor.class);

  private final InitializrProcessorProperties processorProperties;

  public TemplateFileProcessor(InitializrProcessorProperties processorProperties) {
    this.processorProperties = processorProperties;
  }

  /**
   * Process Method
   *
   * @param bean init bean instance
   * @return result
   */
  public InitializrResult process(InitializrBean bean) {

    return null;
  }

  /**
   * Processor Worker
   *
   * <p>
   */
  private static class Processor {}
}
