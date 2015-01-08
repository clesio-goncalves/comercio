package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import jdbc.dao.ComercioDao;
import jdbc.dao.FuncionarioDao;
import jdbc.dao.UsuarioDao;
import bean.Usuario;

public class JanLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelLogin;
	private JLabel labelUsuario;
	private JLabel labelSenha;
	private JTextField textUsuario;
	private JPasswordField passwordSenha;
	private JButton buttonEntrar;
	private JButton buttonCancelar;

	public JanLogin() {
		super("Login");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);
	}

	private void Componentes() {
		this.panelLogin = new JPanel();
		this.labelUsuario = new JLabel();
		this.labelSenha = new JLabel();
		this.textUsuario = new JTextField();
		this.passwordSenha = new JPasswordField();
		this.buttonCancelar = new JButton();
		this.buttonEntrar = new JButton();

		setDefaultCloseOperation(3);

		this.panelLogin.setBorder(BorderFactory
				.createTitledBorder("Login no Sistema"));

		// usuário
		this.labelUsuario.setText("Usuário:");
		this.labelUsuario.setFont(new Font("Tahoma", 1, 11));
		this.textUsuario.setFont(new Font("Tahoma", 1, 11));

		// Senha
		this.labelSenha.setText("Senha:");
		this.labelSenha.setFont(new Font("Tahoma", 1, 11));
		this.passwordSenha.setFont(new Font("Tahoma", 1, 11));

		// Button Cancelar
		this.buttonCancelar.setBackground(new Color(255, 255, 255));
		this.buttonCancelar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/cancelar.png")));
		this.buttonCancelar.setText("Cancelar");
		this.buttonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

		// Button Entrar
		this.buttonEntrar.setBackground(new Color(255, 255, 255));
		this.buttonEntrar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/entrar.png")));
		this.buttonEntrar.setText("Entrar");
		this.buttonEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanLogin.this.buttonEntrarActionPerformed(evt);
			}
		});
		getRootPane().setDefaultButton(buttonEntrar);

		GroupLayout gl_panelLogin = new GroupLayout(panelLogin);
		gl_panelLogin
				.setHorizontalGroup(gl_panelLogin
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panelLogin
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelLogin
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_panelLogin
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelLogin
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelSenha)
																						.addComponent(
																								labelUsuario))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelLogin
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								textUsuario,
																								GroupLayout.DEFAULT_SIZE,
																								129,
																								Short.MAX_VALUE)
																						.addComponent(
																								passwordSenha,
																								GroupLayout.DEFAULT_SIZE,
																								129,
																								Short.MAX_VALUE)))
														.addGroup(
																gl_panelLogin
																		.createSequentialGroup()
																		.addComponent(
																				buttonCancelar,
																				GroupLayout.PREFERRED_SIZE,
																				130,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				buttonEntrar)))
										.addContainerGap()));
		gl_panelLogin
				.setVerticalGroup(gl_panelLogin
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelLogin
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelLogin
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelUsuario)
														.addComponent(
																textUsuario,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												gl_panelLogin
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelSenha)
														.addComponent(
																passwordSenha,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED, 18,
												Short.MAX_VALUE)
										.addGroup(
												gl_panelLogin
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																buttonEntrar)
														.addComponent(
																buttonCancelar))
										.addContainerGap()));
		panelLogin.setLayout(gl_panelLogin);

		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.TRAILING).addGroup(
						layout.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panelLogin,
										GroupLayout.PREFERRED_SIZE, 315,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelLogin,
										GroupLayout.DEFAULT_SIZE, 144,
										Short.MAX_VALUE).addContainerGap()));
		getContentPane().setLayout(layout);
		pack();
	}

	private void buttonEntrarActionPerformed(ActionEvent evt) {
		if ((this.textUsuario.getText().equals(""))
				|| (this.passwordSenha.getText().equals(""))) {
			JOptionPane.showMessageDialog(null,
					"Todos os campos são obrigatórios!");
		} else {
			Usuario usuario = new Usuario();

			// Login
			usuario.setNome_usuario(this.textUsuario.getText());
			usuario.setSenha_usuario(this.passwordSenha.getText());

			// UsuarioDao
			UsuarioDao dao = new UsuarioDao();

			if (dao.login(usuario)) {
				if (dao.usuarioFuncionario(usuario)) {
					if (dao.ativoFuncionario(usuario)) {
						if (dao.ativo(usuario)) {
							dispose();
							JanPrincipal janela = new JanPrincipal();
							janela.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null,
									"Usuário inativo");
							funcao();
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"O usuário pertence a um funcionário inativo");
						funcao();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Usuário não pertence a nenhum funcionário");
					funcao();
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Usuário ou Senha inválidos");
				funcao();
			}
		}
	}

	private void funcao() {
		this.textUsuario.setText("");
		this.passwordSenha.setText("");
		this.textUsuario.grabFocus();
	}

	public static void main(String[] args) throws SQLException {
		// UsuarioDao
		UsuarioDao daoUsuario = new UsuarioDao();

		// ComercioDao
		ComercioDao daoComercio = new ComercioDao();

		// FuncionarioDao
		FuncionarioDao daoFuncionario = new FuncionarioDao();

		if (daoUsuario.count() > 0) {
			if (daoComercio.count() > 0) {
				if (daoFuncionario.countFuncionarioAtivoUsuarioAtivo3() > 0) {
					JanLogin jl = new JanLogin();
					jl.setVisible(true);
				} else {
					JanCadFuncionario CadC = new JanCadFuncionario("principal");
					CadC.setVisible(true);
				}
			} else {
				JanCadComercio CadC = new JanCadComercio("principal");
				CadC.setVisible(true);
			}

		} else {
			JanCadUsuario CadU = new JanCadUsuario("login");
			CadU.setVisible(true);
		}
	}
}
