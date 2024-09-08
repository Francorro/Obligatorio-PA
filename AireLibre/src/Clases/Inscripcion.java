package Clases;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate fechaInsc;
    
    @Column
    private int cantidadDeportistas;
    
    @Column
    private double costo;

    // Relación con Deportista
    @ManyToOne
    @JoinColumn(name = "deportista_id")
    private Deportista deportista;

    // Relación con Clase
    @ManyToOne
    @JoinColumn(name = "clase_id")
    private Clase clase;

    // Constructor por defecto
    public Inscripcion() {
    }

    // Constructor con parámetros
    public Inscripcion(LocalDate fechaInsc, int cantidadDeportistas, double costo, Deportista deportista, Clase clase) {
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

    public Deportista getDeportista() {
        return deportista;
    }

    public void setDeportista(Deportista deportista) {
        this.deportista = deportista;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }
}
