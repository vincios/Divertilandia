package grafica;


import entita.Cliente;
import entita.ParcoDivertimenti;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AggiungiBigliettoFrameOld extends JFrame{
    private JList<Cliente> selezioneCliente;
    private JList<ParcoDivertimenti> selezioneParco;
    private DefaultListModel<ParcoDivertimenti> parcoDefaultListModel;
    private DefaultListModel<Cliente> clienteDefaultListModel;
    private DbHelper dbh;

    JPanel content;
    public AggiungiBigliettoFrameOld(){
        content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(10,10,10,10));
        dbh = new DbHelper();

        content.add(createNorthPanel(), BorderLayout.NORTH);

        setContentPane(content);
        setSize(1300, 750);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private JPanel createNorthPanel() {
        JPanel panel = new JPanel(new GridLayout(1,2, 20,0));
        JPanel panelLeft = new JPanel(new BorderLayout());

        JLabel l = new JLabel("Seleziona un cliente:");
        l.setFont(l.getFont().deriveFont(50));
        panelLeft.add(l, BorderLayout.NORTH);

        clienteDefaultListModel = new DefaultListModel<>();
        selezioneCliente = new JList<>(clienteDefaultListModel);

        aggiornaClientiList();

        JLabel l2 = new JLabel("Oppure ");

        JButton b = new JButton("Aggiungi cliente");

        AggiungiClienteFrame fr = new AggiungiClienteFrame(null);

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.setVisible(true);
            }
        });
        JPanel pp = new JPanel(new FlowLayout());
        pp.add(l2);
        pp.add(b);

        selezioneCliente.setCellRenderer(new CustomJListCell());

        JScrollPane sp = new JScrollPane();
        sp.setPreferredSize(new Dimension(getWidth(), 200));
        sp.setViewportView(selezioneCliente);

        panelLeft.add(sp, BorderLayout.CENTER);
        panelLeft.add(pp, BorderLayout.SOUTH);

        panel.add(panelLeft);



        JPanel panelRigth = new JPanel(new BorderLayout());

        parcoDefaultListModel = new DefaultListModel<>();

        selezioneParco = new JList<>(parcoDefaultListModel);
        ArrayList<ParcoDivertimenti> parchi = dbh.getInfoParchiDivertimento();

        for (ParcoDivertimenti pd : parchi)
            parcoDefaultListModel.addElement(pd);

        selezioneParco.setCellRenderer(new CustomJListCell());

        panelRigth.add(new JLabel("Seleziona un parco: "), BorderLayout.NORTH);
        JScrollPane sp2 = new JScrollPane(selezioneParco);

        panelRigth.add(sp2, BorderLayout.CENTER);

        panel.add(panelRigth);
        return panel;
    }

    protected void aggiornaClientiList() {
        clienteDefaultListModel.removeAllElements();
        ArrayList<Cliente> clienti = dbh.getClienti();
        for (Cliente c : clienti)
            clienteDefaultListModel.addElement(c);
    }


}
