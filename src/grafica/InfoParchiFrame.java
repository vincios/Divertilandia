package grafica;


import entita.ParcoDivertimenti;
import main.DbHelper;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InfoParchiFrame extends JFrame{

    public InfoParchiFrame() throws HeadlessException {
        DbHelper dbh = new DbHelper();
        ArrayList<ParcoDivertimenti> parchi = dbh.getInfoParchiDivertimento();

        JPanel content = new JPanel(new BorderLayout());
        TableData td = new TableData(parchi);
        JTable t = new JTable(td);
        t.setRowHeight(30);
        content.add(new JScrollPane(t));

        setContentPane(content);

        setSize(1200,300);
        setVisible(true);
    }
}

class TableData extends AbstractTableModel{

    ArrayList<ParcoDivertimenti> parchi;

    public TableData(ArrayList<ParcoDivertimenti> parchi) {
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
            case 2: return parchi.get(rowIndex).getNumeriTelefonici().toString();
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
            case 5: return "Percorso";
            case 6: return "Tema";
            case 7: return "N. Piscine";
            case 8: return "Biglietti Venduti";
            default: return "";
        }
    }
}
