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
import javax.swing.border.LineBorder;

public class PrereqApp extends JPanel
{
	private ComponentMap components;
	private JPanel list;
	
	// Construct Application
	
	public PrereqApp()
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		// Student N-Number
		
		AppTextField nNumber = new AppTextField();
		nNumber.setName("N-Number");
		
		// Course Code
		
		AppTextField code = new AppTextField();
		code.setName("Course Code");
		
		// Submit Button
		
		JButton submit = new JButton("Check Prerequisite Satisfaction");
		
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
		
		// Satisfaction Panel
		
		this.list = new JPanel();
		this.list.setLayout(new BoxLayout(this.list, BoxLayout.Y_AXIS));
		this.list.setBorder(new LineBorder(Color.GREEN));
		
		// Add Components
		
		this.add(new JLabel("Student N-Number:"));
		this.add(nNumber);
		
		this.add(new JLabel("Course Code:"));
		this.add(code);
		
		this.add(submit);
		
		this.add(this.list);
		
		// Component Map
		
		this.components = new ComponentMap(this);
	}
	
	// Evaluate Input
	
	private boolean evaluate()
	{
		return this.components.evaluateTextComponents();
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
		String code = (String) this.components.getValue("Course Code");
		
		// Check Prerequisite Satisfaction
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String prereqQuery = "SELECT Pnumber FROM PREREQUISITE WHERE Cnumber=?";
		
		PreparedStatement prereqStmt = conn.prepareStatement(prereqQuery);
		
		prereqStmt.setString(1, code);
		
		ResultSet prereqSet = prereqStmt.executeQuery();
		
		// List Satisfied/Unsatisfied Courses
		
		boolean satisfied = true;
		
		while (prereqSet.next())
		{
			String prereq = prereqSet.getString("Pnumber");
			
			String satisfyQuery = "SELECT Grade FROM ENROLLED_IN WHERE Student=? AND Course=?";
			
			PreparedStatement satisfyStmt = conn.prepareStatement(satisfyQuery);
			
			satisfyStmt.setString(1, nNumber);
			satisfyStmt.setString(2, prereq);
			
			ResultSet courses = satisfyStmt.executeQuery();
			
			int count = DBC.queryCount("FROM ENROLLED_IN WHERE Student='" + nNumber + "' AND Course='" + prereq + "'");
			
			if (count > 0)
			{
				String letterGrade = "F";
				double courseGpa = 0;
				
				while (courses.next())
				{
					String currentLetterGrade = courses.getString("Grade");
					double currentCourseGpa = Utilities.letterToGPA(currentLetterGrade);
					
					if (currentCourseGpa > courseGpa)
					{
						letterGrade = currentLetterGrade;
						courseGpa = currentCourseGpa;
					}
				}
				
				if (courseGpa >= 2)
				{
					this.list.add(new JLabel(prereq + ": Satisfied with \"" + letterGrade + "\""));
				}
				else
				{
					this.list.add(new JLabel(prereq + ": Not Satisfied with \"" + letterGrade + "\""));
					
					satisfied = false;
				}
			}
			else
			{
				this.list.add(new JLabel(prereq + ": Not Satisfied"));
				
				satisfied = false;
			}
			
			satisfyStmt.close();
			
			courses.close();
		}
		
		prereqSet.close();
		
		prereqStmt.close();
		
		DBC.disconnect();
		
		if (satisfied)
		{
			this.list.add(new JLabel(code + ": Prerequisites are Satisfied"));
			
		}
		else
		{
			this.list.add(new JLabel(code + ": Prerequisites are Not Satisfied"));
		}
		
		this.list.updateUI();
		this.updateUI();
	}
}
