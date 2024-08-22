package com.sujithwork.trainTicketing.Excepion;

public class SeatAlreadyTakenException extends RuntimeException {
    public SeatAlreadyTakenException(String message) {
        super(message);
    }
}
