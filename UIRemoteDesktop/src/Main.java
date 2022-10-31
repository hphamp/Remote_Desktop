import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

public class Main {
    public static void main(String args[]) {
        JFrame frame = new JFrame("Selecting Toggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JToggleButton toggleButton = new JToggleButton("On");

        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    toggleButton.setText("Off");
                } else {
                    toggleButton.setText("On");
                }
            }
        };
        // Attach Listeners
        toggleButton.addItemListener(itemListener);
        frame.add(toggleButton, BorderLayout.NORTH);
        frame.setSize(300, 125);
        frame.setVisible(true);
    }
}