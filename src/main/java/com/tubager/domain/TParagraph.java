package com.tubager.domain;

import java.util.Date;

public class TParagraph {
	private String uuid;
	private String title;
	private int index;
	private String bookUuid;
	private String text;
	private String img;
	private String imgText;
	private String video;
	private String audio;
	private String locationUuid;
	private String timeStamp;
	private String userName;
	private TLocation location;
	private Date dateCreated;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgText() {
		return imgText;
	}
	public void setImgText(String imgText) {
		this.imgText = imgText;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getBookUuid() {
		return bookUuid;
	}
	public void setBookUuid(String bookUuid) {
		this.bookUuid = bookUuid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public String getLocationUuid() {
		return locationUuid;
	}
	public void setLocationUuid(String locationUuid) {
		this.locationUuid = locationUuid;
	}
	public TLocation getLocation() {
		return location;
	}
	public void setLocation(TLocation location) {
		this.location = location;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
}
