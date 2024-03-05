import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FlipFlopGame {
    private JFrame frame;
    private JButton[] buttons;
    private ImageIcon[] images;
    private int[] buttonValues;
    private int previousButtonIndex;
    private boolean canFlip;
    private int flips;
    public int time;
    public long startTime;


    public FlipFlopGame() {

		flips = 0;
		previousButtonIndex=-1;
        frame = new JFrame();
        frame.setTitle("Flip Flop Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        // Load images
        images = new ImageIcon[8];
        images[0] = new ImageIcon("images/img1.png");
        images[1] = new ImageIcon("images/img2.png");
        images[2] = new ImageIcon("images/img3.png");
        images[3] = new ImageIcon("images/img4.png");
        images[4] = new ImageIcon("images/img5.png");
        images[5] = new ImageIcon("images/img6.png");
        images[6] = new ImageIcon("images/img7.png");
        images[7] = new ImageIcon("images/img8.png");
        ImageIcon facedown = new ImageIcon("images/img9.png");

        // Create buttons
        buttons = new JButton[16];
        buttonValues = new int[16];
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            positions.add(i);
        }

        Collections.shuffle(positions);

        JPanel panel = new JPanel(new GridLayout(4, 4));
        for (int i = 0; i < 16; i++) {
            int position = positions.get(i);
            //System.out.println(position); System.out.println(i/2);

            buttons[position] = new JButton();
            buttons[position].setIcon(facedown);
            buttons[position].setDisabledIcon(images[i / 2]);

            buttonValues[position] = i / 2; // Assign values to buttons (0, 0, 1, 1, 2, 2, ...)

            buttons[position].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    actionPerformedHandler(buttons[position], position);
                }
            });


        }

        for(int i = 0; i < 16; i++){
			panel.add(buttons[i]);
			//System.out.println(buttonValues[i]);
		}



        // Add panel to the frame
        frame.add(panel);

        frame.setVisible(true);
        canFlip = true;
        startTime = System.currentTimeMillis();

        revealAllTiles(); // Display all tiles initially
    }

    private void actionPerformedHandler(JButton button, int buttonIndex) {
        // Handle button click
        if (!canFlip) {
            return; // If flipping is not allowed, do nothing
        }

        // Flip the button
        button.setEnabled(false);

        // Check if it's the first button or the second button being flipped
        if (previousButtonIndex == -1) {
            previousButtonIndex = buttonIndex;
        } else {
			ImageIcon facedown = new ImageIcon("images/img9.png");

            canFlip = false;

            // Check if the values of the buttons match
            if (buttonValues[previousButtonIndex] == buttonValues[buttonIndex]) {

				flips ++;
				if(flips == 8) {
					long endTime = System.currentTimeMillis();
                    time = (int) ((endTime - startTime) / 1000);
                    System.out.println(time);
                    java.awt.EventQueue.invokeLater(() -> {
						new TimePassedGUI(time).setVisible(true);
     			   });
					Timer timer = new Timer(1000, new ActionListener() {
					                    public void actionPerformed(ActionEvent e) {
											frame.dispose();

					                    }
					                });
					timer.setRepeats(false);
               		 timer.start();
				}
				else{
					// Matched, keep the buttons face up
					previousButtonIndex = -1;
					canFlip = true;
				}
            }
            else {
                // Not matched, flip the buttons back after a delay
                final int prevIndex = previousButtonIndex;
                final int currIndex = buttonIndex;
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttons[prevIndex].setEnabled(true);
                        buttons[currIndex].setEnabled(true);
                        buttons[prevIndex].setIcon(facedown);
                        buttons[currIndex].setIcon(facedown);
                        previousButtonIndex = -1;
                        canFlip = true;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }
    private void revealAllTiles() {
			ImageIcon facedown = new ImageIcon("images/img9.png");
	        canFlip = false;

	        for (int i = 0; i < buttons.length; i++) {
	            buttons[i].setIcon(images[buttonValues[i]]);
	        }

	        Timer timer = new Timer(1000, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                for (int i = 0; i < buttons.length; i++) {
	                    buttons[i].setIcon(facedown);
	                }
	                canFlip = true;
	            }
	        });
	        timer.setRepeats(false);
	        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FlipFlopGame();
            }
        });
    }
}
