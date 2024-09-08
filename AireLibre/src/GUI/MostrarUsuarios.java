package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import Clases.DataUsuario;
import Clases.DataEntrenador;
import Clases.DataDeportista;
import Clases.DataActividad;
import Clases.DataClase;
import Clases.DataInscripcion;
import Clases.IControladorUsuario;
import Clases.IControladorInscripcion;
import Clases.IControladorActividad;
import Clases.IControladorClase;



public class MostrarUsuarios extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable userTable;
    private JInternalFrame currentDetailFrame;
    private JInternalFrame currentDetailFrame2;
    private JInternalFrame currentDetailFrame3;
    private IControladorUsuario icontroladorUsuario;
    private IControladorInscripcion icontroladorInscripcion;
    private IControladorActividad icontroladorActividad;
    private IControladorClase icontroladorClase;
    JPanel activitiesPanel;
    DefaultTableModel activityModel;
	List<DataUsuario> usuarios;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public MostrarUsuarios(IControladorUsuario icontroladorUsu , IControladorInscripcion icontroladoInsc, IControladorActividad icontroladorActiv,IControladorClase icontroladorCla) {
    	icontroladorUsuario = icontroladorUsu;
    	icontroladorInscripcion = icontroladoInsc;
    	icontroladorActividad = icontroladorActiv;
    	icontroladorClase = icontroladorCla;
    	usuarios= icontroladorUsuario.listaUsuarios();
        setTitle("Mostrar Usuarios");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        setClosable(true);   
        setIconifiable(true); 
        setResizable(true); 
        setMaximizable(true); 

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        loadUserData();
        
        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = userTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    DataUsuario selectedUser = usuarios.get(userTable.convertRowIndexToModel(row));
                    showUserDetails(selectedUser);
                }
            }
        });

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (currentDetailFrame != null) {
                    try {
                        currentDetailFrame.setClosed(true);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }    
                if (currentDetailFrame2 != null) {
                    try {
                        currentDetailFrame2.setClosed(true);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }
                if (currentDetailFrame3 != null) {
                    try {
                        currentDetailFrame3.setClosed(true);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            
        });
    }

    private void loadUserData() {
        // Definir las columnas que se mostrarán en la tabla
        String[] columnNames = { "Nickname", "Nombre", "Apellido" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que todas las celdas no sean editables
            }
        };

        // Llenar la tabla solo con ID, Nombre y Apellido
        for (DataUsuario usuario :  usuarios) {
            Object[] rowData = {
                usuario.getNickname(),
                usuario.getNombre(),
                usuario.getApellido()
            };
            model.addRow(rowData);
        }

        userTable.setModel(model);
    }

    private void showUserDetails(DataUsuario user) {

        // Cerrar el frame anterior de detalles de usuario si existe
        if (currentDetailFrame != null) {
            currentDetailFrame.dispose();
        }

        if (currentDetailFrame2 != null) {
            currentDetailFrame2.dispose();
        }
        
        if (currentDetailFrame3 != null) {
            currentDetailFrame3.dispose();
        }

        // Crear un frame de detalles más pequeño
        JInternalFrame detailFrame = new JInternalFrame("Detalles del Usuario", true, true, true, true);
        detailFrame.setSize(400, 300); // Tamaño más pequeño para el frame de detalles
        detailFrame.setLocation(600, 30); // Ubicación ajustada

        // Crear un panel principal con layout de BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Crear un modelo de tabla no editable para mostrar los detalles del usuario
        String[] columnNames = { "Propiedad", "Valor" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que todas las celdas no sean editables
            }
        };

        // Agregar datos del usuario comunes
        model.addRow(new Object[]{"Nombre", user.getNombre()});
        model.addRow(new Object[]{"Apellido", user.getApellido()});
        model.addRow(new Object[]{"Nickname", user.getNickname()});
        model.addRow(new Object[]{"Correo Electrónico", user.getCorreoElectronico()});
        model.addRow(new Object[]{"Fecha de Nacimiento", user.getFechaNacimiento()});

        // Crear la tabla con el modelo
        JTable detailTable = new JTable(model);
        detailTable.setPreferredScrollableViewportSize(new Dimension(450, 150)); // Tamaño ajustado para la tabla
        JScrollPane detailScrollPane = new JScrollPane(detailTable);
        mainPanel.add(detailScrollPane, BorderLayout.NORTH);

        // Si el usuario es un entrenador, mostrar sus actividades
        DataEntrenador entrenador = null;
        DataDeportista deportista = null;
        entrenador = icontroladorUsuario.getEntrenador(user.getNickname());
        deportista = icontroladorUsuario.getDeportista(user.getNickname());
        
        if (entrenador!=null) {

            // Agregar detalles específicos del entrenador
            model.addRow(new Object[]{"Tipo", "Entrenador"});
            model.addRow(new Object[]{"Disciplina", entrenador.getDisciplina()});
            model.addRow(new Object[]{"Sitio Web", entrenador.getSitioWeb()});

            // Crear un panel para las actividades y agregarlo al panel principal
            JPanel activitiesPanel = createActivitiesPanel(entrenador);
            mainPanel.add(activitiesPanel, BorderLayout.CENTER);
        } else if (deportista!=null) {

            // Agregar detalles específicos del deportista
            model.addRow(new Object[]{"Tipo", "Deportista"});
            model.addRow(new Object[]{"Es Profesional", deportista.isEsProfesional()});

            // Crear un panel para las inscripciones
            JPanel inscripcionesPanel = new JPanel(new BorderLayout());
            mainPanel.add(inscripcionesPanel, BorderLayout.CENTER);

            // Agregar una etiqueta para las inscripciones
            JLabel inscripcionesLabel = new JLabel("Inscripciones");
            inscripcionesLabel.setFont(new Font("Arial", Font.BOLD, 12));
            inscripcionesPanel.add(inscripcionesLabel, BorderLayout.NORTH);

            // Cargar inscripciones para el deportista seleccionado
            List<DataInscripcion> inscripciones = icontroladorInscripcion.obtenerInscripcionDepor(deportista);
            String[] inscripcionesColumnNames = { "Fecha Inscripción", "Costo total", "Cantidad deportistas" };
            DefaultTableModel inscripcionesModel = new DefaultTableModel(inscripcionesColumnNames, 0) {
                private static final long serialVersionUID = 1L;
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Hace que todas las celdas no sean editables
                }
            };

            // Agregar datos de inscripciones
            for (DataInscripcion inscripcion : inscripciones) {
                Object[] inscripcionRowData = {
                    inscripcion.getFechaInsc().format(dateFormatter),  
                    inscripcion.getCosto(),
                    inscripcion.getCantidadDeportistas()
                };
                inscripcionesModel.addRow(inscripcionRowData);
            }

            // Crear y agregar la tabla de inscripciones al panel
            JTable inscripcionesTable = new JTable(inscripcionesModel);
            JScrollPane inscripcionesScrollPane = new JScrollPane(inscripcionesTable);
            inscripcionesPanel.add(inscripcionesScrollPane, BorderLayout.CENTER);
        }

        // Agregar el panel principal al frame y mostrar el frame
        detailFrame.add(mainPanel);
        detailFrame.pack(); // Ajustar el tamaño del frame al contenido
        detailFrame.setVisible(true); 


        detailFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        detailFrame.pack();
        detailTable.setPreferredScrollableViewportSize(new Dimension(400, 160)); // Ajuste del tamaño para que coincida con la actividad
        detailFrame.setVisible(true);

        // Añadir el InternalFrame al contenedor padre (JDesktopPane)
        JDesktopPane desktopPane = getDesktopPane(); 
        desktopPane.add(detailFrame);
        desktopPane.getDesktopManager().activateFrame(detailFrame);

        // Almacenar la referencia al nuevo frame
        currentDetailFrame = detailFrame;

        // Agregar InternalFrameListener para cerrar currentDetailFrame2 si existe
        detailFrame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (currentDetailFrame2 != null) {
                    try {
                        currentDetailFrame2.setClosed(true);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }
                if (currentDetailFrame3 != null) {
                    try {
                        currentDetailFrame3.setClosed(true);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }



    private JPanel createActivitiesPanel(DataEntrenador entrenador) {
        // Crear un panel para la tabla de actividades
        activitiesPanel = new JPanel(new BorderLayout());
        
        JLabel ActividadLabel = new JLabel("Actividades del entrenador:");
        ActividadLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Smaller font for the label
        activitiesPanel.add(ActividadLabel, BorderLayout.NORTH);

        // Crear el modelo de tabla y la tabla para actividades
        String[] columnNames = { "Nombre"};
        activityModel = new DefaultTableModel(columnNames, 0) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cargardatosActividad(entrenador);
        return activitiesPanel;
    }
    
    public void cargardatosActividad(DataEntrenador entrenador) {
    
        // Obtener las actividades del entrenador y llenar la tabla
        List<DataActividad> actividades = icontroladorActividad.transformarActividadesEntrenador(icontroladorUsuario.getEntrenador(entrenador.getNickname()));
        for (DataActividad actividad : actividades) {
            activityModel.addRow(new Object[]{
                actividad.getNombre()
            });
        }

        JTable activityTable = new JTable(activityModel);

        // Crear JScrollPane y establecer dimensiones preferidas
        JScrollPane activityScrollPane = new JScrollPane(activityTable);
        Dimension scrollPaneSize = new Dimension(450, 220); // Ajuste de tamaño para la altura (100)
        activityScrollPane.setPreferredSize(scrollPaneSize); // Establece la altura preferida

        activitiesPanel.add(activityScrollPane, BorderLayout.CENTER);

        // Agregar MouseListener para detectar clics en las filas de la tabla de actividades
        activityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = activityTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    // Obtener la actividad seleccionada
                    DataActividad actividadSeleccionada = actividades.get(activityTable.convertRowIndexToModel(row));
                    showActivityDetails(actividadSeleccionada);
                }
            }
        });
    }
    
    private void showActivityDetails(DataActividad actividad) {
    	
        // Cerrar el frame anterior si existe
        if (currentDetailFrame2 != null) {
            currentDetailFrame2.dispose();
        }
        
        if (currentDetailFrame3 != null) {
            currentDetailFrame3.dispose();
        }
        

        // Crear un frame de detalles más pequeño
        JInternalFrame detailFrame = new JInternalFrame("Detalles de la Actividad", true, true, true, true);
        detailFrame.setSize(400, 300); // Tamaño más pequeño para el frame de detalles
        detailFrame.setLocation(600, 30); // Ubicación ajustada

        // Crear un panel para contener los detalles y clases
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BorderLayout());
        detailFrame.getContentPane().add(detailPanel, BorderLayout.CENTER);

        // Crear un modelo de tabla no editable para los detalles de la actividad
        String[] detailColumnNames = { "Propiedad", "Valor" };
        DefaultTableModel detailModel = new DefaultTableModel(detailColumnNames, 0) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que todas las celdas no sean editables
            }
        };

        // Crear un DateTimeFormatter para formatear LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Agregar datos de la actividad
        detailModel.addRow(new Object[]{"Nombre", actividad.getNombre()});
        detailModel.addRow(new Object[]{"Descripción", actividad.getDescripcion()});
        detailModel.addRow(new Object[]{"Duración", actividad.getDuracion().toHours() + "h " + (actividad.getDuracion().toMinutes() % 60) + "m"}); // Formato de duración
        detailModel.addRow(new Object[]{"Costo", actividad.getCosto()});
        detailModel.addRow(new Object[]{"Fecha de Alta", actividad.getFechaAlta().format(formatter)});
        String estadoTexto = actividad.getEstado() ? "Activo" : "Inactivo";
        detailModel.addRow(new Object[]{"Estado", estadoTexto});

        // Crear la tabla con el modelo
        JTable detailTable = new JTable(detailModel);
        detailTable.setPreferredScrollableViewportSize(new Dimension(400, 120));
        JScrollPane detailScrollPane = new JScrollPane(detailTable);
        detailPanel.add(detailScrollPane, BorderLayout.NORTH);

        // Crear un panel para la información de clases
        JPanel classPanel = new JPanel();
        classPanel.setLayout(new BorderLayout());
        detailPanel.add(classPanel, BorderLayout.CENTER);

        // Agregar una etiqueta para la información de clases
        JLabel classLabel = new JLabel("Información de Clases");
        classLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Fuente más pequeña para la etiqueta
        classPanel.add(classLabel, BorderLayout.NORTH);

        // Cargar clases para la actividad seleccionada
        List<DataClase> clases = icontroladorActividad.transformarClasesActividad(actividad);
        String[] classColumnNames = { "Fecha", "Hora" };
        DefaultTableModel classModel = new DefaultTableModel(classColumnNames, 0) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que todas las celdas no sean editables
            }
        };
        
        Collections.sort(clases, new Comparator<DataClase>() {
            @Override
            public int compare(DataClase c1, DataClase c2) {
                // Combinar fecha y hora en LocalDateTime para una comparación precisa
                LocalDateTime dateTime1 = LocalDateTime.of(c1.getFecha(), c1.getHoraEntrenamiento());
                LocalDateTime dateTime2 = LocalDateTime.of(c2.getFecha(), c2.getHoraEntrenamiento());
                return dateTime1.compareTo(dateTime2);
            }
        });

        // Agregar datos de las clases
        for (DataClase clase : clases) {
            Object[] classRowData = {
                clase.getFecha().format(formatter), // Convertir LocalDate a String
                clase.getFormattedHoraEntrenamiento(), 
            };
            classModel.addRow(classRowData);
        }

        // Crear la tabla con el modelo
        JTable classTable = new JTable(classModel);
        JScrollPane classScrollPane = new JScrollPane(classTable);
        classPanel.add(classScrollPane, BorderLayout.CENTER);

        // Agregar un MouseListener a la tabla de clases
        classTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = classTable.rowAtPoint(e.getPoint());
                if (row >= 0 && e.getClickCount() == 1) {
                    DataClase selectedClass = clases.get(row);
                    showClaseDetails(selectedClass, actividad);
                }
            }
        });

        detailFrame.pack();
        classTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
        detailFrame.setVisible(true);

        // Agregar el InternalFrame al contenedor padre (JDesktopPane)
        JDesktopPane desktopPane = getDesktopPane(); 
        desktopPane.add(detailFrame);
        desktopPane.getDesktopManager().activateFrame(detailFrame);

        // Almacenar la referencia al nuevo frame
        currentDetailFrame2 = detailFrame;
        
        detailFrame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if (currentDetailFrame3 != null) {
                    try {
                        currentDetailFrame3.setClosed(true);
                    } catch (PropertyVetoException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void showClaseDetails(DataClase clase, DataActividad actividad) {
        if (currentDetailFrame3 != null) {
            currentDetailFrame3.dispose();
        	currentDetailFrame3=null;
        }

      
        JInternalFrame detailFrame = new JInternalFrame("Detalles de la Clase", true, true, true, true);
        detailFrame.setSize(500, 400);  
        detailFrame.setLocation(600, 30); 


        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailFrame.getContentPane().add(detailPanel);

        String[] detailColumnNames = { "Propiedad", "Valor" };
        DefaultTableModel detailModel = new DefaultTableModel(detailColumnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };


        detailModel.addRow(new Object[]{"Fecha", clase.getFecha().format(dateFormatter)});
        detailModel.addRow(new Object[]{"Hora", clase.getFormattedHoraEntrenamiento()});
        detailModel.addRow(new Object[]{"Fecha Alta", clase.getFechaAlta().format(dateFormatter)});  
        detailModel.addRow(new Object[]{"Cupos", clase.getCupo()});
        detailModel.addRow(new Object[]{"Nombre Entrenador", icontroladorActividad.getEntrenadorByActId(actividad.getNombre()).getNombre()});
        detailModel.addRow(new Object[]{"Apellido Entrenador", icontroladorActividad.getEntrenadorByActId(actividad.getNombre()).getApellido()});

        JTable detailTable = new JTable(detailModel);
        detailTable.setPreferredScrollableViewportSize(new Dimension(480, 100));
        JScrollPane detailScrollPane = new JScrollPane(detailTable);
        detailPanel.add(detailScrollPane);

        JPanel inscripcionesPanel = new JPanel();
        inscripcionesPanel.setLayout(new BorderLayout());
        detailPanel.add(inscripcionesPanel);

        JLabel inscripcionesLabel = new JLabel("Inscripciones");
        inscripcionesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        inscripcionesPanel.add(inscripcionesLabel, BorderLayout.NORTH);

        List<DataInscripcion> inscripciones = icontroladorClase.inscripcionesPorClase(clase.getId());
        String[] inscripcionesColumnNames = { "Nickname", "Nombre", "Apellido", "Fecha Inscripción", "Costo total", "Cantidad deportistas" };
        DefaultTableModel inscripcionesModel = new DefaultTableModel(inscripcionesColumnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (DataInscripcion inscripcion : inscripciones) {
            Object[] inscripcionRowData = {
                inscripcion.getDeportista().getNickname(),
                inscripcion.getDeportista().getNombre(),
                inscripcion.getDeportista().getApellido(),
                inscripcion.getFechaInsc().format(dateFormatter),  
                inscripcion.getCosto(),
                inscripcion.getCantidadDeportistas()
            };
            inscripcionesModel.addRow(inscripcionRowData);
        }

        JTable inscripcionesTable = new JTable(inscripcionesModel);
        inscripcionesTable.setPreferredScrollableViewportSize(new Dimension(480, 150));
        JScrollPane inscripcionesScrollPane = new JScrollPane(inscripcionesTable);
        inscripcionesPanel.add(inscripcionesScrollPane, BorderLayout.CENTER);

        detailFrame.setVisible(true);

        JDesktopPane desktopPane = getDesktopPane();
        desktopPane.add(detailFrame);
        desktopPane.getDesktopManager().activateFrame(detailFrame);

        currentDetailFrame3 = detailFrame;

    }
    





}
