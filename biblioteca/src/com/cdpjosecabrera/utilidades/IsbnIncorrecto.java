package com.cdpjosecabrera.utilidades;

public class IsbnIncorrecto extends Exception{
    public IsbnIncorrecto() {
        super("isbn no valido");
    }
}
