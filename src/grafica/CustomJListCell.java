package grafica;

import javax.swing.*;
import java.awt.*;
public class CustomJListCell extends DefaultListCellRenderer {
    public CustomJListCell() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        setText(value.toString());
        if(!isSelected) {
          if (index % 2 == 0) {
                Color bg = new Color(145, 187, 213, 100);
                c.setBackground(bg);
            }else {
              setBackground(getBackground());
            }
        }

        return c;

    }
}