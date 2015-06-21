import java.awt.BorderLayout;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;
import dataHandling.*;


public class GameLookup extends JPanel {
	GridBagConstraints c;
	JPanel search;
	LookupPanel stats;
	ImageIcon loadinGIF;
	JTextField nameinput;
	JComboBox regioninput;
	LoLTools frame;
	CardLayout layout;
	GameLookup(LoLTools f, CardLayout l) {
		layout=l;
		frame=f;
		setLayout(layout);
		c = new GridBagConstraints();
		loadinGIF = new ImageIcon(getClass().getClassLoader().getResource("Images/loading.gif"));
		search = new JPanel(new GridBagLayout());
		initSearch();
	}

	public void initSearch() {
		c.fill=GridBagConstraints.HORIZONTAL;
		c.weightx=.3;
		c.weighty=.5;
		this.add(search, "search");
		c.gridx = 0;
		c.gridy = 0;
		search.add(new JLabel("Enter Summoner Name: "), c);
		c.gridx = 1;
		c.gridy = 0;
		nameinput = new JTextField(16);

		nameinput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
		nameinput.getActionMap().put("ENTER", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				loadStats(nameinput.getText(),regioninput.getSelectedItem().toString());
			}
		});
		search.add(nameinput,c);
		
		c.gridx = 2;
		c.gridy = 0;
		regioninput = new JComboBox(new String[] {DataGrabber.REGION_NA,DataGrabber.REGION_EUW,DataGrabber.REGION_EUNE,DataGrabber.REGION_BR});
		search.add(regioninput,c);
		
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.showHome();
			}
		});
		back.setFocusable(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		search.add(back, c);
		
		JButton startsearch = new JButton("Lookup Game");
		startsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadStats(nameinput.getText(),regioninput.getSelectedItem().toString());
			}
		});
		startsearch.setFocusable(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		search.add(startsearch, c);
	}

	public void loadStats(String summoner,String region) {
		getRootPane().getGlassPane().setVisible(true);
		getRootPane().setGlassPane(new lookup(summoner));
		
		LoadLookupPanel loader =new LoadLookupPanel(summoner,region);
		loader.execute();
	}

	public void showSearch() {
		layout.show(this, "search");
		//System.out.println("ss "+layout.preferredLayoutSize(this).height+"   "+layout.preferredLayoutSize(this).width);
		frame.setPreferredSize(layout.preferredLayoutSize(this));
	}

	public void showStats() {
		layout.show(this, "stats");
		//System.out.println("SS2 "+layout.preferredLayoutSize(this).height+"   "+layout.preferredLayoutSize(this).width);
		frame.setPreferredSize(layout.preferredLayoutSize(getParent()));
	
	}
	class LoadLookupPanel extends SwingWorker<LookupPanel, Object> {
		String s;
		String r;
		LoadLookupPanel(String summoner,String region) {
			s = summoner;
			r=region;
		}

		protected LookupPanel doInBackground() throws Exception {

			return new LookupPanel(DataGrabber.getInProgressGameInfo(s,r),c,layout);

		}

		protected void done() {
			try {
				stats = get();
				add(stats, "stats");

				showStats();
			} catch (Exception e){
				System.out.println("Error in LoadLookupPanel");
				LoLTools.message("No active game found for "+nameinput.getText());
			}
			getRootPane().getGlassPane().setVisible(false);
			
		}
	}

	class lookup extends JComponent {
		lookup(String summoner) {
			this.setLayout(new BorderLayout());
			JLabel loading = new JLabel(loadinGIF, JLabel.CENTER);
			loading.setHorizontalTextPosition(JLabel.CENTER);
			loading.setVerticalTextPosition(JLabel.TOP);
			loading.setText("Looking up " + summoner + "\n");
			add(loading, BorderLayout.CENTER);
		}

		public void paint(Graphics g) {
			g.setColor(new Color(192, 192, 192, 188));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			super.paint(g);
		}
	}

}
