package br.com.ufce.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/oi")
public class OiMundo extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Caminho para o arquivo HTML (ajuste conforme o diretório)
        String htmlFilePath = getServletContext().getRealPath("/pagina.html");
        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));

        out.println(htmlContent);
    }
}