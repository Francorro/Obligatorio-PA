package Clases;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class DataActividad {

    private String nombre;

    private String descripcion;

    private Duration duracion; // Almacena la duración en minutos

    private double costo;

    private LocalDate fechaAlta;

    private Boolean estado;

    //private byte[] imagenBytes; // Almacena la imagen como un array de bytes en la base de datos

    //private BufferedImage imagen; // Atributo que no se almacena directamente en la base de datos

    private DataEntrenador entrenador;
    
    private List<DataClase> clases;


    // Constructor sin argumentos
    public DataActividad() {
    }

    public DataActividad(String nombre, String descripcion, Duration duracion, double costo, 
    		LocalDate fechaAlta, Boolean estado, DataEntrenador entrenador) { //BufferedImage imagen, 
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.costo = costo;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        // this.setImagen(imagen); // Utiliza el método setImagen para convertir y asignar la imagen
        this.entrenador = entrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public DataEntrenador getEntrenador() {
        return this.entrenador;
    }
    
   public void setEntrenador(DataEntrenador entren) {
        this.entrenador=entren;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Duration getDuracion() {
        return duracion;
    }

    public void setDuracion(Duration duracion) {
        this.duracion = duracion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    
    public List<DataClase> getClases() {
        return clases;
    }
    
    public void setClases(List<DataClase> clases) {
        this.clases = clases;
    }


    public void setFechaAlta(LocalDate fecha) {
        this.fechaAlta = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


    
}
