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

package com.google.api.client.discovery.types;

/**
 * Type that represents integral numerical data.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class IntegerType extends PrimitiveType {
  @Override
  public IntegerType getInteger() {
    return this;
  }

  /**
   * Returns the minimum possible value that can be used for this type or {@code
   * null} if none was specified.
   */
  public Integer getMinimum() {
    String min = schemaNode.getMinimum();
    return min == null ? null : Integer.parseInt(min);
  }

  /**
   * Returns the maximum possible value that can be used for this type or {@code
   * null} if none was specified.
   */
  public Integer getMaximum() {
    String max = schemaNode.getMaximum();
    return max == null ? null : Integer.parseInt(max);
  }

  /**
   * Returns the default value.
   */
  public Integer getDefault() {
    String def = schemaNode.getDefault();
    return def == null ? null : Integer.parseInt(def);
  }
}
