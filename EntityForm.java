import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class EntityForm
{
	JPanel panel;
	
	public EntityForm()
	{
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
		this.panel.setBorder(new LineBorder(Color.GREEN));
	}
	
	public void fill(String entityName)
	{
		if (entityName.equals("Student"))
		{	
			String[] dropDownOptions =
			{
				"Alabama",
				"Alaska",
				"Arizona",
				"Arkansas",
				"California",
				"Colorado",
				"Connecticut",
				"Delaware",
				"Florida",
				"Georgia",
				"Hawaii",
				"Idaho",
				"Illinois",
				"Indiana",
				"Iowa",
				"Kansas",
				"Kentucky",
				"Louisiana",
				"Maine",
				"Maryland",
				"Massachusetts",
				"Michigan",
				"Minnesota",
				"Mississippi",
				"Missouri",
				"Montana",
				"Nebraska",
				"Nevada",
				"New Hampshire",
				"New Jersey",
				"New Mexico",
				"New York",
				"North Carolina",
				"North Dakota",
				"Ohio",
				"Oklahoma",
				"Oregon",
				"Pennsylvania",
				"Rhode Island",
				"South Carolina",
				"South Dakota",
				"Tennessee",
				"Texas",
				"Utah",
				"Vermont",
				"Virginia",
				"Washington",
				"West Virginia",
				"Wisconsin",
				"Wyoming",
				"Freshman",
				"Sophomore",
				"Junior",
				"Senior",
				"B.A.",
				"B.S.",
				"B.F.A",
				"M.A.",
				"M.S.",
				"M.B.A.",
				"Ph.D.",
				"Ed.D.",
				"J.D."
			};
			
			String[][] fields = 
			{
				{"N-Number", "Text", "[0-9]{8}"},
				{"SSN", "Text", "[0-9]{9}"},
				{"First Name", "Text", "[A-Z][a-z]{0,14}"},
				{"Middle Initial", "Text", "[A-Z]|[a-z]"},
				{"Last Name", "Text", "[A-Z][a-z]{0,14}"},
				{"Phone Number", "Text", "[0-9]{10}"},
				{"Permanent Street", "Text", ".{20}"},
				{"Permanent City", "Text", ".{30}"},
				{"Permanent State", "Dropdown", "0", "49"},
				{"Permanent ZIP", "Text", "[0-9]{5}"},
				{"Current Street", "Text", ".{20}"},
				{"Current City", "Text", ".{30}"},
				{"Current State", "Dropdown", "0", "49"},
				{"Current ZIP", "Text", "[0-9]{5}"},
				{"Class", "Dropdown", "50", "53"},
				{"Degree Program", "Dropdown", "54", "62"},
				{"Major Department", "Dropdown", "63", String.valueOf(dropDownOptions.length - 1)},
				{"Minor Department", "Dropdown", "63", String.valueOf(dropDownOptions.length - 1)}
			};
			
			PanelFunctions.populatePanel(dropDownOptions, fields, this.panel);
		}
		else if (entityName.equals("Instructor"))
		{
			String[] dropDownOptions =
				{
					"Alabama",
					"Alaska",
					"Arizona",
					"Arkansas",
					"California",
					"Colorado",
					"Connecticut",
					"Delaware",
					"Florida",
					"Georgia",
					"Hawaii",
					"Idaho",
					"Illinois",
					"Indiana",
					"Iowa",
					"Kansas",
					"Kentucky",
					"Louisiana",
					"Maine",
					"Maryland",
					"Massachusetts",
					"Michigan",
					"Minnesota",
					"Mississippi",
					"Missouri",
					"Montana",
					"Nebraska",
					"Nevada",
					"New Hampshire",
					"New Jersey",
					"New Mexico",
					"New York",
					"North Carolina",
					"North Dakota",
					"Ohio",
					"Oklahoma",
					"Oregon",
					"Pennsylvania",
					"Rhode Island",
					"South Carolina",
					"South Dakota",
					"Tennessee",
					"Texas",
					"Utah",
					"Vermont",
					"Virginia",
					"Washington",
					"West Virginia",
					"Wisconsin",
					"Wyoming"
				};
				
				String[][] fields = 
				{
					{"N-Number", "Text", "[0-9]{8}"},
					{"SSN", "Text", "[0-9]{9}"},
					{"First Name", "Text", "[A-Z][a-z]{0,14}"},
					{"Middle Initial", "Text", "[A-Z]|[a-z]"},
					{"Last Name", "Text", "[A-Z][a-z]{0,14}"},
					{"Phone Number", "Text", "[0-9]{10}"},
					{"Street", "Text", ".{20}"},
					{"City", "Text", ".{30}"},
					{"State", "Dropdown", "0", "49"},
					{"ZIP", "Text", "[0-9]{5}"},
					{"Department", "Dropdown", "50", String.valueOf(dropDownOptions.length - 1)},
					{"Office Number", "Text", "[0-9]{4}"},
				};
				
				PanelFunctions.populatePanel(dropDownOptions, fields, this.panel);
		}
		else if (entityName.equals("Department"))
		{					
			String[] dropDownOptions = {};
			
			String[][] fields = 
			{
				{"Department Code", "Text", "[A-Z]{1,4}"},
				{"Department Name", "Text", ".{1,30}"},
				{"Office Number", "Text", "[0-9]{4}"},
				{"College", "Text", ".{1,30}"},
				{"Office Phone Number", "Text", "[0-9]{10}"},
			};
			
			PanelFunctions.populatePanel(dropDownOptions, fields, this.panel);
		}
		else if (entityName.equals("Course"))
		{		
			String[] dropDownOptions = {
				"1000",
				"2000",
				"3000",
				"4000",
				"5000",
				"6000",
				"7000",
				"1",
				"2",
				"3",
				"4",
				"5"
			};
			
			String[][] fields = 
			{
				{"Course Number", "Text", "[A-Z]{3}[0-9]{4}"},
				{"Course Name", "Text", ".{30}"},
				{"Course Description", "Text", ".{50}"},
				{"Department", "Dropdown", "12", String.valueOf(dropDownOptions.length - 1)},
				{"Level", "Dropdown", "0", "6"},
				{"Hours", "Dropdown", "7", "11"}
			};
			
			PanelFunctions.populatePanel(dropDownOptions, fields, this.panel);
		}
		else if (entityName.equals("Section"))
		{
			String[] dropDownOptions = {
				"Fall",
				"Spring",
				"Summer A",
				"Summer B"
			};
				
			String[][] fields = 
			{
				{"Course", "Dropdown", "4", String.valueOf(dropDownOptions.length - 1)}, // Change this later
				{"Instructor", "Dropdown", "4", String.valueOf(dropDownOptions.length - 1)},
				{"Semester", "Dropdown", "0", "3"},
				{"Year", "Text", "[0-9]{4}"}
			};
			
			PanelFunctions.populatePanel(dropDownOptions, fields, this.panel);
		}
		
		JButton insertEntity = new JButton("Insert " + entityName);
		
		this.panel.add(insertEntity);
	}

	public void clear()
	{
		this.panel.removeAll();
	}
	
	public String[] data()
	{
		String[] data = {};
		
		return data;
	}
}
