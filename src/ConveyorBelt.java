import java.awt.*;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * The ConveyorBelt class is used to instantiate a conveyor belt
 * upon which the the parcels are produced and dispatched by producers
 * and dispatcher threads. The ConveyorBelt class is also used to draw an
 * illustration of the belt as well as the connected machines and connected dispatchers
 * interacting with the belt.
 */
public class ConveyorBelt {
    private int maxCapacity;
    private Machine connectedMachine;
    private Dispatcher connectedDispatcher;
    private PriorityQueue<Parcel<?>> priorityQueue;

    public ConveyorBelt(int maxCapacity){
        priorityQueue = new PriorityQueue<>();
        this.maxCapacity = maxCapacity;
    }


    public ConveyorBelt(){
        priorityQueue = new PriorityQueue<>();
        maxCapacity = 10;
    }

    public boolean connectMachine(Machine machine){
        if (connectedMachine == null){
            connectedMachine = machine;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean connectDispatcher(Dispatcher dispatcher){
        if (connectedDispatcher == null){
            connectedDispatcher = dispatcher;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean disconnectMachine(Machine machine){
        if (connectedMachine != null && connectedMachine == machine){
            connectedMachine = null;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean disconnectDispatcher(Dispatcher dispatcher){
        if (connectedDispatcher != null && connectedDispatcher == dispatcher){
            connectedDispatcher = null;
            return true;
        }
        else{
            return false;
        }
    }

    public int size(){
        return priorityQueue.size();
    }

    public boolean isEmpty(){
        return priorityQueue.size() == 0;
    }

    public boolean isFull(){
        if (size() == maxCapacity){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean postParcel(Parcel<?> p, Machine machine){
        if (machine == connectedMachine && priorityQueue.size() < maxCapacity){
            priorityQueue.add(p);
            return true;
        }
        else {
            return false;
        }
    }

    public Parcel<?> getFirstParcel(Dispatcher dispatcher){
        if (connectedDispatcher != null && connectedDispatcher == dispatcher){
            return priorityQueue.peek();
        }
        return null;
    }

    public Parcel<?> retrieveParcel(Dispatcher dispatcher){
        if (connectedDispatcher != null && connectedDispatcher == dispatcher){
            return priorityQueue.poll();
        }
        return null;
    }

    public void drawBelt(Graphics g, int x, int y, int width, int height){
        g.drawRect(x,y,width,height);

        Queue<Parcel<?>> parcels = new LinkedList<>(priorityQueue);

        int xPos = 0;

        for (int j = 0; j < maxCapacity; j++){
            xPos = x+(100*j);

            if (parcels.peek() != null){
                parcels.poll().drawBox(g, xPos, y, 100, 100);
            }

            g.setColor(Color.black);
            g.drawRect(xPos, y, width, height);

            if (connectedMachine != null){
                g.setColor(Color.BLUE);
                g.fillOval(985, y, 100, 100);
            }

            if(connectedDispatcher != null){
                g.setColor(Color.red);
                g.fillOval(75, y, 100, 100);
            }
        }
    }
}