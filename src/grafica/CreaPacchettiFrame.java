package grafica;


import entita.Pacchetto;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreaPacchettiFrame extends JFrame {
    private DbHelper dbh;
    private JTextField codicePacchettoTextField;
    private JTextField nomePacchettoTextField;
    private JTextField descrizionePacchettoTextField;
    private JTextField prezzoPacchettoTextField;
    private JTextField PartitaIVAAgenziaTextField;


    /*Da aggiungere una lista di servizi tra cui scegliere*/
    public CreaPacchettiFrame() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(5,5,5,5));
        dbh = new DbHelper();

        content.add(createNorthPanel(), BorderLayout.NORTH);
        content.add(createCenterPanel(), BorderLayout.CENTER);

        JButton b = new JButton("Aggiungi");

        b.addActionListener(e -> {

            try{
                Float.parseFloat(prezzoPacchettoTextField.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "prezzo pacchetto non valido", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Pacchetto p = new Pacchetto(
                    codicePacchettoTextField.getText(),
                    nomePacchettoTextField.getText(),
                    descrizionePacchettoTextField.getText(),
                    Float.parseFloat(prezzoPacchettoTextField.getText()),
                    PartitaIVAAgenziaTextField.getText());

            /*manca l'arraylist di pacchetti come input di insert pacchetto*/

            /*boolean result = dbh.insertPacchetto(p);

            if(result)
                JOptionPane.showMessageDialog(null, "Pacchetto aggiunto correttamente", "OK", JOptionPane.INFORMATION_MESSAGE);
*/
        });

        content.add(b, BorderLayout.SOUTH);
        setContentPane(content);
        setSize(450,250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private JPanel createNorthPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel jLabel = new JLabel("Completare i campi");
        jLabel.setFont(jLabel.getFont().deriveFont(6));
        panel.add(jLabel);
        return panel;
    }

    private JPanel createCenterPanel(){

        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel codicePacchettoLabel = new JLabel("Codice: ");
        JLabel nomePacchettoLabel = new JLabel("Nome: ");
        JLabel descrizionePacchettoLabel = new JLabel("Descrizione: ");
        JLabel prezzoPacchettoLabel = new JLabel("Prezzo: ");
        JLabel PartitaIvaAgenziaLabel = new JLabel("P. IVA Agenzia: ");

        codicePacchettoTextField = new JTextField(20);
        nomePacchettoTextField = new JTextField(15);
        descrizionePacchettoTextField = new JTextField(11);
        prezzoPacchettoTextField = new JTextField(30);
        PartitaIVAAgenziaTextField = new JTextField(30);


        panel.add(codicePacchettoLabel);
        panel.add(nomePacchettoTextField);
        panel.add(nomePacchettoLabel);
        panel.add(codicePacchettoTextField);
        panel.add(descrizionePacchettoLabel);
        panel.add(descrizionePacchettoTextField);
        panel.add(prezzoPacchettoLabel);
        panel.add(prezzoPacchettoTextField);
        panel.add(PartitaIvaAgenziaLabel);
        panel.add(PartitaIVAAgenziaTextField);

        return panel;
    }

}
