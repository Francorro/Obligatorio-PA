package Clases;

import java.time.LocalDate;

public class DataUsuario {
	
    private String nombre;

    private String apellido;

    private String correoElectronico;

    private LocalDate fechaNacimiento;

    private String nickname;
    
    private String pass;

    // Constructor
    public DataUsuario() {}
    
    public DataUsuario(String nombre, String apellido, String nickname, String correoElectronico, LocalDate fechaNacimiento, String pass) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.pass = pass;
    }

    // Getters and setters

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

	public String getPass() {
		return pass;
	}

	public void setPass(String passw) {
		this.pass = passw;
	}
    
}

