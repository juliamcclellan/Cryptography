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
import javax.swing.JTextPane;

public class RSA_Frame extends JFrame
{
	private JTextPane enter, result;
	private JRadioButton e;
	private JButton code;
	private RSA_Frame other; //The person to send and receive messages from
	
	public RSA_Frame(String name, boolean canDecrypt, RSA_Frame other)
	{
		super(name);
		this.other = other;
		setSize(500, 350);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTH;
		
		e = new JRadioButton("Encrypt", true);
		if(canDecrypt)
		{	
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
		}
		
		c.gridy++;
		enter = new JTextPane();
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
		result = new JTextPane();
		result.setPreferredSize(new Dimension(300, 100));
		result.setMinimumSize(new Dimension(300, 100));
		panel.add(result, c);
		
		if(!canDecrypt)
		{
			c.gridy++;
			JButton send = new JButton("Send");
			send.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					other.receive(result.getText());
				}
			});
			panel.add(send, c);
		}
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void receive(String m)
	{
		enter.setText(m);
		result.setText("");
	}
	
	public void addUser(RSA_Frame other)
	{
		this.other = other;
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