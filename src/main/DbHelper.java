package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import entita.Offerta;
import entita.ParcoDivertimenti;

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

	public ArrayList<ParcoDivertimenti> getInfoParchiDivertimento() {
		ArrayList<ParcoDivertimenti> parchi = new ArrayList<>();
		Connection connection = null;
		try {
			connection = connect();
			String query = "select * from parcodivertimenti p, contattotelefonico c where p.Nome = c.NomeParco order by p.Nome";

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

	public ArrayList<Offerta> getOffertePerAttivita(String nomeAttività, boolean orderByData) {
		ArrayList<Offerta> offerte = new ArrayList<>();
		Connection connection = null;
		try {
			connection = connect();
			String query = null;
			 if (orderByData) query = "select * from offerta where NomeAttivita = ? order by DataInizio";
			 else query = "select * from offerta where NomeAttivita = ? order by Nome";
			 
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, nomeAttività);
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
}
