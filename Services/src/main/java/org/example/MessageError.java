package org.example;

public class MessageError extends Exception {
    public MessageError() {
    }

    public MessageError(String message) {
        super(message);
    }

    public MessageError(String message, Throwable cause) {
        super(message, cause);
    }
}
