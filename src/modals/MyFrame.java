package modals;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MyFrame extends JFrame implements ActionListener {
    private DirectedWeightedGraph_Class graph;
    private DirectedWeightedGraphAlgorithms_Class algo;
    private MyPanel mp;
    private MenuBar mb;
    private MenuItem Load;
    private MenuItem Save;
    private MenuItem AddNode;
    private MenuItem DeleteNode;
    private MenuItem AddEdge;
    private MenuItem DeleteEdge;
    private MenuItem Tsp;
    private MenuItem ShortestPath;
    private MenuItem IsConnected;
    private MenuItem ShortestPathDist;
    private MenuItem Center;

    public MyFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dimension.width / 2, dimension.width / 2);
        ImageIcon logo = new ImageIcon("./logo.jpg");
        this.setIconImage(logo.getImage());
        AddMenu();
        AddPanel();
        this.setTitle("OOP - Task 2");
        this.setVisible(true);
        algo = new DirectedWeightedGraphAlgorithms_Class();
        graph = new DirectedWeightedGraph_Class();
        algo.init(graph);
    }

    public MyFrame(String file) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dimension.width / 2, dimension.width / 2);
        ImageIcon logo = new ImageIcon("./logo.jpg");
        this.setIconImage(logo.getImage());
        AddMenu();
        AddPanel();
        this.setTitle("OOP - Task 2");
        this.setVisible(true);
        algo = new DirectedWeightedGraphAlgorithms_Class();
        graph = new DirectedWeightedGraph_Class();
        algo.init(graph);
        algo.load(file);
        this.mp.setGraph(this.graph);
        this.mp.repaint();
    }

    public DirectedWeightedGraph_Class getGraph() {
        return graph;
    }

    public void AddPanel() {
        mp = new MyPanel(this);
        this.add(mp);
    }

    public void AddMenu() {
        this.mb = new MenuBar();
        Menu Load_save = new Menu("Load/Save Graph");
        Menu Edit = new Menu("Edit");
        Menu AlgoMenu = new Menu("Algo");
        Load = new MenuItem("Load");
        Save = new MenuItem("Save");
        AddEdge = new MenuItem("Add Edge");
        AddNode = new MenuItem("Add Node");
        DeleteNode = new MenuItem("Delete Node");
        DeleteEdge = new MenuItem("Delete Edge");
        Tsp = new MenuItem("tsp");
        ShortestPath = new MenuItem("Shortest Path");
        ShortestPathDist = new MenuItem("Shortest Path Dist");
        Center = new MenuItem("Center");
        IsConnected = new MenuItem("Is Connected");

        Load.addActionListener(this);
        Save.addActionListener(this);
        AddNode.addActionListener(this);
        AddEdge.addActionListener(this);
        DeleteEdge.addActionListener(this);
        DeleteNode.addActionListener(this);
        Tsp.addActionListener(this);
        ShortestPathDist.addActionListener(this);
        ShortestPath.addActionListener(this);
        Center.addActionListener(this);
        IsConnected.addActionListener(this);

        Load_save.add(Load);
        Load_save.add(Save);
        Edit.add(AddEdge);
        Edit.add(AddNode);
        Edit.add(DeleteNode);
        Edit.add(DeleteEdge);
        AlgoMenu.add(Tsp);
        AlgoMenu.add(IsConnected);
        AlgoMenu.add(Center);
        AlgoMenu.add(ShortestPathDist);
        AlgoMenu.add(ShortestPath);

        this.mb.add(Load_save);
        this.mb.add(Edit);
        this.mb.add(AlgoMenu);
        this.setMenuBar(this.mb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Load) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                algo.load(selectedFile.getAbsolutePath());
//                this.graph.makeTagsZero();
                this.mp.setGraph(this.graph);
                this.mp.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Load failed",
                        "Load failed",
                        JOptionPane.PLAIN_MESSAGE);
            }
        } else if (e.getSource() == Save) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                this.graph.makeTagsZero();
                algo.save(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == AddEdge) {
            this.graph.makeTagsZero();
            String result;
            int SrcId = Integer.MAX_VALUE;
            while (SrcId == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter source node ID of the edge you want to add:",
                        "Select source node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) SrcId = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            int DestId = Integer.MAX_VALUE;
            while (DestId == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter destination node ID of the edge you want to add:",
                        "Select destination node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) DestId = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            double weight = Double.MAX_VALUE;
            while (weight == Double.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter weight of the edge you want to add:",
                        "Select weight",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "double weight"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            if (result.charAt(i) != '.') {
                                JOptionPane.showMessageDialog(this,
                                        "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in double format.",
                                        "ERROR",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;
                            }
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) weight = Double.parseDouble(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in double format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            if (SrcId != Integer.MAX_VALUE && DestId != Integer.MAX_VALUE && weight != Double.MAX_VALUE) {
                graph.connect(SrcId, DestId, weight);
                mp.setGraph(graph);
                mp.repaint();
            }

        } else if (e.getSource() == AddNode) {
            this.graph.makeTagsZero();
            String result;
            double x = Double.MAX_VALUE, y = Double.MAX_VALUE;
            //Ask for X
            while (x == Double.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter X:",
                        "Select X",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "double x"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            if (result.charAt(i) != '.') {
                                JOptionPane.showMessageDialog(this,
                                        "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in double format.",
                                        "ERROR",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;
                            }
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) x = Double.parseDouble(result);

                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in double format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }

                //Ask for Y
            }
            while (y == Double.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter Y:",
                        "Select Y",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "double Y"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            if (result.charAt(i) != '.') {
                                JOptionPane.showMessageDialog(this,
                                        "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in double format.",
                                        "ERROR",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;
                            }
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) y = Double.parseDouble(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in double format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }

            if (x != Double.MAX_VALUE && y != Double.MAX_VALUE) {
                GeoLocation_Class geo = new GeoLocation_Class(x, y, 0.0);
                NodeData_Class node = new NodeData_Class(graph.nodeSize(), geo);
                this.graph.addNode(node);
                mp.setGraph(this.graph);
                mp.repaint();
            }
        } else if (e.getSource() == DeleteEdge) {
            this.graph.makeTagsZero();
            String result;
            int SrcId = Integer.MAX_VALUE;
            while (SrcId == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter source node ID of the edge you want to remove:",
                        "Select source node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) SrcId = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            int DestId = Integer.MAX_VALUE;
            while (DestId == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter destination node ID of the edge you want to remove:",
                        "Select destination node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) DestId = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            if (SrcId != Integer.MAX_VALUE && DestId != Integer.MAX_VALUE) {
                graph.removeEdge(SrcId, DestId);
                mp.setGraph(graph);
                mp.repaint();
            }

        } else if (e.getSource() == DeleteNode) {
            this.graph.makeTagsZero();
            String result;
            int id = Integer.MAX_VALUE;
            while (id == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please the ID of the node you want to remove:",
                        "Select node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) id = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
                if (id != Integer.MAX_VALUE) {
                    graph.removeNode(id);
                    mp.setGraph(graph);
                    mp.repaint();
                }
            }
        } else if (e.getSource() == Center) {
            this.graph.makeTagsZero();
            algo.center();
            this.mp.setGraph(this.graph);
            this.mp.repaint();
            JOptionPane.showMessageDialog(this,
                    "Center node is now in Green color ",
                    "Message",
                    JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == ShortestPathDist) {
            this.graph.makeTagsZero();
            String result;
            int SrcId = Integer.MAX_VALUE;
            while (SrcId == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter source node ID of the path distance you want to calculate:",
                        "Select source node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) SrcId = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            int DestId = Integer.MAX_VALUE;
            while (DestId == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter destination node ID of the path distance you want to calculate:",
                        "Select destination node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) DestId = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            if (SrcId != Integer.MAX_VALUE && DestId != Integer.MAX_VALUE) {
                JOptionPane.showMessageDialog(this,
                        "The shortest path distance between " + SrcId + " and " + DestId + " is: " + algo.shortestPathDist(SrcId, DestId) + ".",
                        "ERROR",
                        JOptionPane.PLAIN_MESSAGE);
                this.graph.makeTagsZero();
                this.mp.setGraph(this.graph);
                this.mp.repaint();
            }

        } else if (e.getSource() == ShortestPath) {
            this.graph.makeTagsZero();
            String result;
            int SrcId = Integer.MAX_VALUE;
            while (SrcId == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter source node ID of the path distance you want to calculate:",
                        "Select source node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) SrcId = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            int DestId = Integer.MAX_VALUE;
            while (DestId == Integer.MAX_VALUE) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter destination node ID of the path distance you want to calculate:",
                        "Select destination node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) DestId = Integer.parseInt(result);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                            "ERROR",
                            JOptionPane.PLAIN_MESSAGE);
                }
            }
            if (SrcId != Integer.MAX_VALUE && DestId != Integer.MAX_VALUE) {
                algo.shortestPath(SrcId, DestId);
                this.mp.setGraph(this.graph);
                this.mp.repaint();
                JOptionPane.showMessageDialog(this,
                        "Nodes in shortest path are now in Cyan color ",
                        "Message",
                        JOptionPane.PLAIN_MESSAGE);
            }

        } else if (e.getSource() == IsConnected) {
            String s = "" + algo.isConnected();
            JOptionPane.showMessageDialog(this,
                    s.toUpperCase(),
                    "IsConnected?",
                    JOptionPane.PLAIN_MESSAGE);

        } else if (e.getSource() == Tsp) {
            String result = "";
            List<NodeData> cities = new LinkedList<NodeData>();
            while (!result.equals("DONE")) {
                result = (String) JOptionPane.showInputDialog(
                        this,
                        "Please enter node ID to add to tsp list calculation\nTo exit please type DONE",
                        "Enter node ID",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "int id"
                );
                if (result.contentEquals("DONE")) {
                    break;
                }
                if (result != null && result.length() > 0) {
                    boolean flag = false;
                    for (int i = 0; i < result.length(); i++) {
                        if (result.charAt(i) > 57 || result.charAt(i) < 48) {
                            JOptionPane.showMessageDialog(this,
                                    "Invalid Input!\nYou entered: " + result + "\nPlease enter valid input in int format.",
                                    "ERROR",
                                    JOptionPane.PLAIN_MESSAGE);
                            System.out.println(result);
                            break;
                        } else if (i == result.length() - 1) {
                            flag = true;
                        }
                    }
                    if (flag) cities.add(this.graph.getNode(Integer.parseInt(result)));
                }
            }
            if (algo.tsp(cities).size() == 0) {
                JOptionPane.showMessageDialog(this,
                        "No path in given list",
                        "Message",
                        JOptionPane.PLAIN_MESSAGE);
            } else {
                this.mp.setGraph(this.graph);
                this.mp.repaint();
                JOptionPane.showMessageDialog(this,
                        "Nodes in tsp are now in Red color",
                        "Message",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
}
