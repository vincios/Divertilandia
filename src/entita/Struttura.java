package entita;

public class Struttura {
	private String codice; 
	private String nome;
	
	public Struttura(String codice, String nome) {
		super();
		this.codice = codice;
		this.nome = nome;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
