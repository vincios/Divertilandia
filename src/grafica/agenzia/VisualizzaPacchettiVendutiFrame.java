package grafica.agenzia;


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


public class VisualizzaPacchettiVendutiFrame extends JFrame{

    private Agenzia agenziaSelezionata;
    private float incassoTotale;

    public VisualizzaPacchettiVendutiFrame(Agenzia agenziaSelezionata) {
        this.agenziaSelezionata = agenziaSelezionata;
        this.incassoTotale = 0;
        DbHelper dbh = new DbHelper();
        ArrayList<Pacchetto> pacchetti = dbh.getPacchettiVendutiDaAgenzia(agenziaSelezionata.getPartitaIva());

        for (Pacchetto p : pacchetti) {
            incassoTotale += p.getPrezzo();
        }

        Pacchetto incassoTotale = new Pacchetto("Incasso totale", "", "", this.incassoTotale,"");
        pacchetti.add(incassoTotale);
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

        content.add(createNorthPanel(), BorderLayout.NORTH);
        southPanel.add(jList, BorderLayout.CENTER);
        content.add(southPanel, BorderLayout.SOUTH);
        setContentPane(content);

        setSize(1200,450);
        setVisible(true);
    }

    private JPanel createNorthPanel(){
        JPanel panel = new JPanel(new FlowLayout());

        JLabel agenziaLabel = new JLabel("Pacchetti venduti dall'agenzia:");
        JLabel agenziaSelezionataLabel = new JLabel(agenziaSelezionata.getNome());

        panel.add(agenziaLabel);
        panel.add(agenziaSelezionataLabel);

        return panel;
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
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return pacchetti.get(rowIndex).getCodice();
            case 1: return pacchetti.get(rowIndex).getNome();
            case 2: return pacchetti.get(rowIndex).getDescrizione();
            case 3: return pacchetti.get(rowIndex).getPrezzo();
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
            default: return "";
        }
    }

}
