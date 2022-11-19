/*
    Visitor pattern, goes through all the user instances get counts from tweet histories
 */
public class TotalMsgVisitor implements Visitor
{
    private int msgCounter = 0;
    @Override
    public void visitUser(Users visitUsers)
    {
        setMsgCounter(getMsgCounter() + visitUsers.getTweetHistory().size());
    }

    @Override
    public void visitGroup(Groups visitGroups)
    {

    }

    public void setMsgCounter(int numOfMsgs)
    {
        msgCounter = numOfMsgs;
    }
    public int getMsgCounter()
    {
        return msgCounter;
    }
}
