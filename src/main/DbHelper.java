package main;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import entita.*;

public class DbHelper {

    private String url;
    private String user;
    private String password;
    private Logger l;

    public DbHelper() {
        super();

        url = "jdbc:mysql://localhost:3306/divertilandia?useSSL=false";
        user = "root";
        password = "root";
        l = Logger.getGlobal();
    }

    private Connection connect() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            l.log(Level.SEVERE, "Classe Mysql non trovata", e);
        }

        return DriverManager.getConnection(url, user, password);
    }

    /* 1 Restituisce un arrayList con le informazioni sui parchi divertimento gestiti (incluse le informazioni sui biglietti venduti);*/
    public ArrayList<ParcoDivertimenti> getInfoParchiDivertimento() {
        ArrayList<ParcoDivertimenti> parchi = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query =  "select * " +
                    "from parcodivertimenti p left join contattotelefonico c on p.Nome = c.NomeParco " +
                    "order by p.Nome";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            String nome;
            while (result.next()) {
                nome = result.getString("Nome");

                if(!(parchi.isEmpty()) && nome.equals(parchi.get(parchi.size()-1).getNome())) {
                    String telefono = result.getString("Telefono");

                    parchi.get(parchi.size()-1).addNumeroTelefonico(telefono);

                } else {

                    String sede = result.getString("Sede");
                    String tipo = result.getString("Tipo");
                    int nBiglietti = result.getInt("NBiglietti");
                    String percorso = result.getString("Percorso");
                    String tema = result.getString("Tema");
                    int nPiscine = result.getInt("NPiscine");
                    String telefono = result.getString("Telefono");

                    ParcoDivertimenti parco = new ParcoDivertimenti (nome, sede, tipo, nBiglietti, percorso, tema, nPiscine);

                    if(telefono != null)
                        parco.addNumeroTelefonico(telefono);

                    parchi.add(parco);
                }

            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return parchi;
    }

    /* 2 Restituisce un arrayList con le promozioni attive in un determinato periodo di tempo;*/
    public ArrayList<Offerta> getOffertePerPeriodo(Date inizio, Date fine ) {
        ArrayList<Offerta> offerte = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query = "select * " +
                    "from offerta " +
                    "where DataInizio between ? and ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, inizio);
            statement.setDate(2, fine);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String codice = result.getString("Codice");
                String nome = result.getString("Nome");
                String descrizione = result.getString("Descrizione");
                Date dataInizio = result.getDate("DataInizio");
                Date dataFine = result.getDate("DataFine");
                int percentualeSconto = result.getInt("PercentualeSconto");
                String nomeParco = result.getString("NomeParco");
                String nomeAttivita = result.getString("NomeAttivita");

                Offerta offerta = new Offerta(codice, nome, descrizione, dataInizio, dataFine, percentualeSconto, nomeParco, nomeAttivita);

                offerte.add(offerta);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return offerte;
    }

    /* 3 Restituisce un arrayList con le promozioni attive per una determinata attività in ordine cronologico o lessicografico rispetto al nome dell'offerta*/
    public ArrayList<Offerta> getOffertePerAttivita(String nomeAttivita, boolean orderByData) {
        ArrayList<Offerta> offerte = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query = null;
            if (orderByData)
                query = "select * from offerta where NomeAttivita = ? order by DataInizio";
            else
                query = "select * from offerta where NomeAttivita = ? order by Nome";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomeAttivita);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String codice = result.getString("Codice");
                String nome = result.getString("Nome");
                String descrizione = result.getString("Descrizione");
                Date dataInizio = result.getDate("DataInizio");
                Date dataFine = result.getDate("DataFine");
                int percentualeSconto = result.getInt("PercentualeSconto");
                String nomeParco = result.getString("NomeParco");
                String nomeAtt = result.getString("NomeAttivita");

                Offerta offerta = new Offerta(codice, nome, descrizione, dataInizio, dataFine, percentualeSconto, nomeParco, nomeAtt);

                offerte.add(offerta);
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return offerte;
    }

    /* 4 Restituisce le agenzie che hanno pacchetti in vendita*/
    public ArrayList<Agenzia> getAgenzieConPacchettiInVendita() {
        ArrayList<Agenzia> agenzie = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query = "select a.PartitaIVA as PartitaIVAAgenzia, a.Nome as NomeAgenzia, a.citta as cittaAgenzia, a.via as viaAgenzia, a.NCivico as NCivicoAgenzia, a.Telefono, p.Codice, p.nome as NomePacchetto, p.Descrizione , p.Prezzo, s.PartitaIVA as PartitaIVAServizio, s.Nome as NomeServizio, s.Citta as CittaServizio, s.via as viaServizio, s.NCivico as NCivicoServizio, s.tipo " +
                    "from pacchetto p , agenzia a , includere i, servizio s " +
                    "where a.PartitaIVA = p.PivaAgenzia and i.CodicePacchetto = p.Codice and s.PartitaIVA = i.PivaServizio and Codice not in (" +
                    "select pa.Codice " +
                    "from pacchetto pa, acquistare ac " +
                    "where ac.CodicePacchetto = pa.Codice) " +
                    "order by a.PartitaIVA, p.Codice, s.nome;";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String partitaIvaAgenzia = result.getString("PartitaIVAAgenzia");
                String nomeAgenzia = result.getString("NomeAgenzia");
                String cittaAgenzia = result.getString("CittaAgenzia");
                String viaAgenzia = result.getString("ViaAgenzia");
                String nCivicoAgenzia = result.getString("NCivicoAgenzia");
                String telefonoAgenzia = result.getString("Telefono");

                String codicePacchetto = result.getString("Codice");
                String nomePacchetto = result.getString("NomePacchetto");
                String descrizionePacchetto = result.getString("Descrizione");
                float prezzoPacchetto = result.getFloat("Prezzo");

                String partitaIVAServizio = result.getString("partitaIVAServizio");
                String nomeServizio = result.getString("nomeServizio");
                String cittaServizio = result.getString("cittaServizio");
                String viaServizio = result.getString("viaServizio");
                String nCivicoServizio = result.getString("nCivicoServizio");
                String tipoServizio = result.getString("Tipo");


                String partitaIvaPrecedente = null;
                String codicePacchettoPrecedente = null;

                if(!agenzie.isEmpty()) {
                    partitaIvaPrecedente = agenzie.get(agenzie.size()-1).getPartitaIva();
                    codicePacchettoPrecedente = agenzie.get(agenzie.size()-1).getPacchetti().get(agenzie.get(agenzie.size()-1).getPacchetti().size() -1).getCodice();
                }

                if(!(agenzie.isEmpty()) && partitaIvaAgenzia.equals(partitaIvaPrecedente) && codicePacchetto.equals(codicePacchettoPrecedente)) {

                    Servizio s = new Servizio(partitaIVAServizio, nomeServizio, cittaServizio, viaServizio, nCivicoServizio, tipoServizio);
                    agenzie.get(agenzie.size()-1).getPacchetti().get(agenzie.get(agenzie.size()-1).getPacchetti().size() -1).addServizio(s);
                }
                else if (!(agenzie.isEmpty()) && partitaIvaAgenzia.equals(partitaIvaPrecedente)) {

                    agenzie.get(agenzie.size()-1).addPacchetto(new Pacchetto(codicePacchetto, nomePacchetto, descrizionePacchetto, prezzoPacchetto, partitaIvaAgenzia));
                    Servizio s = new Servizio(partitaIVAServizio, nomeServizio, cittaServizio, viaServizio, nCivicoServizio, tipoServizio);
                    agenzie.get(agenzie.size()-1).getPacchetti().get(agenzie.get(agenzie.size()-1).getPacchetti().size() -1).addServizio(s);

                } else {

                    Agenzia agenzia = new Agenzia(partitaIvaAgenzia, nomeAgenzia, telefonoAgenzia, cittaAgenzia, viaAgenzia, nCivicoAgenzia);
                    Pacchetto pacchetto = new Pacchetto(codicePacchetto, nomePacchetto, descrizionePacchetto, prezzoPacchetto, partitaIvaAgenzia);
                    pacchetto.addServizio(new Servizio(partitaIVAServizio, nomeServizio, cittaServizio, viaServizio, nCivicoServizio, tipoServizio));
                    agenzia.addPacchetto(pacchetto);
                    agenzie.add(agenzia);
                }
            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return agenzie;
    }

    /* 5 Inserisce una nuova attivita per un parco*/
    public boolean insertAttivita(Attivita a) {
        Connection connection = null;
        int result;
        try {
            connection = connect();
            String query = "insert into attivita values (?, ?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, a.getNome());
            statement.setString(2, a.getNomeParco());
            statement.setString(3, a.getOrarioApertura());
            statement.setString(4, a.getOrarioChiusura());
            statement.setFloat(5, a.getCosto());
            result = statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
            return false;
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return (result == 1);
    }

    /* 6 Inserisce una nuova agenzia*/
    public boolean insertAgenzia(Agenzia a) {
        Connection connection = null;
        int result;
        try {
            connection = connect();
            String query = "insert into agenzia values (?, ?, ?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, a.getPartitaIva());
            statement.setString(2, a.getNome());
            statement.setString(3, a.getCitta());
            statement.setString(4, a.getVia());
            statement.setString(5, a.getnCivico());
            statement.setString(6, a.getTelefono());
            result = statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
            return false;
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return (result == 1);
    }

    /* 7 Inserisce un nuovo biglietto*/
    public boolean insertBiglietto(Biglietto biglietto, ArrayList<String> attivita) {
        Connection connection = null;
        int resultBiglietto = 0;
        int[] resultAttivita = new int[0];
        int resultParco = 0;
        try {
            connection = connect();
            connection.setAutoCommit(false);
            String queryBiglietto = "insert into biglietto values (?, ?, ?, ?, ?);";

            PreparedStatement statementBiglietto = connection.prepareStatement(queryBiglietto);
            statementBiglietto.setString(1, biglietto.getCodice());
            statementBiglietto.setFloat(2, biglietto.getPrezzo());
            statementBiglietto.setDate(3, biglietto.getDataAcquisto());
            statementBiglietto.setString(4, biglietto.getNomeParco());
            statementBiglietto.setString(5, biglietto.getCFCliente());

            resultBiglietto = statementBiglietto.executeUpdate();

            String queryAttivita = "insert into comprendere values (? ,? ,?);";
            PreparedStatement statementAttivita = connection.prepareStatement(queryAttivita);

            for (String a : attivita) {
                statementAttivita.setString(1, biglietto.getCodice());
                statementAttivita.setString(2, a);
                statementAttivita.setString(3, biglietto.getNomeParco());
                statementAttivita.addBatch();
            }

            resultAttivita = statementAttivita.executeBatch();

            String queryIncrementaBiglietto = "update parcodivertimenti set NBiglietti = NBiglietti + 1 where nome = ?;";
            PreparedStatement statementIncrementaBiglietto = connection.prepareStatement(queryIncrementaBiglietto);

            statementIncrementaBiglietto.setString(1, biglietto.getNomeParco());

            resultParco = statementIncrementaBiglietto.executeUpdate();


            connection.commit();
            connection.setAutoCommit(true);
            statementAttivita.close();
            statementAttivita.close();
            statementBiglietto.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }

        int somma = 0;
        for (int i = 0; i < resultAttivita.length; i++) {
            somma = somma + resultAttivita[i];
        }
        return resultBiglietto == 1 && resultParco == 1 && somma == resultAttivita.length;
    }

    /* 8G Restituisce L'incasso giornaliero di un parco*/
    public float getIncassoGiornaliero(String nomeParco) {
        Connection connection = null;
        try {
            connection = connect();
            String query = "select sum(b.Prezzo) as incasso " +
                    "from biglietto b " +
                    "where b.NomeParco = ? and b.DataAcquisto = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomeParco);
            statement.setDate(2, new Date(System.currentTimeMillis()));
            //statement.setDate(2, Date.valueOf("2017-01-10"));
            ResultSet result = statement.executeQuery();

            result.next();
            Float incasso = result.getFloat("incasso");

            result.close();
            statement.close();

            return incasso;
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return -1;
    }

    /* 8S Restituisce L'incasso settimanale di un parco*/
    public float getIncassoSettimanale(String nomeParco) {
        Connection connection = null;
        try {
            connection = connect();
            String query = "select sum(b.Prezzo) as incasso " +
                    "from biglietto b " +
                    "where b.NomeParco = ? and b.DataAcquisto between ? and ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomeParco);


            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

            Date d = new Date(calendar.getTimeInMillis());

            statement.setDate(2, d);
            statement.setDate(3, new Date(System.currentTimeMillis()));
            ResultSet result = statement.executeQuery();

            result.next();
            Float incasso = result.getFloat("incasso");

            result.close();
            statement.close();

            return incasso;
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return -1;
    }

    /* 8M Restituisce L'incasso mensile di un parco*/
    public float getIncassoMensile(String nomeParco) {
        Connection connection = null;
        try {
            connection = connect();
            String query = "select sum(b.Prezzo) as incasso " +
                    "from biglietto b " +
                    "where b.NomeParco = ? and b.DataAcquisto between ? and ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomeParco);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            statement.setDate(2, new Date(calendar.getTimeInMillis()));

            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            statement.setDate(3, new Date(calendar.getTimeInMillis()));
            ResultSet result = statement.executeQuery();

            result.next();
            Float incasso = result.getFloat("incasso");

            result.close();
            statement.close();

            return incasso;
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return -1;
    }

    /* 9 Inserisce un nuovo pacchetto*/
    public boolean insertPacchetto(Pacchetto p, ArrayList<String> pIVAServizio) throws SQLException {
        Connection connection = null;

        try {
            connection = connect();
            connection.setAutoCommit(false);
            String queryPacchetti = "insert into pacchetto values (?, ?, ?, ?, ?);";

            PreparedStatement statementPacchetto = connection.prepareStatement(queryPacchetti);
            statementPacchetto.setString(1, p.getCodice());
            statementPacchetto.setString(2, p.getNome());
            statementPacchetto.setString(3, p.getDescrizione());
            statementPacchetto.setFloat(4, p.getPrezzo());
            statementPacchetto.setString(5, p.getpIvaAgenzia());
            statementPacchetto.executeUpdate();

            String queryHotel = "insert into includere values(?, ?);";
            PreparedStatement statementServizio = connection.prepareStatement(queryHotel);

            for(String hh: pIVAServizio) {
                statementServizio.setString(1, p.getCodice());
                statementServizio.setString(2, hh);
                statementServizio.addBatch();
            }

            statementServizio.executeBatch();

            connection.commit();
            connection.setAutoCommit(true);
            statementPacchetto.close();
            statementServizio.close();
        } catch (SQLException e) {
            if(e instanceof MySQLIntegrityConstraintViolationException )
                throw e;

            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
            return false;
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }

        return true;
        //TODO: sistemare il check del risultato
    }

    /* 10 Vende un pacchetto*/
    public boolean vendiPacchetto (String codicePacchetto, String CFCliente) {
        Connection connection = null;
        int result;
        try {
            connection = connect();
            String query = "insert into acquistare values (?, ?);";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, codicePacchetto);
            statement.setString(2, CFCliente);
            result = statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
            return false;
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return result == 1;
    }

    /* 11 Restituisce tutti i pacchetti venduti da un'agenzia*/
    public ArrayList<Pacchetto> getPacchettiVendutiDaAgenzia(String PIVAAgenzia) {

        ArrayList<Pacchetto> pacchetti = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query = "select p.Codice as CodicePacchetto, p.Nome as NomePacchetto, p.Prezzo, p.Descrizione, s.PartitaIVA, s.Nome as NomeServizio, s.citta, s.via, s.NCivico, s.tipo, aq.CFCliente " +
                    "from pacchetto p, agenzia a, acquistare aq , includere i, servizio s " +
                    "where p.PivaAgenzia = a.PartitaIVA and i.CodicePacchetto = p.Codice and s.PartitaIVA = i.PivaServizio and a.PartitaIVA = ? and p.Codice = aq.CodicePacchetto " +
                    "order by CodicePacchetto, NomeServizio";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, PIVAAgenzia);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String codicePacchetto = result.getString("CodicePacchetto");
                String nomePacchetto = result.getString("NomePacchetto");
                String descrizionePacchetto = result.getString("Descrizione");
                float prezzoPacchetto = result.getInt("Prezzo");

                String pIVAServizio = result.getString("PartitaIva");
                String nomeServizio = result.getString("NomeServizio");
                String cittaServizio = result.getString("Citta");
                String viaServizio = result.getString("Via");
                String numCivicoServizio = result.getString("NCivico");
                String tipoSerizio = result.getString("Tipo");

                String codicePacchettoPrecedente = null;

                if (!pacchetti.isEmpty()) {
                    codicePacchettoPrecedente = pacchetti.get(pacchetti.size() -1).getCodice();
                }

                if (!(pacchetti.isEmpty()) && codicePacchetto.equals(codicePacchettoPrecedente)) {
                    Pacchetto pacchetto = pacchetti.get(pacchetti.size()-1);
                    pacchetto.addServizio(new Servizio (pIVAServizio, nomeServizio, cittaServizio, viaServizio,numCivicoServizio, tipoSerizio));

                } else {
                    Pacchetto pacchetto = new Pacchetto(codicePacchetto, nomePacchetto, descrizionePacchetto, prezzoPacchetto, PIVAAgenzia);
                    pacchetto.addServizio(new Servizio(pIVAServizio, nomeServizio, cittaServizio, viaServizio, numCivicoServizio, tipoSerizio));
                    pacchetti.add(pacchetto);
                }

            }
            result.close();
            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return pacchetti;

    }

    /*Restituisce le attività con le offerte attive in una determinata data*/
    public ArrayList<Attivita> getAttivitaConOfferteAttiveDiUnParco(String nomeParco, Date data) {

        ArrayList<Attivita> attivita= new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();

            String queryAttvitaConOfferte = "select a.Nome as NomeAttivita, a.Costo, a.OrarioApertura, a.OrarioChiusura, a.NomeParco, o.Codice, o.DataInizio, o.DataFine, o.Nome as NomeOfferta, o.Descrizione, o.PercentualeSconto " +
                    "from attivita a left join offerta o on a.Nome = o.NomeAttivita and a.NomeParco = o.NomeParco " +
                    "where a.NomeParco = ? and o.DataInizio <= ? and o.DataFine >= ? " +
                    "order by a.Nome";

            PreparedStatement statementAttivitaConOfferte = connection.prepareStatement(queryAttvitaConOfferte);
            statementAttivitaConOfferte.setString(1, nomeParco);
            statementAttivitaConOfferte.setDate(2, data);
            statementAttivitaConOfferte.setDate(3, data);
            ResultSet resultAttivitaConOfferta = statementAttivitaConOfferte.executeQuery();

            while (resultAttivitaConOfferta.next()) {

                String nomeAttivita = resultAttivitaConOfferta.getString("NomeAttivita");

                if (!attivita.isEmpty() && nomeAttivita.equals(attivita.get(attivita.size() - 1).getNome())) {

                    String codiceOfferta = resultAttivitaConOfferta.getString("Codice");
                    String nomeOfferta = resultAttivitaConOfferta.getString("nomeOfferta");
                    String descrizioneOfferta = resultAttivitaConOfferta.getString("Descrizione");
                    Date dataInizio = resultAttivitaConOfferta.getDate("DataInizio");
                    Date dataFine = resultAttivitaConOfferta.getDate("DataFine");
                    int percentualeSconto = resultAttivitaConOfferta.getInt("PercentualeSconto");

                    Offerta off = new Offerta(codiceOfferta, nomeOfferta, descrizioneOfferta, dataInizio, dataFine, percentualeSconto, nomeParco, nomeAttivita);

                    attivita.get(attivita.size() - 1).addOfferta(off);

                } else {

                    String oraApertura = resultAttivitaConOfferta.getString("OrarioApertura");
                    String oraChiusura = resultAttivitaConOfferta.getString("OrarioChiusura");
                    float costoattivita = resultAttivitaConOfferta.getFloat("Costo");

                    String codiceOfferta = resultAttivitaConOfferta.getString("Codice");
                    String nomeOfferta = resultAttivitaConOfferta.getString("nomeOfferta");
                    String descrizioneOfferta = resultAttivitaConOfferta.getString("Descrizione");
                    Date dataInizio = resultAttivitaConOfferta.getDate("DataInizio");
                    Date dataFine = resultAttivitaConOfferta.getDate("DataFine");
                    int percentualeSconto = resultAttivitaConOfferta.getInt("PercentualeSconto");

                    Attivita att = new Attivita(nomeAttivita, oraApertura, oraChiusura, costoattivita, nomeParco);
                    Offerta off = new Offerta(codiceOfferta, nomeOfferta, descrizioneOfferta, dataInizio, dataFine, percentualeSconto, nomeParco, nomeAttivita);
                    att.addOfferta(off);
                    attivita.add(att);
                }
            }

            resultAttivitaConOfferta.close();
            statementAttivitaConOfferte.close();

            String queryAttivitaSenzaOfferte =
                    "SELECT * " +
                            "FROM attivita " +
                            "WHERE NomeParco = ? AND nome NOT IN ( SELECT a.Nome " +
                            "FROM attivita a LEFT JOIN offerta o ON a.Nome = o.NomeAttivita AND a.NomeParco = o.NomeParco " +
                            "WHERE a.NomeParco = ? AND o.DataInizio <= ? AND o.DataFine >= ?)";

            PreparedStatement statementAttivitaSenzaOfferta = connection.prepareStatement(queryAttivitaSenzaOfferte);
            statementAttivitaSenzaOfferta.setString(1, nomeParco);
            statementAttivitaSenzaOfferta.setString(2, nomeParco);
            statementAttivitaSenzaOfferta.setDate(3, data);
            statementAttivitaSenzaOfferta.setDate(4, data);
            ResultSet resultAttivitaSenzaOfferta = statementAttivitaSenzaOfferta.executeQuery();

            while (resultAttivitaSenzaOfferta.next()) {
                String nomeAttivita = resultAttivitaSenzaOfferta.getString("Nome");
                String oraApertura = resultAttivitaSenzaOfferta.getString("OrarioApertura");
                String oraChiusura = resultAttivitaSenzaOfferta.getString("OrarioChiusura");
                float costoattivita = resultAttivitaSenzaOfferta.getFloat("Costo");

                attivita.add(new Attivita(nomeAttivita, oraApertura, oraChiusura, costoattivita, nomeParco));
            }

            resultAttivitaSenzaOfferta.close();
            statementAttivitaSenzaOfferta.close();

        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return attivita;
    }

    /*Inserisce un nuovo clente*/
    public boolean insertCliente(Cliente c) {
        Connection connection = null;
        int result;
        try {
            connection = connect();
            String query = "insert into Cliente values (?, ?, ?, ?);";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, c.getCodiceFiscale());
            statement.setString(2, c.getNome());
            statement.setString(3, c.getCognome());
            statement.setDate(4, c.getData());
            result = statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
            return false;
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return result == 1;
    }

    /*Restituisce un'ArrayList di clienti*/
    public ArrayList<Cliente> getClienti() {
        ArrayList<Cliente> clienti = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query =  "select * " +
                    "from cliente " +
                    "order by Cognome";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String nome = result.getString("Nome");
                String cognome = result.getString("Cognome");
                String cF = result.getString("CF");
                Date dDN = result.getDate("DataDiNascita");

                Cliente cliente = new Cliente(cF, nome, cognome, dDN);
                clienti.add(cliente);
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return clienti;
    }

    /*Restituisce un'ArrayList di Attivita*/
    public ArrayList<Attivita> getAttivitaParco(String nomeParco) {
        ArrayList<Attivita> attivita = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query =  "select * from attivita where NomeParco = ? order by Nome;";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomeParco);
            ResultSet result = statement.executeQuery();

            String nome;
            while (result.next()) {
                nome = result.getString("Nome");

                String orarioApertura = result.getString("OrarioApertura");
                String orarioChiusura = result.getString("OrarioChiusura");
                float costo = result.getFloat("Costo");

                attivita.add(new Attivita(nome, orarioApertura, orarioChiusura,costo, nomeParco));
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return attivita;
    }

    /*Ritorna l'elenco dei gruppi attivita di un parco compreso, per ciscuno di esso, l'elenco delle attivita che comprende */
    public ArrayList<GruppoAttivita> getGruppiAttivita(String nomeParco){
        Connection connection = null;
        Map<String, GruppoAttivita> gruppi = new HashMap<>();
        try {
            connection = connect();
            String query = "select g.NomeParco as NomeGruppo, g.NomeParco as NomeParco, g.CostoPromozionale, a.Nome as NomeAttivita, a.OrarioApertura, a.OrarioChiusura " +
                    "from gruppoattivita g, contenere c, attivita a " +
                    "where g.NomeParco = ? and g.Nome = c.NomeGruppo and g.NomeParco = c.NomeParco and c.NomeParco = a.NomeParco and c.NomeAttivita = a.Nome ";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nomeParco);

            ResultSet result = statement.executeQuery();

            while (result.next()){
                String nomeGruppo = result.getString("NomeGruppo");
                String nomePa = result.getString("NomeParco");
                float costoPromozionale = result.getFloat("CostoPromozionale");
                String nomeAttivita = result.getString("NomeAttivita");
                String orarioApertura = result.getString("OrarioApertura");
                String orarioChiusura = result.getString("OrarioChiusura");

                Attivita a = new Attivita(nomeAttivita, orarioApertura, orarioChiusura, 0.0f, nomeParco);

                if(gruppi.containsKey(nomeGruppo)){
                    gruppi.get(nomeGruppo).addAttivita(a);
                }else{
                    GruppoAttivita g = new GruppoAttivita(nomeGruppo, nomeParco, costoPromozionale);
                    g.addAttivita(a);
                    gruppi.put(g.getNome(), g);
                }
            }
            result.close();
            statement.close();

        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }

        return new ArrayList<>(gruppi.values());
    }

    public ArrayList<Agenzia> getAgenzie(){
        ArrayList<Agenzia> agenzie = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query =  "select * " +
                    "from Agenzia " +
                    "order by Nome";

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                String partitaIVA = result.getString("PartitaIVA");
                String nome = result.getString("Nome");
                String citta = result.getString("Citta");
                String via = result.getString("Via");
                String nCivico = result.getString("NCivico");
                String telefono = result.getString("Telefono");

                Agenzia a = new Agenzia(partitaIVA, nome, telefono, citta, via, nCivico);
                agenzie.add(a);
            }

            result.close();
            statement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return agenzie;
    }

    public ArrayList<Servizio> getServiziByTipo(String tipo){
        ArrayList<Servizio> servizi = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query =  "select * " +
                    "from servizio " +
                    "where tipo = ? " +
                    "order by Nome";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tipo);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {

                String partitaIVA = result.getString("PartitaIVA");
                String nome = result.getString("Nome");
                String citta = result.getString("Citta");
                String via = result.getString("Via");
                String nCivico = result.getString("NCivico");
                String tipo1 = result.getString("tipo");

                Servizio s = new Servizio(partitaIVA, nome, citta, via, nCivico, tipo);
                servizi.add(s);
            }

            result.close();
            preparedStatement.close();
        } catch (SQLException e) {
            l.log(Level.SEVERE, "Errore di connessione al DataBase\n" + e.getMessage(), e);
        } finally {
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    l.log(Level.SEVERE, "Errore nella chiusura di connessione", e);
                }
        }
        return servizi;
    }
}
