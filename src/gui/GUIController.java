package gui;

import javax.swing.UIManager;

import domain.DomainController;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class creates the DomainController and launches the application.
 */
public class GUIController {

	private static DomainController dc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		dc = DomainController.getInstance();
		
		try  
		{  
		  //Tell the UIManager to use the platform look and feel  
		  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
		}  
		catch(Exception e)  
		{  
		  //Do nothing  
		} 

		try {
			Login frame = new Login();
			frame.setDc(dc);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}