package Clases;

import java.time.LocalDate;
import java.util.Set;
import java.util.List;

public class DataDeportista extends DataUsuario {

	private String esProfesional;
    
    private Set<DataClase> clases;
    
    private List<DataInscripcion> inscripciones;

    // Constructor sin argumentos
    public DataDeportista() {
    }

    // Constructor con todos los argumentos
    public DataDeportista(String nickname, String nombre, String apellido, String correoElectronico, LocalDate fechaNacimiento, String pass, String esProfesional) {
    	super(nombre, apellido, nickname, correoElectronico, fechaNacimiento, pass);
        this.esProfesional = esProfesional;
    }

    // Getters y setters
    public String isEsProfesional() {
        return esProfesional;
    }

    public void setEsProfesional(String esProfesional) {
        this.esProfesional = esProfesional;
    }
    
    public String getEsProfesional() {
        return this.esProfesional;
    }
    
    
    public List<DataInscripcion> getInscripciones() {
        return inscripciones;
    }
    
    public Set<DataClase> getClases() {
        return clases;
    }
    

    public void setInscripciones(List<DataInscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
    
    public void addInscripcion(DataInscripcion inscripcion) {
        // Verificar si la inscripción ya está en la lista
        if (inscripciones != null && !inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
    }
}

