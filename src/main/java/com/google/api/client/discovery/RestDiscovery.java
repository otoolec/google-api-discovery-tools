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

import com.google.api.client.discovery.types.DiscoveryType;
import com.google.api.services.discovery.model.Jsonschema;
import com.google.api.services.discovery.model.RestDescription;
import com.google.api.services.discovery.model.RestDescriptionAuthOauth2Scopes;
import com.google.api.services.discovery.model.Restmethod;
import com.google.api.services.discovery.model.Restresource;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Maps.EntryTransformer;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Main class that describes an API entirely.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class RestDiscovery implements BaseDiscovery {
  private final RestDescription document;

  /**
   * Create an instance.
   *
   * @param document Wire format bound version of the discovery document.
   */
  public RestDiscovery(RestDescription document) {
    this.document = Preconditions.checkNotNull(document);
  }

  /**
   * The base URI path for REST requests.
   */
  public String getBasePath() {
    return document.getBasePath();
  }

  @Override
  public String getDescription() {
    return document.getDescription();
  }

  @Override
  public String getDocumentationLink() {
    return document.getDocumentationLink();
  }

  @Override
  public List<String> getFeatures() {
    return document.getFeatures();
  }

  @Override
  public List<IconDescription> getIcons() {
    return ImmutableList.of(IconDescription.createX16Icon(document.getIcons().getX16()),
        IconDescription.createX32Icon(document.getIcons().getX32()));
  }

  @Override
  public String getId() {
    return document.getId();
  }

  @Override
  public List<String> getLabels() {
    return document.getLabels();
  }

  /**
   * Returns a list of the methods in this API.
   */
  public Map<String, RestMethod> getMethods() {
    if (document.getMethods() == null) {
      return Collections.emptyMap();
    }

    return Maps.transformValues(
        document.getMethods(), new Function<Restmethod, RestMethod>() {
          public RestMethod apply(Restmethod input) {
            return new RestMethod(document.getSchemas(), input);
          }
        });
  }

  @Override
  public String getName() {
    return document.getName();
  }

  @Override
  public Map<String, DiscoveryType> getParameters() {
    return transformSchemaMap(document.getParameters());
  }

  /**
   * Returns a map of the resources in this API.
   */
  /**
   * Returns a map of the sub-resources on this resource.
   */
  public Map<String, RestResource> getResources() {
    if (document.getResources() == null) {
      return Collections.emptyMap();
    }

    return Maps.transformValues(
        document.getResources(), new Function<Restresource, RestResource>() {
          public RestResource apply(Restresource input) {
            return new RestResource(input, document.getSchemas());
          }
        });
  }

  @Override
  public Map<String, DiscoveryType> getSchemas() {
    return transformSchemaMap(document.getSchemas());
  }

  @Override
  public String getTitle() {
    return document.getTitle();
  }

  @Override
  public String getVersion() {
    return document.getVersion();
  }

  @Override
  public Map<String, OAuth2Scope> getOAuth2Scopes() {
    // Make sure we actually have some scopes to work with
    if (document.getAuth() == null || document.getAuth().getOauth2() == null
        || document.getAuth().getOauth2().getScopes() == null) {
      return Collections.emptyMap();
    }

    return Maps.transformEntries(document.getAuth().getOauth2().getScopes(),
        new EntryTransformer<String, RestDescriptionAuthOauth2Scopes, OAuth2Scope>() {
          public OAuth2Scope transformEntry(String key, RestDescriptionAuthOauth2Scopes value) {
            return new OAuth2Scope(key, value.getDescription());
          }
        });
  }

  private Map<String, DiscoveryType> transformSchemaMap(Map<String, Jsonschema> input) {
    if (input == null) {
      return Collections.emptyMap();
    }

    return Maps.transformValues(input, new Function<Jsonschema, DiscoveryType>() {
      public DiscoveryType apply(Jsonschema value) {
        return DiscoveryType.createTypeFromSchemaNode(value, document.getSchemas());
      }
    });
  }

  @Override
  public boolean equals(Object rhs) {
    if (rhs instanceof RestDiscovery) {
      return Objects.equal(document, ((RestDiscovery) rhs).document);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return document.hashCode();
  }
}
