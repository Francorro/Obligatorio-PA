package Clases;

import java.time.Duration;
import java.util.List;

public interface IControladorActividad {
	
	public abstract DataActividad getDataFromActividad(String nombre);
	public abstract List<DataActividad> transformarActividades();
	public abstract List<DataClase> transformarClasesActividad(DataActividad actividadSeleccionada);
	public boolean existeActividad(String nombre);
	public boolean crearActividad(String nombre, String descripcion, Duration duracion, double costo, String entrenadorid);
	public DataEntrenador getEntrenadorByActId(String nombre);
	public boolean actualizarActividad(String nombre, DataEntrenador antiguo, DataEntrenador entren, String descripcion, Duration duracion, double costo);
	public List<DataActividad> transformarActividadesEntrenador(DataEntrenador entre);
	public List<DataClase> transformarClasesActivasActividad(DataActividad actividadSeleccionada);
}
