import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class RSA_Frame extends JFrame
{
	private JTextArea enter, result;
	private JRadioButton e;
	private JButton code;
	
	public RSA_Frame()
	{
		setSize(500, 350);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		
		e = new JRadioButton("Encrypt", true);
		JRadioButton d = new JRadioButton("Decrypt");
		ButtonText l = new ButtonText();
		e.addActionListener(l);
		d.addActionListener(l);
		ButtonGroup g = new ButtonGroup();
		JPanel buttons = new JPanel();
		g.add(e);
		g.add(d);
		buttons.add(e);
		buttons.add(d);
		panel.add(buttons, c);
		
		c.gridy++;
		enter = new JTextArea();
		enter.setPreferredSize(new Dimension(300, 100));
		enter.setMinimumSize(new Dimension(300, 100));
		panel.add(enter, c);
		
		c.gridy++;
		code = new JButton("Encrypt");
		code.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					if(enter.getText().equals("")) return;
					try
					{
						if(e.isSelected()) result.setText(RSA.convertToString(RSA.encrypt(enter.getText())));
						else result.setText(RSA.decrypt(RSA.convertFromString(enter.getText())));
					}
					catch (FileNotFoundException e1) 
					{
						result.setText("Error in accessing key.");
					}
					result.setPreferredSize(new Dimension(300, 100));
				}
			});
		panel.add(code, c);
		
		c.gridy++;
		panel.add(Box.createVerticalStrut(10), c);
		c.gridy++;
		result = new JTextArea();
		result.setPreferredSize(new Dimension(300, 100));
		result.setMinimumSize(new Dimension(300, 100));
		panel.add(result, c);
		
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private class ButtonText implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			if(e.isSelected()) code.setText("Encrypt");
			else code.setText("Decrypt");
		}
	}
}