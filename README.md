
# Traveling Salesman Problem
OOP Course Ariel University - Task 2.

This project includes implementation of Interfaces:
- GeoLocation -> GeoLocation_Class
- NodeData -> NodeData_Class
- EdgeData -> EdgeData_Class
- DirectedWeightedGraph -> DirectedWeightedGraph_Class
- DirectedWeightedGraphAlgorithms -> DirectedWeightedGraphAlgorithms_Class



## Authors

- Ofek Rotem
- Yonatan Tal


## GUI Screenshot

![Alt text](Screenshot.jpg?raw=true "GUI Screenshot")


## How to run project JAR file
In order to run the project JAR file follow the following steps:  
1. Download JAR file from this github page (Ex2.jar).
2. Insert the JAR and the json file to a folder and run CMD from this folder.
3. In the CMD write this command: java -jar Ex2.jar <The Json file you want the GUI to run with>.  
4. Enjoy :)
## Class Explanations
### GeoLocation_Class:
This class reperesents a location and includes x,y,z paramaters.
### NodeData_Class:
This class reperesents a node in the graph and includes a unique ID for each node and its GeoLocation.
### EdgeData_Class:
This class reperesents an edge in the graph and includes its src node,dest node, and its weight.
### DirectedWeightedGraph_Class:
This class reperesents a graph and it includes these main functions:
- getNode(int key) - returns the node_data by the node_id.
- getEdge(int src, int dest) - returns the data of the edge (src,dest), null if none.
- addNode(NodeData n) - adds a new node to the graph with the given node_data.
- connect(int src, int dest, double w) - onnects an edge with weight w between node src to node dest.
- nodeIter() - This method returns an Iterator for the collection representing all the nodes in the graph.
- edgeIter() - This method returns an Iterator for all the edges in this graph.
- edgeIter(int node_id) - This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
- removeNode(int key) - Deletes the node (with the given ID) from the graph and removes all edges which starts or ends at this node.
- removeEdge(int src, int dest) - Deletes the edge from the graph.
- nodeSize() - Returns the number of vertices (nodes) in the graph.
- edgeSize() - Returns the number of edges (assume directional graph).
- getMC() - Returns the Mode Count - for testing changes in the graph.
### DirectedWeightedGraphAlgorithms_Class:
This class reperesents the algorithms that can be run on a graph and it includes these main functions:
- init(DirectedWeightedGraph g) - Inits the graph on which this set of algorithms operates on.
- DirectedWeightedGraph getGraph() - Returns the underlying graph of which this class works.
- copy() - Computes a deep copy of this weighted graph.
- isConnected() - Returns true if and only if (iff) there is a valid path from each node to each other node.
- shortestPathDist(int src, int dest) - Computes the length of the shortest path between src to dest. if no such path --> returns -1
- shortestPath(int src, int dest) - Computes the the shortest path between src to dest - as an ordered List of nodes.
- center() - Finds the NodeData which minimizes the max distance to all the other nodes.
- tsp(List<NodeData> cities) - Computes a list of consecutive nodes which go over all the nodes in cities. the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution - the lower the better.
- save(String file) - Saves this weighted (directed) graph to the given file name - in JSON format.
- load(String file) - This method loads a graph to this graph algorithm.

## GUI
The GUI for this project is done using two classes - MyFrame and MyPanel.  
MyFrame extends Jframe.  
MyPanel extends Jpanel.  
The GUI supports the following functions:
- Saving/Loading a graph to/from Json file.
- Editing the graph - Add/Remove Node/Edge.
- Activating functions from Graph Algorithms class (tsp, isConnected, Center, Shortest Path dist, Shortest Path).


## UML Diagram
![Alt text](UML.png?raw=true "UML Diagram")
