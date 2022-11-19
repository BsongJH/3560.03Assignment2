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
    public Users(String newName)
    {
        this.userID = newName;
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

    public void followUser(Users newUser)
    {
        followers.add(newUser);
    }

    @Override
    public void update(Subject userSubject, String msg)
    {
        if (userSubject instanceof Users)
        {
            this.newsFeed.add("---> " + ((Users)userSubject).getName() + " : " + msg);
        }
    }

    public String getLatestNewsFeed()
    {
        if(newsFeed.isEmpty())
        {
            return null;
        }
        else
        {
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
