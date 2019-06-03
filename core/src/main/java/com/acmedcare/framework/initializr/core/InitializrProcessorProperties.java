package com.acmedcare.framework.initializr.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * {@link InitializrProcessorProperties}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-03.
 */
@Getter
@Setter
public class InitializrProcessorProperties implements Serializable {

  /**
   * Processor Thread's Nums
   *
   * <p>
   */
  private int processorThreadNums = Runtime.getRuntime().availableProcessors() << 1;
}
