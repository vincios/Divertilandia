package grafica;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class CenterAlignTable extends JTable {

    public CenterAlignTable() {
        setAlignment();
    }

    public CenterAlignTable(TableModel dm) {
        super(dm);
        setAlignment();
    }

    private void setAlignment(){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );

        for(int x=0; x<getColumnCount(); x++){
            this.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
        }
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c =  super.prepareRenderer(renderer, row, column);

        if(!isCellSelected(row, column)) {

            if(row % 2 == 0) {
                c.setBackground(new Color(145, 187, 213, 50));
            }else{
                c.setBackground(getBackground());
            }
        }
        return c;
    }
}
