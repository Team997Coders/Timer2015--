package org.chsrobotics.projects.timer2015;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris G. on 2/10/2015.
 */
public class Main {
    public static final String start = "";
    public static final String endAuto = "";
    public static final String endTeleop = "";
    public static final String end = "";

    private long time = System.currentTimeMillis();

    Timer t1, t2, t3, t4;
    public static void main(String[] args) {
        new Main();
    }
    public Main() {
        JFrame frame = new JFrame("Timer Testing");
        JPanel panel = new JPanel(true) {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.black);
                //if(t1.isRunning())
                    g.drawString(((double) (System.currentTimeMillis() - time)) / 1000.0 + " seconds", 70, 100);
                repaint();
            }
        };
        JButton startButton = new JButton("Start");
        t1 = new Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(start);
            }
        });
        t2 = new Timer(15 * 1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(endAuto);
            }
        });
        t3 = new Timer(135 * 1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(endTeleop);
            }
        });
        t4 = new Timer(150 * 1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(end);
            }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (t1.isRunning()) {
                    t1.restart();
                    t2.restart();
                    t3.restart();
                    t4.restart();
                } else {
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                }
                time = System.currentTimeMillis();
            }
        });
        panel.add(startButton);
        frame.add(panel);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void playSound(String location) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(location));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
