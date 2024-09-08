package Clases;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import jakarta.persistence.*;
import java.util.List;


import javax.imageio.ImageIO;

@Entity
@Table(name = "ACTIVIDAD")
public class Actividad {

    @Id
    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "DURACION")
    private Duration duracion; // Almacena la duración en minutos

    @Column(name = "COSTO")
    private double costo;

    @Column(name = "FECHAALTA")
    private LocalDate fechaAlta;

    @Column(name = "ESTADO")
    private Boolean estado;

    @Lob
    @Column(name = "IMAGEN", nullable = true)
    private byte[] imagenBytes; // Almacena la imagen como un array de bytes en la base de datos

    @Transient
    private BufferedImage imagen; // Atributo que no se almacena directamente en la base de datos

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Entrenador entrenador;
    
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Clase> clases;


    // Constructor sin argumentos
    public Actividad() {
    }

    public Actividad(String nombre, String descripcion, Duration duracion, double costo, LocalDate fechaAlta, Boolean estado, BufferedImage imagen, Entrenador entrenador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.costo = costo;
        this.fechaAlta = fechaAlta;
        this.estado = estado;
        this.setImagen(imagen); // Utiliza el método setImagen para convertir y asignar la imagen
        this.entrenador = entrenador;
    }

    public String getNombre() {
        return nombre;
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
    
    public List<Clase> getClases() {
        return clases;
    }
    
    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }


    public void setFechaAlta() {
        this.fechaAlta = LocalDate.now();
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    // Métodos de conversión
    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
        this.imagenBytes = convertirBufferedImageABytes(imagen);
    }

    public BufferedImage getImagen() {
        if (imagen == null && imagenBytes != null) {
            imagen = convertirBytesABufferedImage(imagenBytes);
        }
        return imagen;
    }
    
    public int getCantidadClases() {
        return clases != null ? clases.size() : 0;
    }

    // Métodos de conversión entre BufferedImage y byte[]
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

    // Getters y setters para imagenBytes (para JPA)
    public byte[] getImagenBytes() {
        return imagenBytes;
    }

    public void setImagenBytes(byte[] imagenBytes) {
        this.imagenBytes = imagenBytes;
        this.imagen = convertirBytesABufferedImage(imagenBytes);
    }
    
    
    public void addClase(Clase clase) {
        if (clase != null && !clases.contains(clase)) {
            clases.add(clase);
        }
    }
}
