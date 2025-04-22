import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class SetStudentGradeApp extends JPanel
{
	private ComponentMap components;
	private JPanel sectionPanel;
	
	// Construct Application
	
	public SetStudentGradeApp() throws SQLException
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		// Initialize Sections Panel
		
		this.sectionPanel = new JPanel();
		
		this.sectionPanel.setLayout(new BoxLayout(this.sectionPanel, BoxLayout.Y_AXIS));
		this.sectionPanel.setBorder(new LineBorder(Color.GREEN));
		
		// N-Number Input
		
		AppTextField nNumber = new AppTextField();
		nNumber.setName("N-Number");
		
		// Submit Button
		
		JButton submit = new JButton("Get Student Sections");
		
		submit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					getSections();
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
		
		this.add(this.sectionPanel);

		this.add(submit);
		
		// Create Component Map
		
		this.components = new ComponentMap(this);
	}
	
	private void getSections() throws SQLException
	{
		this.sectionPanel.removeAll();
		
		String nNumber = (String) this.components.getValue("N-Number");
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String sectionQuery = "SELECT Course, Semester, Year_, Snumber, Grade FROM ENROLLED_IN WHERE Student=?";
		
		PreparedStatement sectionStmt = conn.prepareStatement(sectionQuery);
	    
	    sectionStmt.setString(1, nNumber);
		
		ResultSet sectionSet = sectionStmt.executeQuery();
		
		int sectionCount = DBC.queryCount("SELECT Course, Semester, Year_, Snumber, Grade FROM ENROLLED_IN WHERE Student='" + nNumber + "'");
		
		// Section Table
		
		String[] header = {"Course", "Semester", "Year", "Section Number", "Grade"};
		Object[][] sections = new Object[sectionCount][5];
		
		for (int i = 0; i < sectionCount; i++)
		{
			sectionSet.next();
			
			sections[i][0] = sectionSet.getString("Course");
			sections[i][1] = sectionSet.getString("Semester");
			sections[i][2] = sectionSet.getBigDecimal("Year_");
			sections[i][3] = sectionSet.getInt("Snumber");
			sections[i][4] = sectionSet.getString("Grade");
		}
		
		sectionSet.close();
		
		DBC.disconnect();
		
		JTable table = new JTable(new DefaultTableModel(sections, header));
		table.setName("Sections");
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Grade Input
		
		AppTextField grade = new AppTextField();
		grade.setName("Grade");
		
		// Submit
		
		JButton submit = new JButton("Change Course Grade");
		
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
		
		// Add Components
		
		this.sectionPanel.add(scrollPane);
		this.sectionPanel.add(new JLabel("Enter New Letter Grade"));
		this.sectionPanel.add(grade);
		this.sectionPanel.add(submit);
	
		this.components.append(grade);
		this.components.append(table);
		
		this.sectionPanel.updateUI();
		this.updateUI();
	}
	
	// Update Application

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
			String grade = (String) this.components.getValue("Grade");
			String nNumber = (String) this.components.getValue("N-Number");
			
			DBC.connect();
			
			JTable table = (JTable) this.components.getComponent("Sections");
			int row = table.getSelectedRow();
			
			String course = (String) table.getValueAt(row, 0);
			String semester = (String) table.getValueAt(row, 1);
			BigDecimal year = (BigDecimal) table.getValueAt(row, 2);
			int sNumber = (int) table.getValueAt(row, 3);
			
			Connection conn = DBC.get();
			
			String query = "UPDATE ENROLLED_IN SET Grade=? WHERE Student=? AND Course=? and Semester=? AND Year_=? AND Snumber=?";

		    PreparedStatement stmt = conn.prepareStatement(query);
		    
		    stmt.setString(1, grade);
		    stmt.setString(2, nNumber);
		    stmt.setString(3, course);
		    stmt.setString(4, semester);
		    stmt.setBigDecimal(5, year);
		    stmt.setInt(6, sNumber);
		    
		    stmt.executeQuery();  
		
		    stmt.close();
		    
			DBC.disconnect();
		}
	}
}
