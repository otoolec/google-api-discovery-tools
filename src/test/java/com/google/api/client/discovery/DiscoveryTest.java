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

import com.google.api.client.discovery.Method.Parameter;
import com.google.api.client.discovery.types.DiscoveryType;
import com.google.api.client.discovery.types.StringType;
import com.google.api.client.discovery.wireformat.DiscoveryDocument;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Tests for the {@link Discovery} class.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class DiscoveryTest extends TestCase {

  private DiscoveryDocument doc;
  private Discovery discovery;

  @Override
  public void setUp() throws Exception {
    doc =
        new Gson().fromJson(new InputStreamReader(
            new FileInputStream("src/test/resources/urlshortener-v1-rpc.json")),
            DiscoveryDocument.class);
    discovery = new Discovery(doc);
  }

  public void testBasic() {
    assertEquals("urlshortener:v1", discovery.getId());
    assertEquals("urlshortener", discovery.getName());
    assertEquals("v1", discovery.getVersion());
    assertEquals(2, discovery.getIcons().size());
    assertTrue(discovery.getDocumentationLink().length() > 0);
    assertEquals("/rpc", discovery.getRpcPath());
    assertEquals(ImmutableList.of("labs"), discovery.getLabels());
  }

  public void testMethods() {
    assertEquals(3, discovery.getMethods().size());
    assertTrue(discovery.getMethods().containsKey("urlshortener.url.get"));
    assertTrue(discovery.getMethods().containsKey("urlshortener.url.insert"));
    assertTrue(discovery.getMethods().containsKey("urlshortener.url.list"));

    Method getMethod = discovery.getMethods().get("urlshortener.url.get");
    assertEquals("urlshortener.url.get", getMethod.getId());
    assertTrue(getMethod.getDescription().length() > 0);

    List<Parameter> getParams = getMethod.getRequiredParameters();
    assertEquals(1, getParams.size());
    assertEquals("shortUrl", getParams.get(0).getName());
    assertTrue(getParams.get(0).getType().getRequired());
    assertEquals(DiscoveryType.BaseType.STRING, getParams.get(0).getType().getBaseType());

    Collection<Parameter> optionalGetParams = getMethod.getOptionalParameters();
    assertEquals(1, optionalGetParams.size());

    Parameter projection = optionalGetParams.iterator().next();
    assertEquals("projection", projection.getName());
    assertEquals(DiscoveryType.BaseType.STRING, projection.getType().getBaseType());

    StringType projType = projection.getType().getString();
    assertTrue(projType.isEnum());
    assertEquals(3, projType.getEnumValues().size());
    assertEquals(3, projType.getEnumDescriptions().size());
    assertTrue(projType.getEnumValues().contains("ANALYTICS_CLICKS"));

    DiscoveryType urlType = getMethod.getReturnType();
    assertEquals(DiscoveryType.BaseType.OBJECT, urlType.getBaseType());
    assertEquals("Url", urlType.getId());

    Map<String, DiscoveryType> urlProps = urlType.getObject().getProperties();
    assertEquals(6, urlProps.size());
    assertTrue(urlProps.containsKey("analytics"));
    assertTrue(urlProps.containsKey("created"));

    DiscoveryType analytics = urlProps.get("analytics");
    assertEquals("AnalyticsSummary", analytics.getId());
    assertEquals(DiscoveryType.BaseType.OBJECT, analytics.getBaseType());
    assertEquals(5, analytics.getObject().getProperties().size());
  }

  public void testAuth() {
    Map<String, OAuth2Scope> auth = discovery.getOAuth2Scopes();
    assertEquals(1, auth.size());
    assertEquals(Sets.newHashSet("https://www.googleapis.com/auth/urlshortener"), auth.keySet());
    assertEquals("https://www.googleapis.com/auth/urlshortener",
        auth.entrySet().iterator().next().getValue().getScopeName());

    List<String> scopes = discovery.getMethods().get("urlshortener.url.insert").getScopes();
    assertEquals(1, scopes.size());
    assertEquals("https://www.googleapis.com/auth/urlshortener", scopes.get(0));
  }

  public void testCommonParameters() {
    Map<String, DiscoveryType> commonParameters = discovery.getParameters();
    assertEquals(Sets.newHashSet("alt", "fields", "key", "oauth_token", "prettyPrint", "userIp"),
        commonParameters.keySet());
    assertEquals(DiscoveryType.BaseType.BOOLEAN, commonParameters.get("prettyPrint").getBaseType());
  }

  public void testSchemas() {
    Map<String, DiscoveryType> schemas = discovery.getSchemas();
    assertEquals(5, schemas.size());
    assertEquals(Sets.newHashSet(
        "AnalyticsSnapshot", "AnalyticsSummary", "StringCount", "Url", "UrlHistory"),
        schemas.keySet());

  }
}
