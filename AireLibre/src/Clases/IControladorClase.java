package Clases;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IControladorClase {
	
	public boolean guardarClase(DataActividad actividadSeleccionada,LocalDate fecha,LocalTime hora,String lugar,int cupos,LocalDate fechaAlta);
	public boolean existeConflictoHorario(DataActividad actividadseleccionada, LocalDate fechaClase, LocalTime horaClase);
	public List<DataInscripcion> inscripcionesPorClase(long claseid);
	public DataDeportista transformarDeportista(String Nickname);
}
