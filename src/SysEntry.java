public interface SysEntry
{
    public String getName();
    public void accept(Visitor visitor);
}
