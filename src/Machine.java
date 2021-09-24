import java.awt.*;
import java.util.Random;

public class Machine implements Runnable {
    public static int MIN_CONSUMPTION_TIME = 50;
    public static int MAX_CONSUMPTION_TIME = 300;
    public static int MIN_PRODUCTION_TIME = 100;
    public static int MAX_PRODUCTION_TIME = 500;
    private boolean stop = false;
    private ConveyorBelt[] conveyorBelts;

    public Machine(ConveyorBelt[] conveyorBelts){
        this.conveyorBelts = conveyorBelts;
    }

    public Parcel<?> createParcel(){
        Color color = new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());

        int consumeTime = new Random().nextInt(MAX_CONSUMPTION_TIME)+1;

        while(consumeTime < MIN_CONSUMPTION_TIME) {
            consumeTime = new Random().nextInt(MAX_CONSUMPTION_TIME) + 1;
        }
        System.out.println(consumeTime);

        char letter = (char) (new Random().nextInt(91)+1);

        while ((int) letter < 65){
            letter = (char) (new Random().nextInt(91)+1);
        }

        Object o = new Random().nextInt();

        return new Parcel<>(letter, color, consumeTime, new Random().nextInt(3)+1);
    }

    @Override
    public void run() {

        while (!stop) {
            try {
                machine();
            } catch (InterruptedException e) {

            }
        }
    }


    public void stop(){
        for (int i = 0; i < conveyorBelts.length; i++){
            conveyorBelts[i].disconnectMachine(this);
        }
        stop = true;
    }


    public synchronized void machine() throws InterruptedException {
        int duration = new Random().nextInt(MAX_PRODUCTION_TIME) + 1;

        while (duration < MIN_PRODUCTION_TIME) {
            duration = new Random().nextInt(MAX_PRODUCTION_TIME) + 1;
            System.out.println(duration);
        }

        System.out.println("Final Duration time: "+duration);

        for (int i = 0; i < conveyorBelts.length; i++) {
            if (!conveyorBelts[i].isFull()) {
                if (conveyorBelts[i].connectMachine(this)) {
                    while (!conveyorBelts[i].isFull()) {
                        Thread.sleep(duration);
                        conveyorBelts[i].postParcel(createParcel(), this);
                        }
                    }
                    conveyorBelts[i].disconnectMachine(this);
                }
            }
        }
    }