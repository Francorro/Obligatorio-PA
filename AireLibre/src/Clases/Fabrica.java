package Clases;

public class Fabrica {
	
	private static Fabrica instance;

    private Fabrica() {
    }

    public static Fabrica getInstance() {
        if (instance == null) {
            instance = new Fabrica();
        }
        return instance;
    }
    
    public IControladorActividad getIControladorActividad(){
    	return ControladorActividad.getInstancia();
    }
    
    public IControladorUsuario getIControladorUsuario(){
    	return ControladorUsuario.getInstancia();
    }
    
    public IControladorClase getIControladorClase(){
    	return ControladorClase.getInstancia();
    }
    
    public IControladorInscripcion getIControladorInscripcion(){
    	return ControladorInscripcion.getInstancia();
    }
    
    
    
}
