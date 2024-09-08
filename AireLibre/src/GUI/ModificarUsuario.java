package GUI;

import java.awt.Cursor;
import java.awt.Font;
import java.time.LocalDate;
import java.util.List;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;


import Clases.DataUsuario;
import Clases.IControladorUsuario;
import Clases.DataDeportista;
import Clases.DataEntrenador;

public class ModificarUsuario extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable TablaUsuarios;
    private JTextField txtNickname;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreoElectronico;
    private JTextField txtSitioWeb;
    private JTextField txtDisciplina;
    private JCheckBox chkEsProfesional;
    private JSpinner spinnerDia;
    private JSpinner spinnerMes;
    private JSpinner spinnerAnio;
    private DataUsuario usuarioSeleccionado;
    private IControladorUsuario icontroladorUsuario;
    boolean esDeportista = false;
    boolean esEntrenador = false;
    JLabel lblEsProfesional;
    JLabel lblDisciplina;
    JLabel lblSitioWeb;
    private JPasswordField textPass;

    public ModificarUsuario(IControladorUsuario icontroladorUsu) {
        icontroladorUsuario = icontroladorUsu;

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setMaximizable(true);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Modificar Usuario");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(226, 11, 325, 25);
        getContentPane().add(lblNewLabel);

        TablaUsuarios = new JTable();
        JScrollPane scrollPane = new JScrollPane(TablaUsuarios);
        scrollPane.setBounds(10, 118, 563, 364);
        getContentPane().add(scrollPane);

        TablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = TablaUsuarios.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String nickname = (String) TablaUsuarios.getValueAt(filaSeleccionada, 0);
                    // Obtener el DataUsuario usando el nickname
                    esDeportista = false;
                    esEntrenador = false;
                    usuarioSeleccionado = icontroladorUsuario.getEntrenador(nickname);
                    if (usuarioSeleccionado == null) {
                    	esDeportista=true;
                        usuarioSeleccionado = icontroladorUsuario.getDeportista(nickname);
                    }else
                    	esEntrenador=true;
                    
                    if (usuarioSeleccionado != null) {
                        cargarDatosUsuario();
                    }
                }
            }
        });

        JLabel lblNickname = new JLabel("Nickname:");
        lblNickname.setBounds(590, 134, 85, 25);
        getContentPane().add(lblNickname);

        txtNickname = new JTextField();
        txtNickname.setEditable(false);
        txtNickname.setBounds(680, 137, 223, 20);
        getContentPane().add(txtNickname);
        txtNickname.setColumns(10);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(590, 170, 85, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(680, 173, 223, 20);
        getContentPane().add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(590, 205, 85, 25);
        getContentPane().add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setBounds(680, 208, 223, 20);
        getContentPane().add(txtApellido);
        txtApellido.setColumns(10);

        JLabel lblCorreoElectronico = new JLabel("Correo:");
        lblCorreoElectronico.setBounds(590, 240, 85, 25);
        getContentPane().add(lblCorreoElectronico);

        txtCorreoElectronico = new JTextField();
        txtCorreoElectronico.setBounds(680, 243, 223, 20);
        getContentPane().add(txtCorreoElectronico);
        txtCorreoElectronico.setColumns(10);

        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
        lblFechaNacimiento.setBounds(590, 275, 117, 25);
        getContentPane().add(lblFechaNacimiento);

        // Spinner para el día
        spinnerDia = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        spinnerDia.setBounds(717, 277, 55, 20);  // Ajusta la posición
        getContentPane().add(spinnerDia);

        // Spinner para el mes
        spinnerMes = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        spinnerMes.setBounds(794, 277, 46, 20);  // Ajusta la posición
        getContentPane().add(spinnerMes);

        // Spinner para el año
        spinnerAnio = new JSpinner(new SpinnerNumberModel(1, 1, null, 1));
        spinnerAnio.setBounds(861, 277, 69, 20);  // Ajusta la posición
        getContentPane().add(spinnerAnio);

        lblSitioWeb = new JLabel("Sitio Web:");
        lblSitioWeb.setBounds(590, 345, 85, 25);
        getContentPane().add(lblSitioWeb);

        txtSitioWeb = new JTextField();
        txtSitioWeb.setBounds(680, 347, 223, 20);
        getContentPane().add(txtSitioWeb);
        txtSitioWeb.setColumns(10);

        lblDisciplina = new JLabel("Disciplina:");
        lblDisciplina.setBounds(590, 383, 85, 25);
        getContentPane().add(lblDisciplina);

        txtDisciplina = new JTextField();
        txtDisciplina.setBounds(680, 385, 223, 20);
        getContentPane().add(txtDisciplina);
        txtDisciplina.setColumns(10);

        lblEsProfesional = new JLabel("Profesional");
        lblEsProfesional.setBounds(590, 345, 85, 25);
        getContentPane().add(lblEsProfesional);

        chkEsProfesional = new JCheckBox();
        chkEsProfesional.setBackground(Color.LIGHT_GRAY);
        chkEsProfesional.setBounds(680, 347, 23, 23);
        getContentPane().add(chkEsProfesional);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnGuardar.setBounds(680, 420, 104, 23);
        getContentPane().add(btnGuardar);
        btnGuardar.addActionListener(e -> guardarUsuario());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancelar.setBounds(790, 420, 117, 23);
        getContentPane().add(btnCancelar);
        btnCancelar.addActionListener(e -> cancelar());

        JLabel lblUsuariosExistentes = new JLabel("Seleccione usuario:");
        lblUsuariosExistentes.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblUsuariosExistentes.setBounds(10, 71, 416, 46);
        getContentPane().add(lblUsuariosExistentes);
        
        JLabel lblPass = new JLabel("Contraseña:");
        lblPass.setBounds(590, 309, 117, 25);
        getContentPane().add(lblPass);
        
        textPass = new JPasswordField();
        textPass.setColumns(10);
        textPass.setBounds(680, 311, 223, 20);
        getContentPane().add(textPass);

        setTitle("Modificar Usuario");
        setBounds(100, 100, 1000, 600);
        
        chkEsProfesional.setVisible(false);
        lblEsProfesional.setVisible(false);
        lblDisciplina.setVisible(false);
        lblSitioWeb.setVisible(false);
        txtSitioWeb.setVisible(false);
        txtDisciplina.setVisible(false);


        cargarUsuarios();
    }

    private void cargarUsuarios() {
        List<DataUsuario> usuarios = icontroladorUsuario.listaUsuarios();

        String[] columnNames = { "Nickname", "Nombre", "Apellido" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (DataUsuario usuario : usuarios) {
            Object[] rowData = {
                usuario.getNickname(),
                usuario.getNombre(),
                usuario.getApellido()
            };
            model.addRow(rowData);
        }

        TablaUsuarios.setModel(model);
    }

    private void cargarDatosUsuario() {
        if (usuarioSeleccionado != null) {
            txtNickname.setText(usuarioSeleccionado.getNickname());
            txtNombre.setText(usuarioSeleccionado.getNombre());
            txtApellido.setText(usuarioSeleccionado.getApellido());
            txtCorreoElectronico.setText(usuarioSeleccionado.getCorreoElectronico());
            textPass.setText(usuarioSeleccionado.getPass());
            chkEsProfesional.setSelected(false);
            lblEsProfesional.setVisible(false);
            lblDisciplina.setVisible(false);
            lblSitioWeb.setVisible(false);
            

            // Obtener fecha de nacimiento
            LocalDate fechaNacimiento = usuarioSeleccionado.getFechaNacimiento();
            spinnerDia.setValue(fechaNacimiento.getDayOfMonth());
            spinnerMes.setValue(fechaNacimiento.getMonthValue());
            spinnerAnio.setValue(fechaNacimiento.getYear());

            // Mostrar campos adicionales dependiendo del tipo de usuario
            if (esEntrenador) {
                DataEntrenador entrenador = icontroladorUsuario.getEntrenador(usuarioSeleccionado.getNickname());
                txtSitioWeb.setText(entrenador.getSitioWeb());
                txtDisciplina.setText(entrenador.getDisciplina());
                txtSitioWeb.setVisible(true);
                txtDisciplina.setVisible(true);
                chkEsProfesional.setVisible(false);
                lblDisciplina.setVisible(true);
                lblSitioWeb.setVisible(true);
            } else if (esDeportista) {
            	DataDeportista deportista = icontroladorUsuario.getDeportista(usuarioSeleccionado.getNickname());
        		lblEsProfesional.setVisible(true);
            	if ("Si".equals(deportista.getEsProfesional())) {
            		chkEsProfesional.setSelected(true);
            	}
                txtSitioWeb.setVisible(false);
                txtDisciplina.setVisible(false);
                chkEsProfesional.setVisible(true);
            } else {
                txtSitioWeb.setVisible(false);
                txtDisciplina.setVisible(false);
                chkEsProfesional.setVisible(false);
            }
        }
    }

    private void guardarUsuario() {
        if (usuarioSeleccionado != null) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String correoElectronico = txtCorreoElectronico.getText().trim();
            String sitioWeb = txtSitioWeb.getText().trim();
            String disciplina = txtDisciplina.getText().trim();
            char[] passwordChars = textPass.getPassword();
            String pass = new String(passwordChars).trim();
            boolean esProfesional = chkEsProfesional.isSelected();

            int dia = (Integer) spinnerDia.getValue();
            int mes = (Integer) spinnerMes.getValue();
            int anio = (Integer) spinnerAnio.getValue();
            
            if (nombre.isEmpty() || apellido.isEmpty() || correoElectronico.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                java.util.Arrays.fill(passwordChars, ' ');
                return;
            }
 
            if (esEntrenador && sitioWeb.isEmpty() || esEntrenador && disciplina.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos SitioWeb y Disciplina son obligatorios para los entrenadores.", "Error", JOptionPane.ERROR_MESSAGE);
                return; 	        
            }

            
            // Validación de la fecha
            LocalDate fechaNacimiento;
            try {
                fechaNacimiento = LocalDate.of(anio, mes, dia);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Fecha de nacimiento inválida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (nombre.isEmpty() || apellido.isEmpty() || correoElectronico.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(!esCorreoElectronicoValido(correoElectronico)) {
                JOptionPane.showMessageDialog(this, "El correo electronico no es valido.\nEjemplo: usuario@dominio.com", "Error", JOptionPane.ERROR_MESSAGE);
                return; 	   
            }
            if( esEntrenador && !esSitioWebValido(sitioWeb)) {
                JOptionPane.showMessageDialog(this, "El sitioWeb no es valido.\nEjemplo: https://www.ejemplo.com", "Error", JOptionPane.ERROR_MESSAGE);
                return; 	   
            }

            // Llamar al controlador para actualizar el usuario
            if (icontroladorUsuario.actualizarUsuario(usuarioSeleccionado.getNickname(), nombre, apellido, correoElectronico, fechaNacimiento, esEntrenador ,sitioWeb,disciplina,esProfesional, pass)) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
                cargarUsuarios();
                java.util.Arrays.fill(passwordChars, ' ');
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        
        
        boolean esCorreoElectronicoValido(String correo) {
            String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            return correo.matches(regex);
        }
        
        boolean esSitioWebValido(String sitioWeb) {
            String regex = "^(https?://)?(www\\.)?([\\w-]+)+\\.[a-z]{2,6}(/\\S*)?$";
            return sitioWeb.matches(regex);
        }
        
        private void cancelar() {
            int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea cancelar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
        
        private void limpiarCampos() {
            txtNickname.setText("");
            txtNombre.setText("");
            txtApellido.setText("");
            txtCorreoElectronico.setText("");
            txtSitioWeb.setText("");
            txtDisciplina.setText("");
            chkEsProfesional.setSelected(false);
            
            // Restablecer los spinners a sus valores predeterminados
            spinnerDia.setValue(1);
            spinnerMes.setValue(1);
            spinnerAnio.setValue(Calendar.getInstance().get(Calendar.YEAR));
            
            // Ocultar campos específicos dependiendo del tipo de usuario
            lblEsProfesional.setVisible(false);
            lblDisciplina.setVisible(false);
            lblSitioWeb.setVisible(false);
            txtSitioWeb.setVisible(false);
            txtDisciplina.setVisible(false);
            chkEsProfesional.setVisible(false);
        }
    }
