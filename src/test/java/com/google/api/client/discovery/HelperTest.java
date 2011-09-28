// Copyright 2011 Google Inc. All Rights Reserved.

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
    List<Discovery> allBeans = Helper.getApisFromDirectory();
    assertTrue(allBeans.size() > 20);

    Discovery urlShortener = Helper.getDiscovery("urlshortener", "v1");
    assertEquals(3, urlShortener.getMethods().size());

    Discovery fileUrlShortener =
        Helper.getDiscoveryFromFile(new File("src/test/resources/urlshortener-v1-rpc.json"));
    assertEquals(3, fileUrlShortener.getMethods().size());
  }
}
