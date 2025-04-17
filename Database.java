import java.sql.*;
import java.io.*;  
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Database
{
	public static void main (String args[]) throws SQLException
	{
	    //Connection conn = getConnection();
		
	    JFrame DBFrame = new JFrame("UNF Database Interface");
	    
	    ImageIcon img = new ImageIcon("src\\icon.png");
	    
	    DBFrame.setSize(600, 800);
	    DBFrame.setLocationRelativeTo(null);
	    DBFrame.setResizable(true);
	    DBFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    DBFrame.setTitle("UNF Database Interface");
	    DBFrame.setIconImage(img.getImage());
	    
	    Container DBInterface = DBFrame.getContentPane();
	    
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
	    
	    // Selected Action Form
	    
	    JPanel actionForm = new JPanel();
	    
	    actionForm.setLayout(new BoxLayout(actionForm, BoxLayout.Y_AXIS));
	    actionForm.setBorder(new LineBorder(Color.RED));
	    
	    JLabel formTitle = new JLabel("Action Form");
	    actionForm.add(formTitle);
	    
	    gbc.gridx = 1;
	    gbc.weightx = 0.9;
	    
	    insertAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e, actionForm);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    registerAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e, actionForm);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    gradeAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e, actionForm);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    listAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e, actionForm);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    professorAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e, actionForm);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    prereqAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e, actionForm);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    
	    DBInterface.add(actionForm, gbc);
	    
	    DBFrame.setVisible(true);
	}
	
	private static void selectionButtonPressed(ActionEvent e, JPanel actionForm) throws SQLException
	{
		String command = e.getActionCommand();
		
		actionForm.removeAll();
		
		if (command.equals("Database Insertion"))
		{
			DBInsert(actionForm);
		}
		else if (command.equals("Student Course Registration"))
		{
			courseRegistration(actionForm);
		}
		else if (command.equals("Student Grade Report"))
		{
			gradeReport(actionForm);
		}
		else if (command.equals("Course List"))
		{
			courseList(actionForm);
		}
		else if (command.equals("Professor Courses"))
		{
			professorTeaching(actionForm);
		}
		else if (command.equals("Prequisite Satisfaction"))
		{
			courseGrade(actionForm);
		}
		
		actionForm.updateUI();
	}

	public static void DBInsert(JPanel actionForm) throws SQLException
	{	
		String[] entities = {"Department", "Instructor", "Student", "Course", "Section"};
		
		JComboBox<String> select = new JComboBox<>(entities);
		String name = (String) select.getSelectedItem();
		
		actionForm.add(select);
		
		EntityForm form = new EntityForm();
		
		actionForm.remove(form.panel);
		form.fill((String) select.getSelectedItem());
		actionForm.add(form.panel);

		select.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				form.fill((String) select.getSelectedItem());
				actionForm.updateUI();
			}
		});
		
		// Database Insertion
		/*
		Connection conn = getConnection();
		
		String query = "INSERT INTO (" + entityName.toUpperCase() + ") VALUES (" + values + ")";

	    PreparedStatement statement = conn.prepareStatement(query);*/
	}
	
	public static void courseRegistration(JPanel actionForm) throws SQLException
	{
		
	}
	
	public static void gradeReport(JPanel actionForm) throws SQLException
	{
		
	}
	
	public static void courseList(JPanel actionForm) throws SQLException
	{
		Connection conn = getConnection();
		
		String query = "SELECT Cnumber FROM COURSE";

	    PreparedStatement statement = conn.prepareStatement(query);
	}
	
	public static void professorTeaching(JPanel actionForm) throws SQLException
	{
		
	}
	
	public static void courseGrade(JPanel actionForm) throws SQLException
	{
		
	}
	
	public static void prereqSatisfy(JPanel actionForm) throws SQLException
	{
		
	}
	
	private static String getString()
	{
		Scanner scnr = new Scanner(System.in);
        String input = scnr.nextLine();
        scnr.close();
        return input;
	}
	
	private static Connection getConnection() throws SQLException
	{
		String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
		String uid = "n01577055";
	    String pword = "7055";

	    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	    
	    Connection conn = DriverManager.getConnection(url, uid, pword);
	    
	    return conn;
	}
}
