import java.io.*;


public class Main {
    public static void main(String args[]) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1] + "_avl.txt"));
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(args[1] + "_bst.txt"));
        String line = reader.readLine();
        String[] inputCommand = new String[3];
        inputCommand = line.split(" ");
        AVL avlTree = new AVL(inputCommand[0]);
        BST bstTree = new BST(inputCommand[0]);
        while ((line = reader.readLine()) != null) {
            inputCommand = line.split(" ");
            switch (inputCommand[0]) {
                case "ADDNODE":
                    avlTree.insert(inputCommand[1], writer);
                    bstTree.insert(inputCommand[1], writer2);
                    break;
                case "DELETE":
                    avlTree.remove(inputCommand[1], writer);
                    bstTree.remove(inputCommand[1], writer2);
                    break;   
                case "SEND":
                    avlTree.sendMessage(inputCommand[1], inputCommand[2], writer);
                    bstTree.sendMessage(inputCommand[1], inputCommand[2], writer2);
                    break;
            }
        }
        writer.close();
        writer2.close();
    }
}
