public class LastUpdateVisitor implements Visitor
{
    private String lastUpdateUser = "No one";
    private long lastUpdatedTime = 0;
    @Override
    public void visitUser(Users visitUsers)
    {
        if (visitUsers.getLastUpdated() > lastUpdatedTime)
        {
            this.lastUpdatedTime = visitUsers.getLastUpdated();
            this.lastUpdateUser = visitUsers.getName();
        }
    }

    @Override
    public void visitGroup(Groups visitGroups)
    {
        // Void
    }

    public String getLastUpdateUser()
    {
        return lastUpdateUser;
    }
    public long getLastUpdatedTime()
    {
        return lastUpdatedTime;
    }
}
