package com.cdpjosecabrera.controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;

import com.cdpjosecabrera.modelo.Libros;
import com.cdpjosecabrera.utilidades.CasillaIncorrecta;
import com.cdpjosecabrera.utilidades.Fecha;
import com.cdpjosecabrera.utilidades.IsbnIncorrecto;

//clase controladora

public class LibrosController {
	
	private final static String drv = "com.mysql.jdbc.Driver";
	private final static String db = "jdbc:mysql://localhost:3306/biblioteca";
	private final static String userAndPass = "root";
	Connection cn;
	Statement st;
	
	public ResultSet rs;
	public ArrayList<Libros> listagetlibros;

	public LibrosController() {
		super();
		listagetlibros = new ArrayList<Libros>();
	}

	public void abrirConexion() throws SQLException, ClassNotFoundException {
		Class.forName(drv);
		cn = DriverManager.getConnection(db, userAndPass, "");
		System.out.println("La conexion se ha realizado con exito");
	}

	public void cerrarConexion() throws SQLException {
		if (rs != null)
			rs.close();
		if (st != null)
			st.close();
		if (cn != null)
			cn.close();
		System.out.println("Conexion cerrada");
	}
	

	
	//idLibros, String titulo, String autor, String editorial, String isbn, boolean prestado,Fecha fechaDevolucion
	public ArrayList<Libros> getLibros() throws SQLException, IsbnIncorrecto, ParseException, ClassNotFoundException, CasillaIncorrecta {
		
		listagetlibros=new ArrayList<Libros>();
		abrirConexion();
		
		
		st=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		rs=st.executeQuery("select * from libros");
		while(rs.next()){
			int idLibros=rs.getInt("idLibros");
			String titulo=rs.getString("titulo");
			String autor = rs.getString("autor");
			String editorial = rs.getString("editorial");
			String isbn = rs.getString("isbn");
			boolean prestado = rs.getBoolean("prestado");
			Fecha fechaDevolucion = new Fecha(rs.getDate("fechaDevolucion"));
			
			Libros libro = new Libros(idLibros,titulo, autor, editorial, isbn, prestado,fechaDevolucion);
			listagetlibros.add(libro);
			libro=null;
		}
		
		return listagetlibros;
		
		
	}
	
	
	public boolean agregarLibro(Libros libro) throws SQLException{
		boolean agregado = false;
		

		String titulo = libro.getTitulo();
		String autor = libro.getAutor();
		String editorial = libro.getEditorial();
		String isbn = libro.getIsbn();
		boolean prestado = libro.isPrestado();
		java.sql.Date fechaDevolucion = new java.sql.Date(libro.getFechaDevolucion().getDate().getTime());
		
		String sql="insert into libros values(?,?,?,?,?,?,?)";
		PreparedStatement prepareStatement= cn.prepareStatement(sql);

		prepareStatement.setInt(1, 0);
		prepareStatement.setString(2, titulo);
		prepareStatement.setString(3, autor);
		prepareStatement.setString(4, editorial);
		prepareStatement.setString(5, isbn);
		prepareStatement.setBoolean(6, prestado);		
		prepareStatement.setDate(7, fechaDevolucion);
		
		
		prepareStatement.executeUpdate();
		prepareStatement=null;
		agregado=true;
		return agregado;
				
		
	}
	
	public int modificarLibro(Libros libro) throws SQLException{
		 
		int rows=0; //filas afectadas
		
		String sql="Update libros Set titulo=?, autor=?, editorial=?, prestado=? , fechaDevolucion=? Where isbn=?";
		//String sql="Update libros Set autor=? Where isbn=?";
		PreparedStatement prepareStatement= cn.prepareStatement(sql);
		
		prepareStatement.setString(1, libro.getTitulo());
		prepareStatement.setString(2, libro.getAutor());
		prepareStatement.setString(3, libro.getEditorial());
		prepareStatement.setBoolean(4, libro.isPrestado());
		
		java.sql.Date fechaDevolucion = new java.sql.Date(libro.getFechaDevolucion().getDate().getTime());
		
	
		prepareStatement.setDate(5, fechaDevolucion);
		prepareStatement.setString(6, libro.getIsbn());
		
		rows=prepareStatement.executeUpdate();
		
		return rows;
	
	}
	
	
	public int  borrarLibro(Libros libro) throws SQLException{
		int rows=0;
		String campo="isbn";
		
		String sql="delete from libros where " + campo + "=?";
		PreparedStatement prepareStatement= cn.prepareStatement(sql);
		prepareStatement.setString(1, libro.getIsbn());
		rows=prepareStatement.executeUpdate();
		
		return rows;
	}
	
public ArrayList<Libros> filtrar(String sql) throws SQLException, IsbnIncorrecto, ParseException, ClassNotFoundException, CasillaIncorrecta {
		
		listagetlibros=new ArrayList<Libros>();
		abrirConexion();
		
		
		String consulta="select * from libros where autor like '"+sql+"%'";
		st=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		rs=st.executeQuery(consulta);
		
		while(rs.next()){
			int idLibros=rs.getInt("idLibros");
			String titulo=rs.getString("titulo");
			String autor = rs.getString("autor");
			String editorial = rs.getString("editorial");
			String isbn = rs.getString("isbn");
			boolean prestado = rs.getBoolean("prestado");
			Fecha fechaDevolucion = new Fecha(rs.getDate("fechaDevolucion"));
			
			Libros libro = new Libros(idLibros,titulo, autor, editorial, isbn, prestado,fechaDevolucion);
			listagetlibros.add(libro);
			libro=null;
		}
		return listagetlibros;
	}


public ArrayList<Libros> filtrar2(String sql) throws SQLException, IsbnIncorrecto, ParseException, ClassNotFoundException, CasillaIncorrecta {
	
	listagetlibros=new ArrayList<Libros>();
	abrirConexion();
	
	
	String consulta="select * from libros where editorial like '"+sql+"%'"; 
	st=cn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
	rs=st.executeQuery(consulta);
	
	while(rs.next()){
		int idLibros=rs.getInt("idLibros");
		String titulo=rs.getString("titulo");
		String autor = rs.getString("autor");
		String editorial = rs.getString("editorial");
		String isbn = rs.getString("isbn");
		boolean prestado = rs.getBoolean("prestado");
		Fecha fechaDevolucion = new Fecha(rs.getDate("fechaDevolucion"));
		
		Libros libro = new Libros(idLibros,titulo, autor, editorial, isbn, prestado,fechaDevolucion);
		listagetlibros.add(libro);
		libro=null;
	}
	return listagetlibros;
}
	


}
