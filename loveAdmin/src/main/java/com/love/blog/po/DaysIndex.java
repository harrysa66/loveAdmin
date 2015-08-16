package com.love.blog.po;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("daysIndex")
@XmlRootElement(name="daysIndex")
@Component
public class DaysIndex implements Serializable{

	private static final long serialVersionUID = -8687358147528811187L;
	
	private String id;
	private String boyId;
	private String boyUrl;
	private String girlId;
	private String girlUrl;
	private String boyTitle;
	private String girlTitle;
	private String contentOne;
	private String contentTwo;
	private String contentThree;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBoyId() {
		return boyId;
	}
	public void setBoyId(String boyId) {
		this.boyId = boyId;
	}
	public String getBoyUrl() {
		return boyUrl;
	}
	public void setBoyUrl(String boyUrl) {
		this.boyUrl = boyUrl;
	}
	public String getGirlId() {
		return girlId;
	}
	public void setGirlId(String girlId) {
		this.girlId = girlId;
	}
	public String getGirlUrl() {
		return girlUrl;
	}
	public void setGirlUrl(String girlUrl) {
		this.girlUrl = girlUrl;
	}
	public String getBoyTitle() {
		return boyTitle;
	}
	public void setBoyTitle(String boyTitle) {
		this.boyTitle = boyTitle;
	}
	public String getGirlTitle() {
		return girlTitle;
	}
	public void setGirlTitle(String girlTitle) {
		this.girlTitle = girlTitle;
	}
	public String getContentOne() {
		return contentOne;
	}
	public void setContentOne(String contentOne) {
		this.contentOne = contentOne;
	}
	public String getContentTwo() {
		return contentTwo;
	}
	public void setContentTwo(String contentTwo) {
		this.contentTwo = contentTwo;
	}
	public String getContentThree() {
		return contentThree;
	}
	public void setContentThree(String contentThree) {
		this.contentThree = contentThree;
	}

}
