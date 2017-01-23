package grafica;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

public class CenterAlignTable extends JTable {

    public CenterAlignTable(){
        super();
    }

    public CenterAlignTable(TableModel dm) {
        super(dm);
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

        if(c instanceof JLabel)
            ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
        return c;
    }
}
