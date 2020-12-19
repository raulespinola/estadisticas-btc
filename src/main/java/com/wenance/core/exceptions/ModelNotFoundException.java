package com.wenance.core.exceptions;

public class ModelNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ModelNotFoundException(String mensaje) {
		super(mensaje);
	}
}