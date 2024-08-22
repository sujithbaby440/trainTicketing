package com.sujithwork.trainTicketing.Excepion;

public class SectionFullException extends RuntimeException {
    public SectionFullException(String message) {
        super(message);
    }
}
