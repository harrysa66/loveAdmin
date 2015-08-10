package com.love.blog.po;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("indexPage")
@XmlRootElement(name="indexPage")
@Component
public class IndexPage implements Serializable{

	private static final long serialVersionUID = 5156985078953232292L;
	
	private String id;
	private String title;
	private String subtitle;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

}
