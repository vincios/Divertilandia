package grafica.gestore;

import entita.Agenzia;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AggiungiAgenziaFrame extends JFrame{
    private DbHelper dbh;
    private JTextField nomeAgenziaTextField;
    private JTextField pIvaTextField;
    private JTextField telefonoTextField;
    private JTextField cittaTextField;
    private JTextField viaTextField;
    private JTextField nCivicoTextField;


    public AggiungiAgenziaFrame() {
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
                    Integer.parseInt(telefonoTextField.getText());
                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null, "Formato telefono non valido", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Agenzia a = new Agenzia(
                        pIvaTextField.getText(),
                        nomeAgenziaTextField.getText(),
                        telefonoTextField.getText(),
                        cittaTextField.getText(),
                        viaTextField.getText(),
                        nCivicoTextField.getText());

                boolean result = dbh.insertAgenzia(a);

                if(result)
                    JOptionPane.showMessageDialog(null, "Attivita aggiunta correttamente", "OK", JOptionPane.INFORMATION_MESSAGE);

            }
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

        JLabel pIvaLbl = new JLabel("Partita IVA: ");
        JLabel nomeLbl = new JLabel("Nome: ");
        JLabel telefonoLbl = new JLabel("Telefono: ");
        JLabel cittaaLbl = new JLabel("Citta: ");
        JLabel viaLbl = new JLabel("Via: ");
        JLabel nCivicoLbl = new JLabel("Numero Civico: ");

        nomeAgenziaTextField = new JTextField(20);
        pIvaTextField = new JTextField(15);
        telefonoTextField = new JTextField(11);
        cittaTextField = new JTextField(30);
        viaTextField = new JTextField(30);
        nCivicoTextField = new JTextField(7);


        panel.add(pIvaLbl);
        panel.add(pIvaTextField);
        panel.add(nomeLbl);
        panel.add(nomeAgenziaTextField);
        panel.add(telefonoLbl);
        panel.add(telefonoTextField);
        panel.add(cittaaLbl);
        panel.add(cittaTextField);
        panel.add(viaLbl);
        panel.add(viaTextField);
        panel.add(nCivicoLbl);
        panel.add(nCivicoTextField);

        return panel;
    }
}
