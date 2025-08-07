package br.com.ufce.logica;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufce.dao.ContatoDao;
import br.com.ufce.modelo.Contato;

public class RemoveContatoLogica implements Logica {
    @Override
    public String executa(HttpServletRequest req, HttpServletResponse res) throws Exception {
        long id = Long.parseLong(req.getParameter("id"));
        Contato contato = new Contato();
        contato.setId(id);
        
        Connection connection = (Connection) req.getAttribute("conexao");
        ContatoDao dao = new ContatoDao(connection);
        dao.removeContato(contato);
        System.out.println("Excluindo contato... ");
        return "mvc?logica=ListarContatoLogica"; // Redireciona via ControllerServlet
    }
}