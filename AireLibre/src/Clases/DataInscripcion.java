package Clases;

import java.time.LocalDate;


public class DataInscripcion {


    private Long id;


    private LocalDate fechaInsc;
    

    private int cantidadDeportistas;
    

    private double costo;


    private DataDeportista deportista;

    private DataClase clase;

    // Constructor por defecto
    public DataInscripcion() {
    }

    // Constructor con parámetros
    public DataInscripcion(LocalDate fechaInsc, int cantidadDeportistas, double costo, DataDeportista deportista, DataClase clase) {
        this.fechaInsc = fechaInsc;
        this.cantidadDeportistas = cantidadDeportistas;
        this.costo = costo;
        this.deportista = deportista;
        this.clase = clase;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInsc() {
        return fechaInsc;
    }

    public void setFechaInsc(LocalDate fechaInsc) {
        this.fechaInsc = fechaInsc;
    }

    public int getCantidadDeportistas() {
        return cantidadDeportistas;
    }

    public void setCantidadDeportistas(int cantidadDeportistas) {
        this.cantidadDeportistas = cantidadDeportistas;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public DataDeportista getDeportista() {
        return deportista;
    }

    public void setDeportista(DataDeportista deportista) {
        this.deportista = deportista;
    }

    public DataClase getClase() {
        return clase;
    }

    public void setClase(DataClase clase) {
        this.clase = clase;
    }
}
