package grafica.agenzia;

import entita.Agenzia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AgenziaFrame extends JFrame {

    private Agenzia agenziaSelezionata;
    public AgenziaFrame(Agenzia agenziaSelezionata) {
        setTitle("Agenzia");

        this.agenziaSelezionata = agenziaSelezionata;
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel agenziaLabel = new JLabel("Agenzia:");
        JLabel selezioneLabel = new JLabel(agenziaSelezionata.getNome());

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

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(agenziaLabel);
        northPanel.add(selezioneLabel);

        content.add(buttonPanel, BorderLayout.CENTER);
        content.add(northPanel, BorderLayout.NORTH);

        creaPacchettiButton.addActionListener(e -> new CreaPacchettiFrame(agenziaSelezionata));

        vendiPacchettiButton.addActionListener(e -> new VendiPacchettiFrame(agenziaSelezionata));

        visualizzaPacchettiButton.addActionListener(e -> new VisualizzaPacchettiVendutiFrame(agenziaSelezionata));

       setSize(250,200);
        setVisible(true);
    }
}