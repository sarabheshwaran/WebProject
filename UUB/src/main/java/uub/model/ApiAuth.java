package uub.model;

public class ApiAuth {

	
	String apiKey;
	int userId;
	int scope;
	long createdTime;
	int validity;
	
	
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
	
	
	
}
