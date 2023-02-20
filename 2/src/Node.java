public class Node {
    String ip;
    int height;
    Node leftchild;
    Node rightchild;

    public Node(String ip) {
        this(ip, null, null, 0);
    }

    public Node(String ip, Node leftchild, Node rightchild, int height) {
        this.ip = ip;
        this.leftchild = leftchild;
        this.rightchild = rightchild;
        this.height = height;
    }

    public String getIp() {
        if (ip == null){
            return null;
        }
        return ip;
    }

    public int getHeight() {
        return height;
    } 

    public Node getLeftchild() {
        if(leftchild == null){
            return null;
        }
        return leftchild;
    }

    public Node getRightchild() {
        if(rightchild == null){
            return null;
        }
        return rightchild;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLeftchild(Node leftchild) {
        this.leftchild = leftchild;
    }

    public void setRightchild(Node rightchild) {
        this.rightchild = rightchild;
    }
}
