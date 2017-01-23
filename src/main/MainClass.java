package main;

import java.sql.Date;
import java.util.ArrayList;

import entita.*;

public class MainClass {

	private static DbHelper dh;

	public static void main(String[] args) {

		dh = new DbHelper();

		ArrayList<ParcoDivertimenti> parchi = dh.getInfoParchiDivertimento();

		for(ParcoDivertimenti p : parchi) {
			System.out.println(p.toString());
		}

		System.out.println("");
		ArrayList<Offerta> offerte = dh.getOffertePerPeriodo(Date.valueOf("2017-01-01"), Date.valueOf("2017-02-28"));

		for(Offerta o : offerte) {
			System.out.println(o.toString());
		}

		System.out.println("");
		ArrayList<Offerta> offerte2 = dh.getOffertePerAttivita("L'enigma di Joker", true);

		for(Offerta o : offerte2) {
			System.out.println(o.toString());
		}

		System.out.println("");
		ArrayList<Offerta> offerte3 = dh.getOffertePerAttivita("L'enigma di Joker", false);

		for(Offerta o : offerte3) {
			System.out.println(o.toString());
		}


		System.out.println("\n\nGetAgenzie con pacchetti in vendita\n\n");
		ArrayList<Agenzia> agenzie = dh.getAgenzieConPacchettiInVendita();

		for(Agenzia a : agenzie) {
			String s = a.getNome() + ", ";

			for (Pacchetto p : a.getPacchetti()) {
				System.out.println(s + p.toString());
				for(Servizio sss : p.getServizi()) {
					System.out.println("\t" + sss);
				}
			}
			System.out.println();
		}

		//	System.out.println("\n" + dh.insertAttivita(new Attivita("Volando sul Central Park", "13:00", "19:00", 23, "SuperHero Park")));
		//	System.out.println("\n" + dh.insertAgenzia(new Agenzia("PRNS", "Pronostico", "0495709", "Vinciprova", "Delle Scarpette", "584")));

		System.out.println("");
		System.out.println("Incasso Giornaliero SuperHero Park: " + dh.getIncassoGiornaliero("SuperHero Park"));

		System.out.println("");
		System.out.println("Incasso Settimanale SuperHero Park: " + dh.getIncassoSettimanale("SuperHero Park"));

		System.out.println("");
		System.out.println("Incasso Mensile SuperHero Park: " + dh.getIncassoMensile("SuperHero Park"));

		System.out.println("");
		Pacchetto p = new Pacchetto(Long.toString(System.currentTimeMillis()), "Pacchetto di prova", "Ciaooooo",120, "GNZS");
		ArrayList<String> servizi = new ArrayList<>();
		servizi.add("BTTL");
		servizi.add("LLTM");
		servizi.add("PNPR");
		servizi.add("CBPR");
		//dh.insertPacchetto(p, servizi);


		System.out.println("getAttivitaConOfferteAttiveDiUnParco");
		ArrayList<Attivita> arr = dh.getAttivitaConOfferteAttiveDiUnParco("SuperHero Park", Date.valueOf("2017-01-25"));
		ArrayList<String> att = new ArrayList<>();

		float prezzoBiglietto = 0;
		for(Attivita a: arr) {
			System.out.println(a);
			for(Offerta o : a.getOfferte()) {
				System.out.println("\t" + o);
			}
			prezzoBiglietto = prezzoBiglietto + a.getPrezzoScontato().floatValue();
			System.out.println();
			att.add(a.getNome());
		}

		System.out.println("\n");
		Biglietto b = new Biglietto(Long.toString(System.currentTimeMillis()), prezzoBiglietto, new Date(System.currentTimeMillis()), "SuperHero Park", "BDDSPC");


		dh.insertBiglietto(b,att);

		ArrayList<Pacchetto> pacchetti = dh.getPacchettiVendutiDaAgenzia("GNZS");

		for(Pacchetto pacc : pacchetti) {
			System.out.println(pacc);
		}

		//inserisciTantiPacchetti();

		//dh.vendiPacchetto("1484739324113","TNNSTR");

		ArrayList<Cliente> ac = dh.getClienti();

		for(Cliente c : ac) {
			System.out.println(c);
		}

	}

	private static void inserisciTantiPacchetti () {
		Pacchetto p = new Pacchetto(Long.toString(System.currentTimeMillis()), "Pacchetto di prova", "Ciaooooo",120, "GNZS");
		ArrayList<String> s = new ArrayList<>();
		s.add("BTTL");
		s.add("LLTM");
		s.add("PNPR");
		s.add("CBPR");
		//dh.insertPacchetto(p, s);

		Pacchetto p1 = new Pacchetto(Long.toString(System.currentTimeMillis()), "Pacchetto di prova 2", "Ciaooooo2",122, "LGRG");
		ArrayList<String> s1 = new ArrayList<>();
		s1.add("LSTT");
		s1.add("MRMN");
		s1.add("GDZL");
		s1.add("LCVD");
		//dh.insertPacchetto(p1, s1);

		Pacchetto p2 = new Pacchetto(Long.toString(System.currentTimeMillis()), "Pacchetto di prova 3", "Ciaooooo3",120, "GNZS");
		ArrayList<String> s2 = new ArrayList<>();
		s2.add("HTLP");
		s2.add("KMHT");
		s2.add("CPSL");
		s2.add("VDDR");
		//dh.insertPacchetto(p2, s2);

		Pacchetto p3 = new Pacchetto(Long.toString(System.currentTimeMillis()), "Pacche35tto d345435i prova4", "Ciaooooo",120, "CNTR");
		ArrayList<String> s3 = new ArrayList<>();
		s3.add("PKML");
		s3.add("MRMN");
		s3.add("PNPR");
		s3.add("LLTC");
		//dh.insertPacchetto(p3, s3);

		Pacchetto p4 = new Pacchetto(Long.toString(System.currentTimeMillis()), "P64356acchet463to 645356di prova", "Ci463545aooooo",120, "DVNS");
		ArrayList<String> s4 = new ArrayList<>();
		s4.add("RSTW");
		s4.add("PNPR");
		s4.add("LLLL");
		s4.add("NLPC");
		//dh.insertPacchetto(p4, s4);

	}
}
