/*
    one of visitor pattern method/class counting through the user instances that are created
 */
public class UserTotalVisitor implements Visitor
{
    private int userCounter = 0;
    @Override
    public void visitUser(Users visitUsers)
    {
        setUserCounter(getUserCounter() + 1);
    }

    @Override
    public void visitGroup(Groups visitGroups)
    {
        // Nothing here
    }
    public int getUserCounter()
    {
        return userCounter;
    }
    public void setUserCounter(int userCounter)
    {
        this.userCounter = userCounter;
    }
}
