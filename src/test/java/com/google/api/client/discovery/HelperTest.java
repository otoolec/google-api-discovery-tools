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

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author moshenko@google.com (Jake Moshenko)
 *
 */
public class HelperTest extends TestCase {
  public void testHelper() throws IOException {
    List<RestDiscovery> allBeans = RestHelper.getApisFromDirectory();
    assertTrue(allBeans.size() > 20);

    RestDiscovery urlShortener = RestHelper.getDiscovery("urlshortener", "v1");
    assertEquals(1, urlShortener.getResources().size());

    RestDiscovery fileUrlShortener =
        RestHelper.getDiscoveryFromFile(new File("src/test/resources/urlshortener-v1-rest.json"));
    assertEquals(1, fileUrlShortener.getResources().size());
  }
}
