package main;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	/*Restituisce un arrayList con le informazioni sui parchi divertimento gestiti (incluse le informazioni sui biglietti venduti);*/
	public ArrayList<ParcoDivertimenti> getInfoParchiDivertimento() {
		ArrayList<ParcoDivertimenti> parchi = new ArrayList<>();
		Connection connection = null;
		try {
			connection = connect();
			String query = "SELECT * FROM parcodivertimenti p LEFT JOIN contattotelefonico c ON p.Nome = c.NomeParco ORDER BY p.Nome";

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

	/*Restituisce un arrayList con le promozioni attive in un determinato periodo di tempo;*/
	public ArrayList<Offerta> getOffertePerPeriodo(Date inizio, Date fine ) {
		ArrayList<Offerta> offerte = new ArrayList<>();
		Connection connection = null;
		try {
			connection = connect();
			String query = "select * from offerta o where o.DataInizio >= ? and o.DataInizio <= ?";

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

	/*Restituisce un arrayList con le promozioni attive per una determinata attività in ordine cronologico o lessicografico*/
	public ArrayList<Offerta> getOffertePerAttivita(String nomeAttivita, boolean orderByData) {
		ArrayList<Offerta> offerte = new ArrayList<>();
		Connection connection = null;
		try {
			connection = connect();
			String query = null;
			if (orderByData) query = "select * from offerta where NomeAttivita = ? order by DataInizio";
			else query = "select * from offerta where NomeAttivita = ? order by Nome";

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

	/*Restituisce le attività con le offerte attive in una determinata data*/
	public ArrayList<Attivita> getAttivitaConOfferteAttiveDiUnParco(String nomeParco, Date data) {
		ArrayList<Attivita> attivita= new ArrayList<>();
		Connection connection = null;
		try {
			connection = connect();

			String query = "select a.Nome as NomeAttivita, a.Costo, a.OrarioApertura, a.OrarioChiusura, a.NomeParco, o.Codice, o.DataInizio, o.DataFine, o.Nome as NomeOfferta, o.Descrizione, o.PercentualeSconto from attivita a join offerta o on a.Nome = o.NomeAttivita AND a.NomeParco = o.NomeParco where a.NomeParco = ? AND o.DataInizio <= ? AND o.DataFine >= ? order by a.Nome";

			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nomeParco);
			statement.setDate(2, data);
			statement.setDate(3, data);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				String nomeAttivita = result.getString("NomeAttivita");

				if (!(attivita.isEmpty()) && nomeAttivita.equals(attivita.get(attivita.size() - 1).getNome())) {

					String codiceOfferta = result.getString("Codice");
					String nomeOfferta = result.getString("nomeOfferta");
					String descrizioneOfferta = result.getString("Descrizione");
					Date dataInizio = result.getDate("DataInizio");
					Date dataFine = result.getDate("DataFine");
					int percentualeSconto = result.getInt("PercentualeSconto");

					Offerta off = new Offerta(codiceOfferta, nomeOfferta, descrizioneOfferta, dataInizio, dataFine, percentualeSconto, nomeParco, nomeAttivita);

					attivita.get(attivita.size() - 1).addOfferta(off);

				} else {

					String oraApertura = result.getString("OrarioApertura");
					String oraChiusura = result.getString("OrarioChiusura");
					float costoattivita = result.getFloat("Costo");

					String codiceOfferta = result.getString("Codice");
					String nomeOfferta = result.getString("nomeOfferta");
					String descrizioneOfferta = result.getString("Descrizione");
					Date dataInizio = result.getDate("DataInizio");
					Date dataFine = result.getDate("DataFine");
					int percentualeSconto = result.getInt("PercentualeSconto");

					Attivita att = new Attivita(nomeAttivita, oraApertura, oraChiusura, costoattivita, nomeParco);
					Offerta off = new Offerta(codiceOfferta, nomeOfferta, descrizioneOfferta, dataInizio, dataFine, percentualeSconto, nomeParco, nomeAttivita);
					att.addOfferta(off);
					attivita.add(att);
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
		return attivita;
	}

	/*Restituisce le agenzie che hanno pacchetti in vendita*/
	public ArrayList<Agenzia> getAgenzieConPacchettiInVendita() {
		ArrayList<Agenzia> agenzie = new ArrayList<>();
		Connection connection = null;
		try {
			connection = connect();
			String query = "select a.PartitaIVA, a.Nome as NomeAgenzia, a.citta, a.via, a.NCivico, a.Telefono, p.Codice, p.nome as NomePacchetto, p.Descrizione , p.Prezzo from pacchetto p , agenzia a where a.PartitaIVA = p.PivaAgenzia and Codice not in (select pa.Codice from pacchetto pa, acquistare ac where ac.CodicePacchetto = pa.Codice) order by a.PartitaIVA;";

			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);

			String partitaIva;
			while (result.next()) {
				partitaIva = result.getString("PartitaIVA");

				if(!(agenzie.isEmpty()) && partitaIva.equals(agenzie.get(agenzie.size()-1).getPartitaIva())) {

					String codice = result.getString("Codice");
					String nomePacchetto = result.getString("NomePacchetto");
					String descrizione = result.getString("Descrizione");
					float prezzo = result.getFloat("Prezzo");

					agenzie.get(agenzie.size()-1).addPacchetto(new Pacchetto(codice, nomePacchetto, descrizione, prezzo, partitaIva));
				} else {

					String nomeAgenzia = result.getString("NomeAgenzia");
					String citta = result.getString("Citta");
					String via = result.getString("Via");
					String nCivico = result.getString("NCivico");
					String telefono = result.getString("Telefono");

					String codice = result.getString("Codice");
					String nomePacchetto = result.getString("NomePacchetto");
					String descrizione = result.getString("Descrizione");
					float prezzo = result.getFloat("Prezzo");

					Agenzia agenzia = new Agenzia(partitaIva, nomeAgenzia, telefono, citta, via, nCivico);

					agenzia.addPacchetto(new Pacchetto(codice, nomePacchetto, descrizione, prezzo, partitaIva));
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

	/*Inserisce una nuova attivita per un parco*/
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
		if (result == 1)
			return true;
		else return false;
	}

	/*Inserisce una nuova agenzia*/
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
		if (result == 1)
			return true;
		else return false;
	}

	/*Restituisce L'incasso giornaliero di un parco*/
	public float getIncassoGiornaliero(String nomeParco) {
		Connection connection = null;
		try {
			connection = connect();
			String query = "select sum(b.Prezzo) as incasso from biglietto b where b.NomeParco = ? and b.DataAcquisto = ?";

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

	/*Restituisce L'incasso settimanale di un parco*/
	public float getIncassoSettimanale(String nomeParco) {
		Connection connection = null;
		try {
			connection = connect();
			String query = "select sum(b.Prezzo) as incasso from biglietto b where b.NomeParco = ? and b.DataAcquisto between ? and ?";

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

	/*Restituisce L'incasso mensile di un parco*/
	public float getIncassoMensile(String nomeParco) {
		Connection connection = null;
		try {
			connection = connect();
			String query = "select sum(b.Prezzo) as incasso from biglietto b where b.NomeParco = ? and b.DataAcquisto between ? and ?";

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

	/*Inserisce un nuovo pacchetto*/
	public boolean insertPacchetto(Pacchetto p, ArrayList<String> hotelPIVA, ArrayList<String> ristorantePIVA) {
		Connection connection = null;
		int resultPacchetto;
		int[] resultHotel;
		int[] resultRistorante;
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
			resultPacchetto = statementPacchetto.executeUpdate();

			String queryHotel = "insert into includereh values(?, ?);";
			PreparedStatement statementHotel = connection.prepareStatement(queryHotel);

			for(String hh: hotelPIVA) {
				statementHotel.setString(1, p.getCodice());
				statementHotel.setString(2, hh);
				statementHotel.addBatch();
			}

			resultHotel = statementHotel.executeBatch();

			String queryRistorante = "insert into includerer values(?, ?);";
			PreparedStatement statementRistorante = connection.prepareStatement(queryRistorante);

			for(String rr: ristorantePIVA) {
				statementRistorante.setString(1, p.getCodice());
				statementRistorante.setString(2, rr);
				statementRistorante.addBatch();
			}

			resultRistorante = statementRistorante.executeBatch();

			connection.commit();
			connection.setAutoCommit(true);
			statementPacchetto.close();
			statementHotel.close();
			statementRistorante.close();
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

		if (resultPacchetto == 1)
			return true;
		else return false;
	}

	/*Inserisce un nuovo biglietto*/
	public boolean insertBiglietto(Biglietto biglietto, ArrayList<String> attivita) {
		Connection connection = null;
		int result;
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

			result = statementBiglietto.executeUpdate();

			String queryAttivita = "insert into comprendere values (? ,? ,?);";
			PreparedStatement statementAttivita = connection.prepareStatement(queryAttivita);

			for (String a : attivita) {
				statementAttivita.setString(1, biglietto.getCodice());
				statementAttivita.setString(2, a);
				statementAttivita.setString(3, biglietto.getNomeParco());
				statementAttivita.addBatch();
			}

			statementAttivita.executeBatch();

			String queryIncrementaBiglietto = "update parcodivertimenti set NBiglietti = NBiglietti + 1 where nome = ?;";
			PreparedStatement statementIncrementaBiglietto = connection.prepareStatement(queryIncrementaBiglietto);

			statementIncrementaBiglietto.setString(1, biglietto.getNomeParco());

			statementIncrementaBiglietto.executeUpdate();


			connection.commit();
			connection.setAutoCommit(true);
			statementAttivita.close();
			statementAttivita.close();
			statementBiglietto.close();
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
		if (result == 1)
			return true;
		else return false;

	}

	/*Vende un pacchetto*/
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

    /*Restituisce tutti i pacchetti venduti da un'agenzia*/
    public ArrayList<Pacchetto> getPacchettiVendutiDaAgenzia(String PIVAAgenzia) {

        ArrayList<Pacchetto> pacchetti = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connect();
            String query = "SELECT p.Codice, p.Nome, p.Prezzo, p.Descrizione FROM pacchetto p, agenzia a, acquistare aq WHERE p.PivaAgenzia = a.PartitaIVA AND  a.PartitaIVA = ? AND p.Codice = aq.CodicePacchetto";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, PIVAAgenzia);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String codice = result.getString("Codice");
                String nome = result.getString("Nome");
                String descrizione = result.getString("Descrizione");
                float prezzo = result.getInt("Prezzo");

                Pacchetto pacchetto = new Pacchetto(codice, nome, descrizione, prezzo, PIVAAgenzia);
                pacchetti.add(pacchetto);
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

}
