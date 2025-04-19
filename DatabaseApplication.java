import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class DatabaseApplication
{
	private JFrame frame;
	private JPanel action;
	
	public DatabaseApplication()
	{
		this.frame = new JFrame();
		
		ImageIcon img = new ImageIcon("src\\icon.png");
		
		this.frame.setSize(600, 800);
		this.frame.setLocationRelativeTo(null);
		this.frame.setResizable(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setTitle("UNF Database Interface");
		this.frame.setIconImage(img.getImage());
		
		Container DBInterface = this.frame.getContentPane();
	    
	    DBInterface.setLayout(new GridBagLayout());
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.insets = new Insets(5, 5, 5, 5);
	    gbc.gridwidth = 1;
	    gbc.gridheight = 1;
	    gbc.weightx = 1;
	    gbc.weighty = 1;
	    gbc.gridy = 0;
	    
	    // Action Bar
	    
	    JPanel selectActions = new JPanel();
	    
	    selectActions.setLayout(new BoxLayout(selectActions, BoxLayout.Y_AXIS));
	    selectActions.setBorder(new LineBorder(Color.BLUE));
	    
	    JLabel actionHeader = new JLabel("Database Actions");
	    JButton insertAction = new JButton("Database Insertion");
	    JButton registerAction = new JButton("Student Course Registration");
	    JButton gradeAction = new JButton("Student Grade Report");
	    JButton listAction = new JButton("Course List");
	    JButton professorAction = new JButton("Professor Courses");
	    JButton prereqAction = new JButton("Prequisite Satisfaction");
	    
	    actionHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
	    insertAction.setAlignmentX(Component.CENTER_ALIGNMENT);
	    registerAction.setAlignmentX(Component.CENTER_ALIGNMENT);
	    gradeAction.setAlignmentX(Component.CENTER_ALIGNMENT);
	    listAction.setAlignmentX(Component.CENTER_ALIGNMENT);
	    professorAction.setAlignmentX(Component.CENTER_ALIGNMENT);
	    prereqAction.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    actionHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, actionHeader.getPreferredSize().height));
	    insertAction.setMaximumSize(new Dimension(Integer.MAX_VALUE, insertAction.getPreferredSize().height));
	    registerAction.setMaximumSize(new Dimension(Integer.MAX_VALUE, registerAction.getPreferredSize().height));
	    gradeAction.setMaximumSize(new Dimension(Integer.MAX_VALUE, gradeAction.getPreferredSize().height));
	    listAction.setMaximumSize(new Dimension(Integer.MAX_VALUE, listAction.getPreferredSize().height));
	    professorAction.setMaximumSize(new Dimension(Integer.MAX_VALUE, professorAction.getPreferredSize().height));
	    prereqAction.setMaximumSize(new Dimension(Integer.MAX_VALUE, prereqAction.getPreferredSize().height));
	    
	    insertAction.setHorizontalAlignment(SwingConstants.LEFT);
	    registerAction.setHorizontalAlignment(SwingConstants.LEFT);
	    gradeAction.setHorizontalAlignment(SwingConstants.LEFT);
	    listAction.setHorizontalAlignment(SwingConstants.LEFT);
	    professorAction.setHorizontalAlignment(SwingConstants.LEFT);
	    prereqAction.setHorizontalAlignment(SwingConstants.LEFT);
	    
	    selectActions.add(Box.createRigidArea(new Dimension(10,25)));
	    selectActions.add(actionHeader);
	    selectActions.add(Box.createRigidArea(new Dimension(10,25)));
	    selectActions.add(insertAction);
	    selectActions.add(Box.createRigidArea(new Dimension(10,10)));
	    selectActions.add(registerAction);
	    selectActions.add(Box.createRigidArea(new Dimension(10,10)));
	    selectActions.add(gradeAction);
	    selectActions.add(Box.createRigidArea(new Dimension(10,10)));
	    selectActions.add(listAction);
	    selectActions.add(Box.createRigidArea(new Dimension(10,10)));
	    selectActions.add(professorAction);
	    selectActions.add(Box.createRigidArea(new Dimension(10,10)));
	    selectActions.add(prereqAction);
	    
	    gbc.gridx = 0;
	    gbc.weightx = 0.1;
	    
	    DBInterface.add(selectActions, gbc);
	    
	    // Action Form
	    
	    this.action = new JPanel();
	    
	    action.setLayout(new BoxLayout(action, BoxLayout.Y_AXIS));
	    action.setBorder(new LineBorder(Color.RED));
	    
	    JLabel formTitle = new JLabel("Action Form");
	    action.add(formTitle);
	    
	    gbc.gridx = 1;
	    gbc.weightx = 0.9;
	    
	    insertAction.addActionListener(e -> {
			try {
				selection(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    registerAction.addActionListener(e -> {
			try {
				selection(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    gradeAction.addActionListener(e -> {
			try {
				selection(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    listAction.addActionListener(e -> {
			try {
				selection(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    professorAction.addActionListener(e -> {
			try {
				selection(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    prereqAction.addActionListener(e -> {
			try {
				selection(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    
	    DBInterface.add(action, gbc);
	    
	    this.frame.setVisible(true);
	}
	
	private void selection(ActionEvent e) throws SQLException
	{
		String command = e.getActionCommand();
		
		this.action.removeAll();
		
		if (command.equals("Database Insertion"))
		{
			DBInsert();
		}
		else if (command.equals("Student Course Registration"))
		{
			courseRegistration();
		}
		else if (command.equals("Student Grade Report"))
		{
			gradeReport();
		}
		else if (command.equals("Course List"))
		{
			courseList();
		}
		else if (command.equals("Professor Courses"))
		{
			professorTeaching();
		}
		else if (command.equals("Prequisite Satisfaction"))
		{
			prereqSatisfy();
		}
		
		this.action.updateUI();
	}

	private void DBInsert() throws SQLException
	{
		InsertApp insert = new InsertApp();
		this.action.add(insert);
	}
	
	private void courseRegistration() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}
	
	private void gradeReport() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}
	
	private void courseList() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}

	private void professorTeaching() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}
	
	private void prereqSatisfy() throws SQLException
	{
		// TODO Auto-generated method stub
		
	}
}
