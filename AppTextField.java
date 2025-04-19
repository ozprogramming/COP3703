import java.util.regex.Pattern;

import javax.swing.JTextField;

public class AppTextField extends JTextField
{
	private String regex;
	
	public AppTextField(String regex)
	{
		this.regex = regex;
	}
	
	public AppTextField()
	{
		this.regex = ".*";
	}
	
	public boolean evaluate()
	{
		boolean matches = Pattern.matches(regex, this.getText());
		
		return matches;
	}
}
