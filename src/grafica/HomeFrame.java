package grafica;

import grafica.agenzia.AgenziaFrame;
import grafica.agenzia.SelezioneAgenziaFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomeFrame extends JFrame {

    public HomeFrame(){
        setSize(400, 100);
        setTitle("Divertilandia");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(content);

        JLabel titolo = new JLabel("Entra come:", SwingConstants.HORIZONTAL);
        content.add(titolo, BorderLayout.NORTH);

        JButton gestoreButton = new JButton("Gestore");
        JButton agenziaButton = new JButton("Agenzia");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(gestoreButton);
        buttonPanel.add(agenziaButton);
        content.add(buttonPanel, BorderLayout.CENTER);

        gestoreButton.addActionListener(e -> new GestoreFrame());

        agenziaButton.addActionListener(e -> new SelezioneAgenziaFrame());

        setVisible(true);
    }
}
