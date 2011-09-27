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

import com.google.api.client.discovery.wireformat.DiscoveryDocument.Schema;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * This class describes a type that is a JSON object, which is often a Map or
 * object containing key-value properties.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class ObjectType extends DiscoveryType {

  @Override
  public ObjectType getObject() {
    return this;
  }

  /**
   * Returns the named properties and the strongly typed description of what
   * they will contain.
   */
  public Map<String, DiscoveryType> getProperties() {
    Map<String, Schema> props = schemaNode.getProperties();
    return props == null ? null : Maps.transformValues(props, schemaToDisicoveryType);
  }

  /**
   * Returns the type of any properties that exist on the object that are not in
   * the named properties section.
   */
  public DiscoveryType getAdditionalPropertyType() {
    return schemaToDisicoveryType.apply(schemaNode.getAdditionalProperties());
  }
}
