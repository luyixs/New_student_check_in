package com.example.entity;

import javax.persistence.*;

@Table(name = "submit_info")
public class SubmitInfo  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "time")
	private String time;
	@Column(name = "userName")
	private String userName;
	@Column(name = "level")
	private Integer level;
	@Column(name = "status")
	private String status;
	@Column(name = "reason")
	private String reason;
	@Column(name = "verifyName")
	private String verifyName;
	@Column(name = "fileName")
	private String fileName;
	@Column(name = "fileId")
	private Long fileId;
	@Column(name = "uploadUserId")
	private Long uploadUserId;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getVerifyName() {
		return verifyName;
	}
	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Long getUploadUserId() {
		return uploadUserId;
	}
	public void setUploadUserId(Long uploadUserId) {
		this.uploadUserId = uploadUserId;
	}


    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }

}
