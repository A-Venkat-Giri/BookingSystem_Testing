package com.bbs.exception;

public class UpdateFailedException extends RuntimeException{

	public UpdateFailedException(String string)
	{
		System.err.println(string);
	}
}
