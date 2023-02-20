import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class ACC {

    String name;
    ArrayList<String> airports;
    ArrayList<Flight> flights = new ArrayList<Flight>();
    PriorityQueue<Flight> queue;
    int finishtime = 0;

    public ACC(String name, ArrayList<String> airports) {
        this.name = name;
        this.airports = airports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getAirports() {
        return airports;
    }

    public void setAirports(ArrayList<String> airports) {
        this.airports = airports;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public void creatingPriorityQueue() {
        Comparator<Flight> comparator = new FlightComparator();
        this.queue = new PriorityQueue<Flight>(flights.size(),comparator);
        queue.addAll(flights);
    }

    public void setFinishtime(int time) {
        this.finishtime = time;
    }

    public int getFinishtime() {
        return finishtime;
    }

    ATC[] HashTable = new ATC[1000];
    public int getHashValue(String airportCode) {
        int HashValue = 0;
        for (int i = 0; i < airportCode.length(); i++) {
            HashValue += (int) airportCode.charAt(i) * (int) Math.pow(31, i);
        }
        return HashValue;
    }

    public void addHashTable(String airportCode) {
        int HashValue = getHashValue(airportCode) % 1000;
        while(HashTable[HashValue] != null) {
            HashValue = (HashValue + 1) % 1000;
        }
        HashTable[HashValue] = new ATC(airportCode);
    }

    public int getIndex(String airportName){
        int HashValue = getHashValue(airportName) % 1000;
        while(HashTable[HashValue] != null && !HashTable[HashValue].getName().equals(airportName)) {
            HashValue = (HashValue + 1) % 1000;
        }
        return HashValue;
    }

    public String getAirportName(){
        String airportName = "";
        for(int i = 0; i < HashTable.length; i++){
            if(i < 10){
                if (HashTable[i] != null) {
                    airportName += " " + HashTable[i].getName() + 0 + 0+ i;
                }
            }
            else if (i < 100){
                if (HashTable[i] != null) {
                    airportName +=  " " + HashTable[i].getName() +0+ i;
                }
            }

            else {
                if (HashTable[i] != null) {
                    airportName +=  " " + HashTable[i].getName() + i;
                }
            }
        }
        return airportName;
    }

    public void implementation() throws IOException{
        creatingPriorityQueue();
        int totaltime = 0;
        Queue <Flight> queue3 = new ArrayDeque<>();  //ACC
        int counter = 30;
        Boolean truefalse = false;
        while(!queue.isEmpty()){
            ArrayList<Flight> toDelete = new ArrayList<Flight>();
            ArrayList<Flight> flightsToCompare = new ArrayList<>();
            ArrayList<Flight> flightsToCompareHigh = new ArrayList<>();
            ArrayList<Flight> flightsToCompareATCDeparture = new ArrayList<>();
            ArrayList<Flight> flightsToCompareATCArrival = new ArrayList<>();
            Flight flight = queue.peek();
            PriorityQueue<Flight> queueCopy = new PriorityQueue<>(queue);
            boolean switchcase = false;
            while(!queueCopy.isEmpty() && !switchcase) {
                ATC departureATC = HashTable[getIndex(flight.departureAirport.getName())];
                ATC arrivalATC = HashTable[getIndex(flight.arrivalAirport.getName())];
                switch (flight.operations.size()) {
                    case 1, 9, 11, 19, 21:
                        if (flight != queue3.peek()) {
                            queueCopy.remove(flight);
                            flight = queueCopy.peek();
                        }
                        else {
                            switchcase = true;
                        }
                        break;
                    case 8, 6, 4, 2:
                        if (flight != arrivalATC.getATCqueue().peek()) {
                            queueCopy.remove(flight);
                            flight = queueCopy.peek();
                        }
                        else {
                            switchcase = true;
                        }
                        break;
                    case 18, 16, 14, 12:
                        if (flight != departureATC.getATCqueue().peek()) {
                            queueCopy.remove(flight);
                            flight = queueCopy.peek();
                        }
                        else {
                            switchcase = true;
                        }
                        break;
                    case 3, 5, 7, 10, 13, 15, 17, 20, 22:
                        switchcase = true;
                        break;
                }
            }

            int time = flight.operations.getFirst();
            for (Flight flight1 : flights){
                ATC departureATC = HashTable[getIndex(flight1.departureAirport.getName())];
                ATC arrivalATC = HashTable[getIndex(flight1.arrivalAirport.getName())];
                departureATC.controller = false;
                arrivalATC.controller = false;
            }
            boolean controller = false;
            if(queue3.isEmpty()){
                for (Flight flight1 : flights){
                    ATC departureATC = HashTable[getIndex(flight1.departureAirport.getName())];
                    ATC arrivalATC = HashTable[getIndex(flight1.arrivalAirport.getName())];
                    switch(flight1.operations.size()){
                        case 3,5,7,10,13,15,17,20,22:
                            if(!flight1.processed) {
                                int variable = flight1.operations.size();
                                flight1.setTime(time, variable, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), flight1, flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                            }
                            break;
                        case 1,9,11,19,21:
                            if(!controller){
                                if(!flight1.processed){
                                    int variable2 = queue3.peek().operations.size();
                                    queue3.peek().processed = true;
                                    queue3.peek().setTime(time, variable2,queue, queue3,departureATC.getATCqueue(),arrivalATC.getATCqueue(),queue3.peek(),flightsToCompare,flightsToCompareHigh,flightsToCompareATCDeparture,flightsToCompareATCArrival,toDelete,truefalse);
                                    controller = true;
                                }
                            }
                            break;
                        case 8,6,4,2:
                            if(!arrivalATC.controller){
                                if(!flight1.processed){
                                    if(flight1 == arrivalATC.getATCqueue().peek()) {
                                        int variable3 = arrivalATC.getATCqueue().peek().operations.size();
                                        arrivalATC.getATCqueue().peek().processed = true;
                                        arrivalATC.getATCqueue().peek().setTime(time, variable3, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), arrivalATC.getATCqueue().peek(), flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                                        arrivalATC.controller = true;
                                    }
                                }
                            }
                            break;
                        case 18,16,14,12:
                            if(!departureATC.controller) {
                                if (!flight1.processed) {
                                    if(flight1 == departureATC.getATCqueue().peek()) {
                                        int variable4 = departureATC.getATCqueue().peek().operations.size();
                                        departureATC.getATCqueue().peek().processed = true;
                                        departureATC.getATCqueue().peek().setTime(time, variable4, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), departureATC.getATCqueue().peek(), flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                                        departureATC.controller = true;
                                    }
                                }
                            }
                            break;
                    }
                }
                for (Flight flight5 : toDelete){
                    flights.remove(flight5);
                }
                totaltime += time;
            }
            else if(time > 30 && counter == 30){
                for (Flight flight1 : flights){
                    ATC departureATC = HashTable[getIndex(flight1.departureAirport.getName())];
                    ATC arrivalATC = HashTable[getIndex(flight1.arrivalAirport.getName())];
                    switch(flight1.operations.size()){
                        case 3,5,7,10,13,15,17,20,22:
                            if(!flight1.processed) {
                                int variable = flight1.operations.size();
                                flight1.setTime(30, variable, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), flight1, flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                            }
                            break;
                        case 1,9,11,19,21:
                            if(!controller){
                                if(!flight1.processed){
                                    int variable2 = queue3.peek().operations.size();
                                    queue3.peek().processed = true;
                                    queue3.peek().setTime(30, variable2,queue, queue3,departureATC.getATCqueue(),arrivalATC.getATCqueue(),queue3.peek(),flightsToCompare,flightsToCompareHigh,flightsToCompareATCDeparture,flightsToCompareATCArrival,toDelete,truefalse);
                                    controller = true;
                                }
                            }
                            break;
                        case 8,6,4,2:
                            if(!arrivalATC.controller){
                                if(!flight1.processed){
                                    if(flight1 == arrivalATC.getATCqueue().peek()) {
                                        int variable3 = arrivalATC.getATCqueue().peek().operations.size();
                                        arrivalATC.getATCqueue().peek().processed = true;
                                        arrivalATC.getATCqueue().peek().setTime(30, variable3, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), arrivalATC.getATCqueue().peek(), flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                                        arrivalATC.controller = true;
                                    }
                                }
                            }
                            break;
                        case 18,16,14,12:
                            if(!departureATC. controller) {
                                if (!flight1.processed) {
                                    if(flight1 == departureATC.getATCqueue().peek()) {
                                        int variable4 = departureATC.getATCqueue().peek().operations.size();
                                        departureATC.getATCqueue().peek().processed = true;
                                        departureATC.getATCqueue().peek().setTime(30, variable4, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), departureATC.getATCqueue().peek(), flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                                        departureATC.controller = true;
                                    }
                                }
                            }
                            break;
                    }
                }
                for (Flight flight5 : toDelete){
                    flights.remove(flight5);
                }
                totaltime += 30;
            }
            else{
                if (time >= counter){
                    time = counter;
                    truefalse = true;
                }
                for (Flight flight1 : flights){
                    ATC departureATC = HashTable[getIndex(flight1.departureAirport.getName())];
                    ATC arrivalATC = HashTable[getIndex(flight1.arrivalAirport.getName())];
                    switch(flight1.operations.size()){
                        case 3,5,7,10,13,15,17,20,22:
                            if(!flight1.processed) {
                                int variable = flight1.operations.size();
                                flight1.setTime(time, variable, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), flight1, flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                            }
                            break;
                        case 1,9,11,19,21:
                            if(!controller) {
                                if (!flight1.processed) {
                                    int variable2 = queue3.peek().operations.size();
                                    int checker = queue3.size();
                                    int checker2 = flightsToCompare.size();
                                    Flight pilot = queue3.peek();
                                    queue3.peek().processed = true;
                                    queue3.peek().setTime(time, variable2, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), queue3.peek(), flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                                    if (checker != queue3.size() && checker2 != flightsToCompare.size()) {
                                        truefalse = true;
                                    }
                                    else if (pilot != queue3.peek()){
                                        truefalse = true;
                                    }
                                    controller = true;
                                }
                            }
                            break;
                        case 8,6,4,2:
                            if(!flight1.processed) {
                                if (!arrivalATC.controller) {
                                    if(flight1 == arrivalATC.getATCqueue().peek()) {
                                        int variable2 = arrivalATC.getATCqueue().peek().operations.size();
                                        arrivalATC.getATCqueue().peek().processed = true;
                                        arrivalATC.getATCqueue().peek().setTime(time, variable2, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), arrivalATC.getATCqueue().peek(), flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                                        arrivalATC.controller = true;
                                    }
                                }
                            }
                            break;
                        case 18,16,14,12:
                            if(!flight1.processed) {
                                if (!departureATC. controller) {
                                    if(flight1 == departureATC.getATCqueue().peek()){
                                    int variable3 = departureATC.getATCqueue().peek().operations.size();
                                    departureATC.getATCqueue().peek().processed = true;
                                    departureATC.getATCqueue().peek().setTime(time, variable3, queue, queue3, departureATC.getATCqueue(), arrivalATC.getATCqueue(), departureATC.getATCqueue().peek(), flightsToCompare, flightsToCompareHigh, flightsToCompareATCDeparture, flightsToCompareATCArrival, toDelete, truefalse);
                                    departureATC. controller = true;
                                    }
                                }
                            }
                            break;
                    }
                }
                for (Flight flight5 : toDelete){
                    flights.remove(flight5);
                }
                if(truefalse){
                    counter = 30;
                    truefalse = false;
                }
                else if(!queue3.isEmpty()){
                    counter = counter - time;
                }
                totaltime += time;
            }

            for(Flight flight2 : flights){
                flight2.processed = false;
            }

            if(flightsToCompareHigh.size() > 1) {
                Collections.sort(flightsToCompareHigh, new Comparator<Flight>() {
                    @Override
                    public int compare(Flight o1, Flight o2) {
                        return o1.getFlightcode().compareTo(o2.getFlightcode());
                    }
                });
            }

            if(flightsToCompare.size() > 1) {
                Collections.sort(flightsToCompare, new Comparator<Flight>() {
                    @Override
                    public int compare(Flight o1, Flight o2) {
                        return o1.getFlightcode().compareTo(o2.getFlightcode());
                    }
                });
            }

            if(flightsToCompareATCDeparture.size() > 1) {
                Collections.sort(flightsToCompareATCDeparture, new Comparator<Flight>() {
                    @Override
                    public int compare(Flight o1, Flight o2) {
                        return o1.getFlightcode().compareTo(o2.getFlightcode());
                    }
                });
            }
            if(flightsToCompareATCArrival.size() > 1) {
                Collections.sort(flightsToCompareATCArrival, new Comparator<Flight>() {
                    @Override
                    public int compare(Flight o1, Flight o2) {
                        return o1.getFlightcode().compareTo(o2.getFlightcode());
                    }
                });
            }

            for (Flight flight1 : flightsToCompareHigh){
                queue3.add(flight1);
            }
            for (Flight flight1 : flightsToCompare){
                queue3.add(flight1);
            }
            for (Flight flight1 : flightsToCompareATCDeparture){
                ATC departureATC = HashTable[getIndex(flight1.departureAirport.getName())];
                departureATC.getATCqueue().add(flight1);
            }
            for (Flight flight1 : flightsToCompareATCArrival){
                ATC arrivalATC = HashTable[getIndex(flight1.arrivalAirport.getName())];
                arrivalATC.getATCqueue().add(flight1);
            }
            if(queue.size() == 1 && queue.peek().operations.size() == 0){
                queue.poll();
            }
        }
        setFinishtime(totaltime);
    }
}
