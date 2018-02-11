import java.util.logging.Level;
import java.util.logging.Logger;

public class Rider extends Thread {
    private int rider_id;
    Rider(int index) {
        this.rider_id = index;
    }

    /**
     * a rider will  first get in to the waiting set
     * When the bus arrives , rider can get into the bus by acquiring it
     */

    public void run() {
        try {
            Stimulate.mutex.acquire();
            System.out.println("Rider " + rider_id + " is waiting !!");
            Stimulate.waiting += 1;                   //increment the number of waiting riders by one
            Stimulate.mutex.release();

            Stimulate.bus.acquire();                  //acquire the bus semaphore to get on board
            System.out.println("Rider " + rider_id + " got onboard.");
            Stimulate.boarded.release();              //once boarded, release the boarded semaphore

        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, "Rider" + rider_id + "'s thread got interrupted !!", ex);

        }
    }
}
