package Modelo;

public class Especialista_Associacao {

	private int codAssociacao;
	private int codEspecialista;
	private int numeroEspe; /*Esta vari�vel representa em qual
	posi��o de avalia��o est� o especialista.
	*/
	
	public int getCodAssociacao() {
		return codAssociacao;
	}
	
	public void setCodAssociacao(int codAssociacao) {
		this.codAssociacao = codAssociacao;
	}
	
	public int getCodEspecialista() {
		return codEspecialista;
	}
	
	public void setCodEspecialista(int codEspecialista) {
		this.codEspecialista = codEspecialista;
	}
	
	public int getNumeroEspe() {
		return numeroEspe;
	}
	
	public void setNumeroEspe(int numeroEspe) {
		this.numeroEspe = numeroEspe;
	}
	
	
}
