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
 * Type which will indicate a true or false condition.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class BooleanType extends DiscoveryType {

  @Override
  public BooleanType getBoolean() {
    return this;
  }

  /**
   * Returns the default if one was specified or {@code null} if none was specified.
   */
  public Boolean getDefault() {
    String def = schemaNode.getDefault();
    return def == null ? null : Boolean.parseBoolean(schemaNode.getDefault());
  }
}
