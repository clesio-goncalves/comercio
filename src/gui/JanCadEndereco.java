package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

import jdbc.dao.EnderecoDao;
import jdbc.dao.UsuarioDao;
import regraTextField.JtextFieldSomenteLetras;
import regraTextField.JtextFieldSomenteLetrasNumero;
import regraTextField.JtextFieldSomenteNumeros;
import bean.Endereco;

public class JanCadEndereco extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nome = null;
	String inicioCep = null;
	MaskFormatter maskCep;
	private JTextField textPesquisar;
	private JTextField textCodigo;
	private JTextField textCep;
	private JTextField textLogradouro;
	private JTextField textNumero;
	private JTextField textBairro;
	private JComboBox<Object> comboUf;
	private JTextField textCidade;
	private JTextField textComplemento;
	private JButton buttonPesquisar;
	private JButton buttonNovo;
	private JButton buttonAlterar;
	private JButton buttonExcluir;
	private JButton buttonAtualizar;
	private JButton buttonSalvar;
	private JButton buttonFechar;
	private JLabel labelCodigo;
	private JLabel labelCep;
	private JLabel labelUf;
	private JLabel labelNumero;
	private JLabel labelLogradouro;
	private JLabel labelBairro;
	private JLabel labelCidade;
	private JLabel labelComplemento;
	private JMenuBar menuBar;
	private JMenu menuArquivo;
	private JMenuItem subNovo;
	private JMenuItem subFechar;
	private JMenu menuCadastro;
	private JMenuItem subCadCliente;
	private JMenuItem subCadComercio;
	private JMenuItem subCadFornecedor;
	private JPanel panelPesquisarEndereco;
	private JPanel panelDadosCadastrais;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacaoes;
	private JRadioButton radioCodigo;
	private JRadioButton radioCep;
	private JRadioButton radioLogradouro;
	private JRadioButton radioNumero;
	private JRadioButton radioBairro;
	private JRadioButton radioUf;
	private JRadioButton radioCidade;

	public JanCadEndereco(String nome) {
		super("Cadastro - Endereço " + nome);
		this.nome = nome;
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		EnderecoDao dao = new EnderecoDao();
		List<Endereco> listaEndereco = dao.listar();

		preencherJTable(listaEndereco);
		Invisivel();
		desativarBotoes();

		this.comboUf.removeAllItems();
		this.comboUf.addItem("");
		this.comboUf.addItem("AC");
		this.comboUf.addItem("AL");
		this.comboUf.addItem("AM");
		this.comboUf.addItem("AP");
		this.comboUf.addItem("BA");
		this.comboUf.addItem("CE");
		this.comboUf.addItem("DF");
		this.comboUf.addItem("ES");
		this.comboUf.addItem("GO");
		this.comboUf.addItem("MA");
		this.comboUf.addItem("MG");
		this.comboUf.addItem("MS");
		this.comboUf.addItem("MT");
		this.comboUf.addItem("PA");
		this.comboUf.addItem("PB");
		this.comboUf.addItem("PE");
		this.comboUf.addItem("PI");
		this.comboUf.addItem("PR");
		this.comboUf.addItem("RJ");
		this.comboUf.addItem("RN");
		this.comboUf.addItem("RO");
		this.comboUf.addItem("RR");
		this.comboUf.addItem("RS");
		this.comboUf.addItem("SC");
		this.comboUf.addItem("SE");
		this.comboUf.addItem("SP");
		this.comboUf.addItem("TO");

		UsuarioDao daoUsuario = new UsuarioDao();
		if (daoUsuario.consultarNivelLogado() != 3) {
			this.subCadComercio.setEnabled(false);
		}
	}

	public void Invisivel() {
		this.textCep.setEditable(false);
		this.textLogradouro.setEditable(false);
		this.textNumero.setEditable(false);
		this.textBairro.setEditable(false);
		this.comboUf.setEnabled(false);
		this.textCidade.setEditable(false);
		this.textComplemento.setEditable(false);
	}

	public void Visivel() {
		this.textCep.setEditable(true);
		this.textLogradouro.setEditable(true);
		this.textNumero.setEditable(true);
		this.textBairro.setEditable(true);
		this.comboUf.setEnabled(true);
		this.textCidade.setEditable(true);
		this.textComplemento.setEditable(true);
	}

	public void desativarBotoes() {
		this.buttonSalvar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonAlterar.setEnabled(false);
	}

	public void esvasiarCampos() {
		this.textCep.setText("");
		this.textLogradouro.setText("");
		this.textNumero.setText("");
		this.textBairro.setText("");
		this.comboUf.setSelectedItem("");
		this.textCidade.setText("");
		this.textComplemento.setText("");
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
				|| (this.textCep.getText().equals("  .   -   "))
				|| (this.textLogradouro.getText().equals(""))
				|| (this.textBairro.getText().equals(""))
				|| (this.comboUf.getSelectedItem().equals(""))
				|| (this.textCidade.getText().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Endereco> listaEndereco) {
		this.tableInformacaoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(1)
				.setPreferredWidth(50);
		this.tableInformacaoes.getColumnModel().getColumn(2)
				.setPreferredWidth(150);
		this.tableInformacaoes.getColumnModel().getColumn(3)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(4)
				.setPreferredWidth(50);
		this.tableInformacaoes.getColumnModel().getColumn(5)
				.setPreferredWidth(2);
		this.tableInformacaoes.getColumnModel().getColumn(6)
				.setPreferredWidth(50);
		this.tableInformacaoes.getColumnModel().getColumn(7)
				.setPreferredWidth(60);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacaoes
				.getModel();
		tabela.setNumRows(0);

		for (Endereco endereco : listaEndereco) {
			tabela.addRow(new Object[] { endereco.getCodigo_endereco(),
					endereco.getCep(), endereco.getNome_logradouro(),
					endereco.getNumero_logradouro(), endereco.getBairro(),
					endereco.getUf(), endereco.getCidade(),
					endereco.getComplemento() });
		}

		this.tableInformacaoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadEndereco.this.tableInformacaoesTableChanged(e);
					}
				});

	}

	private void Componentes() {
		try {
			this.maskCep = new MaskFormatter("##.###-###");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel inserir a mascara");
		}

		this.textPesquisar = new JTextField();
		this.textCodigo = new JtextFieldSomenteNumeros(7);
		this.textCep = new JFormattedTextField(this.maskCep);
		this.textLogradouro = new JtextFieldSomenteLetrasNumero(100);
		this.textNumero = new JtextFieldSomenteNumeros(4);
		this.textBairro = new JtextFieldSomenteLetrasNumero(50);
		this.comboUf = new JComboBox<>();
		this.textCidade = new JtextFieldSomenteLetras(50);
		this.textComplemento = new JtextFieldSomenteLetrasNumero(100);
		this.buttonPesquisar = new JButton();
		this.buttonNovo = new JButton();
		this.buttonAlterar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAtualizar = new JButton();
		this.buttonSalvar = new JButton();
		this.buttonFechar = new JButton();
		this.labelCodigo = new JLabel();
		this.labelCep = new JLabel();
		this.labelLogradouro = new JLabel();
		this.labelNumero = new JLabel();
		this.labelBairro = new JLabel();
		this.labelUf = new JLabel();
		this.labelCidade = new JLabel();
		this.labelComplemento = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuArquivo = new JMenu();
		this.subNovo = new JMenuItem();
		this.subFechar = new JMenuItem();
		this.menuCadastro = new JMenu();
		this.subCadCliente = new JMenuItem();
		this.subCadComercio = new JMenuItem();
		this.subCadFornecedor = new JMenuItem();
		this.panelPesquisarEndereco = new JPanel();
		this.panelDadosCadastrais = new JPanel();
		this.panelBotoes = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacaoes = new JTable();
		tableInformacaoes.setColumnSelectionAllowed(true);
		this.radioCodigo = new JRadioButton();
		this.radioCep = new JRadioButton();
		this.radioLogradouro = new JRadioButton();
		this.radioNumero = new JRadioButton();
		this.radioBairro = new JRadioButton();
		this.radioUf = new JRadioButton();
		this.radioCidade = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR ENDEREÇOS ----------- //
		this.panelPesquisarEndereco.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Endereços"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCodigo.setText("Código");
		this.radioCodigo.setFont(new Font("Tahoma", 1, 11));
		this.radioCodigo.setActionCommand("codigo_endereco");
		this.radioCodigo.setSelected(true);

		this.radioCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.actionPerformed(evt);
			}
		});

		// Radio CEP.
		this.radioCep.setText("CEP");
		this.radioCep.setFont(new Font("Tahoma", 1, 11));
		this.radioCep.setActionCommand("cep");

		this.radioCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.actionPerformed(evt);
			}
		});

		// Radio Logradouro.
		this.radioLogradouro.setText("Logradouro");
		this.radioLogradouro.setFont(new Font("Tahoma", 1, 11));
		this.radioLogradouro.setActionCommand("nome_logradouro");

		this.radioLogradouro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.actionPerformed(evt);
			}
		});

		// Radio Número.
		this.radioNumero.setText("Número");
		this.radioNumero.setFont(new Font("Tahoma", 1, 11));
		this.radioNumero.setActionCommand("numero_logradouro");

		this.radioNumero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.actionPerformed(evt);
			}
		});

		// Radio Bairro.
		this.radioBairro.setText("Bairro");
		this.radioBairro.setFont(new Font("Tahoma", 1, 11));
		this.radioBairro.setActionCommand("bairro");

		this.radioBairro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.actionPerformed(evt);
			}
		});

		// Radio UF.
		this.radioUf.setText("UF");
		this.radioUf.setFont(new Font("Tahoma", 1, 11));
		this.radioUf.setActionCommand("uf");

		this.radioUf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.actionPerformed(evt);
			}
		});

		// Radio Cidade.
		this.radioCidade.setText("Cidade");
		this.radioCidade.setFont(new Font("Tahoma", 1, 11));
		this.radioCidade.setActionCommand("cidade");

		this.radioCidade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCodigo);
		group.add(this.radioCep);
		group.add(this.radioLogradouro);
		group.add(this.radioNumero);
		group.add(this.radioBairro);
		group.add(this.radioUf);
		group.add(this.radioCidade);

		GroupLayout layoutPesquisarEndereco = new GroupLayout(
				this.panelPesquisarEndereco);
		layoutPesquisarEndereco
				.setHorizontalGroup(layoutPesquisarEndereco
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarEndereco
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarEndereco
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarEndereco
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				444,
																				Short.MAX_VALUE)
																		.addGap(18)
																		.addComponent(
																				buttonPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				211,
																				Short.MAX_VALUE))
														.addGroup(
																layoutPesquisarEndereco
																		.createSequentialGroup()
																		.addComponent(
																				radioCodigo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioCep)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioLogradouro)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioNumero)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioBairro)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioUf)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioCidade)))
										.addContainerGap()));
		layoutPesquisarEndereco
				.setVerticalGroup(layoutPesquisarEndereco
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarEndereco
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarEndereco
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
										.addGap(1)
										.addGroup(
												layoutPesquisarEndereco
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioCodigo)
														.addComponent(radioCep)
														.addComponent(
																radioLogradouro,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																radioNumero,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																radioBairro)
														.addComponent(radioUf)
														.addComponent(
																radioCidade))));
		this.panelPesquisarEndereco.setLayout(layoutPesquisarEndereco);

		// ---------- PANEL DADOS CADASTRAIS ----------- //
		this.panelDadosCadastrais.setBorder(BorderFactory
				.createTitledBorder("Dados Cadastrais"));

		// Codigo.
		this.labelCodigo.setText("Código:");
		this.textCodigo.setFont(new Font("Tahoma", 1, 11));
		this.textCodigo.setEnabled(false);

		// CEP.
		this.labelCep.setText("CEP:");
		this.textCep.setFont(new Font("Tahoma", 1, 11));

		// Logradouro.
		this.labelLogradouro.setText("Logradouro:");
		this.textLogradouro.setFont(new Font("Tahoma", 1, 11));

		// Número.
		this.labelNumero.setText("Número:");
		this.textNumero.setFont(new Font("Tahoma", 1, 11));

		// Bairro.
		this.labelBairro.setText("Bairro:");
		this.textBairro.setFont(new Font("Tahoma", 1, 11));

		// UF.
		this.labelUf.setText("UF:");
		this.comboUf.setFont(new Font("Tahoma", 1, 11));

		// Cidade.
		this.labelCidade.setText("Cidade:");
		this.textCidade.setFont(new Font("Tahoma", 1, 11));

		// Complemento.
		this.labelComplemento.setText("Complemento:");
		this.textComplemento.setFont(new Font("Tahoma", 1, 11));

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
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelNumero)
																						.addComponent(
																								labelCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								54,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								textNumero,
																								65,
																								65,
																								65)
																						.addComponent(
																								textCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								65,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addGroup(
																								layoutDadosCadastrais
																										.createSequentialGroup()
																										.addComponent(
																												labelCep)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textCep,
																												GroupLayout.DEFAULT_SIZE,
																												113,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelLogradouro)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textLogradouro,
																												GroupLayout.DEFAULT_SIZE,
																												256,
																												Short.MAX_VALUE))
																						.addGroup(
																								layoutDadosCadastrais
																										.createSequentialGroup()
																										.addComponent(
																												labelBairro,
																												GroupLayout.DEFAULT_SIZE,
																												48,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textBairro,
																												GroupLayout.DEFAULT_SIZE,
																												146,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelUf)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												comboUf,
																												GroupLayout.PREFERRED_SIZE,
																												67,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelCidade,
																												GroupLayout.DEFAULT_SIZE,
																												60,
																												Short.MAX_VALUE)
																										.addGap(3)
																										.addComponent(
																												textCidade,
																												GroupLayout.PREFERRED_SIZE,
																												129,
																												GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelComplemento)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textComplemento,
																				GroupLayout.PREFERRED_SIZE,
																				299,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		layoutDadosCadastrais
				.setVerticalGroup(layoutDadosCadastrais
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutDadosCadastrais
										.createSequentialGroup()
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.BASELINE,
																								false)
																						.addComponent(
																								labelCep)
																						.addComponent(
																								textCep,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								labelLogradouro)
																						.addComponent(
																								textLogradouro,
																								GroupLayout.PREFERRED_SIZE,
																								18,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addGap(15)
																		.addComponent(
																				labelCodigo)))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelNumero)
														.addComponent(
																labelBairro)
														.addComponent(
																textBairro,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelUf)
														.addComponent(
																comboUf,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelCidade)
														.addComponent(
																textCidade,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textNumero,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelComplemento)
														.addComponent(
																textComplemento,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addGap(17)));
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
				JanCadEndereco.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.buttonFecharActionPerformed(evt);
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
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null } }, new String[] { "Código",
				"CEP", "Logradouro", "Número", "Bairro", "UF", "Cidade",
				"Complemento" }));

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
				JanCadEndereco.this.subNovoActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subNovo);

		this.subFechar.setText("Fechar");
		subFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				SHORTCUT_MASK));
		this.subFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.subFecharActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subFechar);

		this.menuBar.add(this.menuArquivo);

		// Menu Cadastro.
		this.menuCadastro.setMnemonic('C');
		this.menuCadastro.setText("Cadastro");

		// Cliente
		this.subCadCliente.setText("Cliente");
		subCadCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				SHORTCUT_MASK));
		this.subCadCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.subCadClienteActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadCliente);

		// Comércio
		this.subCadComercio.setText("Comércio");
		subCadComercio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				SHORTCUT_MASK));
		this.subCadComercio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.subCadComercioActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadComercio);

		// Fornecedor
		this.subCadFornecedor.setText("Fornecedor");
		subCadFornecedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				SHORTCUT_MASK));
		this.subCadFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadEndereco.this.subCadFornecedorActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadFornecedor);

		this.menuBar.add(this.menuCadastro);

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
														707, Short.MAX_VALUE)
												.addComponent(
														panelDadosCadastrais,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														panelPesquisarEndereco,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														707, Short.MAX_VALUE)
												.addComponent(
														panelBotoes,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														707, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelPesquisarEndereco,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(3)
								.addComponent(panelDadosCadastrais,
										GroupLayout.PREFERRED_SIZE, 135,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelBotoes,
										GroupLayout.DEFAULT_SIZE, 53,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPaneInformacaoes,
										GroupLayout.PREFERRED_SIZE, 122,
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

		Integer codigo_endereco = Integer.parseInt(tableInformacaoes
				.getValueAt(row, 0).toString());
		String cep = tableInformacaoes.getValueAt(row, 1).toString();
		String logradouro = tableInformacaoes.getValueAt(row, 2).toString();
		Integer numero = Integer.parseInt(tableInformacaoes.getValueAt(row, 3)
				.toString());
		String bairro = tableInformacaoes.getValueAt(row, 4).toString();
		String uf = tableInformacaoes.getValueAt(row, 5).toString();
		String cidade = tableInformacaoes.getValueAt(row, 6).toString();
		String complemento = tableInformacaoes.getValueAt(row, 7).toString();

		this.textCodigo.setText(codigo_endereco.toString());
		this.textCep.setText(cep.toString());
		this.textLogradouro.setText(logradouro);
		this.textNumero.setText(numero.toString());
		this.textBairro.setText(bairro.toString());
		this.comboUf.setSelectedItem(uf.toString());
		this.textCidade.setText(cidade.toString());
		this.textComplemento.setText(complemento.toString());

		this.buttonAlterar.setEnabled(true);

		EnderecoDao dao = new EnderecoDao();

		if ((dao.countEnderecoCliente(codigo_endereco) == 0)
				&& (dao.countEnderecoFornecedor(codigo_endereco) == 0)
				&& (dao.countEnderecoComercio(codigo_endereco) == 0)) {
			this.buttonExcluir.setEnabled(true);
		}
	}

	String acao = "codigo_endereco";

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
			EnderecoDao dao = new EnderecoDao();

			List<Endereco> listaEndereco = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaEndereco.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaEndereco = dao.listar();
				preencherJTable(listaEndereco);
			} else {
				preencherJTable(listaEndereco);
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
		EnderecoDao dao = new EnderecoDao();
		this.textCodigo.setText(Long.toString(dao.retornaAutoIncrement()));
		esvasiarCampos();
		this.textCep.grabFocus();
	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum endereço selecionado!");
		} else {
			Visivel();
			desativarBotoes();
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum endereço selecionado!");
		} else {
			Endereco endereco = new Endereco();
			endereco.setCodigo_endereco(Integer.parseInt((this.textCodigo
					.getText())));

			String mensagem = "Deseja excluir o endereço de código "
					+ endereco.getCodigo_endereco() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				EnderecoDao dao = new EnderecoDao();
				dao.remover(endereco);

				List<Endereco> listaEndereco = dao.listar();

				preencherJTable(listaEndereco);

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

			Endereco endereco = new Endereco();
			endereco.setCodigo_endereco(Integer.parseInt((this.textCodigo
					.getText())));
			endereco.setCep(this.textCep.getText());
			endereco.setNome_logradouro(this.textLogradouro.getText());
			endereco.setNumero_logradouro(Integer.parseInt((this.textNumero
					.getText())));
			endereco.setBairro(this.textBairro.getText());
			endereco.setUf(this.comboUf.getSelectedItem().toString());
			endereco.setCidade(this.textCidade.getText());
			endereco.setComplemento(this.textComplemento.getText());

			// Atualiza!!!
			EnderecoDao dao = new EnderecoDao();
			dao.alterar(endereco);

			JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
			List<Endereco> listaEndereco = dao.listar();
			preencherJTable(listaEndereco);

			esvasiarCampos();
			this.textCodigo.setText("");

			Invisivel();

			desativarBotoes();
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Todos os campos são obrigatórios!");
		} else {

			Endereco endereco = new Endereco();

			endereco.setCodigo_endereco(Integer.parseInt((this.textCodigo
					.getText())));
			endereco.setCep(this.textCep.getText());
			endereco.setNome_logradouro(this.textLogradouro.getText());
			try {
				endereco.setNumero_logradouro(Integer.parseInt((this.textNumero
						.getText())));
			} catch (java.lang.NumberFormatException e) {
				throw new RuntimeException(e);
			}
			endereco.setBairro(this.textBairro.getText());
			endereco.setUf(this.comboUf.getSelectedItem().toString());
			endereco.setCidade(this.textCidade.getText());
			endereco.setComplemento(this.textComplemento.getText());

			// grave nessa conexão!!!
			EnderecoDao dao = new EnderecoDao();
			dao.inserir(endereco);

			JOptionPane.showMessageDialog(null,
					"O cadastro do endereco:\n" + this.textCodigo.getText()
							+ " - " + endereco.getNome_logradouro()
							+ "\nFoi realizado com sucesso!");
			List<Endereco> listaEndereco = dao.listar();
			preencherJTable(listaEndereco);

			esvasiarCampos();
			this.textCodigo.setText("");

			Invisivel();

			desativarBotoes();
		}

	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {
		EnderecoDao dao = new EnderecoDao();
		if (dao.count() == 0) {
			JOptionPane.showMessageDialog(null, "Cadastre um endereço");
		} else {
			switch (this.nome) {
			case "Cliente":
				dispose();
				JanCadCliente cadC = new JanCadCliente("principal");
				cadC.setVisible(true);
				break;

			case "Fornecedor":
				dispose();
				JanCadFornecedor cadF = new JanCadFornecedor("principal");
				cadF.setVisible(true);
				break;

			case "Comercio":
				dispose();
				JanCadComercio cadCo = new JanCadComercio("principal");
				cadCo.setVisible(true);
				break;

			case "Comércio":
				dispose();
				JanCadComercio cadCom = new JanCadComercio("cargo");
				cadCom.setVisible(true);
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

	// JMenuItemCliente
	private void subCadClienteActionPerformed(ActionEvent evt) {
		EnderecoDao dao = new EnderecoDao();
		if (dao.count() == 0) {
			JOptionPane.showMessageDialog(null, "Cadastre um endereço");
		} else {

			dispose();
			JanCadCliente cadC = new JanCadCliente("endereco");
			cadC.setVisible(true);
		}

	}

	// JMenuItemComercio
	private void subCadComercioActionPerformed(ActionEvent evt) {

		EnderecoDao dao = new EnderecoDao();
		if (dao.count() == 0) {
			JOptionPane.showMessageDialog(null, "Cadastre um endereço");
		} else {
			dispose();
			JanCadComercio cadC = new JanCadComercio("endereco");
			cadC.setVisible(true);
		}
	}

	// JMenuItemFornecedor
	private void subCadFornecedorActionPerformed(ActionEvent evt) {
		EnderecoDao dao = new EnderecoDao();
		if (dao.count() == 0) {
			JOptionPane.showMessageDialog(null, "Cadastre um endereço");
		} else {
			dispose();
			JanCadFornecedor cadU = new JanCadFornecedor("endereco");
			cadU.setVisible(true);
		}

	}
}
