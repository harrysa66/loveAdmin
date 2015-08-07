package com.love.restful.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.love.blog.po.MediaType;

@XmlRootElement(name="mediaTypeList")
public class MediaTypeList {
	
	private List<MediaType> mediaTypeList;

	public List<MediaType> getMediaTypeList() {
		return mediaTypeList;
	}

	public void setMediaTypeList(List<MediaType> mediaTypeList) {
		this.mediaTypeList = mediaTypeList;
	}

}
