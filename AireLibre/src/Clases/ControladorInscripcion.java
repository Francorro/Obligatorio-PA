package Clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ControladorInscripcion implements IControladorInscripcion {
	
	 private static ControladorInscripcion instancia;
	
	private ControladorInscripcion() {}
	
	
    public static ControladorInscripcion getInstancia() {
        if (instancia == null) {
            synchronized (ControladorInscripcion.class) {
                if (instancia == null) {
                    instancia = new ControladorInscripcion();
                }
            }
        }
        return instancia;
    }
    
    // Guardar inscripción en la base de datos
    public boolean guardarInscripcion(DataDeportista deportista, DataClase clase, int cantidad, DataActividad actividad) {
        Inscripcion inscripcion = new Inscripcion();
        Deportista depor = DB.getDeportistaById(deportista.getNickname());
        Clase clas =  DB.getClaseById(clase.getId());
        inscripcion.setDeportista(depor);
        inscripcion.setClase(clas);
        inscripcion.setCantidadDeportistas(cantidad);
        
        inscripcion.setCosto(actividad.getCosto() * cantidad);
        inscripcion.setFechaInsc(LocalDate.now());
        
        depor.addInscripcion(inscripcion);
        clas.addInscripcion(inscripcion);
        clas.setCupo(clas.getCupo() - cantidad);
        
       return DB.saveInscripcion(inscripcion, clas, depor);
    	
    }
    
    
    public List<DataInscripcion> obtenerInscripcionDepor(DataDeportista depor) {
    	
    		Deportista deportista = DB.getDeportistaById(depor.getNickname());
    		
			List<Inscripcion> inscripciones = DB.obtenerInscripciones(deportista);
			List<DataInscripcion> dataInscripciones = new ArrayList<>();

			for (Inscripcion inscripcion : inscripciones) {
				DataInscripcion dataInscripcion = new DataInscripcion();
				
				dataInscripcion.setFechaInsc(inscripcion.getFechaInsc());
				dataInscripcion.setCosto(inscripcion.getCosto());
				dataInscripcion.setCantidadDeportistas(inscripcion.getCantidadDeportistas());
				
				dataInscripciones.add(dataInscripcion);
	        
			}
			return dataInscripciones;
    }
    

}
