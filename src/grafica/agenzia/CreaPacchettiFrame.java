package grafica.agenzia;


import entita.Agenzia;
import entita.Pacchetto;
import entita.Servizio;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreaPacchettiFrame extends JFrame {
    private DbHelper dbh;
    private JTextField codicePacchettoTextField;
    private JTextField nomePacchettoTextField;
    private JTextArea descrizionePacchettoTextField;
    private JTextField prezzoPacchettoTextField;


    private DefaultListModel<Servizio> ristorantiDefaultListModel;
    private DefaultListModel<Servizio> hotelDefaultListModel;
    private JList<Servizio> hotelJList;
    private JList<Servizio> ristorantiJList;

    private Agenzia agenziaSelezionata;

    public CreaPacchettiFrame(Agenzia selezione) {
        this.agenziaSelezionata = selezione;

        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(10,10,5,5));
        dbh = new DbHelper();

        content.add(createNorthPanel(), BorderLayout.NORTH);
        content.add(createCenterPanel(), BorderLayout.CENTER);

        JButton b = new JButton("Aggiungi");

        b.addActionListener(e -> {



            String codice = codicePacchettoTextField.getText();
            String nome = nomePacchettoTextField.getText();
            String decr = descrizionePacchettoTextField.getText();

            ArrayList<String> servizi = new ArrayList<>();
            for (Servizio s : ristorantiJList.getSelectedValuesList()) {
                servizi.add(s.getPartitaIva());
            }
            for (Servizio s : hotelJList.getSelectedValuesList()) {
                servizi.add(s.getPartitaIva());
            }

            if(codice.trim().isEmpty() || nome.trim().isEmpty() || decr.trim().isEmpty() || servizi.isEmpty()){
                JOptionPane.showMessageDialog(null, "Uno o piu campi non compilati", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try{
                Float.parseFloat(prezzoPacchettoTextField.getText());
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(null, "Prezzo pacchetto non valido", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pacchetto p = new Pacchetto(
                    codice,
                    nome,
                    decr,
                    Float.parseFloat(prezzoPacchettoTextField.getText()),
                    agenziaSelezionata.getPartitaIva());


            boolean result;
            try {
                result = dbh.insertPacchetto(p, servizi);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "Estiste gia un pacchetto con codice " + codice, "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if(result)
                JOptionPane.showMessageDialog(null, "Pacchetto aggiunto correttamente", "OK", JOptionPane.INFORMATION_MESSAGE);



        });

        content.add(b, BorderLayout.SOUTH);
        setContentPane(content);
        setSize(685,570);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    private JPanel createNorthPanel(){
        JPanel panel = new JPanel(new BorderLayout());

        JLabel agenziaLabel = new JLabel("Agenzia:");
        JLabel agenziaSelezionataLabel = new JLabel(agenziaSelezionata.getNome());
        JLabel codicePacchettoLabel = new JLabel("Codice pacchetto:");
        JLabel nomePacchettoLabel = new JLabel("Nome:");
        JLabel descrizionePacchettoLabel = new JLabel("Descrizione:");
        JLabel prezzoPacchettoLabel = new JLabel("Prezzo:");

        codicePacchettoTextField = new JTextField(15);
        nomePacchettoTextField = new JTextField(30);
        descrizionePacchettoTextField = new JTextArea(3, 50);

        JScrollPane spDescr = new JScrollPane(descrizionePacchettoTextField);
        prezzoPacchettoTextField = new JTextField(5);

        JPanel northPanel = new JPanel(new BorderLayout());
        JPanel nNorthPanel = new JPanel(new FlowLayout());
        JPanel cNorthPanel = new JPanel(new FlowLayout());
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignOnBaseline(true);

        JPanel centerPanel = new JPanel(flowLayout);
        JPanel southPanel = new JPanel(new FlowLayout());

        nNorthPanel.add(agenziaLabel);
        nNorthPanel.add(agenziaSelezionataLabel);
        cNorthPanel.add(codicePacchettoLabel);
        cNorthPanel.add(codicePacchettoTextField);
        cNorthPanel.add(nomePacchettoLabel);
        cNorthPanel.add(nomePacchettoTextField);
        cNorthPanel.add(prezzoPacchettoLabel);
        cNorthPanel.add(prezzoPacchettoTextField);
        centerPanel.add(descrizionePacchettoLabel);
        centerPanel.add(spDescr);



        northPanel.add(nNorthPanel, BorderLayout.NORTH);
        northPanel.add(cNorthPanel, BorderLayout.CENTER);


        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }


    private JPanel createCenterPanel(){
        JPanel panel = new JPanel(new BorderLayout());

        JLabel l = new JLabel("Selezionare uno o piu servizi tra ristoranti e hotel");
        JLabel l2 = new JLabel("Ristoranti");
        JLabel l3 = new JLabel("Hotel");

        JPanel centerPanel = new JPanel(new GridLayout(1,2,50,0));

        JPanel ristorantiPanel = new JPanel(new BorderLayout());
        JPanel hotelPanel = new JPanel(new BorderLayout());

        ristorantiPanel.add(l2, BorderLayout.NORTH);
        hotelPanel.add(l3, BorderLayout.NORTH);

        ArrayList<Servizio> ristoranti = dbh.getServiziByTipo("ristorante");
        ArrayList<Servizio> hotel = dbh.getServiziByTipo("hotel");

        ristorantiDefaultListModel = new DefaultListModel<>();
        hotelDefaultListModel = new DefaultListModel<>();

        for(Servizio s : ristoranti)
            ristorantiDefaultListModel.addElement(s);

        for (Servizio s : hotel)
            hotelDefaultListModel.addElement(s);

        ristorantiJList = new JList<>(ristorantiDefaultListModel);
        hotelJList = new JList<>(hotelDefaultListModel);

        ristorantiJList.setCellRenderer(new ServiziListCellRenderer());
        hotelJList.setCellRenderer(new ServiziListCellRenderer());

        ristorantiJList.setFixedCellHeight(27);
        hotelJList.setFixedCellHeight(27);

        ristorantiJList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                }
                else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        hotelJList.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                }
                else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });


        JScrollPane ristorantiSP = new JScrollPane(ristorantiJList);
        JScrollPane hotelSP = new JScrollPane(hotelJList);

        ristorantiPanel.add(ristorantiSP, BorderLayout.CENTER);
        hotelPanel.add(hotelSP, BorderLayout.CENTER);

        centerPanel.add(ristorantiPanel);
        centerPanel.add(hotelPanel);

        panel.add(l, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
}


class ServiziListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Servizio s = ((Servizio) value);

        l.setText(s.getNome() + " - " + s.getVia() + " " + s.getnCivico() + ", " + s.getCitta());

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