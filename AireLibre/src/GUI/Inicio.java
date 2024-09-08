package GUI;

import java.awt.EventQueue;
import Clases.Fabrica;
import Clases.IControladorActividad;
import Clases.IControladorUsuario;
import Clases.IControladorClase;
import Clases.IControladorInscripcion;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Cursor;


import javax.swing.JLabel;
import java.awt.Font;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.awt.Color;

import javax.swing.SwingConstants;


public class Inicio {

    private JFrame frmAireLibre;
    private JInternalFrame currentFrame = null;
    private JDesktopPane panelPrincipal; 
    private IControladorActividad icontroladorActividad;
    private IControladorUsuario icontroladorUsuario;
    private IControladorClase icontroladorClase;
    private IControladorInscripcion icontroladorInscripcion;
    private Fabrica fabrica;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Inicio window = new Inicio();
                    window.frmAireLibre.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Inicio() {
        fabrica=  Fabrica.getInstance();
        icontroladorActividad= fabrica.getIControladorActividad();
        icontroladorUsuario= fabrica.getIControladorUsuario();
        icontroladorClase = fabrica.getIControladorClase();
        icontroladorInscripcion= fabrica.getIControladorInscripcion();
        initialize();
    }

    private void abrirInternalFrame(JInternalFrame internalFrame) {
        // Verifico si ya hay un formulario del mismo tipo en el panel
        for (JInternalFrame frame : panelPrincipal.getAllFrames()) {
            if (frame.getClass().equals(internalFrame.getClass())) {
                try {
                    if (frame.isIcon()) {
                        // Si el frame está minimizado, lo restauro
                        frame.setIcon(false);
                    }
                    frame.setSelected(true);  // Lo selecciono y lo traigo al frente
                    frame.toFront();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(
                    frmAireLibre,
                    "Ya hay un formulario de este tipo abierto.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }

        // Verifico que no haya otro formulario abierto o si el formulario actual es cerrable
        if (currentFrame == null) {
            // Coloco el nuevo formulario como el actual
            currentFrame = internalFrame;
            panelPrincipal.add(currentFrame);
            currentFrame.setVisible(true);
        } else {
            // Si ya hay un formulario abierto, sale diálogo
            int resultado = JOptionPane.showConfirmDialog(
                frmAireLibre, // 
                "Si desea abrir otro formulario debe cerrar el actual", // Mensaje del diálogo
                "Advertencia", // Titulo del mensaje
                JOptionPane.YES_NO_OPTION, // Opciones del diálogo
                JOptionPane.WARNING_MESSAGE // Tipo de mensaje
            );

            // Si el usuario aprieta en yes
            if (resultado == JOptionPane.YES_OPTION) {
                // Cierra el formulario actual
                if (currentFrame != null) {
                    currentFrame.dispose();
                    currentFrame = null;
                }

                // Coloco el nuevo formulario 
                currentFrame = internalFrame;
                panelPrincipal.add(currentFrame);
                currentFrame.setVisible(true);
            } else {
                // No hace nada si el usuario elige "No"
            }
        }
    }

    
    public void clearCurrentFrame() {
        this.currentFrame = null;
    }
    private void initialize() {
        frmAireLibre = new JFrame();
        frmAireLibre.setResizable(false);
        frmAireLibre.getContentPane().setBackground(SystemColor.controlShadow);
        frmAireLibre.setTitle("AIRE LIBRE");
        frmAireLibre.setBounds(100, 100, 450, 300);
        frmAireLibre.setSize(1200, 800);
        frmAireLibre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmAireLibre.getContentPane().setLayout(null);

        // inizializo para conectar con la base de datos al iniciar programa.
        new AltaActivadadDeportiva(icontroladorUsuario,icontroladorActividad);
        
        // Inicializa el JDesktopPane
        panelPrincipal = new JDesktopPane();
        panelPrincipal.setBounds(0, 0, 1200, 748);
        frmAireLibre.getContentPane().add(panelPrincipal);
        panelPrincipal.setLayout(null);
        
        JLabel lblTitulo = new JLabel("A I R E  L I B R E");
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 50));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER); // Centrar texto horizontalmente
        lblTitulo.setBounds(0, 322, 1200, 50); // Ajustar el tamaño y la posición
        panelPrincipal.add(lblTitulo);

        JLabel lblTitulo2 = new JLabel("Bienvenido");
        lblTitulo2.setFont(new Font("Consolas", Font.BOLD, 40));
        lblTitulo2.setHorizontalAlignment(SwingConstants.CENTER); // Centrar texto horizontalmente
        lblTitulo2.setBounds(0, 261, 1200, 50); // Ajustar el tamaño y la posición
        lblTitulo2.setOpaque(false); // Hacer el fondo del JLabel transparente
        panelPrincipal.add(lblTitulo2);


   
        // Agregar efecto de desvanecimiento para "Bienvenido"
        Timer timer = new Timer(75, new ActionListener() { // Intervalo más largo para desvanecimiento más lento
            private float alpha = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.03f; // Reducir la opacidad más lentamente
                if (alpha <= 0.0f) {
                    alpha = 0.0f; // Asegura que alpha no sea menor que 0
                    ((Timer) e.getSource()).stop(); // Detener el timer cuando se complete el desvanecimiento
                }
                lblTitulo2.setForeground(new Color(0, 0, 0, alpha)); // Ajustar la opacidad del color del texto
            }
        });
        timer.setInitialDelay(500); // Retraso inicial antes de comenzar el desvanecimiento
        timer.start();



        JMenuBar menuBar = new JMenuBar();
        frmAireLibre.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Inicio");
        mnNewMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuBar.add(mnNewMenu);

        JMenu mnNewMenu_1 = new JMenu("Registro");
        mnNewMenu_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuBar.add(mnNewMenu_1);

        JMenu mnNewMenu_3 = new JMenu("Alta");
        mnNewMenu_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_1.add(mnNewMenu_3);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Usuario");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirInternalFrame(new AltaUsuario(icontroladorUsuario));
                clearCurrentFrame();
            }
        });
        mntmNewMenuItem_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_3.add(mntmNewMenuItem_1);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("Actividad");
        mntmNewMenuItem_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirInternalFrame(new AltaActivadadDeportiva(icontroladorUsuario,icontroladorActividad));
                clearCurrentFrame();
            }
        });
        mntmNewMenuItem_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_3.add(mntmNewMenuItem_2);

        JMenuItem mntmNewMenuItem_3 = new JMenuItem("Clase");
        mntmNewMenuItem_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirInternalFrame(new alta_clase_Deportiva(icontroladorActividad, icontroladorClase));
                clearCurrentFrame();
            }
        });
        mntmNewMenuItem_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_3.add(mntmNewMenuItem_3);

        JMenu mnNewMenu_4 = new JMenu("Modificar");
        mnNewMenu_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_1.add(mnNewMenu_4);

        JMenuItem mntmNewMenuItem_4 = new JMenuItem("Usuario");
        mntmNewMenuItem_4.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                abrirInternalFrame(new ModificarUsuario(icontroladorUsuario));
                clearCurrentFrame();
        	}
        });
        mntmNewMenuItem_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_4.add(mntmNewMenuItem_4);

        JMenuItem mntmNewMenuItem_5 = new JMenuItem("Actividad");
        mntmNewMenuItem_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirInternalFrame(new ModificarActividadDeportiva(icontroladorActividad, icontroladorUsuario));
                clearCurrentFrame();
            }
        });
        mntmNewMenuItem_5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_4.add(mntmNewMenuItem_5);


        JMenuItem mntmNewMenuItem = new JMenuItem("Inscripciones");
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                abrirInternalFrame(new Inscripciones(icontroladorInscripcion, icontroladorActividad, icontroladorUsuario));
                clearCurrentFrame();
        	}
        });
        mntmNewMenuItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_1.add(mntmNewMenuItem);

        JMenu mnNewMenu_2 = new JMenu("Consultas");
        mnNewMenu_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuBar.add(mnNewMenu_2);

        JMenuItem mntmNewMenuItem_7 = new JMenuItem("Usuarios");
        mntmNewMenuItem_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MostrarUsuarios mostrarUsuariosFrame = new MostrarUsuarios(icontroladorUsuario, icontroladorInscripcion, icontroladorActividad, icontroladorClase);
                abrirInternalFrame(mostrarUsuariosFrame);
                clearCurrentFrame();
            }
        });
        mntmNewMenuItem_7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_2.add(mntmNewMenuItem_7);

        JMenuItem mntmNewMenuItem_8 = new JMenuItem("Actividades");
        mntmNewMenuItem_8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirInternalFrame(new consulta_Actividad(icontroladorActividad,icontroladorClase));
                clearCurrentFrame();
             
            }
        });
        mntmNewMenuItem_8.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_2.add(mntmNewMenuItem_8);

        JMenuItem mntmNewMenuItem_9 = new JMenuItem("Ranking actividad deportiva");
        mntmNewMenuItem_9.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                abrirInternalFrame(new RankingActividadDeportiva(icontroladorActividad));
                clearCurrentFrame();
        	}
        });
        mntmNewMenuItem_9.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mnNewMenu_2.add(mntmNewMenuItem_9);
    }
}
