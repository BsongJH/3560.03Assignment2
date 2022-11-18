import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Groups implements SysEntry
{
    private Groups parent;
    private String groupID;
    private List<SysEntry> entryList;
    private HashSet<String> userID;

    public Groups(String newName)
    {
        this.groupID = newName;
        entryList = new ArrayList<>();
        userID = new HashSet<>();
        parent = this;

        System.out.println("Group: " + newName + " has been created");
    }

    public String getName()
    {
        return groupID;
    }
    public String toString() { return "(Group) " + groupID; }

    public void addEntry(SysEntry newGroup)
    {
        this.entryList.add(newGroup);
    }
    public Boolean contains(String userID)
    {
        for (SysEntry entries : entryList)
        {
            if (entries instanceof Users)
            {
                if (entries.getName().equals(userID))
                {
                    return true;
                }
            }
            else if (entries instanceof Groups)
            {
                if (((Groups) entries).contains(userID))
                {
                    return true;
                }
            }
        }
        return false;
    }
    public SysEntry getEntry(String userID, SysEntry entry)
    {
        for (SysEntry entries : entryList)
        {
            if (entries instanceof Users)
            {
                if (entries.getName().equals(userID))
                {
                    return entries;
                }
            }
            else if (entries instanceof Groups)
            {
                if (((Groups) entries).contains(userID))
                {
                    getEntry(userID, entries);
                }
            }
        }
        return null;
    }

    // Loops until there are nothing on the list of entry (Root Group)
    @Override
    public void accept(Visitor visitor)
    {
        System.out.println("Group accept method visited!");
        visitor.visitGroup(this);
        for (SysEntry entries : entryList)
        {
            if (entries instanceof Users)
            {
                entries.accept(visitor);
            }
            else if (entries instanceof Groups)
            {
                entries.accept(visitor);
            }
        }
    }
}
