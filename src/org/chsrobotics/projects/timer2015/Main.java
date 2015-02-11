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

    public static final int timeAuto = 1000 * 15;
    public static final int timeTeleop = 1000 * 120;
    public static final int timeEnding = 1000 * 15;

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
                if(t4.isRunning()) {
                    g.drawString("Total: " + ((double) (System.currentTimeMillis() - time)) / 1000.0 + " seconds", 70, 100);
                    if (t2.isRunning())
                        g.drawString("To End of Auto: " + ((double) (timeAuto - (System.currentTimeMillis() - time))) / 1000.0, 70, 130);
                    else if (t3.isRunning())
                        g.drawString("To End of Teleop: " + ((double) (timeTeleop + timeAuto - (System.currentTimeMillis() - time))) / 1000.0, 70, 130);
                    if (t4.isRunning()) {
                        if((t2.isRunning() || t3.isRunning()))
                            g.drawString("To End of Match: " + ((double) (timeEnding + timeTeleop + timeAuto - (System.currentTimeMillis() - time))) / 1000.0, 70, 160);
                        else
                            g.drawString("To End of Match: " + ((double) (timeEnding + timeTeleop + timeAuto - (System.currentTimeMillis() - time))) / 1000.0, 70, 130);
                    }
                }
                repaint();
            }
        };
        JButton startButton = new JButton("Start");
        t1 = new Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(start);
                t1.stop();
            }
        });
        t2 = new Timer(timeAuto, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(endAuto);
                t2.stop();
            }
        });
        t3 = new Timer(timeTeleop + timeAuto, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(endTeleop);
                t3.stop();
            }
        });
        t4 = new Timer(timeEnding + timeTeleop + timeAuto, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSound(end);
                t4.stop();
            }
        });
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (t4.isRunning()) {
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
