package grafica.gestore;

import entita.ParcoDivertimenti;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ParchiTableData extends AbstractTableModel {

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
