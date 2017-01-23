package grafica.gestore;


import entita.Agenzia;
import entita.Pacchetto;
import entita.Servizio;
import grafica.CenterAlignTable;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoPacchettiFrame extends JFrame {

    public InfoPacchettiFrame() {
        DbHelper dbh = new DbHelper();
        ArrayList<Agenzia> fromDB = dbh.getAgenzieConPacchettiInVendita();
        Map<String, Agenzia> agenzie = new HashMap<>();
        ArrayList<Pacchetto> pacchetti = new ArrayList<>();


        for(Agenzia a : fromDB){
            for(Pacchetto p : a.getPacchetti()) {
                pacchetti.add(p);
            }

            agenzie.put(a.getPartitaIva(), a);
        }
        JPanel content = new JPanel(new BorderLayout());
        PacchettiTableData td = new PacchettiTableData(pacchetti, agenzie);

        JTable t = new CenterAlignTable(td);
        t.setRowHeight(30);
        content.add(new JScrollPane(t), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(new EmptyBorder(10,10,5,10));
        JLabel titolo = new JLabel("Il pacchetto comprende:");

        southPanel.add(titolo, BorderLayout.NORTH);
        DefaultListModel<String> dm = new DefaultListModel<>();
        JList<String> jList = new JList<>(dm);
        jList.setFont(jList.getFont().deriveFont(6));
        dm.addElement("");
        dm.addElement("");
        dm.addElement("");

        t.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                dm.removeAllElements();
                for (Servizio s : pacchetti.get(t.getSelectedRow()).getServizi()) {
                    dm.addElement(s.getInfo());
                }
            }
        });

        southPanel.add(jList, BorderLayout.CENTER);
        content.add(southPanel, BorderLayout.SOUTH);
        setContentPane(content);

        setSize(1200,450);
        setVisible(true);
    }
}




class PacchettiTableData extends AbstractTableModel {

    private ArrayList<Pacchetto> pacchetti;
    private Map<String, Agenzia> agenzie;

    public PacchettiTableData(ArrayList<Pacchetto> pacchetti, Map<String, Agenzia> agenzie) {
        this.pacchetti = pacchetti;
        this.agenzie = agenzie;
    }

    @Override
    public int getRowCount() {
        return pacchetti.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return pacchetti.get(rowIndex).getCodice();
            case 1: return pacchetti.get(rowIndex).getNome();
            case 2: return pacchetti.get(rowIndex).getDescrizione();
            case 3: return pacchetti.get(rowIndex).getPrezzo();
            case 4: return agenzie.get(pacchetti.get(rowIndex).getpIvaAgenzia()).getNome();
            default: return "nd";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Codice";
            case 1: return "Nome";
            case 2: return "Descrizione";
            case 3: return "Prezzo";
            case 4: return "Agenzia";
            default: return "";
        }
    }
}