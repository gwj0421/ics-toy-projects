package com.example.ics.oauth.exception;

public class TokenValidFailedException extends RuntimeException {
    public TokenValidFailedException(String message) {
        super("TokenValidFailedException " + message);
    }
}
