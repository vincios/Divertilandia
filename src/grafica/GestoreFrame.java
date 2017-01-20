package grafica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        content.setBorder(new EmptyBorder(10,5,10,5));
        setContentPane(content);


        JButton parchiButton = new JButton("Visualizza Parchi");
        JButton promozioniButton = new JButton("Visualizza Promozioni");
        JButton pacchettiButton = new JButton("Visualizza Pacchetti in vendita");
        JButton attivitaButton = new JButton("Inserisci attivita");
        JButton agenziaButton = new JButton("Inserisci agenzia");
        JButton bigliettiButton = new JButton("Vendi biglietto");
        JButton incassoButton = new JButton("Visualizza Incasso");

        JPanel buttonPanel = new JPanel(new GridLayout(7,1,2,2));
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

        pacchettiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InfoPacchettiFrame();
            }
        });

        attivitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AggiungiAttivitaFrame();
            }
        });

        agenziaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AggiungiAgenziaFrame();
            }
        });

        incassoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizzaIncassoFrame();
            }
        });

        setVisible(true);
    }
}

