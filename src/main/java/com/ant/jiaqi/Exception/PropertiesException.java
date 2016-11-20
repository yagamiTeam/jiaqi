package com.ant.jiaqi.Exception;

public class PropertiesException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public PropertiesException(String message) {
		super(message);
	}
	
	public PropertiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
