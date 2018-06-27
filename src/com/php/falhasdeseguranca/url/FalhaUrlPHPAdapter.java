package com.php.falhasdeseguranca.url;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;

public class FalhaUrlPHPAdapter implements MetodosAdapter {

	private ArrayList<String> resultado;
	private ArrayList<String> variaveisCapturadas;
	private int contarChaves = 0;
	private boolean entroIf = false;

	protected FalhaUrlPHPAdapter() {
		resultado = new ArrayList<>();
		variaveisCapturadas = new ArrayList<>();
	}

	@Override
	public void analisa(String linha, int num) {
		analisaContextoDeTratativas(linha, num);
	}

	private void analisaContextoDeTratativas(String linha, int num) {
		linha = linha.replace(" ", "").trim();
		capturaVariaveis(linha);
		if (linha.startsWith(Constants.PALAVRA_IF)
				&& linha.contains(Constants.METODO_ARRAY_SEARCH + Constants.METODO_GET_URL_PHP)) {
			entroIf = true;
		}
		
		if (entroIf) {
			contandoChaves(linha);
		}
		
		if (estaNoIf()) {
			removeVariaveisTratadas(linha);
		} else {
			entroIf = false;
			verificaPossivelFalha(linha, num);
		}
	}
	
	private void verificaPossivelFalha(String linha, int num) {
		for (String variavel : variaveisCapturadas) {
			if (linha.contains(Constants.PALAVRA_INCLUDE + Constants.CARACTER_PARENTES_ABERTO + variavel)) {
				resultado.add(num + Constants.CARACTER_TRACO + linha);
			}
		}
		
	}
 	
	private void removeVariaveisTratadas(String linha) {
		int size = variaveisCapturadas.size();
		for (int i = 0; i < size; i++) {
			if (linha.contains(variaveisCapturadas.get(i))) {
				variaveisCapturadas.remove(i);
			}
		}
	}

	private void capturaVariaveis(String linha) {
		if (linha.contains(Constants.CARACTER_IGUAL + Constants.METODO_GET_URL_PHP)) {
			variaveisCapturadas.add(linha.substring(0,linha.indexOf(Constants.CARACTER_IGUAL)).trim());
		}
	}
	
	private void contandoChaves(String linha) {
		if (linha.contains(Constants.CARACTER_CHAVE_ABERTA)) {
			contarChaves++;
		}

		if (linha.contains(Constants.CARACTER_CHAVE_FECHADA)) {
			contarChaves--;
		}
	}

	private boolean estaNoIf() {
		return contarChaves > 0;
	}
	
	@Override
	public ArrayList<String> getResultado() {
		return resultado;
	}

}
