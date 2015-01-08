package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Funcionario;

public class FuncionarioDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public FuncionarioDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Funcionario';");
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

	public void inserir(Funcionario funcionario) {
		String sql = "INSERT INTO Funcionario (cpf_funcionario, nome_funcionario, telefone_funcionario, email_funcionario, sexo_funcionario, ativo, cargo_funcionario, comercio_funcionario, usuario_funcionario)  VALUES (?,?,?,?,?,?,(select codigo_cargo as cargo_funcionario from Cargo where nome_cargo like '"
				+ funcionario.getCargo_funcionario().getNome_cargo()
				+ "'),(select codigo_comercio as comercio_funcionario from Comercio where nome_comercio like '"
				+ funcionario.getComercio_funcionario().getNome_comercio()
				+ "'),(select codigo_usuario as usuario_funcionario from Usuario where nome_usuario like '"
				+ funcionario.getUsuario_funcionario().getNome_usuario()
				+ "'));";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, funcionario.getCpf_funcionario());
			stmt.setString(2, funcionario.getNome_funcionario());
			stmt.setString(3, funcionario.getTelefone_funcionario());
			stmt.setString(4, funcionario.getEmail_funcionario());
			stmt.setString(5, funcionario.getSexo_funcionario());
			stmt.setBoolean(6, funcionario.getAtivo());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Funcionario> pesquisar(String pesquisa, String acao) {
		try {
			List<Funcionario> funcionarios = new ArrayList<Funcionario>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_funcionario":
				stmt = this.conexao
						.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where f.codigo_funcionario="
								+ pesquisa + " order by f.codigo_funcionario;");
				break;

			case "cpf_funcionario":
				stmt = this.conexao
						.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where f.cpf_funcionario like '%"
								+ pesquisa + "%' order by f.cpf_funcionario;");
				break;

			case "nome_funcionario":
				stmt = this.conexao
						.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where f.nome_funcionario like '%"
								+ pesquisa + "%' order by f.nome_funcionario;");
				break;

			case "telefone_funcionario":
				stmt = this.conexao
						.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where f.telefone_funcionario like '%"
								+ pesquisa
								+ "%' order by f.telefone_funcionario;");
				break;

			case "email_funcionario":
				stmt = this.conexao
						.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where f.email_funcionario like '%"
								+ pesquisa + "%' order by f.email_funcionario;");
				break;

			case "sexo_funcionario":
				stmt = this.conexao
						.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where f.sexo_funcionario like '"
								+ pesquisa + "' order by f.sexo_funcionario;");
				break;

			case "cargo_funcionario":
				stmt = this.conexao
						.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where c.nome_cargo like '%"
								+ pesquisa + "%' order by c.nome_cargo;");
				break;

			case "usuario_funcionario":
				stmt = this.conexao
						.prepareStatement("select f.*, u.nome_usuario, c.nome_cargo from Funcionario as f inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where u.nome_usuario like '%"
								+ pesquisa + "%' order by u.nome_usuario;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setCodigo_funcionario(rs
						.getInt("codigo_funcionario"));
				funcionario.setCpf_funcionario(rs.getString("cpf_funcionario"));
				funcionario.setNome_funcionario(rs
						.getString("nome_funcionario"));
				funcionario.setTelefone_funcionario(rs
						.getString("telefone_funcionario"));
				funcionario.setEmail_funcionario(rs
						.getString("email_funcionario"));
				funcionario.setSexo_funcionario(rs
						.getString("sexo_funcionario"));
				funcionario.setAtivo(rs.getBoolean("ativo"));
				funcionario.getCargo_funcionario().setNome_cargo(
						rs.getString("nome_cargo"));
				funcionario.getUsuario_funcionario().setCodigo_usuario(
						rs.getInt("usuario_funcionario"));
				

				// adicionando o objeto à lista
				funcionarios.add(funcionario);
			}

			rs.close();
			stmt.close();
			return funcionarios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean cpfFuncionario(Funcionario funcionario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT cpf_funcionario FROM Funcionario;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto
				String cpf_funcionario = rs.getString("cpf_funcionario");

				if (funcionario.getCpf_funcionario().equals(cpf_funcionario)) {
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

	public String consultarNomeFuncionario() {
		try {
			UsuarioDao dao = new UsuarioDao();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select f.nome_funcionario from Funcionario as f inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario where u.nome_usuario like '"
							+ dao.consultarNomeLogado() + "';");
			ResultSet rs = stmt.executeQuery();

			String nome = null;

			while (rs.next()) {
				nome = rs.getString("nome_funcionario");
			}
			rs.close();
			stmt.close();
			return nome;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public int consultarCodigoFuncionario() {
		try {
			UsuarioDao dao = new UsuarioDao();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select f.codigo_funcionario from Funcionario as f inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario where u.nome_usuario like '"
							+ dao.consultarNomeLogado() + "';");
			ResultSet rs = stmt.executeQuery();

			int codigo = 0;

			while (rs.next()) {
				codigo = rs.getInt("codigo_funcionario");
			}
			rs.close();
			stmt.close();
			return codigo;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Funcionario> listar() {
		try {
			List<Funcionario> funcionarios = new ArrayList<Funcionario>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setCodigo_funcionario(rs
						.getInt("codigo_funcionario"));
				funcionario.setCpf_funcionario(rs.getString("cpf_funcionario"));
				funcionario.setNome_funcionario(rs
						.getString("nome_funcionario"));
				funcionario.setTelefone_funcionario(rs
						.getString("telefone_funcionario"));
				funcionario.setEmail_funcionario(rs
						.getString("email_funcionario"));
				funcionario.setSexo_funcionario(rs
						.getString("sexo_funcionario"));
				funcionario.setAtivo(rs.getBoolean("ativo"));
				funcionario.getCargo_funcionario().setNome_cargo(
						rs.getString("nome_cargo"));
				funcionario.getUsuario_funcionario().setCodigo_usuario(
						rs.getInt("usuario_funcionario"));

				// adicionando o objeto à lista
				funcionarios.add(funcionario);
			}

			rs.close();
			stmt.close();
			return funcionarios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int countAtivoInativo(Boolean valor) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Funcionario where ativo="
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

	public int countFuncionarioAtivoUsuarioAtivo3() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT count(*) FROM Funcionario as f inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario where f.ativo=true and u.ativo=true and u.nivel_usuario=3;");
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

	public int countFuncionarioVenda(Integer codigo_funcionario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Pedido as p inner join Funcionario as f on f.codigo_funcionario = p.funcionario_pedido where f.codigo_funcionario="
							+ codigo_funcionario + ";");
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

	public int countFuncionarioCompra(Integer codigo_funcionario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Compra as c inner join Funcionario as f on f.codigo_funcionario = c.funcionario_compra where f.codigo_funcionario="
							+ codigo_funcionario + ";");
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

	public boolean usuarioFuncionario(Funcionario funcionario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select f.* from Funcionario as f inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario where f.cpf_funcionario like '"
							+ funcionario.getCpf_funcionario()
							+ "' and u.ativo=true and u.nivel_usuario=3;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				return true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public boolean ativo(Funcionario funcionario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select ativo from Funcionario where cpf_funcionario like '"
							+ funcionario.getCpf_funcionario() + "';");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Boolean ativo = rs.getBoolean("ativo");
				if (ativo == true) {
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

	public List<Funcionario> consultaInativo() {
		try {
			List<Funcionario> funcionarios = new ArrayList<Funcionario>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select f.*, c.nome_cargo from Funcionario as f inner join Cargo as c on c.codigo_cargo = f.cargo_funcionario where f.ativo=false;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setCodigo_funcionario(rs
						.getInt("codigo_funcionario"));
				funcionario.setCpf_funcionario(rs.getString("cpf_funcionario"));
				funcionario.setNome_funcionario(rs
						.getString("nome_funcionario"));
				funcionario.setTelefone_funcionario(rs
						.getString("telefone_funcionario"));
				funcionario.setEmail_funcionario(rs
						.getString("email_funcionario"));
				funcionario.setSexo_funcionario(rs
						.getString("sexo_funcionario"));
				funcionario.setAtivo(rs.getBoolean("ativo"));
				funcionario.getCargo_funcionario().setNome_cargo(
						rs.getString("nome_cargo"));
				funcionario.getUsuario_funcionario().setCodigo_usuario(
						rs.getInt("usuario_funcionario"));

				// adicionando o objeto à lista
				funcionarios.add(funcionario);
			}

			rs.close();
			stmt.close();
			return funcionarios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o Cpf do funcionário
	public void alterar(Funcionario funcionario) {
		String sql = "UPDATE Funcionario SET nome_funcionario=?, telefone_funcionario=?, email_funcionario=?, sexo_funcionario=?, ativo=?, cargo_funcionario=(select codigo_cargo as cargo_funcionario from Cargo where nome_cargo like '"
				+ funcionario.getCargo_funcionario().getNome_cargo()
				+ "'), usuario_funcionario=(select codigo_usuario as usuario_funcionario from Usuario where nome_usuario like '"
				+ funcionario.getUsuario_funcionario().getNome_usuario()
				+ "') WHERE codigo_funcionario=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, funcionario.getNome_funcionario());
			stmt.setString(2, funcionario.getTelefone_funcionario());
			stmt.setString(3, funcionario.getEmail_funcionario());
			stmt.setString(4, funcionario.getSexo_funcionario());
			stmt.setBoolean(5, funcionario.getAtivo());
			stmt.setInt(6, funcionario.getCodigo_funcionario());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Funcionario funcionario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Funcionario WHERE codigo_funcionario=?;");

			// preenche os valores
			stmt.setInt(1, funcionario.getCodigo_funcionario());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
