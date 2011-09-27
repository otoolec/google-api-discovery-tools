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
 * Base class for the simple types: string, boolean, integer, number.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public abstract class PrimitiveType extends DiscoveryType {

  /**
   * An additional regular expression or key that helps constrain the value or
   * {@code null} if none was provided. For more details see:
   * http://tools.ietf.org/html/draft-zyp-json-schema-03#section-5.23
   */
  public String getFormat() {
    return schemaNode.getFormat();
  }
}
