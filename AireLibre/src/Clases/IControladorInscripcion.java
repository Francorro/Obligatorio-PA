package Clases;

import java.util.List;

public interface IControladorInscripcion {
	
	 public boolean guardarInscripcion(DataDeportista deportista, DataClase clase, int cantidad, DataActividad actividad);
	 public List<DataInscripcion> obtenerInscripcionDepor(DataDeportista depor);

}
