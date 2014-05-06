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
 * on 2014-05-06 at 03:12:03 UTC 
 * Modify at your own risk.
 */

package com.linkedlunchbuddy.lunchdateendpoint.model;

/**
 * Model definition for TimeInterval.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the lunchdateendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class TimeInterval extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long endTime;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long length;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long startTime;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getEndTime() {
    return endTime;
  }

  /**
   * @param endTime endTime or {@code null} for none
   */
  public TimeInterval setEndTime(java.lang.Long endTime) {
    this.endTime = endTime;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getLength() {
    return length;
  }

  /**
   * @param length length or {@code null} for none
   */
  public TimeInterval setLength(java.lang.Long length) {
    this.length = length;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getStartTime() {
    return startTime;
  }

  /**
   * @param startTime startTime or {@code null} for none
   */
  public TimeInterval setStartTime(java.lang.Long startTime) {
    this.startTime = startTime;
    return this;
  }

  @Override
  public TimeInterval set(String fieldName, Object value) {
    return (TimeInterval) super.set(fieldName, value);
  }

  @Override
  public TimeInterval clone() {
    return (TimeInterval) super.clone();
  }

}
