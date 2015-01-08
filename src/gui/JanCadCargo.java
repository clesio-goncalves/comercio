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
import jdbc.dao.CargoDao;
import jdbc.dao.ComercioDao;
import bean.Cargo;

public class JanCadCargo extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private JTextField textPesquisar;
	private JTextField textCodigo;
	private JTextField textNome;
	private JTextField textSalario;
	private JTextField textQntHorasSemana;
	private JButton buttonPesquisar;
	private JButton buttonNovo;
	private JButton buttonAlterar;
	private JButton buttonExcluir;
	private JButton buttonAtualizar;
	private JButton buttonSalvar;
	private JButton buttonFechar;
	private JLabel labelCodigo;
	private JLabel labelNome;
	private JLabel labelSalario;
	private JLabel labelQntHorasSemana;
	private JMenuBar menuBar;
	private JMenu menuArquivo;
	private JMenuItem subNovo;
	private JMenuItem subFechar;
	private JMenu menuFuncionario;
	private JPanel panelPesquisarCargo;
	private JPanel panelDadosCadastrais;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacaoes;
	private JRadioButton radioCodigo;
	private JRadioButton radioNome;
	private JRadioButton radioSalario;
	private JRadioButton radioQntHorasSemana;

	public JanCadCargo(String nome) {
		super("Cadastro - Cargo");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;

		CargoDao dao = new CargoDao();
		List<Cargo> listaCargo = dao.listar();

		preencherJTable(listaCargo);
		Invisivel();
		desativarBotoes();
	}

	public void Invisivel() {
		this.textNome.setEditable(false);
		this.textSalario.setEditable(false);
		this.textQntHorasSemana.setEditable(false);
	}

	public void Visivel() {
		this.textSalario.setEditable(true);
		this.textQntHorasSemana.setEditable(true);
	}

	public void desativarBotoes() {
		this.buttonSalvar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonAlterar.setEnabled(false);
	}

	public void esvasiarCampos() {
		this.textNome.setText("");
		this.textSalario.setText("");
		this.textQntHorasSemana.setText("");
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
				|| (this.textSalario.getText().equals(""))
				|| (this.textQntHorasSemana.getText().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Cargo> listaCargo) {
		this.tableInformacaoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(1)
				.setPreferredWidth(200);
		this.tableInformacaoes.getColumnModel().getColumn(2)
				.setPreferredWidth(10);
		this.tableInformacaoes.getColumnModel().getColumn(3)
				.setPreferredWidth(10);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacaoes
				.getModel();
		tabela.setNumRows(0);

		for (Cargo cargo : listaCargo) {
			tabela.addRow(new Object[] { cargo.getCodigo_cargo(),
					cargo.getNome_cargo(), cargo.getSalario(),
					cargo.getQnt_horas_semana() });
		}

		this.tableInformacaoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadCargo.this.tableInformacaoesTableChanged(e);
					}
				});
	}

	private void Componentes() {
		this.textPesquisar = new JTextField();
		this.textCodigo = new JtextFieldSomenteNumeros(7);
		this.textNome = new JtextFieldSomenteLetras(50);
		this.textSalario = new JtextFieldSomenteNumeros(4);
		this.textQntHorasSemana = new JtextFieldSomenteNumeros(2);
		this.buttonPesquisar = new JButton();
		this.buttonNovo = new JButton();
		this.buttonAlterar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAtualizar = new JButton();
		this.buttonSalvar = new JButton();
		this.buttonFechar = new JButton();
		this.labelCodigo = new JLabel();
		this.labelNome = new JLabel();
		this.labelSalario = new JLabel();
		this.labelQntHorasSemana = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuArquivo = new JMenu();
		this.subNovo = new JMenuItem();
		this.subFechar = new JMenuItem();
		this.menuFuncionario = new JMenu();
		this.panelPesquisarCargo = new JPanel();
		this.panelDadosCadastrais = new JPanel();
		this.panelBotoes = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacaoes = new JTable();
		tableInformacaoes.setColumnSelectionAllowed(true);
		this.radioCodigo = new JRadioButton();
		this.radioNome = new JRadioButton();
		this.radioSalario = new JRadioButton();
		this.radioQntHorasSemana = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR CARGOS ----------- //
		this.panelPesquisarCargo.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Cargos"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCodigo.setText("Código");
		this.radioCodigo.setFont(new Font("Tahoma", 1, 11));
		this.radioCodigo.setActionCommand("codigo_cargo");
		this.radioCodigo.setSelected(true);

		this.radioCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.actionPerformed(evt);
			}
		});

		// Radio Nome.
		this.radioNome.setText("Nome");
		this.radioNome.setFont(new Font("Tahoma", 1, 11));
		this.radioNome.setActionCommand("nome_cargo");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.actionPerformed(evt);
			}
		});

		// Radio Salário.
		this.radioSalario.setText("Salário");
		this.radioSalario.setFont(new Font("Tahoma", 1, 11));
		this.radioSalario.setActionCommand("salario");

		this.radioSalario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.actionPerformed(evt);
			}
		});

		// Radio CH Semana.
		this.radioQntHorasSemana.setText("CH Semana");
		this.radioQntHorasSemana.setFont(new Font("Tahoma", 1, 11));
		this.radioQntHorasSemana.setActionCommand("qnt_horas_semana");

		this.radioQntHorasSemana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCodigo);
		group.add(this.radioNome);
		group.add(this.radioSalario);
		group.add(this.radioQntHorasSemana);

		GroupLayout layoutPesquisarCargo = new GroupLayout(
				this.panelPesquisarCargo);
		layoutPesquisarCargo
				.setHorizontalGroup(layoutPesquisarCargo
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarCargo
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarCargo
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarCargo
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				437,
																				Short.MAX_VALUE)
																		.addGap(18))
														.addGroup(
																layoutPesquisarCargo
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
																				radioSalario)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioQntHorasSemana)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addComponent(buttonPesquisar,
												GroupLayout.DEFAULT_SIZE, 204,
												Short.MAX_VALUE)
										.addContainerGap()));
		layoutPesquisarCargo
				.setVerticalGroup(layoutPesquisarCargo
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarCargo
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarCargo
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
								layoutPesquisarCargo
										.createSequentialGroup()
										.addContainerGap(28, Short.MAX_VALUE)
										.addGroup(
												layoutPesquisarCargo
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioCodigo)
														.addComponent(radioNome)
														.addComponent(
																radioSalario)
														.addComponent(
																radioQntHorasSemana))
										.addContainerGap()));
		this.panelPesquisarCargo.setLayout(layoutPesquisarCargo);

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

		// Salário.
		this.labelSalario.setText("Salário:");
		this.textSalario.setFont(new Font("Tahoma", 1, 11));

		// CH Semana.
		this.labelQntHorasSemana.setText("CH Semana:");
		this.textQntHorasSemana.setFont(new Font("Tahoma", 1, 11));

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
																Alignment.LEADING,
																false)
														.addComponent(
																labelSalario,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																labelCodigo,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
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
																				457,
																				Short.MAX_VALUE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				textSalario,
																				GroupLayout.PREFERRED_SIZE,
																				81,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				labelQntHorasSemana)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textQntHorasSemana,
																				GroupLayout.PREFERRED_SIZE,
																				86,
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
														.addComponent(
																textNome,
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
																labelSalario)
														.addComponent(
																textSalario,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelQntHorasSemana)
														.addComponent(
																textQntHorasSemana,
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
				JanCadCargo.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.buttonFecharActionPerformed(evt);
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
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null }, }, new String[] { "Código",
				"Nome", "Salário", "CH Semana" }));

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
				JanCadCargo.this.subNovoActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subNovo);

		this.subFechar.setText("Fechar");
		subFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				SHORTCUT_MASK));
		this.subFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.subFecharActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subFechar);

		this.menuBar.add(this.menuArquivo);

		// Funcionário
		this.menuFuncionario.setMnemonic('F');
		this.menuFuncionario.setText("Funcionário");
		this.menuFuncionario.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanCadCargo.this.funcionarioActionPerformed(evt);
			}
		});
		this.menuFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCargo.this.funcionarioActionPerformed(evt);
			}
		});
		this.menuBar.add(this.menuFuncionario);

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
														panelPesquisarCargo,
														Alignment.TRAILING,
														GroupLayout.DEFAULT_SIZE,
														693, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelPesquisarCargo,
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

		Integer codigo_cargo = Integer.parseInt(tableInformacaoes.getValueAt(
				row, 0).toString());
		String nome = tableInformacaoes.getValueAt(row, 1).toString();
		Double salario = Double.parseDouble(tableInformacaoes
				.getValueAt(row, 2).toString());
		Integer qntHorasSemana = Integer.parseInt(tableInformacaoes.getValueAt(
				row, 3).toString());

		this.textCodigo.setText(codigo_cargo.toString());
		this.textNome.setText(nome.toString());
		this.textSalario.setText(salario.toString());
		this.textQntHorasSemana.setText(qntHorasSemana.toString());

		this.buttonAlterar.setEnabled(true);

		CargoDao daoCargo = new CargoDao();

		if (daoCargo.countCargoFuncionarioCompra(codigo_cargo) == 0
				&& daoCargo.countCargoFuncionarioVenda(codigo_cargo) == 0) {
			this.buttonExcluir.setEnabled(true);
		}

	}

	String acao = "codigo_cargo";

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
			CargoDao dao = new CargoDao();

			List<Cargo> listaCargo = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaCargo.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaCargo = dao.listar();
				preencherJTable(listaCargo);
			} else {
				preencherJTable(listaCargo);
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
		CargoDao dao = new CargoDao();
		this.textCodigo.setText(Long.toString(dao.retornaAutoIncrement()));
		esvasiarCampos();
		this.textNome.grabFocus();
	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum cargo selecionado!");
		} else {
			Visivel();
			desativarBotoes();
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum cargo selecionado!");
		} else {
			Cargo cargo = new Cargo();
			cargo.setCodigo_cargo(Integer.parseInt((this.textCodigo.getText())));

			String mensagem = "Deseja excluir o cargo de código "
					+ cargo.getCodigo_cargo() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				CargoDao dao = new CargoDao();
				dao.remover(cargo);

				List<Cargo> listaCargo = dao.listar();

				preencherJTable(listaCargo);

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

			Cargo cargo = new Cargo();
			cargo.setCodigo_cargo(Integer.parseInt((this.textCodigo.getText())));
			cargo.setSalario(Double.parseDouble(this.textSalario.getText()
					.toString()));
			cargo.setQnt_horas_semana(Integer.parseInt(this.textQntHorasSemana
					.getText().toString()));

			// Atualiza!!!
			CargoDao dao = new CargoDao();
			dao.alterar(cargo);

			JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
			List<Cargo> listaCargo = dao.listar();
			preencherJTable(listaCargo);

			esvasiarCampos();
			this.textCodigo.setText("");

			Invisivel();

			desativarBotoes();
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		Cargo cargo = new Cargo();
		cargo.setNome_cargo(this.textNome.getText());

		CargoDao dao = new CargoDao();
		if (dao.nomeCargo(cargo)) {
			JOptionPane.showMessageDialog(null, "Nome do cargo já cadastrado!");
			this.textNome.setText("");
			this.textNome.grabFocus();
		} else {
			if (this.testeCampos()) {
				JOptionPane.showMessageDialog(null,
						"Todos os campos são obrigatórios!");
			} else {

				cargo.setCodigo_cargo(Integer.parseInt((this.textCodigo
						.getText())));
				cargo.setNome_cargo(this.textNome.getText());
				cargo.setSalario(Double.parseDouble(this.textSalario.getText()
						.toString()));
				cargo.setQnt_horas_semana(Integer
						.parseInt(this.textQntHorasSemana.getText().toString()));

				// grave nessa conexão!!!
				dao.inserir(cargo);

				JOptionPane.showMessageDialog(null,
						"O cadastro do cargo:\n" + this.textCodigo.getText()
								+ " - " + cargo.getNome_cargo()
								+ "\nFoi realizado com sucesso!");
				List<Cargo> listaCargo = dao.listar();
				preencherJTable(listaCargo);

				esvasiarCampos();
				this.textCodigo.setText("");

				Invisivel();

				desativarBotoes();
			}
		}
	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {
		CargoDao dao = new CargoDao();
		if (dao.count() == 0) {
			JOptionPane.showMessageDialog(null,
					"Cadastre o cargo do funcionário");
		} else {

			switch (this.nome) {
			case "principal":
				dispose();
				JanPrincipal janP = new JanPrincipal();
				janP.setVisible(true);
				break;

			case "funcionario":
				dispose();
				JanCadFuncionario cadF = new JanCadFuncionario("principal");
				cadF.setVisible(true);
				break;

			case "login":
				dispose();
				JanCadFuncionario cadFu = new JanCadFuncionario("login");
				cadFu.setVisible(true);
				break;

			default:
				break;
			}
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

	// Funcionário.
	private void funcionarioActionPerformed(MouseEvent evt) {
		CargoDao dao = new CargoDao();
		if (dao.count() == 0) {
			JOptionPane.showMessageDialog(null, "Cadastre um cargo");
		} else {
			// ComercioDao
			ComercioDao daoComercio = new ComercioDao();
			if (daoComercio.count() > 0) {
				dispose();
				JanCadFuncionario cadF = new JanCadFuncionario("cargo");
				cadF.setVisible(true);
			} else {
				dispose();
				JanCadComercio cadC = new JanCadComercio("funcionario");
				cadC.setVisible(true);
			}

		}
	}

	// Funcionário.
	private void funcionarioActionPerformed(ActionEvent evt) {
		funcionarioActionPerformed(evt);
	}

}
