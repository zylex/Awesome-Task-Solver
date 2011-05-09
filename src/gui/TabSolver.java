package gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.text.JTextComponent;
import domain.DomainController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class displays the main window of the application.
 */
public class TabSolver extends JFrame {

	private DomainController dc;
	private Font font = new Font("Monospaced", Font.PLAIN, 10);

	private static final long serialVersionUID = 1L;
	private JTabbedPane tasksolverGuiPane = null;
	private JPanel taskOwnerPanel = null;
	private JPanel taskManagerPanel = null;
	private JButton showTaskButtonTO = null;
	private JLabel tasknbLabel = null;
	private JTextField taskNumberTextField = null;
	private JTextPane displayTextArea = null;
	private JButton showTaskBidButtonTO = null;
	private JLabel taskbidnbLabel1 = null;
	private JTextField taskBidNumberTextField1 = null;
	private JButton taskWinnerjButtonTO = null;
	private JLabel winnjLabel = null;
	private JTextField taskBidNumberTextField11 = null;
	private JButton createTaskButtonTO = null;
	private JLabel taskidLabel = null;
	private JTextField taskidjTextField = null;
	private JLabel budgetLabel = null;
	private JTextField budgetTextField = null;
	private JLabel dateoutLabel1 = null;
	private JTextField dateBidStopTextField11 = null;
	private JLabel tasknameLabel = null;
	private JTextField tasknamejTextField = null;
	private JLabel taskDescriptionLabel = null;
	private JTextField taskDescriptionTextField = null;
	private JTextArea feedbackjTextArea = null;
	private JPanel taskSolverPanel = null;
	private JPanel hrPanel = null;
	private JButton showTaskButtonTM = null;
	private JLabel tasknbLabel1 = null;
	private JTextField taskNumberTextField1 = null;
	private JButton showsubtaskButtonTM = null;
	private JButton showsubtaskbidButtonTM = null;
	private JLabel subtasknbLabel11 = null;
	private JTextField subtaskNumberTextField11 = null;
	private JLabel subtaskBidNbLabel111 = null;
	private JTextField subtaskBidNbTextField111 = null;
	private JTextArea jTextArea = null;
	private JScrollBar jScrollBar = null;
	private JButton showTaskBidButtonTM = null;
	private JLabel taskbidnbLabel11 = null;
	private JTextField taskBidNumberTextField13 = null;
	private JButton enterTaskBidButton = null;
	private JButton subtaskWinnerButtonTM = null;
	private JButton createSubtaskButtonTM = null;
	private JTextArea feedbackTextArea1 = null;
	private JLabel subTaskLabel1 = null;
	private JTextField subTskNumberTextField1 = null;
	private JLabel TaskLabel1 = null;
	private JTextField TskNumberTextField1 = null;
	private JLabel dateEndSubtLabel = null;
	private JTextField dateEndSubtTextField = null;
	private JLabel offer1Label = null;
	private JLabel subTskNameLabel = null;
	private JLabel subdescpjLabel = null;
	private JTextField budgtjTextField = null;
	private JTextField subnamejTextField = null;
	private JTextField subdescrpTextField = null;
	private JLabel winnjLabel1 = null;
	private JTextField subtaskBidWinnerNbTextField1111 = null;
	private JLabel hoursLabel1 = null;
	private JButton showTaskButtonTS = null;
	private JLabel tasknbLabel11 = null;
	private JTextField taskNumberTextField11 = null;
	private JButton showsubtaskButtonTS = null;
	private JLabel subtasknbLabel111 = null;
	private JTextField subtaskNumberTextField111 = null;
	private JButton showsubtaskbidButtonTS = null;
	private JLabel subtaskBidNbLabel1111 = null;
	private JTextField subtaskBidNbTextField1111 = null;
	private JTextArea jTextArea1 = null;
	private JButton enterSubtskBidButtonTS = null;
	private JLabel subtasknbLabel1111 = null;
	private JTextField subtaskNumberTextField1111 = null;
	private JLabel offerLabel1 = null;
	private JTextField OfferTextField1 = null;
	private JLabel hoursLabel2 = null;
	private JTextArea jTextArea11 = null;
	private JButton employeeReportButton = null;
	private JLabel employeenmejLabel = null;
	private JTextArea employeeNameTextArea2 = null;
	private JTextArea feedbackTextArea11 = null;
	private JTextArea feedbackTextArea12 = null;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_12;
	private JTextField addUserToGroupTextField;
	private JTextArea groupTextArea;
	private JButton newGroupButton;
	private JTextField taskIDWinnerTextField;
	private JTextField textField_14;
	private JButton btnShowMyTasks;
	private JPanel adminPanel;
	private JTextField solveSubtaskTextField;

	private boolean checkForFilledFields(ArrayList<JTextComponent> components) {
		int size = components.size();
		for (int i = 0; i < size; i++) {
			if (components.get(i).getText().equals("")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method initializes jTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getTasksolverGuiPane() {
		if (tasksolverGuiPane == null) {
			tasksolverGuiPane = new JTabbedPane();
			tasksolverGuiPane.addTab("Task Owner", null, getTaskOwnerPanel(),
					null);
			tasksolverGuiPane.addTab("Task Manager", null,
					getTaskManagerPanel(), null);
			tasksolverGuiPane.addTab("Task Solver", null, getTaskSolverPanel(),
					null);
			tasksolverGuiPane.addTab("HR", null, getHrPanel(), null);

			adminPanel = new JPanel();
			tasksolverGuiPane.addTab("Administrator", null, adminPanel, null);
			adminPanel.setLayout(null);

			final JTextPane textPane = new JTextPane();
			textPane.setBounds(270, 11, 647, 300);
			textPane.setFont(font);
			adminPanel.add(textPane);

			JButton showAllUsersButton = new JButton("Show All Users");
			showAllUsersButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					textPane.setText(dc.showAllUsers());
				}
			});
			showAllUsersButton.setBounds(732, 322, 185, 23);
			adminPanel.add(showAllUsersButton);

			JLabel lblCreateUser = new JLabel("Create User:");
			lblCreateUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblCreateUser.setBounds(10, 11, 139, 23);
			adminPanel.add(lblCreateUser);

			JLabel lblNmae = new JLabel("Name:");
			lblNmae.setBounds(52, 32, 78, 14);
			adminPanel.add(lblNmae);

			JLabel lblPassword = new JLabel("Password:");
			lblPassword.setBounds(22, 58, 87, 14);
			adminPanel.add(lblPassword);

			JLabel lblLocation = new JLabel("Location:");
			lblLocation.setBounds(32, 83, 106, 14);
			adminPanel.add(lblLocation);

			JLabel lblSecurityLayer = new JLabel("Security:");
			lblSecurityLayer.setBounds(33, 108, 67, 14);
			adminPanel.add(lblSecurityLayer);

			textField_1 = new JTextField();
			textField_1.setBounds(97, 31, 86, 20);
			adminPanel.add(textField_1);
			textField_1.setColumns(10);

			textField_2 = new JTextField();
			textField_2.setBounds(97, 56, 86, 20);
			adminPanel.add(textField_2);
			textField_2.setColumns(10);

			textField_3 = new JTextField();
			textField_3.setBounds(97, 81, 86, 20);
			adminPanel.add(textField_3);
			textField_3.setColumns(10);

			textField_4 = new JTextField();
			textField_4.setBounds(97, 106, 86, 20);
			adminPanel.add(textField_4);
			textField_4.setColumns(10);

			JLabel lblEditUser = new JLabel("Edit User:");
			lblEditUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblEditUser.setBounds(10, 162, 110, 23);
			adminPanel.add(lblEditUser);

			JLabel lblUserId = new JLabel("User Id:");
			lblUserId.setBounds(42, 183, 67, 14);
			adminPanel.add(lblUserId);

			JLabel lblName = new JLabel("Name:");
			lblName.setBounds(52, 208, 46, 14);
			adminPanel.add(lblName);

			JLabel lblPassword_1 = new JLabel("Password:");
			lblPassword_1.setBounds(22, 233, 88, 14);
			adminPanel.add(lblPassword_1);

			JLabel lblLocation_1 = new JLabel("Location:");
			lblLocation_1.setBounds(32, 259, 79, 14);
			adminPanel.add(lblLocation_1);

			JLabel lblSecurityLayer_1 = new JLabel("Security:");
			lblSecurityLayer_1.setBounds(32, 285, 86, 14);
			adminPanel.add(lblSecurityLayer_1);

			textField_5 = new JTextField();
			textField_5.setBounds(97, 181, 86, 20);
			adminPanel.add(textField_5);
			textField_5.setColumns(10);

			textField_6 = new JTextField();
			textField_6.setBounds(97, 207, 86, 20);
			adminPanel.add(textField_6);
			textField_6.setColumns(10);

			textField_7 = new JTextField();
			textField_7.setBounds(97, 231, 86, 20);
			adminPanel.add(textField_7);
			textField_7.setColumns(10);

			textField_8 = new JTextField();
			textField_8.setBounds(97, 256, 86, 20);
			adminPanel.add(textField_8);
			textField_8.setColumns(10);

			textField_9 = new JTextField();
			textField_9.setBounds(97, 281, 86, 20);
			adminPanel.add(textField_9);
			textField_9.setColumns(10);

			JButton createUserButton = new JButton("Ok");
			createUserButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (textField_1.getText().equals("")
							|| textField_2.getText().equals("")
							|| textField_3.getText().equals("")
							|| textField_4.getText().equals("")) {
						feedbackjTextArea.setText("Not enough values.");
					} else {
						textPane.setText(dc.createUsers(
								Integer.parseInt(textField_4.getText()),
								textField_1.getText(), textField_2.getText(),
								textField_3.getText()));
					}
				}
			});
			createUserButton.setBounds(128, 137, 110, 23);
			adminPanel.add(createUserButton);

			JButton editUserButton = new JButton("Ok");
			editUserButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					ArrayList<JTextComponent> textFields = new ArrayList<JTextComponent>();
					textFields.add(textField_5);
					textFields.add(textField_6);
					textFields.add(textField_7);
					textFields.add(textField_8);
					textFields.add(textField_9);
					if (checkForFilledFields(textFields)) {
						textPane.setText(dc.editUser(
								Integer.parseInt(textField_5.getText()),
								textField_6.getText(), textField_7.getText(),
								textField_8.getText(),
								Integer.parseInt(textField_9.getText())));
					} else
						textPane.setText("You have not filled enough fields!");
				}
			});
			editUserButton.setBounds(128, 312, 110, 23);
			adminPanel.add(editUserButton);

			JScrollBar scrollBar = new JScrollBar();
			scrollBar.setBounds(843, 11, 17, 300);
			adminPanel.add(scrollBar);
		}
		return tasksolverGuiPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	public JPanel getTaskOwnerPanel() {
		if (taskOwnerPanel == null) {
			taskDescriptionLabel = new JLabel();
			taskDescriptionLabel.setBounds(new Rectangle(379, 337, 127, 21));
			taskDescriptionLabel.setText("Task Description:");
			tasknameLabel = new JLabel();
			tasknameLabel.setBounds(new Rectangle(416, 311, 100, 21));
			tasknameLabel.setText("Task Name:");
			dateoutLabel1 = new JLabel();
			dateoutLabel1.setBounds(new Rectangle(226, 368, 66, 17));
			dateoutLabel1.setText("Bid ends:");
			budgetLabel = new JLabel();
			budgetLabel.setBounds(new Rectangle(234, 339, 58, 17));
			budgetLabel.setText("Budget:");
			taskidLabel = new JLabel();
			taskidLabel.setBounds(new Rectangle(240, 313, 58, 17));
			taskidLabel.setText("TaskId:");
			winnjLabel = new JLabel();
			winnjLabel.setBounds(new Rectangle(9, 394, 164, 18));
			winnjLabel.setText("Winner Task Bid ID:");
			taskbidnbLabel1 = new JLabel();
			taskbidnbLabel1.setBounds(new Rectangle(9, 243, 118, 18));
			taskbidnbLabel1.setText(" Task Bid ID:");
			tasknbLabel = new JLabel();
			tasknbLabel.setBounds(new Rectangle(39, 63, 118, 18));
			tasknbLabel.setText("Task ID:");
			taskOwnerPanel = new JPanel();
			taskOwnerPanel.setLayout(null);
			taskOwnerPanel.add(getShowTaskButtonTO(), null);
			taskOwnerPanel.add(tasknbLabel, null);
			taskOwnerPanel.add(getTaskNumberTextField(), null);
			taskOwnerPanel.add(getDisplayTextArea(), null);
			taskOwnerPanel.add(getShowTaskBidButtonTO(), null);
			taskOwnerPanel.add(taskbidnbLabel1, null);
			taskOwnerPanel.add(getTaskBidNumberTextField1(), null);
			taskOwnerPanel.add(getTaskWinnerjButtonTO(), null);
			taskOwnerPanel.add(winnjLabel, null);
			taskOwnerPanel.add(getTaskBidNumberTextField11(), null);
			taskOwnerPanel.add(getCreateTaskButtonTO(), null);
			taskOwnerPanel.add(taskidLabel, null);
			taskOwnerPanel.add(getTaskidjTextField(), null);
			taskOwnerPanel.add(budgetLabel, null);
			taskOwnerPanel.add(getBudgetTextField(), null);
			taskOwnerPanel.add(dateoutLabel1, null);
			taskOwnerPanel.add(getDateBidStopTextField11(), null);
			taskOwnerPanel.add(tasknameLabel, null);
			taskOwnerPanel.add(getTasknamejTextField(), null);
			taskOwnerPanel.add(taskDescriptionLabel, null);
			taskOwnerPanel.add(getTaskDescriptionTextField(), null);
			taskOwnerPanel.add(getFeedbackjTextArea(), null);

			JLabel lblShowTask = new JLabel("Show Task:");
			lblShowTask.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowTask.setBounds(9, 38, 90, 14);
			taskOwnerPanel.add(lblShowTask);

			JLabel lblShowTaskBids = new JLabel("Show Task Bids:");
			lblShowTaskBids.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowTaskBids.setBounds(9, 218, 148, 14);
			taskOwnerPanel.add(lblShowTaskBids);

			JLabel lblSelectTaskAuction = new JLabel(
					"Select Task Auction Winner:");
			lblSelectTaskAuction.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSelectTaskAuction.setBounds(9, 368, 232, 14);
			taskOwnerPanel.add(lblSelectTaskAuction);

			JLabel lblCreateTask = new JLabel("Create Task:");
			lblCreateTask.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblCreateTask.setBounds(226, 288, 118, 14);
			taskOwnerPanel.add(lblCreateTask);

			JButton showAllTasksButtonTO = new JButton("Show All Tasks");
			showAllTasksButtonTO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					displayTextArea.setText(dc.showAllTasks());
				}
			});
			showAllTasksButtonTO.setBounds(723, 265, 164, 26);
			taskOwnerPanel.add(showAllTasksButtonTO);

			JButton editTaskButtonTO = new JButton("Edit");
			editTaskButtonTO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ArrayList<JTextComponent> textFields = new ArrayList<JTextComponent>();
					textFields.add(taskidjTextField);
					textFields.add(tasknamejTextField);
					textFields.add(taskDescriptionTextField);
					textFields.add(budgetTextField);
					textFields.add(dateBidStopTextField11);
					if (checkForFilledFields(textFields)) {
						String display = dc.editTask(
								Integer.parseInt(taskidjTextField.getText()),
								tasknamejTextField.getText(),
								taskDescriptionTextField.getText(),
								Integer.parseInt(budgetTextField.getText()),
								Date.valueOf(dateBidStopTextField11.getText()));
						feedbackjTextArea.setText(display);
						displayTextArea.setText(display);
					} else {
						displayTextArea.setText("Not enough values.");
						feedbackjTextArea.setText("Not enough values.");
					}
				}
			});
			editTaskButtonTO.setBounds(622, 491, 110, 26);
			taskOwnerPanel.add(editTaskButtonTO);

			JScrollBar scrollBar = new JScrollBar();
			scrollBar.setBounds(870, 13, 17, 233);
			taskOwnerPanel.add(scrollBar);

			JLabel label = new JLabel("Task ID:");
			label.setBounds(9, 424, 125, 18);
			taskOwnerPanel.add(label);

			taskIDWinnerTextField = new JTextField();
			taskIDWinnerTextField.setBounds(148, 414, 90, 26);
			taskOwnerPanel.add(taskIDWinnerTextField);
			taskIDWinnerTextField.setColumns(10);
			taskOwnerPanel.add(getBtnShowMyTasks());
		}
		return taskOwnerPanel;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTaskManagerPanel() {
		if (taskManagerPanel == null) {
			hoursLabel1 = new JLabel();
			hoursLabel1.setBounds(new Rectangle(415, 433, 72, 20));
			hoursLabel1.setText("Hours");
			winnjLabel1 = new JLabel();
			winnjLabel1.setBounds(new Rectangle(4, 460, 166, 16));
			winnjLabel1.setText("Winner Subtask Bid ID:");
			subdescpjLabel = new JLabel();
			subdescpjLabel.setBounds(new Rectangle(440, 384, 110, 20));
			subdescpjLabel.setText("Description:");
			subTskNameLabel = new JLabel();
			subTskNameLabel.setBounds(new Rectangle(430, 360, 129, 20));
			subTskNameLabel.setText("Subtask Name:");
			offer1Label = new JLabel();
			offer1Label.setBounds(new Rectangle(225, 433, 71, 20));
			offer1Label.setText("Budget:");
			dateEndSubtLabel = new JLabel();
			dateEndSubtLabel.setBounds(new Rectangle(225, 410, 71, 20));
			dateEndSubtLabel.setText("Bid End:");
			TaskLabel1 = new JLabel();
			TaskLabel1.setBounds(new Rectangle(225, 360, 71, 20));
			TaskLabel1.setText("Task ID:");
			subTaskLabel1 = new JLabel();
			subTaskLabel1.setBounds(new Rectangle(225, 384, 88, 20));
			subTaskLabel1.setText("SubTask ID:");
			taskbidnbLabel11 = new JLabel();
			taskbidnbLabel11.setBounds(new Rectangle(28, 136, 126, 16));
			taskbidnbLabel11.setText("Task Bid ID:");
			subtaskBidNbLabel111 = new JLabel();
			subtaskBidNbLabel111.setBounds(new Rectangle(10, 336, 114, 16));
			subtaskBidNbLabel111.setText("Subtask Bid ID:");
			subtasknbLabel11 = new JLabel();
			subtasknbLabel11.setBounds(new Rectangle(28, 225, 114, 16));
			subtasknbLabel11.setText("Subtask ID:");
			tasknbLabel1 = new JLabel();
			tasknbLabel1.setBounds(new Rectangle(44, 46, 126, 16));
			tasknbLabel1.setText(" Task ID:");
			taskManagerPanel = new JPanel();
			taskManagerPanel.setLayout(null);
			taskManagerPanel.add(getShowTaskButtonTM(), null);
			taskManagerPanel.add(tasknbLabel1, null);
			taskManagerPanel.add(getTaskNumberTextField1(), null);
			taskManagerPanel.add(getShowsubtaskButtonTM(), null);
			taskManagerPanel.add(getShowsubtaskbidButtonTM(), null);
			taskManagerPanel.add(subtasknbLabel11, null);
			taskManagerPanel.add(getSubtaskNumberTextField11(), null);
			taskManagerPanel.add(subtaskBidNbLabel111, null);
			taskManagerPanel.add(getSubtaskBidNbTextField111(), null);
			taskManagerPanel.add(getJTextArea(), null);
			taskManagerPanel.add(getJScrollBar(), null);
			taskManagerPanel.add(getShowTaskBidButtonTM(), null);
			taskManagerPanel.add(taskbidnbLabel11, null);
			taskManagerPanel.add(getTaskBidNumberTextField13(), null);
			taskManagerPanel.add(getEnterTaskBidButton(), null);
			taskManagerPanel.add(getSubtaskWinnerButtonTM(), null);
			taskManagerPanel.add(getCreateSubtaskButtonTM(), null);
			taskManagerPanel.add(getFeedbackTextArea1(), null);
			taskManagerPanel.add(subTaskLabel1, null);
			taskManagerPanel.add(getSubTskNumberTextField1(), null);
			taskManagerPanel.add(TaskLabel1, null);
			taskManagerPanel.add(getTskNumberTextField1(), null);
			taskManagerPanel.add(dateEndSubtLabel, null);
			taskManagerPanel.add(getDateEndSubtTextField(), null);
			taskManagerPanel.add(offer1Label, null);
			taskManagerPanel.add(subTskNameLabel, null);
			taskManagerPanel.add(subdescpjLabel, null);
			taskManagerPanel.add(getBudgtjTextField(), null);
			taskManagerPanel.add(getSubnamejTextField(), null);
			taskManagerPanel.add(getSubdescrpTextField(), null);
			taskManagerPanel.add(winnjLabel1, null);
			taskManagerPanel.add(getSubtaskBidWinnerNbTextField1111(), null);
			taskManagerPanel.add(hoursLabel1, null);

			JLabel lblShowTask_2 = new JLabel("Show Task:");
			lblShowTask_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowTask_2.setBounds(10, 21, 135, 14);
			taskManagerPanel.add(lblShowTask_2);

			JLabel lblShowTaskBids_1 = new JLabel("Show Task Bid:");
			lblShowTaskBids_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowTaskBids_1.setBounds(10, 110, 132, 14);
			taskManagerPanel.add(lblShowTaskBids_1);

			JLabel lblShowSubtask_1 = new JLabel("Show Subtask:");
			lblShowSubtask_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowSubtask_1.setBounds(10, 201, 114, 14);
			taskManagerPanel.add(lblShowSubtask_1);

			JLabel lblShowSubtaskBid_1 = new JLabel("Show Subtask Bid:");
			lblShowSubtaskBid_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowSubtaskBid_1.setBounds(10, 311, 180, 14);
			taskManagerPanel.add(lblShowSubtaskBid_1);

			JLabel lblSelectSubtaskWinner = new JLabel("Select Subtask Winner:");
			lblSelectSubtaskWinner.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblSelectSubtaskWinner.setBounds(10, 436, 219, 14);
			taskManagerPanel.add(lblSelectSubtaskWinner);

			JLabel lblCreateSubtask = new JLabel("Create Subtask:");
			lblCreateSubtask.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblCreateSubtask.setBounds(235, 335, 124, 14);
			taskManagerPanel.add(lblCreateSubtask);

			JButton showAllSubtasksButtonTM = new JButton("Show All Subtasks");
			showAllSubtasksButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jTextArea.setText(dc.showAllSubtasks());
				}
			});
			showAllSubtasksButtonTM.setBounds(544, 324, 184, 23);
			taskManagerPanel.add(showAllSubtasksButtonTM);

			JButton showAllTasksButtonTM = new JButton("Show All Tasks");
			showAllTasksButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					jTextArea.setText(dc.showAllTasks());
				}
			});
			showAllTasksButtonTM.setBounds(369, 323, 161, 23);
			taskManagerPanel.add(showAllTasksButtonTM);

			JButton editSubtaskButtonTM = new JButton("Edit");
			editSubtaskButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ArrayList<JTextComponent> textFields = new ArrayList<JTextComponent>();
					textFields.add(subTskNumberTextField1);
					textFields.add(dateEndSubtTextField);
					textFields.add(budgtjTextField);
					textFields.add(subnamejTextField);
					textFields.add(subdescrpTextField);
					if (checkForFilledFields(textFields)) {
						feedbackTextArea1.setText(dc.editSubTask(Integer
								.parseInt(subTskNumberTextField1.getText()),
								subnamejTextField.getText(), subdescrpTextField
										.getText(), Integer
										.parseInt(budgtjTextField.getText()),
								Date.valueOf(dateEndSubtTextField.getText())));
					} else {
						feedbackTextArea1.setText("Fill all fields!");
					}
				}
			});
			editSubtaskButtonTM.setBounds(415, 488, 110, 26);
			taskManagerPanel.add(editSubtaskButtonTM);

			JScrollBar scrollBar_1 = new JScrollBar();
			scrollBar_1.setBounds(866, 15, 17, 291);
			taskManagerPanel.add(scrollBar_1);

			JLabel lblBidOnTask = new JLabel("Bid On Task:");
			lblBidOnTask.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblBidOnTask.setBounds(765, 331, 119, 26);
			taskManagerPanel.add(lblBidOnTask);

			JLabel lblTaskId = new JLabel("Task ID:");
			lblTaskId.setBounds(715, 363, 76, 14);
			taskManagerPanel.add(lblTaskId);

			JLabel lblCompletionHours = new JLabel("Hours:");
			lblCompletionHours.setBounds(725, 387, 59, 14);
			taskManagerPanel.add(lblCompletionHours);

			textField_10 = new JTextField();
			textField_10.setBounds(777, 360, 86, 20);
			taskManagerPanel.add(textField_10);
			textField_10.setColumns(10);

			textField_12 = new JTextField();
			textField_12.setBounds(777, 385, 86, 20);
			taskManagerPanel.add(textField_12);
			textField_12.setColumns(10);

			JButton btnOk = new JButton("Ok");
			btnOk.setBounds(777, 409, 86, 23);
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if ((textField_10.getText().equals("") || textField_12
							.getText().equals(""))) {
						feedbackTextArea1.setText("Not Enough Values");
					} else {
						feedbackTextArea1.setText(dc.makeTaskbid(
								Integer.parseInt(textField_10.getText()),
								Integer.parseInt(textField_12.getText())));
					}
				}
			});
			taskManagerPanel.add(btnOk);

			JLabel label = new JLabel("Subtask ID:");
			label.setBounds(74, 488, 96, 15);
			taskManagerPanel.add(label);

			textField_14 = new JTextField();
			textField_14.setBounds(166, 477, 95, 26);
			taskManagerPanel.add(textField_14);
			textField_14.setColumns(10);
		}
		return taskManagerPanel;
	}

	/**
	 * This method initializes showTaskButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowTaskButtonTO() {
		if (showTaskButtonTO == null) {
			showTaskButtonTO = new JButton();
			showTaskButtonTO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (taskNumberTextField.getText().equals("")) {
						feedbackjTextArea
								.setText("Must enter a Task ID number.");
					} else {
						displayTextArea.setText(dc.showTask(Integer
								.parseInt(taskNumberTextField.getText())));
						feedbackjTextArea.setText("Success!");
					}
				}
			});
			showTaskButtonTO.setBounds(new Rectangle(46, 92, 127, 26));
			showTaskButtonTO.setText("Ok");

		}
		return showTaskButtonTO;
	}

	/**
	 * This method initializes taskNumberTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTaskNumberTextField() {
		if (taskNumberTextField == null) {
			taskNumberTextField = new JTextField();
			taskNumberTextField.setBounds(new Rectangle(94, 55, 90, 26));
		}
		return taskNumberTextField;
	}

	/**
	 * This method initializes displayTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextPane getDisplayTextArea() {
		if (displayTextArea == null) {
			displayTextArea = new JTextPane();
			displayTextArea.setFont(font);
			displayTextArea.setBounds(new Rectangle(234, 13, 653, 240));

		}
		return displayTextArea;
	}

	/**
	 * This method initializes showTaskBidButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowTaskBidButtonTO() {
		if (showTaskBidButtonTO == null) {
			showTaskBidButtonTO = new JButton();
			showTaskBidButtonTO.setBounds(new Rectangle(46, 272, 127, 26));
			showTaskBidButtonTO.setText("Ok");
			showTaskBidButtonTO
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (taskBidNumberTextField1.getText().equals("")) {
								feedbackjTextArea
										.setText("Must enter a Task Bid ID number.");
							} else {
								displayTextArea.setText(dc.showTaskBid(Integer
										.parseInt(taskBidNumberTextField1
												.getText())));
								feedbackjTextArea
										.setText("Success!... I hope =P");
							}
						}
					});
		}
		return showTaskBidButtonTO;
	}

	/**
	 * This method initializes taskBidNumberTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTaskBidNumberTextField1() {
		if (taskBidNumberTextField1 == null) {
			taskBidNumberTextField1 = new JTextField();
			taskBidNumberTextField1.setBounds(new Rectangle(104, 235, 90, 26));
		}
		return taskBidNumberTextField1;
	}

	/**
	 * This method initializes winnerjButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getTaskWinnerjButtonTO() {
		if (taskWinnerjButtonTO == null) {
			taskWinnerjButtonTO = new JButton();
			taskWinnerjButtonTO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (taskBidNumberTextField11.getText().equals("")
							|| taskIDWinnerTextField.getText().equals("")) {
						feedbackjTextArea
								.setText("You did not enter enough information! selectTaskBidWinner");
					} else {
						feedbackjTextArea
								.setText("Request sent - selectTaskBidWinner");
						displayTextArea.setText(dc.selectTaskBidWinner(Integer
								.parseInt(taskIDWinnerTextField.getText()),
								Integer.parseInt(taskBidNumberTextField11
										.getText())));
					}
				}
			});
			taskWinnerjButtonTO.setBounds(new Rectangle(57, 461, 127, 26));
			taskWinnerjButtonTO.setText("Ok");
		}
		return taskWinnerjButtonTO;
	}

	/**
	 * This method initializes taskBidNumberTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTaskBidNumberTextField11() {
		if (taskBidNumberTextField11 == null) {
			taskBidNumberTextField11 = new JTextField();
			taskBidNumberTextField11.setBounds(new Rectangle(148, 386, 90, 26));
		}
		return taskBidNumberTextField11;
	}

	/**
	 * This method initializes createButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCreateTaskButtonTO() {
		if (createTaskButtonTO == null) {
			createTaskButtonTO = new JButton();
			createTaskButtonTO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tasknamejTextField.getText().equals("")
							|| taskDescriptionTextField.getText().equals("")
							|| budgetTextField.getText().equals("")
							|| dateBidStopTextField11.getText().equals("")) {
						feedbackjTextArea.setText("Not enough values.");
					} else {
						feedbackjTextArea.setText(dc.createTask(
								tasknamejTextField.getText(),
								taskDescriptionTextField.getText(),
								Integer.parseInt(budgetTextField.getText()),
								Date.valueOf(dateBidStopTextField11.getText())));
					}
				}
			});
			createTaskButtonTO.setBounds(new Rectangle(756, 489, 110, 26));
			createTaskButtonTO.setText("Ok");

		}
		return createTaskButtonTO;
	}

	/**
	 * This method initializes taskidjTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTaskidjTextField() {
		if (taskidjTextField == null) {
			taskidjTextField = new JTextField();
			taskidjTextField.setBounds(new Rectangle(300, 303, 76, 26));
		}
		return taskidjTextField;
	}

	/**
	 * This method initializes budgetTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBudgetTextField() {
		if (budgetTextField == null) {
			budgetTextField = new JTextField();
			budgetTextField.setBounds(new Rectangle(300, 332, 76, 26));
		}
		return budgetTextField;
	}

	/**
	 * This method initializes dateBidStopTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDateBidStopTextField11() {
		if (dateBidStopTextField11 == null) {
			dateBidStopTextField11 = new JTextField();
			dateBidStopTextField11.setBounds(new Rectangle(300, 360, 76, 26));
		}
		return dateBidStopTextField11;
	}

	/**
	 * This method initializes tasknamejTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTasknamejTextField() {
		if (tasknamejTextField == null) {
			tasknamejTextField = new JTextField();
			tasknamejTextField.setBounds(new Rectangle(503, 306, 390, 26));
		}
		return tasknamejTextField;
	}

	/**
	 * This method initializes taskDescriptionTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTaskDescriptionTextField() {
		if (taskDescriptionTextField == null) {
			taskDescriptionTextField = new JTextField();
			taskDescriptionTextField
					.setBounds(new Rectangle(503, 341, 384, 71));
		}
		return taskDescriptionTextField;
	}

	/**
	 * This method initializes feedbackjTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getFeedbackjTextArea() {
		if (feedbackjTextArea == null) {
			feedbackjTextArea = new JTextArea();
			feedbackjTextArea.setBounds(new Rectangle(0, 526, 807, 26));
		}
		return feedbackjTextArea;
	}

	/**
	 * This method initializes TaskSolverPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTaskSolverPanel() {
		if (taskSolverPanel == null) {
			hoursLabel2 = new JLabel();
			hoursLabel2.setBounds(new Rectangle(800, 384, 74, 16));
			hoursLabel2.setText("Hours");
			offerLabel1 = new JLabel();
			offerLabel1.setBounds(new Rectangle(653, 384, 92, 16));
			offerLabel1.setText("Offer:");
			subtasknbLabel1111 = new JLabel();
			subtasknbLabel1111.setBounds(new Rectangle(571, 354, 141, 20));
			subtasknbLabel1111.setText("SubTask Number:");
			subtaskBidNbLabel1111 = new JLabel();
			subtaskBidNbLabel1111.setBounds(new Rectangle(6, 342, 121, 20));
			subtaskBidNbLabel1111.setText("SubTask Bid ID:");
			subtasknbLabel111 = new JLabel();
			subtasknbLabel111.setBounds(new Rectangle(18, 246, 129, 20));
			subtasknbLabel111.setText("SubTask ID:");
			tasknbLabel11 = new JLabel();
			tasknbLabel11.setBounds(new Rectangle(16, 53, 99, 20));
			tasknbLabel11.setText("Task Number:");
			taskSolverPanel = new JPanel();
			taskSolverPanel.setLayout(null);
			taskSolverPanel.add(getShowTaskButtonTS(), null);
			taskSolverPanel.add(tasknbLabel11, null);
			taskSolverPanel.add(getTaskNumberTextField11(), null);
			taskSolverPanel.add(getShowsubtaskButtonTS(), null);
			taskSolverPanel.add(subtasknbLabel111, null);
			taskSolverPanel.add(getSubtaskNumberTextField111(), null);
			taskSolverPanel.add(getShowsubtaskbidButtonTS(), null);
			taskSolverPanel.add(subtaskBidNbLabel1111, null);
			taskSolverPanel.add(getSubtaskBidNbTextField1111(), null);
			taskSolverPanel.add(getJTextArea1(), null);
			taskSolverPanel.add(getEnterSubtskBidButtonTS(), null);
			taskSolverPanel.add(subtasknbLabel1111, null);
			taskSolverPanel.add(getSubtaskNumberTextField1111(), null);
			taskSolverPanel.add(offerLabel1, null);
			taskSolverPanel.add(getOfferTextField1(), null);
			taskSolverPanel.add(hoursLabel2, null);
			taskSolverPanel.add(getFeedbackTextArea11(), null);

			JLabel lblEnterSubtaskBid = new JLabel("Enter Subtask Bid:");
			lblEnterSubtaskBid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblEnterSubtaskBid.setBounds(571, 335, 141, 14);
			taskSolverPanel.add(lblEnterSubtaskBid);

			JLabel lblShowSubtaskBid = new JLabel("Show Subtask Bid:");
			lblShowSubtaskBid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowSubtaskBid.setBounds(8, 319, 199, 14);
			taskSolverPanel.add(lblShowSubtaskBid);

			JLabel lblShowSubtask = new JLabel("Show Subtask:");
			lblShowSubtask.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowSubtask.setBounds(8, 222, 129, 14);
			taskSolverPanel.add(lblShowSubtask);

			JLabel lblShowTask_1 = new JLabel("Show Task:");
			lblShowTask_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblShowTask_1.setBounds(8, 29, 92, 14);
			taskSolverPanel.add(lblShowTask_1);

			JButton showAllSubtasksTS = new JButton("Show All Subtasks");
			showAllSubtasksTS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jTextArea1.setText(dc.showAllSubtasks());
				}
			});
			showAllSubtasksTS.setBounds(712, 293, 167, 23);
			taskSolverPanel.add(showAllSubtasksTS);

			JButton editSubtaskButtonTS = new JButton("Edit");
			editSubtaskButtonTS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			editSubtaskButtonTS.setBounds(624, 412, 110, 29);
			taskSolverPanel.add(editSubtaskButtonTS);

			JScrollBar scrollBar = new JScrollBar();
			scrollBar.setBounds(862, 18, 17, 263);
			taskSolverPanel.add(scrollBar);

			JTextArea textArea = new JTextArea();
			textArea.setBounds(8, 406, 1, 15);
			taskSolverPanel.add(textArea);

			addUserToGroupTextField = new JTextField();
			addUserToGroupTextField.setBounds(219, 335, 126, 27);
			taskSolverPanel.add(addUserToGroupTextField);
			addUserToGroupTextField.setColumns(10);

			JButton addUserToGroupButton = new JButton("Add User");
			addUserToGroupButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (addUserToGroupTextField.getText().equals("")) {
						feedbackTextArea11.setText("Must input a User ID!");
					} else {
						int userId = Integer.parseInt(addUserToGroupTextField
								.getText());
						groupTextArea.setText(dc.addUserToGroup(userId));
						feedbackTextArea11.setText("");
					}
				}
			});
			addUserToGroupButton.setBounds(219, 380, 126, 44);
			taskSolverPanel.add(addUserToGroupButton);
			taskSolverPanel.add(getGroupTextArea());
			taskSolverPanel.add(getNewGroupButton());

			JLabel solveSubtaskLabel = new JLabel("Solve Subtask:");
			solveSubtaskLabel.setBounds(12, 412, 125, 15);
			taskSolverPanel.add(solveSubtaskLabel);

			JLabel solveSubtaskIDLabel = new JLabel("Subtask ID:");
			solveSubtaskIDLabel.setBounds(12, 436, 88, 15);
			taskSolverPanel.add(solveSubtaskIDLabel);

			solveSubtaskTextField = new JTextField();
			solveSubtaskTextField.setBounds(93, 433, 99, 20);
			taskSolverPanel.add(solveSubtaskTextField);
			solveSubtaskTextField.setColumns(10);

			JButton solveSubtaskButton = new JButton("Solve");
			solveSubtaskButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (solveSubtaskTextField.getText().equals(""))
						jTextArea1.setText("Error not enough values!");
					else
						jTextArea1.setText(dc.solveSubtask(Integer
								.parseInt(solveSubtaskTextField.getText())));
				}
			});
			solveSubtaskButton.setBounds(20, 460, 117, 25);
			taskSolverPanel.add(solveSubtaskButton);
		}
		return taskSolverPanel;
	}

	/**
	 * This method initializes HRPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getHrPanel() {
		if (hrPanel == null) {
			employeenmejLabel = new JLabel();
			employeenmejLabel.setBounds(new Rectangle(20, 62, 141, 20));
			employeenmejLabel.setText("Employee Name:");
			hrPanel = new JPanel();
			hrPanel.setLayout(null);
			hrPanel.add(getJTextArea11(), null);
			hrPanel.add(getEmployeeReportButton(), null);
			hrPanel.add(employeenmejLabel, null);
			hrPanel.add(getEmployeeNameTextArea2(), null);
			hrPanel.add(getFeedbackTextArea12(), null);

			JLabel lblViewEmployeeReport = new JLabel("View Employee Report:");
			lblViewEmployeeReport.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblViewEmployeeReport.setBounds(10, 37, 180, 20);
			hrPanel.add(lblViewEmployeeReport);

			JScrollBar scrollBar = new JScrollBar();
			scrollBar.setBounds(856, 16, 17, 356);
			hrPanel.add(scrollBar);
		}
		return hrPanel;
	}

	/**
	 * This method initializes showTaskButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowTaskButtonTM() {
		if (showTaskButtonTM == null) {
			showTaskButtonTM = new JButton();
			showTaskButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (taskNumberTextField1.getText().equals("")) {
						feedbackTextArea1
								.setText("Must enter a Task ID number.");
					} else {
						jTextArea.setText(dc.showTask(Integer
								.parseInt(taskNumberTextField1.getText())));
						feedbackTextArea1.setText("Success!");
					}
				}
			});
			showTaskButtonTM.setBounds(new Rectangle(44, 73, 110, 26));
			showTaskButtonTM.setText("Ok");

		}
		return showTaskButtonTM;
	}

	/**
	 * This method initializes taskNumberTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTaskNumberTextField1() {
		if (taskNumberTextField1 == null) {
			taskNumberTextField1 = new JTextField();
			taskNumberTextField1.setBounds(new Rectangle(118, 36, 95, 26));
		}
		return taskNumberTextField1;
	}

	/**
	 * This method initializes showsubtaskButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowsubtaskButtonTM() {
		if (showsubtaskButtonTM == null) {
			showsubtaskButtonTM = new JButton();
			showsubtaskButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (subtaskNumberTextField11.getText().equals("")) {
						feedbackTextArea1
								.setText("Must enter a Subtask ID number.");
					} else {
						jTextArea.setText(dc.showSubtask(Integer
								.parseInt(subtaskNumberTextField11.getText())));
						feedbackTextArea1.setText("Success!");
					}
				}
			});
			showsubtaskButtonTM.setBounds(new Rectangle(44, 253, 110, 26));
			showsubtaskButtonTM.setText("Ok");

		}
		return showsubtaskButtonTM;
	}

	/**
	 * This method initializes showsubtaskbidButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowsubtaskbidButtonTM() {
		if (showsubtaskbidButtonTM == null) {
			showsubtaskbidButtonTM = new JButton();
			showsubtaskbidButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (subtaskBidNbTextField111.getText().equals("")) {
						feedbackTextArea1
								.setText("Must enter a Subtask Bid ID number.");
					} else {
						jTextArea.setText(dc.showSubtaskBid(Integer
								.parseInt(subtaskBidNbTextField111.getText())));
						feedbackTextArea1.setText("Success!");
					}
				}
			});
			showsubtaskbidButtonTM.setBounds(new Rectangle(44, 357, 110, 26));
			showsubtaskbidButtonTM.setText("Ok");

		}
		return showsubtaskbidButtonTM;
	}

	/**
	 * This method initializes subtaskNumberTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubtaskNumberTextField11() {
		if (subtaskNumberTextField11 == null) {
			subtaskNumberTextField11 = new JTextField();
			subtaskNumberTextField11.setBounds(new Rectangle(115, 216, 98, 26));
		}
		return subtaskNumberTextField11;
	}

	/**
	 * This method initializes subtaskBidNbTextField111
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubtaskBidNbTextField111() {
		if (subtaskBidNbTextField111 == null) {
			subtaskBidNbTextField111 = new JTextField();
			subtaskBidNbTextField111.setBounds(new Rectangle(115, 326, 98, 26));
		}
		return subtaskBidNbTextField111;
	}

	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();

			jTextArea.setFont(font);
			jTextArea.setBounds(new Rectangle(225, 15, 659, 297));
		}
		return jTextArea;
	}

	/**
	 * This method initializes jScrollBar
	 * 
	 * @return javax.swing.JScrollBar
	 */
	private JScrollBar getJScrollBar() {
		if (jScrollBar == null) {
			jScrollBar = new JScrollBar();
			jScrollBar.setBounds(new Rectangle(715, 16, 13, 247));
		}
		return jScrollBar;
	}

	/**
	 * This method initializes showTaskBidButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowTaskBidButtonTM() {
		if (showTaskBidButtonTM == null) {
			showTaskBidButtonTM = new JButton();
			showTaskBidButtonTM.setBounds(new Rectangle(44, 152, 110, 26));
			showTaskBidButtonTM.setText("Ok");
			showTaskBidButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (taskBidNumberTextField13.getText().equals("")) {
						feedbackTextArea1
								.setText("Must enter a Task Bid ID number.");
					} else {
						jTextArea.setText(dc.showTaskBid(Integer
								.parseInt(taskBidNumberTextField13.getText())));
						feedbackTextArea1.setText("Success!");
					}
				}
			});
		}
		return showTaskBidButtonTM;
	}

	/**
	 * This method initializes taskBidNumberTextField13
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTaskBidNumberTextField13() {
		if (taskBidNumberTextField13 == null) {
			taskBidNumberTextField13 = new JTextField();
			taskBidNumberTextField13.setBounds(new Rectangle(118, 126, 95, 26));
		}
		return taskBidNumberTextField13;
	}

	/**
	 * This method initializes enterTaskBidButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEnterTaskBidButton() {
		if (enterTaskBidButton == null) {
			enterTaskBidButton = new JButton();
			enterTaskBidButton.setBounds(new Rectangle(234, 152, 125, 26));
			enterTaskBidButton.setText("Enter Task Bid");

		}
		return enterTaskBidButton;
	}

	/**
	 * This method initializes subtaskWinnerButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSubtaskWinnerButtonTM() {
		if (subtaskWinnerButtonTM == null) {
			subtaskWinnerButtonTM = new JButton();
			subtaskWinnerButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ArrayList<JTextComponent> list = new ArrayList<JTextComponent>();
					list.add(subtaskBidWinnerNbTextField1111);
					list.add(textField_14);
					if (checkForFilledFields(list))
						jTextArea.setText(dc.selectSubtaskBidWinner(Integer
								.parseInt(subtaskBidWinnerNbTextField1111
										.getText()), Integer
								.parseInt(textField_14.getText())));
					else
						jTextArea.setText("Not enough values entered");
				}
			});
			subtaskWinnerButtonTM.setBounds(new Rectangle(44, 502, 110, 26));
			subtaskWinnerButtonTM.setText("Ok");
		}
		return subtaskWinnerButtonTM;
	}

	/**
	 * This method initializes createSubtaskButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCreateSubtaskButtonTM() {
		if (createSubtaskButtonTM == null) {
			createSubtaskButtonTM = new JButton();
			createSubtaskButtonTM.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (TskNumberTextField1.getText().equals("")
							|| subnamejTextField.getText().equals("")
							|| subdescrpTextField.getText().equals("")
							|| budgtjTextField.getText().equals("")
							|| dateEndSubtTextField.getText().equals("")) {
						feedbackTextArea1.setText("Not Enough Values.");
					} else {
						String display = dc.createSubtask(
								Integer.parseInt(TskNumberTextField1.getText()),
								subnamejTextField.getText(),
								subdescrpTextField.getText(),
								Integer.parseInt(budgtjTextField.getText()),
								Date.valueOf(dateEndSubtTextField.getText()));
						jTextArea.setText(display);
						feedbackTextArea1.setText(display);
					}
				}
			});
			createSubtaskButtonTM.setBounds(new Rectangle(540, 488, 110, 26));
			createSubtaskButtonTM.setText("Ok");
		}
		return createSubtaskButtonTM;
	}

	/**
	 * This method initializes feedbackTextArea1
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getFeedbackTextArea1() {
		if (feedbackTextArea1 == null) {
			feedbackTextArea1 = new JTextArea();
			feedbackTextArea1.setBounds(new Rectangle(10, 540, 802, 20));
		}
		return feedbackTextArea1;
	}

	/**
	 * This method initializes subTskNumberTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubTskNumberTextField1() {
		if (subTskNumberTextField1 == null) {
			subTskNumberTextField1 = new JTextField();
			subTskNumberTextField1.setBounds(new Rectangle(331, 385, 82, 20));
		}
		return subTskNumberTextField1;
	}

	/**
	 * This method initializes TskNumberTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTskNumberTextField1() {
		if (TskNumberTextField1 == null) {
			TskNumberTextField1 = new JTextField();
			TskNumberTextField1.setBounds(new Rectangle(330, 361, 82, 20));
		}
		return TskNumberTextField1;
	}

	/**
	 * This method initializes dateEndSubtTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDateEndSubtTextField() {
		if (dateEndSubtTextField == null) {
			dateEndSubtTextField = new JTextField();
			dateEndSubtTextField.setBounds(new Rectangle(331, 411, 82, 20));
		}
		return dateEndSubtTextField;
	}

	/**
	 * This method initializes budgtjTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getBudgtjTextField() {
		if (budgtjTextField == null) {
			budgtjTextField = new JTextField();
			budgtjTextField.setBounds(new Rectangle(331, 433, 82, 21));
		}
		return budgtjTextField;
	}

	/**
	 * This method initializes subnamejTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubnamejTextField() {
		if (subnamejTextField == null) {
			subnamejTextField = new JTextField();
			subnamejTextField.setBounds(new Rectangle(540, 361, 165, 20));
		}
		return subnamejTextField;
	}

	/**
	 * This method initializes subdescrpTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubdescrpTextField() {
		if (subdescrpTextField == null) {
			subdescrpTextField = new JTextField();
			subdescrpTextField.setBounds(new Rectangle(540, 384, 165, 92));
		}
		return subdescrpTextField;
	}

	/**
	 * This method initializes subtaskBidWinnerNbTextField1111
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubtaskBidWinnerNbTextField1111() {
		if (subtaskBidWinnerNbTextField1111 == null) {
			subtaskBidWinnerNbTextField1111 = new JTextField();
			subtaskBidWinnerNbTextField1111.setBounds(new Rectangle(166, 450,
					95, 26));
		}
		return subtaskBidWinnerNbTextField1111;
	}

	/**
	 * This method initializes showTaskButton11
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowTaskButtonTS() {
		if (showTaskButtonTS == null) {
			showTaskButtonTS = new JButton();
			showTaskButtonTS.setBounds(new Rectangle(27, 85, 110, 29));
			showTaskButtonTS.setText("Ok");
			showTaskButtonTS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (taskNumberTextField11.getText().equals("")) {
						feedbackTextArea11
								.setText("Must enter a Task ID number.");
					} else {
						jTextArea1.setText(dc.showTask(Integer
								.parseInt(taskNumberTextField11.getText())));
						feedbackTextArea11.setText("Success!");
					}
				}
			});
		}
		return showTaskButtonTS;
	}

	/**
	 * This method initializes taskNumberTextField11
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTaskNumberTextField11() {
		if (taskNumberTextField11 == null) {
			taskNumberTextField11 = new JTextField();
			taskNumberTextField11.setBounds(new Rectangle(118, 54, 89, 20));
		}
		return taskNumberTextField11;
	}

	/**
	 * This method initializes showsubtaskButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowsubtaskButtonTS() {
		if (showsubtaskButtonTS == null) {
			showsubtaskButtonTS = new JButton();
			showsubtaskButtonTS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (subtaskNumberTextField111.getText().equals("")) {
						feedbackTextArea11
								.setText("Must enter a Subtask ID number.");
					} else {
						jTextArea1.setText(dc.showSubtask(Integer
								.parseInt(subtaskNumberTextField111.getText())));
						feedbackTextArea11.setText("Success!");
					}
				}
			});
			showsubtaskButtonTS.setBounds(new Rectangle(27, 278, 110, 29));
			showsubtaskButtonTS.setText("Ok");

		}
		return showsubtaskButtonTS;
	}

	/**
	 * This method initializes subtaskNumberTextField111
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubtaskNumberTextField111() {
		if (subtaskNumberTextField111 == null) {
			subtaskNumberTextField111 = new JTextField();
			subtaskNumberTextField111
					.setBounds(new Rectangle(118, 247, 89, 20));
		}
		return subtaskNumberTextField111;
	}

	/**
	 * This method initializes showsubtaskbidButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowsubtaskbidButtonTS() {
		if (showsubtaskbidButtonTS == null) {
			showsubtaskbidButtonTS = new JButton();
			showsubtaskbidButtonTS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (subtaskBidNbTextField1111.getText().equals("")) {
						feedbackTextArea11
								.setText("Must enter a Subtask Bid ID number.");
					} else {
						jTextArea1.setText(dc.showSubtaskBid(Integer
								.parseInt(subtaskBidNbTextField1111.getText())));
						feedbackTextArea11.setText("Success!");
					}
				}
			});
			showsubtaskbidButtonTS.setBounds(new Rectangle(27, 371, 110, 29));
			showsubtaskbidButtonTS.setText("Ok");

		}
		return showsubtaskbidButtonTS;
	}

	/**
	 * This method initializes subtaskBidNbTextField1111
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubtaskBidNbTextField1111() {
		if (subtaskBidNbTextField1111 == null) {
			subtaskBidNbTextField1111 = new JTextField();
			subtaskBidNbTextField1111
					.setBounds(new Rectangle(118, 343, 89, 20));
		}
		return subtaskBidNbTextField1111;
	}

	/**
	 * This method initializes jTextArea1
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea1 == null) {
			jTextArea1 = new JTextArea();
			jTextArea1.setFont(font);
			jTextArea1.setBounds(new Rectangle(219, 18, 660, 263));
		}
		return jTextArea1;
	}

	/**
	 * This method initializes enterSubtskBidButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEnterSubtskBidButtonTS() {
		if (enterSubtskBidButtonTS == null) {
			enterSubtskBidButtonTS = new JButton();
			enterSubtskBidButtonTS.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (subtaskNumberTextField1111.getText().equals("")
							|| OfferTextField1.getText().equals("")) {
						feedbackTextArea11.setText("Not Enough information.");
					} else {
						String display = dc.makeSubtaskBid(
								Integer.parseInt(subtaskNumberTextField1111
										.getText()), Integer
										.parseInt(OfferTextField1.getText()));
						jTextArea1.setText(display);
						feedbackTextArea11.setText(display);
					}
				}
			});
			enterSubtskBidButtonTS.setBounds(new Rectangle(742, 412, 110, 29));
			enterSubtskBidButtonTS.setText("Ok");

		}
		return enterSubtskBidButtonTS;
	}

	/**
	 * This method initializes subtaskNumberTextField1111
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSubtaskNumberTextField1111() {
		if (subtaskNumberTextField1111 == null) {
			subtaskNumberTextField1111 = new JTextField();
			subtaskNumberTextField1111
					.setBounds(new Rectangle(699, 352, 99, 20));
		}
		return subtaskNumberTextField1111;
	}

	/**
	 * This method initializes OfferTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getOfferTextField1() {
		if (OfferTextField1 == null) {
			OfferTextField1 = new JTextField();
			OfferTextField1.setBounds(new Rectangle(699, 380, 99, 20));
		}
		return OfferTextField1;
	}

	/**
	 * This method initializes jTextArea11
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getJTextArea11() {
		if (jTextArea11 == null) {
			jTextArea11 = new JTextArea();
			jTextArea11.setFont(font);
			jTextArea11.setBounds(new Rectangle(270, 16, 603, 356));
		}
		return jTextArea11;
	}

	/**
	 * This method initializes employeeReportButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getEmployeeReportButton() {
		if (employeeReportButton == null) {
			employeeReportButton = new JButton();
			employeeReportButton.setBounds(new Rectangle(20, 122, 110, 26));
			employeeReportButton.setText("Ok");

		}
		return employeeReportButton;
	}

	/**
	 * This method initializes employeeNameTextArea2
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getEmployeeNameTextArea2() {
		if (employeeNameTextArea2 == null) {
			employeeNameTextArea2 = new JTextArea();
			employeeNameTextArea2.setBounds(new Rectangle(20, 89, 132, 20));
		}
		return employeeNameTextArea2;
	}

	/**
	 * This method initializes feedbackTextArea11
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getFeedbackTextArea11() {
		if (feedbackTextArea11 == null) {
			feedbackTextArea11 = new JTextArea();
			feedbackTextArea11.setBounds(new Rectangle(-2, 524, 806, 29));
		}
		return feedbackTextArea11;
	}

	/**
	 * This method initializes feedbackTextArea12
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getFeedbackTextArea12() {
		if (feedbackTextArea12 == null) {
			feedbackTextArea12 = new JTextArea();
			feedbackTextArea12.setBounds(new Rectangle(-1, 525, 808, 16));
		}
		return feedbackTextArea12;
	}

	/**
	 * This is the default constructor
	 */
	public TabSolver(DomainController dc) {
		super();
		this.dc = dc;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(940, 637);
		this.setContentPane(getTasksolverGuiPane());
		this.setTitle("TASK SOLVER");
	}

	private JTextArea getGroupTextArea() {
		if (groupTextArea == null) {
			groupTextArea = new JTextArea();
			groupTextArea.setBounds(370, 333, 195, 147);
		}
		return groupTextArea;
	}

	private JButton getNewGroupButton() {
		if (newGroupButton == null) {
			newGroupButton = new JButton("New Group");
			newGroupButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					groupTextArea.setText(dc.newGroup());
					feedbackTextArea11.setText("New group created!");
				}
			});
			newGroupButton.setBounds(219, 433, 126, 47);
		}
		return newGroupButton;
	}

	private JButton getBtnShowMyTasks() {
		if (btnShowMyTasks == null) {
			btnShowMyTasks = new JButton("Show My Tasks");
			btnShowMyTasks.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					displayTextArea.setText(dc.showMyTasks());
				}
			});
			btnShowMyTasks.setBounds(416, 265, 179, 25);
		}
		return btnShowMyTasks;
	}
} // @jve:decl-index=0:visual-constraint="10,10"
