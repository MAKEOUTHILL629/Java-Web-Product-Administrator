/*
Esta clase me permite manipular los datos de los usuarios
 */
package Modelo.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 *
* @author Angelica Barros Espitia
 * @author Luis Felipe Hernandez chica
 */
public class UsuarioDTO implements Serializable{// se implemnta serializable para que el usuario pueda ser enviado 
    //atributos del usuario
   private int id;
   private String nombre, apellido, numeroIdentificacion, correo, clave, fechaNacimiento, estado;
   private char sexo;

    public UsuarioDTO() {
    }
    //constructor por id
    public UsuarioDTO(int id) {
        this.id = id;
    }

   //constructor con el id del usuario, tiene todos los atributos
    public UsuarioDTO(int id, String nombre, String apellido, String numeroIdentificacion, String correo, String clave, String fechaNacimiento, String estado, char sexo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroIdentificacion = numeroIdentificacion;
        this.correo = correo;
        this.clave = clave;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.sexo = sexo;
    }
//Contructor sin el id del usuario
    public UsuarioDTO(String nombre, String apellido, String numeroIdentificacion, String correo, String clave, String fechaNacimiento, String estado, char sexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroIdentificacion = numeroIdentificacion;
        this.correo = correo;
        this.clave = clave;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.sexo = sexo;
    }
    //Crear un usuario sin la clave, para enlistarlo en una lista
    public UsuarioDTO(int id, String nombre, String apellido, String numeroIdentificacion, String correo, String fechaNacimiento, String estado, char sexo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroIdentificacion = numeroIdentificacion;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.estado = estado;
        this.sexo = sexo;
    }
    // contrcutor para trasnformar un sesioDTO a UsuarioDTO
    public UsuarioDTO(int id,String correo, String clave) {
        this.correo = correo;
        this.clave = clave;
        this.id = id;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioDTO other = (UsuarioDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.sexo != other.sexo) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellido, other.apellido)) {
            return false;
        }
        if (!Objects.equals(this.numeroIdentificacion, other.numeroIdentificacion)) {
            return false;
        }
        if (!Objects.equals(this.correo, other.correo)) {
            return false;
        }
        if (!Objects.equals(this.clave, other.clave)) {
            return false;
        }
        if (!Objects.equals(this.fechaNacimiento, other.fechaNacimiento)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        return true;
    }
   //covertir un sesionDTO  a usuarioDTO
    public static UsuarioDTO parseSesionDTO(SesionDTO s){
        return new UsuarioDTO(s.getId(), s.getCorreo(), s.getClave());
    }
   
}
