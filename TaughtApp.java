import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class TaughtApp extends JPanel
{
	private ComponentMap components;
	private JPanel list;
	
	// Construct Application
	
	public TaughtApp() throws SQLException
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		// Instructor N-Number
		
		AppTextField nNumber = new AppTextField();
		nNumber.setName("N-Number");
		
		// Submit Button
		
		JButton submit = new JButton("Get Taught Courses");
		
		submit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				try {
					submit();
				} catch (SQLException error) {
					error.printStackTrace();
				}
			}
		});
		
		// Listing Panel
		
		this.list = new JPanel();
		this.list.setLayout(new BoxLayout(this.list, BoxLayout.Y_AXIS));
		this.list.setBorder(new LineBorder(Color.GREEN));
		
		// Add Components
		
		this.add(new JLabel("Instructor N-Number:"));
		this.add(nNumber);
		this.add(submit);
		this.add(this.list);
		
		// Component Map
		
		this.components = new ComponentMap(this);
	}
	
	// Evaluate Input
	
	private boolean evaluate()
	{
		AppTextField nNumber = (AppTextField) this.components.getComponent("N-Number");
		
		return nNumber.evaluate();
	}
	
	// Submit 
	
	private void submit() throws SQLException
	{
		if (this.evaluate())
		{
			this.update();
		}
	}
	
	// Update Application
	
	private void update() throws SQLException
	{
		this.list.removeAll();
		
		String nNumber = (String) this.components.getValue("N-Number");
		
		// Get Instructor Name
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String instructorQuery = "SELECT PERSON.Fname, PERSON.Lname FROM PERSON, INSTRUCTOR WHERE PERSON.Nnumber=INSTRUCTOR.Nnumber AND INSTRUCTOR.Nnumber=?";
		
		PreparedStatement instructorStmt = conn.prepareStatement(instructorQuery);
		
		instructorStmt.setString(1, nNumber);
		
		ResultSet instructorSet = instructorStmt.executeQuery();
		
		instructorSet.next();
		
		String name = instructorSet.getString("Fname") + " " + instructorSet.getString("Lname");
		
		instructorStmt.close();
		
		instructorSet.close();
		
		DBC.disconnect();
		
		// Retrieve Taught Courses from Database
		
		DBC.connect();
		
		conn = DBC.get();
		
		String courseQuery = "SELECT DISTINCT Course FROM SECTION WHERE Instructor=?";
		
		PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
		
		courseStmt.setString(1, nNumber);
		
		ResultSet courseSet = courseStmt.executeQuery();
		
		int courseCount = 0;
		
		String countQuery = "SELECT COUNT(DISTINCT Course) FROM SECTION WHERE Instructor=?";
		
		PreparedStatement countStmt = conn.prepareStatement(countQuery);
		
		countStmt.setString(1, nNumber);
		
		ResultSet countSet = countStmt.executeQuery();
		
		if (countSet.next())
		{
			courseCount = countSet.getInt(1);
		}
		
		countStmt.close();
		countSet.close();
		
		String[][] courses = new String[courseCount][1];
		
		for (int i = 0; i < courseCount; i++)
		{
			courseSet.next();
			
			courses[i][0] = courseSet.getString("Course");
		}
		
		courseSet.close();
		
		courseStmt.close();
		
		DBC.disconnect();
		
		// Table
		
		String[] header = {"Course"};
		
		JTable table = new JTable(new DefaultTableModel(courses, header));
		
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Finalize
		
		this.list.add(new JLabel("Instructor " + name + " has taught:"));
		this.list.add(scrollPane);
		
		this.list.updateUI();
		this.updateUI();
	}
}