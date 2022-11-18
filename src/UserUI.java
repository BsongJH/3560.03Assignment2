import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserUI
{
    private JFrame userFrame;
    private JList followList;
    private JList tweetList;
    private DefaultListModel tweetModel, followerModel;
    private Users viewingUser;
    private List<UserUI> userUIList;

    public UserUI(Users userView, Groups root, List<UserUI> userUIList )
    {
        this.viewingUser = userView;

        this.userUIList = userUIList;

        userFrame = new JFrame();

        tweetModel = new DefaultListModel();

        tweetList = new JList(tweetModel);

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
                    followUser.setText(null);
                }
                // if user is yourself send error
                else if (userView == aUser)
                {
                    errorMsg("You cannot add yourself!" );
                    followUser.setText(null);
                }
                else if (followUser.getText().isEmpty())
                {
                    errorMsg("Text box is empty!");
                    followUser.setText(null);
                }
                else
                {
                    if (followerModel.contains(aUser))
                    {
                        errorMsg("You are already following that User!");
                        followUser.setText(null);
                    }
                    else
                    {
                        userView.followUser(aUser);
                        followerModel.addElement(aUser);
                        aUser.attach(userView);
                    }
                }
                // if the user exists follow
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
                    //errorMsg("You cannot post empty message!");
                    tweetList.setModel(tweetModel);
                }
                else
                {
                    userView.tweet(tweetMsgTxt.getText());

                    //tweetModel.add(0,userView.getLatestNewsFeed());
                    //tweetModel.addElement(userView.getLatestNewsFeed());
                    //updateTweets(userView.getLatestNewsFeed());
                    //System.out.println(tweetModel);
                    //tweetList.setModel(tweetModel);
                    //System.out.println(tweetModel);
                    addToModel();
                    //tweetList.setModel(tweetModel);
                    System.out.println(userUIList.toString());

                    //tweetList.ensureIndexIsVisible(tweetModel.getSize());
                }
                tweetMsgTxt.setText(null);
            }
        });
        //tweetList.setModel(tweetModel);


        userFrame.setTitle(userView.toString() + "'s view'");
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

    public void updateTweets(String msg)
    {
        for (UserUI openUIs : userUIList)
        {
            openUIs.tweetModel.add(0, msg);
        }
    }

    private void addToModel()
    {
        tweetModel.clear();
        for (String history : viewingUser.getNewsFeed())
        {
            updateTweets(history);
        }
        //tweetList.setModel(tweetModel);
    }
}
