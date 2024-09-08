package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Clases.IControladorActividad;
import Clases.IControladorClase;
import Clases.DataInscripcion;
import Clases.DataActividad;
import Clases.DataClase;
import Clases.DataEntrenador;

public class consulta_Actividad extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable activityTable;
    private List<DataActividad> actividades;
    private JInternalFrame currentDetailFrame;
    private JInternalFrame currentDetailFrame2;
    private IControladorActividad icontroladorActividad;
    private IControladorClase icontroladorClase;


    public consulta_Actividad(IControladorActividad icontroladroAct, IControladorClase icontroladorCla) {
    	icontroladorActividad = icontroladroAct;
    	icontroladorClase = icontroladorCla;
        actividades = icontroladorActividad.transformarActividades();
        setTitle("Mostrar Actividades");
        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setClosable(true);   
        setIconifiable(true); 
        setResizable(true); 
        setMaximizable(true); 

        
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

       
        activityTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(activityTable);
        panel.add(scrollPane, BorderLayout.CENTER);

 
        loadActivityData();
        
  
        activityTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = activityTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    DataActividad selectedActividad = actividades.get(activityTable.convertRowIndexToModel(row));
                    showActivityDetails(selectedActividad);
                }
            }
        });


        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                closeDetailFrames();
            }
        });
    }

    private void loadActivityData() {
        String[] columnNames = { "Nombre" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        for (DataActividad actividad : actividades) {
            Object[] rowData = {
                actividad.getNombre(),
            };
            model.addRow(rowData);
        }

        activityTable.setModel(model);
    }

    private void showActivityDetails(DataActividad actividad) {
        if (currentDetailFrame != null) {
            currentDetailFrame.dispose();
        }
        
        if (currentDetailFrame2 != null) {
            currentDetailFrame2.dispose();
        }


        JInternalFrame detailFrame = new JInternalFrame("Detalles de la Actividad", true, true, true, true);
        detailFrame.setSize(400, 300); 
        detailFrame.setLocation(600, 30); 

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BorderLayout());
        detailFrame.getContentPane().add(detailPanel, BorderLayout.CENTER);

        String[] detailColumnNames = { "Propiedad", "Valor" };
        DefaultTableModel detailModel = new DefaultTableModel(detailColumnNames, 0) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        detailModel.addRow(new Object[]{"Nombre", actividad.getNombre()});
        detailModel.addRow(new Object[]{"Descripción", actividad.getDescripcion()});
        detailModel.addRow(new Object[]{"Duración", actividad.getDuracion().toHours() + "h " + (actividad.getDuracion().toMinutes() % 60) + "m",}); // Formato de duración
        detailModel.addRow(new Object[]{"Costo", actividad.getCosto()});
        detailModel.addRow(new Object[]{"Fecha de Alta", actividad.getFechaAlta().format(formatter)});
        String estadoTexto = actividad.getEstado() ? "Activo" : "Inactivo";
        detailModel.addRow(new Object[]{"Estado", estadoTexto});

        JTable detailTable = new JTable(detailModel);
        detailTable.setPreferredScrollableViewportSize(new Dimension(400, 120));
        JScrollPane detailScrollPane = new JScrollPane(detailTable);
        detailPanel.add(detailScrollPane, BorderLayout.NORTH);

        JPanel classPanel = new JPanel();
        classPanel.setLayout(new BorderLayout());
        detailPanel.add(classPanel, BorderLayout.CENTER);

        JLabel classLabel = new JLabel("Información de Clases");
        classLabel.setFont(new Font("Arial", Font.BOLD, 12)); // Smaller font for the label
        classPanel.add(classLabel, BorderLayout.NORTH);

        List<DataClase> clases = icontroladorActividad.transformarClasesActividad(actividad);
        String[] classColumnNames = { "Fecha", "Hora" };
        DefaultTableModel classModel = new DefaultTableModel(classColumnNames, 0) {
        	private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        for (DataClase clase : clases) {
            Object[] classRowData = {
                clase.getFecha().format(formatter), 
                clase.getFormattedHoraEntrenamiento()    
            };
            classModel.addRow(classRowData);
        }

        JTable classTable = new JTable(classModel);
        classTable.setPreferredScrollableViewportSize(new Dimension(400, 300));
        JScrollPane classScrollPane = new JScrollPane(classTable);
        classPanel.add(classScrollPane, BorderLayout.CENTER);

        classTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = classTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    DataClase selectedClase = clases.get(classTable.convertRowIndexToModel(row));
                    showClaseDetails(selectedClase, actividad);
                }
            }
        });

        detailFrame.pack();
        detailFrame.setVisible(true);

        JDesktopPane desktopPane = getDesktopPane(); 
        desktopPane.add(detailFrame);
        desktopPane.getDesktopManager().activateFrame(detailFrame);

        currentDetailFrame = detailFrame;
        
        detailFrame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                closeDetailFrames();
            }
        });
    }

    private void showClaseDetails(DataClase clase, DataActividad actividad) {
        if (currentDetailFrame2 != null) {
            currentDetailFrame2.dispose();
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

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        DataEntrenador dataEntrenador=icontroladorActividad.getEntrenadorByActId(actividad.getNombre());
        detailModel.addRow(new Object[]{"Fecha", clase.getFecha().format(dateFormatter)});
        detailModel.addRow(new Object[]{"Hora", clase.getFormattedHoraEntrenamiento()});
        detailModel.addRow(new Object[]{"Fecha Alta", clase.getFechaAlta().format(dateFormatter)});  
        detailModel.addRow(new Object[]{"Cupos", clase.getCupo()});
        detailModel.addRow(new Object[]{"Nombre Entrenador", dataEntrenador.getNombre()});
        detailModel.addRow(new Object[]{"Apellido Entrenador", dataEntrenador.getApellido()});

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

        currentDetailFrame2 = detailFrame;
    }




    private void closeDetailFrames() {
        if (currentDetailFrame != null) {
            currentDetailFrame.dispose();
            currentDetailFrame = null;
        }
        if (currentDetailFrame2 != null) {
            currentDetailFrame2.dispose();
            currentDetailFrame2 = null;
        }
    }
}
