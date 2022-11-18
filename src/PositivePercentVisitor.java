import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PositivePercentVisitor implements Visitor
{
    private double percentage = 0;
    private double positiveMsgCounter = 0;
    private double allMsg = 0;
    private List<String> positiveWords = new ArrayList<>(Arrays.asList("good",
            "lit","nice","great","excellent"));

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
