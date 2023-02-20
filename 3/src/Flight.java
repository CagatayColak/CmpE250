import java.time.YearMonth;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Flight {

    String flightcode;
    int admissiontime;
    ATC departureAirport;
    ATC arrivalAirport;
    ArrayDeque <Integer> operations;
    boolean processed;

    public Flight(String flightcode, int admissiontime, ATC departureAirport, ATC arrivalAirport, ArrayDeque<Integer> operations, boolean processed) {
        this.flightcode = flightcode;
        this.admissiontime = admissiontime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.operations = operations;
        this.processed = processed;
    }

    public String getFlightcode() {
        return flightcode;
    }

    public void setFlightcode(String flightcode) {
        this.flightcode = flightcode;
    }

    public int getAdmissiontime() {
        return admissiontime;
    }

    public void setAdmissiontime(int admissiontime) {
        this.admissiontime = admissiontime;
    }

    public ATC getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(ATC departureAirport) {
        this.departureAirport = departureAirport;
    }

    public ArrayDeque<Integer> getOperations() {
        return operations;
    }

    public void setOperations(ArrayDeque<Integer> operations) {
        this.operations = operations;
    }
    public void setTime(int time, int operation, PriorityQueue<Flight> queue, Queue<Flight> queue3, Queue<Flight> queue2, Queue<Flight> queue4, Flight flight, ArrayList<Flight> flightsToCompare, ArrayList<Flight> flightsToCompareHigh, ArrayList<Flight> flightsToCompareATCDeparture, ArrayList<Flight> flightsToCompareATCArrival, ArrayList<Flight> toDelete, Boolean checker){
        int queuesize = queue.size();
        if(queue.size() > 1){
            queue.remove(flight);
        }
        int temp = flight.operations.pop() - time;
        if (temp > 0) {
            switch(operation) {
                case 1, 9, 11, 19, 21:
                    if (time == 30 || checker) {
                        flightsToCompare.add(flight);
                        queue3.poll();
                    }
                    break;
            }
            flight.operations.addFirst(temp);
            if(queue.size() > 0 && queuesize != 1) {
                queue.add(flight);
            }
        }
        else if (temp == 0){
            if (flight.operations.size() == 0){
                toDelete.add(flight);
                queue.remove(flight);
            }
            else{
                if(queue.size() > 0 && queuesize != 1) {
                    queue.add(flight);
                }
            }
            switch(operation){
                case 1,11,21:
                    queue3.poll();
                    break;
                case 22,20,10:
                    flightsToCompareHigh.add(flight);
                    break;
                case 19:
                    queue3.poll();
                    flightsToCompareATCDeparture.add(flight);
                    break;
                case 9:
                    queue3.poll();
                    flightsToCompareATCArrival.add(flight);
                    break;
                case 12:
                    queue2.poll();
                    flightsToCompareHigh.add(flight);
                    break;
                case 2:
                    queue4.poll();
                    flightsToCompareHigh.add(flight);
                    break;
                case 14,16,18:
                    queue2.poll();
                    break;
                case 4,6,8:
                    queue4.poll();
                    break;
                case 17,15,13:
                    flightsToCompareATCDeparture.add(flight);
                    break;
                case 7,5,3:
                    flightsToCompareATCArrival.add(flight);
                    break;
            }
        }
    }
}


    // @Override
    // public int compareTo(Flight o) {
    //     Flight flight = (Flight) o;
    //     if(this.operations.getFirst() > flight.operations.getFirst()){
    //         return 1;
    //     }else if(this.operations.getFirst() < flight.operations.getFirst()){
    //         return -1;
    //     }else{
    //         return 0;
    //     }
    // }

