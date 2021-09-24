public class Dispatcher implements Runnable{
    private ConveyorBelt[] conveyorBelts;
    private boolean stop = false;

    public Dispatcher(ConveyorBelt[] conveyorBelts) {
        this.conveyorBelts = conveyorBelts;
    }

    @Override
    public void run() {

        while(!stop) {
            try{
                consumer();
            }
            catch (InterruptedException e){

            }
        }
    }


    public void stop(){
        for (int i = 0; i < conveyorBelts.length; i++){
            conveyorBelts[i].disconnectDispatcher(this);
        }
        stop = true;
    }


    public synchronized void consumer() throws InterruptedException {
        for (int i = 0; i < conveyorBelts.length; i++) {
            if (!conveyorBelts[i].isEmpty()) {
                if (conveyorBelts[i].connectDispatcher(this)) {
                        conveyorBelts[i].getFirstParcel(this).consume();
                        conveyorBelts[i].retrieveParcel(this);
                        conveyorBelts[i].disconnectDispatcher(this);
                }
            }
        }
    }
}
