package grafica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestoreFrame extends JFrame {

    public GestoreFrame() throws HeadlessException {
        setTitle("Gestore");
        setSize(250,400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(content);


        JButton parchiButton = new JButton("Visualizza Parchi");
        JButton promozioniButton = new JButton("Visualizza Promozioni");
        JButton pacchettiButton = new JButton("Visualizza Pacchetti");
        JButton attivitaButton = new JButton("Inserisci attività");
        JButton agenziaButton = new JButton("Inserisci agenzia");
        JButton bigliettiButton = new JButton("Vendi biglietto");
        JButton incassoButton = new JButton("Visualizza Incasso");

        JPanel buttonPanel = new JPanel(new GridLayout(7,1,10,10));
        buttonPanel.add(parchiButton);
        buttonPanel.add(promozioniButton);
        buttonPanel.add(pacchettiButton);
        buttonPanel.add(attivitaButton);
        buttonPanel.add(agenziaButton);
        buttonPanel.add(bigliettiButton);
        buttonPanel.add(incassoButton);

        content.add(buttonPanel, BorderLayout.CENTER);

        parchiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InfoParchiFrame();
            }
        });

        promozioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizzaPromozioniFrame();
            }
        });

        setVisible(true);
    }
}

