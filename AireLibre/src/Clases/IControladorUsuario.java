package Clases;

import java.time.LocalDate;
import java.util.List;

public interface IControladorUsuario {
	
	public List<DataEntrenador> listaEntrenadores();
	public DataEntrenador getEntrenador(String Nickname);
	public boolean crearUsuario(String nombre, String apellido, String nickname, String correoElectronico, LocalDate fechaNacimiento, 
			String pass, boolean esDeportista, boolean esEntrenador, boolean esProfesional, String sitio, String disciplina);
	public List<DataDeportista> listaDeportistas();
	public List<DataUsuario> listaUsuarios();
	public DataDeportista getDeportista(String Nickname);
	public boolean actualizarUsuario(String Nickname, String nombre, String apellido, String correoElectronico, LocalDate fechaNacimiento,Boolean esEntrenador ,String sitioWeb,String disciplina, boolean esProfesional, String pass);
}
