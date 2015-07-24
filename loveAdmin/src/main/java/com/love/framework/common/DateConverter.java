package com.love.framework.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {
	public Date convert(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		if (StringUtils.isNotEmpty(source))
			try {
				return dateFormat.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		return null;
	}
}
