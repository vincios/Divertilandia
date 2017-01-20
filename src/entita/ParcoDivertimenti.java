package entita;

import java.util.ArrayList;

public class ParcoDivertimenti {
	private String nome;
	private String sede;
	private String tipo;
	private int nBiglietti;
	private String percorso;
	private String tema;
	private int nPiscine;
	private ArrayList<String> contattiTelefonici;

	
	public ParcoDivertimenti(String nome, String sede, String tipo, int nBiglietti, String percorso, String tema,
			int nPiscine) {
		super();
		this.nome = nome;
		this.sede = sede;
		this.tipo = tipo;
		this.nBiglietti = nBiglietti;
		this.percorso = percorso;
		this.tema = tema;
		this.nPiscine = nPiscine;
		this.contattiTelefonici = new ArrayList<>();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSede() {
		return sede;
	}
	
	public void setSede(String sede) {
		this.sede = sede;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getnBiglietti() {
		return nBiglietti;
	}
	
	public void setnBiglietti(int nBiglietti) {
		this.nBiglietti = nBiglietti;
	}
	
	public String getPercorso() {
		return percorso;
	}
	
	public void setPercorso(String percorso) {
		this.percorso = percorso;
	}
	
	public String getTema() {
		return tema;
	}
	
	public void setTema(String tema) {
		this.tema = tema;
	}
	
	public int getnPiscine() {
		return nPiscine;
	}
	
	public void setnPiscine(int nPiscine) {
		this.nPiscine = nPiscine;
	}
	
	public void addNumeroTelefonico(String nTelefono) {
		this.contattiTelefonici.add(nTelefono);
	}
	
	public ArrayList<String> getNumeriTelefonici() {
		return this.contattiTelefonici;
	}

	public String numeriTelefoniciToString(){
		String r = contattiTelefonici.get(0);
		for(int i = 1; i<contattiTelefonici.size(); i++)
			r += ", " + contattiTelefonici.get(i);

		return r;
	}

	@Override
	public String toString() {
		String str =  nome + ", " + sede + ", " + tipo + ", " + nBiglietti + ", " + percorso + ", " + tema + ", " + nPiscine;
		
		for(String s : contattiTelefonici) {
			str = str + ", " + s; 
		}
		
		return str;
	}
	
}
