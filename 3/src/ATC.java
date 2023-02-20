import java.util.LinkedList;
import java.util.Queue;

public class ATC {
    String name;
    Queue <Flight> ATCqueue = new LinkedList<>();
    boolean controller = false;

    public ATC(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Queue<Flight> getATCqueue() {
        return ATCqueue;
    }

    public void setATCqueue(Queue<Flight> ATCqueue) {
        this.ATCqueue = ATCqueue;
    }

    public void addFlight(Flight flight){
        ATCqueue.add(flight);
    }

    public boolean isController() {
        return controller;
    }
}

