package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Fornecedor;

public class FornecedorDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public FornecedorDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Fornecedor';");
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

	public void inserir(Fornecedor fornecedor) {
		String sql = "INSERT INTO Fornecedor (cnpj_fornecedor, nome_fornecedor, telefone_fornecedor, email_fornecedor, endereco_fornecedor) VALUES (?,?,?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, fornecedor.getCnpj_fornecedor());
			stmt.setString(2, fornecedor.getNome_fornecedor());
			stmt.setString(3, fornecedor.getTelefone_fornecedor());
			stmt.setString(4, fornecedor.getEmail_fornecedor());
			stmt.setInt(5, fornecedor.getEndereco_fornecedor()
					.getCodigo_endereco());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean cnpjFornecedor(Fornecedor fornecedor) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT cnpj_fornecedor FROM Fornecedor;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto
				String cnpj_fornecedor = rs.getString("cnpj_fornecedor");

				if (fornecedor.getCnpj_fornecedor().equals(cnpj_fornecedor)) {
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

	public int count() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Fornecedor;");
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

	public int countFornecedorCompra(Integer codigo_fornecedor) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Compra as c inner join Fornecedor as f on f.codigo_fornecedor = c.fornecedor_compra where f.codigo_fornecedor="
							+ codigo_fornecedor + ";");
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

	public String codigoFornecedor(Integer codigo_produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select co.fornecedor_compra from Produto as p inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto inner join Compra as co on co.codigo_compra = ic.codigo_compra where p.codigo_produto="
							+ codigo_produto + ";");
			ResultSet rs = stmt.executeQuery();

			String codigo = "";

			while (rs.next()) {
				codigo = rs.getString("fornecedor_compra").toString();
			}
			rs.close();
			stmt.close();
			return codigo;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public ArrayList<Integer> comboFornecedor() {
		try {
			ArrayList<Integer> listaFornecedor = new ArrayList<Integer>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT codigo_fornecedor FROM Fornecedor ORDER BY codigo_fornecedor DESC;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				listaFornecedor.add(Integer.parseInt(rs
						.getString("codigo_fornecedor")));
			}
			rs.close();
			stmt.close();
			return listaFornecedor;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> listaFornecedor(Integer codigo_fornecedor) {
		String sql = "select * from Fornecedor where codigo_fornecedor=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_fornecedor);
			ResultSet rs = stmt.executeQuery();

			List<String> fornecedores = new ArrayList<String>();

			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setCnpj_fornecedor(rs.getString("cnpj_fornecedor"));
				fornecedor.setNome_fornecedor(rs.getString("nome_fornecedor"));

				// adicionando o objeto à lista
				fornecedores.add(fornecedor.getCnpj_fornecedor());
				fornecedores.add(fornecedor.getNome_fornecedor());
			}

			rs.close();
			stmt.close();
			return fornecedores;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Fornecedor> pesquisar(String pesquisa, String acao) {
		try {
			List<Fornecedor> fornecedors = new ArrayList<Fornecedor>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_fornecedor":
				stmt = this.conexao
						.prepareStatement("select * from Fornecedor where codigo_fornecedor="
								+ pesquisa + " order by codigo_fornecedor;");
				break;

			case "cnpj_fornecedor":
				stmt = this.conexao
						.prepareStatement("select * from Fornecedor where cnpj_fornecedor like '%"
								+ pesquisa + "%' order by cnpj_fornecedor;");
				break;

			case "nome_fornecedor":
				stmt = this.conexao
						.prepareStatement("select * from Fornecedor where nome_fornecedor like '%"
								+ pesquisa + "%' order by nome_fornecedor;");
				break;

			case "telefone_fornecedor":
				stmt = this.conexao
						.prepareStatement("select * from Fornecedor where telefone_fornecedor like '%"
								+ pesquisa + "%' order by telefone_fornecedor;");
				break;

			case "email_fornecedor":
				stmt = this.conexao
						.prepareStatement("select * from Fornecedor where email_fornecedor like '%"
								+ pesquisa + "%' order by email_fornecedor;");
				break;

			case "endereco_fornecedor":
				stmt = this.conexao
						.prepareStatement("select * from Fornecedor where endereco_fornecedor="
								+ pesquisa + " order by endereco_fornecedor;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setCodigo_fornecedor(rs.getInt("codigo_fornecedor"));
				fornecedor.setCnpj_fornecedor(rs.getString("cnpj_fornecedor"));
				fornecedor.setNome_fornecedor(rs.getString("nome_fornecedor"));
				fornecedor.setTelefone_fornecedor(rs
						.getString("telefone_fornecedor"));
				fornecedor
						.setEmail_fornecedor(rs.getString("email_fornecedor"));
				fornecedor.getEndereco_fornecedor().setCodigo_endereco(
						rs.getInt("endereco_fornecedor"));

				// adicionando o objeto à lista
				fornecedors.add(fornecedor);
			}

			rs.close();
			stmt.close();
			return fornecedors;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Fornecedor> listar() {
		try {
			List<Fornecedor> fornecedors = new ArrayList<Fornecedor>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * from Fornecedor;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setCodigo_fornecedor(rs.getInt("codigo_fornecedor"));
				fornecedor.setCnpj_fornecedor(rs.getString("cnpj_fornecedor"));
				fornecedor.setNome_fornecedor(rs.getString("nome_fornecedor"));
				fornecedor.setTelefone_fornecedor(rs
						.getString("telefone_fornecedor"));
				fornecedor
						.setEmail_fornecedor(rs.getString("email_fornecedor"));
				fornecedor.getEndereco_fornecedor().setCodigo_endereco(
						rs.getInt("endereco_fornecedor"));

				// adicionando o objeto à lista
				fornecedors.add(fornecedor);
			}

			rs.close();
			stmt.close();
			return fornecedors;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o Cnpj do Fornecedor
	public void alterar(Fornecedor fornecedor) {
		String sql = "UPDATE Fornecedor SET nome_fornecedor=?, telefone_fornecedor=?, email_fornecedor=?, endereco_fornecedor=? WHERE codigo_fornecedor=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, fornecedor.getNome_fornecedor());
			stmt.setString(2, fornecedor.getTelefone_fornecedor());
			stmt.setString(3, fornecedor.getEmail_fornecedor());
			stmt.setInt(4, fornecedor.getEndereco_fornecedor()
					.getCodigo_endereco());
			stmt.setInt(5, fornecedor.getCodigo_fornecedor());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Fornecedor fornecedor) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Fornecedor WHERE codigo_fornecedor=?;");

			// preenche os valores
			stmt.setInt(1, fornecedor.getCodigo_fornecedor());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
