import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class JungleTimers extends JPanel {
	public TimerLabel BaronLabel, DragonLabel, GolemLabel, LizardLabel,
			WolfLabel, WraithLabel;

	JungleTimers() {
		BaronLabel = new TimerLabel(new ImageIcon(getClass().getClassLoader()
				.getResource("Images/BaronIcon.png")), 420);
		DragonLabel = new TimerLabel(new ImageIcon(getClass().getClassLoader()
				.getResource("Images/DragonIcon.png")), 360);
		GolemLabel = new TimerLabel(new ImageIcon(getClass().getClassLoader()
				.getResource("Images/GolemIcon.png")), 300);
		LizardLabel = new TimerLabel(new ImageIcon(getClass().getClassLoader()
				.getResource("Images/LizardIcon.png")), 300);
		WolfLabel = new TimerLabel(new ImageIcon(getClass().getClassLoader()
				.getResource("Images/WolfIcon.png")), 60);
		WraithLabel = new TimerLabel(new ImageIcon(getClass().getClassLoader()
				.getResource("Images/WraithIcon.png")), 50);

		JPanel content = new JPanel();

		content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
		content.add(WolfLabel);
		content.add(WraithLabel);
		content.add(GolemLabel);
		content.add(LizardLabel);
		content.add(DragonLabel);
		content.add(BaronLabel);

		add(content);

	}

	class TimerLabel extends JLabel {

	long countdown;
	final int respawnTime;
	Timer timer = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			countdown--;
			drawTime();
		}
	});

	TimerLabel(ImageIcon img, int respawn) {
		super(img);
		respawnTime = respawn;
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				timer.start();
				resetTime();
				drawTime();
			}
		});
		setForeground(Color.white);
		setHorizontalTextPosition(CENTER);
		setVerticalTextPosition(CENTER);
	}

	public void drawTime() {
		setFont(new Font("Arial", Font.PLAIN, 40));
		setText(String.valueOf(countdown));
		if (countdown == 0){
			setText("");
			timer.stop();
		}
	}

	public void resetTime() {
		countdown = respawnTime;
	}
}

}
