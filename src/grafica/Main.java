package grafica;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] par){
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null)
            defaults.put("Table.alternateRowColor", new Color(145, 187, 213, 50));
        new HomeFrame();
    }
}
