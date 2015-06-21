import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Debug {
	ImageIcon loadinGIF=new ImageIcon(getClass().getClassLoader().getResource("Images/loading.gif"));
	public static void main(String args[]){
		Debug d=new Debug();
		JFrame frame=new JFrame();
		
//		frame.setContentPane(new GameLookup());
//		frame.add(d.new lookup("hdstarcraft"));
//		frame.getRootPane().getGlassPane().setVisible(true);
//		frame.getRootPane().setGlassPane(d.new lookup("gawd"));
		
		frame.validate();
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
	class lookup extends JComponent{
		lookup(String summoner){
			this.setLayout(new BorderLayout());
			//super("Looking up "+summoner+"\n", loadinGIF,JLabel.CENTER);
			//add(new JLabel("Looking up "+summoner, loadinGIF,JLabel.CENTER),BorderLayout.CENTER);
			JLabel loading=new JLabel(loadinGIF,JLabel.CENTER);
			loading.setHorizontalTextPosition(JLabel.CENTER);
			loading.setVerticalTextPosition(JLabel.TOP);
			loading.setText("Looking up "+summoner+"\n");
			add(loading);
		}
		public void paint(Graphics g){
			g.setColor(new Color(192,192,192,125));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			super.paint(g);
		}
	}
}
