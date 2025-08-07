package br.com.ufce.logica;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufce.dao.ContatoDao;
import br.com.ufce.modelo.Contato;

public class ListarContatoLogica implements Logica {
	@Override
	public String executa(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Connection connection = (Connection) request.getAttribute("conexao");
		List<Contato> contatos = new ContatoDao(connection).getContatos();
		request.setAttribute("contatos", contatos);
		
		return "cadastro.jsp";
	}
}