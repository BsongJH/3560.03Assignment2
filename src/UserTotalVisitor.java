public class UserTotalVisitor implements Visitor
{
    private int userCounter = 0;
    @Override
    public void visitUser(Users visitUsers)
    {
        System.out.println("Total Visitor Counter visited!");
        setUserCounter(getUserCounter() + 1);
    }

    @Override
    public void visitGroup(Groups visitGroups)
    {
        //
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
