import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminPanel
{
    public static AdminPanel panelInstance;
    private JFrame frame;
    private Groups root;
    private JTree jtree;
    private DefaultMutableTreeNode rootNode;
    private List<UserUI> userUIList;

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

    private AdminPanel()
    {
        root = new Groups("root");
        rootNode = new DefaultMutableTreeNode(root);
        jtree = new JTree(rootNode);

        userUIList = new ArrayList<>();

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

                    UserUI userView = new UserUI(viewing, root, userUIList);

                    userUIList.add(userView);
                    System.out.println(userUIList.toString());
                }
            }

        });

        JButton showUserTotBtn = new JButton("Show User Total");
        showUserTotBtn.setBounds(220, 220, 135, 50);
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
        showGroupTotBtn.setBounds(355, 220, 135, 50);
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
        showTotMsgBtn.setBounds(220, 270, 135, 50);
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
        showPosPerBtn.setBounds(355, 270, 135, 50);
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


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 200, 320);
        scrollPane.setBackground(new Color(0xEBEBE3));
        scrollPane.setViewportView(jtree);

        frame = new JFrame();
        frame.setTitle("Mini Twitter");
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(500, 500 / 12 * 9));
        frame.setSize(500, 500 / 12 * 9);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        frame.getContentPane().setBackground(new Color(0xC7D6DB));

    }


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
