package Clases;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.List;

@Entity
@DiscriminatorValue("DEPORTISTA")
public class Deportista extends Usuario {
    @Column(name = "PROFESIONAL")
    private String esProfesional;
    
    @ManyToMany(mappedBy = "deportistas")
    private Set<Clase> clases;
    
    @OneToMany(mappedBy = "deportista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscripcion> inscripciones;

    // Constructor sin argumentos
    public Deportista() {
    }

    // Constructor con todos los argumentos
    public Deportista(String nickname, String nombre, String apellido, String correoElectronico, LocalDate fechaNacimiento, String pass, String esProfesional) {
    	super(nombre, apellido, nickname, correoElectronico, fechaNacimiento, pass);
        this.esProfesional = esProfesional;
    }

    // Getters y setters
    public String getEsProfesional() {
        return this.esProfesional;
    }
    
    public void setEsProfesional(String esProfesional) {
        this.esProfesional = esProfesional;
    }
    
    
    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
    
    
    
    public void addInscripcion(Inscripcion inscripcion) {
        // Verificar si la inscripción ya está en la lista
        if (inscripciones != null && !inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
    }
}
