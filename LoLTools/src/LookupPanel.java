import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dataHandling.*;
public class LookupPanel extends JPanel{
	GridBagConstraints c;
	CardLayout l;
	GameInfo game;
	LookupPanel(GameInfo g,GridBagConstraints con,CardLayout la){
		super(new GridBagLayout());
		l = la;
		c = con;
		game = g;
		c.weighty = .3;
		c.weightx = .2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = -1;
		c.gridy = 0;
		c.ipadx = 4;
		c.ipady = 4;
		for(Player p: game.getTeamOnePlayers()){
			c.gridx++;
			add(new PlayerLabel(p),c);
		}
		c.gridx = -1;
		c.gridy = 1;
		for(Player p: game.getTeamTwoPlayers()){
			c.gridx++;
			add(new PlayerLabel(p),c);
		}
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l.show(getParent(), "search");

			}
		});
		c.gridwidth = 3;
		c.gridx = 3;
		c.gridy = 3;
		add(back, c);
	}
	public void repaint(){
		super.repaint();
		System.out.println(this.getHeight()+"  "+this.getWidth());
	}
	public class PlayerLabel extends JLabel{
		final Player playa;
		PlayerLabel(Player p){		
			super("<html>Summoner Name: "+p.getsummonerName()+"<br>Level: "+p.getsummonerLevel()+"<br>Solo Queue League: "+p.getsoloLeague()+"<br>Ranked Team 5v5 League: "+p.getfivesLeague()+"<br>Ranked Team 3v3 League: "+p.getthreesLeague()+"</html>");
			playa=p;
		}
	}

}
