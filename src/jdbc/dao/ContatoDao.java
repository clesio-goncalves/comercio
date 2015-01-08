package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Contato;

public class ContatoDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public ContatoDao() {
		this.conexao = new Conexao().getConnection();
	}

	public void inserir(Contato contato) {
		String sql = "INSERT INTO Contato (cliente_contato, nome_contato, telefone_contato) VALUES (?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setInt(1, contato.getCliente_contato().getCodigo_cliente());
			stmt.setString(2, contato.getNome_contato());
			stmt.setString(3, contato.getTelefone_contato());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean nomeContato(Contato contato) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_contato FROM Contato;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto
				String nome_contato = rs.getString("nome_contato");

				if (contato.getNome_contato().equals(nome_contato)) {
					return true;
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}
		return false;
	}

	public List<Contato> pesquisar(String pesquisa, String acao) {
		try {
			List<Contato> contatos = new ArrayList<Contato>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "cliente_contato":
				stmt = this.conexao
						.prepareStatement("select * from Contato where cliente_contato="
								+ pesquisa + " order by cliente_contato;");
				break;

			case "nome_contato":
				stmt = this.conexao
						.prepareStatement("select * from Contato where nome_contato like '%"
								+ pesquisa + "%' order by nome_contato;");
				break;

			case "telefone_contato":
				stmt = this.conexao
						.prepareStatement("select * from Contato where telefone_contato like '%"
								+ pesquisa + "%' order by telefone_contato;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Contato contato = new Contato();
				contato.getCliente_contato().setCodigo_cliente(
						rs.getInt("cliente_contato"));
				contato.setNome_contato(rs.getString("nome_contato"));
				contato.setTelefone_contato(rs.getString("telefone_contato"));

				// adicionando o objeto à lista
				contatos.add(contato);
			}

			rs.close();
			stmt.close();
			return contatos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Contato> listar() {
		try {
			List<Contato> conta_pagas = new ArrayList<Contato>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * from Contato;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Contato contato = new Contato();
				contato.getCliente_contato().setCodigo_cliente(
						rs.getInt("cliente_contato"));
				contato.setNome_contato(rs.getString("nome_contato"));
				contato.setTelefone_contato(rs.getString("telefone_contato"));

				// adicionando o objeto à lista
				conta_pagas.add(contato);
			}

			rs.close();
			stmt.close();
			return conta_pagas;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Só permite alterar o nome do telefone
	public void alterar(Contato contato) {
		String sql = "UPDATE Contato SET telefone_contato=? WHERE cliente_contato=? AND nome_contato=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, contato.getTelefone_contato());
			stmt.setInt(2, contato.getCliente_contato().getCodigo_cliente());
			stmt.setString(3, contato.getNome_contato());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Contato contato) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Contato WHERE cliente_contato=? AND nome_contato=?;");

			// preenche os valores
			stmt.setInt(1, contato.getCliente_contato().getCodigo_cliente());
			stmt.setString(2, contato.getNome_contato());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
