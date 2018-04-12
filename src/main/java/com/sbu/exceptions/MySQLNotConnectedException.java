package com.sbu.exceptions;


public class MySQLNotConnectedException extends RuntimeException {

    public MySQLNotConnectedException() {

    }

    public MySQLNotConnectedException(String message) {
        super(message);

    }

    public MySQLNotConnectedException(Throwable cause) {
        super(cause);
    }

    public MySQLNotConnectedException(String message, Throwable cause) {
        super(message, cause);
    }
}
