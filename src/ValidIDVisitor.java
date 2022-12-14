public class ValidIDVisitor implements Visitor
{
    private boolean exist = false;
    private String tempName;
    @Override
    public void visitUser(Users visitUsers)
    {
        if (visitUsers.getName().equals(tempName))
        {
            setExist();
        }
    }

    @Override
    public void visitGroup(Groups visitGroups)
    {
        if (visitGroups.getName().equals(tempName))
        {
            setExist();
        }
    }

    public void setTempName(String name)
    {
        this.tempName = name;
    }

    public boolean getExist()
    {
        return exist;
    }

    public void setExist()
    {
        this.exist = true;
    }
}
