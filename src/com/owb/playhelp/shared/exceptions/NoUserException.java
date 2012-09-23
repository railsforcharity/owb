package com.owb.playhelp.shared.exceptions;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NoUserException extends Exception implements Serializable{
	public NoUserException(){
		
	}
    
	public NoUserException(String msg){
		super(msg);
	}
    
	public NoUserException(String msg, Throwable th){
		super(msg, th);
	}
    
	public NoUserException(Throwable th){
		super(th);
	}
}
