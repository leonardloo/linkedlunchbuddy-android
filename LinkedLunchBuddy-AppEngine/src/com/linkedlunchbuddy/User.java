package com.linkedlunchbuddy;

import javax.jdo.annotations.Extension;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.google.appengine.api.datastore.Key;

@Entity
public class User {

	// eduEmail is String as encoded Key for the User
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String eduEmail;
	
	public String getEncodedKey() {
		return eduEmail;
	}

	private String fbId;
	private String privateEmail;
	private String deviceRegistrationId;
	private String name;
	private String gender;
	private Long requestId;

	// Dummy constructor for JPA
	public User() {
	}

	public User(String eduEmail, String name, String fbId, String privateEmail,
			String gender) {
		if (eduEmail == null) {
			throw new IllegalArgumentException();
		}
		this.eduEmail = eduEmail;
		this.name = name;
		this.fbId = fbId;
		this.privateEmail = privateEmail;
		this.gender = gender;
	}


	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public String getDeviceRegistrationId() {
		return deviceRegistrationId;
	}

	public void setDeviceRegistrationId(String deviceRegistrationId) {
		this.deviceRegistrationId = deviceRegistrationId;
	}

	public String getFbId() {
		return fbId;
	}

	public void setFbId(String fbId) {
		this.fbId = fbId;
	}

	public String getPrivateEmail() {
		return privateEmail;
	}

	public void setPrivateEmail(String privateEmail) {
		this.privateEmail = privateEmail;
	}

	public String getEduEmail() {
		return eduEmail;
	}

	public void setEduEmail(String eduEmail) {
		this.eduEmail = eduEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}