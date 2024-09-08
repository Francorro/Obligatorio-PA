package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Collections;
import java.time.LocalDateTime;

import Clases.IControladorInscripcion;
import Clases.IControladorActividad;
import Clases.IControladorUsuario;
import Clases.DataActividad;
import Clases.DataClase;
import Clases.DataDeportista;

public class Inscripciones extends JInternalFrame {
    private JComboBox<DataActividad> comboActividad;
    private JComboBox<DataDeportista> comboDeportista;
    private JComboBox<DataClase> comboClase; 
    private static final long serialVersionUID = 1L;
    private JSpinner spinnerCantidad; 
    private JLabel lblCostoTotal; 
    private IControladorInscripcion icontroladorInscripcion;
    private IControladorActividad icontroladorActividad;
    private IControladorUsuario icontroladorUsuario;
    
    public Inscripciones(IControladorInscripcion icontroladorInscrip, IControladorActividad icontroladorActiv, IControladorUsuario icontroladorUsu) {
    	icontroladorInscripcion= icontroladorInscrip;
    	icontroladorActividad= icontroladorActiv;
    	icontroladorUsuario = icontroladorUsu;
    	getContentPane().setBackground(Color.LIGHT_GRAY);
        // Configurar el InternalFrame
        setTitle("Gestión de Inscripciones");
        setClosable(true);   
        setIconifiable(true);
        setResizable(true); 
        setMaximizable(true); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 350); // Ajustar tamaño para incluir el nuevo campo
        getContentPane().setLayout(null);

        JLabel lblActividad = new JLabel("Actividad:");
        lblActividad.setBounds(30, 23, 100, 20);
        getContentPane().add(lblActividad);

        comboActividad = new JComboBox<>();
        comboActividad.setBounds(150, 23, 234, 20);
        getContentPane().add(comboActividad);

        JLabel lblDeportista = new JLabel("Deportista:");
        lblDeportista.setBounds(30, 81, 100, 20);
        getContentPane().add(lblDeportista);

        comboDeportista = new JComboBox<>();
        comboDeportista.setBounds(150, 81, 234, 20);
        getContentPane().add(comboDeportista);

        JLabel lblClase = new JLabel("Clase:");
        lblClase.setBounds(30, 139, 100, 20);
        getContentPane().add(lblClase);

        comboClase = new JComboBox<>();
        comboClase.setBounds(150, 139, 234, 20);
        getContentPane().add(comboClase);

        
        JLabel lblCantidad = new JLabel("Cantidad deportistas:");
        lblCantidad.setBounds(30, 176, 121, 20);
        getContentPane().add(lblCantidad);
        
        SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1); // valor inicial, valor mínimo, valor máximo, incremento
        spinnerCantidad = new JSpinner(model);
        spinnerCantidad.setBounds(150, 176, 100, 20);
        getContentPane().add(spinnerCantidad);
        
        JButton btnGuardar = new JButton("Guardar Inscripción");
        btnGuardar.setBounds(51, 260, 153, 30);
        getContentPane().add(btnGuardar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBounds(221, 260, 163, 30);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(e -> cancelar());
        
        JLabel lblHora = new JLabel("Hora");
        lblHora.setBounds(255, 123, 65, 20);
        getContentPane().add(lblHora);
        
        JLabel lblCupos = new JLabel("Cupos");
        lblCupos.setBounds(330, 123, 65, 20);
        getContentPane().add(lblCupos);
        
        JLabel lblFecha = new JLabel("Fecha");
        lblFecha.setBounds(163, 123, 65, 20);
        getContentPane().add(lblFecha);
        
        lblCostoTotal = new JLabel("Costo total: $0");
        lblCostoTotal.setBounds(30, 215, 400, 20);
        getContentPane().add(lblCostoTotal);

        // Cargar actividades, deportistas y clases
        cargarActividades();
        cargarDeportistas();
        
        comboActividad.addActionListener(e -> {
            DataActividad actividadSeleccionada = (DataActividad) comboActividad.getSelectedItem();
            if (actividadSeleccionada != null) {
                cargarClases(actividadSeleccionada);
                actualizarCostoTotal();
            }
        });
        
        
        comboClase.addActionListener(e -> {
            actualizarCostoTotal(); // Actualizar el costo total al cambiar la clase
        });

        spinnerCantidad.addChangeListener(e -> {
            actualizarCostoTotal(); // Actualizar el costo total al cambiar la cantidad
        });

        btnGuardar.addActionListener(e -> {
            DataDeportista deportista = (DataDeportista) comboDeportista.getSelectedItem();
            DataClase clase = (DataClase) comboClase.getSelectedItem(); // Obtener la clase seleccionada

            if (deportista != null && clase != null) {
                int cantidad = (Integer) spinnerCantidad.getValue();
                
                // Validar si la cantidad excede los cupos disponibles
                if (cantidad > clase.getCupo()) {
                    JOptionPane.showMessageDialog(this, "La cantidad de deportistas excede los cupos disponibles en la clase.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Salir del método para evitar guardar la inscripción
                }
                
                DataActividad actividadSeleccionada = (DataActividad) comboActividad.getSelectedItem();
                
              
                
                if (icontroladorInscripcion.guardarInscripcion(deportista,clase, cantidad, actividadSeleccionada)) {
                    JOptionPane.showMessageDialog(this, "Inscripción guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Limpiar los campos en lugar de cerrar el JInternalFrame
                    comboDeportista.setSelectedIndex(-1);
                    comboClase.setSelectedIndex(-1);
                    comboActividad.setSelectedIndex(-1);
                    spinnerCantidad.setValue(1); // Resetear el spinner
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar la inscripción.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione Actividad, Deportista y Clase.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });

    }

    private void cargarActividades() {
        // Obtener la lista de actividades desde la base de datos
        List<DataActividad> actividades = icontroladorActividad.transformarActividades();
        DefaultComboBoxModel<DataActividad> model = new DefaultComboBoxModel<>();
        
        model.removeAllElements();
        model.addElement(null);
        
        for (DataActividad actividad : actividades) {
            model.addElement(actividad);
        }
        
        comboActividad.setModel(model);
        comboActividad.setRenderer(new ActividadRenderer());
        comboActividad.setSelectedItem(null);
    }

    private void cargarDeportistas() {
        // Obtener la lista de deportistas desde la base de datos
        List<DataDeportista> deportistas = icontroladorUsuario.listaDeportistas();
        DefaultComboBoxModel<DataDeportista> model = new DefaultComboBoxModel<>();
        
        model.removeAllElements();
        model.addElement(null);
        
        for (DataDeportista deportista : deportistas) {
            model.addElement(deportista);
        }
        
        comboDeportista.setModel(model);
        comboDeportista.setRenderer(new DeportistaRenderer());
        comboDeportista.setSelectedItem(null);
    }

    private void cargarClases(DataActividad actividad) {
        // Obtener la lista de clases desde la base de datos
        List<DataClase> clases = icontroladorActividad.transformarClasesActivasActividad(actividad);
        if (clases.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clases disponibles para esta actividad.", "Información", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar el JComboBox si no hay clases
            comboClase.removeAllItems();
            return;
        }
        
        Collections.sort(clases, new Comparator<DataClase>() {
            @Override
            public int compare(DataClase c1, DataClase c2) {
                // Combinar fecha y hora en LocalDateTime para una comparación precisa
                LocalDateTime dateTime1 = LocalDateTime.of(c1.getFecha(), c1.getHoraEntrenamiento());
                LocalDateTime dateTime2 = LocalDateTime.of(c2.getFecha(), c2.getHoraEntrenamiento());
                return dateTime1.compareTo(dateTime2);
            }
        });
        
        DefaultComboBoxModel<DataClase> model = new DefaultComboBoxModel<>();
        
        model.removeAllElements();
        model.addElement(null);
        
        for (DataClase clase : clases) {
            model.addElement(clase);
        }
        
        comboClase.setModel(model);
        comboClase.setRenderer(new ClaseRenderer());
        comboClase.setSelectedItem(null);
    }

    private static class ActividadRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value == null) {
                setText("");
            } else if (value instanceof DataActividad) {
                DataActividad actividad = (DataActividad) value;
                setText(actividad.getNombre() + " (" + actividad.getDuracion().toHours() + "h " + (actividad.getDuracion().toMinutes() % 60) + "m)" + " $" + actividad.getCosto());
            }

            // Ajustar el espaciado y el alineamiento
            setHorizontalAlignment(SwingConstants.LEFT);

            return this;
        }
    }

    private static class DeportistaRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value == null) {
                setText("");
            } else if (value instanceof DataDeportista) {
                DataDeportista deportista = (DataDeportista) value;
                setText(deportista.getNickname() + " - " +deportista.getNombre() + " " + deportista.getApellido());
            }

            // Ajustar el espaciado y el alineamiento
            setHorizontalAlignment(SwingConstants.LEFT);

            return this;
        }
    }

    private static class ClaseRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value == null) {
                setText("");
            } else if (value instanceof DataClase) {
                DataClase clase = (DataClase) value;

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                String fechaStr = clase.getFecha().format(dateFormatter);
                String horaStr = clase.getHoraEntrenamiento().format(timeFormatter);

                setText(fechaStr + "      -       " + horaStr + "        -        " + clase.getCupo());
            }

            // Ajustar el espaciado y el alineamiento
            setHorizontalAlignment(SwingConstants.LEFT);

            return this;
        }
    }
    private void actualizarCostoTotal() {
        DataActividad actividadSeleccionada = (DataActividad) comboActividad.getSelectedItem();
        DataClase claseSeleccionada = (DataClase) comboClase.getSelectedItem();
        int cantidad = (Integer) spinnerCantidad.getValue();

        if (actividadSeleccionada != null && claseSeleccionada != null) {
            double costoTotal = actividadSeleccionada.getCosto() * cantidad;
            lblCostoTotal.setText("Costo total: $" + costoTotal);
        } else {
            lblCostoTotal.setText("Costo total: $0");
        }
    }
    
    // Método para cerrar la ventana al cancelar
    private void cancelar() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea cancelar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose(); // Cierra la ventana actual
        }
    }
    
    
    
}



