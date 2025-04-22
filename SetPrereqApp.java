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

public class SetPrereqApp extends JPanel
{
	private ComponentMap components;
	
	// Construct Application
	
	public SetPrereqApp()
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		// Course Number
		
		AppTextField cNumber = new AppTextField();
		cNumber.setName("Course Number");
		
		// Prerequisite Number
		
		AppTextField pNumber = new AppTextField();
		pNumber.setName("Prerequisite Number");
		
		// Submit Button
		
		JButton submit = new JButton("Set Prerequisite");
		
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
		
		this.add(new JLabel("Course Number:"));
		this.add(cNumber);
		
		this.add(new JLabel("Prerequisite Number:"));
		this.add(pNumber);
		
		this.add(submit);
		
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
		String cNumber = (String) this.components.getValue("Course Number");
		String pNumber = (String) this.components.getValue("Prerequisite Number");
		
		// Set Prerequisite
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String prereqQuery = "INSERT INTO PREREQUISITE (Cnumber, Pnumber) VALUES (?, ?)";
		
		PreparedStatement prereqStmt = conn.prepareStatement(prereqQuery);
		
		prereqStmt.setString(1, cNumber);
		prereqStmt.setString(2, pNumber);
		
		prereqStmt.executeUpdate();
		
		prereqStmt.close();
		
		DBC.disconnect();
	}
}
