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
 * Class that describes a single OAuth2 scope.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class OAuth2Scope {
  private final String scopeName;
  private final String description;

  /**
   * Create an OAuth scope.
   *
   * @param scopeName Name for the OAuth 2.0 scope.
   * @param description Description of the permissions requested by the scope.
   */
  OAuth2Scope(String scopeName, String description) {
    this.scopeName = Preconditions.checkNotNull(scopeName);
    this.description = description;
  }

  /**
   * Returns the name of this OAuth 2.0 scope.
   */
  public String getScopeName() {
    return scopeName;
  }

  /**
   * Returns the description of what permissions are requested by this OAuth 2.0
   * scope or {@code null} if none was provided.
   */
  public String getDescription() {
    return description;
  }
}
