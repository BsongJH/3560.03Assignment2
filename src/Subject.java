import java.util.ArrayList;
import java.util.List;

/*
    Observer pattern that has the function attach so people can follow each
    other and saves it to the list. Also notifying each person whenever there
    are updates.
 */
public class Subject
{
    private List<Observer> followers = new ArrayList<>();

    public void attach(Observer observer)
    {
        followers.add(observer);
    }

    public void notifyFollowers(String msg)
    {
        for(Observer observer : this.followers)
        {
            observer.update(this, msg);
        }
    }
}
