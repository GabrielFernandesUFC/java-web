package br.com.ufce.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = new ConnectionFactory().getConnection();
		System.out.println("Conexão aberta");
		
		System.out.println("Fechando conexão...");
		connection.close();
		System.out.println("Conexão fechada");
	}

}
