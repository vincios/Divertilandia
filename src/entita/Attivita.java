package entita;

public class Attivita {

	private String nome;
	private String orarioApertura;
	private String orarioChiusura;
	private float costo;
	private String nomeParco;
	
	public Attivita(String nome, String orarioApertura, String orarioChiusura, float costo, String nomeParco) {
		super();
		this.nome = nome;
		this.orarioApertura = orarioApertura;
		this.orarioChiusura = orarioChiusura;
		this.costo = costo;
		this.nomeParco = nomeParco;
	}

	public String getNome() {
		return nome;
	}

	public String getOrarioApertura() {
		return orarioApertura;
	}

	public String getOrarioChiusura() {
		return orarioChiusura;
	}

	public float getCosto() {
		return costo;
	}

	public String getNomeParco() {
		return nomeParco;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setOrarioApertura(String orarioApertura) {
		this.orarioApertura = orarioApertura;
	}

	public void setOrarioChiusura(String orarioChiusura) {
		this.orarioChiusura = orarioChiusura;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public void setNomeParco(String nomeParco) {
		this.nomeParco = nomeParco;
	}
	
}
