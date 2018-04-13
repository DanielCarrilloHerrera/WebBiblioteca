/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.Date;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Editorial;
import model.Libro;

/**
 *
 * @author dacarrillo
 */
public class LibroDAO {
    public static boolean registrar(Libro lib){
        try{
            String SQL = "INSERT INTO libros(isbn, titulo, descripcion, nombre_autor, publicacion, codigo_categoria, nit_editorial, fecha_registro)"
                        + " VALUES(?, ?, ?, ?, ?, ?, ?, (select now()))";
            Connection con = Conexion.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, lib.getIsbn());
            st.setString(2, lib.getTitulo());
            st.setString(3, lib.getDescripcion());
            st.setString(4, lib.getNombre_autor());
            st.setDate(5, Date.valueOf(lib.getPublicacion()));
            st.setInt(6, Integer.parseInt(lib.getCodigo_categoria()));
            st.setString(7, lib.getNit_editorial());
            if(st.executeUpdate() > 0){
                return true;
            } else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static ArrayList<Libro> listar(){
        try{
            String SQL = "SELECT * FROM libros;";
            Connection con = Conexion.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            //st.setString(1, cat.getNombre());
            ResultSet resultado = st.executeQuery();
            ArrayList<Libro> lista = new ArrayList<>();
            Libro lib;
            while (resultado.next()){
                lib = new Libro();
                lib.setIsbn(resultado.getString("isbn"));
                lib.setTitulo(resultado.getString("titulo"));
                lib.setDescripcion(resultado.getString("descripcion"));
                lib.setNombre_autor(resultado.getString("nombre_autor"));
                lib.setPublicacion(resultado.getDate("publicacion").toString());
                lib.setFecha_registro(resultado.getDate("fecha_registro").toString());
                lib.setCodigo_categoria(Integer.toString(resultado.getInt("codigo_categoria")));
                lib.setNit_editorial(resultado.getString("nit_editorial"));
                lista.add(lib);
            }
            return lista;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean actualizar(Libro lib){
        try{
            String SQL = "UPDATE libro set " 
                         + "(isbn, titulo, descripcion, nombre_autor, publicacion, codigo_categoria, nit_editorial)"
                         + "    titulo=?,"
                         + "    descripcion=?,"
                         + "    nombre_autor=?,"
                         + "    publicacion=?,"
                         + "    codigo_categoria=?,"
                         + "    nit_editorial=?"
                         + "    where isbn=?";
            Connection con = Conexion.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(7, lib.getIsbn());
            st.setString(1, lib.getTitulo());
            st.setString(2, lib.getDescripcion());
            st.setString(3, lib.getNombre_autor());
            st.setString(4, lib.getPublicacion());
            st.setInt(5, Integer.parseInt(lib.getCodigo_categoria()));
            st.setString(6, lib.getNit_editorial());
            if(st.executeUpdate() > 0){
                return true;
            } else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean eliminar(Libro lib){
        try{
            String SQL = "DELETE from libro where isbn=?";
            Connection con = Conexion.conectar();
            PreparedStatement st = con.prepareStatement(SQL);
            st.setString(1, lib.getIsbn());
            if(st.executeUpdate() > 0){
                return true;
            } else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
