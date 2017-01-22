package grafica.agenzia;


import entita.Agenzia;
import grafica.CenterAlignTable;
import main.DbHelper;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SelezioneAgenziaFrame extends JFrame {

    private DbHelper dbh;

    public SelezioneAgenziaFrame(){
        dbh = new DbHelper();
        ArrayList<Agenzia> agenzie = dbh.getAgenzie();

        JPanel content = new JPanel(new BorderLayout());
        JLabel jLabel = new JLabel("Seleziona l'agenzia con cui vuoi accedere");
        jLabel.setFont(jLabel.getFont().deriveFont(50));

        AgenzieTableData td = new AgenzieTableData(agenzie);
        JTable t = new CenterAlignTable(td);
        t.setRowHeight(30);

        JButton b = new JButton("Prosegui");

        b.addActionListener(e -> {
            if(t.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null,
                        "Selezionare un'agenzia",
                        "Errore",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Agenzia agenziaSelezionata = agenzie.get(t.getSelectedRow());
            new AgenziaFrame(agenziaSelezionata);
            dispose();
        });

        content.add(jLabel, BorderLayout.NORTH);
        content.add(new JScrollPane(t), BorderLayout.CENTER);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        p.add(b);

        content.add(p, BorderLayout.SOUTH);
        setContentPane(content);

        setSize(1300,500);
        setTitle("Seleziona Agenzia");
        setVisible(true);
    }


}

class AgenzieTableData extends AbstractTableModel {

    ArrayList<Agenzia> agenzie;

    public AgenzieTableData(ArrayList<Agenzia> agenzie) {
        this.agenzie = agenzie;
    }

    @Override
    public int getRowCount() {
        return agenzie.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return agenzie.get(rowIndex).getPartitaIva();
            case 1: return agenzie.get(rowIndex).getNome();
            case 2: return
                    agenzie.get(rowIndex).getVia() + " " + agenzie.get(rowIndex).getnCivico() + ", " + agenzie.get(rowIndex).getCitta();
            case 3: return agenzie.get(rowIndex).getTelefono();
            default: return "nd";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Partita Iva";
            case 1: return "Nome";
            case 2: return "Indirizzo";
            case 3: return "Telefono";
            default: return "";
        }
    }

}