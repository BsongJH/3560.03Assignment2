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
    private DefaultListModel tweetModel, followerModel;
    private Users viewingUser;
    private HashMap<Users, UserUI> userUIHashMap;

    public UserUI(Users userView, Groups root, HashMap<Users, UserUI> uimap)
    {
        this.viewingUser = userView;
        this.userUIHashMap = uimap;

        userFrame = new JFrame();

        // List and Model for tweets
        tweetModel = new DefaultListModel();
        tweetList = new JList(tweetModel);

        // List and model for followers
        followerModel = new DefaultListModel();
        followList = new JList(followerModel);

        followList.setBounds(13,60,395, 100);
        tweetList.setBounds(13,210,395, 120);

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
                Users aUser = (Users) root.getEntry(followUser.getText(), root);
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
                {
                    if (followerModel.contains(aUser))
                    {
                        errorMsg("You are already following that User!");
                    }
                    else
                    {
                        // If all the conditions have met the user follows, adds in to the list model, and
                        // attach into the observer pattern
                        viewingUser.followUser(aUser);
                        followerModel.addElement(aUser);
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
                    updateTweets(viewingUser.getLatestNewsFeed());
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

    /*
        goes through all the User objects that has been opened and updates all the
        trees individually including itself.
     */
    public void updateTweets(String msg)
    {
        for (Map.Entry<Users, UserUI> entry : userUIHashMap.entrySet())
        {
            Users key = entry.getKey();
            UserUI value = entry.getValue();
            if (followerModel.contains(key) || key.equals(viewingUser))
            {
                value.tweetModel.add(0, msg);
            }
        }
    }

}
