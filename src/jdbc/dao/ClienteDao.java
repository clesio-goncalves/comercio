package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Cliente;

public class ClienteDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public ClienteDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Cliente';");
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

	public void inserir(Cliente cliente) {
		String sql = "INSERT INTO Cliente (cpf_cliente, nome_cliente, telefone_cliente, email_cliente, ativo, endereco_cliente) VALUES (?, ?,?,?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, cliente.getCpf_cliente());
			stmt.setString(2, cliente.getNome_cliente());
			stmt.setString(3, cliente.getTelefone_cliente());
			stmt.setString(4, cliente.getEmail_cliente());
			stmt.setBoolean(5, cliente.getAtivo());
			stmt.setInt(6, cliente.getEndereco_cliente().getCodigo_endereco());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public boolean ativoCpf(Cliente cliente) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT ativo from Cliente where cpf_cliente='"
							+ cliente.getCpf_cliente() + "';");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Boolean ativo = rs.getBoolean("ativo");

				if (ativo) {
					return true;
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public boolean ativoCodigo(Cliente cliente) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT ativo from Cliente where codigo_cliente="
							+ cliente.getCodigo_cliente() + ";");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Boolean ativo = rs.getBoolean("ativo");

				if (ativo) {
					return true;
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public boolean cpfCliente(Cliente cliente) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT cpf_cliente FROM Cliente;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto
				String cpf_cliente = rs.getString("cpf_cliente");

				if (cliente.getCpf_cliente().equals(cpf_cliente)) {
					return true;
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public List<String> listaCliente(Integer codigo_cliente) {
		String sql = "select * from Cliente where codigo_cliente=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_cliente);
			ResultSet rs = stmt.executeQuery();

			List<String> clientes = new ArrayList<String>();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setCpf_cliente(rs.getString("cpf_cliente"));
				cliente.setNome_cliente(rs.getString("nome_cliente"));

				// adicionando o objeto à lista
				clientes.add(cliente.getCpf_cliente());
				clientes.add(cliente.getNome_cliente());
			}

			rs.close();
			stmt.close();
			return clientes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public ArrayList<Integer> comboCliente() {
		try {
			ArrayList<Integer> listaCliente = new ArrayList<Integer>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT codigo_cliente FROM Cliente ORDER BY codigo_cliente DESC;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				listaCliente.add(Integer.parseInt(rs
						.getString("codigo_cliente")));
			}
			rs.close();
			stmt.close();
			return listaCliente;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Cliente> pesquisar(String pesquisa, String acao) {
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_cliente":
				stmt = this.conexao
						.prepareStatement("select * from Cliente where codigo_cliente="
								+ pesquisa + " order by codigo_cliente;");
				break;

			case "cpf_cliente":
				stmt = this.conexao
						.prepareStatement("select * from Cliente where cpf_cliente like '%"
								+ pesquisa + "%' order by cpf_cliente;");
				break;

			case "nome_cliente":
				stmt = this.conexao
						.prepareStatement("select * from Cliente where nome_cliente like '%"
								+ pesquisa + "%' order by nome_cliente;");
				break;

			case "telefone_cliente":
				stmt = this.conexao
						.prepareStatement("select * from Cliente where telefone_cliente like '%"
								+ pesquisa + "%' order by telefone_cliente;");
				break;

			case "email_cliente":
				stmt = this.conexao
						.prepareStatement("select * from Cliente where email_cliente like '%"
								+ pesquisa + "%' order by email_cliente;");
				break;

			case "endereco_cliente":
				stmt = this.conexao
						.prepareStatement("select * from Cliente where endereco_cliente="
								+ pesquisa + " order by endereco_cliente;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setCodigo_cliente(rs.getInt("codigo_cliente"));
				cliente.setCpf_cliente(rs.getString("cpf_cliente"));
				cliente.setNome_cliente(rs.getString("nome_cliente"));
				cliente.setTelefone_cliente(rs.getString("telefone_cliente"));
				cliente.setEmail_cliente(rs.getString("email_cliente"));
				cliente.getEndereco_cliente().setCodigo_endereco(
						rs.getInt("endereco_cliente"));
				cliente.setAtivo(rs.getBoolean("ativo"));

				// adicionando o objeto à lista
				clientes.add(cliente);
			}

			rs.close();
			stmt.close();
			return clientes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Cliente> listar() {
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * from Cliente;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setCodigo_cliente(rs.getInt("codigo_cliente"));
				cliente.setCpf_cliente(rs.getString("cpf_cliente"));
				cliente.setNome_cliente(rs.getString("nome_cliente"));
				cliente.setTelefone_cliente(rs.getString("telefone_cliente"));
				cliente.setEmail_cliente(rs.getString("email_cliente"));
				cliente.setAtivo(rs.getBoolean("ativo"));
				cliente.getEndereco_cliente().setCodigo_endereco(
						rs.getInt("endereco_cliente"));

				// adicionando o objeto à lista
				clientes.add(cliente);
			}

			rs.close();
			stmt.close();
			return clientes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int countAtivoInativo(Boolean valor) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Cliente where ativo="
							+ valor + ";");
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

	public int countClienteVenda(Integer codigo_cliente) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Pedido as p inner join Cliente as c on c.codigo_cliente = p.cliente_pedido where c.codigo_cliente="
							+ codigo_cliente + ";");
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

	public List<Cliente> consultaInativo() {
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * from Cliente where ativo=false;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setCodigo_cliente(rs.getInt("codigo_cliente"));
				cliente.setCpf_cliente(rs.getString("cpf_cliente"));
				cliente.setNome_cliente(rs.getString("nome_cliente"));
				cliente.setTelefone_cliente(rs.getString("telefone_cliente"));
				cliente.setEmail_cliente(rs.getString("email_cliente"));
				cliente.setAtivo(rs.getBoolean("ativo"));
				cliente.getEndereco_cliente().setCodigo_endereco(
						rs.getInt("endereco_cliente"));

				// adicionando o objeto à lista
				clientes.add(cliente);
			}

			rs.close();
			stmt.close();
			return clientes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o Cpf do Cliente
	public void alterar(Cliente cliente) {
		String sql = "UPDATE Cliente SET nome_cliente=?, telefone_cliente=?, email_cliente=?, ativo=?, endereco_cliente=? WHERE codigo_cliente=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, cliente.getNome_cliente());
			stmt.setString(2, cliente.getTelefone_cliente());
			stmt.setString(3, cliente.getEmail_cliente());
			stmt.setBoolean(4, cliente.getAtivo());
			stmt.setInt(5, cliente.getEndereco_cliente().getCodigo_endereco());
			stmt.setInt(6, cliente.getCodigo_cliente());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Cliente cliente) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Cliente WHERE codigo_cliente=?;");

			// preenche os valores
			stmt.setInt(1, cliente.getCodigo_cliente());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
