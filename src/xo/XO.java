
package xo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
public class XO extends JFrame implements ActionListener {

    byte toWinSameRow = 0;
    byte toWinSameColumn = 0;
    JPanel panel;
    JButton[][] buttons;
    JLabel l1, l2, l3, l4;
    int track = 0;
    boolean click;
    boolean first = true;
    Font font = new Font(Font.SERIF, Font.LAYOUT_LEFT_TO_RIGHT, 40);
//    char play = 'X';
    char[][] play = {{'0', '0', '0', '0'}, {'0', '0', '0', '0'}, {'0', '0', '0', '0'}};

// {'0', '0', '0','0'}
    char nowRes = '0';
    JLabel label1, label2, label3, label4 ,background;

    public XO() {
        super("X-O");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(550, 150, 400, 500);
        super.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("xologo.jpg")));

        
        panel = new JPanel(new GridLayout(3, 3, 10, 10));
        buttons = new JButton[3][3];

        for (int r = 0; r < buttons.length; ++r) {
            for (int c = 0; c < buttons[r].length; ++c) {
                buttons[r][c] = new JButton("");
                buttons[r][c].addActionListener(this);
                buttons[r][c].setBorder(new LineBorder(Color.ORANGE));
                buttons[r][c].setFont(font);
                panel.add(buttons[r][c]);
            }
        }
        ImageIcon img = new ImageIcon("xo.png");
background = new JLabel("",img,JLabel.CENTER);
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();

        panel.setBackground(new Color(150,110,100));
        label1.setOpaque(true);
        label1.setBackground(new Color(100,38,38));
        label2.setOpaque(true);
        label2.setBackground(new Color(100,38,38));
          label3.setOpaque(true);
        label3.setBackground(new Color(100,38,38));
          label4.setOpaque(true);
        label4.setBackground(new Color(100,38,38));

        super.add(panel);

        super.add(label1, BorderLayout.NORTH);
        label1.setPreferredSize(new Dimension(10, 10));

        super.add(label2, BorderLayout.SOUTH);
        label2.setPreferredSize(new Dimension(10, 10));

        super.add(label3, BorderLayout.EAST);
        label3.setPreferredSize(new Dimension(10, 10));

        super.add(label4, BorderLayout.WEST);
        label4.setPreferredSize(new Dimension(10, 10));
        setVisible(true);

    }

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            Logger.getLogger(XO.class.getName()).log(Level.SEVERE, null, ex);
        }
        new XO();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (byte r = 0; r < buttons.length; ++r) {
            for (byte c = 0; c < buttons[r].length; ++c) {
                if (e.getSource() == buttons[r][c]) {
//                    winner(r, c);
                    if (first) {
                        first = false;
                        play[r][c] = 'X';
                        nowRes = play[r][c];
                        buttons[r][c].setForeground((nowRes == 'X') ? Color.BLUE : Color.RED);
                    } else {
                        if (play[r][c] == '0'&&buttons[r][c].getText().trim().equals("")) {
                            play[r][c] = (nowRes == 'X') ? 'O' : 'X';
                            buttons[r][c].setForeground((play[r][c] == 'X') ? Color.BLUE : Color.RED);
                            nowRes = play[r][c];
                        }

                    }
                    buttons[r][c].setText(play[r][c] + "");

                    if (winner(r, c)) {
                        JOptionPane.showMessageDialog(null, play[r][c] + " is WINNER!!");
                        palyAgain();
                    }

                }
            }
        }
        if (checkAllSqures()) {
            JOptionPane.showMessageDialog(null, "#The result is equal#");
            palyAgain();
        }
    }

    public boolean checkAllSqures() {
        for (int r = 0; r < buttons.length; ++r) {
            for (int c = 0; c < buttons[r].length; ++c) {
                if (play[r][c] == '0') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean winner(byte r, byte c) {
        if (sameRow(r, c) || SameCol(r, c) || diagonal()) {
            return true;
        }

        return false;
    }

    private boolean SameCol(byte r, byte c) {
        char temp = play[0][c];
        byte cnt = 1;
        for (int i = 1; i < play.length; ++i) {
            if (play[i][c] != temp) {
                return false;
            }
            ++cnt;
            if (cnt == play.length) {
                return true;
            }

        }
        return false;
    }

    private boolean sameRow(byte r, byte c) {

        char temp = play[r][0];
        byte cnt = 1;
        for (int i = 1; i < play.length; ++i) {
            if (play[r][i] != temp) {
                return false;
            }
            ++cnt;
            if (cnt == play.length) {
                return true;
            }

        }
        return false;
    }

    public boolean diagonal() {
        byte cnt = 1;
        if (play[0][0] != '0') {
            char temp = play[0][0];
            for (int i = 1; i < play.length; ++i) {

                if (play[i][i] != temp) {
                    return false;
                }
                ++cnt;
            }
            if (cnt == play.length) {
                return true;
            }

        } else if (play[0][play.length - 1] != '0') {
            char temp = play[0][play.length - 1];
            for (int r = 1, c = play.length - 2; r < play.length && c >= 0; r++, c--) {
                if (play[r][c] != temp) {
                    return false;
                }
                ++cnt;
                if (cnt == play.length) {
                    return true;
                }
            }

        } else if (play[play.length - 1][0] != '0') {
            char temp = play[play.length - 1][0];
            for (int r = play.length - 2, c = 1; r >= 0 && c < play.length; r--, c++) {
                if (play[r][c] != temp) {
                    return false;
                }
                ++cnt;
                if (cnt == play.length) {
                    return true;
                }
            }
        } else if (play[play.length - 1][play.length - 1] != '0') {
            char temp = play[play.length - 1][play.length - 1];
            for (int r = play.length - 1; r < play.length; --r) {
                if (play[r][r] != temp) {
                    return false;
                }
                ++cnt;
            }
            if (cnt == play.length) {
                return true;
            }

        }
        return false;
    }

    public void palyAgain() {
        for (byte r = 0; r < buttons.length; ++r) {
            for (byte c = 0; c < buttons[r].length; ++c) {
                buttons[r][c].setText("");
                play[r][c] = '0';
            }
        }
        first = true;
    }
}

