package hitthemole;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class HitTheMoleGame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel panel;
    private JButton startButton;
    private JLabel scoreLabel, timerLabel;
    private int score;
    private int moleX, moleY;
    private boolean moleVisible;
    private Timer moleTimer, gameTimer;
    private int timeLeft;

    public HitTheMoleGame() {
        setTitle("Hit the Mole Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame(g);
            }
        };

        panel.setPreferredSize(new Dimension(800, 600));
        panel.addMouseListener(new MouseAdapter() {
            
            public void mousePressed(MouseEvent e) {
                if (moleVisible && new Rectangle(moleX - 25, moleY - 25, 50, 50).contains(e.getPoint())) {
                    score++;
                    scoreLabel.setText("Score: " + score);
                    moleVisible = false;
                    panel.repaint();
                }
            }
        });

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        scoreLabel = new JLabel("Score: 0");
        timerLabel = new JLabel("Time: 30");
        score = 0;
        timeLeft = 30;

        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(scoreLabel);
        controlPanel.add(timerLabel);

        add(panel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        moleTimer = new Timer(1000, new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                moleVisible = !moleVisible;
                if (moleVisible) {
                    Random rand = new Random();
                    int hole = rand.nextInt(6);
                    moleX = 150 + (hole % 3) * 200;
                    moleY = 150 + (hole / 3) * 200;
                }
                panel.repaint();
            }
        });

        gameTimer = new Timer(1000, new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time: " + timeLeft);
                if (timeLeft <= 0) {
                    endGame();
                }
            }
        });
    }

    private void startGame() {
        score = 0;
        scoreLabel.setText("Score: " + score);
        timeLeft = 30;
        timerLabel.setText("Time: " + timeLeft);
        moleVisible = false;
        moleTimer.start();
        gameTimer.start();
    }

    private void endGame() {
        moleTimer.stop();
        gameTimer.stop();
        moleVisible = false;
        panel.repaint();
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
    }

    private void drawGame(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        g.setColor(Color.gray);
        for (int i = 150; i <= panel.getWidth() - 150; i += 200) {
            for (int j = 150; j <= panel.getHeight() - 150; j += 200) {
                g.fillOval(i - 50, j - 50, 100, 100);
            }
        }

        if (moleVisible) {
            g.setColor(new Color(139, 69, 19)); // Brown color for the mole
            g.fillOval(moleX - 25, moleY - 25, 50, 50);

            // Draw eyes
            g.setColor(Color.white);
            g.fillOval(moleX - 15, moleY - 15, 10, 10);
            g.fillOval(moleX + 5, moleY - 15, 10, 10);

            g.setColor(Color.black);
            g.fillOval(moleX - 12, moleY - 12, 5, 5);
            g.fillOval(moleX + 8, moleY - 12, 5, 5);

            // Draw mouth
            g.setColor(Color.black);
            g.drawArc(moleX - 10, moleY - 5, 20, 10, 0, -180);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
           
            public void run() {
                new HitTheMoleGame().setVisible(true);
            }
        });
    }
}
