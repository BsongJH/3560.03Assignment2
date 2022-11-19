/*
    Composite pattern SysEntry interface

    Visitor patter to implement inside the SysEntry Class instead
    of having separate class for it.
 */
public interface SysEntry
{
    public String getName();
    public void accept(Visitor visitor);
}
