package Clases;

import java.util.List;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


public class ControladorActividad implements IControladorActividad{

	 private static ControladorActividad instancia;

	
	private ControladorActividad() {}
	
	public List<DataActividad> transformarActividades(){
		List<Actividad> actividades = DB.obtenerActividades();
		List<DataActividad> dataActividades= new ArrayList<>();
		for (Actividad actividad : actividades) {
                dataActividades.add(getDataFromActividad(actividad.getNombre()));
        }
        return dataActividades;		
	}
	
	public List<DataActividad> transformarActividadesEntrenador(DataEntrenador entre){
		Entrenador entrenador = DB.getEntrenadorById(entre.getNickname());
		List<Actividad> actividades = entrenador.getActividades();
		List<DataActividad> dataActividades= new ArrayList<>();
		for (Actividad actividad : actividades) {
                dataActividades.add(getDataFromActividad(actividad.getNombre()));
        }
        return dataActividades;		
	}
	
	
	public DataActividad getDataFromActividad(String nombre) {
		Actividad act = DB.getActividadById(nombre);
		ControladorUsuario controladorUsuario = ControladorUsuario.getInstancia();
		DataActividad data = new DataActividad(act.getNombre(), act.getDescripcion(), act.getDuracion(), 
				act.getCosto(), act.getFechaAlta(), act.getEstado(), controladorUsuario.getEntrenador(act.getEntrenador().getNickname()));
		return data;
	}
	
	
	
    public static ControladorActividad getInstancia() {
        if (instancia == null) {
            synchronized (ControladorActividad.class) {
                if (instancia == null) {
                    instancia = new ControladorActividad();
                }
            }
        }
        return instancia;
    }
	
    public List<DataClase> transformarClasesActividad(DataActividad actividadSeleccionada) {
    	//Obtengo todas las clases de la actividad
    	List<Clase> clases = DB.obtenerClasesPorActividad(DB.getActividadById(actividadSeleccionada.getNombre()));
    	
    	List<DataClase> dataClases= new ArrayList<>();
    	ControladorClase controladorClase = ControladorClase.getInstancia();
		for (Clase clase : clases) {
                dataClases.add(controladorClase.getDataFromClases(clase.getId()));
        }
        return dataClases;		
	}
    
    public List<DataClase> transformarClasesActivasActividad(DataActividad actividadSeleccionada) {
        // Obtengo todas las clases de la actividad
        List<Clase> clases = DB.obtenerClasesPorActividad(DB.getActividadById(actividadSeleccionada.getNombre()));
        
        List<DataClase> dataClases = new ArrayList<>();
        ControladorClase controladorClase = ControladorClase.getInstancia();
        LocalDate fechaActual = LocalDate.now();  // Obtener la fecha actual
        LocalTime horaActual = LocalTime.now();

        
        for (Clase clase : clases) {
            // Suponiendo que Clase tiene un método getFechaAlta() que retorna LocalDate
        	if(clase.getFecha().isAfter(fechaActual)  || clase.getFecha().isEqual(fechaActual) && clase.getHoraEntrenamiento().isAfter(horaActual)) {
        		if (clase.getFechaAlta().isBefore(fechaActual) || clase.getFechaAlta().isEqual(fechaActual)) {
        			dataClases.add(controladorClase.getDataFromClases(clase.getId()));
        		}
        	}
        }
        
        return dataClases;        
    }
    
    
	public boolean existeActividad(String nombre) {
		return DB.existeActividad(nombre);
	}
	
	public DataEntrenador getEntrenadorByActId(String nombre) {
		Actividad actividad = DB.getActividadById(nombre);
		Entrenador entrenador = actividad.getEntrenador();
		DataEntrenador data = new DataEntrenador();
		data.setNombre(entrenador.getNombre());
		data.setApellido(entrenador.getApellido());
		data.setNickname(entrenador.getNickname());
		
		
		return data;
		
	}
	
	public boolean actualizarActividad(String nombre, DataEntrenador antig, DataEntrenador entren, String descripcion, Duration duracion, double costo) {
		
		Actividad actividadSeleccionada = DB.getActividadById(nombre);
		Entrenador entrenador = DB.getEntrenadorById(entren.getNickname());
		Entrenador antiguo =  DB.getEntrenadorById(antig.getNickname());
        
        actividadSeleccionada.setDescripcion(descripcion);
        actividadSeleccionada.setDuracion(duracion);
        actividadSeleccionada.setCosto(costo);
        if (!antiguo.getNickname().equals(entrenador.getNickname())) {
        	actividadSeleccionada.setEntrenador(entrenador);
        	antiguo.RemoverActividad(actividadSeleccionada);
        	entrenador.addActividad(actividadSeleccionada);
        	
    }
        
        return DB.actualizarActividad(actividadSeleccionada, entrenador, antiguo);
        
		
	}
	
	
	
	public boolean crearActividad(String nombre, String descripcion, Duration duracion, double costo, String entrenadorid) {
		
        Actividad nuevaActividad = new Actividad();
        Entrenador entrenadorSeleccionado = DB.getEntrenadorById(entrenadorid);
        nuevaActividad.setNombre(nombre);
        nuevaActividad.setDescripcion(descripcion);
        nuevaActividad.setDuracion(duracion);
        nuevaActividad.setCosto(costo);
        nuevaActividad.setFechaAlta();
        nuevaActividad.setEstado(true);
        nuevaActividad.setEntrenador(entrenadorSeleccionado);
        
        return DB.saveActividad(nuevaActividad, entrenadorSeleccionado);
		
	}
	
    	
}
	
	
		
