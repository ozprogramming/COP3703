import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class RegistrationApp extends JPanel
{
	private ComponentMap components;
	
	// Construct Application
	
	public RegistrationApp() throws SQLException
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		// N-Number Input
		
		AppTextField nNumber = new AppTextField();
		nNumber.setName("N-Number");
		
		// Get Courses from Database
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String courseQuery = "SELECT Cnumber FROM COURSE";
		
		PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
		
		ResultSet courseSet = courseStmt.executeQuery();
		
		DBC.disconnect();
		
		// Course Dropdown
		
		JComboBox<String> course = new JComboBox<>();
		
		while (courseSet.next())
		{
			String courseCode = courseSet.getString("Cnumber");
			course.addItem(courseCode);
		}
		
		course.setName("Course");
		
		course.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					update();
				} 
				catch (SQLException error)
				{
					error.printStackTrace();
				}
			}
		});
		
		// Get Sections of Course from Database
		
		DBC.connect();
		
		conn = DBC.get();
		
		String sectionQuery = "SELECT Instructor, Semester, Year, Snumber FROM COURSE WHERE Course=\"" + (String) course.getSelectedItem() + "\";";
		
		PreparedStatement sectionStmt = conn.prepareStatement(sectionQuery);
		
		ResultSet sectionSet = sectionStmt.executeQuery();
		
		DBC.disconnect();
		
		// Section Table
		
		int sectionCount = resultSetSize(sectionSet);
		
		String[] header = {"Instructor", "Semester", "Year", "Section Number"};
		Object[][] sections = new Object[sectionCount][4];
		
		for (int i = 0; i < sectionCount; i++)
		{
			sectionSet.next();
			
			sections[i][0] = sectionSet.getString("Instructor");
			sections[i][1] = sectionSet.getString("Semester");
			sections[i][2] = sectionSet.getBigDecimal("Year");
			sections[i][3] = sectionSet.getInt("Section Number");
		}
		
		JTable table = new JTable(new DefaultTableModel(sections, header));
		table.setName("Sections");
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Submit Button
		
		JButton submitButton = new JButton("Enroll Student in Course");
		submitButton.setName("Enrollment Button");
		
		// Add Components
		
		this.add(new JLabel("Student N-Number:"));
		this.add(nNumber);
		
		this.add(new JLabel("Course:"));
		this.add(course);
		
		this.add(new JLabel("Sections:"));
		this.add(scrollPane);

		this.add(submitButton);
		
		// Create Component Map
		
		this.components = new ComponentMap(this);
	}
	
	// Update Application
	
	private void update() throws SQLException
	{
		String course = (String) this.components.getValue("Course");
		
		JTable table = (JTable) this.components.getComponent("Sections");
		
		// Get Sections of Course from Database
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String sectionQuery = "SELECT Instructor, Semester, Year, Snumber FROM COURSE WHERE Course=" + course + ";";
		
		PreparedStatement sectionStmt = conn.prepareStatement(sectionQuery);
		
		ResultSet sectionSet = sectionStmt.executeQuery();
		
		DBC.disconnect();
		
		// Repopulate Table
		
		int sectionCount = resultSetSize(sectionSet);
		
		String[] header = {"Instructor", "Semester", "Year", "Section Number"};
		String[][] sections = new String[sectionCount][4];
		
		for (int i = 0; i < sectionCount; i++)
		{
			sectionSet.next();
			
			sections[i][0] = sectionSet.getString("Instructor");
			sections[i][1] = sectionSet.getString("Semester");
			sections[i][2] = sectionSet.getString("Year");
			sections[i][3] = sectionSet.getString("Section Number");
		}
		
		table.setModel(new DefaultTableModel(sections, header));
		
		table.updateUI();
	}

	// Evaluate Application Input
	
	private boolean evaluate()
	{
		return this.components.evaluateTextComponents();
	}
	
	// Submit to Database
	
	private void submit() throws SQLException
	{
		if (this.evaluate())
		{
			DBC.connect();
			
			Connection conn = DBC.get();
			
			String query = "INSERT INTO ENROLLED_IN VALUES (?, ?, ?, ?, ?, ?);";

		    PreparedStatement stmt = conn.prepareStatement(query);
		    
		    Object[] section = (Object[]) this.components.getValue("Sections");
		    
		    stmt.setString(1, (String) this.components.getValue("N-Number"));
		    stmt.setString(2, (String) this.components.getValue("Course"));
		    stmt.setString(3, (String) section[1]);
		    stmt.setString(4, (String) section[2]);
		    stmt.setString(5, (String) section[3]);
		    
			int rows = stmt.executeUpdate();
		    
		    if (rows > 0)
		    {
		    	System.out.println("Student successfully registered.");
		    }
		    else
		    {
		    	System.out.println("Failed to register student.");
		    }
			
			DBC.disconnect();
		}
	}
	
	private int resultSetSize(ResultSet rs) throws SQLException
	{
		int count = 0;
		
        if (rs != null)
        {
            rs.beforeFirst();

            while (rs.next())
            {
                count++;
            }
            
            rs.beforeFirst();
        }
        
        return count;
	}
}