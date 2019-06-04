package com.acmedcare.framework.initializr.core;

import lombok.*;

import java.io.Serializable;

/**
 * {@link InitializrResult}
 *
 * @author <a href="mailto:iskp.me@gmail.com">Elve.Xu</a>
 * @version ${project.version} - 2019-06-04.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitializrResult implements Serializable {

  /**
   * Dest Zipped File Full Path
   *
   * <p>
   */
  private String destZipFile;

  /**
   * File Name
   *
   * <p>
   */
  private String fileName;

  /**
   * process result code
   *
   * <p>default: -1 ,otherwise success code is 0
   */
  private int code = -1;
}
