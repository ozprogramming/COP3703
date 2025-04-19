import java.util.regex.Pattern;

import javax.swing.text.JTextComponent;

public class AppTextField<T extends JTextComponent> extends JTextComponent
{
	private String regex;
	private T textField;
	
	public AppTextField(String regex, T textField)
	{
		this.regex = regex;
		this.textField = textField;
	}
	
	public AppTextField(T textField)
	{
		this.regex = ".*";
		this.textField = textField;
	}
	
	@Override
	public void setName(String name)
	{
		textField.setName(name);
	}
	
	@Override
	public String getName()
	{
		return this.textField.getName();
	}
	
	public boolean evaluate()
	{
		boolean matches = Pattern.matches(regex, textField.getText());
		
		return matches;
	}
}
