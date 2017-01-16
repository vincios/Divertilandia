package entita;

public class GruppoAttivita {
	private String Nome;
	private String NomeParco;
	private String costoPromozionale;
	public GruppoAttivita(String nome, String nomeParco, String costoPromozionale) {
		super();
		Nome = nome;
		NomeParco = nomeParco;
		this.costoPromozionale = costoPromozionale;
	}
	public String getNome() {
		return Nome;
	}
	public String getNomeParco() {
		return NomeParco;
	}
	public String getCostoPromozionale() {
		return costoPromozionale;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public void setNomeParco(String nomeParco) {
		NomeParco = nomeParco;
	}
	public void setCostoPromozionale(String costoPromozionale) {
		this.costoPromozionale = costoPromozionale;
	}
	
	
}
