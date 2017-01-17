package entita;

import java.util.ArrayList;

public class Agenzia {

	private String partitaIva;
	private String nome;
	private String telefono;
	private String citta;
	private String via;
	private String nCivico;
	private ArrayList<Pacchetto> pacchetti;
	

	public Agenzia(String partitaIva, String nome, String telefono, String citta, String via, String nCivico) {
		super();
		this.partitaIva = partitaIva;
		this.nome = nome;
		this.telefono = telefono;
		this.citta = citta;
		this.via = via;
		this.nCivico = nCivico;
		this.pacchetti = new ArrayList<>();
	}
	
	public String getPartitaIva() {
		return partitaIva;
	}
	public String getNome() {
		return nome;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getCitta() {
		return citta;
	}
	public String getVia() {
		return via;
	}
	public String getnCivico() {
		return nCivico;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public void setnCivico(String nCivico) {
		this.nCivico = nCivico;
	}
	
	public void addPacchetto(Pacchetto p) {
		pacchetti.add(p);
	}
	
	public ArrayList<Pacchetto> getPacchetti() {
		return pacchetti;
	}
	
	@Override
	public String toString() {
		return partitaIva + ", " + nome + ", " + telefono + ", " + citta
				+ ", " + via + ", " + nCivico;
	}
}
