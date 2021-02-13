package sample;


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author mouzaalblooshi
 */
public class GraphDraw extends JFrame {

    int width;
    int height;
    ArrayList<Node> nodes;
    ArrayList<edge> edges;

    public GraphDraw(String name) { //Construct with label
        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();
        width = 30;
        height = 30;
    }

    class Node {

        int x, y;
        String name;

        public Node(String myName, int myX, int myY) {
            x = myX;
            y = myY;
            name = myName;
        }
    }

    class edge {

        int i, j;

        public edge(int ii, int jj) {
            i = ii;
            j = jj;
        }
    }

    public void addNode(String name, int x, int y) {
        //add a node at pixel (x,y)
        nodes.add(new Node(name, x, y));
        this.repaint();
    }

    public void addEdge(int i, int j) {
        //add an edge between nodes i and j
        edges.add(new edge(i, j));
        this.repaint();
    }

    public void paint(Graphics g) { // draw the nodes and edges
        
        // get metrics from the graphics
        FontMetrics f = g.getFontMetrics();
        // get the height of a line of text in this font and calculate the hight of the node 
        int nodeHeight = Math.max(height, f.getHeight());

        g.setColor(Color.black);
        for (edge e : edges) {
            g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y, nodes.get(e.j).x, nodes.get(e.j).y);
        }

        for (Node n : nodes) {
            int nodeWidth = Math.max(width, f.stringWidth(n.name) + width / 2);
            g.setColor(Color.white);
            g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
            g.setColor(Color.black);
            g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2, nodeWidth, nodeHeight);
            g.drawString(n.name, n.x - f.stringWidth(n.name) / 2,
                    n.y + f.getHeight() / 2);
      
        }
    }

    public static void main(String[] args) {
        GraphDraw frame = new GraphDraw("Draw A Graph");

        frame.setSize(400, 300);

        frame.setVisible(true);

        frame.addNode("a", 50, 50);
        frame.addNode("b", 100, 100);
        frame.addNode("l", 200, 200);
        frame.addNode("c", 150, 150);
        frame.addNode("z", 20, 150);
        frame.addEdge(0, 1);
        frame.addEdge(1, 4);
        frame.addEdge(4, 2);
        frame.addEdge(0, 3);

    }
}
