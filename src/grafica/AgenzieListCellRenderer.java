package grafica;

import entita.Agenzia;

import javax.swing.*;
import java.awt.*;

public class AgenzieListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if(value instanceof Agenzia ){
            Agenzia a = (Agenzia) value;
            setText(a.getNome() + ", " + a.getVia() + " " + a.getnCivico() + ", " + a.getCitta());
        }

        return c;
    }
}
