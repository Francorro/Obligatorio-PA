package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;


import Clases.IControladorUsuario;
import javax.swing.UIManager;

public class AltaUsuario extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtNickname;
    private JTextField txtEmail;
    private JPasswordField txtPass;
    private JSpinner spinnerDia;
    private JSpinner spinnerMes;
    private JSpinner spinnerAnio;
    private JCheckBox chkDeportista;
    private JCheckBox chkEntrenador;
    private JCheckBox chkProfesional;
    private JCheckBox chkNoProfesional;
    private JTextField txtSitioWeb;
    private JTextField txtDisciplina;
    private IControladorUsuario icontroladorUsuario;
    JLabel lblDisciplina;
    JLabel lblSitioWeb;

    public AltaUsuario(IControladorUsuario icontroladorUsu) {
    	getContentPane().setForeground(new Color(0, 0, 0));
    	icontroladorUsuario=icontroladorUsu;
        setClosable(true);   
        setIconifiable(true);
        setResizable(true); 
        setMaximizable(true);
        setTitle("Alta Usuario");
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setBounds(100, 100, 450, 300);
        setSize(600, 600);
        getContentPane().setLayout(null);

        JLabel lblDatosBasicos = new JLabel("Datos básicos:");
        lblDatosBasicos.setForeground(UIManager.getColor("CheckBox.foreground"));
        lblDatosBasicos.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblDatosBasicos.setBounds(10, 11, 162, 15);
        getContentPane().add(lblDatosBasicos);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(78, 435, 117, 25);
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        getContentPane().add(btnRegistrar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(238, 435, 117, 25);
        btnCancelar.addActionListener(e -> cancelar());
        getContentPane().add(btnCancelar);
                
        txtNombre = new JTextField();
        txtNombre.setBounds(120, 56, 114, 19);
        getContentPane().add(txtNombre);
        txtNombre.setColumns(10);
                                
        txtApellido = new JTextField();
        txtApellido.setBounds(120, 82, 114, 19);
        getContentPane().add(txtApellido);
        txtApellido.setColumns(10);
                                                
        txtNickname = new JTextField();
        txtNickname.setBounds(120, 109, 114, 19);
        getContentPane().add(txtNickname);
        txtNickname.setColumns(10);
        
        txtPass = new JPasswordField();
        txtPass.setBounds(120, 135, 114, 19);
        getContentPane().add(txtPass);

                                                                            
        txtEmail = new JTextField();
        txtEmail.setBounds(176, 165, 273, 19);
        getContentPane().add(txtEmail);
        txtEmail.setColumns(10);
                                                                                     
        spinnerDia = new JSpinner();
        spinnerDia.setBounds(179, 215, 55, 20);
        getContentPane().add(spinnerDia);
        
        spinnerMes = new JSpinner();
        spinnerMes.setBounds(257, 215, 48, 20);
        getContentPane().add(spinnerMes);
                
        spinnerAnio = new JSpinner();
        spinnerAnio.setBounds(327, 215, 69, 20);
        getContentPane().add(spinnerAnio);
                        
        // Agregar CheckBox para Deportista y Entrenador
        chkDeportista = new JCheckBox("Deportista");
        chkDeportista.setBounds(32, 256, 120, 20);
        getContentPane().add(chkDeportista);
        chkDeportista.setBackground(Color.LIGHT_GRAY);
        chkDeportista.setForeground(Color.DARK_GRAY);
        chkDeportista.setFont(new Font("Tahoma", Font.BOLD, 11));
                                
        chkEntrenador = new JCheckBox("Entrenador");
        chkEntrenador.setBounds(32, 292, 120, 20);
        getContentPane().add(chkEntrenador);
        chkEntrenador.setBackground(Color.LIGHT_GRAY);
        chkEntrenador.setForeground(Color.DARK_GRAY);
        chkEntrenador.setFont(new Font("Tahoma", Font.BOLD, 11));
                                        
        // CheckBoxes para Profesional y No Profesional
        chkProfesional = new JCheckBox("Profesional");
        chkProfesional.setBounds(176, 256, 120, 20);
        getContentPane().add(chkProfesional);
        chkProfesional.setBackground(Color.LIGHT_GRAY);
        chkProfesional.setForeground(Color.DARK_GRAY);
        chkProfesional.setFont(new Font("Tahoma", Font.BOLD, 11));                                                                                                
                                                                                                                                                
        chkNoProfesional = new JCheckBox("No Profesional");
        chkNoProfesional.setBounds(176, 292, 138, 20);
        getContentPane().add(chkNoProfesional);
        chkNoProfesional.setBackground(Color.LIGHT_GRAY);
        chkNoProfesional.setForeground(Color.DARK_GRAY);
        chkNoProfesional.setFont(new Font("Tahoma", Font.BOLD, 11));
                                                                                                                          
        
        txtSitioWeb = new JTextField();
        txtSitioWeb.setBounds(120, 330, 250, 19);
        getContentPane().add(txtSitioWeb);

        
        
        
        txtDisciplina = new JTextField();
        txtDisciplina.setBounds(120, 366, 250, 19);
        getContentPane().add(txtDisciplina);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(32, 54, 206, 25);
        getContentPane().add(lblNombre);
        
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setBounds(32, 80, 206, 25);
        getContentPane().add(lblApellido);
        
        JLabel lblNickname = new JLabel("Nickname:");
        lblNickname.setBounds(32, 107, 206, 25);
        getContentPane().add(lblNickname);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(32, 133, 206, 25);
        getContentPane().add(lblPassword);
        
        JLabel lblCorreoElectronico = new JLabel("Correo electronico:");
        lblCorreoElectronico.setBounds(32, 163, 206, 25);
        getContentPane().add(lblCorreoElectronico);
        
        JLabel lblFechaNacimiento = new JLabel("Fecha nacimiento:");
        lblFechaNacimiento.setBounds(32, 213, 206, 25);
        getContentPane().add(lblFechaNacimiento);
        
        lblSitioWeb = new JLabel("Sitio web:");
        lblSitioWeb.setBounds(32, 328, 206, 25);
        getContentPane().add(lblSitioWeb);
        lblSitioWeb.setVisible(false);
        
        lblDisciplina = new JLabel("Disciplina:");
        lblDisciplina.setBounds(32, 364, 206, 25);
        getContentPane().add(lblDisciplina);
        
        JLabel lblDia = new JLabel("Día");
        lblDia.setBounds(186, 195, 55, 25);
        getContentPane().add(lblDia);
        
        JLabel lblMes = new JLabel("Mes");
        lblMes.setBounds(262, 195, 55, 25);
        getContentPane().add(lblMes);
        
        JLabel lblAño = new JLabel("Año");
        lblAño.setBounds(341, 195, 55, 25);
        getContentPane().add(lblAño);
        txtDisciplina.setVisible(false);
        txtSitioWeb.setVisible(false);
        lblDisciplina.setVisible(false);
        chkNoProfesional.setVisible(false);

        	chkNoProfesional.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			if (chkNoProfesional.isSelected()) {
        				chkProfesional.setSelected(false);
        			}
        		}
        	});
        	chkProfesional.setVisible(false);

        	// Añadir listeners para los checkboxes de profesionalidad
        	chkProfesional.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			if (chkProfesional.isSelected()) {
        				chkNoProfesional.setSelected(false);
        			}
        		}
        	});

        	// Añadir listener para mostrar/ocultar los campos de sitio web y disciplina
        	chkEntrenador.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			if (chkEntrenador.isSelected()) {
        				chkDeportista.setSelected(false);
        				txtSitioWeb.setVisible(true);
        				txtDisciplina.setVisible(true);
        				lblDisciplina.setVisible(true);
        				lblSitioWeb.setVisible(true);
        			} else {
        				lblDisciplina.setVisible(false);
        				lblSitioWeb.setVisible(false);
        				txtSitioWeb.setVisible(false);
        				txtDisciplina.setVisible(false);
        			}
        		}
        	});

        	chkEntrenador.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			if (chkEntrenador.isSelected()) {
        				chkDeportista.setSelected(false);
        				chkProfesional.setVisible(false);
        				chkNoProfesional.setVisible(false);
        			}
        		}
        	});

        	// Añadir listener para mostrar/ocultar los checkboxes de profesionalidad
        	chkDeportista.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent e) {
        			if (chkDeportista.isSelected()) {
        				chkEntrenador.setSelected(false);
        				chkProfesional.setVisible(true);
        				chkNoProfesional.setVisible(true);
        				lblDisciplina.setVisible(false);
        				txtDisciplina.setVisible(false);
        				lblSitioWeb.setVisible(false);
        				txtSitioWeb.setVisible(false);
        			} else {
        				chkProfesional.setVisible(false);
        				chkNoProfesional.setVisible(false);
        			}
        		}
        	});
    	}

    private void registerUser() {
        String nickname = txtNickname.getText();
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String correoElectronico = txtEmail.getText();
        String pass = new String(txtPass.getPassword());
        String sitio = txtSitioWeb.getText();
        String disciplina = txtDisciplina.getText();
     
        

        int dia = (int) spinnerDia.getValue();
        int mes = (int) spinnerMes.getValue(); 
        int anio = (int) spinnerAnio.getValue();


        boolean esDeportista = chkDeportista.isSelected();
        boolean esEntrenador = chkEntrenador.isSelected();
        boolean esProfesional = chkProfesional.isSelected();
        boolean esNoProfesional = chkNoProfesional.isSelected();
        
        if (nickname.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || correoElectronico.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            // Validación de categorías
        }else if (!esDeportista && !esEntrenador) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una categoría (Deportista o Entrenador).", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
        }else if (esDeportista) {
                    if (!esProfesional && !esNoProfesional) {
                        JOptionPane.showMessageDialog(this, "Debe seleccionar Profesional o No Profesional.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    } 
        }else if (esEntrenador && sitio.isEmpty() || disciplina.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos SitioWeb y Disciplina son obligatorios para los entrenadores.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 	        
        }
        if(!esCorreoElectronicoValido(correoElectronico)) {
            JOptionPane.showMessageDialog(this, "El correo electronico no es valido.\nEjemplo: usuario@dominio.com", "Error", JOptionPane.ERROR_MESSAGE);
            return; 	   
        }
        if( esEntrenador && !esSitioWebValido(sitio)) {
            JOptionPane.showMessageDialog(this, "El sitioWeb no es valido.\nEjemplo: https://www.ejemplo.com", "Error", JOptionPane.ERROR_MESSAGE);
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
        boolean exito = icontroladorUsuario.crearUsuario(nombre,apellido, nickname,  
        	correoElectronico,fechaNacimiento, pass, esDeportista, esEntrenador,  esProfesional, sitio, disciplina);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            clearFields(); // Limpiar campos después del registro
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar el usuario. Inténtelo nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
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
    

    private void clearFields() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtNickname.setText("");
        txtEmail.setText("");
        txtPass.setText("");
        spinnerDia.setValue(0);
        spinnerMes.setValue(0);
        spinnerAnio.setValue(0);
        chkDeportista.setSelected(false);
        chkEntrenador.setSelected(false);
        chkProfesional.setSelected(false);
        chkNoProfesional.setSelected(false);
        txtSitioWeb.setText(""); // Limpiar campo Sitio Web
        txtDisciplina.setText(""); // Limpiar campo Disciplina
        lblDisciplina.setVisible(false);
        lblSitioWeb.setVisible(false);
        txtSitioWeb.setVisible(false);
        txtDisciplina.setVisible(false);
        

    }
    
    // Método para cerrar la ventana al cancelar
    private void cancelar() {
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea cancelar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            dispose(); // Cierra la ventana actual
        }
    }
}
