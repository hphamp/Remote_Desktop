package UIRemote;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class nhappp {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("JOptionPane Demo");
        JRadioButton button1 = new JRadioButton(".pdf");
        JRadioButton button2 = new JRadioButton(".docx");
        JRadioButton button3 = new JRadioButton(".txt");
        JRadioButton button4 = new JRadioButton(".pptx");
        JButton chooseFile = new JButton("Choose File");
        JButton cancel = new JButton("Cancel");
        JPanel panel = new JPanel();


        // implement ItemListener interface
        class MyItemListener implements ItemListener {

            public void itemStateChanged(ItemEvent e) {
                if(button1.isSelected()){
                    System.out.println(button1.getText());
                }
                if(button2.isSelected()){
                    System.out.println(button2.getText());
                }
                if(button3.isSelected()){
                    System.out.println(button3.getText());
                }
                if(button4.isSelected()){
                    System.out.println(button4.getText());
                }
            }
        }



        // add event handler
        MyItemListener myItemListener = new MyItemListener();
        button1.addItemListener(myItemListener);
        button2.addItemListener(myItemListener);
        button3.addItemListener(myItemListener);
        button4.addItemListener(myItemListener);

        // add radio buttons to a ButtonGroup
        final ButtonGroup group = new ButtonGroup();
        group.add(button1);
        group.add(button2);
        group.add(button3);
        group.add(button4);

        // Frame setting
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        Container cont = frame.getContentPane();

        cont.setLayout(new GridLayout(0, 1));
        cont.add(new JLabel("Choose type file you send !!!"));
        cont.add(button1);
        cont.add(button2);
        cont.add(button3);
        cont.add(button4);
        panel.add(chooseFile);
        panel.add(cancel);
        cont.add(panel);
        panel.setVisible(true);

        frame.setVisible(true);
    }
}

//public class nhappp {
//    public static void main(String[] args){
//                final JPanel panel = new JPanel();
//                panel.setSize(400,400);
//                final JLabel lb = new JLabel("Choose type file you send !!!");
//        final JRadioButton button1 = new JRadioButton(".pdf");
//        final JRadioButton button2 = new JRadioButton(".docx");
//        final JRadioButton button3 = new JRadioButton(".txt");
//        final JRadioButton button4 = new JRadioButton(".pptx");
//
//        panel.add(lb);
//        panel.add(button1);
//                panel.add(button2);
//        panel.add(button3);
//        panel.add(button4);
//        ImageIcon icon = new ImageIcon("image\\question.png");
//
//                JOptionPane.showMessageDialog(null, panel,"",JOptionPane.INFORMATION_MESSAGE,icon);
//
//
//    }
//}
