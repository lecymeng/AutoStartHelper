package com.weicools.auto.starter;

import androidx.annotation.NonNull;

/**
 * @author Weicools Create on 2020.02.18
 *
 * desc:
 */
public class GuardPermissionPage {
  @NonNull
  private String title;
  @NonNull
  private String componentName;

  public GuardPermissionPage(@NonNull String title, @NonNull String componentName) {
    this.title = title;
    this.componentName = componentName;
  }

  @NonNull
  public String getTitle() {
    return title;
  }

  @NonNull
  public String getComponentName() {
    return componentName;
  }
}
