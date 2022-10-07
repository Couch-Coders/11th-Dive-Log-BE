package kr.couchcoding.divelog.exception;


public class InvalidAuthTokenException extends Exception{
    public InvalidAuthTokenException(String message) {
        super(message);
    }
}
