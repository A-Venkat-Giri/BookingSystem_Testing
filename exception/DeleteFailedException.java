package com.bbs.exception;

public class DeleteFailedException extends RuntimeException{

	public DeleteFailedException(String string)
	{
		System.err.println(string);
	}
}
