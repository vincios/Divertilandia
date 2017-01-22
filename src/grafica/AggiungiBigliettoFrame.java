package grafica;

import entita.*;
import main.DbHelper;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AggiungiBigliettoFrame extends JFrame implements TableModelListener{

    private static final int FROM_DB = 0;
    private static final int FROM_GRUPPO = 2;
    private static final int FROM_ATTIVITA_PARCO = 1;
    private JPanel content;
    private JPanel primaPagina;
    private JPanel secondaPagina;
    private JPanel terzaPagina;
    private static final String SELEZIONE_CLIENTE =  "selezione_cliente";
    private static final String SELEZIONE_PARCO = "selezione_parco";
    private static final String SELEZIONE_ATTIVITA = "selezione_attivita";

    private ClientiTableData ctd;
    private ArrayList<Cliente> clienti;
    private ArrayList<Attivita> attivitaGruppo;
    private ArrayList<Attivita> attivitaParco;


    private JLabel parcoSelezionatoLabel;
    private JTextField prezzoTotaleField;
    private DefaultListModel<GruppoAttivita> gruppoAttivitaDefaultListModel;
    private AttivitaTableModel atm;
    private JTable tAttivita;

    private int attivitaMostrate;

    private DbHelper dbh;
    private Cliente clienteSelezionato;
    private ParcoDivertimenti parcoSelezionato;
    private BigDecimal prezzoTotale;

    public AggiungiBigliettoFrame() {
        dbh = new DbHelper();
        content = new JPanel(new CardLayout());

        primaPagina = createPrimaPagina();

        content.add(createPrimaPagina(), SELEZIONE_CLIENTE);
        secondaPagina = null;
        terzaPagina = null;

        prezzoTotale = new BigDecimal(BigInteger.ZERO);
        prezzoTotale = prezzoTotale.setScale(2, BigDecimal.ROUND_HALF_UP);
        setContentPane(content);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        swapToPage(SELEZIONE_CLIENTE);

        setTitle("Vendita biglietti");
        setSize(1350,600);
        setVisible(true);
    }

    private JPanel createPrimaPagina(){
        primaPagina = new JPanel(new BorderLayout());

        JPanel panelNorth = new JPanel(new FlowLayout());
        JPanel panelCenter = new JPanel(new BorderLayout());
        JPanel panelSouth = new JPanel(new GridLayout(1,2));

        JPanel southLeftPanel = new JPanel();
        JPanel southRightPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JLabel l = new JLabel("Seleziona un cliente:");
        JLabel l2 = new JLabel("Oppure  ");

        aggiornaClientiTable();

        JTable tClienti = new CenterAlignTable(ctd);
        JScrollPane csp = new JScrollPane(tClienti);

        JButton bNext = new JButton("Avanti");
        JButton bNew = new JButton("Aggiungi cliente");

        tClienti.setRowHeight(30);

        bNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tClienti.getSelectedRow() != -1) {
                    clienteSelezionato = clienti.get(tClienti.getSelectedRow());
                    swapToPage(SELEZIONE_PARCO);
                }else{
                    JOptionPane.showMessageDialog(null, "Nessun cliente selezionato", "ERRORE", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        AggiungiClienteFrame fr = new AggiungiClienteFrame(this);

        bNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fr.setVisible(true);
            }
        });

        panelNorth.add(l);
        panelCenter.add(csp, BorderLayout.CENTER);
        southRightPanel.add(bNext);

        southLeftPanel.add(l2);
        southLeftPanel.add(bNew);

        panelSouth.add(southLeftPanel);
        panelSouth.add(southRightPanel);
        primaPagina.add(panelNorth, BorderLayout.NORTH);
        primaPagina.add(panelCenter, BorderLayout.CENTER);
        primaPagina.add(panelSouth, BorderLayout.SOUTH);

        return primaPagina;
    }

    protected void aggiornaClientiTable() {
        clienti = dbh.getClienti();
        if(ctd == null)
            ctd = new ClientiTableData(clienti);
        else
            ctd.setData(clienti);
    }

    private JPanel createSecondaPagina(){
        secondaPagina = new JPanel(new BorderLayout());


        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JLabel clienteLabel = new JLabel("Cliente selezionato: ");

        JLabel clienteSelezionatoLabel = new JLabel();

        JLabel selezionaParcoLabel = new JLabel("Seleziona un parco: ");

        ArrayList<ParcoDivertimenti> parchi = dbh.getInfoParchiDivertimento();
        ParchiTableData pdm = new ParchiTableData(parchi);
        JTable parchiTable = new CenterAlignTable(pdm);
        parchiTable.setRowHeight(30  );

        JScrollPane psp = new JScrollPane(parchiTable);

        JButton prevBtn = new JButton("Indietro");
        JButton nextBtn = new JButton("Avanti");

        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapToPage(SELEZIONE_CLIENTE);
            }
        });

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(parchiTable.getSelectedRow() != -1) {
                    parcoSelezionato = parchi.get(parchiTable.getSelectedRow());
                    swapToPage(SELEZIONE_ATTIVITA);
                }else{
                    JOptionPane.showMessageDialog(null, "Nessun parco selezionato", "ERRORE", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        northPanel.add(clienteLabel);
        northPanel.add(clienteSelezionatoLabel);

        centerPanel.add(selezionaParcoLabel, BorderLayout.NORTH);
        centerPanel.add(psp, BorderLayout.CENTER);


        southPanel.add(prevBtn);
        southPanel.add(nextBtn);
        secondaPagina.add(northPanel, BorderLayout.NORTH);
        secondaPagina.add(centerPanel, BorderLayout.CENTER);
        secondaPagina.add(southPanel, BorderLayout.SOUTH);

        secondaPagina.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                if(clienteSelezionatoLabel != null)
                    clienteSelezionatoLabel.setText(clienteSelezionato.getCognome() + " " + clienteSelezionato.getNome());
            }
            public void ancestorRemoved(AncestorEvent event) { }
            public void ancestorMoved(AncestorEvent event) { }
        });

        return secondaPagina;
    }

    private JPanel createTerzaPagina(){
        terzaPagina = new JPanel(new BorderLayout());

        JPanel northPanel = new JPanel();
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new GridLayout(1,2));
        JPanel leftSouthPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        JPanel rightSouthPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        JPanel selezioneGruppoPanel = new JPanel(new BorderLayout());


        JLabel slezionaAttivita = new JLabel("Seleziona delle attivita oppure un gruppo attivita ad un prezzo speciale");
        JLabel parcoLabel = new JLabel("Parco selezionato: ");
        JLabel prezzoLabel = new JLabel("Prezzo totale: ");
        JLabel gruppiLabel = new JLabel("Gruppi di attivita disponibili:");
        JLabel clienteLabel = new JLabel("Cliente selezionato: ");

        JLabel clienteSelezionatoLabel = new JLabel();

        parcoSelezionatoLabel = new JLabel();

        gruppoAttivitaDefaultListModel = new DefaultListModel<>();

        JList<GruppoAttivita> gruppiAttivitaJList = new JList<>(gruppoAttivitaDefaultListModel);
        gruppiAttivitaJList.setCellRenderer(new GruppiAttivitaListCellRenderer());

        atm = new AttivitaTableModel(true);

        tAttivita = new CenterAlignTable(atm);
        tAttivita.getColumnModel().getColumn(0).setMinWidth(23);
        tAttivita.getColumnModel().getColumn(0).setMaxWidth(23);
        tAttivita.setRowHeight(25);
        tAttivita.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tAttivita.getModel().addTableModelListener(this);
        JScrollPane asp = new JScrollPane(tAttivita);
        JScrollPane grsp = new JScrollPane(gruppiAttivitaJList);

        prezzoTotaleField = new JTextField();
        prezzoTotaleField.setColumns(20);
        prezzoTotaleField.setEditable(false);
        prezzoTotaleField.setText(""+prezzoTotale);


        JButton prevBtn = new JButton("Indietro");
        JButton nextBtn = new JButton("Completa acquisto");
        JButton annullaSelezioneButton = new JButton("Annulla selezione");

        reloadAttivita(FROM_DB);
        reloadGruppiAttivita();

        prevBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapToPage(SELEZIONE_PARCO);
            }
        });

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Biglietto b = new Biglietto(
                        ""+System.currentTimeMillis(),
                        prezzoTotale.floatValue(),
                        new Date(System.currentTimeMillis()),
                        parcoSelezionato.getNome(),
                        clienteSelezionato.getCodiceFiscale());

                ArrayList<String> att = new ArrayList<>();
                if(atm.getAttivitaSelezionate().size() == 0){
                    JOptionPane.showMessageDialog(null, "Nessuna attivita selezionata", "ERRORE", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                for(Attivita a : atm.getAttivitaSelezionate()){
                    att.add(a.getNome());
                }

                boolean eseguito = dbh.insertBiglietto(b,att);
                if(eseguito) {
                    JOptionPane.showMessageDialog(null, "Biglietto aggiunto correttamente", "OK", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }else
                    JOptionPane.showMessageDialog(null, "Impossibile aggiungere biglietto", "ERRORE", JOptionPane.ERROR_MESSAGE);
            }
        });

        annullaSelezioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gruppiAttivitaJList.clearSelection();
                reloadAttivita(FROM_ATTIVITA_PARCO);
            }
        });

        gruppiAttivitaJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(gruppoAttivitaDefaultListModel.size() > 0) {
                    GruppoAttivita gruppoAttivita = gruppoAttivitaDefaultListModel.getElementAt(e.getFirstIndex());
                    attivitaGruppo = gruppoAttivita.getAttivita();
                    reloadAttivita(FROM_GRUPPO);
                    prezzoTotale = new BigDecimal(gruppoAttivita.getCostoPromozionale());
                    prezzoTotaleField.setText("" + prezzoTotale);
                }
            }
        });

        northPanel.add(clienteLabel);
        northPanel.add(clienteSelezionatoLabel);
        northPanel.add(parcoLabel);
        northPanel.add(parcoSelezionatoLabel);

        selezioneGruppoPanel.add(gruppiLabel, BorderLayout.NORTH);
        selezioneGruppoPanel.add(grsp, BorderLayout.CENTER);
        selezioneGruppoPanel.add(annullaSelezioneButton, BorderLayout.SOUTH);
        centerPanel.add(slezionaAttivita, BorderLayout.NORTH);
        centerPanel.add(asp, BorderLayout.CENTER);
        centerPanel.add(selezioneGruppoPanel, BorderLayout.SOUTH);

        leftSouthPanel.add(prezzoLabel);
        leftSouthPanel.add(prezzoTotaleField);

        rightSouthPanel.add(prevBtn);
        rightSouthPanel.add(nextBtn);

        southPanel.add(leftSouthPanel);
        southPanel.add(rightSouthPanel);

        terzaPagina.addAncestorListener(new AncestorListener() {
            @Override
            public void ancestorAdded(AncestorEvent event) {
                if( (parcoSelezionatoLabel != null) && !parcoSelezionatoLabel.getText().equals(parcoSelezionato.getNome()) ) {
                    reloadGruppiAttivita();
                    reloadAttivita(FROM_DB);
                }
                if(parcoSelezionatoLabel != null)
                    parcoSelezionatoLabel.setText(parcoSelezionato.getNome());

                if(clienteSelezionatoLabel != null)
                    clienteSelezionatoLabel.setText(clienteSelezionato.getCognome() + " " + clienteSelezionato.getNome());
            }
            public void ancestorRemoved(AncestorEvent event) { }
            public void ancestorMoved(AncestorEvent event) { }
        });

        terzaPagina.add(northPanel, BorderLayout.NORTH);
        terzaPagina.add(centerPanel, BorderLayout.CENTER);
        terzaPagina.add(southPanel, BorderLayout.SOUTH);

        return terzaPagina;
    }

    private void reloadGruppiAttivita() {
        ArrayList<GruppoAttivita> gruppi = dbh.getGruppiAttivita(parcoSelezionato.getNome());
        gruppoAttivitaDefaultListModel.clear();
        for(GruppoAttivita g : gruppi){
            gruppoAttivitaDefaultListModel.addElement(g);
        }

    }

    private void reloadAttivita(int mode){
        if(mode == FROM_DB){
            attivitaParco = dbh.getAttivitaConOfferteAttiveDiUnParco(parcoSelezionato.getNome(), new Date(System.currentTimeMillis()));
            atm.setData(attivitaParco);
            tAttivita.setRowSelectionAllowed(true);
            tAttivita.setEnabled(true);
            attivitaMostrate = FROM_ATTIVITA_PARCO;
            prezzoTotale = prezzoTotale.subtract(prezzoTotale);
            prezzoTotaleField.setText(""+prezzoTotale);
        }else if(mode == FROM_GRUPPO){
            atm.setData(attivitaGruppo);
            tAttivita.setRowSelectionAllowed(false);
            tAttivita.setEnabled(false);
            attivitaMostrate = FROM_GRUPPO;
            atm.checkAll();
        }else if(mode == FROM_ATTIVITA_PARCO ){
            atm.setData(attivitaParco);
            tAttivita.setRowSelectionAllowed(true);
            tAttivita.setEnabled(true);
            attivitaMostrate = FROM_ATTIVITA_PARCO;
            prezzoTotale = BigDecimal.ZERO;
            prezzoTotaleField.setText(""+prezzoTotale);
        }
    }

    private void swapToPage(String page){
        if(page.equals(SELEZIONE_PARCO)){
            if(secondaPagina == null){
                content.add(createSecondaPagina(), SELEZIONE_PARCO);
            }
        }else if(page.equals(SELEZIONE_ATTIVITA)){
            if(terzaPagina == null)
                content.add(createTerzaPagina(), SELEZIONE_ATTIVITA);

        }
        ((CardLayout) content.getLayout()).show(content, page);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if(e.getType() == TableModelEvent.UPDATE){
            if(e.getColumn() == 0){
                boolean attivitaSelezionata = ((boolean) atm.getValueAt(e.getFirstRow(), 0));
                if(attivitaSelezionata) {
                    Attivita a = null;
                    if(attivitaMostrate == FROM_ATTIVITA_PARCO )
                        a = attivitaParco.get(e.getFirstRow());
                    else
                        a = attivitaGruppo.get(e.getFirstRow());

                    prezzoTotale = prezzoTotale.add(a.getPrezzoScontato());
                    prezzoTotaleField.setText(""+prezzoTotale);
                }else{
                    Attivita a = null;
                    if(attivitaMostrate == FROM_ATTIVITA_PARCO )
                        a = attivitaParco.get(e.getFirstRow());
                    else
                        a = attivitaGruppo.get(e.getFirstRow());

                    prezzoTotale = prezzoTotale.subtract(a.getPrezzoScontato());
                    prezzoTotaleField.setText(""+prezzoTotale);
                }

            }
        }
    }
}

class ClientiTableData extends AbstractTableModel {

    private final SimpleDateFormat dateFormatter;
    private ArrayList<Cliente> clienti;

    public ClientiTableData(ArrayList<Cliente> clienti) {
        this.clienti = clienti;
        this.dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    }

    public void setData(ArrayList<Cliente> clienti){
        this.clienti = clienti;
        fireTableDataChanged();
    }
    @Override
    public int getRowCount() {
        return clienti.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return clienti.get(rowIndex).getCodiceFiscale();
            case 1: return clienti.get(rowIndex).getCognome();
            case 2: return clienti.get(rowIndex).getNome();
            case 3: return dateFormatter.format(clienti.get(rowIndex).getData());
            default: return "nd";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Codice Fiscale";
            case 1: return "Cognome";
            case 2: return "Nome";
            case 3: return "Data di nascita";
            default: return "";
        }
    }
}

class GruppiAttivitaListCellRenderer extends DefaultListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        GruppoAttivita g = ((GruppoAttivita) value);

        l.setText(g.getNome() + " - COSTO: " + g.getCostoPromozionale());
        return l;
    }
}