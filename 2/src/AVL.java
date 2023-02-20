import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AVL {

    Node root;

    public AVL(String ip){
        this.root = new Node(ip);
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    public void insert(String x, BufferedWriter writer) throws IOException {
        root = insert(x, root, writer);
    }

    private Node insert(String ip, Node node, BufferedWriter writer) throws IOException {
        if (node == null) {
            return new Node(ip, null, null,0);
        }
        int temp = ip.compareTo(node.getIp());
        if (temp < 0) {
            writer.write(node.getIp() + ": New node being added with IP:" + ip + "\n");
            node.setLeftchild(insert(ip, node.getLeftchild(), writer));
        }
        else if (temp > 0) {
            writer.write(node.getIp() + ": New node being added with IP:" + ip + "\n");
            node.setRightchild(insert(ip, node.getRightchild(), writer));
        }
        else {
            return node;
        }
        return balance(node, writer);
    }

    private Node balance(Node node, BufferedWriter writer) throws IOException {
        if (node == null) {
            return node;
        }
        if (height(node.getLeftchild()) - height(node.getRightchild()) > 1) {
            if (height(node.getLeftchild().getLeftchild()) >= height(node.getLeftchild().getRightchild())) {
                writer.write("Rebalancing: right rotation" + "\n");
                node = leftRotate(node);
            }
            else {
                writer.write("Rebalancing: left-right rotation" + "\n");
                node.setLeftchild(rightRotate(node.getLeftchild()));
                return leftRotate(node);
            }
        }
        else if (height(node.getRightchild()) - height(node.getLeftchild()) > 1) {
            if (height(node.getRightchild().getRightchild()) >= height(node.getRightchild().getLeftchild())) {
                writer.write("Rebalancing: left rotation" + "\n");
                node = rightRotate(node);
            }
            else {
                writer.write("Rebalancing: right-left rotation" + "\n");
                node.setRightchild(leftRotate(node.getRightchild()));
                return rightRotate(node);
            }
        }
        node.setHeight(Math.max(height(node.getLeftchild()), height(node.getRightchild())) + 1);
        return node;
    }

    private Node leftRotate(Node node){
        Node temp = node.getLeftchild();
        node.setLeftchild(temp.getRightchild());
        temp.setRightchild(node);
        node.setHeight(Math.max(height(node.getLeftchild()), height(node.getRightchild())) + 1);
        temp.setHeight(Math.max(height(temp.getLeftchild()), height(temp.getRightchild())) + 1);
        return temp;
    }
    
    private Node rightRotate(Node node){
        Node temp = node.getRightchild();
        node.setRightchild(temp.getLeftchild());
        temp.setLeftchild(node);
        node.setHeight(Math.max(height(node.getRightchild()), height(node.getLeftchild())) + 1);
        temp.setHeight(Math.max(height(temp.getRightchild()), height(temp.getLeftchild())) + 1);
        return temp;
    }

    public void remove(String x, BufferedWriter writer) throws IOException {
        root = remove(x, root,null, writer, 0);
    }

    private Node remove(String ip, Node node, Node parent, BufferedWriter writer, int counter) throws IOException {
        if (node == null) {
            return node;
        }
        int temp = ip.compareTo(node.getIp());
        if (temp < 0) {
            parent = node;
            node.setLeftchild(remove(ip, node.getLeftchild(), parent, writer, counter));
        }
        else if (temp > 0) {
            parent = node;
            node.setRightchild(remove(ip, node.getRightchild(), parent, writer, counter));
        }
        else if (node.getLeftchild() != null && node.getRightchild() != null) {
            writer.write(parent.getIp() + ": Non Leaf Node Deleted; removed: " + ip + " replaced: " + minValue(node.getRightchild()).getIp() + "\n");
            node.setIp(minValue(node.getRightchild()).getIp());
            parent = node;
            counter = 1;
            node.setRightchild(remove(node.getIp(), node.getRightchild(), parent, writer, counter));
        }
        else {
            if(node.getLeftchild() != null){
                if(counter == 1){
                    node = node.getLeftchild();
                }
                else{
                    writer.write(parent.getIp() + ": Node with single child Deleted: " + ip + "\n");
                    node = node.getLeftchild();
                }
            }
            else if(node.getRightchild() != null){
                if(counter == 1){
                    node = node.getRightchild();
                }
                else {
                    writer.write(parent.getIp() + ": Node with single child Deleted: " + ip + "\n");
                    node = node.getRightchild();
                }
            }
            else{
                if(counter == 1){
                    node = node.getRightchild();
                }
                else{
                    writer.write(parent.getIp() + ": Leaf Node Deleted: " + ip + "\n");
                    node = node.getRightchild();
                }
            }
        }
        return balance(node, writer);
    }

    private Node minValue(Node node){
        Node current = node;
        while (current.getLeftchild() != null) {
            current = current.getLeftchild();
        }
        return current;
    }

    private Node lowestCommonNode(Node node, String n1, String n2){
        if (node == null) {
            return null;
        }
        int temp1 = n1.compareTo(node.getIp());
        int temp2 = n2.compareTo(node.getIp());
        if (temp1 < 0 && temp2 < 0) {
            return lowestCommonNode(node.getLeftchild(), n1, n2);
        }
        if (temp1 > 0 && temp2 > 0) {
            return lowestCommonNode(node.getRightchild(), n1, n2);
        }
        return node;
    }

    private void findPath(String x, Node node, ArrayList<String> path) {
        while(node != null)
        {
            int temp = x.compareTo(node.getIp());
            if(temp < 0){
                path.add(node.getIp());
                node = node.getLeftchild();
            }
            else if(temp > 0){
                path.add(node.getIp());
                node = node.getRightchild();
            }
            else{
                path.add(node.getIp());
                break;
            }
        }
    }

    public ArrayList<String> sendMessage(String ip1, String ip2, BufferedWriter writer) throws IOException {
        writer.write(ip1 + ": Sending message to: " + ip2 + "\n");
        ArrayList<String> path1 = new ArrayList<String>();
        ArrayList<String> path2 = new ArrayList<String>();
        Node lcn = lowestCommonNode(root, ip1, ip2);
        findPath(ip1, lcn, path1);
        findPath(ip2, lcn, path2);
        for (int i = 1; i <  path1.size(); i++) {
            path2.add(0,path1.get(i));
        }
        for (int i = 1; i < path2.size() - 1; i++) {
            writer.write(path2.get(i) + ": Transmission from: " + path2.get(i-1) +  " receiver: " + ip2 + " sender:" + ip1 + "\n");
        }
        writer.write(ip2 + ": Received message from: " + ip1 + "\n");
        return path2;
    }
}
