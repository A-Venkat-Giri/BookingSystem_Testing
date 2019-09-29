package com.bbs.exception;

public class TicketRetrievalFailedException extends RuntimeException{

	public TicketRetrievalFailedException(String string)
	{
		System.err.println(string);
	}
}
