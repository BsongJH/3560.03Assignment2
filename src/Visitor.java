/*
    Interface for Visitor, depending on the usage of the Class
    it choose to go through user or group
 */
public interface Visitor
{
    void visitUser(Users visitUsers);
    void visitGroup(Groups visitGroups);
}
