public class GroupTotalVisitor implements Visitor
{
    private int GroupCounter = 0;
    @Override
    public void visitUser(Users visitUsers)
    {
        // Nothing here
    }

    @Override
    public void visitGroup(Groups visitGroups)
    {
        setGroupCounter(getGroupCounter() + 1);
    }

    public void setGroupCounter(int counter)
    {
        this.GroupCounter = counter;
    }
    public int getGroupCounter()
    {
        return GroupCounter;
    }

}