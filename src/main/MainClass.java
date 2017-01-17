package main;

import java.sql.Date;
import java.util.ArrayList;

import entita.Offerta;
import entita.ParcoDivertimenti;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DbHelper dh = new DbHelper();
		
		ArrayList<ParcoDivertimenti> parchi = dh.getInfoParchiDivertimento();
		
		for(ParcoDivertimenti p : parchi) {
			System.out.println(p.toString());
		}
		
		ArrayList<Offerta> offerte = dh.getOffertePerPeriodo(Date.valueOf("2017-01-01"), Date.valueOf("2017-02-28"));
		
		for(Offerta o : offerte) {
			System.out.println(o.toString());
		}
		
	}

}
