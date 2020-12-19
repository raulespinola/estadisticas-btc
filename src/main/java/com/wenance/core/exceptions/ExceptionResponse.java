package com.wenance.core.exceptions;


import java.util.Date;


public class ExceptionResponse extends Throwable {

	private final Date timestampError;
	private final String codigoError;
	private final String mensajeError;
	private final Throwable cause;

	public ExceptionResponse(Date timeStamp, String mensajeError, String codigoError, Throwable cause) {
		this.timestampError = timeStamp;
		this.mensajeError = mensajeError;
		this.codigoError = codigoError;
		this.cause = cause;
	}

	public Date getTimestampError() {
		return timestampError;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	@Override
	public synchronized Throwable getCause() {
		return cause;
	}

	@Override
	public String toString() {
		return "ExceptionResponse{" +
				"timestampError=" + timestampError +
				", codigoError='" + codigoError + '\'' +
				", mensajeError='" + mensajeError + '\'' +
				'}';
	}
}
