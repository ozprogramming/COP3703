import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
		
		// Course Dropdown
		
		JComboBox<String> course = new JComboBox<>(DBC.Courses());
		
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
		
		Connection conn = DBC.get();
		
		String sectionQuery = "SELECT Instructor, Semester, Year_, Snumber FROM SECTION WHERE Course=?";
		
		PreparedStatement sectionStmt = conn.prepareStatement(sectionQuery);
		
		// Get Course Number from Course Name
		
		String cNumberQuery = "SELECT Cnumber FROM COURSE WHERE Cname=?";
	    
	    PreparedStatement cNumberStmt = conn.prepareStatement(cNumberQuery);
	    
	    cNumberStmt.setString(1, (String) course.getSelectedItem());
	    
	    ResultSet cNumberSet = cNumberStmt.executeQuery();  
	    
	    cNumberSet.next();
	    
	    String cNumber = cNumberSet.getString("Cnumber");
	    
	    cNumberStmt.close();
	    
	    cNumberSet.close();
	    
	    sectionStmt.setString(1, cNumber);
		
		ResultSet sectionSet = sectionStmt.executeQuery();
		
		int sectionCount = DBC.queryCount("SELECT Instructor, Semester, Year_, Snumber FROM SECTION WHERE Course='" + cNumber + "'");
		
		// Section Table
		
		String[] header = {"Instructor", "Semester", "Year", "Section Number"};
		Object[][] sections = new Object[sectionCount][4];
		
		for (int i = 0; i < sectionCount; i++)
		{
			sectionSet.next();
			
			sections[i][0] = sectionSet.getString("Instructor");
			sections[i][1] = sectionSet.getString("Semester");
			sections[i][2] = sectionSet.getBigDecimal("Year_");
			sections[i][3] = sectionSet.getInt("Snumber");
		}
		
		sectionSet.close();
		
		DBC.disconnect();
		
		JTable table = new JTable(new DefaultTableModel(sections, header));
		table.setName("Sections");
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Submit Button
		
		JButton submit = new JButton("Enroll Student in Course");
		
		submit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					submit();
				}
				catch (SQLException error)
				{
					error.printStackTrace();
				}
			}
		});
		
		submit.setName("Enrollment Button");
		
		// Add Components
		
		this.add(new JLabel("Student N-Number:"));
		this.add(nNumber);
		
		this.add(new JLabel("Course:"));
		this.add(course);
		
		this.add(new JLabel("Sections:"));
		this.add(scrollPane);

		this.add(submit);
		
		// Create Component Map
		
		this.components = new ComponentMap(this);
		this.components.append(table);
	}
	
	// Update Application
	
	private void update() throws SQLException
	{
		String course = (String) this.components.getValue("Course");
		
		JTable table = (JTable) this.components.getComponent("Sections");
		
		// Get Sections of Course from Database
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String sectionQuery = "SELECT Instructor, Semester, Year_, Snumber FROM SECTION WHERE Course=?";
		
		PreparedStatement sectionStmt = conn.prepareStatement(sectionQuery);
		
		String cNumberQuery = "SELECT Cnumber FROM COURSE WHERE Cname=?";
	    
	    PreparedStatement cNumberStmt = conn.prepareStatement(cNumberQuery);
	    
	    cNumberStmt.setString(1, (String) this.components.getValue("Course"));
	    
	    ResultSet cNumberSet = cNumberStmt.executeQuery();  
	    
	    cNumberSet.next();
	    
	    String cNumber = cNumberSet.getString("Cnumber");
	    
	    cNumberStmt.close();
	    
	    cNumberSet.close();
	    
	    sectionStmt.setString(1, cNumber);
		
		ResultSet sectionSet = sectionStmt.executeQuery();
		
		int sectionCount = DBC.queryCount("SELECT Instructor, Semester, Year_, Snumber FROM SECTION WHERE Course='" + cNumber + "'");
		
		// Repopulate Table
		
		String[] header = {"Instructor", "Semester", "Year", "Section Number"};
		Object[][] sections = new Object[sectionCount][4];
		
		for (int i = 0; i < sectionCount; i++)
		{
			sectionSet.next();
			
			sections[i][0] = sectionSet.getString("Instructor");
			sections[i][1] = sectionSet.getString("Semester");
			sections[i][2] = sectionSet.getBigDecimal("Year_");
			sections[i][3] = sectionSet.getInt("Snumber");
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
			
			String query = "INSERT INTO ENROLLED_IN VALUES (?, ?, ?, ?, ?, 'A')";

		    PreparedStatement stmt = conn.prepareStatement(query);
		    
		    String cNumberQuery = "SELECT Cnumber FROM COURSE WHERE Cname=?";
		    
		    PreparedStatement cNumberStmt = conn.prepareStatement(cNumberQuery);
		    
		    cNumberStmt.setString(1, (String) this.components.getValue("Course"));
		    
		    ResultSet cNumberSet = cNumberStmt.executeQuery();  
		    
		    cNumberSet.next();
		    
		    String cNumber = cNumberSet.getString("Cnumber");
		    
		    cNumberStmt.close();
		    
		    cNumberSet.close();
		    
		    JTable sections = ((JTable) this.components.getComponent("Sections"));
		    int row = sections.getSelectedRow();
		    
		    stmt.setString(1, (String) this.components.getValue("N-Number"));
		    stmt.setString(2, cNumber);
		    stmt.setString(3, (String) sections.getValueAt(row, 1));
		    stmt.setBigDecimal(4, (BigDecimal) sections.getValueAt(row, 2));
		    stmt.setInt(5, (int) sections.getValueAt(row, 3));
		    
			stmt.executeQuery();
			
			DBC.disconnect();
		}
	}
}