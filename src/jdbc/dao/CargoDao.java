package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Cargo;

public class CargoDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public CargoDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Cargo';");
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

	public void inserir(Cargo cargo) {
		String sql = "INSERT INTO Cargo (nome_cargo, salario, qnt_horas_semana) VALUES (?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, cargo.getNome_cargo());
			stmt.setDouble(2, cargo.getSalario());
			stmt.setInt(3, cargo.getQnt_horas_semana());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public ArrayList<String> comboCargo() {
		try {
			ArrayList<String> listaCargo = new ArrayList<String>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_cargo FROM Cargo ORDER BY nome_cargo;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				listaCargo.add(rs.getString("nome_cargo"));
			}
			rs.close();
			stmt.close();
			return listaCargo;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Cargo> pesquisar(String pesquisa, String acao) {
		try {
			List<Cargo> cargos = new ArrayList<Cargo>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_cargo":
				stmt = this.conexao
						.prepareStatement("select * from Cargo where codigo_cargo="
								+ pesquisa + " order by codigo_cargo;");
				break;

			case "nome_cargo":
				stmt = this.conexao
						.prepareStatement("select * from Cargo where nome_cargo like '%"
								+ pesquisa + "%' order by nome_cargo;");
				break;

			case "salario":
				stmt = this.conexao
						.prepareStatement("select * from Cargo where salario="
								+ pesquisa + " order by salario;");
				break;

			case "qnt_horas_semana":
				stmt = this.conexao
						.prepareStatement("select * from Cargo where qnt_horas_semana="
								+ pesquisa + " order by qnt_horas_semana;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cargo cargo = new Cargo();
				cargo.setCodigo_cargo(rs.getInt("codigo_cargo"));
				cargo.setNome_cargo(rs.getString("nome_cargo"));
				cargo.setSalario(rs.getDouble("salario"));
				cargo.setQnt_horas_semana(rs.getInt("qnt_horas_semana"));

				// adicionando o objeto à lista
				cargos.add(cargo);
			}

			rs.close();
			stmt.close();
			return cargos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean nomeCargo(Cargo cargo) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_cargo FROM Cargo;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto cargo
				String nomeCargo = rs.getString("nome_cargo");

				if (cargo.getNome_cargo().equals(nomeCargo)) {
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

	public String consultarNomeCargo() {
		try {
			UsuarioDao dao = new UsuarioDao();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select c.nome_cargo from Cargo as c inner join Funcionario as f on f.cargo_funcionario = c.codigo_cargo inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario where u.nome_usuario like '"
							+ dao.consultarNomeLogado() + "';");
			ResultSet rs = stmt.executeQuery();

			String nome = null;

			while (rs.next()) {
				nome = rs.getString("nome_cargo");
			}
			rs.close();
			stmt.close();
			return nome;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public int count() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Cargo;");
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

	public int countCargoFuncionarioVenda(Integer codigo_cargo) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Cargo as c inner join Funcionario as f on f.cargo_funcionario = c.codigo_cargo inner join Pedido as p on p.funcionario_pedido = f.codigo_funcionario where c.codigo_cargo="
							+ codigo_cargo + ";");
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

	public int countCargoFuncionarioCompra(Integer codigo_cargo) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Cargo as c inner join Funcionario as f on f.cargo_funcionario = c.codigo_cargo inner join Compra as co on co.funcionario_compra = f.codigo_funcionario where c.codigo_cargo="
							+ codigo_cargo + ";");
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

	public List<Cargo> listar() {
		try {
			List<Cargo> cargos = new ArrayList<Cargo>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * from Cargo;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Cargo cargo = new Cargo();
				cargo.setCodigo_cargo(rs.getInt("codigo_cargo"));
				cargo.setNome_cargo(rs.getString("nome_cargo"));
				cargo.setSalario(rs.getDouble("salario"));
				cargo.setQnt_horas_semana(rs.getInt("qnt_horas_semana"));

				// adicionando o objeto à lista
				cargos.add(cargo);
			}

			rs.close();
			stmt.close();
			return cargos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o nome do cargo
	public void alterar(Cargo cargo) {
		String sql = "UPDATE Cargo SET salario=?, qnt_horas_semana=? WHERE codigo_cargo=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setDouble(1, cargo.getSalario());
			stmt.setInt(2, cargo.getQnt_horas_semana());
			stmt.setInt(3, cargo.getCodigo_cargo());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void remover(Cargo cargo) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Cargo WHERE codigo_cargo=?;");

			// preenche os valores
			stmt.setInt(1, cargo.getCodigo_cargo());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
