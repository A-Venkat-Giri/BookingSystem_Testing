package com.bbs.exception;

public class BookingFailedException extends RuntimeException{

	public BookingFailedException(String string)
	{
		System.err.println(string);
	}
}
