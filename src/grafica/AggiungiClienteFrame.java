package grafica;


import entita.Cliente;
import grafica.gestore.AggiungiBigliettoFrame;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class AggiungiClienteFrame extends JFrame{
    private DbHelper dbh;
    private JTextField cfTextField;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField giornoTextField;
    private JTextField meseTextField;
    private JTextField annoTextField;


    public AggiungiClienteFrame(AggiungiBigliettoFrame f) {
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(5,5,5,5));
        dbh = new DbHelper();

        content.add(createNorthPanel(), BorderLayout.NORTH);
        content.add(createCenterPanel(), BorderLayout.CENTER);

        JButton b = new JButton("Aggiungi");

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    Integer.parseInt(giornoTextField.getText());
                    Integer.parseInt(meseTextField.getText());
                    Integer.parseInt(annoTextField.getText());
                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null, "Formato data non valido", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String sddn = annoTextField.getText() + "-" +
                        meseTextField.getText() + "-" +
                        giornoTextField.getText();
                Date ddn;

                try {
                    ddn = Date.valueOf(sddn);
                } catch (IllegalArgumentException e1){
                    JOptionPane.showMessageDialog(null, "Formato data non valido", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Cliente a = new Cliente(
                        cfTextField.getText().toUpperCase(),
                        nomeTextField.getText(),
                        cognomeTextField.getText(),
                        ddn);

                boolean result = false;
                try {
                    result = dbh.insertCliente(a);
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Esiste gia un cliente con tale codice fiscale", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if(result) {
                    JOptionPane.showMessageDialog(null, "Cliente aggiunto correttamente", "OK", JOptionPane.INFORMATION_MESSAGE);

                    if(f != null) {
                        f.aggiornaClientiTable();
                        dispose();
                    }
                }
            }
        });

        content.add(b, BorderLayout.SOUTH);
        setContentPane(content);
        setSize(300,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private JPanel createNorthPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label = new JLabel("Completare i campi");
        label.setFont(label.getFont().deriveFont(6));
        panel.add(label);
        return panel;
    }

    private JPanel createCenterPanel(){

        JPanel panel = new JPanel(new GridLayout(6, 2, 0,5));

        panel.setBorder(new EmptyBorder(0,0,10,0));
        JLabel cfLbl = new JLabel("Codice Fiscale: ");
        JLabel nomeLbl = new JLabel("Nome: ");
        JLabel cognomeLbl = new JLabel("Cognome: ");
        JLabel gNascitaLbl = new JLabel("Giorno di nascita: ");
        JLabel mNascitaLbl = new JLabel("Mese di nascita: ");
        JLabel aNascitaLbl = new JLabel("Anno di nascita: ");

        cfTextField = new JTextField(20);
        nomeTextField = new JTextField(15);
        cognomeTextField = new JTextField(11);
        giornoTextField = new JTextField(30);
        meseTextField = new JTextField(30);
        annoTextField = new JTextField(7);


        panel.add(cfLbl);
        panel.add(cfTextField);
        panel.add(nomeLbl);
        panel.add(nomeTextField);
        panel.add(cognomeLbl);
        panel.add(cognomeTextField);
        panel.add(gNascitaLbl);
        panel.add(giornoTextField);
        panel.add(mNascitaLbl);
        panel.add(meseTextField);
        panel.add(aNascitaLbl);
        panel.add(annoTextField);

        return panel;
    }
}
