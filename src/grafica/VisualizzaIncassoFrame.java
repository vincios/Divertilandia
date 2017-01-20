package grafica;


import entita.ParcoDivertimenti;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("ALL")
public class VisualizzaIncassoFrame extends JFrame{

    private DbHelper dbh;
    private JComboBox<ParcoDivertimenti> selezioneParco;
    private JTextField giornalieroTextField;
    private JTextField settimanaleTextField;
    private JTextField mensileTextField;

    public VisualizzaIncassoFrame() {
        dbh = new DbHelper();
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(10,10,10,10));

        content.add(createNorthPanel(), BorderLayout.NORTH);
        content.add(createCenterPanel(), BorderLayout.CENTER);

        selezioneParco.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParcoDivertimenti p = ((ParcoDivertimenti) selezioneParco.getSelectedItem());

                giornalieroTextField.setText(""+dbh.getIncassoGiornaliero(p.getNome()));
                settimanaleTextField.setText(""+dbh.getIncassoSettimanale(p.getNome()));
                mensileTextField.setText(""+dbh.getIncassoMensile(p.getNome()));

            }
        });
        setContentPane(content);
        setSize(650,180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridLayout(3,2));


        giornalieroTextField = new JTextField();
        settimanaleTextField = new JTextField();
        mensileTextField = new JTextField();

        giornalieroTextField.setEditable(false);
        settimanaleTextField.setEditable(false);
        mensileTextField.setEditable(false);

        JLabel giornalieroLbl  = new JLabel("Incasso giornaliero:");
        JLabel settimanaleLbl = new JLabel("Incasso settimanale (a partire da Lunedi):");
        JLabel mensileLbl = new JLabel("Incasso mensile (a partire dal primo del mese):");

        panel.add(giornalieroLbl);
        panel.add(giornalieroTextField);
        panel.add(settimanaleLbl);
        panel.add(settimanaleTextField);
        panel.add(mensileLbl);
        panel.add(mensileTextField);

        return panel;
    }


    private JPanel createNorthPanel() {
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

                    setFont(getFont().deriveFont(6));
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


}