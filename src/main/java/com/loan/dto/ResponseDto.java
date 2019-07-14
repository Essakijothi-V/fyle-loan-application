package com.loan.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author essakijothi_v
 *
 */
public class ResponseDto {

	private final String DATE_FORMAT = "MM/dd/YYYY hh:mm:ss aaa";
			
	private SimpleDateFormat format;

	private String message;
	
	private Date time;
	
	private String status;
	
	public UserAuthDto getAuth() {
		return auth;
	}

	public void setAuth(UserAuthDto auth) {
		this.auth = auth;
	}

	private UserAuthDto auth;
	
	public ResponseDto() {
		format = new SimpleDateFormat(DATE_FORMAT);
	}
	
	public ResponseDto(String dateFormat) {
		format = new SimpleDateFormat(dateFormat);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		if(format == null || format.equals(""))
			format = new SimpleDateFormat(DATE_FORMAT);
		if(time != null)
			return format.format(time);
		return format.format(new Date());
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
