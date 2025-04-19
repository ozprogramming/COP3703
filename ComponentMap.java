import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

public class ComponentMap
{
	private HashMap<String, Component> componentMap;
	
	public ComponentMap(JPanel panel)
	{
		this.componentMap = new HashMap<String, Component>();
        
        this.put(panel);
	}
	
	public ComponentMap()
	{
		this.componentMap = new HashMap<String, Component>();
	}
	
	public void put(JPanel panel)
	{
		Component[] components = panel.getComponents();
        
        for (int i = 0; i < components.length; i++)
        {
        	if (components[i].getName() != null)
        	{
        		componentMap.put(components[i].getName(), components[i]);
        	}
        }
	}
	
	public void append(Component component)
	{
		componentMap.put(component.getName(), component);
	}
	
	public boolean evaluateTextComponents()
	{
		for (Component component : this.componentMap.values())
		{
			if (component instanceof AppTextField && !component.getName().equals(null))
			{
				if (!((AppTextField) component).evaluate())
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Component getComponent(String name)
	{
		if (componentMap.containsKey(name))
		{
			return (Component) componentMap.get(name);
		}
		else return null;
	}
	
	public void clear()
	{
		componentMap.clear();
	}
}
