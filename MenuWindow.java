import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Color;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class MenuWindow {

	private static MenuWindow instance;
	
	private JFrame frmMinitwitter;
	private JTextField textField;
	private JTextField textField_1;
	private DefaultMutableTreeNode selectedNode;
	
	private Hashtable<String, UserView> userPanels;
	private int positiveMessages = 0;
	/**
	 * Create the application.
	 */
	private MenuWindow() {
		userPanels = new Hashtable<String, UserView>();
		initialize();
	}
	
	public static MenuWindow getInstance() {
		if (instance == null) {
			instance = new MenuWindow();
		}
		return instance;
	}
	
	public void setVisibility(boolean value) {
		frmMinitwitter.setVisible(value);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMinitwitter = new JFrame();
		frmMinitwitter.setTitle("MiniTwitter");
		frmMinitwitter.setResizable(false);
		frmMinitwitter.setBounds(200, 200, 900, 625);
		frmMinitwitter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMinitwitter.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(454, 11, 200, 20);
		frmMinitwitter.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(454, 42, 200, 20);
		frmMinitwitter.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JTextPane txtpnWelcomeToTwitter = new JTextPane();
		txtpnWelcomeToTwitter.setText("Welcome to Mini-Twitter!\r\n\r\nDeveloped by Michael Cruz\r\nfor Class CPP Yu Sun CS 365 Summer 2017");
		txtpnWelcomeToTwitter.setEditable(false);
		txtpnWelcomeToTwitter.setForeground(Color.GREEN);
		txtpnWelcomeToTwitter.setBackground(Color.BLACK);
		txtpnWelcomeToTwitter.setBounds(454, 116, 430, 366);
		frmMinitwitter.getContentPane().add(txtpnWelcomeToTwitter);
		
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Users") {
				{
				}
			}
		));
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
			   if (tree.getLastSelectedPathComponent() != null) {
				    selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				    StringBuilder output = new StringBuilder();
				    output.append("Selected Node: " + selectedNode + "\n\n");
				    
				    if (tree.getModel().getRoot() != selectedNode) {
				    	if (!selectedNode.getAllowsChildren()) {
			   				output.append("Type: User\n  UserID: " + User.getUser(selectedNode.toString()).getID());
			   				output.append("\n  Name: " + User.getUser(selectedNode.toString()).getName());
			   				output.append("\n  Followers: " + User.getUser(selectedNode.toString()).getFollowerCount());
			   				output.append("\n  Following: " + User.getUser(selectedNode.toString()).getFollowingCount());
			   				output.append("\n  Messages Sent: " + User.getUser(selectedNode.toString()).getMessageCount());
			   				output.append("\n  Creation Time: " + User.getUser(selectedNode.toString()).getCreationTime());
			   				output.append("\n  Last Update Time: " + User.getUser(selectedNode.toString()).getLastUpdateTime());
			   			} else {
			   				output.append("Type: Group\nGroupID: " + Group.getGroup(selectedNode.toString()).getID());
			   				output.append("\n  Name: " +  Group.getGroup(selectedNode.toString()).getGroupName());
			   				output.append("\n  Group Size: " + Group.getGroup(selectedNode.toString()).getGroupSize());
			   				output.append("\n  Creation Time: " + Group.getGroup(selectedNode.toString()).getCreationTime());
			   			}
				    }
			   		txtpnWelcomeToTwitter.setText(output.toString());
			   }
			  }
			});
		
		tree.setBounds(10, 13, 434, 573);
		frmMinitwitter.getContentPane().add(tree);
				
		JButton btnNewButton = new JButton("Add User");
		btnNewButton.setBounds(664, 10, 220, 23);
		frmMinitwitter.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField.getText().length() == 0 || textField.getText().equals("Error"))
					textField.setText("Error");
				else{
					if (!User.checkUserExists(textField.getText())) {					
						if (selectedNode == null)
							selectedNode = (DefaultMutableTreeNode) tree.getModel().getRoot();

						DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
						DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(textField.getText());
						newNode.setAllowsChildren(false);
						try{
							selectedNode.add(newNode);
							model.reload();
							txtpnWelcomeToTwitter.setText("Successfully added User " + textField.getText() + " to Parent " + selectedNode);
							User newUser = new User(textField.getText());
							if (tree.getModel().getRoot() != selectedNode) 
								Group.getGroup(selectedNode.toString()).addMember(newUser);
							textField.setText("");
						} catch (IllegalStateException e) {
							txtpnWelcomeToTwitter.setText("<!> You can't add a User into another User >_>");
						}
					} else {
						txtpnWelcomeToTwitter.setText("User " + textField.getText() + " already exists");
						textField.setText("");
					}
				}
			}
		});
		
		JButton btnAddGroup = new JButton("Add Group");
		btnAddGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textField_1.getText().length() == 0 || textField_1.getText().equals("Error"))
					textField_1.setText("Error");
				else{
					if (!Group.checkGroupExists(textField_1.getText())) {
						DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
						((DefaultMutableTreeNode) model.getRoot()).add(new DefaultMutableTreeNode(textField_1.getText()));
						model.reload();
						txtpnWelcomeToTwitter.setText("Successfully added Group " + textField_1.getText());
						new Group(textField_1.getText());
						textField_1.setText("");
					} else {
						txtpnWelcomeToTwitter.setText("Group " + textField.getText() + " already exists");
						textField.setText("");
					}
				}
			}
		});
		btnAddGroup.setBounds(664, 41, 220, 23);
		frmMinitwitter.getContentPane().add(btnAddGroup);
		
		JButton btnShowUserTotal = new JButton("Show User Total");
		btnShowUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtpnWelcomeToTwitter.setText("Total User Count: " + User.getUserCount());
			}
		});
		btnShowUserTotal.setBounds(454, 493, 200, 23);
		frmMinitwitter.getContentPane().add(btnShowUserTotal);
		
		JButton btnShowGroupTotal = new JButton("Show Group Total");
		btnShowGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtpnWelcomeToTwitter.setText("Total Group Count: " + Group.getGroupCount());
			}
		});
		btnShowGroupTotal.setBounds(684, 493, 200, 23);
		frmMinitwitter.getContentPane().add(btnShowGroupTotal);
		
		JButton btnShowMessagesTotal = new JButton("Show Messages Total");
		btnShowMessagesTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hashtable<String, User> userList = User.getUserList();
				Enumeration<User> elements =userList.elements();
				int sum = 0;
				while(elements.hasMoreElements()) {
					sum += elements.nextElement().getMessageCount();
				}
				txtpnWelcomeToTwitter.setText("Total Message Count: " + sum);
			}
		});
		btnShowMessagesTotal.setBounds(454, 527, 200, 23);
		frmMinitwitter.getContentPane().add(btnShowMessagesTotal);
		
		JButton btnShowPositivePercentage = new JButton("Show Positive Percentage");
		btnShowPositivePercentage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hashtable<String, User> userList = User.getUserList();
				Enumeration<User> elements =userList.elements();
				int sum = 0;
				while(elements.hasMoreElements()) {
					sum += elements.nextElement().getMessageCount();
				}
				txtpnWelcomeToTwitter.setText("Total Message Count: " + sum);
				txtpnWelcomeToTwitter.setText("Total Positive Msg (Messages Containing ' :) ' ) Count: " + ((double) positiveMessages / (double) sum) * 100 );
			}
		});
		btnShowPositivePercentage.setBounds(684, 527, 200, 23);
		frmMinitwitter.getContentPane().add(btnShowPositivePercentage);
				
		JButton btnOpenUserView = new JButton("Open User View");
		btnOpenUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedNode != null && !selectedNode.getAllowsChildren()) {
					if (!userPanels.containsKey(selectedNode.toString()))
						userPanels.put(selectedNode.toString(), new UserView(User.getUser(selectedNode.toString())));
					else
						txtpnWelcomeToTwitter.setText("<!> User Interface for " + selectedNode + " is already active!");
				} else {
					txtpnWelcomeToTwitter.setText("<!> Invalid Node named " + selectedNode + " to open User View with!\nUser view can only be opened when selecting a User!");
				}
			}
		});
		btnOpenUserView.setBounds(454, 73, 430, 32);
		frmMinitwitter.getContentPane().add(btnOpenUserView);
		
		JButton btnIdVerification = new JButton("ID Verification");
		btnIdVerification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Due to the fact that all Users are prevented to be duplicated on creation in a HashTable. It is impossible to have a duplicate user in this system.
				
				Hashtable<String, User> userList = User.getUserList();
				Enumeration<User> elements =userList.elements();
				boolean flag = true;
				while(elements.hasMoreElements()) {
					if (elements.nextElement().getName().contains(" ")) {
						flag = false;
						break;
					}
				}
				if (flag) {
					Enumeration<Group> gElements = Group.getGroupList().elements();
					while (gElements.hasMoreElements()) {
						if (gElements.nextElement().getGroupName().contains(" ")) {
							flag = false;
							break;
						}
					}
				}
				if (flag) {
					txtpnWelcomeToTwitter.setText("All User Names and Groups have Valid Names");
				} else {
					txtpnWelcomeToTwitter.setText("Invalid Name Detected!");
				}
			}
		});
		btnIdVerification.setBounds(454, 561, 200, 23);
		frmMinitwitter.getContentPane().add(btnIdVerification);
		
		JButton btnFindLastUpdated = new JButton("Find Last Updated");
		btnFindLastUpdated.setBounds(684, 561, 200, 23);
		btnFindLastUpdated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Due to the fact that all Users are prevented to be duplicated on creation in a HashTable. It is impossible to have a duplicate user in this system.
				
				Hashtable<String, User> userList = User.getUserList();
				Enumeration<User> elements =userList.elements();
				User testUser = null;
				User currentUser = null;
				while(elements.hasMoreElements()) {
					if (testUser == null) {
						currentUser = elements.nextElement();
						if (currentUser.getLastUpdateTime() != -1) {
							testUser = currentUser;
						}
					} else {
						currentUser = elements.nextElement();
						if (currentUser.getLastUpdateTime() != -1) {
							if (testUser.getLastUpdateTime() > currentUser.getLastUpdateTime()) {
								testUser = currentUser;
							}
						}
					}
				}
				if (testUser != null) {
					txtpnWelcomeToTwitter.setText("Latest User: " + testUser.getName() + "\n  Last Update Time: " + testUser.getLastUpdateTime() + "\n  Latest Message: " + testUser.getFeed().peek().getMessage());
				} else {
					txtpnWelcomeToTwitter.setText("No users have made any updates!");
				}
			}
		});
		frmMinitwitter.getContentPane().add(btnFindLastUpdated);
	}
	
	protected void updateUserScreen(String name) {
		userPanels.get(name).updateFeed();
	}
	
	protected boolean isPositiveMessage(String message) {
		if (message.contains(":)")) {
			positiveMessages++;
			return true;
		}
		return false;
	}
}
