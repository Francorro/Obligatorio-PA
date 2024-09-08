package Clases;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Entity
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Column(name = "HORA")
    private LocalTime horaEntrenamiento;

    @Column(name = "LUGAR")
    private String lugar;

    @Column(name = "FECHAALTA")
    private LocalDate fechaAlta;

    @Column(name = "CUPO")
    private int cupo;
    
    @Lob
    @Column(name = "IMAGEN", nullable = true)
    private byte[] imagenBytes;

    @Transient
    private BufferedImage imagen;
    
    @ManyToMany
    @JoinTable(
        name = "Clase_Deportista",
        joinColumns = @JoinColumn(name = "clase_id"),
        inverseJoinColumns = @JoinColumn(name = "deportista_id")
    )
    private Set<Deportista> deportistas;
    
    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Inscripcion> inscripciones;
    
    @ManyToOne
    @JoinColumn(name = "ACTIVIDAD_NOMBRE", nullable = false)
    private Actividad actividad;
    

    public Clase() {
    }

    public Clase(LocalDate fecha, LocalTime horaEntrenamiento, String lugar, LocalDate fechaAlta, int cupo, byte[] imagenBytes, BufferedImage imagen, Actividad actividad) {
        this.fecha = fecha;
        this.horaEntrenamiento = horaEntrenamiento;
        this.lugar = lugar;
        this.fechaAlta = fechaAlta;
        this.cupo = cupo;
        this.imagenBytes = imagenBytes;
        this.imagen = imagen;
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
        return "No disponible";
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

    public byte[] getImagenBytes() {
        return imagenBytes;
    }
    /*
    public void setImagenBytes(byte[] imagenBytes) {
        this.imagenBytes = imagenBytes;
        this.imagen = convertirBytesABufferedImage(imagenBytes);
    }

    public BufferedImage getImagen() {
        if (imagen == null && imagenBytes != null) {
            imagen = convertirBytesABufferedImage(imagenBytes);
        }
        return imagen;
    }
    
    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
        this.imagenBytes = convertirBufferedImageABytes(imagen);
    }
    
    
    */

    public Set<Deportista> getDeportistas() {
        return deportistas;
    }

    public void setDeportistas(Set<Deportista> deportistas) {
        this.deportistas = deportistas;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
    
    public void addInscripcion(Inscripcion inscripcion) {
        // Verificar si la inscripción ya está en la lista
        if (inscripciones != null && !inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
    }
    
}
/*
    // Métodos de conversión
    private byte[] convertirBufferedImageABytes(BufferedImage bufferedImage) {
        if (bufferedImage != null) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(bufferedImage, "jpg", baos); // Cambia "jpg" por el formato adecuado
                return baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    private BufferedImage convertirBytesABufferedImage(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
                return ImageIO.read(bais);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}*/
    
    
    
