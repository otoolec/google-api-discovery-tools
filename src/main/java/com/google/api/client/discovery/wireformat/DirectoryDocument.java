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
  public class DirectoryItem {
    private final String id;
    private final List<String> labels;
    private final String name;
    private final String version;
    private final String description;
    private final String documentationLink;
    private final Map<String, String> icons;
    private final Boolean preferred;
    private final String title;

    /**
     * This constructor for use by GSON only.
     */
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
      super();
      this.id = id;
      this.labels = labels;
      this.name = name;
      this.version = version;
      this.description = description;
      this.documentationLink = documentationLink;
      this.icons = icons;
      this.preferred = preferred;
      this.title = title;
    }

    /**
     * Returns the id for this service in the format apiname:version
     */
    public String getId() {
      return id;
    }

    /**
     * Returns a list of labels associated with this service. E.g. "labs"
     */
    public List<String> getLabels() {
      return labels;
    }

    /**
     * Return the name identifier of this API.
     */
    public String getName() {
      return name;
    }

    /**
     * Return the version identifier of this API.
     */
    public String getVersion() {
      return version;
    }

    /**
     * Returns the description.
     */
    public String getDescription() {
      return description;
    }

    /**
     * Returns a map which maps icon size to a URL for that size icon.
     */
    public Map<String, String> getIcons() {
      return Collections.unmodifiableMap(icons);
    }

    /**
     * Returns the link at which documentation can be found.
     */
    public String getDocumentationLink() {
      return documentationLink;
    }

    /**
     * Returns true if this version is the preferred version to use.
     */
    public Boolean getPreferred() {
      return preferred;
    }

    /**
     * Returns the title of this API.
     */
    public String getTitle() {
      return title;
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
