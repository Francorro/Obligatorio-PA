package GUI;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;

import Clases.IControladorActividad;
import Clases.IControladorClase;
import Clases.DataActividad;
import Clases.DataClase;

public class alta_clase_Deportiva extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable table_Clases_existentes;
    private JSpinner spinnerFecha;
    private JSpinner spinnerHora;
    private JTextField txtLugar;
    private JSpinner spinnerCupos;
    private JSpinner spinnerFechaAlta;
    private JComboBox<DataActividad> comboBoxActividades;
    private JButton btnLimpiar;
    private IControladorActividad icontroladorActividad;
    private IControladorClase icontroladorClase;

    public alta_clase_Deportiva(IControladorActividad icontroladorAct,IControladorClase icontroladorCla) {
    	icontroladorActividad= icontroladorAct;
    	icontroladorClase= icontroladorCla;
        setClosable(true);   
        setIconifiable(true); 
        setResizable(true); 
        setMaximizable(true); 
        setTitle("Alta Clase Deportiva");
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setBounds(100, 100, 950, 600);
        getContentPane().setLayout(null);

        // Etiqueta para la lista de clases existentes
        JLabel lblClasesExistentes = new JLabel("Clases existentes por actividad:");
        lblClasesExistentes.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblClasesExistentes.setBounds(10, 58, 290, 27);
        getContentPane().add(lblClasesExistentes);

        // Crear la tabla y agregarla a un JScrollPane
        table_Clases_existentes = new JTable();
        table_Clases_existentes.setBorder(new LineBorder(new Color(0, 0, 0)));
        table_Clases_existentes.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table_Clases_existentes);
        scrollPane.setBounds(10, 95, 565, 400);
        getContentPane().add(scrollPane);

        // Separador y etiquetas para los campos de clase
        JSeparator separator = new JSeparator();
        separator.setBounds(593, 86, 284, 5);
        getContentPane().add(separator);

        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(593, 121, 46, 14);
        getContentPane().add(lblFecha);

        JLabel lblHora = new JLabel("Hora:");
        lblHora.setBounds(593, 146, 46, 14);
        getContentPane().add(lblHora);

        JLabel lblLugar = new JLabel("Lugar:");
        lblLugar.setBounds(593, 177, 46, 14);
        getContentPane().add(lblLugar);

        JLabel lblCupos = new JLabel("Cupos:");
        lblCupos.setBounds(593, 202, 46, 14);
        getContentPane().add(lblCupos);

        JLabel lblFechaAlta = new JLabel("Fecha Alta:");
        lblFechaAlta.setBounds(593, 237, 82, 14);
        getContentPane().add(lblFechaAlta);

        JLabel lblActividad = new JLabel("Actividad:");
        lblActividad.setBounds(585, 58, 82, 14);
        getContentPane().add(lblActividad);

        // Configuración de comboBox y campos
        comboBoxActividades = new JComboBox<>();
        comboBoxActividades.setBounds(643, 55, 234, 20);
        getContentPane().add(comboBoxActividades);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGuardar.setBounds(593, 330, 89, 23);
        getContentPane().add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBounds(704, 330, 89, 23);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(e -> cancelar());
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLimpiar.setBounds(815, 330, 89, 23); // Ajusta las coordenadas según sea necesario
        getContentPane().add(btnLimpiar);

        // Agregar ActionListener para el botón Limpiar
        btnLimpiar.addActionListener(e -> limpiarCampos());

        // Configuración de JSpinner
        spinnerFecha = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
        spinnerFecha.setEditor(dateEditor);
        spinnerFecha.setBounds(649, 118, 86, 20);
        getContentPane().add(spinnerFecha);

        spinnerHora = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerHora, "HH:mm");
        spinnerHora.setEditor(timeEditor);
        spinnerHora.setBounds(649, 143, 86, 20);
        getContentPane().add(spinnerHora);

        txtLugar = new JTextField();
        txtLugar.setBounds(649, 174, 86, 20);
        getContentPane().add(txtLugar);

        spinnerCupos = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        spinnerCupos.setBounds(649, 199, 50, 20);
        getContentPane().add(spinnerCupos);

        spinnerFechaAlta = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        JSpinner.DateEditor dateAltaEditor = new JSpinner.DateEditor(spinnerFechaAlta, "dd/MM/yyyy");
        spinnerFechaAlta.setEditor(dateAltaEditor);
        spinnerFechaAlta.setBounds(669, 234, 86, 20);
        getContentPane().add(spinnerFechaAlta);

        // Inicializar el comboBox sin selección
        comboBoxActividades.setModel(new DefaultComboBoxModel<>());

        // Cargar actividades y configurar acciones
        cargarActividades();
        btnGuardar.addActionListener(e -> guardarClase());
        comboBoxActividades.addActionListener(e -> actualizarTablaClases());
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
        
        comboBoxActividades.setModel(model);
        comboBoxActividades.setRenderer(new ActividadRenderer());
        
        // Establecer la selección del JComboBox a nulo para que no haya nada seleccionado al inicio
        comboBoxActividades.setSelectedItem(null);
    }

    private void actualizarTablaClases() {
        DataActividad actividadSeleccionada = (DataActividad) comboBoxActividades.getSelectedItem();
        if (actividadSeleccionada != null) {
            List<DataClase> clases = icontroladorActividad.transformarClasesActividad(actividadSeleccionada);

            // Ordenar la lista de clases por fecha y hora
            Collections.sort(clases, new Comparator<DataClase>() {
                @Override
                public int compare(DataClase c1, DataClase c2) {
                    // Combinar fecha y hora en LocalDateTime para una comparación precisa
                    LocalDateTime dateTime1 = LocalDateTime.of(c1.getFecha(), c1.getHoraEntrenamiento());
                    LocalDateTime dateTime2 = LocalDateTime.of(c2.getFecha(), c2.getHoraEntrenamiento());
                    return dateTime1.compareTo(dateTime2);
                }
            });

            CustomTableModel tableModel = new CustomTableModel(new String[]{"Fecha", "Hora", "Lugar", "Cupos", "Fecha Alta"});
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (DataClase clase : clases) {
                tableModel.addRow(new Object[]{
                    clase.getFecha().format(formatter).toString(),
                    clase.getFormattedHoraEntrenamiento().toString(),
                    clase.getLugar(),
                    clase.getCupo(),
                    clase.getFechaAlta().format(formatter).toString()
                });
            }
            table_Clases_existentes.setModel(tableModel);
        }
    }

    private void guardarClase() {
        // Obtener los valores de los campos
        String lugar = txtLugar.getText().trim();
        int cupos = (int) spinnerCupos.getValue();
        LocalDate fecha = convertDateToLocalDate((Date) spinnerFecha.getValue());
        LocalTime hora = convertDateToLocalTime((Date) spinnerHora.getValue());
        LocalDate fechaAlta = convertDateToLocalDate((Date) spinnerFechaAlta.getValue());
        DataActividad actividadSeleccionada = (DataActividad) comboBoxActividades.getSelectedItem();

        // Validaciones de campos obligatorios
        if (actividadSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una actividad.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (lugar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El lugar no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cupos <= 0) {
            JOptionPane.showMessageDialog(this, "El número de cupos debe ser mayor que 0.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la fecha no sea anterior a la fecha actual
        LocalDate today = LocalDate.now();
        if (fecha.isBefore(today)) {
            JOptionPane.showMessageDialog(this, "La fecha no puede ser anterior a la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la fecha de alta sea mayor que la fecha actual y menor que la fecha de la clase
        if (fechaAlta.isBefore(today)) {
            JOptionPane.showMessageDialog(this, "La fecha de alta debe ser mayor a la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (fechaAlta.isAfter(fecha)) {
            JOptionPane.showMessageDialog(this, "La fecha de alta no puede ser posterior a la fecha de la clase.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si existe conflicto de horario para todas las actividades dictadas por el entrenador de la actividad seleccionada
     
        if (icontroladorClase.existeConflictoHorario(actividadSeleccionada, fecha, hora)) {
            JOptionPane.showMessageDialog(this, "Ya existe una clase en el rango horario indicado dictada por el entrenador para la actividad seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (icontroladorClase.guardarClase(actividadSeleccionada,fecha,hora,lugar,cupos,fechaAlta)) {
            JOptionPane.showMessageDialog(this, "Clase guardada exitosamente.");
            actualizarTablaClases();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar la clase.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private LocalTime convertDateToLocalTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    private static class ActividadRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            // Llamar al método base para configurar las propiedades de selección y foco
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Manejar el caso donde el valor es null
            if (value == null) {
                setText(""); // No mostrar texto para el valor null
            } else if (value instanceof DataActividad) {
                DataActividad actividad = (DataActividad) value;
                setText(actividad.getNombre() + " (" + actividad.getDuracion().toHours() + "h " + (actividad.getDuracion().toMinutes() % 60) + "m)");
            }

            return this;
        }
    }

    
    
    private void limpiarCampos() {
        // Restablecer todos los campos a sus valores predeterminados
        comboBoxActividades.setSelectedItem(null);
        spinnerFecha.setValue(new Date());
        spinnerHora.setValue(new Date());
        txtLugar.setText("");
        spinnerCupos.setValue(0);
        spinnerFechaAlta.setValue(new Date());
        
        // Limpiar la tabla de clases existentes
        table_Clases_existentes.setModel(new CustomTableModel(new String[]{"Fecha", "Hora", "Lugar", "Cupos", "Fecha Alta"}));
    }
    

    // Subclase de DefaultTableModel para hacer que las celdas no sean editables
    private static class CustomTableModel extends DefaultTableModel {
    	private static final long serialVersionUID = 1L;
        public CustomTableModel(Object[] columnNames) {
            super(columnNames, 0);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // No permitir edición en ninguna celda
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
