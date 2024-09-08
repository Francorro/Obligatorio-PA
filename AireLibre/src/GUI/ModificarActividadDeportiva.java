package GUI;

import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.time.Duration;

import Clases.DataActividad;
import Clases.DataEntrenador;
import Clases.IControladorActividad;
import Clases.IControladorUsuario;

import java.awt.Color;

public class ModificarActividadDeportiva extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable TablaActividades;
    private JTextField txtNombre_Actividad;
    private JTextArea txtDescripcion;
    private JSpinner spinnerDuracionHoras;
    private JSpinner spinnerDuracionMinutos;
    private JTextField txtCosto;
    private JComboBox<String> comboBoxEntrenadores;
    private DataActividad actividadSeleccionada; // Actividad actualmente seleccionada
    private IControladorActividad icontroladorActividad;
    private IControladorUsuario icontroladorUsuario;
    DataEntrenador antiguo;
    

    public ModificarActividadDeportiva(IControladorActividad icontroladorActiv, IControladorUsuario icontroladorUsu) {
    	icontroladorActividad = icontroladorActiv;
    	icontroladorUsuario = icontroladorUsu;
        // Configuración de JInternalFrame
        setClosable(true);  
        setIconifiable(true); 
        setResizable(true);
        setMaximizable(true); 
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().setLayout(null);

        // Título del marco
        JLabel lblNewLabel = new JLabel("Modificar actividad deportiva");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(226, 11, 325, 25);
        getContentPane().add(lblNewLabel);

        // Tabla de actividades
        TablaActividades = new JTable();
        JScrollPane scrollPane = new JScrollPane(TablaActividades);
        scrollPane.setBounds(10, 118, 563, 364);
        getContentPane().add(scrollPane);

        TablaActividades.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = TablaActividades.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String nombreActividad = (String) TablaActividades.getValueAt(filaSeleccionada, 0);
                    actividadSeleccionada =  icontroladorActividad.getDataFromActividad(nombreActividad); // Obtener la actividad seleccionada
                    if (actividadSeleccionada != null) {
                        cargarDatosActividad();
                    }
                }
            }
        });

        JLabel lblNewLabel_1 = new JLabel("Entrenador de la actividad:");
        lblNewLabel_1.setBounds(497, 56, 206, 25);
        getContentPane().add(lblNewLabel_1);

        comboBoxEntrenadores = new JComboBox<>();
        comboBoxEntrenadores.setBounds(660, 58, 247, 20);
        getContentPane().add(comboBoxEntrenadores);

        JSeparator separator = new JSeparator();
        separator.setBounds(590, 108, 284, 0);
        getContentPane().add(separator);

        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(590, 93, 338, 4);
        getContentPane().add(separator_1);

        JLabel lblNewLabel_1_1 = new JLabel("Nombre:");
        lblNewLabel_1_1.setBounds(590, 134, 85, 25);
        getContentPane().add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_2 = new JLabel("Datos de Actividad");
        lblNewLabel_1_2.setBounds(660, 100, 183, 25);
        getContentPane().add(lblNewLabel_1_2);

        txtDescripcion = new JTextArea();
        txtDescripcion.setBounds(610, 196, 297, 76);
        txtDescripcion.setLineWrap(true); // Habilita el ajuste de línea
        txtDescripcion.setWrapStyleWord(true); // Ajusta las palabras completas

        getContentPane().add(txtDescripcion);

        txtNombre_Actividad = new JTextField();
        txtNombre_Actividad.setEditable(false);
        txtNombre_Actividad.setBounds(653, 137, 223, 20);
        getContentPane().add(txtNombre_Actividad);
        txtNombre_Actividad.setColumns(10);

        JLabel lblNewLabel_1_1_2 = new JLabel("Duracion (horas):");
        lblNewLabel_1_1_2.setBounds(590, 283, 115, 25);
        getContentPane().add(lblNewLabel_1_1_2);

        spinnerDuracionHoras = new JSpinner();
        spinnerDuracionHoras.setBounds(715, 285, 60, 20);
        getContentPane().add(spinnerDuracionHoras);

        spinnerDuracionMinutos = new JSpinner();
        spinnerDuracionMinutos.setBounds(785, 285, 58, 20);
        getContentPane().add(spinnerDuracionMinutos);

        JLabel lblNewLabel_1_1_2_1 = new JLabel("Costo:");
        lblNewLabel_1_1_2_1.setBounds(590, 310, 50, 25);
        getContentPane().add(lblNewLabel_1_1_2_1);

        txtCosto = new JTextField();
        txtCosto.setBounds(653, 313, 142, 20);
        getContentPane().add(txtCosto);
        txtCosto.setColumns(10);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGuardar.setBounds(610, 459, 104, 23);
        getContentPane().add(btnGuardar);
        btnGuardar.addActionListener(e -> guardarActividad());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBounds(726, 459, 117, 23);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(e -> cancelar());

        JLabel lblActividadesExistentes = new JLabel("Seleccione actividad:");
        lblActividadesExistentes.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblActividadesExistentes.setBounds(10, 71, 416, 46);
        getContentPane().add(lblActividadesExistentes);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("Descripcion:");
        lblNewLabel_1_1_1.setBounds(590, 170, 85, 25);
        getContentPane().add(lblNewLabel_1_1_1);

        setTitle("Modificar actividad Deportiva");
        setBounds(100, 100, 950, 600);

        // Cargar entrenadores en el JComboBox
        cargarEntrenadores();

        // Cargar actividades en la tabla
        cargarActividades();
    }

    private void cargarEntrenadores() {
        // Obtener la lista de entrenadores desde la base de datos
        List<DataEntrenador> entrenadores = icontroladorUsuario.listaEntrenadores();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        for (DataEntrenador entrenador : entrenadores) {
            model.addElement(entrenador.getNickname() + " - " + entrenador.getNombre() + " " + entrenador.getApellido() + " (" + entrenador.getDisciplina() + ")");
        }

        comboBoxEntrenadores.setModel(model);
        comboBoxEntrenadores.setSelectedIndex(-1);
    }

    private void cargarActividades() {
        // Obtener la lista de actividades desde la base de datos
        List<DataActividad> actividades = icontroladorActividad.transformarActividades();

        // Definir las columnas de la tabla
        String[] columnNames = { "Nombre", "Duración", "Costo" };

        // Crear el modelo de la tabla
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }
        };

        // Llenar la tabla con los datos de las actividades
        for (DataActividad actividad : actividades) {
            Object[] rowData = {
                actividad.getNombre(),
                actividad.getDuracion().toHours() + "h " + (actividad.getDuracion().toMinutes() % 60) + "m", // Formato de duración
                actividad.getCosto()
            };
            model.addRow(rowData);
        }

        TablaActividades.setModel(model);
    }

    private void cargarDatosActividad() {
        if (actividadSeleccionada != null) {
            txtNombre_Actividad.setText(actividadSeleccionada.getNombre());
            txtDescripcion.setText(actividadSeleccionada.getDescripcion());
            Duration duracion = actividadSeleccionada.getDuracion();
            long horas = duracion.toHours(); // Devuelve long
            long minutosTotales = duracion.toMinutes(); // Devuelve long
            int minutos = (int) (minutosTotales % 60); // Convertir minutos a int

            // Establecer los valores en los spinners, asegurándose de usar Integer
            spinnerDuracionHoras.setValue((int) horas); // Convertir long a int
            spinnerDuracionMinutos.setValue(minutos); 
            txtCosto.setText(String.valueOf(actividadSeleccionada.getCosto()));

            // Seleccionar el entrenador en el JComboBox
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBoxEntrenadores.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                String item = model.getElementAt(i);
                String idEntrenador = item.split(" - ")[0];
                if (icontroladorActividad.getEntrenadorByActId(actividadSeleccionada.getNombre()).getNickname().equals(idEntrenador)) {
                    comboBoxEntrenadores.setSelectedIndex(i);
                    break;
                }
            }
        }
    }


    private void guardarActividad() {
        if (actividadSeleccionada != null) {
        	antiguo =  icontroladorActividad.getEntrenadorByActId(actividadSeleccionada.getNombre());
            String descripcion = txtDescripcion.getText().trim();
            
            Integer horasInteger = (Integer) spinnerDuracionHoras.getValue();
            Integer minutosInteger = (Integer) spinnerDuracionMinutos.getValue();
            
            // Crear Duration con valores de tipo Long
            Duration duracion = Duration.ofHours(horasInteger.longValue())
                                          .plusMinutes(minutosInteger.longValue());

            double costo;
            
            try {
                costo = Double.parseDouble(txtCosto.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El costo debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String idEntrenadorStr = comboBoxEntrenadores.getSelectedItem().toString().split(" - ")[0];
            DataEntrenador entrenador = icontroladorUsuario.getEntrenador(idEntrenadorStr);
            
            if (entrenador == null) {
                JOptionPane.showMessageDialog(this, "Entrenador seleccionado no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar que la duración sea razonable (por ejemplo, debe ser mayor que 0)
            if (horasInteger < 0 || minutosInteger < 0 || (horasInteger == 0 && minutosInteger == 0)) {
                JOptionPane.showMessageDialog(this, "La duración debe ser mayor que 0 minutos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
    
            
          
            
          
            // Actualiza la actividad en la base de datos
            if (icontroladorActividad.actualizarActividad(actividadSeleccionada.getNombre(), antiguo, entrenador, descripcion, duracion, costo)) {
                JOptionPane.showMessageDialog(this, "Actividad actualizada exitosamente.");
                cargarActividades(); // Recargar la lista de actividades
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la actividad.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            
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
