package main.java.server;

import main.java.utilities.CarDTO;

import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerThreadMediationQueues
{
    private static AbstractQueue<CarDTO> redCarTransforms = new ConcurrentLinkedQueue<>();
    private static AbstractQueue<CarDTO> greenCarTransforms = new ConcurrentLinkedQueue<>();

    /**
     * Add a Car data transmission update object to the shared server queue
     * @param threadNumber
     * @param carTransaction
     */
    public static void enqueue(int threadNumber, CarDTO carTransaction)
    {
        if (threadNumber == 1) {
            redCarTransforms.add(carTransaction);
        }
        else {
            greenCarTransforms.add(carTransaction);
        }
    }

    /**
     * Remote the most recent Car data transmission update object from the shared server queue
     * @param threadNumber
     * @return CarDTO Most recent Car transformation update
     */
    public static CarDTO dequeue(int threadNumber)
    {
        if (threadNumber == 1) {
            return greenCarTransforms.poll();
        }
        else {
            return redCarTransforms.poll();
        }
    }

    /**
     * Determine whether either shared DTO queue is empty
     * @return
     */
    public static boolean isEitherQueueEmpty()
    {
        if (redCarTransforms.isEmpty() || greenCarTransforms.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}
