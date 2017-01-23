package grafica.gestore;


import entita.Attivita;
import entita.ParcoDivertimenti;
import grafica.AttivitaTableModel;
import grafica.CenterAlignTable;
import main.DbHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class InfoParchiFrame extends JFrame{

    private DbHelper dbh;
    private AttivitaParcoFrame attivitaParcoFrame;

    public InfoParchiFrame() throws HeadlessException {
        dbh = new DbHelper();
        ArrayList<ParcoDivertimenti> parchi = dbh.getInfoParchiDivertimento();

        JPanel content = new JPanel(new BorderLayout());
        ParchiTableData td = new ParchiTableData(parchi);
        JTable t = new CenterAlignTable(td);
        t.setRowHeight(30);

        attivitaParcoFrame = null;

        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    String parcoSelezionato = t.getModel().getValueAt(row, 0).toString();
                    ArrayList<Attivita> att = dbh.getAttivitaParco(parcoSelezionato);
                    if (attivitaParcoFrame == null) {
                        attivitaParcoFrame = new AttivitaParcoFrame(att, parcoSelezionato);
                    } else {
                        attivitaParcoFrame.dispose();
                        attivitaParcoFrame = new AttivitaParcoFrame(att, parcoSelezionato);
                    }
                }
            }
        });
        content.add(new JScrollPane(t));

        setContentPane(content);

        setSize(1300,500);
        setTitle("Visualizzazione Parchi");
        setVisible(true);
    }


}

class AttivitaParcoFrame extends JFrame {

    public AttivitaParcoFrame(ArrayList<Attivita> attivita, String nomeParco) {
        JPanel content = new JPanel(new BorderLayout());
        AttivitaTableModel tm = new AttivitaTableModel(attivita, false);

        JTable t = new CenterAlignTable(tm);

        t.setRowHeight(25);

        JScrollPane p = new JScrollPane(t);

        content.add(p, BorderLayout.CENTER);

        setContentPane(content);
        setSize(800,300);
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setTitle("Visualizzazione attivita per " + nomeParco);
        setVisible(true);
    }
}

