import java.awt.*;

public class Parcel<E> implements Comparable<Parcel<?>> {
    private E element;
    private Color color;
    private int consumeTime, priority;
    private Long timeStamp;


    public Parcel(E element, Color color, int consumeTime, int priority){
        this.element = element;
        this.color = color;
        this.consumeTime = consumeTime;
        this.priority = priority;
        timeStamp = System.nanoTime();
    }

    public void consume() throws InterruptedException {
        try {
            Thread.sleep(consumeTime);
        }
        catch (InterruptedException e){
            System.err.println("Sleep Error");
        }
    }

    public String toString(){
        return element+"("+priority+")";
    }

    public void drawBox(Graphics g, int x, int y, int width, int height){

        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawString(toString(), x+10, y+20);
    }

    @Override
    public int compareTo(Parcel<?> o) {
        if (priority == o.priority){
            if (this.timeStamp < o.timeStamp){
                return 1;
            }
            else{
                return -1;
            }
        }
        else if (this.priority > o.priority){
            return 1;
        }
        else{
            return -1;
        }
    }
}