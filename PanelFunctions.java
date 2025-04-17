import java.awt.Component;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelFunctions
{
	public static void populatePanel(String[] dropDownOptions, String[][] fields, JPanel panel)
	{
		panel.removeAll();
		
		for (String[] fieldInfo : fields)
		{
			String fieldName = fieldInfo[0];
			String fieldType = fieldInfo[1];
			
			JLabel fieldLabel = new JLabel(fieldName);
			fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			panel.add(fieldLabel);
			
			if (fieldType.equals("Text"))
			{
				JTextField fieldInput = new JTextField();
				fieldInput.setName(fieldName);
				
				panel.add(fieldInput);
			}
			else if (fieldType.equals("Dropdown"))
			{
				int start = Integer.parseInt(fieldInfo[2]);
				int end = Integer.parseInt(fieldInfo[3]);
				
				JComboBox<String> fieldInput = new JComboBox<>(Arrays.copyOfRange(dropDownOptions, start, end + 1));
				fieldInput.setName(fieldName);
				
				panel.add(fieldInput);
			}
		}
	}
}
