package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import jdbc.dao.CategoriaDao;
import jdbc.dao.FornecedorDao;
import jdbc.dao.ProdutoDao;
import regraTextField.JtextFieldSomenteLetrasNumero;
import regraTextField.JtextFieldSomenteNumeros;
import bean.Produto;

public class JanCadProduto extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nome = null;
	private JTextField textPesquisar;
	private JTextField textCodigo;
	private JTextField textNome;
	private JComboBox<Object> comboCategoria;
	private JTextField textCodigoFornecedor;
	private JTextField textCnpj;
	private JTextField textNomeFornecedor;
	private JTextField textValorCusto;
	private JTextField textValorUnitario;
	private JTextField textPercentualLucro;
	private JTextField textUltimaCompra;
	private JTextField textQuantidadeAtual;
	private JTextField textEstoqueMinimo;
	private JCheckBox checkBoxAtivo;
	private JButton buttonPesquisar;
	private JButton buttonNovo;
	private JButton buttonAlterar;
	private JButton buttonExcluir;
	private JButton buttonAtualizar;
	private JButton buttonSalvar;
	private JButton buttonFechar;
	private JLabel labelCodigo;
	private JLabel labelNome;
	private JLabel labelCategoria;
	private JLabel labelFornecedor;
	private JLabel labelCnpj;
	private JLabel labelNomeFornecedor;
	private JLabel labelPrecoCusto;
	private JLabel labelPrecoVenda;
	private JLabel labelPercentualLucro;
	private JLabel labelUltimaCompra;
	private JLabel labelQuantidadeAtual;
	private JLabel labelEstoqueMinimo;
	private JMenuBar menuBar;
	private JMenu menuAtualizacao;
	private JMenuItem subSaida;
	private JMenuItem subEntrada;
	private JMenu menuCadastro;
	private JMenuItem subCategoria;
	private JMenuItem subFornecedor;
	private JMenu menuInativo;
	private JPanel panelPesquisarProduto;
	private JPanel panelDadosCadastrais;
	private JPanel panelFinanceiro;
	private JPanel panelFisico;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacoes;
	private JRadioButton radioCodigo;
	private JRadioButton radioNome;
	private JRadioButton radioValorUnitario;
	private JRadioButton radioCategoria;
	private JRadioButton radioFornecedor;

	public JanCadProduto(String nome) {
		super("Controle de Estoque - Produtos");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;

		ProdutoDao dao = new ProdutoDao();
		List<Produto> listaProduto = dao.listar();

		preencherJTable(listaProduto);
		Invisivel();
		desativarBotoes();

		// Categoria
		this.comboCategoria.removeAllItems();
		this.comboCategoria.addItem("");

		CategoriaDao daoCategoria = new CategoriaDao();
		ArrayList<String> categoria = daoCategoria.comboCategoria();
		Iterator<String> i = categoria.iterator();

		while (i.hasNext()) {
			this.comboCategoria.addItem(String.valueOf(i.next()));
		}
	}

	public void Invisivel() {
		this.textNome.setEditable(false);
		this.comboCategoria.setEnabled(false);
		this.textCodigoFornecedor.setEditable(false);
		this.textCnpj.setEditable(false);
		this.textNomeFornecedor.setEditable(false);
		this.checkBoxAtivo.setEnabled(false);

		// Financeiro
		this.textValorCusto.setEditable(false);
		this.textValorUnitario.setEditable(false);
		this.textPercentualLucro.setEditable(false);
		this.textUltimaCompra.setEditable(false);

		// Físico
		this.textQuantidadeAtual.setEditable(false);
		this.textEstoqueMinimo.setEditable(false);
	}

	public void Visivel() {
		this.comboCategoria.setEnabled(true);

		// Físico
		this.textEstoqueMinimo.setEditable(true);

		ProdutoDao dao = new ProdutoDao();
		Produto produto = new Produto();
		produto.setNome_produto(this.textNome.getText().toString());

		if ((dao.countAtivoInativo(true) >= 1 && dao.nomeProduto(produto) == false)
				|| (dao.countAtivoInativo(true) > 1 && dao.nomeProduto(produto) == true)
				|| (dao.countAtivoInativo(true) == 1 && dao
						.nomeProduto(produto) == false)
				|| (dao.nomeProduto(produto) == true && dao.ativoNome(produto) == false)) {
			this.checkBoxAtivo.setEnabled(true);
		}
	}

	public void desativarBotoes() {
		this.buttonSalvar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonAlterar.setEnabled(false);
	}

	public void esvasiarCampos() {
		this.textNome.setText("");
		this.comboCategoria.setSelectedItem("");
		this.textCodigoFornecedor.setText("");
		this.textCnpj.setText("");
		this.textNomeFornecedor.setText("");

		// Financeiro
		this.textValorCusto.setText("");
		this.textValorUnitario.setText("");
		this.textUltimaCompra.setText("");

		// Físico
		this.textQuantidadeAtual.setText("");
		this.textEstoqueMinimo.setText("");
	}

	public void esvasiarCamposCategoria() {
		this.textPercentualLucro.setText("");
	}

	public void funcoes() {
		desativarBotoes();
		esvasiarCampos();
		Invisivel();
		this.textCodigo.setText("");
	}

	// teste campos vaisios
	public Boolean testeCampos() {
		if ((this.textCodigo.getText().equals(""))
				|| (this.textNome.getText().equals(""))
				|| (this.comboCategoria.getSelectedItem().equals(""))
				|| (this.textEstoqueMinimo.getText().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Produto> listaProduto) {
		this.tableInformacoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(1)
				.setPreferredWidth(150);
		this.tableInformacoes.getColumnModel().getColumn(2)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(3)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(4)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(5)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(6)
				.setPreferredWidth(10);
		this.tableInformacoes.getColumnModel().getColumn(7)
				.setPreferredWidth(5);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacoes
				.getModel();
		tabela.setNumRows(0);

		FornecedorDao daoFornecedor = new FornecedorDao();

		for (Produto produto : listaProduto) {

			String codigo_fornecedor = daoFornecedor.codigoFornecedor(produto
					.getCodigo_produto());

			tabela.addRow(new Object[] { produto.getCodigo_produto(),
					produto.getNome_produto(), produto.getValor_unitario(),
					produto.getQuantidade_atual(), produto.getEstoque_minimo(),
					codigo_fornecedor,
					produto.getCategoria_produto().getNome_categoria(),
					produto.getAtivo() });
		}

		this.tableInformacoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadProduto.this.tableInformacoesTableChanged(e);
					}
				});
	}

	private void Componentes() {
		this.textPesquisar = new JTextField();
		this.textCodigo = new JtextFieldSomenteNumeros(7);
		this.textNome = new JtextFieldSomenteLetrasNumero(100);
		this.comboCategoria = new JComboBox<>();
		this.textCodigoFornecedor = new JTextField();
		this.textCnpj = new JTextField();
		this.textNomeFornecedor = new JTextField();
		this.textValorCusto = new JTextField();
		this.textValorUnitario = new JTextField();
		this.textPercentualLucro = new JTextField();
		this.textUltimaCompra = new JTextField();
		this.textQuantidadeAtual = new JTextField();
		this.textEstoqueMinimo = new JtextFieldSomenteNumeros(3);
		this.checkBoxAtivo = new JCheckBox();
		this.buttonPesquisar = new JButton();
		this.buttonNovo = new JButton();
		this.buttonAlterar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAtualizar = new JButton();
		this.buttonSalvar = new JButton();
		this.buttonFechar = new JButton();
		this.labelCodigo = new JLabel();
		this.labelNome = new JLabel();
		this.labelCategoria = new JLabel();
		this.labelFornecedor = new JLabel();
		this.labelCnpj = new JLabel();
		this.labelNomeFornecedor = new JLabel();
		this.labelPrecoCusto = new JLabel();
		this.labelPrecoVenda = new JLabel();
		this.labelPercentualLucro = new JLabel();
		this.labelUltimaCompra = new JLabel();
		this.labelQuantidadeAtual = new JLabel();
		this.labelEstoqueMinimo = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuAtualizacao = new JMenu();
		this.subSaida = new JMenuItem();
		this.subEntrada = new JMenuItem();
		this.menuCadastro = new JMenu();
		this.subCategoria = new JMenuItem();
		this.subFornecedor = new JMenuItem();
		this.menuInativo = new JMenu();
		this.panelPesquisarProduto = new JPanel();
		this.panelDadosCadastrais = new JPanel();
		this.panelFinanceiro = new JPanel();
		this.panelFisico = new JPanel();
		this.panelBotoes = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacoes = new JTable();
		tableInformacoes.setColumnSelectionAllowed(true);
		this.radioCodigo = new JRadioButton();
		this.radioNome = new JRadioButton();
		this.radioValorUnitario = new JRadioButton();
		this.radioCategoria = new JRadioButton();
		this.radioFornecedor = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR PRODUTOS ----------- //
		this.panelPesquisarProduto.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Produtos"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCodigo.setText("Código");
		this.radioCodigo.setFont(new Font("Tahoma", 1, 11));
		this.radioCodigo.setActionCommand("codigo_produto");
		this.radioCodigo.setSelected(true);

		this.radioCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.actionPerformed(evt);
			}
		});

		// Radio Nome.
		this.radioNome.setText("Nome");
		this.radioNome.setFont(new Font("Tahoma", 1, 11));
		this.radioNome.setActionCommand("nome_produto");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.actionPerformed(evt);
			}
		});

		// Radio Valor.
		this.radioValorUnitario.setText("Valor");
		this.radioValorUnitario.setFont(new Font("Tahoma", 1, 11));
		this.radioValorUnitario.setActionCommand("valor_unitario");

		this.radioValorUnitario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.actionPerformed(evt);
			}
		});

		// Radio Categoria.
		this.radioCategoria.setText("Categoria");
		this.radioCategoria.setFont(new Font("Tahoma", 1, 11));
		this.radioCategoria.setActionCommand("categoria_produto");

		this.radioCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.actionPerformed(evt);
			}
		});

		// Radio Fornecedor.
		this.radioFornecedor.setText("Fornecedor");
		this.radioFornecedor.setFont(new Font("Tahoma", 1, 11));
		this.radioFornecedor.setActionCommand("fornecedor_produto");

		this.radioFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCodigo);
		group.add(this.radioNome);
		group.add(this.radioValorUnitario);
		group.add(this.radioCategoria);
		group.add(this.radioFornecedor);

		GroupLayout layoutPesquisarProduto = new GroupLayout(
				this.panelPesquisarProduto);
		layoutPesquisarProduto
				.setHorizontalGroup(layoutPesquisarProduto
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarProduto
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarProduto
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarProduto
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				501,
																				Short.MAX_VALUE)
																		.addGap(18))
														.addGroup(
																layoutPesquisarProduto
																		.createSequentialGroup()
																		.addComponent(
																				radioCodigo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioNome)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioValorUnitario)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioCategoria)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioFornecedor)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addComponent(buttonPesquisar,
												GroupLayout.DEFAULT_SIZE, 140,
												Short.MAX_VALUE)
										.addContainerGap()));
		layoutPesquisarProduto
				.setVerticalGroup(layoutPesquisarProduto
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarProduto
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarProduto
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textPesquisar,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																buttonPesquisar,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addGap(24))
						.addGroup(
								layoutPesquisarProduto
										.createSequentialGroup()
										.addContainerGap(28, Short.MAX_VALUE)
										.addGroup(
												layoutPesquisarProduto
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioCodigo)
														.addComponent(radioNome)
														.addComponent(
																radioValorUnitario,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																radioCategoria)
														.addComponent(
																radioFornecedor))
										.addContainerGap()));
		this.panelPesquisarProduto.setLayout(layoutPesquisarProduto);

		// ---------- PANEL FINANCEIRO ----------- //
		this.panelFinanceiro.setBorder(BorderFactory
				.createTitledBorder("Financeiro"));

		// Preço de custo.
		this.labelPrecoCusto.setText("Preço de custo:");
		this.labelPrecoCusto.setFont(new Font("Tahoma", 1, 11));
		this.textValorCusto.setFont(new Font("Tahoma", 1, 11));

		// Preço de venda.
		this.labelPrecoVenda.setText("Preço de venda:");
		this.labelPrecoVenda.setFont(new Font("Tahoma", 1, 11));
		this.textValorUnitario.setFont(new Font("Tahoma", 1, 11));

		// Margem de lucro.
		this.labelPercentualLucro.setText("Percentual de lucro:");
		this.labelPercentualLucro.setFont(new Font("Tahoma", 1, 11));
		this.textPercentualLucro.setFont(new Font("Tahoma", 1, 11));

		// Última compra
		this.labelUltimaCompra.setText("Última compra:");
		this.labelUltimaCompra.setFont(new Font("Tahoma", 1, 11));
		this.textUltimaCompra.setFont(new Font("Tahoma", 1, 11));

		GroupLayout gl_panelFinanceiro = new GroupLayout(panelFinanceiro);
		gl_panelFinanceiro
				.setHorizontalGroup(gl_panelFinanceiro
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelFinanceiro
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelFinanceiro
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelFinanceiro
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelFinanceiro
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelPrecoCusto)
																						.addComponent(
																								labelPrecoVenda)
																						.addComponent(
																								textValorCusto,
																								GroupLayout.PREFERRED_SIZE,
																								135,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				27,
																				Short.MAX_VALUE)
																		.addGroup(
																				gl_panelFinanceiro
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelUltimaCompra,
																								GroupLayout.PREFERRED_SIZE,
																								123,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								labelPercentualLucro)
																						.addComponent(
																								textPercentualLucro,
																								GroupLayout.PREFERRED_SIZE,
																								135,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																gl_panelFinanceiro
																		.createSequentialGroup()
																		.addComponent(
																				textValorUnitario,
																				GroupLayout.PREFERRED_SIZE,
																				135,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				32,
																				Short.MAX_VALUE)
																		.addComponent(
																				textUltimaCompra,
																				GroupLayout.PREFERRED_SIZE,
																				135,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		gl_panelFinanceiro
				.setVerticalGroup(gl_panelFinanceiro
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelFinanceiro
										.createSequentialGroup()
										.addGap(5)
										.addGroup(
												gl_panelFinanceiro
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelPrecoCusto)
														.addComponent(
																labelPercentualLucro))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelFinanceiro
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textPercentualLucro,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textValorCusto,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panelFinanceiro
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelPrecoVenda)
														.addComponent(
																labelUltimaCompra))
										.addGap(4)
										.addGroup(
												gl_panelFinanceiro
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textValorUnitario,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textUltimaCompra,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		panelFinanceiro.setLayout(gl_panelFinanceiro);

		// ---------- PANEL DADOS CADASTRAIS ----------- //
		this.panelDadosCadastrais.setBorder(BorderFactory
				.createTitledBorder("Dados Cadastrais"));

		// Codigo.
		this.labelCodigo.setText("Código:");
		this.textCodigo.setFont(new Font("Tahoma", 1, 11));
		this.textCodigo.setEnabled(false);

		// Nome.
		this.labelNome.setText("Nome:");
		this.textNome.setFont(new Font("Tahoma", 1, 11));

		// Categoria.
		this.labelCategoria.setText("Categoria:");
		this.comboCategoria.setFont(new Font("Tahoma", 1, 11));
		this.comboCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.comboCategoriaActionPerformed(evt);
			}
		});

		// Fornecedor.
		this.labelFornecedor.setText("Fornecedor:");
		this.textCodigoFornecedor.setFont(new Font("Tahoma", 1, 11));

		// CNPJ.
		this.labelCnpj.setText("CNPJ:");
		this.textCnpj.setFont(new Font("Tahoma", 1, 11));

		// Nome Fornecedor.
		this.labelNomeFornecedor.setText("Nome:");
		this.textNomeFornecedor.setFont(new Font("Tahoma", 1, 11));

		// Ativo.
		this.checkBoxAtivo.setText("Ativo");
		this.checkBoxAtivo.setFont(new Font("Tahoma", 1, 11));

		GroupLayout layoutDadosCadastrais = new GroupLayout(
				this.panelDadosCadastrais);
		layoutDadosCadastrais
				.setHorizontalGroup(layoutDadosCadastrais
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutDadosCadastrais
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelCodigo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textCodigo,
																				GroupLayout.PREFERRED_SIZE,
																				61,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelNome)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textNome,
																				GroupLayout.PREFERRED_SIZE,
																				225,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(12)
																		.addComponent(
																				labelCategoria)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				comboCategoria,
																				GroupLayout.PREFERRED_SIZE,
																				141,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelFornecedor)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textCodigoFornecedor,
																				GroupLayout.PREFERRED_SIZE,
																				78,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelCnpj,
																				GroupLayout.PREFERRED_SIZE,
																				39,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textCnpj,
																				GroupLayout.PREFERRED_SIZE,
																				149,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelNomeFornecedor,
																				GroupLayout.PREFERRED_SIZE,
																				45,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textNomeFornecedor,
																				GroupLayout.DEFAULT_SIZE,
																				204,
																				Short.MAX_VALUE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				panelFinanceiro,
																				GroupLayout.PREFERRED_SIZE,
																				336,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				panelFisico,
																				GroupLayout.PREFERRED_SIZE,
																				169,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(44)
																		.addComponent(
																				checkBoxAtivo,
																				GroupLayout.PREFERRED_SIZE,
																				57,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		layoutDadosCadastrais
				.setVerticalGroup(layoutDadosCadastrais
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutDadosCadastrais
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelCodigo)
														.addComponent(
																textCodigo,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelNome)
														.addComponent(
																textNome,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelCategoria)
														.addComponent(
																comboCategoria,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelFornecedor)
														.addComponent(labelCnpj)
														.addComponent(
																textCnpj,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelNomeFornecedor)
														.addComponent(
																textNomeFornecedor,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textCodigoFornecedor,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								panelFinanceiro,
																								GroupLayout.DEFAULT_SIZE,
																								129,
																								Short.MAX_VALUE)
																						.addComponent(
																								panelFisico,
																								GroupLayout.DEFAULT_SIZE,
																								129,
																								Short.MAX_VALUE))
																		.addGap(10))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				checkBoxAtivo,
																				GroupLayout.PREFERRED_SIZE,
																				22,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(63)))));

		// ---------- PANEL FÍSICO ----------- //
		this.panelFisico.setBorder(BorderFactory.createTitledBorder("Físico"));

		// Quantidade Atual.
		this.labelQuantidadeAtual.setText("Quantidade atual:");
		this.labelQuantidadeAtual.setFont(new Font("Tahoma", 1, 11));
		this.textQuantidadeAtual.setFont(new Font("Tahoma", 1, 11));

		// Quantidade Mínima.
		this.labelEstoqueMinimo.setText("Estoque mínimo:");
		this.labelEstoqueMinimo.setFont(new Font("Tahoma", 1, 11));
		this.textEstoqueMinimo.setFont(new Font("Tahoma", 1, 11));

		GroupLayout gl_panelFisico = new GroupLayout(panelFisico);
		gl_panelFisico
				.setHorizontalGroup(gl_panelFisico
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelFisico
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelFisico
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																labelQuantidadeAtual)
														.addComponent(
																labelEstoqueMinimo)
														.addComponent(
																textQuantidadeAtual,
																GroupLayout.PREFERRED_SIZE,
																135,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textEstoqueMinimo,
																GroupLayout.PREFERRED_SIZE,
																135,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		gl_panelFisico.setVerticalGroup(gl_panelFisico.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panelFisico
						.createSequentialGroup()
						.addGap(5)
						.addComponent(labelQuantidadeAtual)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textQuantidadeAtual,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 12,
								Short.MAX_VALUE)
						.addComponent(labelEstoqueMinimo)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(textEstoqueMinimo,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(10)));
		panelFisico.setLayout(gl_panelFisico);
		this.panelDadosCadastrais.setLayout(layoutDadosCadastrais);

		// ---------- PANEL DOS BOTÕES ----------- //
		this.panelBotoes.setBorder(BorderFactory.createCompoundBorder());

		// Botões.
		this.buttonNovo.setBackground(new Color(255, 255, 255));
		this.buttonNovo.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/novo_registro.gif")));
		this.buttonNovo.setText("Novo");
		this.buttonNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.buttonFecharActionPerformed(evt);
			}
		});

		GroupLayout layoutBotoes = new GroupLayout(this.panelBotoes);
		layoutBotoes.setHorizontalGroup(layoutBotoes.createParallelGroup(
				Alignment.LEADING).addGroup(
				layoutBotoes
						.createSequentialGroup()
						.addComponent(buttonNovo, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonAlterar, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonExcluir, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonAtualizar,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonSalvar, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonFechar, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		layoutBotoes
				.setVerticalGroup(layoutBotoes
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutBotoes
										.createSequentialGroup()
										.addGap(12)
										.addGroup(
												layoutBotoes
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																buttonNovo)
														.addComponent(
																buttonFechar)
														.addComponent(
																buttonAlterar)
														.addComponent(
																buttonSalvar)
														.addComponent(
																buttonAtualizar)
														.addComponent(
																buttonExcluir,
																GroupLayout.PREFERRED_SIZE,
																33,
																GroupLayout.PREFERRED_SIZE))
										.addGap(0, 0, Short.MAX_VALUE)));
		this.panelBotoes.setLayout(layoutBotoes);

		// ---------- TABLE INFORMAÇÕES ----------- //
		this.tableInformacoes.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null }, },
				new String[] { "Código", "Nome", "Valor", "Qnt", "Mínimo",
						"Forn", "Categoria", "Ativo" }));

		this.tableInformacoes.setFocusable(false);
		this.scrollPaneInformacaoes.setViewportView(this.tableInformacoes);

		// ---------- BARRA DE FERRAMENTAS ----------- //
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		// Menu Atualização.
		this.menuAtualizacao.setMnemonic('A');
		this.menuAtualizacao.setText("Atualização");

		this.subSaida.setText("Saída");
		subSaida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				SHORTCUT_MASK));
		this.subSaida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.subSaidaActionPerformed(evt);
			}
		});
		this.menuAtualizacao.add(this.subSaida);

		this.subEntrada.setText("Entrada");
		subEntrada.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				SHORTCUT_MASK));
		this.subEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.subEntradaActionPerformed(evt);
			}
		});
		this.menuAtualizacao.add(this.subEntrada);

		this.menuBar.add(this.menuAtualizacao);

		// Menu Cadastro.
		this.menuCadastro.setMnemonic('C');
		this.menuCadastro.setText("Cadastro");

		this.subCategoria.setText("Categoria");
		subCategoria.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				SHORTCUT_MASK));
		this.subCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.subCategoriaActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCategoria);

		this.subFornecedor.setText("Fornecedor");
		subFornecedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				SHORTCUT_MASK));
		this.subFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.subFornecedorActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subFornecedor);

		this.menuBar.add(this.menuCadastro);

		// Inativo
		this.menuInativo.setMnemonic('I');
		this.menuInativo.setText("Inativo");
		this.menuInativo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanCadProduto.this.inativoActionPerformed(evt);
			}
		});
		this.menuInativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadProduto.this.inativoActionPerformed(evt);
			}
		});
		this.menuBar.add(this.menuInativo);

		setJMenuBar(this.menuBar);

		// ---------- LAYOULT ----------- //
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														panelDadosCadastrais,
														Alignment.LEADING,
														GroupLayout.PREFERRED_SIZE,
														693, Short.MAX_VALUE)
												.addComponent(
														scrollPaneInformacaoes,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														693, Short.MAX_VALUE)
												.addComponent(
														panelBotoes,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														panelPesquisarProduto,
														GroupLayout.DEFAULT_SIZE,
														693, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelPesquisarProduto,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelDadosCadastrais,
										GroupLayout.PREFERRED_SIZE, 239,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelBotoes,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPaneInformacaoes,
										GroupLayout.PREFERRED_SIZE, 117,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}

	// tableInformacoesTableChanged
	private void tableInformacoesTableChanged(TableModelEvent e) {
		funcoes();

		int column = e.getColumn();
		int row = e.getFirstRow();

		if (column < 0 || row < 0)
			return;

		Integer codigo_produto = Integer.parseInt(tableInformacoes.getValueAt(
				row, 0).toString());
		String nome = tableInformacoes.getValueAt(row, 1).toString();
		Double valor = Double.parseDouble(tableInformacoes.getValueAt(row, 2)
				.toString());
		Integer quantidade = Integer.parseInt(tableInformacoes.getValueAt(row,
				3).toString());
		Integer estoque_minimo = Integer.parseInt(tableInformacoes.getValueAt(
				row, 4).toString());
		String codigo_fornecedor = tableInformacoes.getValueAt(row, 5)
				.toString();
		String categoria = tableInformacoes.getValueAt(row, 6).toString();
		Boolean ativo = Boolean.parseBoolean(tableInformacoes
				.getValueAt(row, 7).toString());

		this.textCodigo.setText(codigo_produto.toString());
		this.textNome.setText(nome.toString());
		this.textValorUnitario.setText(valor.toString());
		this.textQuantidadeAtual.setText(quantidade.toString());
		this.textEstoqueMinimo.setText(estoque_minimo.toString());
		this.textCodigoFornecedor.setText(codigo_fornecedor.toString());
		this.comboCategoria.setSelectedItem(categoria.toString());
		this.checkBoxAtivo.setSelected(ativo);

		FornecedorDao daoFornecedor = new FornecedorDao();

		ProdutoDao daoProduto = new ProdutoDao();
		Produto produto = new Produto();

		if (!codigo_fornecedor.equals("")) {
			// Fornecedor
			List<String> listaFornecedor = daoFornecedor
					.listaFornecedor(Integer.parseInt(codigo_fornecedor));

			this.textCnpj.setText(listaFornecedor.get(0).toString());
			this.textNomeFornecedor.setText(listaFornecedor.get(1).toString());
		}

		// Preço Custo
		this.textValorCusto.setText(daoProduto.valorCusto(codigo_produto)
				.toString());

		// Data compra
		String dataFormatada = new SimpleDateFormat("dd-MM-yyyy")
				.format(daoProduto.dataCompraProduto(codigo_produto).getTime());
		this.textUltimaCompra.setText(dataFormatada);

		// Botões
		this.buttonAlterar.setEnabled(true);

		produto.setNome_produto(this.textNome.getText().toString());

		if ((daoProduto.countAtivoInativo(true) >= 1 && daoProduto
				.nomeProduto(produto) == false)
				|| (daoProduto.countAtivoInativo(true) > 1 && daoProduto
						.nomeProduto(produto) == true)
				|| (daoProduto.countAtivoInativo(true) == 1 && daoProduto
						.nomeProduto(produto) == false)
				|| (daoProduto.nomeProduto(produto) == true && daoProduto
						.ativoNome(produto) == false)) {
			if (daoProduto.countProdutoCompra(codigo_produto) == 0
					&& daoProduto.countProdutoVenda(codigo_produto) == 0) {
				this.buttonExcluir.setEnabled(true);
			}
		}
	}

	String acao = "codigo_produto";

	private void actionPerformed(ActionEvent evt) {
		acao = evt.getActionCommand();
	}

	// TextPesquisar.
	private void textPesquisarActionPerformed(ActionEvent evt) {
		buttonPesquisarActionPerformed(evt);
	}

	// ButtonPesquisar.
	private void buttonPesquisarActionPerformed(ActionEvent evt) {
		funcoes();
		if (!(this.textPesquisar.getText().equals(""))) {
			ProdutoDao dao = new ProdutoDao();

			List<Produto> listaProduto = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaProduto.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaProduto = dao.listar();
				preencherJTable(listaProduto);
			} else {
				preencherJTable(listaProduto);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Preencha o campo da pesquisa");
		}
	}

	// ButtonNovo.
	private void buttonNovoActionPerformed(ActionEvent evt) {

		// FornecedorDao
		FornecedorDao daoFornecedor = new FornecedorDao();

		// CategoriaDao
		CategoriaDao daoCategoria = new CategoriaDao();

		if (daoFornecedor.count() > 0) {
			if (daoCategoria.count() > 0) {
				desativarBotoes();
				this.buttonSalvar.setEnabled(true);
				this.textNome.setEditable(true);

				Visivel();
				ProdutoDao dao = new ProdutoDao();
				this.textCodigo.setText(Long.toString(dao
						.retornaAutoIncrement()));
				esvasiarCampos();
				this.checkBoxAtivo.setSelected(true);

				this.textNome.grabFocus();
			} else {
				JOptionPane.showMessageDialog(null,
						"Nenhuma categoria cadastrada!");
				dispose();
				JanCadCategoria cadC = new JanCadCategoria("produto");
				cadC.setVisible(true);
			}
		} else {
			JOptionPane
					.showMessageDialog(null, "Nenhum fornecedor cadastrado!");
			dispose();
			JanCadFornecedor cadC = new JanCadFornecedor("produto");
			cadC.setVisible(true);
		}

	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
		} else {
			Visivel();
			desativarBotoes();
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum produto selecionado!");
		} else {

			Produto produto = new Produto();
			produto.setCodigo_produto(Integer.parseInt((this.textCodigo
					.getText())));

			String mensagem = "Deseja excluir o produto de código "
					+ produto.getCodigo_produto() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				ProdutoDao dao = new ProdutoDao();
				dao.remover(produto);

				List<Produto> listaProduto = dao.listar();

				preencherJTable(listaProduto);

				esvasiarCampos();
				this.textCodigo.setText("");
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

	// ButtonAtualisar.
	private void buttonAtualizarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Todos os campos são obrigatórios!");
		} else {

			Produto produto = new Produto();
			produto.setCodigo_produto(Integer.parseInt((this.textCodigo
					.getText().toString())));
			produto.setEstoque_minimo(Integer.parseInt((this.textEstoqueMinimo
					.getText().toString())));
			produto.setAtivo(this.checkBoxAtivo.isSelected());

			// Categoria
			produto.getCategoria_produto().setNome_categoria(
					this.comboCategoria.getSelectedItem().toString());

			// Atualiza!!!
			ProdutoDao dao = new ProdutoDao();
			dao.alterar(produto);

			JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
			List<Produto> listaProduto = dao.listar();
			preencherJTable(listaProduto);

			esvasiarCampos();
			this.textCodigo.setText("");

			Invisivel();

			desativarBotoes();
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		Produto produto = new Produto();
		produto.setNome_produto(this.textNome.getText());

		ProdutoDao dao = new ProdutoDao();
		if (dao.nomeProduto(produto)) {
			JOptionPane.showMessageDialog(null,
					"Nome do produto já cadastrado!");
			this.textNome.setText("");
			this.textNome.grabFocus();
		} else {
			if (this.testeCampos()) {
				JOptionPane.showMessageDialog(null,
						"Todos os campos são obrigatórios!");
			} else {

				produto.setCodigo_produto(Integer.parseInt((this.textCodigo
						.getText().toString())));
				produto.setNome_produto(this.textNome.getText().toString());
				produto.setEstoque_minimo(Integer
						.parseInt((this.textEstoqueMinimo.getText().toString())));
				produto.setAtivo(this.checkBoxAtivo.isSelected());

				// Categoria
				produto.getCategoria_produto().setNome_categoria(
						this.comboCategoria.getSelectedItem().toString());

				// grave nessa conexão!!!
				dao.inserir(produto);

				JOptionPane.showMessageDialog(null,
						"O cadastro do produto:\n" + this.textCodigo.getText()
								+ " - " + produto.getNome_produto()
								+ "\nFoi realizado com sucesso!");
				List<Produto> listaProduto = dao.listar();
				preencherJTable(listaProduto);

				esvasiarCampos();
				this.textCodigo.setText("");

				Invisivel();

				desativarBotoes();
			}
		}
	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {

		switch (this.nome) {
		case "venda":
			dispose();
			JanVenda janPe = new JanVenda();
			janPe.setVisible(true);
			break;

		case "compra":
			dispose();
			JanCompra janC = new JanCompra();
			janC.setVisible(true);
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

	// JMenuItemSaida.
	private void subSaidaActionPerformed(ActionEvent evt) {
		dispose();
		JanVenda janP = new JanVenda();
		janP.setVisible(true);
	}

	// JMenuItemEntrada.
	private void subEntradaActionPerformed(ActionEvent evt) {
		dispose();
		JanCompra janC = new JanCompra();
		janC.setVisible(true);
	}

	// JMenuItemCategoria.
	private void subCategoriaActionPerformed(ActionEvent evt) {
		dispose();
		JanCadCategoria cadC = new JanCadCategoria("produto");
		cadC.setVisible(true);
	}

	// JMenuItemFornecedor.
	private void subFornecedorActionPerformed(ActionEvent evt) {
		dispose();
		JanCadFornecedor cadF = new JanCadFornecedor("produto");
		cadF.setVisible(true);
	}

	// Inativo.
	private void inativoActionPerformed(MouseEvent evt) {
		funcoes();
		ProdutoDao dao = new ProdutoDao();
		if (dao.countAtivoInativo(false) > 0) {
			List<Produto> listaProduto = dao.consultaInativo();

			preencherJTable(listaProduto);
		} else {
			JOptionPane.showMessageDialog(null,
					"Nehum produto inativo cadastrado!");
		}
	}

	// Inativo.
	private void inativoActionPerformed(ActionEvent evt) {
		inativoActionPerformed(evt);
	}

	// comboCategoria
	private void comboCategoriaActionPerformed(ActionEvent evt) {
		if (!this.comboCategoria.getSelectedItem().equals("")) {
			try {
				CategoriaDao dao = new CategoriaDao();
				List<Double> listaCategoria = dao
						.listaCategoria(this.comboCategoria.getSelectedItem()
								.toString());
				this.textPercentualLucro.setText(listaCategoria.get(0)
						.toString());
			} catch (java.lang.IndexOutOfBoundsException indexOutOfBoundsException) {
				JOptionPane.showMessageDialog(null, "A categoria "
						+ this.comboCategoria.getSelectedItem()
						+ " não foi encontrada");
			}

		} else {
			esvasiarCamposCategoria();
		}

	}
}
