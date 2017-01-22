package grafica;


import entita.Attivita;
import entita.ParcoDivertimenti;
import main.DbHelper;

import javax.swing.*;
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
        JTable t = new CenterAlignTable(td);
        t.setRowHeight(30);

        attivitaParcoFrame = null;

        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    String parcoSelezionato = t.getModel().getValueAt(row, 0).toString();
                    ArrayList<Attivita> att = dbh.getAttivitaParco(parcoSelezionato);
                    if (attivitaParcoFrame == null) {
                        attivitaParcoFrame = new AttivitaParcoFrame(att, parcoSelezionato);
                    } else {
                        attivitaParcoFrame.dispose();
                        attivitaParcoFrame = new AttivitaParcoFrame(att, parcoSelezionato);
                    }
                }
            }
        });
        content.add(new JScrollPane(t));

        setContentPane(content);

        setSize(1300,500);
        setTitle("Visualizzazione Parchi");
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

    public AttivitaParcoFrame(ArrayList<Attivita> attivita, String nomeParco) {
        JPanel content = new JPanel(new BorderLayout());
        AttivitaTableModel tm = new AttivitaTableModel(attivita, false);

        JTable t = new CenterAlignTable(tm);

        t.setRowHeight(25);

        JScrollPane p = new JScrollPane(t);

        content.add(p, BorderLayout.CENTER);

        setContentPane(content);
        setSize(800,300);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setTitle("Visualizzazione attivita per " + nomeParco);
        setVisible(true);
    }
}

class AttivitaTableModel extends AbstractTableModel {

    private ArrayList<Attivita> attivita;
    private ArrayList<Boolean> selectedRows;
    private boolean needCheckBox;

    public AttivitaTableModel(ArrayList<Attivita> attivita, boolean needCheckBox) {
        this.attivita = attivita;
        this.needCheckBox = needCheckBox;
        this.selectedRows = new ArrayList<>(attivita.size());
        if(needCheckBox)
            addChechBox();
    }
    public AttivitaTableModel(boolean needCheckBox) {
        this.attivita = new ArrayList<>();
        this.needCheckBox = needCheckBox;
        this.selectedRows = new ArrayList<>(attivita.size());
        if(needCheckBox)
            addChechBox();
    }
    public void setData(ArrayList<Attivita> attivita){
        this.attivita = attivita;
        if(needCheckBox)
            addChechBox();
        fireTableDataChanged();
    }

    public void checkAll(){
        if(needCheckBox){
            for (int i = 0; i < attivita.size(); i++) {
                setValueAt(true, i, 0);
            }
        }
    }

    public void checkNone(){
        if(needCheckBox){
            for (int i = 0; i < attivita.size(); i++) {
                setValueAt(false, i, 0);
            }
        }
    }

    public ArrayList<Attivita> getAttivitaSelezionate(){
        ArrayList<Attivita> a = new ArrayList<>();

        for(int i = 0; i<selectedRows.size(); i++){
            if(selectedRows.get(i)){
                a.add(attivita.get(i));
            }
        }

        return a;
    }
    private void addChechBox(){
        if(selectedRows.size() > 0){
            for(int i = selectedRows.size()-1; i>=0; i--){
                selectedRows.remove(i);
            }
        }
        for (int i = 0; i < attivita.size(); i++) {
            selectedRows.add(false);
        }

    }

    @Override
    public int getRowCount() {
        return attivita.size();
    }

    @Override
    public int getColumnCount() {
        if(needCheckBox)
            return 6;
        return 4;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(needCheckBox)
            if(columnIndex == 0) return Boolean.class;

        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(needCheckBox)
            if(columnIndex == 0)
                return true;

        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(needCheckBox)
            if(columnIndex == 0)
                selectedRows.set(rowIndex, ((boolean) aValue));

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(needCheckBox) {
            switch (columnIndex) {
                case 0:
                    return selectedRows.get(rowIndex);
                case 1:
                    return attivita.get(rowIndex).getNome();
                case 2:
                    return attivita.get(rowIndex).getOrarioApertura();
                case 3:
                    return attivita.get(rowIndex).getOrarioChiusura();
                case 4:
                    return attivita.get(rowIndex).getCosto();
                case 5:
                    return attivita.get(rowIndex).getPrezzoScontato();
                default:
                    return "nd";
            }
        }else{
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
    }

    @Override
    public String getColumnName(int column) {
        if(needCheckBox){
            switch (column) {
                case 0: return "";
                case 1:
                    return "Nome";
                case 2:
                    return "Apertura";
                case 3:
                    return "Chiusura";
                case 4:
                    return "Prezzo originale";
                case 5:
                    return "Prezzo (con eventuali sconti)";
                default:
                    return "";
            }
        }else{
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
