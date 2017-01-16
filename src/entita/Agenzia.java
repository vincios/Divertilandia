package entita;


public class Agenzia {

	private String partitaIva;
	private String nome;
	private String indirizzo;
	private String telefono;
	
	public Agenzia(String partitaIva, String nome, String indirizzo, String telefono) {
		super();
		this.partitaIva = partitaIva;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.telefono = telefono;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public String getNome() {
		return nome;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
