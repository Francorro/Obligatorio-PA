package Clases;

import java.time.LocalDate;
import java.util.List;

public class DataEntrenador extends DataUsuario {

	private String disciplina;

    private String sitioWeb;
    
    private List<DataActividad> actividades;

    // Constructor sin argumentos
    public DataEntrenador() {
    }

    // Constructor con todos los argumentos
    public DataEntrenador(String nickname, String nombre, String apellido, String correoElectronico, LocalDate fechaNacimiento, String pass, String disciplina, String sitioWeb) {
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
    
    public void addActividad(DataActividad actividad) {
        if (actividad != null) {
            actividades.add(actividad);
            actividad.setEntrenador(this); // Establecer la relación bidireccional
        }
    }
    
    public List<DataActividad> getActividades() {
        return actividades;
    }
    
    public void RemoverActividad(DataActividad actividad) {
    	    if (actividad != null && actividades.contains(actividad)) {
    	        actividades.remove(actividad);
    	    }
    	}
    
    
    public void setActividades(List<DataActividad> actividad) {
        this.actividades=actividad;
    }
 }
