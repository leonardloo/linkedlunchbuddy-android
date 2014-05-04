/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-04-15 19:10:39 UTC)
 * on 2014-05-04 at 05:01:32 UTC 
 * Modify at your own risk.
 */

package com.linkedlunchbuddy.requestendpoint;

/**
 * Service definition for Requestendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link RequestendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Requestendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the requestendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://linear-axle-547.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "requestendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Requestendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Requestendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getRequest".
   *
   * This request holds the parameters needed by the the requestendpoint server.  After setting any
   * optional parameters, call the {@link GetRequest#execute()} method to invoke the remote operation.
   *
   * @param id
   * @return the request
   */
  public GetRequest getRequest(java.lang.Long id) throws java.io.IOException {
    GetRequest result = new GetRequest(id);
    initialize(result);
    return result;
  }

  public class GetRequest extends RequestendpointRequest<com.linkedlunchbuddy.requestendpoint.model.Request> {

    private static final String REST_PATH = "request/{id}";

    /**
     * Create a request for the method "getRequest".
     *
     * This request holds the parameters needed by the the requestendpoint server.  After setting any
     * optional parameters, call the {@link GetRequest#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetRequest#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetRequest(java.lang.Long id) {
      super(Requestendpoint.this, "GET", REST_PATH, null, com.linkedlunchbuddy.requestendpoint.model.Request.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetRequest setAlt(java.lang.String alt) {
      return (GetRequest) super.setAlt(alt);
    }

    @Override
    public GetRequest setFields(java.lang.String fields) {
      return (GetRequest) super.setFields(fields);
    }

    @Override
    public GetRequest setKey(java.lang.String key) {
      return (GetRequest) super.setKey(key);
    }

    @Override
    public GetRequest setOauthToken(java.lang.String oauthToken) {
      return (GetRequest) super.setOauthToken(oauthToken);
    }

    @Override
    public GetRequest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetRequest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetRequest setQuotaUser(java.lang.String quotaUser) {
      return (GetRequest) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetRequest setUserIp(java.lang.String userIp) {
      return (GetRequest) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetRequest setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetRequest set(String parameterName, Object value) {
      return (GetRequest) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertRequest".
   *
   * This request holds the parameters needed by the the requestendpoint server.  After setting any
   * optional parameters, call the {@link InsertRequest#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.linkedlunchbuddy.requestendpoint.model.Request}
   * @return the request
   */
  public InsertRequest insertRequest(com.linkedlunchbuddy.requestendpoint.model.Request content) throws java.io.IOException {
    InsertRequest result = new InsertRequest(content);
    initialize(result);
    return result;
  }

  public class InsertRequest extends RequestendpointRequest<com.linkedlunchbuddy.requestendpoint.model.Request> {

    private static final String REST_PATH = "request";

    /**
     * Create a request for the method "insertRequest".
     *
     * This request holds the parameters needed by the the requestendpoint server.  After setting any
     * optional parameters, call the {@link InsertRequest#execute()} method to invoke the remote
     * operation. <p> {@link InsertRequest#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.linkedlunchbuddy.requestendpoint.model.Request}
     * @since 1.13
     */
    protected InsertRequest(com.linkedlunchbuddy.requestendpoint.model.Request content) {
      super(Requestendpoint.this, "POST", REST_PATH, content, com.linkedlunchbuddy.requestendpoint.model.Request.class);
    }

    @Override
    public InsertRequest setAlt(java.lang.String alt) {
      return (InsertRequest) super.setAlt(alt);
    }

    @Override
    public InsertRequest setFields(java.lang.String fields) {
      return (InsertRequest) super.setFields(fields);
    }

    @Override
    public InsertRequest setKey(java.lang.String key) {
      return (InsertRequest) super.setKey(key);
    }

    @Override
    public InsertRequest setOauthToken(java.lang.String oauthToken) {
      return (InsertRequest) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertRequest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertRequest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertRequest setQuotaUser(java.lang.String quotaUser) {
      return (InsertRequest) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertRequest setUserIp(java.lang.String userIp) {
      return (InsertRequest) super.setUserIp(userIp);
    }

    @Override
    public InsertRequest set(String parameterName, Object value) {
      return (InsertRequest) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listRequest".
   *
   * This request holds the parameters needed by the the requestendpoint server.  After setting any
   * optional parameters, call the {@link ListRequest#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListRequest listRequest() throws java.io.IOException {
    ListRequest result = new ListRequest();
    initialize(result);
    return result;
  }

  public class ListRequest extends RequestendpointRequest<com.linkedlunchbuddy.requestendpoint.model.CollectionResponseRequest> {

    private static final String REST_PATH = "request";

    /**
     * Create a request for the method "listRequest".
     *
     * This request holds the parameters needed by the the requestendpoint server.  After setting any
     * optional parameters, call the {@link ListRequest#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListRequest#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListRequest() {
      super(Requestendpoint.this, "GET", REST_PATH, null, com.linkedlunchbuddy.requestendpoint.model.CollectionResponseRequest.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListRequest setAlt(java.lang.String alt) {
      return (ListRequest) super.setAlt(alt);
    }

    @Override
    public ListRequest setFields(java.lang.String fields) {
      return (ListRequest) super.setFields(fields);
    }

    @Override
    public ListRequest setKey(java.lang.String key) {
      return (ListRequest) super.setKey(key);
    }

    @Override
    public ListRequest setOauthToken(java.lang.String oauthToken) {
      return (ListRequest) super.setOauthToken(oauthToken);
    }

    @Override
    public ListRequest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListRequest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListRequest setQuotaUser(java.lang.String quotaUser) {
      return (ListRequest) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListRequest setUserIp(java.lang.String userIp) {
      return (ListRequest) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListRequest setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListRequest setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListRequest set(String parameterName, Object value) {
      return (ListRequest) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeRequest".
   *
   * This request holds the parameters needed by the the requestendpoint server.  After setting any
   * optional parameters, call the {@link RemoveRequest#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveRequest removeRequest(java.lang.Long id) throws java.io.IOException {
    RemoveRequest result = new RemoveRequest(id);
    initialize(result);
    return result;
  }

  public class RemoveRequest extends RequestendpointRequest<Void> {

    private static final String REST_PATH = "request/{id}";

    /**
     * Create a request for the method "removeRequest".
     *
     * This request holds the parameters needed by the the requestendpoint server.  After setting any
     * optional parameters, call the {@link RemoveRequest#execute()} method to invoke the remote
     * operation. <p> {@link RemoveRequest#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveRequest(java.lang.Long id) {
      super(Requestendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveRequest setAlt(java.lang.String alt) {
      return (RemoveRequest) super.setAlt(alt);
    }

    @Override
    public RemoveRequest setFields(java.lang.String fields) {
      return (RemoveRequest) super.setFields(fields);
    }

    @Override
    public RemoveRequest setKey(java.lang.String key) {
      return (RemoveRequest) super.setKey(key);
    }

    @Override
    public RemoveRequest setOauthToken(java.lang.String oauthToken) {
      return (RemoveRequest) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveRequest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveRequest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveRequest setQuotaUser(java.lang.String quotaUser) {
      return (RemoveRequest) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveRequest setUserIp(java.lang.String userIp) {
      return (RemoveRequest) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveRequest setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveRequest set(String parameterName, Object value) {
      return (RemoveRequest) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateRequest".
   *
   * This request holds the parameters needed by the the requestendpoint server.  After setting any
   * optional parameters, call the {@link UpdateRequest#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.linkedlunchbuddy.requestendpoint.model.Request}
   * @return the request
   */
  public UpdateRequest updateRequest(com.linkedlunchbuddy.requestendpoint.model.Request content) throws java.io.IOException {
    UpdateRequest result = new UpdateRequest(content);
    initialize(result);
    return result;
  }

  public class UpdateRequest extends RequestendpointRequest<com.linkedlunchbuddy.requestendpoint.model.Request> {

    private static final String REST_PATH = "request";

    /**
     * Create a request for the method "updateRequest".
     *
     * This request holds the parameters needed by the the requestendpoint server.  After setting any
     * optional parameters, call the {@link UpdateRequest#execute()} method to invoke the remote
     * operation. <p> {@link UpdateRequest#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.linkedlunchbuddy.requestendpoint.model.Request}
     * @since 1.13
     */
    protected UpdateRequest(com.linkedlunchbuddy.requestendpoint.model.Request content) {
      super(Requestendpoint.this, "PUT", REST_PATH, content, com.linkedlunchbuddy.requestendpoint.model.Request.class);
    }

    @Override
    public UpdateRequest setAlt(java.lang.String alt) {
      return (UpdateRequest) super.setAlt(alt);
    }

    @Override
    public UpdateRequest setFields(java.lang.String fields) {
      return (UpdateRequest) super.setFields(fields);
    }

    @Override
    public UpdateRequest setKey(java.lang.String key) {
      return (UpdateRequest) super.setKey(key);
    }

    @Override
    public UpdateRequest setOauthToken(java.lang.String oauthToken) {
      return (UpdateRequest) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateRequest setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateRequest) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateRequest setQuotaUser(java.lang.String quotaUser) {
      return (UpdateRequest) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateRequest setUserIp(java.lang.String userIp) {
      return (UpdateRequest) super.setUserIp(userIp);
    }

    @Override
    public UpdateRequest set(String parameterName, Object value) {
      return (UpdateRequest) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Requestendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Requestendpoint}. */
    @Override
    public Requestendpoint build() {
      return new Requestendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link RequestendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setRequestendpointRequestInitializer(
        RequestendpointRequestInitializer requestendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(requestendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
