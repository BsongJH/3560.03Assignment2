import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
    User class containing Hashsets of followers avoiding any duplicates
    tweet history for its own tweets and newsfeed for all the follower + self
    It has visitor and observer pattern's functions implemented as well as
    itself being the leaf node of a SysEntry Class.
 */
public class Users extends Subject implements SysEntry, Observer
{
    private String userID;
    private HashSet<Users> followers = new HashSet<>();
    private List<String> tweetHistory = new ArrayList<>();
    private List<String> newsFeed = new ArrayList<>();
    private DefaultListModel followModel = new DefaultListModel();
    private DefaultListModel tweetModel = new DefaultListModel();
    private long creationTime;
    private long lastUpdated;
    public Users(String newName)
    {
        this.userID = newName;
        this.creationTime = System.currentTimeMillis();
        this.lastUpdated = System.currentTimeMillis();
    }
    public long getLastUpdated()
    {
        return lastUpdated;
    }
    public long getCreationTime()
    {
        return creationTime;
    }
    public String getName()
    {
        return userID;
    }
    public String toString() { return "(User) " + userID; }

    // Accept method for visitor patter passes itself
    @Override
    public void accept(Visitor visitor)
    {
        visitor.visitUser(this);
    }

    // Follow user adds to its list and its DefaultListModel
    public void followUser(Users newUser)
    {
        followers.add(newUser);
        followModel.addElement(newUser);
    }
    public DefaultListModel getFollowModel()
    {
        return followModel;
    }
    public DefaultListModel getTweetModel()
    {
        return tweetModel;
    }
    // Observer patter method gets feed from others and stores in if follows
    @Override
    public void update(Subject userSubject, String msg)
    {
        if (userSubject instanceof Users)
        {
            lastUpdated = System.currentTimeMillis();
            this.newsFeed.add("Last updated: " + (((Users) userSubject).getLastUpdated()));
            tweetModel.add(0, "Last updated: " + ((Users) userSubject).getLastUpdated());
            this.newsFeed.add("--> " + ((Users)userSubject).getName() + " : " + msg);
            tweetModel.add(0, "--> " + ((Users)userSubject).getName() + " : " + msg);
        }
    }

    public List<String> getTweetHistory()
    {
        return this.tweetHistory;
    }

    // Observer method to notify everyone each tweet
    public void tweet(String tweet)
    {
        tweetHistory.add(tweet);
        lastUpdated = System.currentTimeMillis();
        newsFeed.add("Last Updated: " + lastUpdated);
        tweetModel.add(0,"Last Updated: " + lastUpdated);
        newsFeed.add("--> " + this.userID + " : " + tweet);
        tweetModel.add(0, "--> " + this.userID + " : " + tweet);
        notifyFollowers(tweet);
    }
}
