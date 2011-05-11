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
	private JButton button;

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

		button = new JButton("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				disableForm();
				if (textFieldLogin.getText().equals("") || passwordField.getPassword().length < 1) {
					JOptionPane.showMessageDialog(null, "You have to fill out all fields!", "Login Error", JOptionPane.ERROR_MESSAGE);
					resetForm();
				} else {
					try {
						int userId = Integer.parseInt(textFieldLogin.getText());
						String pass = "";
						char[] p = passwordField.getPassword();
						for (int i = 0; i < p.length; i++) {
							pass += p[i];
							p[i] = 0; // security
						}
						if (dc.login(userId, pass)) {
							resetForm();
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
							JOptionPane.showMessageDialog(null, "Login information is incorrect!", "Login Error", JOptionPane.ERROR_MESSAGE);
							resetForm();
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "UserID has to be a number!", "Login Info", JOptionPane.ERROR_MESSAGE);
						resetForm();
					}

				}
			}
		});
		button.setBounds(149, 82, 117, 25);
		contentPane.add(button);
		
		getRootPane().setDefaultButton(button);

		passwordField = new JPasswordField();
		passwordField.setBounds(12, 85, 114, 19);
		contentPane.add(passwordField);
	}
	
	public void disableForm() {
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		passwordField.setEditable(false);
		textFieldLogin.setEditable(false);
		redrawEverything();
	}

	public void resetForm() {
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		passwordField.setEditable(true);
		textFieldLogin.setEditable(true);
		passwordField.setText("");
		textFieldLogin.setText("");
		redrawEverything();
	}
	
	public void redrawEverything() {
		passwordField.revalidate();
		textFieldLogin.revalidate();
		button.revalidate();
		contentPane.revalidate();
	}

	public void setDc(DomainController dc) {
		this.dc = dc;
	}

}
