import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ReportForm
{
	JPanel panel;
	
	public ReportForm()
	{
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
		this.panel.setBorder(new LineBorder(Color.GREEN));
	}
	
	public void fill()
	{
		
	}
}
