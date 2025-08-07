package br.com.ufce.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

// todas as requisições da aplicação são filtradas
@WebFilter("/*")
public class FiltroTempoDeExecucao implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		long tempoInicial = System.currentTimeMillis();
		
		chain.doFilter(request, response);
		long tempoFinal = System.currentTimeMillis();
		
		String uri = ((HttpServletRequest)request).getRequestURI();
		String parametros = ((HttpServletRequest)request).getParameter("logica");
		System.out.println("O tempo da requisição de " 
				+ uri 
				+ "?logica=" 
				+ parametros 
				+ " demorou(ms): "
				+ (tempoFinal - tempoInicial));
	}
	
}
