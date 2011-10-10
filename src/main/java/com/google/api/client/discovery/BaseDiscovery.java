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

import java.util.List;
import java.util.Map;

/**
 * Base interface with common attributes for all versions of discovery.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public interface BaseDiscovery {

  /**
   * Returns a text description of the API which this document describes.
   */
  public abstract String getDescription();

  /**
   * Return a link at which documentation can be found.
   */
  public abstract String getDocumentationLink();

  /**
   * Returns a list of supported features of this API.
   */
  public abstract List<String> getFeatures();

  /**
   * Returns a map which maps icon size to a URL for that size icon.
   */
  public abstract List<IconDescription> getIcons();

  /**
   * Returns the ID of this document, in the form <name>:<version>.
   */
  public abstract String getId();

  /**
   * Returns a list of labels for the status of this API, such as labs or
   * deprecated.
   */
  public abstract List<String> getLabels();

  /**
   * Returns the name of this document.
   */
  public abstract String getName();

  /**
   * Returns the extra parameters available in all methods of this API.
   */
  public abstract Map<String, DiscoveryType> getParameters();

  /**
   * The schemas for this API.
   */
  public abstract Map<String, DiscoveryType> getSchemas();

  /**
   * The title of this API.
   */
  public abstract String getTitle();

  /**
   * Returns the version of this document.
   */
  public abstract String getVersion();

  /**
   * Returns a map of the OAuth 2.0 scopes names to the {@link OAuth2Scope}
   * descriptions.
   */
  public abstract Map<String, OAuth2Scope> getOAuth2Scopes();

}
