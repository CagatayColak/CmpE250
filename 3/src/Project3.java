import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Project3 {
    public static void main(String args[]) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String line = reader.readLine();
        String[] lineSplit = line.split(" ");
        int numberOfAcc = Integer.parseInt(lineSplit[0]);
        ArrayList<ACC> accs = new ArrayList<>();
        for(int i = 0; i < numberOfAcc; i++){
            line = reader.readLine();
            lineSplit = line.split(" ");
            ArrayList<String> airports = new ArrayList<String>(Arrays.asList(lineSplit));
            airports.remove(0);
            ACC acc = new ACC(lineSplit[0], airports);
            accs.add(acc);
        }
        while ((line = reader.readLine()) != null) {
            lineSplit = line.split(" ");
            ArrayDeque<Integer> operations = new ArrayDeque<>();
            operations.add(Integer.parseInt(lineSplit[0]));
            for(int i = 5; i < lineSplit.length; i++){
                operations.add(Integer.parseInt(lineSplit[i]));
            }
            ATC departureAirport = new ATC(lineSplit[3]);
            ATC arrivalAirport = new ATC(lineSplit[4]);
            Flight flight = new Flight(lineSplit[1], Integer.parseInt(lineSplit[0]), departureAirport, arrivalAirport, operations, false);
            for(ACC acc : accs){
                if(acc.getName().equals(lineSplit[2])){
                    acc.addFlight(flight);
                }
            }
        }
        for(ACC acc : accs){
            for(String airport : acc.getAirports()){
                acc.addHashTable(airport);
            }
        }
        for(ACC acc : accs){
            BufferedWriter writer = new BufferedWriter(new FileWriter(args[1],true));
            if(acc.flights.size() != 0){
                acc.implementation();
                writer.write( acc.getName() +" "+ acc.getFinishtime() + acc.getAirportName());
                writer.write("\n");
                writer.close();
            }
            else{
                writer.write( acc.getName() +" "+ 0 + acc.getAirportName());
                writer.write("\n");
                writer.close();
            }
        }
    }
}

