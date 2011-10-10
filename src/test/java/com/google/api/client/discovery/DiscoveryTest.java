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

import com.google.api.client.discovery.RestMethod.Parameter;
import com.google.api.client.discovery.types.DiscoveryType;
import com.google.api.client.discovery.types.StringType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import junit.framework.TestCase;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Tests for the {@link RestDiscovery} class.
 *
 * @author moshenko@google.com (Jake Moshenko)
 */
public class DiscoveryTest extends TestCase {

  private RestDiscovery discovery;

  @Override
  public void setUp() throws Exception {
    discovery =
        RestHelper.getDiscoveryFromFile(new File("src/test/resources/urlshortener-v1-rest.json"));
  }

  public void testBasic() {
    assertEquals("urlshortener:v1", discovery.getId());
    assertEquals("urlshortener", discovery.getName());
    assertEquals("v1", discovery.getVersion());
    assertEquals(2, discovery.getIcons().size());
    assertTrue(discovery.getDocumentationLink().length() > 0);
    assertEquals("/urlshortener/v1/", discovery.getBasePath());
    assertEquals(ImmutableList.of("labs"), discovery.getLabels());
  }

  public void testMethods() {
    assertEquals(1, discovery.getResources().size());
    assertTrue(discovery.getResources().containsKey("url"));

    RestResource urlResource = discovery.getResources().get("url");

    assertEquals(3, urlResource.getMethods().size());
    assertTrue(urlResource.getMethods().containsKey("get"));
    assertTrue(urlResource.getMethods().containsKey("insert"));
    assertTrue(urlResource.getMethods().containsKey("list"));

    RestMethod getMethod = urlResource.getMethods().get("get");
    assertEquals("urlshortener.url.get", getMethod.getId());
    assertTrue(getMethod.getDescription().length() > 0);

    List<Parameter> getParams = getMethod.getRequiredParameters();
    assertEquals(1, getParams.size());
    assertEquals("shortUrl", getParams.get(0).getName());
    assertTrue(getParams.get(0).getType().getRequired());
    assertEquals(DiscoveryType.BaseType.STRING, getParams.get(0).getType().getBaseType());
    assertEquals(RestMethod.ParameterLocation.QUERY, getParams.get(0).getLocation());

    Collection<Parameter> optionalGetParams = getMethod.getOptionalParameters();
    assertEquals(1, optionalGetParams.size());

    Parameter projection = optionalGetParams.iterator().next();
    assertEquals("projection", projection.getName());
    assertEquals(DiscoveryType.BaseType.STRING, projection.getType().getBaseType());
    assertEquals(RestMethod.ParameterLocation.QUERY, projection.getLocation());

    StringType projType = projection.getType().getString();
    assertTrue(projType.isEnum());
    assertEquals(3, projType.getEnumValues().size());
    assertEquals(3, projType.getEnumDescriptions().size());
    assertTrue(projType.getEnumValues().contains("ANALYTICS_CLICKS"));

    DiscoveryType urlType = getMethod.getResponse();
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

    List<String> scopes =
        discovery.getResources().get("url").getMethods().get("insert").getScopes();
    assertEquals(1, scopes.size());
    assertEquals("https://www.googleapis.com/auth/urlshortener", scopes.get(0));
  }

  public void testCommonParameters() {
    Map<String, DiscoveryType> commonParameters = discovery.getParameters();
    assertEquals(Sets.newHashSet("alt",
        "fields",
        "key",
        "oauth_token",
        "prettyPrint",
        "quotaUser",
        "userIp"), commonParameters.keySet());
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
