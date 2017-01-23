package grafica;

import entita.Attivita;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class AttivitaTableModel extends AbstractTableModel {

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
