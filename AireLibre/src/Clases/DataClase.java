package Clases;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataClase {

	private Long id;

    private LocalDate fecha;

    private LocalTime horaEntrenamiento;

    private String lugar;

    private LocalDate fechaAlta;

    private int cupo;
    
    //private byte[] imagenBytes;

    //private BufferedImage imagen;
    
    private Set<DataDeportista> deportistas;
    
    private List<DataInscripcion> inscripciones;
    
    private DataActividad actividad;
    

    public DataClase() {
    }

    public DataClase(long idd ,LocalDate fecha, LocalTime horaEntrenamiento, String lugar, LocalDate fechaAlta, int cupo, 
    		byte[] imagenBytes, BufferedImage imagen, DataActividad actividad) {
        this.fecha = fecha;
        this.id = idd;
        this.horaEntrenamiento = horaEntrenamiento;
        this.lugar = lugar;
        this.fechaAlta = fechaAlta;
        this.cupo = cupo;
       //this.imagenBytes = imagenBytes;
        //this.imagen = imagen;
        this.actividad = actividad;
    }

    // Getters y setters para todos los atributos
    public Long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraEntrenamiento() {
        return horaEntrenamiento;
    }
    
    public String getFormattedHoraEntrenamiento() {
        if (horaEntrenamiento != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return horaEntrenamiento.format(formatter);
        }
        return "No disponible"; // O cualquier otro valor predeterminado
    }

    public void setHoraEntrenamiento(LocalTime horaEntrenamiento) {
        this.horaEntrenamiento = horaEntrenamiento;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }
/*
    public byte[] getImagenBytes() {
        return imagenBytes;
    }
*/	
    public Set<DataDeportista> getDeportistas() {
        return deportistas;
    }

    public void setDeportistas(Set<DataDeportista> deportistas) {
        this.deportistas = deportistas;
    }

    public DataActividad getActividad() {
        return actividad;
    }

    public void setActividad(DataActividad actividad) {
        this.actividad = actividad;
    }
    
    public void addInscripcion(DataInscripcion inscripcion) {
        // Verificar si la inscripción ya está en la lista
        if (inscripciones != null && !inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
    }
}
