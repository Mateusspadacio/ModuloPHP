package com.php.falhasdeseguranca.url;

import java.util.ArrayList;

import com.metodos.padrao.MetodosAdapter;
import com.metodos.padrao.MetodosPadrao;

public class FalhaUrlPHP implements MetodosPadrao, Cloneable {

	private ArrayList<String> resultadoAnalise;
	private MetodosAdapter adapter;
	
	public FalhaUrlPHP() {
		resultadoAnalise = new ArrayList<>();
		adapter = new FalhaUrlPHPAdapter();
	}

	@Override
	public void analisando(String linha, int num) {
		adapter.analisa(linha, num);
		resultadoAnalise = adapter.getResultado();
	}

	@Override
	public ArrayList<String> getResultadoAnalise() {
		return resultadoAnalise;
	}

	@Override
	public void clearSession() {
		resultadoAnalise.clear();
	}

	private void setResultadoAnalise(ArrayList<String> resultadoAnalise) {
		this.resultadoAnalise = resultadoAnalise;
	}

	@Override
	public Object clonar() {
		try {
			return clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		FalhaUrlPHP falhaUrlPHP = (FalhaUrlPHP) super.clone();
		ArrayList<String> listaClone = (ArrayList<String>) falhaUrlPHP.getResultadoAnalise().clone();
		falhaUrlPHP.setResultadoAnalise(listaClone);
		return falhaUrlPHP;
	}

}
