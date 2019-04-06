package com.networkflow.apputils;

public enum StatusMessage {
	Valid("Valid"),
	NoBuiltInDirection("No Built in Direciton");

    private String message;
	 
	StatusMessage(String message) {
        this.message = message;
    }
 
    public String getMessage() {
        return message;
    }
}
