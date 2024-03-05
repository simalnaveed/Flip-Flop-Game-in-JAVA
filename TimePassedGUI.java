import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TimePassedGUI extends JFrame {
    private JLabel timeLabel;
    private Timer timer;

    public TimePassedGUI(int timePassedInSeconds) {
        // Set up the frame
        setTitle("Time Passed");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and configure the time label
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(timeLabel);

        // Convert time passed to minute:second format
        int minutes = timePassedInSeconds / 60;
        int seconds = timePassedInSeconds % 60;
        String timePassed = String.format("%02d:%02d", minutes, seconds);

        // Set the time passed
        timeLabel.setText("Time Passed: " + timePassed);

        // Create a timer to dispose of the frame after 2 seconds
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        int timePassedInSeconds = 150;

        // Create and display the GUI
        java.awt.EventQueue.invokeLater(() -> {
            new TimePassedGUI(timePassedInSeconds).setVisible(true);
        });
    }
}
