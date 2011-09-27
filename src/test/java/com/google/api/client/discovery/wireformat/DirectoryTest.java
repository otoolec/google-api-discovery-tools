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

import com.google.api.client.discovery.wireformat.DirectoryDocument.DirectoryItem;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Test for proper deserialization of the Directory document.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class DirectoryTest extends TestCase {
  public void testDirectory() throws Exception {
    DirectoryDocument doc = new Gson().fromJson(
        new InputStreamReader(new FileInputStream("src/test/resources/directory.json")),
        DirectoryDocument.class);

    assertEquals(3, doc.getItems().size());

    DirectoryItem adExchange = doc.getItems().get(0);
    assertEquals("adexchangebuyer:v1", adExchange.getId());
    assertEquals("adexchangebuyer", adExchange.getName());
    assertEquals("v1", adExchange.getVersion());
    assertTrue(adExchange.getDescription().length() > 0);
    assertTrue(adExchange.getTitle().length() > 0);
    assertTrue(adExchange.getDocumentationLink().length() > 0);
    assertEquals(ImmutableList.of("labs"), adExchange.getLabels());
    assertEquals(Boolean.TRUE, adExchange.getPreferred());
  }
}
