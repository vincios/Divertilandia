package grafica;

import entita.Offerta;
import entita.ParcoDivertimenti;
import main.DbHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class VisualizzaPromozioniFrame extends JFrame{

    private JLabel aLabel;
    private JLabel daLabel;
    private JLabel attivitaLabel;
    private JLabel ordinaLabel;
    private JPanel content;
    private JRadioButton periodoRB;
    private JRadioButton attivitaRB;
    private JRadioButton ordinaDataRB;
    private JRadioButton ordinaNomeRB;
    private JFormattedTextField daFormattedTextField;
    private JFormattedTextField aFormattedTextField;

    private JTextField nomeAttivitaTextField;
    private OfferteTableData tm;
    private DbHelper dbh;

    public VisualizzaPromozioniFrame(){
        dbh = new DbHelper();
        ArrayList<ParcoDivertimenti> parchi = dbh.getInfoParchiDivertimento();

        content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1,2));

        //creazione Pannello superiore sinistro
        JPanel topLeftPanel = new JPanel(new BorderLayout());
        topPanel.add(topLeftPanel);

        createButtonGroup();
        JPanel topLeftCenterPanel = new JPanel(new FlowLayout());
        daFormattedTextField = new JFormattedTextField();
        aFormattedTextField = new JFormattedTextField();
        daFormattedTextField.setColumns(10);
        aFormattedTextField.setColumns(10);

        daLabel = new JLabel("Da:" );
        aLabel = new JLabel("A:" );

        topLeftCenterPanel.add(daLabel);
        topLeftCenterPanel.add(daFormattedTextField);
        topLeftCenterPanel.add(aLabel);
        topLeftCenterPanel.add(aFormattedTextField);


        try {
            MaskFormatter daDateMask = new MaskFormatter("##/##/####");
            MaskFormatter aDateMask = new MaskFormatter("##/##/####");
            daDateMask.install(daFormattedTextField);
            aDateMask.install(aFormattedTextField);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        topLeftPanel.add(periodoRB, BorderLayout.NORTH);
        topLeftPanel.add(topLeftCenterPanel, BorderLayout.CENTER);


        //Creazione pannello superiore destro
        JPanel topRightPanel = new JPanel(new BorderLayout());
        topPanel.add(topRightPanel);
        topRightPanel.add(attivitaRB, BorderLayout.NORTH);

        attivitaLabel = new JLabel("Attivita:");
        nomeAttivitaTextField = new JTextField();
        nomeAttivitaTextField.setColumns(40);
        nomeAttivitaTextField.setEnabled(false);
        attivitaLabel.setEnabled(false);

        ordinaDataRB = new JRadioButton("Data");
        ordinaNomeRB = new JRadioButton("Nome");
        ButtonGroup bg = new ButtonGroup();
        bg.add(ordinaDataRB);
        bg.add(ordinaNomeRB);
        ordinaLabel = new JLabel("Ordina per:");

        JPanel topRightCenterPanel = new JPanel(new FlowLayout());
        topRightCenterPanel.add(attivitaLabel);
        topRightCenterPanel.add(nomeAttivitaTextField);
        topRightPanel.add(topRightCenterPanel, BorderLayout.CENTER);

        JPanel topRightSouthPanel = new JPanel(new FlowLayout());
        topRightSouthPanel.add(ordinaLabel);
        topRightSouthPanel.add(ordinaDataRB);
        topRightSouthPanel.add(ordinaNomeRB);
        topRightPanel.add(topRightSouthPanel, BorderLayout.SOUTH);

        ordinaLabel.setEnabled(false);
        ordinaNomeRB.setEnabled(false);
        ordinaDataRB.setEnabled(false);
        ordinaDataRB.setSelected(true);

        content.add(topPanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(createTable()), BorderLayout.CENTER);
        centerPanel.add(createEseguiButton(), BorderLayout.NORTH);
        content.add(centerPanel, BorderLayout.CENTER);
        setContentPane(content);

        setSize(1200,500);
        setVisible(true);

    }

    private void createButtonGroup() {
        periodoRB = new JRadioButton("Per periodo");
        periodoRB.setSelected(true);
        attivitaRB = new JRadioButton("Per attivita");

        periodoRB.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.DESELECTED){
                    daFormattedTextField.setText("");
                    aFormattedTextField.setText("");
                    daFormattedTextField.setEnabled(false);
                    aFormattedTextField.setEnabled(false);
                    daLabel.setEnabled(false);
                    aLabel.setEnabled(false);
                    attivitaLabel.setEnabled(true);
                    nomeAttivitaTextField.setEnabled(true);
                    ordinaLabel.setEnabled(true);
                    ordinaNomeRB.setEnabled(true);
                    ordinaDataRB.setEnabled(true);
                    nomeAttivitaTextField.requestFocus();
                }else{
                    daFormattedTextField.setEnabled(true);
                    aFormattedTextField.setEnabled(true);
                    daLabel.setEnabled(true);
                    aLabel.setEnabled(true);
                    attivitaLabel.setEnabled(false);
                    nomeAttivitaTextField.setEnabled(false);
                    ordinaLabel.setEnabled(false);
                    ordinaNomeRB.setEnabled(false);
                    ordinaDataRB.setEnabled(false);

                    daFormattedTextField.requestFocus();
                }
            }
        });
        ButtonGroup group = new ButtonGroup();
        group.add(periodoRB);
        group.add(attivitaRB);
    }


    private JButton createEseguiButton(){
        JButton b = new JButton("Esegui");

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tm.clearTable();
                if(periodoRB.isSelected()){
                    Date da = createDate(daFormattedTextField.getText());
                    Date a = createDate(aFormattedTextField.getText());
                    ArrayList<Offerta> offertePerPeriodo = dbh.getOffertePerPeriodo(da, a);

                    tm.addOfferte(offertePerPeriodo);
                }else if(attivitaRB.isSelected()){
                    String attivita = nomeAttivitaTextField.getText();
                    boolean ordinaPerData = ordinaDataRB.isSelected();
                    ArrayList<Offerta> offertePerAttivita = dbh.getOffertePerAttivita(attivita, ordinaPerData);
                    System.out.println(offertePerAttivita.size());
                    tm.addOfferte(offertePerAttivita);
                }
            }
        });

        return b;
    }

    private JTable createTable(){
        tm = new OfferteTableData();
        JTable t = new JTable(tm);
        t.setRowHeight(20);
        return t;
    }

    private Date createDate(String s){
        String[] tmp = s.split("/");
        int giornoTMP = Integer.parseInt(tmp[0]);
        int meseTMP = Integer.parseInt(tmp[1]);
        int annoTMP = Integer.parseInt(tmp[2]);

        return Date.valueOf(""+annoTMP+"-"+meseTMP+"-"+giornoTMP);
    }
}

class OfferteTableData extends AbstractTableModel {

    ArrayList<Offerta> offerte;

    public OfferteTableData(ArrayList<Offerta> offerte) {
        this.offerte = offerte;
    }
    public OfferteTableData() {
        this.offerte = new ArrayList<>();
    }

    public void addOfferte(ArrayList<Offerta> offerte){
        this.offerte = offerte;
        fireTableDataChanged();
    }
    public void clearTable(){
        offerte.clear();
        fireTableDataChanged();
    }
    @Override
    public int getRowCount() {
        return offerte.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return offerte.get(rowIndex).getCodice();
            case 1: return offerte.get(rowIndex).getNome();
            case 2: return offerte.get(rowIndex).getDescrizione();
            case 3: return offerte.get(rowIndex).getDataInizio() + " - " + offerte.get(rowIndex).getDataFine();
            case 4: return offerte.get(rowIndex).getPercentualeSconto();
            case 5: return offerte.get(rowIndex).getNomeAttivita() + ", " + offerte.get(rowIndex).getNomeParco();
            default: return "nd";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "Codice";
            case 1: return "Nome";
            case 2: return "Descrizione";
            case 3: return "Periodo";
            case 4: return "Percentuale Sconto";
            case 5: return "Attivita";
            default: return "";
        }
    }

}
