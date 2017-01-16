package entita;

public class Pacchetto {

	private String codice;
	private String nome;
	private String descrizione;
	private float prezzo;
	
	public Pacchetto(String codice, String nome, String descrizione, float prezzo, Agenzia agenzia) {
		super();
		this.codice = codice;
		this.nome = nome;
		this.descrizione = descrizione;
		this.prezzo = prezzo;
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
	
}
