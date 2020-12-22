package com.wenance.core.exceptions;

public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ServiceException(String mensaje) {
        super(mensaje);
}

}