package Clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorUsuario implements IControladorUsuario{
	
	 private static ControladorUsuario instancia;
	
	 
		private ControladorUsuario() {}
	 
	    public static ControladorUsuario getInstancia() {
	        if (instancia == null) {
	            synchronized (ControladorUsuario.class) {
	                if (instancia == null) {
	                    instancia = new ControladorUsuario();
	                }
	            }
	        }
	        return instancia;
	    }
	    
	    
		public List<DataUsuario> listaUsuarios(){
			List<Usuario> usuarios = DB.obtenerUsuarios();
			List<DataUsuario> dataUsuarios = new ArrayList<>();

			for (Usuario usuario : usuarios) {
				DataUsuario dataUsuario = new DataUsuario();
				
				dataUsuario.setNombre(usuario.getNombre());
				dataUsuario.setApellido(usuario.getApellido());
				dataUsuario.setNickname(usuario.getNickname());
				dataUsuario.setCorreoElectronico(usuario.getCorreoElectronico());
				dataUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
				
				dataUsuarios.add(dataUsuario);
	        
			}
			return dataUsuarios;
		}
	    
	    
		public List<DataEntrenador> listaEntrenadores(){
			List<Entrenador> entrenadores = DB.obtenerEntrenadores();
			List<DataEntrenador> dataEntrenadores = new ArrayList<>();

			for (Entrenador entrenador : entrenadores) {
				DataEntrenador dataEntrenador = new DataEntrenador();
				
				dataEntrenador.setDisciplina(entrenador.getDisciplina());
				dataEntrenador.setSitioWeb(entrenador.getSitioWeb());
				dataEntrenador.setNombre(entrenador.getNombre());
				dataEntrenador.setApellido(entrenador.getApellido());
				dataEntrenador.setNickname(entrenador.getNickname());
				dataEntrenadores.add(dataEntrenador);
	        
			}
			return dataEntrenadores;
		}
		
		public List<DataDeportista> listaDeportistas(){
			List<Deportista> deportistas = DB.obtenerDeportistas();
			List<DataDeportista> dataDeportistas = new ArrayList<>();

			for (Deportista deportista : deportistas) {
				DataDeportista dataDeportista = new DataDeportista();
				
				dataDeportista.setNombre(deportista.getNombre());
				dataDeportista.setApellido(deportista.getApellido());
				dataDeportista.setNickname(deportista.getNickname());
				dataDeportista.setEsProfesional(deportista.getEsProfesional());
				dataDeportistas.add(dataDeportista);
	        
			}
			return dataDeportistas;
		}
		
		
		
		public DataEntrenador getEntrenador(String Nickname){
			Entrenador entrenador=DB.getEntrenadorById(Nickname);
			if (entrenador==null) {
				return null;
			}else   {
				DataEntrenador dataEntrenador = new DataEntrenador();
				dataEntrenador.setDisciplina(entrenador.getDisciplina());
				dataEntrenador.setSitioWeb(entrenador.getSitioWeb());
				dataEntrenador.setNombre(entrenador.getNombre());
				dataEntrenador.setApellido(entrenador.getApellido());
				dataEntrenador.setNickname(entrenador.getNickname());
				dataEntrenador.setFechaNacimiento(entrenador.getFechaNacimiento());
				dataEntrenador.setCorreoElectronico(entrenador.getCorreoElectronico());
				dataEntrenador.setPass(entrenador.getPass());
				return dataEntrenador;
			}
		}
		
		public DataDeportista getDeportista(String Nickname){
			Deportista deportista = DB.getDeportistaById(Nickname);
			if (deportista==null) {
				return null;
			}else   {
				DataDeportista dataDeportista = new DataDeportista();
				dataDeportista.setNombre(deportista.getNombre());
				dataDeportista.setApellido(deportista.getApellido());
				dataDeportista.setNickname(deportista.getNickname());
				dataDeportista.setEsProfesional(deportista.getEsProfesional());
				dataDeportista.setFechaNacimiento(deportista.getFechaNacimiento());
				dataDeportista.setCorreoElectronico(deportista.getCorreoElectronico());
				dataDeportista.setEsProfesional(deportista.getEsProfesional());
				dataDeportista.setPass(deportista.getPass());
				return dataDeportista;
			}
		}
		
		public boolean actualizarUsuario(String Nickname, String nombre, String apellido, String correoElectronico, LocalDate fechaNacimiento ,Boolean esEntrenador, String sitioWeb, String disciplina,boolean esProfesional, String pass){
			Entrenador entrenador = null;
			Deportista deportista = null;
			if(esEntrenador) {
				entrenador = DB.getEntrenadorById(Nickname);
				entrenador.setNombre(nombre);
				entrenador.setApellido(apellido);
				entrenador.setCorreoElectronico(correoElectronico);
				entrenador.setFechaNacimiento(fechaNacimiento);
				entrenador.setSitioWeb(sitioWeb);
				entrenador.setDisciplina(disciplina);
				entrenador.setPass(pass);
			}else {
				deportista = DB.getDeportistaById(Nickname);
				deportista.setNombre(nombre);
				deportista.setApellido(apellido);
				deportista.setCorreoElectronico(correoElectronico);
				deportista.setFechaNacimiento(fechaNacimiento);
				deportista.setPass(pass);
				if (esProfesional) {
					deportista.setEsProfesional("Si");
				}else {
					deportista.setEsProfesional("No");
				}
					
			}
				
			
			return DB.actualizarUsuario(entrenador,deportista);
			
		}
		
		
		
		public boolean crearUsuario(String nombre, String apellido, String nickname, String correoElectronico, LocalDate fechaNacimiento, 
				String pass, boolean esDeportista, boolean esEntrenador, boolean esProfesional, String sitio, String disciplina) {
				if (esDeportista) {
					String profesional = esProfesional ? "Si" : "No";
					Deportista deportista = new Deportista(nickname, nombre, apellido, correoElectronico, fechaNacimiento, pass, profesional);
					return DB.saveDeportista(deportista);
				} else if (esEntrenador) {
					Entrenador entrenador = new Entrenador(nickname, nombre, apellido, correoElectronico, fechaNacimiento, pass, disciplina, sitio);
					return DB.saveEntrenador(entrenador);
				} else {
					// Este caso ya está cubierto por la validación anterior
					return false;
				}
			

			}
}
