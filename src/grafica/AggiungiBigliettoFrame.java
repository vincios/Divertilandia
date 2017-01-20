package grafica;


import entita.Cliente;
import main.DbHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AggiungiBigliettoFrame extends JFrame{
    private JList<Cliente> selezioneCliente;
    private DefaultListModel<Cliente> clienteDefaultListModel;
    private DbHelper dbh;

    JPanel content;
    public AggiungiBigliettoFrame(){
        content = new JPanel(new BorderLayout());
        dbh = new DbHelper();

        content.add(createNorthPanel(), BorderLayout.NORTH);

        setContentPane(content);
        setSize(600, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    private JPanel createNorthPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel l = new JLabel("Seleziona un cliente");
        panel.add(l, BorderLayout.NORTH);

        clienteDefaultListModel = new DefaultListModel<>();
        selezioneCliente = new JList<>(clienteDefaultListModel);

        aggiornaClientiList();

        JLabel l2 = new JLabel("Oppure ");

        JButton b = new JButton("Aggiungi cliente");

        AggiungiClienteFrame fr = new AggiungiClienteFrame(this);

        JPanel p = new JPanel(new FlowLayout());
        JButton b2 = new JButton("fe");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(content);
                setSize(getWidth()-1, getHeight());
                setSize(getWidth()+1, getHeight());
            }
        });
        p.add(b2);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //fr.setVisible(true);
                setContentPane(p);
                setSize(getWidth()-1, getHeight());
                setSize(getWidth()+1, getHeight());
            }
        });
        JPanel pp = new JPanel(new FlowLayout());
        pp.add(l2);
        pp.add(b);

        selezioneCliente.setCellRenderer(new CustomJListCell());

        JScrollPane sp = new JScrollPane();
        sp.setPreferredSize(new Dimension(getWidth(), 100));
        sp.setViewportView(selezioneCliente);

        JPanel centerPanel = new JPanel(new GridLayout(1,2));
        centerPanel.add(sp);
        centerPanel.add(pp);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    public void aggiornaClientiList() {
        clienteDefaultListModel.removeAllElements();
        ArrayList<Cliente> clienti = dbh.getClienti();
        for (Cliente c : clienti)
            clienteDefaultListModel.addElement(c);
    }
}
