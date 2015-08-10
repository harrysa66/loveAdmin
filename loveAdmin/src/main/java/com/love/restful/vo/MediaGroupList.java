package com.love.restful.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.love.blog.po.MediaGroup;

@XmlRootElement(name="mediaGroupList")
public class MediaGroupList {
	
	private List<MediaGroup> mediaGroupList;

	public List<MediaGroup> getMediaGroupList() {
		return mediaGroupList;
	}

	public void setMediaGroupList(List<MediaGroup> mediaGroupList) {
		this.mediaGroupList = mediaGroupList;
	}

}
