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

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Class which we can deserialize our Discovery response. This is for use in
 * deserializing Discovery documents, and should not be used by end users.
 *
 * @see com.google.api.client.discovery.Discovery
 * @author moshenko@google.com (Jacob Moshenko)
 */
public class DiscoveryDocument extends AbstractDiscoveryItem {

  public static final class Schema {
    @SerializedName("$ref")
    private final String ref;

    private final Schema additionalProperties;

    @SerializedName("default")
    private final String defaultValue;

    private final String description;

    @SerializedName("enum")
    private final List<String> enumValues;

    private final List<String> enumDescriptions;
    private final String format;
    private final String id;
    private final Schema items;
    private final String maximum;
    private final String minimum;
    private final String pattern;
    private final Map<String, Schema> properties;
    private final Boolean required;
    private final String type;

    /**
     * This constructor is for use by Gson only.
     */
    public Schema() {
      this(null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null,
          null);
    }

    Schema(String ref,
        Schema additionalProperties,
        String defaultValue,
        String description,
        List<String> enumValues,
        List<String> enumDescriptions,
        String format,
        String id,
        Schema items,
        String maximum,
        String minimum,
        String pattern,
        Map<String, Schema> properties,
        Boolean required,
        String type) {
      this.ref = ref;
      this.additionalProperties = additionalProperties;
      this.defaultValue = defaultValue;
      this.description = description;
      this.enumValues = enumValues;
      this.enumDescriptions = enumDescriptions;
      this.format = format;
      this.id = id;
      this.items = items;
      this.maximum = maximum;
      this.minimum = minimum;
      this.pattern = pattern;
      this.properties = properties;
      this.required = required;
      this.type = type;
    }

    /**
     * Returns the schema name of the referenced schema.
     */
    public String getRef() {
      return ref;
    }

    /**
     * Returns the type of any additional properties on the object.
     */
    public Schema getAdditionalProperties() {
      return additionalProperties;
    }

    /**
     * Returns the default value.
     */
    public String getDefault() {
      return defaultValue;
    }

    /**
     * Returns the description.
     */
    public String getDescription() {
      return description;
    }

    /**
     * Returns the possible values if this schema defines an enumeration.
     */
    public List<String> getEnumValues() {
      return enumValues;
    }

    /**
     * Returns a parallel array of enum descriptions that match the enum values.
     */
    public List<String> getEnumDescriptions() {
      return enumDescriptions;
    }

    /**
     * Returns the format of the schema if it is a string type.
     */
    public String getFormat() {
      return format;
    }

    /**
     * Returns the id.
     */
    public String getId() {
      return id;
    }

    /**
     * Returns the items.
     */
    public Schema getItems() {
      return items;
    }

    /**
     * Returns the maximum.
     */
    public String getMaximum() {
      return maximum;
    }

    /**
     * Returns the minimum.
     */
    public String getMinimum() {
      return minimum;
    }

    /**
     * Returns the pattern which this must adhere to.
     */
    public String getPattern() {
      return pattern;
    }

    /**
     * Returns the properties.
     */
    public Map<String, Schema> getProperties() {
      return properties == null ? null : Collections.unmodifiableMap(properties);
    }

    /**
     * Returns whether or not a parameter is required.
     */
    public Boolean getRequired() {
      return required;
    }

    /**
     * Returns the type.
     */
    public String getType() {
      return type;
    }
  }

  public static final class Method {
    private final String id;
    private final String description;
    private final Schema returns;

    private final Map<String, Schema> parameters;
    private final List<String> parameterOrder;
    private final List<String> scopes;

    /**
     * This constructor is for use by Gson only.
     */
    public Method() {
      this(null, null, null, null, null, null);
    }

    Method(String id,
        String description,
        Schema returns,
        Map<String, Schema> parameters,
        List<String> parameterOrder,
        List<String> scopes) {
      this.id = id;
      this.description = description;
      this.returns = returns;
      this.parameters = parameters;
      this.parameterOrder = parameterOrder;
      this.scopes = scopes;
    }

    /**
     * Returns the httpMethod
     */
    public String getId() {
      return id;
    }

    /**
     * Returns the description
     */
    public String getDescription() {
      return description;
    }

    /**
     * Returns the response object
     */
    public Schema getReturns() {
      return returns;
    }

    /**
     * Returns the parameters
     */
    public Map<String, Schema> getParameters() {
      return parameters == null ? null : Collections.unmodifiableMap(parameters);
    }

    /**
     * Returns the parameterOrder
     */
    public List<String> getParameterOrder() {
      return parameterOrder == null ? null : Collections.unmodifiableList(parameterOrder);
    }

    /**
     * Returns the scopes
     */
    public List<String> getScopes() {
      return scopes == null ? null : Collections.unmodifiableList(scopes);
    }
  }

  public static final class OAuth2Scope {
    private final String description;

    public OAuth2Scope() {
      this(null);
    }

    OAuth2Scope(String description) {
      this.description = description;
    }

    /**
     * Returns the description of the scope, possible including what permissions
     * are being requested.
     */
    public String getDescription() {
      return description;
    }
  }

  public static final class OAuth2 {
    private final Map<String, OAuth2Scope> scopes;

    public OAuth2() {
      this(null);
    }

    OAuth2(Map<String, OAuth2Scope> scopes) {
      this.scopes = scopes;
    }

    /**
     * Returns the OAuth2 scopes available
     */
    public Map<String, OAuth2Scope> getScopes() {
      return scopes == null ? null : Collections.unmodifiableMap(scopes);
    }
  }

  public static final class Auth {
    private final OAuth2 oauth2;

    public Auth() {
      this(null);
    }

    Auth(OAuth2 oauth2) {
      this.oauth2 = oauth2;
    }

    /**
     * Returns the oauth block
     */
    public OAuth2 getOAuth2() {
      return oauth2;
    }
  }

  private final Auth auth;
  private final List<String> features;
  private final Map<String, Method> methods;
  private final Map<String, Schema> parameters;
  private final String rpcPath;
  private final Map<String, Schema> schemas;

  /**
   * This constructor is for use by Gson only.
   */
  public DiscoveryDocument() {
    this(null, null, null, null, null, null, null, null, null, null, null, null, null, null);
  }

  DiscoveryDocument(String name,
      String version,
      String id,
      String title,
      String description,
      String documentationLink,
      String rpcPath,
      Auth auth,
      Map<String, Schema> schemas,
      Map<String, Method> methods,
      Map<String, Schema> parameters,
      Map<String, String> icons,
      List<String> features,
      List<String> labels) {
    super(id, labels, name, version, description, documentationLink, icons, title);
    this.schemas = schemas;
    this.methods = methods;
    this.auth = auth;
    this.parameters = parameters;
    this.rpcPath = rpcPath;
    this.features = features;
  }

  /**
   * Returns the path to which requests should be made.
   */
  public String getRpcPath() {
    return rpcPath;
  }

  /**
   * Returns the auth block
   */
  public Auth getAuth() {
    return auth;
  }

  /**
   * Returns the schemas
   */
  public Map<String, Schema> getSchemas() {
    return schemas == null ? null : Collections.unmodifiableMap(schemas);
  }

  /**
   * Returns the methods
   */
  public Map<String, Method> getMethods() {
    return methods == null ? null : Collections.unmodifiableMap(methods);
  }

  /**
   * Returns the common parameters for this API.
   */
  public Map<String, Schema> getParameters() {
    return parameters == null ? null : Collections.unmodifiableMap(parameters);
  }

  /**
   * Returns a list of supported features of this API.
   */
  public List<String> getFeatures() {
    return features == null ? null : Collections.unmodifiableList(features);
  }
}
