package uub.model;

import uub.enums.AuditAction;
import uub.enums.AuditResult;

public class Audit {

	private long id ;
	private int userId;
	private long time;
	private AuditAction action;
	private int targetId;
	private AuditResult result;
	private String desc;
	

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public long getId() {
		return id;
	}
	public void setId(long auditId) {
		this.id = auditId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public AuditAction getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = AuditAction.setAction(action);
	}
	public void setAction(AuditAction action) {
		this.action = action;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	public AuditResult getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = AuditResult.valueOf(result);
	}
	public void setResult(AuditResult result) {
		this.result = result;
	}
}
