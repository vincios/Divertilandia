package main;

import java.sql.Date;
import java.util.ArrayList;

import entita.*;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DbHelper dh = new DbHelper();
		
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
		
		
		System.out.println("");
		ArrayList<Agenzia> agenzie = dh.getAgenzieConPacchettiInVendita();
		
		for(Agenzia a : agenzie) {
			String s = a.getNome() + ", ";
			
			for (Pacchetto p : a.getPacchetti()) {
				System.out.println(s + p.toString());
			}
		}
	
	//	System.out.println("\n" + dh.insertAttivita(new Attivita("Volando sul Central Park", "13:00", "19:00", 23, "SuperHero Park")));
	//	System.out.println("\n" + dh.insertAgenzia(new Agenzia("PRNS", "Pronostico", "0495709", "Vinciprova", "Delle Scarpette", "584")));
		
		System.out.println("");
		System.out.println("Incasso Giornaliero SuperHero Park: " + dh.getIncassoGiornaliero("SuperHero Park"));
	}
}
