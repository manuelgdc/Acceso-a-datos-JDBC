package com.cdpjosecabrera.utilidades;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class Fecha implements Comparable<Fecha>{
	   private Calendar fecha;
	   
	   
	   
	   
	   private int dia,mes,año;
	  

	    public Fecha() {
	        fecha=GregorianCalendar.getInstance(Locale.getDefault());
	        this.setFecha(this.toString());
	        //Date date=null;
	        //fecha.setTime(date);
	        
	    }
	    
	    public Fecha(Date date) throws ParseException {	     	    	  
	    	  fecha=Calendar.getInstance();
	    	  fecha.setTime(date);
	        
	    }

	    public Fecha(String date) throws FechaIncorrecta{
	        if(!isFechaValida(date))
	            throw new FechaIncorrecta("Introduzca una fecha valida");
	        this.setFecha(date);
	}

	    public Calendar getFecha() {
	        return fecha;
	    }

	    public void setFecha(Calendar fecha) {
	        this.fecha = fecha;
	    }

	    private void setFecha(String  date) {
	       String[] arrayFecha=date.split("/");
	       this.fecha=new GregorianCalendar(Integer.parseInt(arrayFecha[2]),Integer.parseInt(arrayFecha[1])-1,Integer.parseInt(arrayFecha[0]));
	       this.setAño();this.setMes();this.setDia();
	    }

	    public int getDia() {
	        return dia;
	    }

	    private void setDia() {
	        this.dia=this.fecha.get(Calendar.DAY_OF_MONTH);
	        
	    }

	    public int getMes() {
	        return mes+1;
	    }

	    public void setMes() {
	        this.mes=this.fecha.get((Calendar.MONTH));
	    }

	    public int getAño() {
	        return año;
	    }

	    public void setAño() {
	        this.año = fecha.get(Calendar.YEAR);
	    }
	    public Date getDate(){
	        Date date=null;
	        if(fecha !=null)
	            date=(Date) fecha.getTime();
	        return date;
	    }
	    public void agregarDias(int dias){
	       
	        this.fecha.add(Calendar.DAY_OF_MONTH, dias);
	   
	    
	    }
	    // Tiene que ser fechas del mismo año
	    public static int diasEntreFechas(Fecha fechaPosterior,Fecha fechaAnterior){
	        int dias=-1;
	        if(fechaPosterior.compareTo(fechaAnterior)>=0 && (fechaPosterior.año-fechaAnterior.año==0)){
	            long milis=fechaPosterior.getFecha().getTimeInMillis()-fechaAnterior.getFecha().getTimeInMillis();
	            Calendar c = Calendar.getInstance();
	            c.setTimeInMillis(milis);
	            dias=c.get(Calendar.DAY_OF_YEAR);
	            dias--;
	        
	        }
	    
	        return dias;
	    }
	    
	    public int getEdad(){
	    
	     Calendar hoy = Calendar.getInstance();
	     int difYear=hoy.get(Calendar.YEAR)-fecha.get(Calendar.YEAR);
	     int difMes=hoy.get(Calendar.MONTH)-fecha.get(Calendar.MONTH);
	     int difDia=hoy.get(Calendar.DAY_OF_MONTH)-fecha.get(Calendar.DAY_OF_MONTH);
	    if (difMes < 0 || (difMes == 0 && difDia < 0)) {
	        difYear--;
	    }
	    return difYear;
	    }
	    @Override
	    public int hashCode() {
	        int hash = 7;
	        hash = 59 * hash + Objects.hashCode(this.fecha);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Fecha other = (Fecha) obj;
	        if (!Objects.equals(this.fecha, other.fecha)) {
	            return false;
	        }
	        return true;
	    }
	    

	    @Override
	    public String toString() {
	         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	           return dateFormat.format(fecha.getTime());
	      
	    }
	        
	    
	        private static boolean isFechaValida(String fecha) {
	        try {
	            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	            formatoFecha.setLenient(false);//Evita que se corriga la fecha Ej. 32 enero pase a 2 febrero
	            formatoFecha.parse(fecha);
	        } catch (ParseException e) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public int compareTo(Fecha f) {
	         Fecha fecha=(Fecha)f;
	         
	        return this.fecha.compareTo(f.fecha);
	        
	       
	    }
	   
	}

