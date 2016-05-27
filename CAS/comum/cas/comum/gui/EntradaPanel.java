package cas.comum.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EntradaPanel extends JPanel {
	private JTextField formLogin;
	private JTextField formSenha;

	/**
	 * Create the panel.
	 */
	public EntradaPanel() {
		setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(133, 14, 1, 1);
		add(layeredPane);
		
		JLabel Login = new JLabel("Login:");
		Login.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Login.setBounds(100, 26, 57, 14);
		add(Login);
		
		formLogin = new JTextField();
		formLogin.setBounds(167, 23, 116, 20);
		add(formLogin);
		formLogin.setColumns(10);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(98, 69, 59, 14);
		add(lblSenha);
		
		formSenha = new JTextField();
		formSenha.setColumns(10);
		formSenha.setBounds(167, 66, 116, 20);
		add(formSenha);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuFrame frame = new MenuFrame();
				frame.setVisible(true);				
			}
		});
		btnEntrar.setBounds(167, 116, 116, 23);
		add(btnEntrar);

	}
}
