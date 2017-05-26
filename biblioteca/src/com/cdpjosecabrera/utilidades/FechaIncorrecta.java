package com.cdpjosecabrera.utilidades;

public class FechaIncorrecta extends Exception{
	public FechaIncorrecta (String string){
		super("fecha no valida");
	}
}
