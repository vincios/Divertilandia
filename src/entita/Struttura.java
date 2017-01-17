package entita;

public class Struttura {
	private String codice; 
	private String nome;
	private String nomeParco;
	public Struttura(String codice, String nome, String nomeParco) {
		super();
		this.codice = codice;
		this.nome = nome;
		this.nomeParco = nomeParco;
	}
	public String getCodice() {
		return codice;
	}
	public String getNome() {
		return nome;
	}
	public String getNomeParco() {
		return nomeParco;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setNomeParco(String nomeParco) {
		this.nomeParco = nomeParco;
	}
	@Override
	public String toString() {
		return codice + ", " + nome + ", " + nomeParco;
	}
	
}
