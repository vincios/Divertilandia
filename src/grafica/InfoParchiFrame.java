package grafica;


import entita.Attivita;
import entita.ParcoDivertimenti;
import main.DbHelper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InfoParchiFrame extends JFrame{

    private DbHelper dbh;
    private AttivitaParcoFrame attivitaParcoFrame;

    public InfoParchiFrame() throws HeadlessException {
        dbh = new DbHelper();
        ArrayList<ParcoDivertimenti> parchi = dbh.getInfoParchiDivertimento();

        JPanel content = new JPanel(new BorderLayout());
        ParchiTableData td = new ParchiTableData(parchi);
        JTable t = new JTable(td);
        t.setRowHeight(30);

        attivitaParcoFrame = null;

        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    ArrayList<Attivita> att = dbh.getAttivitaParco(t.getModel().getValueAt(row, 0).toString());
                    if (attivitaParcoFrame == null) {
                        attivitaParcoFrame = new AttivitaParcoFrame(att);
                    } else {
                        attivitaParcoFrame.dispose();
                        attivitaParcoFrame = new AttivitaParcoFrame(att);
                    }
                }
            }
        });
        content.add(new JScrollPane(t));

        //content.add(creaEastPanel(), BorderLayout.SOUTH);
        setContentPane(content);

        setSize(1300,500);
        setVisible(true);
    }


}

class ParchiTableData extends AbstractTableModel{

    ArrayList<ParcoDivertimenti> parchi;

    public ParchiTableData(ArrayList<ParcoDivertimenti> parchi) {
        this.parchi = parchi;
    }

    @Override
    public int getRowCount() {
        return parchi.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return parchi.get(rowIndex).getNome();
            case 1: return parchi.get(rowIndex).getSede();
            case 2: return parchi.get(rowIndex).numeriTelefoniciToString();
            case 3: return parchi.get(rowIndex).getTipo();
            case 4: return ((parchi.get(rowIndex).getPercorso() == null) ? "nd" : parchi.get(rowIndex).getPercorso());
            case 5: return ((parchi.get(rowIndex).getTema() == null) ? "nd" : parchi.get(rowIndex).getTema());
            case 6: return ((parchi.get(rowIndex).getnPiscine() == 0) ? "nd" : parchi.get(rowIndex).getnPiscine());
            case 7: return parchi.get(rowIndex).getnBiglietti();
            default: return "nd";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Nome";
            case 1: return "Sede";
            case 2: return "Telefono";
            case 3: return "Tipo";
            case 4: return "Percorso";
            case 5: return "Tema";
            case 6: return "N. Piscine";
            case 7: return "Biglietti Venduti";
            default: return "";
        }
    }

}

class AttivitaParcoFrame extends JFrame {

    public AttivitaParcoFrame(ArrayList<Attivita> attivita) {
        JPanel content = new JPanel(new BorderLayout());
        AttivitaTableModel tm = new AttivitaTableModel(attivita);

        JTable t = new JTable(tm);

        t.setRowHeight(25);

        JScrollPane p = new JScrollPane(t);

        content.add(p, BorderLayout.CENTER);

        setContentPane(content);
        setSize(800,300);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    class AttivitaTableModel extends AbstractTableModel {

        private ArrayList<Attivita> attivita;

        public AttivitaTableModel(ArrayList<Attivita> attivita) {
            this.attivita = attivita;
        }

        @Override
        public int getRowCount() {
            return attivita.size();
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return attivita.get(rowIndex).getNome();
                case 1:
                    return attivita.get(rowIndex).getOrarioApertura();
                case 2:
                    return attivita.get(rowIndex).getOrarioChiusura();
                case 3:
                    return attivita.get(rowIndex).getCosto();
                default:
                    return "nd";
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Nome";
                case 1:
                    return "Apertura";
                case 2:
                    return "Chiusura";
                case 3:
                    return "Prezzo";
                default:
                    return "";
            }
        }
    }
}
