import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Finds the percentage of the positive words in the total messages that has been sent by users
public class PositivePercentVisitor implements Visitor
{
    private double percentage = 0;
    private double positiveMsgCounter = 0;
    private double allMsg = 0;
    private List<String> positiveWords = new ArrayList<>(Arrays.asList("good",
            "lit","nice","great","excellent"));

    // Visitor pattern of User class it goes through all the tweet histories and compare
    // it with the list that I assigned.
    @Override
    public void visitUser(Users visitUsers)
    {
        for(String tweets: visitUsers.getTweetHistory())
        {
            allMsg++;
            for(String positive : positiveWords)
            {
                if(tweets.toLowerCase().contains(positive.toLowerCase()))
                {
                    positiveMsgCounter++;
                    break;
                }
            }
        }
    }

    @Override
    public void visitGroup(Groups visitGroups)
    {
        // Nothing here
    }

    // Calculates the positive message percentage
    public double getPercentage()
    {
        if (allMsg == 0)
        {
            return percentage;
        }
        setPercentage((positiveMsgCounter / allMsg) * 100.00);
        return percentage;
    }
    public void setPercentage(double percentage)
    {
        this.percentage = percentage;
    }
}
