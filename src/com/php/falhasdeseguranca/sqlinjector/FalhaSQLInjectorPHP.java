package com.php.falhasdeseguranca.sqlinjector;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;
import com.metodos.padrao.MetodosPadrao;

public class FalhaSQLInjectorPHP implements MetodosPadrao, Cloneable{

	private ArrayList<String> resultadoAnalise;
	private MetodosAdapter adapter;
	
	public FalhaSQLInjectorPHP() {
		resultadoAnalise = new ArrayList<>();
		adapter = new FalhaSQLInjectorPHPAdapter();
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

	private void setResultadoAnalise(ArrayList<String> resultadoAnalise) {
		this.resultadoAnalise = resultadoAnalise;
	}
	
	@Override
	public void clearSession() {
		resultadoAnalise.clear();
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
		FalhaSQLInjectorPHP falhaSQLInjectorPHP = (FalhaSQLInjectorPHP) super.clone();
		ArrayList<String> listaClone = (ArrayList<String>) getResultadoAnalise().clone();
		falhaSQLInjectorPHP.setResultadoAnalise(listaClone);
		return falhaSQLInjectorPHP;
	}

}
