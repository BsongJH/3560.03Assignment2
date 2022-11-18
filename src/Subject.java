import java.util.ArrayList;
import java.util.List;

public class Subject
{
    private List<Observer> followers = new ArrayList<>();

    public void attach(Observer observer)
    {
        System.out.println("attach has been visited!");
        followers.add(observer);
        followers.toString();
    }

    public void notifyFollowers(String msg)
    {
        for(Observer observer : this.followers)
        {
            System.out.println("notified user" + followers.toString());
            observer.update(this, msg);
        }
    }
}
