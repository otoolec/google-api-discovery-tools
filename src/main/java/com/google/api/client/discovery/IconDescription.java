/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.discovery;

import com.google.common.base.Preconditions;

/**
 * Representation of an icon url.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class IconDescription {
  /**
   * Size identifier for the icon.
   */
  public enum Size {
    X16,
    X32,
  }

  private Size size;
  private String url;

  /**
   * Private constructor to prevent instantiation.
   */
  private IconDescription() {
  }

  /**
   * Returns the size for this icon description.
   */
  public Size getSize() {
    return size;
  }

  /**
   * Returns the url of the icon.
   */
  public String getUrl() {
    return url;
  }

  static IconDescription createX16Icon(String url) {
    IconDescription instance = new IconDescription();
    instance.size = Size.X16;
    instance.url = Preconditions.checkNotNull(url);
    return instance;
  }

  static IconDescription createX32Icon(String url) {
    IconDescription instance = new IconDescription();
    instance.size = Size.X16;
    instance.url = Preconditions.checkNotNull(url);
    return instance;
  }
}
