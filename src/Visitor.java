/*
    Interface for Visitor, depending on the usage of the Class
    it choose to go through user or group
 */
public interface Visitor
{
    public void visitUser(Users visitUsers);
    public void visitGroup(Groups visitGroups);
}
