package com.wenance.core.exceptions;

public class ServerCommunicationException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ServerCommunicationException(String mensaje) {
        super(mensaje);
    }
}
