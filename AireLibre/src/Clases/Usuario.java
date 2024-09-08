package Clases;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate;



import jakarta.persistence.*;

@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public class Usuario {
	
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "CORREOELECTRONICO")
    private String correoElectronico;

    @Column(name = "FECHANACIMIENTO")
    private LocalDate fechaNacimiento;

    @Id
    @Column(name = "NICKNAME")
    private String nickname;
    
    @Column(name = "PASS")
    private String pass;

    // Getters and setters

    // Getters y setters

    // Constructor
    public Usuario() {}

    public Usuario(String nombre, String apellido, String nickname, String correoElectronico, LocalDate fechaNacimiento, String pass) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
        this.pass = pass;
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

    public String getNickname() {
        return nickname;
    }
    
    public String getPass() {
        return pass;
    }

    public void setPass(String passw) {
        this.pass= passw;
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
}
