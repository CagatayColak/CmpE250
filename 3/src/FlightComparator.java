import java.util.Comparator;

public class FlightComparator implements Comparator<Flight>{
    @Override
    public int compare(Flight flight1, Flight flight2) {
        if(flight1.operations.getFirst() > flight2.operations.getFirst()){
            return 1;
        }else if(flight1.operations.getFirst() < flight2.operations.getFirst()){
            return -1;
        }else{
            return 0;
        }
    }
}
