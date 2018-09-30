package br.com.bruno;

import java.util.ArrayList;

public class EstoqueProduto {
	private ArrayList<Produto> produtos;
	
	public EstoqueProduto(int qtdProdutos, int qtdDeCadaProduto) {
		produtos = new ArrayList<Produto>();
		for(int i = 0; i < qtdProdutos; i++) {
			Produto produto = new Produto(i, qtdDeCadaProduto);
			produtos.add(produto);
		}
	}
	
	public Produto getProduto(int index){
		return produtos.get(index);
	}
	
	public int comprar(int tipoProduto, int qtdCompra) {
		Produto produto = produtos.get(tipoProduto);
		return produto.decrementar(qtdCompra);
	}
}
