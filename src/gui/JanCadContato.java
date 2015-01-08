package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;

import regraTextField.JtextFieldSomenteLetras;
import jdbc.dao.ClienteDao;
import jdbc.dao.ContatoDao;
import bean.Contato;

public class JanCadContato extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String inicioTelefone = null;
	MaskFormatter maskTelefone;
	private JTextField textPesquisar;
	private JComboBox<Object> comboCliente;
	private JTextField textCpf;
	private JTextField textNome;
	private JTextField textNomeContato;
	private JTextField textTelefone;
	private JButton buttonPesquisar;
	private JButton buttonNovo;
	private JButton buttonAlterar;
	private JButton buttonExcluir;
	private JButton buttonAtualizar;
	private JButton buttonSalvar;
	private JButton buttonFechar;
	private JLabel labelCliente;
	private JLabel labelCpf;
	private JLabel labelNome;
	private JLabel labelNomeContato;
	private JLabel labelTelefone;
	private JMenuBar menuBar;
	private JMenu menuArquivo;
	private JMenuItem subNovo;
	private JMenuItem subFechar;
	private JPanel panelPesquisarContato;
	private JPanel panelDadosCadastrais;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacaoes;
	private JRadioButton radioCliente;
	private JRadioButton radioNome;
	private JRadioButton radioTelefone;

	public JanCadContato() {
		super("Cadastro - Contato");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		ContatoDao daoContato = new ContatoDao();
		List<Contato> listaContato = daoContato.listar();

		preencherJTable(listaContato);
		Invisivel();
		desativarBotoes();

		ClienteDao daoCliente = new ClienteDao();
		this.comboCliente.removeAllItems();
		this.comboCliente.addItem("");
		ArrayList<Integer> cliente = daoCliente.comboCliente();
		Iterator<Integer> i = cliente.iterator();

		while (i.hasNext()) {
			this.comboCliente.addItem(String.valueOf(i.next()));
		}

		preencherJTable(listaContato);
		Invisivel();
		desativarBotoes();
	}

	public void Invisivel() {
		this.comboCliente.setEnabled(false);
		this.textCpf.setEditable(false);
		this.textNome.setEditable(false);
		this.textNomeContato.setEditable(false);
		this.textTelefone.setEditable(false);
	}

	public void Visivel() {
		this.comboCliente.setEnabled(true);
		this.textNomeContato.setEditable(true);
		this.textTelefone.setEditable(true);
		VisivelTelefone();
	}

	public void VisivelTelefone() {
		this.textTelefone.setEditable(true);
	}

	public void desativarBotoes() {
		this.buttonSalvar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonAlterar.setEnabled(false);
	}

	public void esvasiarCampos() {
		this.textNomeContato.setText("");
		this.textTelefone.setText("");
		esvasiarCamposCliente();
	}

	public void esvasiarCamposCliente() {
		this.textCpf.setText("");
		this.textNome.setText("");
	}
	
	public void funcoes() {
		desativarBotoes();
		esvasiarCampos();
		Invisivel();
		this.comboCliente.setSelectedItem("");
	}

	// teste campos vaisios
	public Boolean testeCampos() {
		if ((this.comboCliente.getSelectedItem().equals(""))
				|| (this.textNomeContato.getText().equals(""))
				|| (this.textTelefone.getText().equals("(  )    -    "))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Contato> listaContato) {
		this.tableInformacaoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(1)
				.setPreferredWidth(200);
		this.tableInformacaoes.getColumnModel().getColumn(2)
				.setPreferredWidth(100);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacaoes
				.getModel();
		tabela.setNumRows(0);

		for (Contato contato : listaContato) {
			tabela.addRow(new Object[] {
					contato.getCliente_contato().getCodigo_cliente(),
					contato.getNome_contato(), contato.getTelefone_contato() });
		}

		this.tableInformacaoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadContato.this.tableInformacaoesTableChanged(e);
					}
				});
	}

	private void Componentes() {
		try {
			this.maskTelefone = new MaskFormatter("(##)####-####");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel inserir a mascara");
		}
		this.textPesquisar = new JTextField();
		this.comboCliente = new JComboBox<>();
		this.textCpf = new JTextField();
		this.textNome = new JTextField();
		this.textTelefone = new JFormattedTextField(this.maskTelefone);
		this.textNomeContato = new JtextFieldSomenteLetras(50);
		this.buttonPesquisar = new JButton();
		this.buttonNovo = new JButton();
		this.buttonAlterar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAtualizar = new JButton();
		this.buttonSalvar = new JButton();
		this.buttonFechar = new JButton();
		this.labelCliente = new JLabel();
		this.labelCpf = new JLabel();
		this.labelNome = new JLabel();
		this.labelTelefone = new JLabel();
		this.labelNomeContato = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuArquivo = new JMenu();
		this.subNovo = new JMenuItem();
		this.subFechar = new JMenuItem();
		this.panelPesquisarContato = new JPanel();
		this.panelDadosCadastrais = new JPanel();
		this.panelBotoes = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacaoes = new JTable();
		tableInformacaoes.setColumnSelectionAllowed(true);
		this.radioCliente = new JRadioButton();
		this.radioNome = new JRadioButton();
		this.radioTelefone = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR CARGOS ----------- //
		this.panelPesquisarContato.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Contatos"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCliente.setText("Cliente");
		this.radioCliente.setFont(new Font("Tahoma", 1, 11));
		this.radioCliente.setActionCommand("cliente_contato");
		this.radioCliente.setSelected(true);

		this.radioCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.actionPerformed(evt);
			}
		});

		// Radio Nome.
		this.radioNome.setText("Nome");
		this.radioNome.setFont(new Font("Tahoma", 1, 11));
		this.radioNome.setActionCommand("nome_contato");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.actionPerformed(evt);
			}
		});

		// Radio Salário.
		this.radioTelefone.setText("Telefone");
		this.radioTelefone.setFont(new Font("Tahoma", 1, 11));
		this.radioTelefone.setActionCommand("telefone_contato");

		this.radioTelefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCliente);
		group.add(this.radioNome);
		group.add(this.radioTelefone);

		GroupLayout layoutPesquisarContato = new GroupLayout(
				this.panelPesquisarContato);
		layoutPesquisarContato
				.setHorizontalGroup(layoutPesquisarContato
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarContato
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarContato
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarContato
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				437,
																				Short.MAX_VALUE)
																		.addGap(18))
														.addGroup(
																layoutPesquisarContato
																		.createSequentialGroup()
																		.addComponent(
																				radioCliente)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioNome)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioTelefone)))
										.addComponent(buttonPesquisar,
												GroupLayout.DEFAULT_SIZE, 204,
												Short.MAX_VALUE)
										.addContainerGap()));
		layoutPesquisarContato
				.setVerticalGroup(layoutPesquisarContato
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarContato
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarContato
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
								layoutPesquisarContato
										.createSequentialGroup()
										.addContainerGap(28, Short.MAX_VALUE)
										.addGroup(
												layoutPesquisarContato
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioCliente)
														.addComponent(radioNome)
														.addComponent(
																radioTelefone))
										.addContainerGap()));
		this.panelPesquisarContato.setLayout(layoutPesquisarContato);

		// ---------- PANEL DADOS CADASTRAIS ----------- //
		this.panelDadosCadastrais.setBorder(BorderFactory
				.createTitledBorder("Dados Cadastrais"));

		// Cliente.
		this.labelCliente.setText("Cliente:");
		this.comboCliente.setFont(new Font("Tahoma", 1, 11));
		this.comboCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.comboClienteActionPerformed(evt);
			}
		});

		// CPF.
		this.labelCpf.setText("CPF:");
		this.textCpf.setFont(new Font("Tahoma", 1, 11));

		// Nome.
		this.labelNome.setText("Nome:");
		this.textNome.setFont(new Font("Tahoma", 1, 11));

		// Telefone.
		this.labelTelefone.setText("Telefone:");
		this.textTelefone.setFont(new Font("Tahoma", 1, 11));

		// Nome Contato.
		this.labelNomeContato.setText("Nome Contato:");
		this.textNomeContato.setFont(new Font("Tahoma", 1, 11));

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
																				labelCliente)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				comboCliente,
																				GroupLayout.PREFERRED_SIZE,
																				72,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelCpf)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textCpf,
																				GroupLayout.PREFERRED_SIZE,
																				145,
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
																				251,
																				Short.MAX_VALUE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelNomeContato)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textNomeContato,
																				GroupLayout.DEFAULT_SIZE,
																				243,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelTelefone)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textTelefone,
																				GroupLayout.PREFERRED_SIZE,
																				207,
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
																Alignment.LEADING)
														.addGroup(
																layoutDadosCadastrais
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				labelCliente)
																		.addComponent(
																				textNome,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layoutDadosCadastrais
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				comboCliente,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				labelCpf)
																		.addComponent(
																				textCpf,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				labelNome)))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textTelefone,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelNomeContato)
														.addComponent(
																textNomeContato,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelTelefone))));
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
				JanCadContato.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.buttonFecharActionPerformed(evt);
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
				{ null, null, null }, { null, null, null },
				{ null, null, null }, }, new String[] { "Código Cliente",
				"Nome Contato", "Telefone Contato" }));

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
				JanCadContato.this.subNovoActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subNovo);

		this.subFechar.setText("Fechar");
		subFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				SHORTCUT_MASK));
		this.subFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadContato.this.subFecharActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subFechar);

		this.menuBar.add(this.menuArquivo);

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
														GroupLayout.DEFAULT_SIZE,
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
														panelPesquisarContato,
														GroupLayout.DEFAULT_SIZE,
														693, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelPesquisarContato,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelDadosCadastrais,
										GroupLayout.PREFERRED_SIZE, 102,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelBotoes,
										GroupLayout.DEFAULT_SIZE, 53,
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

		Integer cliente = Integer.parseInt(tableInformacaoes.getValueAt(row, 0)
				.toString());
		String nomeContato = tableInformacaoes.getValueAt(row, 1).toString();
		String telefoneContato = tableInformacaoes.getValueAt(row, 2)
				.toString();

		this.comboCliente.setSelectedItem(cliente.toString());
		this.textNomeContato.setText(nomeContato.toString());
		this.textTelefone.setText(telefoneContato.toString());

		this.buttonAlterar.setEnabled(true);
		this.buttonExcluir.setEnabled(true);
	}

	String acao = "cliente_contato";

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
			ContatoDao dao = new ContatoDao();

			List<Contato> listaContato = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaContato.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaContato = dao.listar();
				preencherJTable(listaContato);
			} else {
				preencherJTable(listaContato);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Preencha o campo da pesquisa");
		}
	}

	// ButtonNovo.
	private void buttonNovoActionPerformed(ActionEvent evt) {
		desativarBotoes();
		this.buttonSalvar.setEnabled(true);

		Visivel();
		esvasiarCampos();
		this.comboCliente.grabFocus();
	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum contato selecionado!");
		} else {
			VisivelTelefone();
			desativarBotoes();
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum contato selecionado!");
		} else {
			Contato contato = new Contato();
			contato.getCliente_contato().setCodigo_cliente(
					Integer.parseInt((this.comboCliente.getSelectedItem()
							.toString())));

			contato.setNome_contato(this.textNomeContato.getText());

			String mensagem = "Deseja excluir o contato de do cliente "
					+ contato.getCliente_contato() + " e nome do contato "
					+ contato.getNome_contato() + " ?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				ContatoDao dao = new ContatoDao();
				dao.remover(contato);

				List<Contato> listaContato = dao.listar();

				preencherJTable(listaContato);

				esvasiarCampos();
				this.comboCliente.setSelectedItem("");
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

			Contato contato = new Contato();
			contato.getCliente_contato().setCodigo_cliente(
					Integer.parseInt((this.comboCliente.getSelectedItem()
							.toString())));
			contato.setNome_contato(this.textNomeContato.getText());
			contato.setTelefone_contato(this.textTelefone.getText());

			// Atualiza!!!
			ContatoDao dao = new ContatoDao();
			dao.alterar(contato);

			JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
			List<Contato> listaContato = dao.listar();
			preencherJTable(listaContato);

			esvasiarCampos();
			this.comboCliente.setSelectedItem("");

			Invisivel();

			desativarBotoes();
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		Contato contato = new Contato();
		contato.setNome_contato(this.textNome.getText());

		ContatoDao dao = new ContatoDao();
		if (dao.nomeContato(contato)) {
			JOptionPane.showMessageDialog(null, "Contato já cadastrado!");
			this.textNomeContato.setText("");
		} else {
			if (this.testeCampos()) {
				JOptionPane.showMessageDialog(null,
						"Todos os campos são obrigatórios!");
			} else {

				contato.getCliente_contato().setCodigo_cliente(
						Integer.parseInt((this.comboCliente.getSelectedItem()
								.toString())));
				contato.setNome_contato(this.textNomeContato.getText());
				contato.setTelefone_contato(this.textTelefone.getText());

				// grave nessa conexão!!!
				dao.inserir(contato);

				JOptionPane.showMessageDialog(
						null,
						"O cadastro do contato:\n"
								+ this.comboCliente.getSelectedItem() + " - "
								+ contato.getNome_contato()
								+ "\nFoi realizado com sucesso!");
				List<Contato> listaContato = dao.listar();
				preencherJTable(listaContato);

				esvasiarCampos();
				this.comboCliente.setSelectedItem("");

				Invisivel();

				desativarBotoes();
			}
		}
	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {
		dispose();
		JanCadCliente janC = new JanCadCliente("principal");
		janC.setVisible(true);
	}

	// JMenuItemNovo.
	private void subNovoActionPerformed(ActionEvent evt) {
		buttonNovoActionPerformed(evt);
	}

	// JMenuItemFechar.
	private void subFecharActionPerformed(ActionEvent evt) {
		buttonFecharActionPerformed(evt);
	}

	// comboCliente
	private void comboClienteActionPerformed(ActionEvent evt) {
		if (!this.comboCliente.getSelectedItem().equals("")) {
			try {
				ClienteDao dao = new ClienteDao();
				List<String> listaCliente = dao.listaCliente(Integer
						.parseInt(this.comboCliente.getSelectedItem()
								.toString()));
				this.textCpf.setText(listaCliente.get(0));
				this.textNome.setText(listaCliente.get(1));
			} catch (java.lang.IndexOutOfBoundsException indexOutOfBoundsException) {
				JOptionPane.showMessageDialog(null, "O cliente "
						+ this.comboCliente.getSelectedItem()
						+ " não foi encontrado");
			}

		} else {
			esvasiarCamposCliente();
		}

	}

}