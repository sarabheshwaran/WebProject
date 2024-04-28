package uub.model;

import uub.staticlayer.DateUtils;

public class ApiAuth {

	
	private String apiKey;
	private int userId;
	private int scope;
	private long createdTime;
	private int validity;
	
	
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getScope() {
		return scope;
	}
	public void setScope(int scope) {
		this.scope = scope;
	}
	public long getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(long createdAt) {
		this.createdTime = createdAt;
	}
	public int getValidity() {
		return validity;
	}
	public void setValidity(int validity) {
		this.validity = validity;
	}
	public boolean isValid() {
		
		return (createdTime + (validity * (24l*60*60*1000))) >= DateUtils.getTime();

	}

	
}
