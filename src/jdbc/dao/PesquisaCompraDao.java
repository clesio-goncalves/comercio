package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdbc.Conexao;
import bean.Compra;
import bean.PesquisaCompra;

public class PesquisaCompraDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public PesquisaCompraDao() {
		this.conexao = new Conexao().getConnection();
	}

	public void inserir(PesquisaCompra pesquisaCompra) {

		String sql = "insert into Pesquisa select distinct(c.codigo_compra) as codigo, c.data_compra as data from Compra as c inner join Item_compra as ic on ic.codigo_compra = c.codigo_compra where";

		Boolean anterior = false;

		if (pesquisaCompra.getData_inicial() != null
				&& pesquisaCompra.getData_final() != null) {
			String data_inicial = new SimpleDateFormat("yyyy-MM-dd")
					.format(pesquisaCompra.getData_inicial().getTime());

			String data_final = new SimpleDateFormat("yyyy-MM-dd")
					.format(pesquisaCompra.getData_final().getTime());
			sql += " c.data_compra between '" + data_inicial + "' and '"
					+ data_final + "'";
			anterior = true;
		}

		if (pesquisaCompra.getCodigo_compra() != 0) {
			if (anterior) {
				sql += " and c.codigo_compra="
						+ pesquisaCompra.getCodigo_compra() + "";
			} else {
				sql += " c.codigo_compra=" + pesquisaCompra.getCodigo_compra()
						+ "";
			}
			anterior = true;
		}

		if (pesquisaCompra.getCodigo_fornecedor() != 0) {
			if (anterior) {
				sql += " and c.fornecedor_compra="
						+ pesquisaCompra.getCodigo_fornecedor() + "";
			} else {
				sql += " c.fornecedor_compra=" + pesquisaCompra.getCodigo_fornecedor()
						+ "";
			}
			anterior = true;
		}

		if (pesquisaCompra.getCodigo_produto() != 0) {
			if (anterior) {
				sql += " and ic.codigo_produto="
						+ pesquisaCompra.getCodigo_produto() + "";
			} else {
				sql += " ic.codigo_produto="
						+ pesquisaCompra.getCodigo_produto() + "";
			}
		}

		sql += " order by c.data_compra desc, c.codigo_compra desc;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// executa
			remover();
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Compra> listar() {
		try {
			List<Compra> comprasPesquisados = new ArrayList<Compra>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from Pesquisa;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Compra compras = new Compra();
				compras.setCodigo_compra(rs.getInt("codigo"));

				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("data"));
				compras.setData_compra(data);

				// adicionando o objeto à lista
				comprasPesquisados.add(compras);
			}

			rs.close();
			stmt.close();
			return comprasPesquisados;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("delete from Pesquisa;");

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
