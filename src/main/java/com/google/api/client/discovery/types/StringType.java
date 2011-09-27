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

import java.util.List;

/**
 * Class that describes a string variable.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class StringType extends PrimitiveType {

  @Override
  public StringType getString() {
    return this;
  }

  /**
   * Regular expression against which a value can be validated.
   */
  public String getPattern() {
    return schemaNode.getPattern();
  }

  /**
   * Returns all possible values that can be passed as a value for this enumeration.
   */
  public List<String> getEnumValues() {
    return schemaNode.getEnumValues();
  }

  /**
   * Returns descriptions of the values that can be passed for this enumeration.
   */
  public List<String> getEnumDescriptions() {
    return schemaNode.getEnumDescriptions();
  }

  /**
   * Returns whether or not this field is an enumeration.
   */
  public boolean isEnum() {
    return schemaNode.getEnumValues() != null;
  }

  public String getDefault() {
    return schemaNode.getDefault();
  }
}
