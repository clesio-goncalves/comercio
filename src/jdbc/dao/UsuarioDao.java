package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Usuario;

public class UsuarioDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public UsuarioDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Usuario';");
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

	public int count() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Usuario where nivel_usuario=3 and ativo=true;");
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

	public int countAtivoInativo(Boolean valor) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Usuario where ativo="
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

	public int countUsuarioFuncionarioVenda(Integer codigo_usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Usuario as u inner join Funcionario as f on f.usuario_funcionario = u.codigo_usuario inner join Pedido as p on p.funcionario_pedido = f.codigo_funcionario where u.codigo_usuario="
							+ codigo_usuario + ";");
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

	public int countUsuarioFuncionarioCompra(Integer codigo_usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Usuario as u inner join Funcionario as f on f.usuario_funcionario = u.codigo_usuario inner join Compra as co on co.funcionario_compra = f.codigo_funcionario where u.codigo_usuario="
							+ codigo_usuario + ";");
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

	public String nomeUsuario(Integer codigo_usuario) {
		String sql = "select nome_usuario from Usuario where codigo_usuario=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_usuario);
			ResultSet rs = stmt.executeQuery();

			String nome = "";

			while (rs.next()) {
				nome = rs.getString("nome_usuario");
			}

			rs.close();
			stmt.close();
			return nome;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public ArrayList<String> comboInsereUsuario() {
		try {
			ArrayList<String> listaUsuario = new ArrayList<String>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT u.nome_usuario FROM Usuario as u  where u.codigo_usuario not in (select f.usuario_funcionario as codigo_usuario from Funcionario as f where f.usuario_funcionario is not null) ORDER BY u.nome_usuario;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				listaUsuario.add(rs.getString("nome_usuario"));
			}
			rs.close();
			stmt.close();
			return listaUsuario;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean login(Usuario usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * FROM Usuario;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto usuario
				String user = rs.getString("nome_usuario");
				String senha = rs.getString("senha_usuario");

				if (usuario.getNome_usuario().equals(user)
						&& usuario.getSenha_usuario().equals(senha)) {
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

	public boolean usuarioFuncionario(Usuario usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select u.nome_usuario from Usuario as u inner join Funcionario as f on u.codigo_usuario = f.usuario_funcionario where u.nome_usuario like '"
							+ usuario.getNome_usuario() + "';");
			ResultSet rs = stmt.executeQuery();

			int cont = 0;

			while (rs.next()) {
				cont++;
			}

			if (cont > 0) {
				return true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public boolean ativo(Usuario usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select ativo from Usuario where nome_usuario like '"
							+ usuario.getNome_usuario() + "';");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Boolean ativo = rs.getBoolean("ativo");

				if (ativo == true) {
					usuario.setLogado(true);
					alterarLogado(usuario);
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

	public boolean ativoFuncionario(Usuario usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select f.ativo from Funcionario as f inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario  where u.nome_usuario like '"
							+ usuario.getNome_usuario() + "';");
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

	public boolean nomeUsuario(Usuario usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_usuario FROM Usuario;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto usuario
				String nomeUsuario = rs.getString("nome_usuario");

				if (usuario.getNome_usuario().equals(nomeUsuario)) {
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

	public boolean nivelUsuario(Usuario usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from Usuario where nome_usuario like '"
							+ usuario.getNome_usuario()
							+ "' and nivel_usuario=3;");
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

	public List<Usuario> pesquisar(String pesquisa, String acao) {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_usuario":
				stmt = this.conexao
						.prepareStatement("select * from Usuario where codigo_usuario="
								+ pesquisa + " order by codigo_usuario;");
				break;

			case "nome_usuario":
				stmt = this.conexao
						.prepareStatement("select * from Usuario where nome_usuario like '%"
								+ pesquisa + "%' order by nome_usuario;");
				break;

			case "nivel_usuario":
				stmt = this.conexao
						.prepareStatement("select * from Usuario where nivel_usuario="
								+ pesquisa + " order by nivel_usuario;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setCodigo_usuario(rs.getInt("codigo_usuario"));
				usuario.setNome_usuario(rs.getString("nome_usuario"));
				usuario.setSenha_usuario(rs.getString("senha_usuario"));
				usuario.setNivel_usuario(rs.getInt("nivel_usuario"));
				usuario.setAtivo(rs.getBoolean("ativo"));

				// adicionando o objeto à lista
				usuarios.add(usuario);
			}

			rs.close();
			stmt.close();
			return usuarios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void inserir(Usuario usuario) {
		String sql = "INSERT INTO Usuario (nome_usuario, senha_usuario, nivel_usuario, ativo) VALUES (?,?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, usuario.getNome_usuario());
			stmt.setString(2, usuario.getSenha_usuario());
			stmt.setInt(3, usuario.getNivel_usuario());
			stmt.setBoolean(4, usuario.getAtivo());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Usuario> consultaInativo() {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from Usuario where ativo=false;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setCodigo_usuario(rs.getInt("codigo_usuario"));
				usuario.setNome_usuario(rs.getString("nome_usuario"));
				usuario.setSenha_usuario(rs.getString("senha_usuario"));
				usuario.setNivel_usuario(rs.getInt("nivel_usuario"));
				usuario.setAtivo(rs.getBoolean("ativo"));

				// adicionando o objeto à lista
				usuarios.add(usuario);
			}

			rs.close();
			stmt.close();
			return usuarios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Usuario> listar() {
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * from Usuario;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setCodigo_usuario(rs.getInt("codigo_usuario"));
				usuario.setNome_usuario(rs.getString("nome_usuario"));
				usuario.setSenha_usuario(rs.getString("senha_usuario"));
				usuario.setNivel_usuario(rs.getInt("nivel_usuario"));
				usuario.setAtivo(rs.getBoolean("ativo"));

				// adicionando o objeto à lista
				usuarios.add(usuario);
			}

			rs.close();
			stmt.close();
			return usuarios;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o nome - pois é único
	public void alterar(Usuario usuario) {
		String sql = "UPDATE Usuario SET senha_usuario=?, nivel_usuario=?, ativo=? WHERE codigo_usuario=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, usuario.getSenha_usuario());
			stmt.setInt(2, usuario.getNivel_usuario());
			stmt.setBoolean(3, usuario.getAtivo());
			stmt.setInt(4, usuario.getCodigo_usuario());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public String consultarNomeLogado() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select nome_usuario from Usuario where logado=true;");
			ResultSet rs = stmt.executeQuery();

			String nome = null;

			while (rs.next()) {
				nome = rs.getString("nome_usuario");
			}
			rs.close();
			stmt.close();
			return nome;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int consultarNivelLogado() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select nivel_usuario from Usuario where logado=true;");
			ResultSet rs = stmt.executeQuery();

			int nivel = 0;

			while (rs.next()) {
				nivel = rs.getInt("nivel_usuario");
			}
			rs.close();
			stmt.close();
			return nivel;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterarLogado(Usuario usuario) {
		String sql = "UPDATE Usuario SET logado=? WHERE nome_usuario like '"
				+ usuario.getNome_usuario() + "';";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setBoolean(1, usuario.getLogado());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void remover(Usuario usuario) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Usuario WHERE codigo_usuario=?;");

			// preenche os valores
			stmt.setInt(1, usuario.getCodigo_usuario());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
