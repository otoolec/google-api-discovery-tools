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

package com.google.api.client.discovery.wireformat;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Representation of the directory document as it will be deserialized.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class DirectoryDocument {

  /**
   * Representation of a single service as it will be deserialized.
   */
  public static class DirectoryItem extends AbstractDiscoveryItem {
    private final Boolean preferred;

    public DirectoryItem() {
      this(null, null, null, null, null, null, null, null, null);
    }

    DirectoryItem(String id,
        List<String> labels,
        String name,
        String version,
        String description,
        String documentationLink,
        Map<String, String> icons,
        Boolean preferred,
        String title) {
      super(id, labels, name, version, description, documentationLink, icons, title);
      this.preferred = preferred;
    }

    /**
     * Returns true if this version is the preferred version to use.
     */
    public Boolean getPreferred() {
      return preferred;
    }
  }

  private final List<DirectoryItem> items;

  /**
   * This constructor for use by GSON only.
   */
  public DirectoryDocument() {
    this(null);
  }

  DirectoryDocument(List<DirectoryItem> items) {
    this.items = items;
  }

  /**
   * Returns a list of directory items, one for each service.
   */
  public List<DirectoryItem> getItems() {
    return Collections.unmodifiableList(items);
  }
}
