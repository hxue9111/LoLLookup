import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import dataHandling.DataGrabber;

public class LoLTools extends JFrame {
	JPanel home, timers,gamelookup;

	final Container ui=this.getContentPane();

	CardLayout layout = new CardLayout() {
		public Dimension preferredLayoutSize(Container parent) {
			parent.validate();
			Component current = findCurrentComponent(parent);
			if (current != null) {
				Insets insets = parent.getInsets();
				Dimension pref = current.getPreferredSize();
				pref.width += insets.left + insets.right;
				pref.height += insets.top + insets.bottom;
				return pref;
			}
			return super.preferredLayoutSize(parent);
		}

		public Component findCurrentComponent(Container parent) {
			for (Component comp : parent.getComponents()) {
				if (comp.isVisible()) {
					return comp;
				}
			}
			return null;
		}

	};	
	LoLTools() {
		ui.setLayout(layout);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			message(e.getLocalizedMessage());
			
		}
		JOptionPane.showMessageDialog(this, "LoL Tools v0.1\n\nKnown Problems:\n-Screen resize issue upon game lookup\n\nActive Game Lookup powered by Elophant.com\nUtilizes JSON.org JSON Library\nWritten by Huang Xue\nSend bugs and issues to Huangxue9111@gmail.com");
		
		init();
	}
	
	public void init(){
		home = new JPanel();
		
		JButton initGameLookup = new JButton("Active Game Lookup");
		initGameLookup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showGameLookup();
			}
		});
		home.add(initGameLookup);
		
		JButton initTimers = new JButton("Jungle Timers");
		initTimers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showTimers();
			}
		});
		home.add(initTimers);
		
		getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "ESCAPE");
		getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {

				showHome();
			}
			
			
		});
		
		ui.add(home,"home");
		
		showHome();
		setLocationRelativeTo(null);
		setIconImage(new ImageIcon(getClass().getClassLoader().getResource("Images/loltools.png")).getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	public void showHome() {
		layout.show(ui, "home");
		setTitle("LoL Tools v0.1");
		pack();
		setAlwaysOnTop(false);
	}

	public void showTimers() {
		if (timers == null) {
			timers = new JungleTimers();
			ui.add(timers,"timers");
		}
		layout.show(ui, "timers");
		setTitle("Jungle Timers");
		pack();
		setAlwaysOnTop(true);

	}
	public void showGameLookup() {
		if (gamelookup == null) {
			gamelookup = new GameLookup(this,layout);
			ui.add(gamelookup,"Active Game Lookup");
		}
		layout.show(ui, "Active Game Lookup");
		setTitle("Active Game Lookup");
		pack();
		setAlwaysOnTop(true);
		setResizable(true);
	}

	public static void message(String error){
		JOptionPane.showMessageDialog(LoLTools.getFrames()[0], error);
	}
	
	public static void main(String args[]) {
		LoLTools a = new LoLTools();
	}

}
