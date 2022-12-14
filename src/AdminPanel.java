import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
/*
AdminPanel creates and only allows to create 1 admin panel by using Singleton pattern
 */
public class AdminPanel
{
    public static AdminPanel panelInstance;
    private JFrame frame;
    private Groups root;
    private JTree jtree;
    private DefaultMutableTreeNode rootNode;
    private JLabel creationTime;
    private JPanel top;

    // Creating a single panel instance
    public static AdminPanel getAdminPanel()
    {
        if (panelInstance == null)
        {
            synchronized (AdminPanel.class)
            {
                if (panelInstance == null)
                {
                    panelInstance = new AdminPanel();
                }
            }
        }
        return panelInstance;
    }
    /*
        Actual implementation of the admin panel including buttons, text fields, and lists
     */
    private AdminPanel()
    {
        root = new Groups("root");

        // Creating tree for the UI
        rootNode = new DefaultMutableTreeNode(root);
        jtree = new JTree(rootNode);

        // For all the user UI that is being viewed having its key as the name and UserUI object as value

        JTextField userIDText = new JTextField();
        userIDText.setBounds(220, 10, 135, 50);
        userIDText.setBackground(new Color(0xEBEBE3));

        JButton addUserBtn = new JButton("Add User");
        addUserBtn.setBounds(355, 10, 135, 50);
        addUserBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addUser(userIDText.getText());
                userIDText.setText(null);
            }
        });


        JTextField groupIDText = new JTextField();
        groupIDText.setBounds(220, 60, 135, 50);
        groupIDText.setBackground(new Color(0xEBEBE3));

        JButton addGroupBtn = new JButton("Add Group");
        addGroupBtn.setBounds(355, 60, 135, 50);
        addGroupBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addGroup(groupIDText.getText());
                groupIDText.setText(null);
            }
        });


        JButton userViewBtn = new JButton("Open User View");
        userViewBtn.setBounds(220, 110, 270, 50);
        userViewBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                DefaultMutableTreeNode viewingNode = selected();

                if (viewingNode.getUserObject() instanceof Groups)
                {
                    errorMsg("Please Select a User to view!");
                }
                else
                {
                    Users viewing = (Users) selected().getUserObject();
                    UserUI userView = new UserUI(viewing, root);
                }
            }
        });


        JButton showUserTotBtn = new JButton("Show User Total");
        showUserTotBtn.setBounds(220, 215, 135, 50);
        showUserTotBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                UserTotalVisitor showUserTot = new UserTotalVisitor();
                root.accept(showUserTot);
                display("Total number of users: " + showUserTot.getUserCounter() + ".");
            }
        });

        JButton showGroupTotBtn = new JButton("Show Group Total");
        showGroupTotBtn.setBounds(355, 215, 135, 50);
        showGroupTotBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GroupTotalVisitor showGroupTot = new GroupTotalVisitor();
                root.accept(showGroupTot);
                display("Total number of Groups: " + showGroupTot.getGroupCounter() + ".");
            }
        });

        JButton showTotMsgBtn = new JButton("Total Messages");
        showTotMsgBtn.setBounds(220, 265, 135, 50);
        showTotMsgBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                TotalMsgVisitor showTotMsg = new TotalMsgVisitor();
                root.accept(showTotMsg);
                display("Total number of messages: " + showTotMsg.getMsgCounter() + ".");
            }
        });

        JButton showPosPerBtn = new JButton("Positive Percentage");
        showPosPerBtn.setBounds(355, 265, 135, 50);
        showPosPerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                PositivePercentVisitor showPosPer = new PositivePercentVisitor();
                root.accept(showPosPer);
                display("Percentage of positive messages: "
                        + String.format("%.2f", showPosPer.getPercentage()) + "%");
            }
        });

        JButton showIDVerificationBtn = new JButton("ID Verification");
        showIDVerificationBtn.setBounds(220,315,135,50);
        showIDVerificationBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ValidIDVisitor validCheck = new ValidIDVisitor();
                if(userIDText.getText().isEmpty())
                {
                    validCheck.setTempName(groupIDText.getText());
                }
                else
                {
                    validCheck.setTempName(userIDText.getText());
                }
                root.accept(validCheck);
                if(validCheck.getExist())
                {
                    errorMsg("The name already exists!");
                }
                else
                {
                    display("The name does not exist!");
                }
                userIDText.setText(null);
                groupIDText.setText(null);
            }
        });

        JButton showLastUpdateUserBtn = new JButton("Last Updated");
        showLastUpdateUserBtn.setBounds(355,315,135,50);
        showLastUpdateUserBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                LastUpdateVisitor lastUpdateVisitor = new LastUpdateVisitor();
                root.accept(lastUpdateVisitor);
                display("The User: " + lastUpdateVisitor.getLastUpdateUser()
                        + " at " + lastUpdateVisitor.getLastUpdatedTime());
            }
        });



        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 200, 360);
        scrollPane.setBackground(new Color(0xEBEBE3));
        scrollPane.setViewportView(jtree);

        frame = new JFrame();
        frame.setTitle("Mini Twitter");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(500, 550 / 12 * 9));
        frame.setSize(500, 550 / 12 * 9);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        creationTime = new JLabel();
        top = new JPanel();
        creationTime.setForeground(Color.BLACK);
        top.setBackground(new Color(0xC7D6DB));
        top.setBounds(220,160,270,50);
        top.add(creationTime);
        frame.add(top);

        frame.add(addUserBtn);
        frame.add(userIDText);
        frame.add(addGroupBtn);
        frame.add(groupIDText);
        frame.add(userViewBtn);

        frame.add(showUserTotBtn);
        frame.add(showGroupTotBtn);
        frame.add(showTotMsgBtn);
        frame.add(showPosPerBtn);
        frame.add(scrollPane);
        frame.add(showIDVerificationBtn);
        frame.add(showLastUpdateUserBtn);

        frame.getContentPane().setBackground(new Color(0xC7D6DB));

    }

    /*
        Adds user Check if it is a group that is being selected on the
        Admin panel to be precise about where to add who. and adding it to
        the tree model
     */
    private void addUser(String newUserID)
    {
        if (newUserID.isEmpty())
        {
            errorMsg("Please enter a userID");
        }
        else if (!root.contains(newUserID))
        {
            DefaultMutableTreeNode parentNode = selected();
            if (parentNode != null && selected().getUserObject() instanceof Groups)
            {
                // Selected Group is the new parent
                Groups parentGroup = (Groups) parentNode.getUserObject();
                // Creates new User adds it to the group
                Users addUser = new Users(newUserID);
                parentGroup.addEntry(addUser);
                //displayLabel("New user creation time: " + addUser.getCreationTime());
                creationTime.setText("New user creation time: " + addUser.getCreationTime());

                DefaultMutableTreeNode newUserNode = new DefaultMutableTreeNode(addUser);
                updateTree(newUserNode, parentNode);
            }
            else
            {
                errorMsg("Please select a group first!");
            }
        }
        else
        {
            errorMsg("The name already exists!");
        }
    }


    private void addGroup(String newGroupID)
    {
        if (newGroupID.isEmpty())
        {
            errorMsg("Please enter a userID");
        }
        else if (!root.contains(newGroupID))
        {
            DefaultMutableTreeNode parentNode = selected();
            if (parentNode != null && parentNode.getUserObject() instanceof Groups)
            {
                Groups parentGroup = (Groups) parentNode.getUserObject();
                Groups newGroup = new Groups(newGroupID);
                parentGroup.addEntry(newGroup);
                //displayLabel("New group creation time: " + newGroup.getCreationTime());
                creationTime.setText("New group creation time: " + newGroup.getCreationTime());
                DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(newGroup);
                updateTree(newGroupNode, parentNode);
            }
            else
            {
                errorMsg("Please selected a group first!");
            }
        }
        else
        {
            errorMsg("The name already exists!");
        }
    }

    // Returns what is being selected as a Node
    public DefaultMutableTreeNode selected()
    {
        try {
            return ((DefaultMutableTreeNode) jtree.getLastSelectedPathComponent());
        }
        catch (NullPointerException e)
        {
            return null;
        }
    }

    private void errorMsg(String errorMSg)
    {
        JOptionPane.showMessageDialog(null, errorMSg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Updates the UI when nodes(Group or User) are being added
    public void updateTree(DefaultMutableTreeNode nodeToAdd, DefaultMutableTreeNode parentNode)
    {
        DefaultTreeModel model = (DefaultTreeModel) jtree.getModel();
        model.insertNodeInto(nodeToAdd, parentNode, parentNode.getChildCount());
        jtree.scrollPathToVisible(new TreePath(nodeToAdd.getPath()));
    }

    private void display(String message)
    {
        JOptionPane.showMessageDialog(null, message, "Analysis", JOptionPane.INFORMATION_MESSAGE);
    }

}
