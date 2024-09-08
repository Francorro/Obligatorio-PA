package Clases;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import jakarta.persistence.TypedQuery;

public class DB {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("AireLibrePU");

    public static boolean saveUser(Usuario usuario) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AireLibrePU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(usuario);
            tx.commit();
            return true; // Indica que el usuario fue guardado correctamente
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // Para depuración
            return false; // Indica que hubo un error al guardar el usuario
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            emf.close();
        }
    }

    public static List<Usuario> obtenerUsuarios() {
        EntityManager em = emf.createEntityManager();
        List<Usuario> usuarios = null;
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            usuarios = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return usuarios;
    }
    
    public static List<Actividad> obtenerActividades() {
        EntityManager em = emf.createEntityManager();
        List<Actividad> actividades = null;
        try {
            TypedQuery<Actividad> query = em.createQuery("SELECT u FROM Actividad u", Actividad.class);
            actividades = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return actividades;
    }


	public static boolean saveDeportista(Deportista deportista) {
	    EntityManagerFactory emf = Persistence.createEntityManagerFactory("AireLibrePU");
	    EntityManager em = emf.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        em.persist(deportista);
	        tx.commit();
	        return true; // Indica que el deportista fue guardado correctamente
	    } catch (Exception e) {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        e.printStackTrace(); // Para depuración
	        return false; // Indica que hubo un error al guardar el deportista
	    } finally {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	        emf.close();
	    }
	}


	public static boolean saveEntrenador(Entrenador entrenador) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AireLibrePU");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(entrenador);
			tx.commit();
			return true; // Indica que el entrenador fue guardado correctamente
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace(); // Para depuración
			return false; // Indica que hubo un error al guardar el entrenador
		} finally {
			if (em != null && em.isOpen()) {
	            em.close();
	        }
			emf.close();
		}
	}
	
	
	public static boolean saveActividad(Actividad actividad, Entrenador entrenador) {
	    EntityManager em = emf.createEntityManager();
	    EntityTransaction tx = em.getTransaction();
	    try {
	        tx.begin();
	        
	            entrenador.addActividad(actividad);
	            // Persistir la actividad
	            em.persist(actividad);
	            // Actualizar el entrenador en la base de datos
	            em.merge(entrenador);

	        tx.commit();
	        return true; // Indica que la actividad y el entrenador fueron guardados correctamente
	    } catch (Exception e) {
	        if (tx.isActive()) {
	            tx.rollback();
	        }
	        e.printStackTrace(); // Para depuración
	        return false; // Indica que hubo un error al guardar la actividad
	    } finally {
	        if (em != null && em.isOpen()) {
	            em.close();
	        }
	    }
	}

    
    
    
    public static List<Entrenador> obtenerEntrenadores() {
        EntityManager em = emf.createEntityManager();
        List<Entrenador> entrenadores = null;
        try {
            TypedQuery<Entrenador> query = em.createQuery("SELECT e FROM Entrenador e", Entrenador.class);
            entrenadores = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return entrenadores;
    }
    
    public static Entrenador getEntrenadorById(String id) {
        EntityManager em = emf.createEntityManager();
        Entrenador entrenador = null;
        try {
            entrenador = em.find(Entrenador.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return entrenador;
    }

    
    public static Actividad getActividadById(String nombre) {
        EntityManager em = emf.createEntityManager();
        Actividad actividad = null;
        try {
            actividad = em.find(Actividad.class, nombre);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return actividad;
    }
    
    
    public static Clase getClaseById(Long id) {
        EntityManager em = emf.createEntityManager();
        Clase clase= null;
        try {
            clase = em.find(Clase.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return clase;
    }
    
	public static boolean saveClase(Clase clase) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("AireLibrePU");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(clase);
			tx.commit();
			return true; // Indica que el entrenador fue guardado correctamente
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace(); // Para depuración
			return false; // Indica que hubo un error al guardar el entrenador
		} finally {
			if (em != null && em.isOpen()) {
	            em.close();
	        }
			emf.close();
		}
	}
	
    public static boolean saveInscripcion(Inscripcion inscripcion, Clase clase, Deportista deportista) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Persistir la inscripción
            
            em.persist(inscripcion);
            em.merge(clase);
            em.merge(deportista);

            tx.commit();
            return true; // Indica que la inscripción fue guardada correctamente
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // Para depuración
            return false; // Indica que hubo un error al guardar la inscripción
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    public static Inscripcion getInscripcionById(long id) {
        EntityManager em = emf.createEntityManager();
        Inscripcion inscripcion = null;
        try {
           inscripcion = em.find(Inscripcion.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return inscripcion;
    }
    
    public static Deportista getDeportistaById(String Nickname) {
        EntityManager em = emf.createEntityManager();
        Deportista deportista = null;
        try {
           deportista = em.find(Deportista.class, Nickname);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return deportista;
    }
    
	
    public static List<Clase> obtenerClasesPorActividad(Actividad actividad) {
        EntityManager em = emf.createEntityManager();
        List<Clase> clases = null;
        try {
            TypedQuery<Clase> query = em.createQuery(
                "SELECT c FROM Clase c WHERE c.actividad = :actividad", Clase.class);
            query.setParameter("actividad", actividad);
            clases = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return clases;
    }
    
    public static boolean existeActividad(String nombre) {
        EntityManager em = null;
        boolean existe = false;

        try {
            // Obtener el EntityManager
            em = emf.createEntityManager();

            // Crear la consulta para verificar si existe la actividad
            String jpql = "SELECT COUNT(a) FROM Actividad a WHERE a.nombre = :nombre";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("nombre", nombre);

            // Ejecutar la consulta y obtener el resultado
            Long count = query.getSingleResult();

            // Si el conteo es mayor que 0, la actividad existe
            existe = (count > 0);
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); // Cerrar el EntityManager
            }
        }

        return existe;
    }
    
    public static List<Deportista> obtenerDeportistas() {
        EntityManager em = emf.createEntityManager();
        List<Deportista> deportistas = null;
        try {
            TypedQuery<Deportista> query = em.createQuery("SELECT d FROM Deportista d", Deportista.class);
            deportistas = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return deportistas;
    }

    
    public static List<Inscripcion> obtenerInscripcionesPorClase(Clase clase) {
        EntityManager em = emf.createEntityManager();
        List<Inscripcion> inscripciones = null;
        try {
            TypedQuery<Inscripcion> query = em.createQuery(
                "SELECT i FROM Inscripcion i WHERE i.clase = :clase", Inscripcion.class);
            query.setParameter("clase", clase);
            inscripciones = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return inscripciones;
    }
    public static boolean actualizarUsuario(Entrenador entrenador, Deportista deportista) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (entrenador!=null) {
            	em.merge(entrenador);
            }else {
            	em.merge(deportista);
            }
            	
            tx.commit();
            return true; // Indica que el usuario se actualizo correctamente
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // Para depuración
            return false; // Indica que hubo un error al actualizar el usuario
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    public static boolean actualizarActividad(Actividad actividad, Entrenador entrenador, Entrenador antiguo) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(actividad); // Actualiza la entidad existente en la base de datos
            em.merge(antiguo);
            em.merge(entrenador);
            tx.commit();
            reiniciarBaseDeDatos();
            return true; // Indica que la actividad fue actualizada correctamente
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // Para depuración
            return false; // Indica que hubo un error al actualizar la actividad
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    public static boolean reiniciarBaseDeDatos() {
        try {
            // Cerrar el EntityManagerFactory actual
            if (emf != null && emf.isOpen()) {
                emf.close();
            }

            // Esperar un momento para asegurar que el EntityManagerFactory se ha cerrado completamente
            Thread.sleep(1000); // Ajusta este tiempo si es necesario

            // Volver a crear el EntityManagerFactory
            emf = Persistence.createEntityManagerFactory("AireLibrePU");

            return true; // Indica que la base de datos se reinició correctamente
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Indica que hubo un error al reiniciar la base de datos
        }
    }
    
    
    
    public static boolean actualizarClaseActividad(Actividad actividad) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(actividad);
            tx.commit();
            return true; // Indica que la actividad fue actualizada correctamente
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace(); // Para depuración
            return false; // Indica que hubo un error al actualizar la actividad
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    
    public static List<Inscripcion> obtenerInscripciones(Deportista deportista) {
        EntityManager em = emf.createEntityManager();
        List<Inscripcion> inscripciones = null;
        try {
            TypedQuery<Inscripcion> query = em.createQuery(
                "SELECT i FROM Inscripcion i WHERE i.deportista = :deportista", Inscripcion.class);
            query.setParameter("deportista", deportista);
            // Ejecutar la consulta y obtener la lista de inscripciones
            inscripciones = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); // Cerrar el EntityManager
            }
        }
        return inscripciones; // Devolver la lista de inscripciones
    }

    
}   
 

