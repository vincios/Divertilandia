package entita;

import java.util.ArrayList;

public class Pacchetto {

	private String codice;
	private String nome;
	private String descrizione;
	private float prezzo;
	private String pIvaAgenzia;
	private ArrayList<Servizio> servizi;

	public Pacchetto(String codice, String nome, String descrizione, float prezzo, String pIvaAgenzia) {
		super();
		this.codice = codice;
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
		this.pIvaAgenzia = pIvaAgenzia;
		this.servizi = new ArrayList<>();
	}
	public String getCodice() {
		return codice;
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public String getpIvaAgenzia() {
		return pIvaAgenzia;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public void setpIvaAgenzia(String pIvaAgenzia) {
		this.pIvaAgenzia = pIvaAgenzia;
	}

	public ArrayList<Servizio> getServizi() {
		return servizi;
	}

	public void addServizio(Servizio s) {
		this.servizi.add(s);
	}

	@Override
	public String toString() {
		return codice + ", " + nome + ", " + descrizione + ", " + prezzo + ", " + pIvaAgenzia;
	}

}
