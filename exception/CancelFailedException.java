package com.bbs.exception;

public class CancelFailedException extends RuntimeException{

	public CancelFailedException(String string)
	{
		System.err.println(string);
	}
}
