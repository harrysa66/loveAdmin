package com.love.restful.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.love.blog.po.Days;

@XmlRootElement(name="daysList")
public class DaysList {
	
	private List<Days> daysList;

	public List<Days> getDaysList() {
		return daysList;
	}

	public void setDaysList(List<Days> daysList) {
		this.daysList = daysList;
	}

}
