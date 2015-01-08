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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import regraTextField.JtextFieldSomenteLetras;
import regraTextField.JtextFieldSomenteNumeros;
import jdbc.dao.CategoriaDao;
import jdbc.dao.FornecedorDao;
import bean.Categoria;

public class JanCadCategoria extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nome = null;
	private JTextField textPesquisar;
	private JTextField textCodigo;
	private JTextField textNome;
	private JTextField textPercentualLucro;
	private JTextField textDescricao;
	private JButton buttonPesquisar;
	private JButton buttonNovo;
	private JButton buttonAlterar;
	private JButton buttonExcluir;
	private JButton buttonAtualizar;
	private JButton buttonSalvar;
	private JButton buttonFechar;
	private JLabel labelCodigo;
	private JLabel labelNome;
	private JLabel labelPercentualLucro;
	private JLabel labelDescricao;
	private JMenuBar menuBar;
	private JMenu menuArquivo;
	private JMenuItem subNovo;
	private JMenuItem subFechar;
	private JMenu menuProduto;
	private JPanel panelPesquisarCategoria;
	private JPanel panelDadosCadastrais;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacaoes;
	private JRadioButton radioCodigo;
	private JRadioButton radioNome;
	private JRadioButton radioDescricao;
	private JRadioButton radioPercentualLucro;

	public JanCadCategoria(String nome) {
		super("Cadastro - Categoria");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;

		CategoriaDao dao = new CategoriaDao();
		List<Categoria> listaCategoria = dao.listar();

		preencherJTable(listaCategoria);
		Invisivel();
		desativarBotoes();
	}

	public void Invisivel() {
		this.textNome.setEditable(false);
		this.textPercentualLucro.setEditable(false);
		this.textDescricao.setEditable(false);
	}

	public void Visivel() {
		this.textPercentualLucro.setEditable(true);
		this.textDescricao.setEditable(true);
	}

	public void desativarBotoes() {
		this.buttonSalvar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonAlterar.setEnabled(false);
	}

	public void esvasiarCampos() {
		this.textNome.setText("");
		this.textPercentualLucro.setText("");
		this.textDescricao.setText("");
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
				|| (this.textPercentualLucro.getText().equals(""))
				|| (this.textDescricao.getText().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Categoria> listaCategoria) {
		this.tableInformacaoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(1)
				.setPreferredWidth(100);
		this.tableInformacaoes.getColumnModel().getColumn(2)
				.setPreferredWidth(20);
		this.tableInformacaoes.getColumnModel().getColumn(3)
				.setPreferredWidth(200);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacaoes
				.getModel();
		tabela.setNumRows(0);

		for (Categoria categoria : listaCategoria) {
			tabela.addRow(new Object[] { categoria.getCodigo_categoria(),
					categoria.getNome_categoria(),
					categoria.getPercentual_lucro(),
					categoria.getDescricao_categoria() });
		}

		this.tableInformacaoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadCategoria.this.tableInformacaoesTableChanged(e);
					}
				});
	}

	private void Componentes() {
		this.textPesquisar = new JTextField();
		this.textCodigo = new JtextFieldSomenteNumeros(7);
		this.textNome = new JtextFieldSomenteLetras(50);
		this.textPercentualLucro = new JtextFieldSomenteNumeros(3);
		this.textDescricao = new JtextFieldSomenteLetras(255);
		this.buttonPesquisar = new JButton();
		this.buttonNovo = new JButton();
		this.buttonAlterar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAtualizar = new JButton();
		this.buttonSalvar = new JButton();
		this.buttonFechar = new JButton();
		this.labelCodigo = new JLabel();
		this.labelNome = new JLabel();
		this.labelPercentualLucro = new JLabel();
		this.labelDescricao = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuArquivo = new JMenu();
		this.subNovo = new JMenuItem();
		this.subFechar = new JMenuItem();
		this.menuProduto = new JMenu();
		this.panelPesquisarCategoria = new JPanel();
		this.panelDadosCadastrais = new JPanel();
		this.panelBotoes = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacaoes = new JTable();
		tableInformacaoes.setColumnSelectionAllowed(true);
		this.radioCodigo = new JRadioButton();
		this.radioNome = new JRadioButton();
		this.radioDescricao = new JRadioButton();
		this.radioPercentualLucro = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR CARGOS ----------- //
		this.panelPesquisarCategoria.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Categorias"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCodigo.setText("Código");
		this.radioCodigo.setFont(new Font("Tahoma", 1, 11));
		this.radioCodigo.setActionCommand("codigo_categoria");
		this.radioCodigo.setSelected(true);

		this.radioCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.actionPerformed(evt);
			}
		});

		// Radio Nome.
		this.radioNome.setText("Nome");
		this.radioNome.setFont(new Font("Tahoma", 1, 11));
		this.radioNome.setActionCommand("nome_categoria");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.actionPerformed(evt);
			}
		});

		// Percentual Lucro.
		this.radioPercentualLucro.setText("Percentual Lucro");
		this.radioPercentualLucro.setFont(new Font("Tahoma", 1, 11));
		this.radioPercentualLucro.setActionCommand("percentual_lucro");

		this.radioPercentualLucro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.actionPerformed(evt);
			}
		});

		// Radio Descrição.
		this.radioDescricao.setText("Descricão");
		this.radioDescricao.setFont(new Font("Tahoma", 1, 11));
		this.radioDescricao.setActionCommand("descricao_categoria");

		this.radioDescricao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCodigo);
		group.add(this.radioNome);
		group.add(this.radioDescricao);
		group.add(this.radioPercentualLucro);

		GroupLayout layoutPesquisarCategoria = new GroupLayout(
				this.panelPesquisarCategoria);
		layoutPesquisarCategoria
				.setHorizontalGroup(layoutPesquisarCategoria
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarCategoria
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarCategoria
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarCategoria
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				437,
																				Short.MAX_VALUE)
																		.addGap(18))
														.addGroup(
																layoutPesquisarCategoria
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
																				radioDescricao)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioPercentualLucro)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addComponent(buttonPesquisar,
												GroupLayout.DEFAULT_SIZE, 204,
												Short.MAX_VALUE)
										.addContainerGap()));
		layoutPesquisarCategoria
				.setVerticalGroup(layoutPesquisarCategoria
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarCategoria
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarCategoria
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
								layoutPesquisarCategoria
										.createSequentialGroup()
										.addContainerGap(28, Short.MAX_VALUE)
										.addGroup(
												layoutPesquisarCategoria
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioCodigo)
														.addComponent(radioNome)
														.addComponent(
																radioDescricao)
														.addComponent(
																radioPercentualLucro))
										.addContainerGap()));
		this.panelPesquisarCategoria.setLayout(layoutPesquisarCategoria);

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

		// Percentual Categoria.
		this.labelPercentualLucro.setText("Percentual Lucro:");
		this.textPercentualLucro.setFont(new Font("Tahoma", 1, 11));

		// Descrição.
		this.labelDescricao.setText("Descrição:");
		this.textDescricao.setFont(new Font("Tahoma", 1, 11));

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
																				66,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelNome)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textNome,
																				GroupLayout.DEFAULT_SIZE,
																				248,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelPercentualLucro)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textPercentualLucro,
																				GroupLayout.PREFERRED_SIZE,
																				61,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelDescricao)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textDescricao,
																				GroupLayout.DEFAULT_SIZE,
																				573,
																				Short.MAX_VALUE)))
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
																textNome,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textPercentualLucro,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelPercentualLucro)
														.addComponent(
																textCodigo,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelNome))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelDescricao)
														.addComponent(
																textDescricao,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))));
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
				JanCadCategoria.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.buttonFecharActionPerformed(evt);
			}
		});

		GroupLayout layoutBotoes = new GroupLayout(this.panelBotoes);
		layoutBotoes.setHorizontalGroup(layoutBotoes.createParallelGroup(
				Alignment.LEADING).addGroup(
				layoutBotoes
						.createSequentialGroup()
						.addComponent(buttonNovo, GroupLayout.DEFAULT_SIZE, 85,
								Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonAlterar, GroupLayout.DEFAULT_SIZE,
								93, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonExcluir, GroupLayout.DEFAULT_SIZE,
								93, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonAtualizar,
								GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonSalvar, GroupLayout.DEFAULT_SIZE,
								91, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonFechar, GroupLayout.DEFAULT_SIZE,
								99, Short.MAX_VALUE)));
		layoutBotoes
				.setVerticalGroup(layoutBotoes
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutBotoes
										.createSequentialGroup()
										.addContainerGap()
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
		this.tableInformacaoes.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, },
				new String[] { "Código", "Nome", "Percentual Lucro",
						"Descrição" }));

		this.tableInformacaoes.setFocusable(false);
		this.scrollPaneInformacaoes.setViewportView(this.tableInformacaoes);

		// ---------- BARRA DE FERRAMENTAS ----------- //
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		// Arquivo
		this.menuArquivo.setMnemonic('A');
		this.menuArquivo.setText("Arquivo");

		this.subNovo.setText("Novo");
		subNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				SHORTCUT_MASK));
		this.subNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.subNovoActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subNovo);

		this.subFechar.setText("Fechar");
		subFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				SHORTCUT_MASK));
		this.subFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.subFecharActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subFechar);

		this.menuBar.add(this.menuArquivo);

		// Produto
		this.menuProduto.setMnemonic('P');
		this.menuProduto.setText("Produto");
		this.menuProduto.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanCadCategoria.this.produtoActionPerformed(evt);
			}
		});
		this.menuProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCategoria.this.produtoActionPerformed(evt);
			}
		});
		this.menuBar.add(this.menuProduto);

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
												Alignment.LEADING)
												.addComponent(
														scrollPaneInformacaoes,
														GroupLayout.DEFAULT_SIZE,
														693, Short.MAX_VALUE)
												.addComponent(
														panelBotoes,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														panelDadosCadastrais,
														GroupLayout.DEFAULT_SIZE,
														693, Short.MAX_VALUE)
												.addComponent(
														panelPesquisarCategoria,
														Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE,
														693, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelPesquisarCategoria,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelDadosCadastrais,
										GroupLayout.PREFERRED_SIZE, 106,
										Short.MAX_VALUE)
								.addGap(2)
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

	// tableInformacaoesTableChanged
	private void tableInformacaoesTableChanged(TableModelEvent e) {
		funcoes();

		int column = e.getColumn();
		int row = e.getFirstRow();

		if (column < 0 || row < 0)
			return;

		Integer codigo_categoria = Integer.parseInt(tableInformacaoes
				.getValueAt(row, 0).toString());
		String nome = tableInformacaoes.getValueAt(row, 1).toString();
		Double salario = Double.parseDouble(tableInformacaoes
				.getValueAt(row, 2).toString());
		Integer qntHorasSemana = Integer.parseInt(tableInformacaoes.getValueAt(
				row, 3).toString());

		this.textCodigo.setText(codigo_categoria.toString());
		this.textNome.setText(nome.toString());
		this.textPercentualLucro.setText(salario.toString());
		this.textDescricao.setText(qntHorasSemana.toString());
		this.buttonAlterar.setEnabled(true);

		CategoriaDao daoCategoria = new CategoriaDao();

		if (daoCategoria.countCategoriaProdutoCompra(codigo_categoria) == 0
				&& daoCategoria.countCategoriaProdutoVenda(codigo_categoria) == 0) {
			this.buttonExcluir.setEnabled(true);
		}
	}

	String acao = "codigo_categoria";

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
			CategoriaDao dao = new CategoriaDao();

			List<Categoria> listaCategoria = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaCategoria.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaCategoria = dao.listar();
				preencherJTable(listaCategoria);
			} else {
				preencherJTable(listaCategoria);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Preencha o campo da pesquisa");
		}
	}

	// ButtonNovo.
	private void buttonNovoActionPerformed(ActionEvent evt) {
		desativarBotoes();
		this.buttonSalvar.setEnabled(true);
		this.textNome.setEditable(true);

		Visivel();
		CategoriaDao dao = new CategoriaDao();
		this.textCodigo.setText(Long.toString(dao.retornaAutoIncrement()));
		esvasiarCampos();
		this.textNome.grabFocus();
	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Nenhuma categoria selecionado!");
		} else {
			Visivel();
			desativarBotoes();
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Nenhuma categoria selecionado!");
		} else {
			Categoria categoria = new Categoria();
			categoria.setCodigo_categoria(Integer.parseInt((this.textCodigo
					.getText())));

			String mensagem = "Deseja excluir a categoria de código "
					+ categoria.getCodigo_categoria() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				CategoriaDao dao = new CategoriaDao();
				dao.remover(categoria);

				List<Categoria> listaCategoria = dao.listar();

				preencherJTable(listaCategoria);

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

			Categoria categoria = new Categoria();
			categoria.setCodigo_categoria(Integer.parseInt((this.textCodigo
					.getText())));
			categoria
					.setPercentual_lucro(Double
							.parseDouble(this.textPercentualLucro.getText()
									.toString()));
			categoria.setDescricao_categoria(this.textDescricao.getText());

			// Atualiza!!!
			CategoriaDao dao = new CategoriaDao();
			dao.alterar(categoria);

			JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
			List<Categoria> listaCategoria = dao.listar();
			preencherJTable(listaCategoria);

			esvasiarCampos();
			this.textCodigo.setText("");

			Invisivel();

			desativarBotoes();
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		Categoria categoria = new Categoria();
		categoria.setNome_categoria(this.textNome.getText());

		CategoriaDao dao = new CategoriaDao();
		if (dao.nomeCategoria(categoria)) {
			JOptionPane.showMessageDialog(null,
					"Nome da categoria já cadastrado!");
			this.textNome.setText("");
		} else {
			if (this.testeCampos()) {
				JOptionPane.showMessageDialog(null,
						"Todos os campos são obrigatórios!");
			} else {

				categoria.setCodigo_categoria(Integer.parseInt((this.textCodigo
						.getText())));
				categoria.setNome_categoria(this.textNome.getText());
				categoria.setPercentual_lucro(Double
						.parseDouble(this.textPercentualLucro.getText()
								.toString()));
				categoria.setDescricao_categoria(this.textDescricao.getText());

				// grave nessa conexão!!!
				dao.inserir(categoria);

				JOptionPane.showMessageDialog(
						null,
						"O cadastro da categoria:\n"
								+ this.textCodigo.getText() + " - "
								+ categoria.getNome_categoria()
								+ "\nFoi realizado com sucesso!");
				List<Categoria> listaCategoria = dao.listar();
				preencherJTable(listaCategoria);

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
		case "produto":
			dispose();
			JanCadProduto cadP = new JanCadProduto("principal");
			cadP.setVisible(true);
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

	// JMenuItemNovo.
	private void subNovoActionPerformed(ActionEvent evt) {
		buttonNovoActionPerformed(evt);
	}

	// JMenuItemFechar.
	private void subFecharActionPerformed(ActionEvent evt) {
		buttonFecharActionPerformed(evt);
	}

	// Produto.
	private void produtoActionPerformed(MouseEvent evt) {
		// FornecedorDao
		FornecedorDao daoFornecedor = new FornecedorDao();

		// CategoriaDao
		CategoriaDao daoCategoria = new CategoriaDao();

		if (daoCategoria.count() > 0) {
			if (daoFornecedor.count() > 0) {
				dispose();
				JanCadProduto cadP = new JanCadProduto("principal");
				cadP.setVisible(true);
			} else {
				dispose();
				JanCadFornecedor cadF = new JanCadFornecedor("produto");
				cadF.setVisible(true);

			}
		} else {
			JOptionPane.showMessageDialog(null, "Cadastre uma categoria");
		}
	}

	// Produto.
	private void produtoActionPerformed(ActionEvent evt) {
		produtoActionPerformed(evt);
	}

}
