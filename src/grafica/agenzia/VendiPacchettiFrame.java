package grafica.agenzia;

import entita.Agenzia;
import entita.Cliente;
import entita.Pacchetto;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class VendiPacchettiFrame extends JFrame {

    private Agenzia agenziaSelezionata;
    private DbHelper dbh;
    private DefaultListModel<Pacchetto> pacchettiDefaultListModel;
    private DefaultListModel<Cliente> clientiDefaultListModel;
    private JList<Pacchetto> pacchettiJList;
    private JList<Cliente> clientiJList;

    public VendiPacchettiFrame(Agenzia agenziaSelezionata) {
        this.agenziaSelezionata = agenziaSelezionata;
        this.dbh = new DbHelper();

        JPanel content = new JPanel(new BorderLayout());

        content.setBorder(new EmptyBorder(5,10,5,10));

        content.add(createNorthPanel(), BorderLayout.NORTH);
        content.add(createCenterPanel(), BorderLayout.CENTER);
        JButton b = new JButton("Vendi");

        b.addActionListener(e -> {

            Pacchetto selectedPacchetto = pacchettiJList.getSelectedValue();
            Cliente selectedCliente = clientiJList.getSelectedValue();

            if(selectedCliente == null || selectedPacchetto == null){
                JOptionPane.showMessageDialog(null, "Uno o piu campi non selezionati", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean result = result = dbh.vendiPacchetto(selectedPacchetto.getCodice(), selectedCliente.getCodiceFiscale());

            if(result)
                JOptionPane.showMessageDialog(null, "Pacchetto venduto correttamente", "OK", JOptionPane.INFORMATION_MESSAGE);

        });

        content.add(b, BorderLayout.SOUTH);
        setContentPane(content);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(750, 700);
        setVisible(true);
    }
    private JPanel createNorthPanel(){
        JPanel panel = new JPanel(new FlowLayout());

        JLabel agenziaLabel = new JLabel("Agenzia:");
        JLabel agenziaSelezionataLabel = new JLabel(agenziaSelezionata.getNome());

        panel.add(agenziaLabel);
        panel.add(agenziaSelezionataLabel);

        return panel;
    }
    private JPanel createCenterPanel(){
        JPanel panel = new JPanel(new BorderLayout());

        JLabel l = new JLabel("Selezionare un pacchetto e un cliente");
        JLabel l2 = new JLabel("Pacchetti");
        JLabel l3 = new JLabel("Clienti");

        JPanel centerPanel = new JPanel(new GridLayout(1,2,50,0));

        JPanel pacchettiPanel = new JPanel(new BorderLayout());
        JPanel clientiPanel = new JPanel(new BorderLayout());

        pacchettiPanel.add(l2, BorderLayout.NORTH);
        clientiPanel.add(l3, BorderLayout.NORTH);

        ArrayList<Pacchetto> pacchetti = null;
        for (Agenzia agenzia : dbh.getAgenzieConPacchettiInVendita()) {
            if(agenzia.getPartitaIva().equals(agenziaSelezionata.getPartitaIva())){
                pacchetti = agenzia.getPacchetti();
                break;
            }
        }

        if(pacchetti == null){
            JOptionPane.showMessageDialog(null,
                    "L'agenzia " + agenziaSelezionata.getNome() + " non ha pacchetti in vendita",
                    "Attenzione",
                    JOptionPane.WARNING_MESSAGE);
            pacchetti = new ArrayList<>();
        }

        ArrayList<Cliente> clienti = dbh.getClienti();

        pacchettiDefaultListModel = new DefaultListModel<>();
        clientiDefaultListModel = new DefaultListModel<>();

        for(Pacchetto p : pacchetti)
            pacchettiDefaultListModel.addElement(p);

        for (Cliente c : clienti)
            clientiDefaultListModel.addElement(c);

        pacchettiJList = new JList<>(pacchettiDefaultListModel);
        clientiJList = new JList<>(clientiDefaultListModel);

        pacchettiJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientiJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pacchettiJList.setCellRenderer(new PacchettiListCellRenderer());
        clientiJList.setCellRenderer(new ClientiListCellRenderer());

        pacchettiJList.setFixedCellHeight(27);
        clientiJList.setFixedCellHeight(27);

        JScrollPane ristorantiSP = new JScrollPane(pacchettiJList);
        JScrollPane hotelSP = new JScrollPane(clientiJList);

        pacchettiPanel.add(ristorantiSP, BorderLayout.CENTER);
        clientiPanel.add(hotelSP, BorderLayout.CENTER);

        centerPanel.add(pacchettiPanel);
        centerPanel.add(clientiPanel);

        panel.add(l, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
}


class ClientiListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Cliente c = ((Cliente) value);

        l.setText(c.getCognome() + " " + c.getNome() + " - " + c.getCodiceFiscale());

        if(!isSelected) {
            if (index % 2 == 0) {
                Color bg = new Color(145, 187, 213, 100);
                l.setBackground(bg);
            }else {
                setBackground(getBackground());
            }
        }

        return l;
    }
}

class PacchettiListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Pacchetto p = ((Pacchetto) value);

        l.setText(p.getNome() + " - PREZZO: " +  p.getPrezzo());

        if(!isSelected) {
            if (index % 2 == 0) {
                Color bg = new Color(145, 187, 213, 100);
                l.setBackground(bg);
            }else {
                setBackground(getBackground());
            }
        }

        return l;
    }
}