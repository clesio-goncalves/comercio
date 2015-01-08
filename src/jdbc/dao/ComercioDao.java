package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Comercio;

public class ComercioDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public ComercioDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Comercio';");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				return rs.getLong("auto_increment");
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return -1;
	}

	public void inserir(Comercio comercio) {
		String sql = "INSERT INTO Comercio (cnpj_comercio, inscricao_estadual, nome_comercio, telefone_comercio, email_comercio, endereco_comercio) VALUES (?,?,?,?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, comercio.getCnpj_comercio());
			stmt.setString(2, comercio.getInscricao_estadual());
			stmt.setString(3, comercio.getNome_comercio());
			stmt.setString(4, comercio.getTelefone_comercio());
			stmt.setString(5, comercio.getEmail_comercio());
			stmt.setInt(6, comercio.getEndereco_comercio().getCodigo_endereco());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean cnpjComercio(Comercio comercio) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT cnpj_comercio FROM Comercio;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto
				String cnpj_comercio = rs.getString("cnpj_comercio");

				if (comercio.getCnpj_comercio().equals(cnpj_comercio)) {
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

	public String retornaComercio() {
		try {
			String comercio = null;
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_comercio FROM Comercio;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				comercio = rs.getString("nome_comercio");
			}
			rs.close();
			stmt.close();
			return comercio;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> listaComercio(Integer codigo_comercio) {
		String sql = "select * from Comercio where codigo_comercio=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_comercio);
			ResultSet rs = stmt.executeQuery();

			List<String> comercios = new ArrayList<String>();

			while (rs.next()) {
				Comercio comercio = new Comercio();
				comercio.setCnpj_comercio(rs.getString("cnpj_comercio"));
				comercio.setNome_comercio(rs.getString("nome_comercio"));

				// adicionando o objeto à lista
				comercios.add(comercio.getCnpj_comercio());
				comercios.add(comercio.getNome_comercio());
			}

			rs.close();
			stmt.close();
			return comercios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Comercio> pesquisar(String pesquisa, String acao) {
		try {
			List<Comercio> comercios = new ArrayList<Comercio>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_comercio":
				stmt = this.conexao
						.prepareStatement("select codigo_comercio from Comercio where codigo_comercio="
								+ pesquisa + " order by codigo_comercio;");
				break;

			case "cnpj_comercio":
				stmt = this.conexao
						.prepareStatement("select nome_usuario from Comercio where nome_usuario like '%"
								+ pesquisa + "%' order by nome_usuario;");
				break;

			case "inscricao_estadual":
				stmt = this.conexao
						.prepareStatement("select inscricao_estadual from Comercio where inscricao_estadual like '%"
								+ pesquisa + "%' order by inscricao_estadual;");
				break;

			case "nome_comercio":
				stmt = this.conexao
						.prepareStatement("select nome_comercio from Comercio where nome_comercio like '%"
								+ pesquisa + "%' order by nome_comercio;");
				break;

			case "telefone_comercio":
				stmt = this.conexao
						.prepareStatement("select telefone_comercio from Comercio where telefone_comercio like '%"
								+ pesquisa + "%' order by telefone_comercio;");
				break;

			case "email_comercio":
				stmt = this.conexao
						.prepareStatement("select email_comercio from Comercio where email_comercio like '%"
								+ pesquisa + "%' order by email_comercio;");
				break;

			case "endereco_comercio":
				stmt = this.conexao
						.prepareStatement("select endereco_comercio from Comercio where endereco_comercio="
								+ pesquisa + " order by endereco_comercio;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Comercio comercio = new Comercio();
				comercio.setCodigo_comercio(rs.getInt("codigo_comercio"));
				comercio.setCnpj_comercio(rs.getString("cnpj_comercio"));
				comercio.setInscricao_estadual(rs
						.getString("inscricao_estadual"));
				comercio.setNome_comercio(rs.getString("nome_comercio"));
				comercio.setTelefone_comercio(rs.getString("telefone_comercio"));
				comercio.setEmail_comercio(rs.getString("email_comercio"));
				comercio.getEndereco_comercio().setCodigo_endereco(
						rs.getInt("endereco_comercio"));

				// adicionando o objeto à lista
				comercios.add(comercio);
			}

			rs.close();
			stmt.close();
			return comercios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int count() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Comercio;");
			ResultSet rs = stmt.executeQuery();

			int cont = 0;

			while (rs.next()) {
				cont = rs.getInt("count(*)");
			}
			rs.close();
			stmt.close();
			return cont;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Comercio> listar() {
		try {
			List<Comercio> comercios = new ArrayList<Comercio>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from Comercio");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Comercio comercio = new Comercio();
				comercio.setCodigo_comercio(rs.getInt("codigo_comercio"));
				comercio.setCnpj_comercio(rs.getString("cnpj_comercio"));
				comercio.setInscricao_estadual(rs
						.getString("inscricao_estadual"));
				comercio.setNome_comercio(rs.getString("nome_comercio"));
				comercio.setTelefone_comercio(rs.getString("telefone_comercio"));
				comercio.setEmail_comercio(rs.getString("email_comercio"));
				comercio.getEndereco_comercio().setCodigo_endereco(
						rs.getInt("endereco_comercio"));

				// adicionando o objeto à lista
				comercios.add(comercio);
			}

			rs.close();
			stmt.close();
			return comercios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o CNPJ
	public void alterar(Comercio comercio) {
		String sql = "UPDATE Comercio SET inscricao_estadual=?, nome_comercio=?, telefone_comercio=?, email_comercio=?, endereco_comercio=? WHERE codigo_comercio=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, comercio.getInscricao_estadual());
			stmt.setString(2, comercio.getNome_comercio());
			stmt.setString(3, comercio.getTelefone_comercio());
			stmt.setString(4, comercio.getEmail_comercio());
			stmt.setInt(5, comercio.getEndereco_comercio().getCodigo_endereco());
			stmt.setInt(6, comercio.getCodigo_comercio());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void remover(Comercio comercio) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Comercio WHERE codigo_comercio=?;");

			// preenche os valores
			stmt.setInt(1, comercio.getCodigo_comercio());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
