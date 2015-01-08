package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Endereco;

public class EnderecoDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public EnderecoDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Endereco';");
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

	public void inserir(Endereco endereco) {
		String sql = "INSERT INTO Endereco (cep, nome_logradouro, numero_logradouro, bairro, uf, cidade, complemento) VALUES (?,?,?,?,?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, endereco.getCep());
			stmt.setString(2, endereco.getNome_logradouro());
			stmt.setInt(3, endereco.getNumero_logradouro());
			stmt.setString(4, endereco.getBairro());
			stmt.setString(5, endereco.getUf());
			stmt.setString(6, endereco.getCidade());
			stmt.setString(7, endereco.getComplemento());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public int count() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Endereco;");
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

	public int countEnderecoCliente(Integer codigo_endereco) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Endereco as e inner join Cliente as c on c.endereco_cliente = e.codigo_endereco where e.codigo_endereco="
							+ codigo_endereco + ";");
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

	public int countEnderecoFornecedor(Integer codigo_endereco) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Endereco as e inner join Fornecedor as f on f.endereco_fornecedor = e.codigo_endereco where e.codigo_endereco="
							+ codigo_endereco + ";");
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

	public int countEnderecoComercio(Integer codigo_endereco) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Endereco as e inner join Comercio as c on c.endereco_comercio = e.codigo_endereco where e.codigo_endereco="
							+ codigo_endereco + ";");
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

	public ArrayList<Integer> comboEndereco() {
		try {
			ArrayList<Integer> listaEndereco = new ArrayList<Integer>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT codigo_endereco FROM Endereco ORDER BY codigo_endereco DESC;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				listaEndereco.add(Integer.parseInt(rs
						.getString("codigo_endereco")));
			}
			rs.close();
			stmt.close();
			return listaEndereco;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> listaEndereco(Integer codigo_endereco) {
		String sql = "select * from Endereco where codigo_endereco=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_endereco);
			ResultSet rs = stmt.executeQuery();

			List<String> enderecos = new ArrayList<String>();

			while (rs.next()) {
				Endereco endereco = new Endereco();
				endereco.setCep(rs.getString("cep"));
				endereco.setNome_logradouro(rs.getString("nome_logradouro"));
				endereco.setNumero_logradouro(rs.getInt("numero_logradouro"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setUf(rs.getString("uf"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setComplemento(rs.getString("complemento"));

				// adicionando o objeto à lista
				enderecos.add(endereco.getCep());
				enderecos.add(endereco.getNome_logradouro());
				enderecos.add(endereco.getNumero_logradouro().toString());
				enderecos.add(endereco.getBairro());
				enderecos.add(endereco.getUf());
				enderecos.add(endereco.getCidade());
				enderecos.add(endereco.getComplemento());
			}

			rs.close();
			stmt.close();
			return enderecos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Endereco> pesquisar(String pesquisa, String acao) {
		try {
			List<Endereco> enderecos = new ArrayList<Endereco>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_endereco":
				stmt = this.conexao
						.prepareStatement("select * from Endereco where codigo_endereco="
								+ pesquisa + " order by codigo_endereco;");
				break;

			case "cep":
				stmt = this.conexao
						.prepareStatement("select * from Endereco where cep like '%"
								+ pesquisa + "%' order by cep;");
				break;

			case "nome_logradouro":
				stmt = this.conexao
						.prepareStatement("select * from Endereco where nome_logradouro like '%"
								+ pesquisa + "%' order by nome_logradouro;");
				break;

			case "numero_logradouro":
				stmt = this.conexao
						.prepareStatement("select * from Endereco where numero_logradouro="
								+ pesquisa + " order by numero_logradouro;");
				break;

			case "bairro":
				stmt = this.conexao
						.prepareStatement("select * from Endereco where bairro like '%"
								+ pesquisa + "%' order by bairro;");
				break;

			case "uf":
				stmt = this.conexao
						.prepareStatement("select * from Endereco where uf like '"
								+ pesquisa + "' order by uf;");
				break;

			case "cidade":
				stmt = this.conexao
						.prepareStatement("select * from Endereco where cidade like '%"
								+ pesquisa + "%' order by cidade;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Endereco endereco = new Endereco();
				endereco.setCodigo_endereco(rs.getInt("codigo_endereco"));
				endereco.setCep(rs.getString("cep"));
				endereco.setNome_logradouro(rs.getString("nome_logradouro"));
				endereco.setNumero_logradouro(rs.getInt("numero_logradouro"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setUf(rs.getString("uf"));
				endereco.setCidade(rs.getString("cidade"));

				// adicionando o objeto à lista
				enderecos.add(endereco);
			}

			rs.close();
			stmt.close();
			return enderecos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Endereco> listar() {
		try {
			List<Endereco> enderecos = new ArrayList<Endereco>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * from Endereco;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Endereco endereco = new Endereco();
				endereco.setCodigo_endereco(rs.getInt("codigo_endereco"));
				endereco.setCep(rs.getString("cep"));
				endereco.setNome_logradouro(rs.getString("nome_logradouro"));
				endereco.setNumero_logradouro(rs.getInt("numero_logradouro"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setUf(rs.getString("uf"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setComplemento(rs.getString("complemento"));

				// adicionando o objeto à lista
				enderecos.add(endereco);
			}

			rs.close();
			stmt.close();
			return enderecos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(Endereco endereco) {
		String sql = "UPDATE Endereco SET cep=?, nome_logradouro=?, numero_logradouro=?, bairro=?, uf=?, cidade=?, complemento=? WHERE codigo_endereco=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, endereco.getCep());
			stmt.setString(2, endereco.getNome_logradouro());
			stmt.setInt(3, endereco.getNumero_logradouro());
			stmt.setString(4, endereco.getBairro());
			stmt.setString(5, endereco.getUf());
			stmt.setString(6, endereco.getCidade());
			stmt.setString(7, endereco.getComplemento());
			stmt.setInt(8, endereco.getCodigo_endereco());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void remover(Endereco endereco) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Endereco WHERE codigo_endereco=?;");

			// preenche os valores
			stmt.setInt(1, endereco.getCodigo_endereco());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
