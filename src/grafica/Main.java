package grafica;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

public class Main {

    public static void main(String[] par){

        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());

        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.

            try {
                UIManager.setLookAndFeel(new NimbusLookAndFeel());
            } catch (UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            }
        }
       //new HomeFrame();
       //new AggiungiBigliettoFrame();

        new AgenziaFrame();
    }
}
