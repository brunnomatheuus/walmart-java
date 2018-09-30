package br.com.bruno;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Compra")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EstoqueProduto estoque;
	private final int qtdProdutos = 2;
	private final int qtdDeCadaProduto = 2000000;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        estoque = new EstoqueProduto(qtdProdutos, qtdDeCadaProduto);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}/*

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tipoProduto = Integer.valueOf(request.getParameter("tipo"));
		int qtdCompra = Integer.valueOf(request.getParameter("qtdCompra"));
		
		PrintWriter writer = response.getWriter();
		Produto produto = estoque.getProduto(tipoProduto);
		
		try {
			estoque.comprar(tipoProduto, qtdCompra);
			writer.println("{\"compra\": true, \"TipoProduto\": " + tipoProduto + ", \"QuantidadeProduto\": " + produto.getQtd() + "}");
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
			writer.println("{'compra': false}");
		}
		writer.close();
	}

}
