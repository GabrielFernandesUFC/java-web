package br.com.ufce.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.ufce.modelo.Contato;
import br.com.ufce.utils.ConnectionFactory;

public class ContatoDao {
	@SuppressWarnings("unused")
	private Connection connection;
	
	public ContatoDao(Connection connection) {
		this.connection = connection;
	}
	
    public void adiciona(Contato contato) {
        String sql = "insert into contatos (nome, email, endereco, dataNascimento) values (?, ?, ?, ?)";
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getEndereco());
            stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Contato> getContatos() {
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement("select * from contatos");
             ResultSet rs = stmt.executeQuery()) {
            List<Contato> contatos = new ArrayList<>();
            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getLong("id"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setEndereco(rs.getString("endereco"));
                Calendar data = Calendar.getInstance();
                data.setTime(rs.getDate("dataNascimento"));
                contato.setDataNascimento(data);
                contatos.add(contato);
            }
            return contatos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void alteraContato(Contato contato) {
        String sql = "update contatos set nome=?, email=?, endereco=?, dataNascimento=? where id=?";
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getEndereco());
            stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
            stmt.setLong(5, contato.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeContato(Contato contato) {
        try (Connection conn = new ConnectionFactory().getConnection();
             PreparedStatement stmt = conn.prepareStatement("delete from contatos where id=?")) {
            stmt.setLong(1, contato.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}