package Clases;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("ENTRENADOR")

public class Entrenador extends Usuario {
    @Column(name = "DISCIPLINA")
    private String disciplina;

    @Column(name = "SITIO_WEB")
    private String sitioWeb;
    
    @OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Actividad> actividades;

    // Constructor sin argumentos
    public Entrenador() {
    }

    // Constructor con todos los argumentos
    public Entrenador(String nickname, String nombre, String apellido, String correoElectronico, LocalDate fechaNacimiento, String pass, String disciplina, String sitioWeb) {
        super(nombre, apellido, nickname, correoElectronico, fechaNacimiento, pass);
        this.disciplina = disciplina;
        this.sitioWeb = sitioWeb;
    }

    // Getters y setters
    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
    
    public void addActividad(Actividad actividad) {
        if (actividad != null) {
            actividades.add(actividad);
            actividad.setEntrenador(this); // Establecer la relación bidireccional
        }
    }
    
    public List<Actividad> getActividades() {
        return this.actividades;
    }
    
    public void RemoverActividad(Actividad actividad) {
    	    if (actividad != null && actividades.contains(actividad)) {
    	        this.actividades.remove(actividad);
    	    }
    	}
 }
