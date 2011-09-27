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

import com.google.api.client.discovery.wireformat.DirectoryDocument;
import com.google.api.client.discovery.wireformat.DirectoryDocument.DirectoryItem;
import com.google.api.client.discovery.wireformat.DiscoveryDocument;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

/**
 * Helper for interacting with the Discovery service.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class Helper {
  private static final String BASE_DISCOVERY_URL = "https://www.googleapis.com/discovery/v1/apis";

  private static Gson gson = new Gson();

  /**
   * Fetch and deserialize the Discovery document for the given API.
   *
   * @param apiName Name of the API to fetch.
   * @param apiVersion Version of the API to fetch.
   * @return Discovery document.
   */
  public static Discovery getDiscovery(String apiName, String apiVersion)
      throws JsonSyntaxException, JsonIOException, IOException {
    Preconditions.checkNotNull(apiName);
    Preconditions.checkNotNull(apiVersion);
    String urlString =
        Joiner.on('/').join(ImmutableList.of(BASE_DISCOVERY_URL, apiName, apiVersion, "rpc"));
    URL url = new URL(urlString);
    DiscoveryDocument wire =
        gson.fromJson(new InputStreamReader(url.openStream()), DiscoveryDocument.class);
    return new Discovery(wire);
  }

  /**
   * Fetch the Directory document.
   *
   * @return The Directory document.
   */
  public static DirectoryDocument getDirectoryDocument() throws IOException {
    URL url = new URL(BASE_DISCOVERY_URL);
    return gson.fromJson(new InputStreamReader(url.openStream()), DirectoryDocument.class);
  }

  /**
   * Fetch a list of Discovery objects based on the items available in
   * directory.
   *
   * @return List of the Directory document or null place-holders when an
   *         exception occurred.
   */
  public static List<Discovery> getApisFromDirectory() throws IOException {
    DirectoryDocument wire = getDirectoryDocument();
    return Lists.transform(wire.getItems(), new Function<DirectoryItem, Discovery>() {
      public Discovery apply(DirectoryItem api) {
        try {
          return getDiscovery(api.getName(), api.getVersion());
        } catch (IOException e) {
          // Intentionally blank
        }
        return null;
      }
    });
  }
}