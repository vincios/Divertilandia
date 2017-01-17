package main;

import java.util.ArrayList;

import entita.ParcoDivertimenti;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		DbHelper dh = new DbHelper();
		
		ArrayList<ParcoDivertimenti> parchi = dh.getInfoParchiDivertimento();
		
		for(ParcoDivertimenti p : parchi) {
			System.out.println(p.toString());
		}
	}

}
