import java.sql.*;
import java.io.*;  
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

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
	    
    	insertAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    registerAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    gradeAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    listAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    professorAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    prereqAction.addActionListener(e -> {
			try {
				selectionButtonPressed(e);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
	    
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
	    
	    DBInterface.add(actionForm, gbc);
	    
	    DBFrame.setVisible(true);
	}
	
	private static void selectionButtonPressed(ActionEvent e) throws SQLException
	{
		String command = e.getActionCommand();
		
		System.out.println(command);
		
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
			courseGrade();
		}
	}

	public static void DBInsert() throws SQLException
	{
		Connection conn = getConnection();
		
		String[] entities = {"Student", "Instructor", "Department", "Course", "Section"};
		
		JComboBox<String> selectEntity = new JComboBox<>(entities);
		
		JPanel entityForm = new JPanel();
		
		String entityName = (String) selectEntity.getSelectedItem();
		
		String values = "";
		
		if (entityName.equals("Student"))
		{
			JTextField nNumber = new JTextField();
			JTextField ssn = new JTextField();
			JTextField fName = new JTextField(20);
			JTextField mInit = new JTextField(1);
			JTextField lName = new JTextField(20);
			JTextField phone = new JTextField();
			JTextField street = new JTextField(30);
			JTextField city = new JTextField(20);
			JTextField state = new JTextField(2);
			JTextField zip = new JTextField(5);
			JTextField bDate = new JTextField();
			JTextField sex = new JTextField(1);
			JTextField currStreet = new JTextField(30);
			JTextField currCity = new JTextField(20);
			JTextField currState = new JTextField(2);
			JTextField currZip = new JTextField(5);
			JTextField studentClass = new JTextField(15);
			JTextField degreeProgram = new JTextField();
			JTextField Major_dept = new JTextField();
			JTextField Minor_dept = new JTextField();
			
		}
		else if (entityName.equals("Instructor"))
		{
			JTextField nNumber = new JTextField();
			JTextField ssn = new JTextField();
			JTextField fName = new JTextField(20);
			JTextField mInit = new JTextField(1);
			JTextField lName = new JTextField(20);
			JTextField phone = new JTextField();
			JTextField street = new JTextField(30);
			JTextField city = new JTextField(20);
			JTextField state = new JTextField(2);
			JTextField zip = new JTextField(5);
			JTextField bDate = new JTextField();
			JTextField sex = new JTextField(1);
			JTextField dept = new JTextField(4);
			JTextField oNumber = new JTextField(4);
		}
		else if (entityName.equals("Department"))
		{
			
		}
		else if (entityName.equals("Course"))
		{
			
		}
		else if (entityName.equals("Section"))
		{
			
		}
		
		JButton insertEntity = new JButton("Insert " + entityName);
		
		String query = "INSERT INTO (" + entityName.toUpperCase() + ") VALUES (" + values + ")";

	    PreparedStatement statement = conn.prepareStatement(query);
	}
	
	public static void courseRegistration() throws SQLException
	{
		
	}
	
	public static void gradeReport() throws SQLException
	{
		
	}
	
	public static void courseList() throws SQLException
	{
		Connection conn = getConnection();
		
		String query = "SELECT Cnumber FROM COURSE";

	    PreparedStatement statement = conn.prepareStatement(query);
	}
	
	public static void professorTeaching() throws SQLException
	{
		
	}
	
	public static void courseGrade() throws SQLException
	{
		
	}
	
	public static void prereqSatisfy() throws SQLException
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
