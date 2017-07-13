import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.util.Set;
import java.util.Stack;
import java.awt.event.ActionEvent;

public class UserView {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JList list;
	private JList list_1;

	
	private User user;
	/**
	 * Create the application.
	 */
	public UserView(User user) {
		this.user = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(200, 200, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("MiniTwiter User Interface - " + user.getName());
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 717, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		DefaultListModel<String> model1 = new DefaultListModel<String>();
		Set<User> following = user.getFollowing();
		for(User e: following) {
			model1.addElement(e.getName());
		}
		list = new JList(model1);
		list.setForeground(Color.GREEN);
		list.setBackground(Color.BLACK);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setBounds(10, 42, 874, 180);
		frame.getContentPane().add(list);
				
		JButton btnNewButton = new JButton("Follow User");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().length() > 0 && !user.getName().equals(textField.getText()) && User.checkUserExists(textField.getText()) && !textField.getText().equals("Error") && !user.getFollowing().contains(User.getUser(textField.getText()))) {
					user.follow(User.getUser(textField.getText()));
					model1.addElement(textField.getText());
					list.setModel(model1);
					textField.setText("");
				} else {
					textField.setText("Error");
				}
			}
		});
		btnNewButton.setBounds(737, 10, 147, 23);
		frame.getContentPane().add(btnNewButton);
		
		DefaultListModel<String> model2 = new DefaultListModel<String>();
		Stack<Message> feed = user.getFeed();
		for(Message e: feed) {
			model2.addElement( ((TextMessage)e).getHeader() + " : " + ((TextMessage)e).getMessage());
		}
		list_1 = new JList(model2);
		list_1.setForeground(Color.GREEN);
		list_1.setBackground(Color.BLACK);
		list_1.setBounds(10, 264, 874, 297);
		frame.getContentPane().add(list_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 233, 717, 20);
		textField_1.setColumns(10);
		frame.getContentPane().add(textField_1);
		
		JButton btnTweetMessage = new JButton("Tweet Message");
		btnTweetMessage.setBounds(737, 232, 147, 23);
		frame.getContentPane().add(btnTweetMessage);
		btnTweetMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField_1.getText().length() > 0) {
					TextMessage msg = new TextMessage(user, textField_1.getText());
					user.pushMessage(msg);
					updateFeed();
					textField_1.setText("");
				} else {
					textField_1.setText("Error");
				}
			}
		});
		
	}
	
	public void updateFeed() {
		DefaultListModel<String> model2 = new DefaultListModel<String>();
		Stack<Message> feed = user.getFeed();
		for(Message e: feed) {
			model2.addElement( ((TextMessage)e).getHeader() + " : " + ((TextMessage)e).getMessage());
		}
		list_1.setModel(model2);;
	}
	
}
