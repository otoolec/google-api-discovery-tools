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

import com.google.api.services.discovery.model.Jsonschema;
import com.google.api.services.discovery.model.Restmethod;
import com.google.api.services.discovery.model.Restresource;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Map;

/**
 * A resource type in a RESTful API. Contains methods and sub-resources.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class RestResource {
  private final Restresource resource;
  private final Map<String, Jsonschema> topLevelSchemas;

  /**
   * Create an instance.
   *
   * @param resource Wire format bound version of the resource block.
   * @param topLevelSchemas Map of the named top level schemas.
   */
  RestResource(Restresource resource, Map<String, Jsonschema> topLevelSchemas) {
    this.resource = Preconditions.checkNotNull(resource);
    this.topLevelSchemas = Preconditions.checkNotNull(topLevelSchemas);
  }

  /**
   * Returns a map of the methods on this resource.
   */
  public Map<String, RestMethod> getMethods() {
    if (resource.getMethods() == null) {
      return Collections.emptyMap();
    }

    return Maps.transformValues(resource.getMethods(), new Function<Restmethod, RestMethod>() {
      public RestMethod apply(Restmethod input) {
        return new RestMethod(topLevelSchemas, input);
      }
    });
  }

  /**
   * Returns a map of the sub-resources on this resource.
   */
  public Map<String, RestResource> getResources() {
    if (resource.getResources() == null) {
      return Collections.emptyMap();
    }

    return Maps.transformValues(
        resource.getResources(), new Function<Restresource, RestResource>() {
          public RestResource apply(Restresource input) {
            return new RestResource(input, topLevelSchemas);
          }
        });
  }
}
