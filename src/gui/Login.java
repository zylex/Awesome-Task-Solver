package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

import domain.DomainController;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class displays the login window.
 */
public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7008029056813980973L;
	private JPanel contentPane;
	private JTextField textFieldLogin;
	private JLabel lPassword;
	private JPasswordField passwordField;
	private DomainController dc;

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldLogin = new JTextField();
		textFieldLogin.setBounds(12, 27, 114, 19);
		contentPane.add(textFieldLogin);
		textFieldLogin.setColumns(10);

		JLabel lLogin = new JLabel("User ID:");
		lLogin.setBounds(12, 0, 70, 15);
		contentPane.add(lLogin);

		lPassword = new JLabel("Password:");
		lPassword.setBounds(12, 58, 114, 15);
		contentPane.add(lPassword);

		final JButton button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				button.setEnabled(false);

				if (textFieldLogin.getText().equals("") || passwordField.getPassword().length < 1) {
					JOptionPane.showMessageDialog(null, "Not enough information entered!", "Login Info", JOptionPane.ERROR_MESSAGE);
				} else {
					int userId = Integer.parseInt(textFieldLogin.getText());
					String pass = "";
					char[] p = passwordField.getPassword();
					for (int i = 0; i < p.length; i++) {
						pass += p[i];
						p[i] = 0; // security
					}
					if (dc.login(userId, pass)) {
						contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						dc.initAfterLogin();
						dc.setCurrentUser(dc.getUser(userId));
						setVisible(false);
						try {
							TabSolver ts = new TabSolver(dc);
							ts.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						button.setEnabled(true);
						JOptionPane.showMessageDialog(null, "Login information is incorrect!", "Login Info", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		button.setBounds(149, 82, 117, 25);
		contentPane.add(button);

		passwordField = new JPasswordField();
		passwordField.setBounds(12, 85, 114, 19);
		contentPane.add(passwordField);
	}

	public void setDc(DomainController dc) {
		this.dc = dc;
	}

}
