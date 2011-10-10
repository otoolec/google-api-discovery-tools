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

import com.google.api.client.discovery.types.DiscoveryType;
import com.google.api.services.discovery.model.Jsonschema;
import com.google.api.services.discovery.model.Restmethod;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class which describes a method available for this particular API.
 *
 * @author moshenko@google.com (Jacob Moshenko)
 */
public class RestMethod {
  /**
   * Enum which defines the possible locations for a parameter.
   */
  public enum ParameterLocation {
    /**
     * Parameter should appear in the request path.
     */
    PATH("path"),

    /**
     * Parameter should appear as a named query parameter.
     */
    QUERY("query");

    private static final Map<String, ParameterLocation> jsonValueToEnum = Maps.newHashMap();

    static {
      for (ParameterLocation type : values()) {
        jsonValueToEnum.put(type.getJsonFormat(), type);
      }
    }

    /**
     * Returns the enumerated type based on the JSON type string.
     */
    public static ParameterLocation getEnumForJsonValue(String jsonValue) {
      return jsonValueToEnum.get(jsonValue);
    }

    private String jsonValue;

    private ParameterLocation(String jsonValue) {
      this.jsonValue = jsonValue;
    }

    private String getJsonFormat() {
      return jsonValue;
    }
  }
  /**
   * Class which describes a single named parameter to a {@link RestMethod}.
   */
  public class Parameter {
    private final String name;
    private final Jsonschema schemaNode;

    /**
     * Create an instance in the context of the calling method.
     *
     * @param name Name of this parameter.
     * @param schemaNode Schema node for additional information for this parameter.
     */
    Parameter(String name, Jsonschema schemaNode) {
      this.name = Preconditions.checkNotNull(name);
      this.schemaNode = Preconditions.checkNotNull(schemaNode);
    }

    /**
     * Returns the name of this parameter.
     */
    public String getName() {
      return name;
    }

    /**
     * Returns the type description of this parameter.
     */
    public DiscoveryType getType() {
      return DiscoveryType.createTypeFromSchemaNode(schemaNode, topLevelSchemas);
    }

    /**
     * Returns whether this parameter may appear multiple times.
     */
    public Boolean isRepeated() {
      return schemaNode.getRepeated();
    }

    /**
     * Returns whether this parameter goes in the query or the path for REST requests.
     */
    public ParameterLocation getLocation() {
      return ParameterLocation.getEnumForJsonValue(schemaNode.getLocation());
    }
  }

  private final Map<String, Jsonschema> topLevelSchemas;
  private final Restmethod methodNode;

  /**
   * Create an instance.
   *
   * @param topLevelSchemas Map of the named top level schemas.
   * @param methodNode {@link Restmethod} node which this method
   *        wraps.
   */
  RestMethod(Map<String, Jsonschema> topLevelSchemas, Restmethod methodNode) {
    this.topLevelSchemas = Preconditions.checkNotNull(topLevelSchemas);
    this.methodNode = Preconditions.checkNotNull(methodNode);
  }

  /**
   * Returns the description string for this method.
   */
  public String getDescription() {
    return methodNode.getDescription();
  }

  /** HTTP method used by this method. */
  public String getHttpMethod() {
    return methodNode.getHttpMethod();
  }

  /**
   * Returns the ID for this method.
   */
  public String getId() {
    return methodNode.getId();
  }

  /**
   * Returns type descriptions for each of the required parameters, in the
   * suggested order.
   */
  public List<Parameter> getRequiredParameters() {
    return createParameterList(methodNode.getParameterOrder());
  }

  /**
   * Returns type descriptions for each of the parameters that are named but not
   * listed as required. There is no guaranteed ordering of these parameters.
   */
  public Collection<Parameter> getOptionalParameters() {
    Set<String> required = Sets.newHashSet(methodNode.getParameterOrder());
    Set<String> allParameters = methodNode.getParameters().keySet();
    Set<String> optional = Sets.difference(allParameters, required);

    return createParameterList(Lists.newArrayList(optional));
  }

  /**
   * The URI path of this REST method. Should be used in conjunction with the
   * basePath property at the api-level.
   */
  public String getPath() {
    return methodNode.getPath();
  }

  /**
   * Returns the description of what type this method accepts.
   */
  public DiscoveryType getRequest() {
    // TODO(moshenko) remove this when response types are Jsonschema
    Jsonschema requestType = topLevelSchemas.get(methodNode.getRequest().get$ref());
    return DiscoveryType.createTypeFromSchemaNode(requestType, topLevelSchemas);
  }

  /**
   * Returns the description of what type this method returns.
   */
  public DiscoveryType getResponse() {
    // TODO(moshenko) remove this when response types are Jsonschema
    Jsonschema responseType = topLevelSchemas.get(methodNode.getResponse().get$ref());
    return DiscoveryType.createTypeFromSchemaNode(responseType, topLevelSchemas);
  }

  /**
   * Returns a list of the OAuth 2.0 scope names applicable to this method.
   */
  public List<String> getScopes() {
    return methodNode.getScopes();
  }

  private List<Parameter> createParameterList(List<String> paramNamesInOrder) {
    return Lists.transform(paramNamesInOrder, new Function<String, Parameter>() {
      public Parameter apply(String name) {
        return new Parameter(name, methodNode.getParameters().get(name));
      }
    });
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(methodNode, topLevelSchemas);
  }

  @Override
  public boolean equals(Object rhs) {
    if (rhs instanceof RestMethod) {
      RestMethod rhsTyped = (RestMethod) rhs;
      return Objects.equal(methodNode, rhsTyped.methodNode)
          && Objects.equal(topLevelSchemas, rhsTyped.topLevelSchemas);
    } else {
      return false;
    }
  }
}
