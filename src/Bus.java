import java.util.logging.Level;
import java.util.logging.Logger;

public class Bus extends Thread{
    private int riders_to_board;   //number of riders to board when the bus arrives
    private int bus_id;
    private int rider_count = 0;   //nuber of riders boarded

    Bus(int index) {
        this.bus_id = index;
    }

    /**
     *  When the bus arrived at the stop it will lock the bus stop and inform all the raiders.
     *  Bus will allow raiders to get int on by one.
     */
    public void run() {
        try {
            Stimulate.mutex.acquire();                             //bus locks the mutex
            System.out.println("Bus " + bus_id + " arrived at the bus stop !!");

            riders_to_board = Math.min(Stimulate.waiting, 50);//at most 50 passengers can get into a bus

            for (int i = 0; i < riders_to_board; i++) {     //loop through the riders
                Stimulate.bus.release();                              //bus signals that it has arrived and can take a passenger on board
                Stimulate.boarded.acquire();                          //Allows one rider to get on board
                rider_count++;
            }

            Stimulate.waiting = Math.max((Stimulate.waiting - 50), 0); // calculates the remaining waiting
            Stimulate.mutex.release();

        } catch (InterruptedException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, "Bus " + bus_id + "'s thread got interrupted !!", ex);
        }

        System.out.println("Bus " + bus_id + " departed with " + rider_count + " riders on board!");
    }
}
