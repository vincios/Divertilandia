package grafica;


import entita.Pacchetto;
import entita.Servizio;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;


/*Funziona, bisogna aggiungere o un input text in cui inserire la parita iva dell'agenzia, oppure
    mettere la lista di agenzie tra cui scegliere, per ora l'ho messa fissa "GNZS".

    Ho modificato la Query 11 adesso il click su uno dei pacchetti fa visualizzare i servizi inclusi in tale pacchetto*/

public class VisualizzaPacchettiVendutiFrame extends JFrame{

    public VisualizzaPacchettiVendutiFrame() {
        DbHelper dbh = new DbHelper();
        ArrayList<Pacchetto> pacchetti = dbh.getPacchettiVendutiDaAgenzia("GNZS");


        JPanel content = new JPanel(new BorderLayout());
        PacchettiTableDataNoAgenzie td = new PacchettiTableDataNoAgenzie(pacchetti);

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

class PacchettiTableDataNoAgenzie extends AbstractTableModel {

    private ArrayList<Pacchetto> pacchetti;

    public PacchettiTableDataNoAgenzie(ArrayList<Pacchetto> pacchetti) {
        this.pacchetti = pacchetti;
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
            case 4: return pacchetti.get(rowIndex).getpIvaAgenzia();
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
