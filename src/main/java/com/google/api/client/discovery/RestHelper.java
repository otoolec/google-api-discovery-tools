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

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.CustomizeJsonParser;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.discovery.Discovery;
import com.google.api.services.discovery.model.DirectoryList;
import com.google.api.services.discovery.model.DirectoryListItems;
import com.google.api.services.discovery.model.RestDescription;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Helper for interacting with the Discovery service.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class RestHelper {
  private static final String BASE_DISCOVERY_URL = "https://www.googleapis.com/discovery/v1/";

  /**
   * Fetch and deserialize the Discovery document for the given API.
   *
   * @param apiName Name of the API to fetch.
   * @param apiVersion Version of the API to fetch.
   * @return Discovery document.
   */
  public static RestDiscovery getDiscovery(String apiName, String apiVersion) throws IOException {
    try {
      return getDiscovery(apiName, apiVersion, BASE_DISCOVERY_URL);
    } catch (URISyntaxException e) {
      // We are providing the URI so we should never get here
      throw new IllegalStateException(e);
    }
  }

  /**
   * Fetch and deserialize the Discovery document for the given API.
   *
   * @param apiName Name of the API to fetch.
   * @param apiVersion Version of the API to fetch.
   * @param discoveryUrl Base url from which to fetch the discovery document.
   * @return Discovery document.
   */
  public static RestDiscovery getDiscovery(String apiName, String apiVersion, String discoveryUrl)
      throws IOException, URISyntaxException {
    Preconditions.checkNotNull(apiName);
    Preconditions.checkNotNull(apiVersion);
    Preconditions.checkNotNull(discoveryUrl);

    Discovery discoveryClient = new Discovery(new NetHttpTransport(), new GsonFactory());
    changeClientToUrl(discoveryClient, discoveryUrl);
    RestDescription restDescription = discoveryClient.apis.getRest(apiName, apiVersion).execute();

    return new RestDiscovery(restDescription);
  }

  /**
   * Load and parse the REST discovery file from disk.
   *
   * @param discoveryFile File instance to parse.
   * @return Discovery document.
   */
  public static RestDiscovery getDiscoveryFromFile(File discoveryFile) throws IOException {
    Preconditions.checkNotNull(discoveryFile);

    JsonParser parser = new GsonFactory().createJsonParser(new FileReader(discoveryFile));
    RestDescription wire = parser.parseAndClose(RestDescription.class, new CustomizeJsonParser());

    return new RestDiscovery(wire);
  }

  /**
   * Fetch the Directory document.
   *
   * @return The Directory document.
   */
  public static DirectoryList getDirectoryDocument() throws IOException {
    try {
      return getDirectoryDocument(BASE_DISCOVERY_URL);
    } catch (URISyntaxException e) {
      // We are providing the URI so we should never get here
      throw new IllegalStateException(e);
    }
  }

  /**
   * Fetch the Directory document.
   *
   * @param discoveryUrl Url from which to fetch the directory document.
   * @return The Directory document.
   */
  public static DirectoryList getDirectoryDocument(String discoveryUrl)
      throws IOException, URISyntaxException {
    Preconditions.checkNotNull(discoveryUrl);

    Discovery discoveryClient = new Discovery(new NetHttpTransport(), new GsonFactory());
    changeClientToUrl(discoveryClient, discoveryUrl);
    DirectoryList directory = discoveryClient.apis.list().execute();

    return directory;
  }

  /**
   * Fetch a list of Discovery objects based on the items available in
   * directory.
   *
   * @return List of the Directory document or null place-holders when an
   *         exception occurred.
   */
  public static List<RestDiscovery> getApisFromDirectory() throws IOException {
    try {
      return getApisFromDirectory(BASE_DISCOVERY_URL);
    } catch (URISyntaxException e) {
      // We are providing the URI so we should never get here
      throw new IllegalStateException(e);
    }
  }

  /**
   * Fetch a list of Discovery objects based on the items available in
   * directory.
   *
   * @param discoveryUrl Url from which to fetch the directory document.
   * @return List of the Directory document or null place-holders when an
   *         exception occurred.
   */
  public static List<RestDiscovery> getApisFromDirectory(String discoveryUrl)
      throws IOException, URISyntaxException {
    DirectoryList wire = getDirectoryDocument(discoveryUrl);
    return Lists.transform(wire.getItems(), new Function<DirectoryListItems, RestDiscovery>() {
      public RestDiscovery apply(DirectoryListItems api) {
        try {
          return getDiscovery(api.getName(), api.getVersion());
        } catch (IOException e) {
          // Intentionally blank
        }
        return null;
      }
    });
  }

  private static void changeClientToUrl(Discovery client, String discoveryUrl)
      throws URISyntaxException {
    URI uri = new URI(discoveryUrl);
    client.setBaseServer(uri.getScheme() + "://" + uri.getAuthority());
    client.setBasePath(uri.getPath());
  }
}
