package grafica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AgenziaFrame extends JFrame {

    public AgenziaFrame() throws HeadlessException {
        setTitle("Agenzia");
        setSize(250,180);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(10,5,10,5));
        setContentPane(content);


        JButton creaPacchettiButton = new JButton("Crea pacchetto");
        JButton vendiPacchettiButton = new JButton("Vendi pacchetto");
        JButton visualizzaPacchettiButton = new JButton("Visualizza pacchetti venduti");

        JPanel buttonPanel = new JPanel(new GridLayout(3,1,2,2));
        buttonPanel.add(creaPacchettiButton);
        buttonPanel.add(vendiPacchettiButton);
        buttonPanel.add(visualizzaPacchettiButton);

        content.add(buttonPanel, BorderLayout.CENTER);

        creaPacchettiButton.addActionListener(e -> new CreaPacchettiFrame());

        //vendiPacchettiButton.addActionListener(e -> new VisualizzaPromozioniFrame());

        visualizzaPacchettiButton.addActionListener(e -> new VisualizzaPacchettiVendutiFrame());

        setVisible(true);
    }
}
