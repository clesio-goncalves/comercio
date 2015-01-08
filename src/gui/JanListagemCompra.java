package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import jdbc.dao.CompraDao;
import jdbc.dao.Item_compraDao;
import jdbc.dao.PesquisaCompraDao;
import bean.Compra;
import bean.Item_compra;

public class JanListagemCompra extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer quantidade_itens = 0;
	private String nome = null;
	private List<Compra> listaCompra;
	private JButton buttonExcluir;
	private JButton buttonPesquisar;
	private JButton buttonFechar;
	private JTextField textCodigo;
	private JTextField textFuncionario;
	private JTextField textUsuario;
	private JTextField textFornecedor;
	private JTextField textDesconto;
	private JTextField textQuantidade;
	private JLabel labelCodigo;
	private JLabel labelFuncionario;
	private JLabel labelUsuario;
	private JLabel labelFornecedor;
	private JLabel labelDesconto;
	private JLabel labelTotalCompra;
	private JLabel labelQuantidade;
	private JPanel panelCompra;
	private JPanel panelTotalCompra;
	private JScrollPane scrollPaneData;
	private JScrollPane scrollPaneInformacoes;
	private JTable tableData;
	private JTable tableInformacoes;

	public JanListagemCompra(String nome) {
		super("Listagem de compras");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;

		CompraDao daoCompra = new CompraDao();

		if (this.nome.equals("pesquisa")) {
			PesquisaCompraDao dao = new PesquisaCompraDao();
			this.listaCompra = dao.listar();

			if (this.listaCompra.size() == 0) {
				this.listaCompra = daoCompra.listar();
			}
		} else {
			this.listaCompra = daoCompra.listar();
		}

		// Item_compra
		Item_compraDao daoItemCompra = new Item_compraDao();
		List<Item_compra> listaItemCompra = daoItemCompra.listar(0);

		preencherJTableData(this.listaCompra);
		preencherJTableInformacoes(listaItemCompra);
		Invisivel();
		desativarBotoes();
	}

	public void Invisivel() {
		this.textCodigo.setEditable(false);
		this.textFuncionario.setEditable(false);
		this.textUsuario.setEditable(false);
		this.textFornecedor.setEditable(false);
		this.textDesconto.setEditable(false);
		this.textQuantidade.setEditable(false);
	}

	public void esvasiarCampos() {
		this.textCodigo.setText("");
		this.textFuncionario.setText("");
		this.textUsuario.setText("");
		this.textFornecedor.setText("");
		this.textDesconto.setText("");
		this.textQuantidade.setText("");

		this.labelTotalCompra.setText("");
	}

	public void desativarBotoes() {
		this.buttonExcluir.setEnabled(false);
	}

	// teste campos vaisios
	public Boolean testeCampos() {
		if (this.textCodigo.getText().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTableData(List<Compra> listaCompra) {

		this.tableData.getColumnModel().getColumn(0).setPreferredWidth(5);

		DefaultTableModel tabela = (DefaultTableModel) this.tableData
				.getModel();
		tabela.setNumRows(0);

		for (Compra compra : listaCompra) {

			String dataFormatada = new SimpleDateFormat("dd-MM-yyyy")
					.format(compra.getData_compra().getTime());

			tabela.addRow(new Object[] { dataFormatada });
		}

		this.tableData.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanListagemCompra.this.tableDataTableChanged(e);
					}
				});

	}

	public void preencherJTableInformacoes(List<Item_compra> listaItemCompra) {

		this.tableInformacoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(1)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(2)
				.setPreferredWidth(150);
		this.tableInformacoes.getColumnModel().getColumn(3)
				.setPreferredWidth(10);
		this.tableInformacoes.getColumnModel().getColumn(4)
				.setPreferredWidth(10);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacoes
				.getModel();
		tabela.setNumRows(0);

		DecimalFormat df = new DecimalFormat("0.00");

		this.quantidade_itens = 0;

		for (Item_compra item_compra : listaItemCompra) {

			tabela.addRow(new Object[] {
					item_compra.getQuatidade_item(),
					item_compra.getProduto().getCodigo_produto(),
					item_compra.getProduto().getNome_produto(),
					df.format(item_compra.getValor_unitario_compra()),
					df.format(item_compra.getQuatidade_item()
							* item_compra.getValor_unitario_compra()) });
			this.quantidade_itens++;
		}

	}

	private void Componentes() {
		this.buttonExcluir = new JButton();
		this.buttonPesquisar = new JButton();
		this.buttonFechar = new JButton();
		this.textCodigo = new JTextField();
		this.textFuncionario = new JTextField();
		this.textUsuario = new JTextField();
		this.textFornecedor = new JTextField();
		this.textDesconto = new JTextField();
		this.textQuantidade = new JTextField();
		this.labelCodigo = new JLabel();
		this.labelFuncionario = new JLabel();
		this.labelUsuario = new JLabel();
		this.labelFornecedor = new JLabel();
		this.labelDesconto = new JLabel();
		this.labelTotalCompra = new JLabel();
		this.labelQuantidade = new JLabel();
		this.panelCompra = new JPanel();
		this.panelTotalCompra = new JPanel();

		// Data
		this.scrollPaneData = new JScrollPane();
		scrollPaneData
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableData = new JTable();
		tableData.setColumnSelectionAllowed(true);

		// Informações
		this.scrollPaneInformacoes = new JScrollPane();
		scrollPaneInformacoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacoes = new JTable();
		tableInformacoes.setColumnSelectionAllowed(true);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL Produto ----------- //
		this.panelCompra.setBorder(BorderFactory.createTitledBorder(""));

		// Botão Excluir
		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanListagemCompra.this.buttonExcluirActionPerformed(evt);
			}
		});

		// Botão pesquisar
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanListagemCompra.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Botão Fechar
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/fechar.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanListagemCompra.this.buttonFecharActionPerformed(evt);
			}
		});

		// Código
		this.labelCodigo.setText("Código compra");
		this.labelCodigo.setFont(new Font("Tahoma", 1, 11));
		this.textCodigo.setFont(new Font("Tahoma", 1, 11));

		// Funcionário
		this.labelFuncionario.setText("Funcionário");
		this.labelFuncionario.setFont(new Font("Tahoma", 1, 11));
		this.textFuncionario.setFont(new Font("Tahoma", 1, 11));

		// Usuário
		this.labelUsuario.setText("Usuário");
		this.labelUsuario.setFont(new Font("Tahoma", 1, 11));
		this.textUsuario.setFont(new Font("Tahoma", 1, 11));

		// Fornecedor
		this.labelFornecedor.setText("Fornecedor");
		this.labelFornecedor.setFont(new Font("Tahoma", 1, 11));
		this.textFornecedor.setFont(new Font("Tahoma", 1, 11));

		// Desconto
		this.labelDesconto.setText("Desconto");
		this.labelDesconto.setFont(new Font("Tahoma", 1, 11));
		this.textDesconto.setFont(new Font("Tahoma", 1, 11));

		// Quantidade
		this.labelQuantidade.setText("Quantidade de itens:");
		this.labelQuantidade.setFont(new Font("Tahoma", 1, 11));
		this.textQuantidade.setFont(new Font("Tahoma", 1, 11));

		// ---------- PANEL TOTAL PEDIDO ----------- //
		this.panelTotalCompra.setBorder(BorderFactory
				.createTitledBorder("Total Compra R$"));
		this.labelTotalCompra.setFont(new Font("Tahoma", 1, 25));
		this.panelTotalCompra.add(labelTotalCompra);

		GroupLayout gl_panelTotalCompra = new GroupLayout(panelTotalCompra);
		gl_panelTotalCompra.setHorizontalGroup(gl_panelTotalCompra
				.createParallelGroup(Alignment.TRAILING).addGroup(
						gl_panelTotalCompra
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(labelTotalCompra,
										GroupLayout.DEFAULT_SIZE, 135,
										Short.MAX_VALUE).addContainerGap()));
		gl_panelTotalCompra.setVerticalGroup(gl_panelTotalCompra
				.createParallelGroup(Alignment.TRAILING).addGroup(
						gl_panelTotalCompra
								.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(labelTotalCompra)
								.addContainerGap()));
		panelTotalCompra.setLayout(gl_panelTotalCompra);

		GroupLayout gl_panelCompra = new GroupLayout(panelCompra);
		gl_panelCompra
				.setHorizontalGroup(gl_panelCompra
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panelCompra
										.createSequentialGroup()
										.addGap(18)
										.addComponent(scrollPaneData,
												GroupLayout.PREFERRED_SIZE, 95,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panelCompra
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_panelCompra
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelCompra
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelCompra
																										.createSequentialGroup()
																										.addGap(2)
																										.addComponent(
																												labelDesconto))
																						.addComponent(
																								textDesconto,
																								GroupLayout.PREFERRED_SIZE,
																								100,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				170,
																				Short.MAX_VALUE)
																		.addComponent(
																				panelTotalCompra,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panelCompra
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelCompra
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelCompra
																										.createSequentialGroup()
																										.addGroup(
																												gl_panelCompra
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																labelFornecedor,
																																GroupLayout.PREFERRED_SIZE,
																																76,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																textFuncionario,
																																GroupLayout.DEFAULT_SIZE,
																																311,
																																Short.MAX_VALUE))
																										.addGap(12))
																						.addGroup(
																								gl_panelCompra
																										.createSequentialGroup()
																										.addComponent(
																												labelFuncionario)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)))
																		.addGroup(
																				gl_panelCompra
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelUsuario,
																								GroupLayout.PREFERRED_SIZE,
																								76,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textUsuario,
																								GroupLayout.PREFERRED_SIZE,
																								116,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																gl_panelCompra
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelCompra
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								113,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								100,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				65,
																				Short.MAX_VALUE)
																		.addComponent(
																				buttonExcluir)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				buttonPesquisar,
																				GroupLayout.PREFERRED_SIZE,
																				142,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																textFornecedor,
																GroupLayout.PREFERRED_SIZE,
																439,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		gl_panelCompra
				.setVerticalGroup(gl_panelCompra
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panelCompra
										.createSequentialGroup()
										.addGroup(
												gl_panelCompra
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelCompra
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				scrollPaneData,
																				GroupLayout.DEFAULT_SIZE,
																				241,
																				Short.MAX_VALUE))
														.addGroup(
																gl_panelCompra
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelCompra
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelCompra
																										.createSequentialGroup()
																										.addContainerGap()
																										.addGroup(
																												gl_panelCompra
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																buttonPesquisar,
																																GroupLayout.PREFERRED_SIZE,
																																33,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																buttonExcluir,
																																GroupLayout.PREFERRED_SIZE,
																																33,
																																GroupLayout.PREFERRED_SIZE)))
																						.addGroup(
																								gl_panelCompra
																										.createSequentialGroup()
																										.addGap(20)
																										.addComponent(
																												labelCodigo,
																												GroupLayout.PREFERRED_SIZE,
																												14,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textCodigo,
																												GroupLayout.PREFERRED_SIZE,
																												18,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(14)
																										.addGroup(
																												gl_panelCompra
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																labelUsuario,
																																GroupLayout.PREFERRED_SIZE,
																																14,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																labelFuncionario,
																																GroupLayout.PREFERRED_SIZE,
																																14,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_panelCompra
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																textFuncionario,
																																GroupLayout.PREFERRED_SIZE,
																																18,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																textUsuario,
																																GroupLayout.PREFERRED_SIZE,
																																18,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												ComponentPlacement.UNRELATED)
																										.addComponent(
																												labelFornecedor,
																												GroupLayout.PREFERRED_SIZE,
																												14,
																												GroupLayout.PREFERRED_SIZE)))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				textFornecedor,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				labelDesconto,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textDesconto,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(43))
														.addGroup(
																gl_panelCompra
																		.createSequentialGroup()
																		.addGap(173)
																		.addComponent(
																				panelTotalCompra,
																				GroupLayout.DEFAULT_SIZE,
																				80,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		panelCompra.setLayout(gl_panelCompra);

		// ---------- TABLE DATA ----------- //
		this.tableData.setModel(new DefaultTableModel(new Object[][] { { null,
				null, null, null, null } }, new String[] { "Data" }));

		this.tableData.setFocusable(false);
		this.scrollPaneData.setViewportView(this.tableData);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																scrollPaneInformacoes,
																GroupLayout.DEFAULT_SIZE,
																582,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				labelQuantidade)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textQuantidade,
																				GroupLayout.PREFERRED_SIZE,
																				48,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				259,
																				Short.MAX_VALUE)
																		.addComponent(
																				buttonFechar,
																				GroupLayout.PREFERRED_SIZE,
																				128,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																panelCompra,
																GroupLayout.PREFERRED_SIZE,
																582,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(panelCompra,
												GroupLayout.PREFERRED_SIZE,
												262, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(scrollPaneInformacoes,
												GroupLayout.DEFAULT_SIZE, 132,
												Short.MAX_VALUE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				buttonFechar)
																		.addContainerGap())
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								textQuantidade,
																								GroupLayout.PREFERRED_SIZE,
																								18,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								labelQuantidade,
																								GroupLayout.PREFERRED_SIZE,
																								14,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(24)))));

		getContentPane().setLayout(groupLayout);

		// ---------- TABLE INFORMAÇÕES ----------- //
		this.tableInformacoes.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null } }, new String[] { "Qnte",
				"Código", "Produto", "Valor Unit.", "Total" }));

		this.tableInformacoes.setFocusable(false);
		this.scrollPaneInformacoes.setViewportView(this.tableInformacoes);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 531) / 2, (screenSize.height - 545) / 2,
				608, 527);

	}

	// Data
	private void tableDataTableChanged(TableModelEvent e) {

		int column = e.getColumn();
		int row = e.getFirstRow();

		if (column < 0 || row < 0)
			return;

		// Código
		Integer codigo_compra = this.listaCompra.get(row).getCodigo_compra();

		CompraDao daoCompra = new CompraDao();
		DecimalFormat df = new DecimalFormat("0.00");

		// Compra
		List<String> listaCompra = daoCompra.listagemCompra(codigo_compra);

		this.textCodigo.setText(listaCompra.get(0).toString());
		this.textDesconto.setText(df.format(Double.parseDouble(listaCompra.get(
				1).toString())));
		this.labelTotalCompra.setText(df.format(Double.parseDouble(listaCompra
				.get(2).toString())));
		this.textFuncionario.setText(listaCompra.get(3).toString());
		this.textUsuario.setText(listaCompra.get(4).toString());
		this.textFornecedor.setText(listaCompra.get(5).toString());

		// Item_compra
		Item_compraDao daoItemCompra = new Item_compraDao();
		List<Item_compra> listaItemCompra = daoItemCompra.listar(codigo_compra);

		preencherJTableInformacoes(listaItemCompra);

		// Quantidade itens
		this.textQuantidade.setText(this.quantidade_itens.toString());

		this.buttonExcluir.setEnabled(true);

	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {
		switch (this.nome) {
		case "compra":
			dispose();
			JanCompra janPe = new JanCompra();
			janPe.setVisible(true);
			break;

		case "pesquisa":
			dispose();
			JanPrincipal janPr = new JanPrincipal();
			janPr.setVisible(true);
			break;

		case "principal":
			dispose();
			JanPrincipal janP = new JanPrincipal();
			janP.setVisible(true);
			break;
		default:
			break;
		}
	}

	// ButtonPesquisar.
	private void buttonPesquisarActionPerformed(ActionEvent evt) {
		dispose();
		JanPesquisaCompra janC = new JanPesquisaCompra();
		janC.setVisible(true);
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhuma compra selecionada!");
		} else {
			Compra compra = new Compra();
			compra.setCodigo_compra(Integer.parseInt((this.textCodigo.getText())));

			String mensagem = "Deseja excluir a compra de código "
					+ compra.getCodigo_compra() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				CompraDao dao = new CompraDao();
				dao.remover(compra);

				// Item_compra
				Item_compraDao daoItemCompra = new Item_compraDao();
				List<Item_compra> listaItemCompra = daoItemCompra.listar(compra
						.getCodigo_compra());

				daoItemCompra.remover(listaItemCompra);

				this.listaCompra = dao.listar();
				preencherJTableData(this.listaCompra);

				// Item_compra
				listaItemCompra = daoItemCompra.listar(0);
				preencherJTableInformacoes(listaItemCompra);

				esvasiarCampos();
				Invisivel();
				desativarBotoes();
			}
		}
	}

	// Ok_cancel
	private int okcancel(String mensagem) {
		int resultado = JOptionPane.showConfirmDialog((Component) null,
				mensagem, "Exclusão", JOptionPane.OK_CANCEL_OPTION);
		return resultado;
	}
}
