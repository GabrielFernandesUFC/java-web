package br.com.ufce.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufce.logica.Logica;

@WebServlet("/mvc")
public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parametro = request.getParameter("logica");
        String nomeDaClasse = "br.com.ufce.logica." + parametro;

        try {
            Class<?> classe = Class.forName(nomeDaClasse);
            Logica logica = (Logica) classe.getDeclaredConstructor().newInstance();
            String pagina = logica.executa(request, response);
            request.getRequestDispatcher(pagina).forward(request, response);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | 
                 IllegalAccessException | InvocationTargetException e) {
            throw new ServletException("A lógica de negócios causou uma exceção", e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}