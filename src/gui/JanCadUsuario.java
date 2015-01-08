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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

import jdbc.dao.CargoDao;
import jdbc.dao.ComercioDao;
import jdbc.dao.UsuarioDao;
import regraTextField.JPasswordFieldSenha;
import regraTextField.JtextFieldSomenteLetras;
import regraTextField.JtextFieldSomenteNumeros;
import bean.Usuario;

public class JanCadUsuario extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome = null;
	private JTextField textPesquisar;
	private JTextField textCodigo;
	private JTextField textNome;
	private JPasswordField passwordSenha;
	private JPasswordField passwordRepetirSenha;
	private JCheckBox checkBoxAtivo;
	private JComboBox<Object> comboNivel;
	private JButton buttonPesquisar;
	private JButton buttonNovo;
	private JButton buttonAlterar;
	private JButton buttonExcluir;
	private JButton buttonAtualizar;
	private JButton buttonSalvar;
	private JButton buttonFechar;
	private JLabel labelCodigo;
	private JLabel labelNome;
	private JLabel labelNivel;
	private JLabel labelSenha;
	private JLabel labelRepetirSenha;
	private JMenuBar menuBar;
	private JMenu menuArquivo;
	private JMenuItem subNovo;
	private JMenuItem subFechar;
	private JMenu menuFuncionario;
	private JMenu menuInativo;
	private JPanel panelPesquisarUsuario;
	private JPanel panelDadosCadastrais;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacaoes;
	private JRadioButton radioCodigo;
	private JRadioButton radioNome;
	private JRadioButton radioNivel;

	public JanCadUsuario(String nome) {
		super("Cadastro - Usuário");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;

		UsuarioDao dao = new UsuarioDao();
		List<Usuario> listaUsuario = dao.listar();

		preencherJTable(listaUsuario);
		Invisivel();
		desativarBotoes();

		this.comboNivel.removeAllItems();
		this.comboNivel.addItem("");
		this.comboNivel.addItem("3");
		this.comboNivel.addItem("2");
	}

	public void Invisivel() {
		this.textNome.setEditable(false);
		this.passwordSenha.setEditable(false);
		this.passwordRepetirSenha.setEditable(false);
		this.comboNivel.setEnabled(false);
		this.checkBoxAtivo.setEnabled(false);
	}

	public void Visivel() {
		this.passwordSenha.setEditable(true);
		this.passwordRepetirSenha.setEditable(true);

		UsuarioDao dao = new UsuarioDao();

		Usuario usuario = new Usuario();
		usuario.setNome_usuario(this.textNome.getText().toString());

		if (!this.comboNivel.getSelectedItem().toString().equals("")) {
			usuario.setNivel_usuario(Integer.parseInt(this.comboNivel
					.getSelectedItem().toString()));
		}

		if ((dao.count() >= 1 && dao.nomeUsuario(usuario) == false)
				|| (dao.count() > 1 && dao.nomeUsuario(usuario) == true)
				|| (dao.count() == 1 && dao.nivelUsuario(usuario) == false)
				|| (dao.nomeUsuario(usuario) == true && dao.ativo(usuario) == false)) {
			this.comboNivel.setEnabled(true);
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
		this.passwordSenha.setText("");
		this.passwordRepetirSenha.setText("");
		this.comboNivel.setSelectedItem("");
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
				|| (this.passwordSenha.getText().equals(""))
				|| (this.passwordRepetirSenha.getText().equals(""))
				|| (this.comboNivel.getSelectedItem().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Usuario> listaUsuario) {
		this.tableInformacaoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(1)
				.setPreferredWidth(150);
		this.tableInformacaoes.getColumnModel().getColumn(2)
				.setPreferredWidth(150);
		this.tableInformacaoes.getColumnModel().getColumn(3)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(4)
				.setPreferredWidth(5);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacaoes
				.getModel();
		tabela.setNumRows(0);

		for (Usuario usuario : listaUsuario) {
			tabela.addRow(new Object[] { usuario.getCodigo_usuario(),
					usuario.getNome_usuario(), usuario.getSenha_usuario(),
					usuario.getNivel_usuario(), usuario.getAtivo() });
		}

		this.tableInformacaoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadUsuario.this.tableInformacaoesTableChanged(e);
					}
				});

	}

	private void Componentes() {
		this.textPesquisar = new JTextField();
		this.textCodigo = new JtextFieldSomenteNumeros(7);
		this.textNome = new JtextFieldSomenteLetras(10);
		this.passwordSenha = new JPasswordFieldSenha(10);
		this.passwordRepetirSenha = new JPasswordFieldSenha(10);
		this.comboNivel = new JComboBox<>();
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
		this.labelNivel = new JLabel();
		this.labelSenha = new JLabel();
		this.labelRepetirSenha = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuArquivo = new JMenu();
		this.subNovo = new JMenuItem();
		this.subFechar = new JMenuItem();
		this.menuFuncionario = new JMenu();
		this.menuInativo = new JMenu();
		this.panelPesquisarUsuario = new JPanel();
		this.panelDadosCadastrais = new JPanel();
		this.panelBotoes = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacaoes = new JTable();
		tableInformacaoes.setColumnSelectionAllowed(true);
		this.radioCodigo = new JRadioButton();
		this.radioNome = new JRadioButton();
		this.radioNivel = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR USUARIOS ----------- //
		this.panelPesquisarUsuario.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Usuários"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCodigo.setText("Código");
		this.radioCodigo.setFont(new Font("Tahoma", 1, 11));
		this.radioCodigo.setActionCommand("codigo_usuario");
		this.radioCodigo.setSelected(true);

		this.radioCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.actionPerformed(evt);
			}
		});

		// Radio Nome.
		this.radioNome.setText("Nome");
		this.radioNome.setFont(new Font("Tahoma", 1, 11));
		this.radioNome.setActionCommand("nome_usuario");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.actionPerformed(evt);
			}
		});

		// Radio Nível.
		this.radioNivel.setText("Nível");
		this.radioNivel.setFont(new Font("Tahoma", 1, 11));
		this.radioNivel.setActionCommand("nivel_usuario");

		this.radioNivel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCodigo);
		group.add(this.radioNome);
		group.add(this.radioNivel);

		GroupLayout layoutPesquisarUsuario = new GroupLayout(
				this.panelPesquisarUsuario);
		layoutPesquisarUsuario
				.setHorizontalGroup(layoutPesquisarUsuario
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarUsuario
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarUsuario
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarUsuario
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				441,
																				Short.MAX_VALUE)
																		.addGap(18))
														.addGroup(
																layoutPesquisarUsuario
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
																				radioNivel)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addComponent(buttonPesquisar,
												GroupLayout.DEFAULT_SIZE, 208,
												Short.MAX_VALUE)
										.addContainerGap()));
		layoutPesquisarUsuario
				.setVerticalGroup(layoutPesquisarUsuario
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarUsuario
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarUsuario
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
										.addGap(23))
						.addGroup(
								Alignment.TRAILING,
								layoutPesquisarUsuario
										.createSequentialGroup()
										.addContainerGap(27, Short.MAX_VALUE)
										.addGroup(
												layoutPesquisarUsuario
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioCodigo)
														.addComponent(radioNome)
														.addComponent(
																radioNivel,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		this.panelPesquisarUsuario.setLayout(layoutPesquisarUsuario);

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

		// Nivel.
		this.labelNivel.setText("Nível:");
		this.comboNivel.setFont(new Font("Tahoma", 1, 11));

		// Senha.
		this.labelSenha.setText("Senha:");
		this.passwordSenha.setFont(new Font("Tahoma", 1, 11));

		// Repetir Senha.
		this.labelRepetirSenha.setText("Repetir senha:");
		this.passwordRepetirSenha.setFont(new Font("Tahoma", 1, 11));

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
																Alignment.LEADING,
																false)
														.addComponent(
																labelSenha,
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
																				84,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelNome)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				textNome,
																				GroupLayout.PREFERRED_SIZE,
																				297,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelNivel,
																				GroupLayout.PREFERRED_SIZE,
																				39,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				comboNivel,
																				GroupLayout.PREFERRED_SIZE,
																				88,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																Alignment.TRAILING,
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				passwordSenha,
																				GroupLayout.DEFAULT_SIZE,
																				200,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelRepetirSenha)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				passwordRepetirSenha,
																				GroupLayout.PREFERRED_SIZE,
																				207,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				checkBoxAtivo)))
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
																comboNivel,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelNivel)
														.addComponent(
																textNome,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelNome))
										.addGap(22)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelSenha)
														.addComponent(
																passwordSenha,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																checkBoxAtivo)
														.addComponent(
																passwordRepetirSenha,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelRepetirSenha))));
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
				JanCadUsuario.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.buttonFecharActionPerformed(evt);
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
				{ null, null, null, null, null },
				{ null, null, null, null, null } }, new String[] { "Código",
				"Nome", "Senha", "Nível", "Ativo" }));

		this.tableInformacaoes.setFocusable(false);
		this.scrollPaneInformacaoes.setViewportView(this.tableInformacaoes);

		// ---------- BARRA DE FERRAMENTAS ----------- //
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		this.menuArquivo.setMnemonic('A');
		this.menuArquivo.setText("Arquivo");

		this.subNovo.setText("Novo");
		subNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				SHORTCUT_MASK));
		this.subNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.subNovoActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subNovo);

		this.subFechar.setText("Fechar");
		subFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				SHORTCUT_MASK));
		this.subFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.subFecharActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subFechar);

		this.menuBar.add(this.menuArquivo);

		// Funcionário
		this.menuFuncionario.setMnemonic('F');
		this.menuFuncionario.setText("Funcionário");
		this.menuFuncionario.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanCadUsuario.this.funcionarioActionPerformed(evt);
			}
		});
		this.menuFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.funcionarioActionPerformed(evt);
			}
		});
		this.menuBar.add(this.menuFuncionario);

		// Inativo
		this.menuInativo.setMnemonic('I');
		this.menuInativo.setText("Inativo");
		this.menuInativo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanCadUsuario.this.inativoActionPerformed(evt);
			}
		});
		this.menuInativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadUsuario.this.inativoActionPerformed(evt);
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
														scrollPaneInformacaoes,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														701, Short.MAX_VALUE)
												.addComponent(
														panelPesquisarUsuario,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														panelDadosCadastrais,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														panelBotoes,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														701, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelPesquisarUsuario,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(3)
								.addComponent(panelDadosCadastrais,
										GroupLayout.PREFERRED_SIZE, 106,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelBotoes,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPaneInformacaoes,
										GroupLayout.PREFERRED_SIZE, 116,
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

		Integer codigo_usuario = Integer.parseInt(tableInformacaoes.getValueAt(
				row, 0).toString());
		String nome = tableInformacaoes.getValueAt(row, 1).toString();
		String senha = tableInformacaoes.getValueAt(row, 2).toString();
		Integer nivel = Integer.parseInt(tableInformacaoes.getValueAt(row, 3)
				.toString());
		Boolean ativo = Boolean.parseBoolean(tableInformacaoes.getValueAt(row,
				4).toString());

		this.textCodigo.setText(codigo_usuario.toString());
		this.textNome.setText(nome.toString());
		this.passwordSenha.setText(senha.toString());
		this.passwordRepetirSenha.setText(senha.toString());
		this.checkBoxAtivo.setSelected(ativo);
		this.comboNivel.setSelectedItem(nivel.toString());

		this.buttonAlterar.setEnabled(true);

		UsuarioDao dao = new UsuarioDao();
		Usuario usuario = new Usuario();
		usuario.setNome_usuario(this.textNome.getText().toString());

		if (!this.comboNivel.getSelectedItem().toString().equals("")) {
			usuario.setNivel_usuario(Integer.parseInt(this.comboNivel
					.getSelectedItem().toString()));
		}

		if ((dao.countAtivoInativo(true) >= 1 && dao.nomeUsuario(usuario) == false)
				|| (dao.count() > 1 && dao.nomeUsuario(usuario) == true)
				|| (dao.count() == 1 && dao.nivelUsuario(usuario) == false)
				|| (dao.nomeUsuario(usuario) == true && dao.ativo(usuario) == false)) {
			if (dao.countUsuarioFuncionarioCompra(codigo_usuario) == 0
					&& dao.countUsuarioFuncionarioVenda(codigo_usuario) == 0) {
				this.buttonExcluir.setEnabled(true);
			}
		}
	}

	String acao = "codigo_usuario";

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
			UsuarioDao dao = new UsuarioDao();

			List<Usuario> listaUsuario = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaUsuario.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaUsuario = dao.listar();
				preencherJTable(listaUsuario);
			} else {
				preencherJTable(listaUsuario);
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

		esvasiarCampos();
		this.checkBoxAtivo.setSelected(true);
		Visivel();
		UsuarioDao dao = new UsuarioDao();
		this.textCodigo.setText(Long.toString(dao.retornaAutoIncrement()));

		this.textNome.grabFocus();

		UsuarioDao daoUsuario = new UsuarioDao();

		if (daoUsuario.count() == 0) {
			this.comboNivel.setSelectedItem("3");
		}
	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum usuário selecionado!");
		} else {
			desativarBotoes();
			Visivel();
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum usuário selecionado!");
		} else {
			Usuario usuario = new Usuario();
			usuario.setCodigo_usuario(Integer.parseInt((this.textCodigo
					.getText())));

			String mensagem = "Deseja excluir o usuário de código "
					+ usuario.getCodigo_usuario() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				UsuarioDao dao = new UsuarioDao();
				dao.remover(usuario);

				List<Usuario> listaUsuario = dao.listar();

				preencherJTable(listaUsuario);

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
		if (this.passwordSenha.getText().equals(
				this.passwordRepetirSenha.getText())) {
			if (this.testeCampos()) {
				JOptionPane.showMessageDialog(null,
						"Todos os campos são obrigatórios!");
			} else {

				Usuario usuario = new Usuario();
				usuario.setCodigo_usuario(Integer.parseInt((this.textCodigo
						.getText())));
				usuario.setSenha_usuario(this.passwordSenha.getText());
				usuario.setNivel_usuario(Integer.parseInt(this.comboNivel
						.getSelectedItem().toString()));
				usuario.setAtivo(this.checkBoxAtivo.isSelected());

				// Atualiza!!!
				UsuarioDao dao = new UsuarioDao();
				dao.alterar(usuario);

				JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
				List<Usuario> listaUsuario = dao.listar();
				preencherJTable(listaUsuario);

				esvasiarCampos();
				this.textCodigo.setText("");

				Invisivel();

				desativarBotoes();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Senhas incompatíveis!");
			this.passwordSenha.setText("");
			this.passwordRepetirSenha.setText("");
			this.passwordSenha.grabFocus();
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		Usuario usuario = new Usuario();
		usuario.setNome_usuario(this.textNome.getText());

		UsuarioDao dao = new UsuarioDao();
		if (dao.nomeUsuario(usuario)) {
			JOptionPane.showMessageDialog(null, "Usuário já cadastrado!");
			this.textNome.setText("");
			this.textNome.grabFocus();
		} else {
			if (this.passwordSenha.getText().equals(
					this.passwordRepetirSenha.getText())) {
				if (this.testeCampos()) {
					JOptionPane.showMessageDialog(null,
							"Todos os campos são obrigatórios!");
				} else {

					usuario.setNome_usuario(this.textNome.getText());
					usuario.setSenha_usuario(this.passwordSenha.getText());
					usuario.setNivel_usuario(Integer.parseInt(this.comboNivel
							.getSelectedItem().toString()));
					usuario.setAtivo(this.checkBoxAtivo.isSelected());

					// grave nessa conexão!!!
					dao.inserir(usuario);

					JOptionPane.showMessageDialog(
							null,
							"O cadastro do usuario:\n"
									+ this.textCodigo.getText() + " - "
									+ usuario.getNome_usuario()
									+ "\nFoi realizado com sucesso!");
					List<Usuario> listaUsuario = dao.listar();
					preencherJTable(listaUsuario);

					esvasiarCampos();
					this.textCodigo.setText("");

					Invisivel();

					desativarBotoes();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Senhas incompatíveis!");
				this.passwordSenha.setText("");
				this.passwordRepetirSenha.setText("");
				this.passwordSenha.grabFocus();
			}
		}
	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {
		UsuarioDao dao = new UsuarioDao();
		if (dao.count() == 0) {
			JOptionPane.showMessageDialog(null, "Cadastre um usuário");
		} else {

			switch (this.nome) {
			case "funcionario":
				dispose();
				JanCadFuncionario cadF = new JanCadFuncionario("principal");
				cadF.setVisible(true);
				break;

			case "principal":
				dispose();
				JanPrincipal janP = new JanPrincipal();
				janP.setVisible(true);
				break;

			case "login":
				dispose();
				JanCadEndereco cadE = new JanCadEndereco("Comércio");
				cadE.setVisible(true);
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
		UsuarioDao dao = new UsuarioDao();
		if (dao.count() == 0) {
			JOptionPane.showMessageDialog(null, "Cadastre um usuário");
		} else {
			// CargoDao
			CargoDao daoCargo = new CargoDao();

			// ComercioDao
			ComercioDao daoComercio = new ComercioDao();

			if (daoCargo.count() > 0) {
				if (daoComercio.count() > 0) {
					dispose();
					JanCadFuncionario cadF = new JanCadFuncionario("usuario");
					cadF.setVisible(true);
				} else {
					dispose();
					JanCadComercio cadC = new JanCadComercio("funcionario");
					cadC.setVisible(true);
				}
			} else {
				dispose();
				JanCadCargo cadC = new JanCadCargo("funcionario");
				cadC.setVisible(true);
			}
		}
	}

	// Funcionário.
	private void funcionarioActionPerformed(ActionEvent evt) {
		funcionarioActionPerformed(evt);
	}

	// Inativo.
	private void inativoActionPerformed(MouseEvent evt) {
		funcoes();
		UsuarioDao dao = new UsuarioDao();
		if (dao.count() > 0) {

			if (dao.countAtivoInativo(false) > 0) {
				List<Usuario> listaUsuario = dao.consultaInativo();

				preencherJTable(listaUsuario);

			} else {
				JOptionPane.showMessageDialog(null,
						"Nehum usuário inativo cadastrado!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Nehum usuário cadastrado!");
		}
	}

	// Inativo.
	private void inativoActionPerformed(ActionEvent evt) {
		inativoActionPerformed(evt);
	}
}
