package main;

import java.sql.Date;
import java.util.ArrayList;

import entita.*;

public class MainClass {

	public static void main(String[] args) {

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
		
		System.out.println("");
		System.out.println("Incasso Settimanale SuperHero Park: " + dh.getIncassoSettimanale("SuperHero Park"));

		System.out.println("");
		System.out.println("Incasso Mensile SuperHero Park: " + dh.getIncassoMensile("SuperHero Park"));

		Pacchetto p = new Pacchetto(Long.toString(System.currentTimeMillis()), "Pacchetto di prova", "Ciaooooo",120, "GNZS");
		ArrayList<String> hotels = new ArrayList<>();
		hotels.add("BTTL");
		ArrayList<String> ristoranti = new ArrayList<>();
		ristoranti.add("LLTM");
		ristoranti.add("PNPR");
		ristoranti.add("CBPR");
		dh.insertPacchetto(p, hotels,ristoranti);

		ArrayList<Attivita> arr = dh.getAttivitaConOfferteAttiveDiUnParco("SuperHero Park", new Date(2017,4,14));
		ArrayList<String> att = new ArrayList<>();

		for(Attivita a: arr) {
			att.add(a.getNome());
		}

		Biglietto b = new Biglietto(Long.toString(System.currentTimeMillis()),0, new Date(System.currentTimeMillis()), "SuperHero Park", "BDDSPC");


		dh.insertBiglietto(b,att);

		ArrayList<Pacchetto> pacchetti = dh.getPacchettiVendutiDaAgenzia("GNZS");

		for(Pacchetto pacc : pacchetti) {
			System.out.println(pacc);
		}

		dh.vendiPacchetto("1484739324113","TNNSTR");

	}
}
