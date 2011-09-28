// Copyright 2011 Google Inc. All Rights Reserved.

package com.google.api.client.discovery.wireformat;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Common set of parameters that are included in both Directory items and
 * Discovery documents.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public abstract class AbstractDiscoveryItem {
  private final String id;
  private final List<String> labels;
  private final String name;
  private final String version;
  private final String description;
  private final String documentationLink;
  private final Map<String, String> icons;
  private final String title;

  /**
   * This constructor for use by GSON only.
   */
  public AbstractDiscoveryItem() {
    this(null, null, null, null, null, null, null, null);
  }

  AbstractDiscoveryItem(String id,
      List<String> labels,
      String name,
      String version,
      String description,
      String documentationLink,
      Map<String, String> icons,
      String title) {
    super();
    this.id = id;
    this.labels = labels;
    this.name = name;
    this.version = version;
    this.description = description;
    this.documentationLink = documentationLink;
    this.icons = icons;
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
   * Returns the title of this API.
   */
  public String getTitle() {
    return title;
  }
}
