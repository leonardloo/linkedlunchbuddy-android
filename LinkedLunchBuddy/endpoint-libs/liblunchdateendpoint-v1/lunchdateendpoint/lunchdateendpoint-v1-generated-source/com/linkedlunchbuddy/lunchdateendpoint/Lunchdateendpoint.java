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
 * on 2014-04-24 at 15:02:07 UTC 
 * Modify at your own risk.
 */

package com.linkedlunchbuddy.lunchdateendpoint;

/**
 * Service definition for Lunchdateendpoint (v1).
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
 * This service uses {@link LunchdateendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Lunchdateendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.16.0-rc of the lunchdateendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
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
  public static final String DEFAULT_SERVICE_PATH = "lunchdateendpoint/v1/";

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
  public Lunchdateendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Lunchdateendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getLunchDate".
   *
   * This request holds the parameters needed by the the lunchdateendpoint server.  After setting any
   * optional parameters, call the {@link GetLunchDate#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetLunchDate getLunchDate(java.lang.Long id) throws java.io.IOException {
    GetLunchDate result = new GetLunchDate(id);
    initialize(result);
    return result;
  }

  public class GetLunchDate extends LunchdateendpointRequest<com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate> {

    private static final String REST_PATH = "lunchdate/{id}";

    /**
     * Create a request for the method "getLunchDate".
     *
     * This request holds the parameters needed by the the lunchdateendpoint server.  After setting
     * any optional parameters, call the {@link GetLunchDate#execute()} method to invoke the remote
     * operation. <p> {@link
     * GetLunchDate#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetLunchDate(java.lang.Long id) {
      super(Lunchdateendpoint.this, "GET", REST_PATH, null, com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate.class);
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
    public GetLunchDate setAlt(java.lang.String alt) {
      return (GetLunchDate) super.setAlt(alt);
    }

    @Override
    public GetLunchDate setFields(java.lang.String fields) {
      return (GetLunchDate) super.setFields(fields);
    }

    @Override
    public GetLunchDate setKey(java.lang.String key) {
      return (GetLunchDate) super.setKey(key);
    }

    @Override
    public GetLunchDate setOauthToken(java.lang.String oauthToken) {
      return (GetLunchDate) super.setOauthToken(oauthToken);
    }

    @Override
    public GetLunchDate setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetLunchDate) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetLunchDate setQuotaUser(java.lang.String quotaUser) {
      return (GetLunchDate) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetLunchDate setUserIp(java.lang.String userIp) {
      return (GetLunchDate) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetLunchDate setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetLunchDate set(String parameterName, Object value) {
      return (GetLunchDate) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertLunchDate".
   *
   * This request holds the parameters needed by the the lunchdateendpoint server.  After setting any
   * optional parameters, call the {@link InsertLunchDate#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate}
   * @return the request
   */
  public InsertLunchDate insertLunchDate(com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate content) throws java.io.IOException {
    InsertLunchDate result = new InsertLunchDate(content);
    initialize(result);
    return result;
  }

  public class InsertLunchDate extends LunchdateendpointRequest<com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate> {

    private static final String REST_PATH = "lunchdate";

    /**
     * Create a request for the method "insertLunchDate".
     *
     * This request holds the parameters needed by the the lunchdateendpoint server.  After setting
     * any optional parameters, call the {@link InsertLunchDate#execute()} method to invoke the remote
     * operation. <p> {@link InsertLunchDate#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate}
     * @since 1.13
     */
    protected InsertLunchDate(com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate content) {
      super(Lunchdateendpoint.this, "POST", REST_PATH, content, com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate.class);
    }

    @Override
    public InsertLunchDate setAlt(java.lang.String alt) {
      return (InsertLunchDate) super.setAlt(alt);
    }

    @Override
    public InsertLunchDate setFields(java.lang.String fields) {
      return (InsertLunchDate) super.setFields(fields);
    }

    @Override
    public InsertLunchDate setKey(java.lang.String key) {
      return (InsertLunchDate) super.setKey(key);
    }

    @Override
    public InsertLunchDate setOauthToken(java.lang.String oauthToken) {
      return (InsertLunchDate) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertLunchDate setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertLunchDate) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertLunchDate setQuotaUser(java.lang.String quotaUser) {
      return (InsertLunchDate) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertLunchDate setUserIp(java.lang.String userIp) {
      return (InsertLunchDate) super.setUserIp(userIp);
    }

    @Override
    public InsertLunchDate set(String parameterName, Object value) {
      return (InsertLunchDate) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listLunchDate".
   *
   * This request holds the parameters needed by the the lunchdateendpoint server.  After setting any
   * optional parameters, call the {@link ListLunchDate#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListLunchDate listLunchDate() throws java.io.IOException {
    ListLunchDate result = new ListLunchDate();
    initialize(result);
    return result;
  }

  public class ListLunchDate extends LunchdateendpointRequest<com.linkedlunchbuddy.lunchdateendpoint.model.CollectionResponseLunchDate> {

    private static final String REST_PATH = "lunchdate";

    /**
     * Create a request for the method "listLunchDate".
     *
     * This request holds the parameters needed by the the lunchdateendpoint server.  After setting
     * any optional parameters, call the {@link ListLunchDate#execute()} method to invoke the remote
     * operation. <p> {@link ListLunchDate#initialize(com.google.api.client.googleapis.services.Abstra
     * ctGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ListLunchDate() {
      super(Lunchdateendpoint.this, "GET", REST_PATH, null, com.linkedlunchbuddy.lunchdateendpoint.model.CollectionResponseLunchDate.class);
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
    public ListLunchDate setAlt(java.lang.String alt) {
      return (ListLunchDate) super.setAlt(alt);
    }

    @Override
    public ListLunchDate setFields(java.lang.String fields) {
      return (ListLunchDate) super.setFields(fields);
    }

    @Override
    public ListLunchDate setKey(java.lang.String key) {
      return (ListLunchDate) super.setKey(key);
    }

    @Override
    public ListLunchDate setOauthToken(java.lang.String oauthToken) {
      return (ListLunchDate) super.setOauthToken(oauthToken);
    }

    @Override
    public ListLunchDate setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListLunchDate) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListLunchDate setQuotaUser(java.lang.String quotaUser) {
      return (ListLunchDate) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListLunchDate setUserIp(java.lang.String userIp) {
      return (ListLunchDate) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListLunchDate setCursor(java.lang.String cursor) {
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

    public ListLunchDate setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListLunchDate set(String parameterName, Object value) {
      return (ListLunchDate) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeLunchDate".
   *
   * This request holds the parameters needed by the the lunchdateendpoint server.  After setting any
   * optional parameters, call the {@link RemoveLunchDate#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveLunchDate removeLunchDate(java.lang.Long id) throws java.io.IOException {
    RemoveLunchDate result = new RemoveLunchDate(id);
    initialize(result);
    return result;
  }

  public class RemoveLunchDate extends LunchdateendpointRequest<Void> {

    private static final String REST_PATH = "lunchdate/{id}";

    /**
     * Create a request for the method "removeLunchDate".
     *
     * This request holds the parameters needed by the the lunchdateendpoint server.  After setting
     * any optional parameters, call the {@link RemoveLunchDate#execute()} method to invoke the remote
     * operation. <p> {@link RemoveLunchDate#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveLunchDate(java.lang.Long id) {
      super(Lunchdateendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveLunchDate setAlt(java.lang.String alt) {
      return (RemoveLunchDate) super.setAlt(alt);
    }

    @Override
    public RemoveLunchDate setFields(java.lang.String fields) {
      return (RemoveLunchDate) super.setFields(fields);
    }

    @Override
    public RemoveLunchDate setKey(java.lang.String key) {
      return (RemoveLunchDate) super.setKey(key);
    }

    @Override
    public RemoveLunchDate setOauthToken(java.lang.String oauthToken) {
      return (RemoveLunchDate) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveLunchDate setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveLunchDate) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveLunchDate setQuotaUser(java.lang.String quotaUser) {
      return (RemoveLunchDate) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveLunchDate setUserIp(java.lang.String userIp) {
      return (RemoveLunchDate) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveLunchDate setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveLunchDate set(String parameterName, Object value) {
      return (RemoveLunchDate) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateLunchDate".
   *
   * This request holds the parameters needed by the the lunchdateendpoint server.  After setting any
   * optional parameters, call the {@link UpdateLunchDate#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate}
   * @return the request
   */
  public UpdateLunchDate updateLunchDate(com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate content) throws java.io.IOException {
    UpdateLunchDate result = new UpdateLunchDate(content);
    initialize(result);
    return result;
  }

  public class UpdateLunchDate extends LunchdateendpointRequest<com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate> {

    private static final String REST_PATH = "lunchdate";

    /**
     * Create a request for the method "updateLunchDate".
     *
     * This request holds the parameters needed by the the lunchdateendpoint server.  After setting
     * any optional parameters, call the {@link UpdateLunchDate#execute()} method to invoke the remote
     * operation. <p> {@link UpdateLunchDate#initialize(com.google.api.client.googleapis.services.Abst
     * ractGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param content the {@link com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate}
     * @since 1.13
     */
    protected UpdateLunchDate(com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate content) {
      super(Lunchdateendpoint.this, "PUT", REST_PATH, content, com.linkedlunchbuddy.lunchdateendpoint.model.LunchDate.class);
    }

    @Override
    public UpdateLunchDate setAlt(java.lang.String alt) {
      return (UpdateLunchDate) super.setAlt(alt);
    }

    @Override
    public UpdateLunchDate setFields(java.lang.String fields) {
      return (UpdateLunchDate) super.setFields(fields);
    }

    @Override
    public UpdateLunchDate setKey(java.lang.String key) {
      return (UpdateLunchDate) super.setKey(key);
    }

    @Override
    public UpdateLunchDate setOauthToken(java.lang.String oauthToken) {
      return (UpdateLunchDate) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateLunchDate setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateLunchDate) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateLunchDate setQuotaUser(java.lang.String quotaUser) {
      return (UpdateLunchDate) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateLunchDate setUserIp(java.lang.String userIp) {
      return (UpdateLunchDate) super.setUserIp(userIp);
    }

    @Override
    public UpdateLunchDate set(String parameterName, Object value) {
      return (UpdateLunchDate) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Lunchdateendpoint}.
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

    /** Builds a new instance of {@link Lunchdateendpoint}. */
    @Override
    public Lunchdateendpoint build() {
      return new Lunchdateendpoint(this);
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
     * Set the {@link LunchdateendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setLunchdateendpointRequestInitializer(
        LunchdateendpointRequestInitializer lunchdateendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(lunchdateendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
