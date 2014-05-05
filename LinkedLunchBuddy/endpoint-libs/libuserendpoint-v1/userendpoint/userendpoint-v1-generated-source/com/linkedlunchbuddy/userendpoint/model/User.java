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
 * on 2014-05-05 at 20:35:02 UTC 
 * Modify at your own risk.
 */

package com.linkedlunchbuddy.userendpoint.model;

/**
 * Model definition for User.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class User extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String deviceRegistrationId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String eduEmail;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String encodedKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String fbId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String gender;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String privateEmail;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long requestId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDeviceRegistrationId() {
    return deviceRegistrationId;
  }

  /**
   * @param deviceRegistrationId deviceRegistrationId or {@code null} for none
   */
  public User setDeviceRegistrationId(java.lang.String deviceRegistrationId) {
    this.deviceRegistrationId = deviceRegistrationId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEduEmail() {
    return eduEmail;
  }

  /**
   * @param eduEmail eduEmail or {@code null} for none
   */
  public User setEduEmail(java.lang.String eduEmail) {
    this.eduEmail = eduEmail;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getEncodedKey() {
    return encodedKey;
  }

  /**
   * @param encodedKey encodedKey or {@code null} for none
   */
  public User setEncodedKey(java.lang.String encodedKey) {
    this.encodedKey = encodedKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFbId() {
    return fbId;
  }

  /**
   * @param fbId fbId or {@code null} for none
   */
  public User setFbId(java.lang.String fbId) {
    this.fbId = fbId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getGender() {
    return gender;
  }

  /**
   * @param gender gender or {@code null} for none
   */
  public User setGender(java.lang.String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public User setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPrivateEmail() {
    return privateEmail;
  }

  /**
   * @param privateEmail privateEmail or {@code null} for none
   */
  public User setPrivateEmail(java.lang.String privateEmail) {
    this.privateEmail = privateEmail;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getRequestId() {
    return requestId;
  }

  /**
   * @param requestId requestId or {@code null} for none
   */
  public User setRequestId(java.lang.Long requestId) {
    this.requestId = requestId;
    return this;
  }

  @Override
  public User set(String fieldName, Object value) {
    return (User) super.set(fieldName, value);
  }

  @Override
  public User clone() {
    return (User) super.clone();
  }

}
