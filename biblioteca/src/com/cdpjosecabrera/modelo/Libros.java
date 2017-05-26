package com.cdpjosecabrera.modelo;

import com.cdpjosecabrera.utilidades.CasillaIncorrecta;
import com.cdpjosecabrera.utilidades.Fecha;
import com.cdpjosecabrera.utilidades.IsbnIncorrecto;

public class Libros {
	
	int idLibros;
	String titulo;
	String autor;
	String editorial;
	String isbn;
	boolean prestado;
	Fecha fechaDevolucion;
	
	public Libros(int idLibros, String titulo, String autor, String editorial, String isbn, boolean prestado,
			Fecha fechaDevolucion) throws IsbnIncorrecto, CasillaIncorrecta {
		super();
		this.idLibros = idLibros;
		if(compruebaCasilla(titulo)){
			this.titulo = titulo;
		}
		else{
			throw new CasillaIncorrecta();
		}
		if(compruebaCasilla(autor)){
			this.autor = autor;
		}
		else{
			throw new CasillaIncorrecta();
		}
		if(compruebaCasilla(editorial)){
			this.editorial = editorial;
		}
		else{
			throw new CasillaIncorrecta();
		}

		if(compruebaIsbn(isbn)){
			this.isbn = isbn;
		}
		else{
			throw new IsbnIncorrecto();
		}
		
		this.prestado = prestado;
		this.fechaDevolucion = fechaDevolucion;
	}
	
	public Libros(){
		super();
	}

	public int getIdLibros() {
		return idLibros;
	}

	public void setIdLibros(int idLibros) {
		this.idLibros = idLibros;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	public Fecha getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Fecha fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	@Override
	public String toString() {
		return "Libros [idLibros=" + idLibros + ", titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial
				+ ", isbn=" + isbn + ", prestado=" + prestado + ", fechaDevolucion=" + fechaDevolucion + "]";
	}
	
	
	private boolean compruebaIsbn(String isbn){
	    int suma=0;
	    int nuevo=0;
	    isbn= isbn.replace(" ", "").replace("-", "");
	    for(int i=0; i<13;i++){
	        nuevo=Integer.parseInt(isbn.substring(i,i+1));
	        if(i%2==0){
	            suma+=nuevo*1;
	        }
	        else{
	            suma+=nuevo*3;
	        }
	    }
	    if(suma%10==0){
	        return true;
	    }
	    else{
	        return false;
	    }
	}
	
	private boolean compruebaCasilla(String cadena){
		if(cadena.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	

}


