package GUI;

import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.time.Duration;

import Clases.IControladorUsuario;
import Clases.IControladorActividad;
import Clases.DataEntrenador;
import Clases.DataActividad;
import java.awt.Color;


public class AltaActivadadDeportiva extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable TablaActividades;
    private JTextField txtNombre_Actividad;
    private JTextArea txtDescripcion;
    private JSpinner spinnerDuracionHoras;
    private JSpinner spinnerDuracionMinutos;
    private JTextField txtCosto;
    private JComboBox<String> comboBoxEntrenadores;
    private IControladorUsuario icontroladorUsuario;
    private IControladorActividad icontroladorActividad;

 
    public AltaActivadadDeportiva(IControladorUsuario icontroladorUsua, IControladorActividad icontroladorAct) {
    	icontroladorUsuario=icontroladorUsua;
    	icontroladorActividad=icontroladorAct;
        // Configuración de JInternalFrame
        setClosable(true);   
        setIconifiable(true); 
        setResizable(true); 
        setMaximizable(true);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().setLayout(null);

        // Título del marco
        JLabel lblNewLabel = new JLabel("Alta de actividad deportiva");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(226, 11, 325, 25);
        getContentPane().add(lblNewLabel);

        // Tabla de actividades
        TablaActividades = new JTable();
        JScrollPane scrollPane = new JScrollPane(TablaActividades);
        scrollPane.setBounds(10, 118, 563, 364);
        getContentPane().add(scrollPane);

        JLabel lblNewLabel_1 = new JLabel("Entrenador:");
        lblNewLabel_1.setBounds(589, 56, 206, 25);
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

        JLabel lblActividadesExistentes = new JLabel("Actividades existentes:");
        lblActividadesExistentes.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblActividadesExistentes.setBounds(10, 71, 416, 46);
        getContentPane().add(lblActividadesExistentes);
        
        JLabel lblNewLabel_1_1_1 = new JLabel("Descripcion:");
        lblNewLabel_1_1_1.setBounds(590, 170, 85, 25);
        getContentPane().add(lblNewLabel_1_1_1);

        setTitle("Alta actividad Deportiva ");
        setBounds(100, 100, 950, 600);

        // Cargar entrenadores en el JComboBox
        cargarEntrenadores();

        // Cargar actividades en la tabla
        cargarActividades();
    }

    private void cargarEntrenadores() {
        // Obtener la lista de entrenadores
        List<DataEntrenador> entrenadores = icontroladorUsuario.listaEntrenadores();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        

        for (DataEntrenador entrenador : entrenadores) {
            model.addElement(entrenador.getNickname() + " - " + entrenador.getNombre() + " " + entrenador.getApellido() + " (" + entrenador.getDisciplina() + ")");
        }

        comboBoxEntrenadores.setModel(model);
        comboBoxEntrenadores.setSelectedIndex(-1);
    }

    private void cargarActividades() {
        // Obtener la lista de actividades
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

    private void guardarActividad() {
        // Captura de datos desde los campos
        String nombre = txtNombre_Actividad.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        int duracionHoras = (Integer) spinnerDuracionHoras.getValue();
        int duracionMinutos = (Integer) spinnerDuracionMinutos.getValue();
        String costoText = txtCosto.getText().trim();
        
        // Validar campos obligatorios
        if (nombre.isEmpty() || descripcion.isEmpty() || costoText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar el costo
        double costo;
        try {
            costo = Double.parseDouble(costoText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El costo debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar que la duración sea razonable (por ejemplo, debe ser mayor que 0)
        if (duracionHoras < 0 || duracionMinutos < 0 || (duracionHoras == 0 && duracionMinutos == 0)) {
            JOptionPane.showMessageDialog(this, "La duración debe ser mayor que 0 minutos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (icontroladorActividad.existeActividad(nombre)) {
            JOptionPane.showMessageDialog(this, "Ya existe una actividad con el nombre ingresado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Obtener el entrenador seleccionado
        String seleccionado = (String) comboBoxEntrenadores.getSelectedItem();
        if (seleccionado == null || seleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un entrenador.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] partes = seleccionado.split(" - ");
        DataEntrenador entrenadorSeleccionado = icontroladorUsuario.getEntrenador(partes[0]); 
        if (entrenadorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Entrenador seleccionado no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Guardar la actividad en la base de datos
        if (icontroladorActividad.crearActividad(nombre, descripcion, Duration.ofMinutes(duracionHoras * 60 + duracionMinutos), costo, entrenadorSeleccionado.getNickname())) {
            JOptionPane.showMessageDialog(this, "Actividad guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar los campos del formulario
            limpiarCampos();
            // Actualizar la tabla
            cargarActividades();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la actividad.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
 // Método para cerrar la ventana al cancelar
    private void cancelar() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea cancelar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose(); // Cierra la ventana actual
        }
    }
    
    private void limpiarCampos() {
        txtNombre_Actividad.setText("");
        txtDescripcion.setText("");
        spinnerDuracionHoras.setValue(0);
        spinnerDuracionMinutos.setValue(0);
        txtCosto.setText("");
        comboBoxEntrenadores.setSelectedIndex(-1); // Deselecciona cualquier entrenador
    }
}
