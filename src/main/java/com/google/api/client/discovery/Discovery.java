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
import com.google.api.client.discovery.wireformat.DiscoveryDocument;
import com.google.api.client.discovery.wireformat.DiscoveryDocument.Schema;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
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
public class Discovery {
  private final DiscoveryDocument document;

  /**
   * Create an instance.
   *
   * @param document Wire format bound version of the discovery document.
   */
  public Discovery(DiscoveryDocument document) {
    this.document = Preconditions.checkNotNull(document);
  }

  /**
   * Returns a text description of the API which this document describes.
   */
  public String getDescription() {
    return document.getDescription();
  }

  /**
   * Return a link at which documentation can be found.
   */
  public String getDocumentationLink() {
    return document.getDocumentationLink();
  }

  /**
   * Returns the ID of this document, in the form <name>:<version>.
   */
  public String getId() {
    return document.getId();
  }

  /**
   * Returns a list of the methods in this API.
   */
  public Map<String, Method> getMethods() {
    if (document.getMethods() == null) {
      return Collections.emptyMap();
    }

    return Maps.transformValues(
        document.getMethods(), new Function<DiscoveryDocument.Method, Method>() {
          public Method apply(DiscoveryDocument.Method input) {
            return new Method(document.getSchemas(), input);
          }
        });
  }

  /**
   * Returns the name of this document.
   */
  public String getName() {
    return document.getName();
  }

  public Map<String, DiscoveryType> getSchemas() {
    return transformSchemaMap(document.getSchemas());
  }

  /**
   * Returns the version of this document.
   */
  public String getVersion() {
    return document.getVersion();
  }

  /**
   * Returns the extra parameters available in all methods of this API.
   */
  public Map<String, DiscoveryType> getParameters() {
    return transformSchemaMap(document.getParameters());
  }

  /**
   * Returns the path at which requests should be made.
   */
  public String getRpcPath() {
    return document.getRpcPath();
  }

  /**
   * Returns a list of supported features of this API.
   */
  public List<String> getFeatures() {
    return document.getFeatures();
  }

  /**
   * Returns a list of labels for the status of this API, such as labs or
   * deprecated.
   */
  public List<String> getLabels() {
    return document.getLabels();
  }

  /**
   * Returns a map which maps icon size to a URL for that size icon.
   */
  public Map<String, String> getIcons() {
    return document.getIcons();
  }

  /**
   * Returns a map of the OAuth 2.0 scopes names to the {@link OAuth2Scope}
   * descriptions.
   */
  public Map<String, OAuth2Scope> getOAuth2Scopes() {
    // Make sure we actually have some scopes to work with
    if (document.getAuth() == null || document.getAuth().getOAuth2() == null
        || document.getAuth().getOAuth2().getScopes() == null) {
      return Collections.emptyMap();
    }

    return Maps.transformEntries(document.getAuth().getOAuth2().getScopes(),
        new EntryTransformer<String, DiscoveryDocument.OAuth2Scope, OAuth2Scope>() {
          public OAuth2Scope transformEntry(String key, DiscoveryDocument.OAuth2Scope value) {
            return new OAuth2Scope(key, value.getDescription());
          }
        });
  }

  private Map<String, DiscoveryType> transformSchemaMap(Map<String, Schema> input) {
    if (input == null) {
      return Collections.emptyMap();
    }

    return Maps.transformValues(input, new Function<Schema, DiscoveryType>() {
      public DiscoveryType apply(Schema value) {
        return DiscoveryType.createTypeFromSchemaNode(value, document.getSchemas());
      }
    });
  }

  @Override
  public boolean equals(Object rhs) {
    if (rhs instanceof Discovery) {
      return Objects.equal(document, ((Discovery) rhs).document);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return document.hashCode();
  }
}
