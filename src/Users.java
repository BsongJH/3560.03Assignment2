import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Users extends Subject implements SysEntry, Observer
{
    private String userID;
    private HashSet<Users> followers = new HashSet<>();
    private List<String> tweetHistory = new ArrayList<>();
    private List<String> newsFeed = new ArrayList<>();
    private DefaultListModel tweets = new DefaultListModel();
    public Users(String newName)
    {
        this.userID = newName;
        System.out.println("User " + newName + " has been created");
    }
    public String getName()
    {
        return userID;
    }
    public String toString() { return "(User) " + userID; }

    @Override
    public void accept(Visitor visitor)
    {
        visitor.visitUser(this);
    }
    public HashSet<Users> getFollowers()
    {
        return followers;
    }

    public void followUser(Users newUser)
    {
        System.out.println(this.toString() + " added " + newUser.toString());
        System.out.println(this.followers.toString());
        followers.add(newUser);
    }

    @Override
    public void update(Subject userSubject, String msg)
    {
        System.out.println("Update visited");
        if (userSubject instanceof Users)
        {
            System.out.println("newsfeed update");
            System.out.println(((Users) userSubject).getName() + " : " + msg);
            this.newsFeed.add("---> " + ((Users)userSubject).getName() + " : " + msg);
        }
    }

    public List<String> getNewsFeed()
    {
        return this.newsFeed;
    }
    public String getLatestNewsFeed()
    {
        if(newsFeed.isEmpty())
        {
            System.out.println("return null feed visited");
            return null;
        }
        else
        {
            System.out.println(this.getName());
            System.out.println("return latestest newsfeed visited");
            System.out.println(newsFeed.get(newsFeed.size() - 1));
            return newsFeed.get(newsFeed.size() - 1);
        }
    }


    public List<String> getTweetHistory()
    {
        return this.tweetHistory;
    }

    public void tweet(String tweet)
    {
        tweetHistory.add(tweet);
        newsFeed.add("--> " + this.userID + " : " + tweet);
        notifyFollowers(tweet);
    }
}
