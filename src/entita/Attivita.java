package entita;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Attivita {

	private String nome;
	private String orarioApertura;
	private String orarioChiusura;
	private float costo;
	private String nomeParco;
	private ArrayList<Offerta> offerte;
	
	public Attivita(String nome, String orarioApertura, String orarioChiusura, float costo, String nomeParco) {
		super();
		this.nome = nome;
		this.orarioApertura = orarioApertura;
		this.orarioChiusura = orarioChiusura;
		this.costo = costo;
		this.nomeParco = nomeParco;
		this.offerte = new ArrayList<>();
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

	public BigDecimal getPrezzoScontato() {
		if(offerte.isEmpty())
			return BigDecimal.valueOf(costo);

		float prezzoScontato = costo;
		for(Offerta o : offerte) {
			prezzoScontato = prezzoScontato - (prezzoScontato / 100 * o.getPercentualeSconto());
		}

		BigDecimal bd = new BigDecimal(Float.toString(prezzoScontato));
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd;
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

	public void addOfferta(Offerta o) {
		offerte.add(o);
	}

	public ArrayList<Offerta> getOfferte() {
		return offerte;
	}

	@Override
	public String toString() {
		return nome + ", " + orarioApertura + ", " + orarioChiusura + ", " + costo + ", " + nomeParco;
	}
}
