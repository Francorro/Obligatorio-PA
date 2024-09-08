package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Clases.IControladorActividad;
import Clases.DataActividad;



public class RankingActividadDeportiva extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private JTable rankingTable;
    private List<DataActividad> actividades;
    private DefaultTableModel model;
    private IControladorActividad icontroladorActividad;

    public RankingActividadDeportiva(IControladorActividad icontroladorActi) {
    	 icontroladorActividad =  icontroladorActi;
        setTitle("Ranking de Actividades Deportivas");
        setBounds(100, 100, 600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setClosable(true);   
        setIconifiable(true); 
        setResizable(true); // 
        setMaximizable(true); 

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        String[] columnNames = { "Nombre de Actividad", "Cantidad de Clases" };
        model = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        rankingTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(rankingTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar");
        panel.add(btnActualizar, BorderLayout.SOUTH);

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRankingData();
            }
        });

        loadRankingData();
    }

    private void loadRankingData() {
        // Recargar la lista de actividades desde el controlador
        actividades = icontroladorActividad.transformarActividades();

        // Limpiar las filas existentes en el modelo
        model.setRowCount(0);

        // Contar el número de clases para cada actividad
        Map<DataActividad, Long> activityClassCount = actividades.stream()
            .collect(Collectors.toMap(
                actividad -> actividad,
                actividad -> (long) icontroladorActividad.transformarClasesActividad(actividad).size()
            ));

        // Ordenar las actividades primero por número de clases en orden descendente, 
        // y luego por nombre en orden ascendente si tienen la misma cantidad de clases
        List<Map.Entry<DataActividad, Long>> sortedActivities = activityClassCount.entrySet().stream()
            .sorted((entry1, entry2) -> {
                // Comparar por número de clases (descendente)
                int classCountComparison = entry2.getValue().compareTo(entry1.getValue());
                if (classCountComparison != 0) {
                    return classCountComparison;
                }
                // Si el número de clases es el mismo, comparar por nombre de actividad (ascendente)
                return entry1.getKey().getNombre().compareTo(entry2.getKey().getNombre());
            })
            .collect(Collectors.toList());

        // Rellenar la tabla con los datos de las actividades ordenadas
        for (Map.Entry<DataActividad, Long> entry : sortedActivities) {
            DataActividad actividad = entry.getKey();
            Long classCount = entry.getValue();
            Object[] rowData = {
                actividad.getNombre(),
                classCount
            };
            model.addRow(rowData);
        }
    }
}
