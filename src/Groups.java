import java.util.ArrayList;
import java.util.List;

/*
    Composite pattern
    Groups class implementing the SysEntry interface which it would be sharing the same
    interface with User class. This will be the "container" of user and group classes.
 */
public class Groups implements SysEntry
{
    private String groupID;
    private List<SysEntry> entryList;
    private long creationTime;
    private long lastUpdated = 0;
    public Groups(String newName)
    {
        this.groupID = newName;
        entryList = new ArrayList<>();
        this.creationTime = System.currentTimeMillis();
    }

    public String getName()
    {
        return groupID;
    }
    public String toString() { return "[Group] " + groupID; }

    public void addEntry(SysEntry newGroup)
    {
        this.entryList.add(newGroup);
    }

    // Checks if the param String exists in the list of SysEntries that it carries
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

    // Similar to the .contain method but this returns the actual object.
    public SysEntry getEntry(String userID)
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
                    if (((Groups) entries).getEntry(userID) instanceof Users)
                    {
                        return ((Groups) entries).getEntry(userID);
                    }
                }
            }
        }
        return null;
    }

    public long getCreationTime()
    {
        return creationTime;
    }
    public long getLastUpdated()
    {
        return lastUpdated;
    }
/*
    Loops until there are nothing on the list of entry (Root Group)
    Accept method for the visitor method. Goes through all the
    Entries that it carries and accepts.
    Mostly for root group
 */
    @Override
    public void accept(Visitor visitor)
    {
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
