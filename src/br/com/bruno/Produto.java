package br.com.bruno;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author bruno
 *
 */
public class Produto {
	private int tipo;
	private AtomicInteger qtd;
	
	public Produto(int tipo, int qtd) {
		this.tipo = tipo;
		this.qtd = new AtomicInteger(qtd);
	}

	public int getTipo() {
		return tipo;
	}
	
	public synchronized int getQtd() {
		return qtd.get();
	}
	
	public synchronized int decrementar(int decremento) {
		if(qtd.get() - decremento < 0) {
			throw new IllegalArgumentException("Estoque zerado!!");
		}else {
			for(int i = 0; i < decremento; i++) {
				qtd.decrementAndGet();
			}
			return qtd.get();
		}
	}
}
