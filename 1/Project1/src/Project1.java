import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;

class Project1 {

    public static void main(String args[]) throws FileNotFoundException, IOException {
        FactoryImpl factory = new FactoryImpl();

         
        BufferedReader br = new BufferedReader( new FileReader(args[0]));
        BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
        String line = br.readLine();
        while (line != null) {
            String[] commands = new String[4];
            commands = line.split(" ");
            try {
                if (commands[0].equals("AF")) {
                    factory.addFirst(new Product(Integer.parseInt(commands[1]), Integer.parseInt(commands[2])));    
                }
                if (commands[0].equals("AL")) {
                    factory.addLast(new Product(Integer.parseInt(commands[1]), Integer.parseInt(commands[2])));
                }
                if (commands[0].equals("RF")) {
                    bw.write(factory.removeFirst().toString() + "\n");
                }
                if (commands[0].equals("RL")) {
                    bw.write(factory.removeLast().toString() + "\n");
                }
                if (commands[0].equals("A")) {
                    factory.add(Integer.parseInt(commands[1]), new Product(Integer.parseInt(commands[2]), Integer.parseInt(commands[3])));
                }
                if (commands[0].equals("RI")) {
                    bw.write(factory.removeIndex(Integer.parseInt(commands[1])).toString() + "\n");
                } 
                if (commands[0].equals("RP")) {
                    bw.write(factory.removeProduct(Integer.parseInt(commands[1])).toString() + "\n");
                }
                if (commands[0].equals("G")) {
                    bw.write(factory.get(Integer.parseInt(commands[1])).toString() + "\n");
                }
                if (commands[0].equals("U")) {
                    bw.write(factory.update(Integer.parseInt(commands[1]), Integer.parseInt(commands[2])).toString() + "\n");
                }
                if (commands[0].equals("FD")) {
                    bw.write(Integer.toString(factory.filterDuplicates()) + "\n");
                }
                if (commands[0].equals("R")) {
                    factory.reverse();
                    bw.write(factory.toString() + "\n");
                }
                if (commands[0].equals("P")) {
                    bw.write(factory.toString() + "\n");
                }
                if (commands[0].equals("F")) {
                    bw.write(factory.find(Integer.parseInt(commands[1])).toString() + "\n");
                }
            } catch(NoSuchElementException e) {
                bw.write(e.getMessage() + "\n");
            } catch (IndexOutOfBoundsException e) {
                bw.write(e.getMessage() + "\n");
            }

            line = br.readLine();
        }
        
        bw.close();
        br.close();
    }
}
