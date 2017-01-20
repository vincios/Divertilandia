package grafica;

import entita.Attivita;
import entita.ParcoDivertimenti;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class AggiungiAttivitaFrame extends JFrame{
    private DbHelper dbh;
    private JComboBox<ParcoDivertimenti> selezioneParco;
    private JTextField nomeAttivitaTextField;
    private JFormattedTextField oraAperturaTextField;
    private JFormattedTextField oraChiusuraTextField;
    private JTextField costoTextField;


    public AggiungiAttivitaFrame() throws HeadlessException {
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(5,5,5,5));
        dbh = new DbHelper();

        content.add(createNorthPanel(), BorderLayout.NORTH);
        content.add(createCenterPanel(), BorderLayout.CENTER);

        JButton b = new JButton("Aggiungi");

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float costo;
                try {
                    costo = Float.parseFloat(costoTextField.getText().replace(',','.'));
                }catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Formato Costo non valido", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String nomeParco = ((ParcoDivertimenti) selezioneParco.getSelectedItem()).getNome();

                Attivita a = new Attivita(nomeAttivitaTextField.getText(),
                        oraAperturaTextField.getText(),
                        oraChiusuraTextField.getText(),
                        costo,
                        nomeParco);
                boolean result = dbh.insertAttivita(a);

                if(result)
                    JOptionPane.showMessageDialog(null, "Attivita aggiunta correttamente", "OK", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        content.add(b, BorderLayout.SOUTH);
        setContentPane(content);
        setSize(600,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createNorthPanel(){
        ArrayList<ParcoDivertimenti> parchi = dbh.getInfoParchiDivertimento();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        selezioneParco = new JComboBox<>();

        selezioneParco.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                 Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                 if(value instanceof ParcoDivertimenti){
                     ParcoDivertimenti p = (ParcoDivertimenti) value;
                     setText(p.getNome() + ", " + p.getSede() + ", " + p.getTipo().substring(0,1).toUpperCase()+p.getTipo().substring(1));


                 }

                 return c;
            }
        });

        for(ParcoDivertimenti p : parchi)
            selezioneParco.addItem(p);

        panel.add(new JLabel("Seleziona un parco: "));

        panel.add(selezioneParco);

        return panel;
    }

    private JPanel createCenterPanel(){
        JPanel panel = new JPanel(new FlowLayout());

        JLabel nomeLbl = new JLabel("Nome: ");
        JLabel oraAperturaLbl = new JLabel("Orario apertura: ");
        JLabel oraChiusuraLbl = new JLabel("Orario chiusura: ");
        JLabel costoLbl = new JLabel("Costo: ");


        nomeAttivitaTextField = new JTextField();
        oraAperturaTextField = new JFormattedTextField();
        oraChiusuraTextField = new JFormattedTextField();
        costoTextField = new JTextField();

        oraAperturaTextField.setColumns(3);
        oraChiusuraTextField.setColumns(3);
        nomeAttivitaTextField.setColumns(30);
        costoTextField.setColumns(7);


        try {
            MaskFormatter aperturaMask = new MaskFormatter("##:##");
            MaskFormatter chiusuraMask = new MaskFormatter("##:##");

            aperturaMask.install(oraAperturaTextField);
            chiusuraMask.install(oraChiusuraTextField);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        panel.add(nomeLbl);
        panel.add(nomeAttivitaTextField);
        panel.add(costoLbl);
        panel.add(costoTextField);
        panel.add(oraAperturaLbl);
        panel.add(oraAperturaTextField);
        panel.add(oraChiusuraLbl);
        panel.add(oraChiusuraTextField);

        return panel;
    }
}
