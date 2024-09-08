package Clases;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class ControladorClase implements IControladorClase{
	 private static ControladorClase instancia;
	 
	 
	 
		private ControladorClase() {}
	 
	    public static ControladorClase getInstancia() {
	        if (instancia == null) {
	            synchronized (ControladorClase.class) {
	                if (instancia == null) {
	                    instancia = new ControladorClase();
	                }
	            }
	        }
	        return instancia;
	    }
	    
	    
		public DataClase getDataFromClases(Long id) {
			Clase clase = DB.getClaseById(id);
			ControladorActividad controladorActividad= ControladorActividad.getInstancia();
			
			DataClase data = new DataClase(clase.getId(), clase.getFecha(),clase.getHoraEntrenamiento(), clase.getLugar(), clase.getFechaAlta() , clase.getCupo() , 
		    		null , null , controladorActividad.getDataFromActividad(clase.getActividad().getNombre()));
		
			return data;
		}
		
		public boolean guardarClase(DataActividad actividadSeleccionada,LocalDate fecha,LocalTime hora,String lugar,int cupos,LocalDate fechaAlta) {
			// Crear la nueva clase y guardar en la base de datos
			Clase nuevaClase = new Clase();
			Actividad actividad = DB.getActividadById(actividadSeleccionada.getNombre());
			nuevaClase.setActividad(actividad);
			nuevaClase.setFecha(fecha);
			nuevaClase.setHoraEntrenamiento(hora);
			nuevaClase.setLugar(lugar);
			nuevaClase.setCupo(cupos);
			nuevaClase.setFechaAlta(fechaAlta);
			actividad.addClase(nuevaClase);
			
			if (DB.saveClase(nuevaClase)) {
				DB.actualizarClaseActividad(actividad);
				return true;
		}else return false;
	}
		
		
	   public boolean existeConflictoHorario(DataActividad actividadseleccionada, LocalDate fechaClase, LocalTime horaClase) {
		   	Actividad activida = DB.getActividadById(actividadseleccionada.getNombre());
		   	List<Actividad> actividades = activida.getEntrenador().getActividades();
	        LocalDateTime inicioNuevaClase = LocalDateTime.of(fechaClase, horaClase);

	        for (Actividad actividad : actividades) {
	            List<Clase> clasesExistentes = actividad.getClases(); // Obtener las clases asociadas a la actividad
	            Duration duracionActividad = actividad.getDuracion();
	            LocalDateTime finNuevaClase = inicioNuevaClase.plus(duracionActividad);

	            for (Clase claseExistente : clasesExistentes) {
	                LocalDateTime inicioClaseExistente = LocalDateTime.of(claseExistente.getFecha(), claseExistente.getHoraEntrenamiento());
	                LocalDateTime finClaseExistente = inicioClaseExistente.plus(claseExistente.getActividad().getDuracion());

	                // Verificar solapamiento de periodos
	                if (inicioNuevaClase.isBefore(finClaseExistente) && finNuevaClase.isAfter(inicioClaseExistente)) {
	                    return true; // Conflicto detectado
	                }
	            }
	        }

	        return false; // No se detectaron conflictos
	    }
	   
	   
		public List<DataInscripcion> inscripcionesPorClase(long claseid){
			Clase clase = DB.getClaseById(claseid);
			List<DataInscripcion> dataInscripciones = new ArrayList<>();;
			List<Inscripcion> inscripciones = DB.obtenerInscripcionesPorClase(clase);
			for (Inscripcion inscripcion : inscripciones) {
				dataInscripciones.add(getDataFromInscripcion(inscripcion.getId()));

			}
			return dataInscripciones;
		}
			
			public DataInscripcion getDataFromInscripcion(long id) {
				Inscripcion inc = DB.getInscripcionById(id);
				DataInscripcion data = new DataInscripcion(inc.getFechaInsc(),inc.getCantidadDeportistas(),inc.getCosto(),transformarDeportista(inc.getDeportista().getNickname()),getDataFromClases(inc.getClase().getId()));
			
				return data;
			}
			
			public DataDeportista transformarDeportista(String Nickname) {
				Deportista deportista = DB. getDeportistaById(Nickname);
				DataDeportista data = new DataDeportista(deportista.getNickname(),deportista.getNombre() , deportista.getApellido(),deportista.getCorreoElectronico(), deportista.getFechaNacimiento(), deportista.getPass(), deportista.getEsProfesional());
				return data;
			}
				
}
	   
	   

