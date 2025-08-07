package br.com.ufce.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufce.dao.ContatoDao;
import br.com.ufce.modelo.Contato;

@WebServlet("/adicionarContato")
public class AdicionarContato extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<body>");
        out.println("<h2>Adicionar Contato</h2>");
        out.println("<form action='adicionarContato' method='post'>");
        out.println("<label for='nome'>Nome:</label>");
        out.println("<input type='text' name='nome' required><br>");
        out.println("<label for='endereco'>Endereço:</label>");
        out.println("<input type='text' name='endereco'><br>");
        out.println("<label for='email'>Email:</label>");
        out.println("<input type='email' name='email'><br>");
        out.println("<label for='dataNascimento'>Data de Nascimento (dd/mm/aaaa):</label>");
        out.println("<input type='text' name='dataNascimento' required><br>");
        out.println("<input type='submit' value='Enviar'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Connection connection = (Connection) req.getAttribute("conexao");

        String nome = req.getParameter("nome");
        String endereco = req.getParameter("endereco");
        String email = req.getParameter("email");
        String dataEmTexto = req.getParameter("dataNascimento");
        Calendar dataNascimento = null;

        if (nome == null || nome.trim().isEmpty()) {
            out.println("<h1>Erro: Nome é obrigatório</h1>");
            out.println("<a href='adicionarContato'>Voltar ao formulário</a>");
            return;
        }
        if (dataEmTexto == null || dataEmTexto.trim().isEmpty()) {
            out.println("<h1>Erro: Data de nascimento é obrigatória</h1>");
            out.println("<a href='adicionarContato'>Voltar ao formulário</a>");
            return;
        }

        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dataEmTexto);
            dataNascimento = Calendar.getInstance();
            dataNascimento.setTime(date);
        } catch (ParseException e) {
            out.println("<h1>Erro: Formato de data inválido. Use dd/MM/yyyy</h1>");
            out.println("<a href='adicionarContato'>Voltar ao formulário</a>");
            return;
        }

        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setEndereco(endereco);
        contato.setEmail(email);
        contato.setDataNascimento(dataNascimento);

        try {
            ContatoDao dao = new ContatoDao(connection);
            dao.adiciona(contato);
            resp.sendRedirect("mvc?logica=ListarContatoLogica");
        } catch (Exception e) {
            out.println("<h1>Erro ao salvar contato: " + e.getMessage() + "</h1>");
            out.println("<a href='adicionarContato'>Voltar ao formulário</a>");
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Connection connection = (Connection) req.getAttribute("conexao");
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<h1>Erro: ID do contato é obrigatório</h1>");
            out.println("<a href='mvc?logica=ListarContatoLogica'>Voltar à lista</a>");
            return;
        }
        try {
            Long id = Long.parseLong(idParam);
            Contato contato = new Contato();
            contato.setId(id);
            ContatoDao dao = new ContatoDao(connection);
            dao.removeContato(contato);
            resp.setStatus(HttpServletResponse.SC_OK);
            out.println("<h1>Contato excluído com sucesso!</h1>");
            out.println("<a href='mvc?logica=ListarContatoLogica'>Voltar à lista</a>");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<h1>Erro: ID inválido</h1>");
            out.println("<a href='mvc?logica=ListarContatoLogica'>Voltar à lista</a>");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("<h1>Erro ao excluir contato: " + e.getMessage() + "</h1>");
            out.println("<a href='mvc?logica=ListarContatoLogica'>Voltar à lista</a>");
            e.printStackTrace();
        }
    }
}