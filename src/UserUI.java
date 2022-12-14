import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
/*
    UserUI having buttons and text fields for Following user and uploading tweets.
    Also has separate lists for its list of followings and list of tweets that are updated
 */
public class UserUI
{
    private JFrame userFrame;
    private JList followList;
    private JList tweetList;
    private Users viewingUser;
    private JLabel creationF1, creationT1, creationF2,creationT2;
    private JPanel followP1, tweetP1, followP2, tweetP2;

    public UserUI(Users userView, Groups root)
    {
        this.viewingUser = userView;

        userFrame = new JFrame();

        tweetList = new JList(userView.getTweetModel());
        followList = new JList(userView.getFollowModel());

        followList.setBounds(13,80,395, 80);
        tweetList.setBounds(13,230,395, 100);

        JTextField followUser = new JTextField();
        followUser.setBounds(10, 10, 200, 50);
        followUser.setBackground(new Color(0xEBEBE3));

        JButton followUserBtn = new JButton("Follow");
        followUserBtn.setBounds(210, 10, 200, 50);
        followUserBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Users aUser = (Users) root.getEntry(followUser.getText());
                // if user does not exists sent error.
                if (!root.contains(followUser.getText()))
                {
                    errorMsg("The User does not exist!");
                }
                // if user is yourself send error
                else if (viewingUser == aUser)
                {
                    errorMsg("You cannot add yourself!" );
                }
                else if (followUser.getText().isEmpty())
                {
                    errorMsg("Text box is empty!");
                }
                else
                {       //followerModel.contains(aUser)
                    if (userView.getFollowModel().contains(aUser))
                    {
                        errorMsg("You are already following that User!");
                    }
                    else
                    {
                        // If all the conditions have met the user follows, adds in to the list model, and
                        // attach into the observer pattern
                        viewingUser.followUser(aUser);
                        aUser.attach(viewingUser);
                    }
                }
                followUser.setText(null);
            }
        });

        JTextField tweetMsgTxt = new JTextField();
        tweetMsgTxt.setBounds(10, 160, 200, 50);
        tweetMsgTxt.setBackground(new Color(0xEBEBE3));

        JButton postTweet = new JButton("Post Tweet");
        postTweet.setBounds(210, 160, 200, 50);
        postTweet.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (tweetMsgTxt.getText().isEmpty())
                {
                    errorMsg("You cannot post empty message!");
                }
                else
                {
                    viewingUser.tweet(tweetMsgTxt.getText());

                    creationT2.setText("Last Updated Time: " + viewingUser.getLastUpdated());
                    userFrame.update(userFrame.getGraphics());
                }
                tweetMsgTxt.setText(null);
            }
        });

        userFrame.setTitle(viewingUser.toString() + "'s view'");
        userFrame.setLayout(null);
        userFrame.setResizable(false);
        userFrame.setVisible(true);
        userFrame.setPreferredSize(new Dimension(450, 500 / 12 * 9));
        userFrame.setSize(420, 500 / 12 * 9);
        userFrame.setLocationRelativeTo(null);
        userFrame.getContentPane().setBackground(new Color(0xC7D6DB));

        creationF1 = new JLabel();
        creationF2 = new JLabel();
        creationT1 = new JLabel();
        creationT2 = new JLabel();

        creationT1.setForeground(Color.BLACK);
        creationF1.setForeground(Color.BLACK);
        creationT2.setForeground(Color.BLACK);
        creationF2.setForeground(Color.BLACK);

        creationF1.setText("Current Following:      ");
        creationF2.setText("Creation Time: " + viewingUser.getCreationTime());

        creationT1.setText("News Feed:              ");
        creationT2.setText("Last Updated Time: " + viewingUser.getLastUpdated());
        userFrame.update(userFrame.getGraphics());

        followP1 = new JPanel();
        tweetP1 = new JPanel();
        followP2 = new JPanel();
        tweetP2 = new JPanel();

        followP1.setBackground(new Color(0xC7D6DB));
        followP1.setBounds(13,60,150,20);
        followP2.setBackground(new Color(0xC7D6DB));
        followP2.setBounds(163,60,245,20);

        tweetP1.setBackground(new Color(0xC7D6DB));
        tweetP1.setBounds(13,210,150, 20);
        tweetP2.setBackground(new Color(0xC7D6DB));
        tweetP2.setBounds(163,210,245, 20);

        followP1.add(creationF1);
        tweetP1.add(creationT1);
        followP2.add(creationF2);
        tweetP2.add(creationT2);

        userFrame.add(followP1);
        userFrame.add(tweetP1);
        userFrame.add(followP2);
        userFrame.add(tweetP2);
        userFrame.add(followUserBtn);
        userFrame.add(followUser);
        userFrame.add(tweetMsgTxt);
        userFrame.add(postTweet);
        userFrame.add(followList);
        userFrame.add(tweetList);

    }

    private void errorMsg(String errorMSg)
    {
        JOptionPane.showMessageDialog(null, errorMSg, "Error", JOptionPane.ERROR_MESSAGE);
    }

}
