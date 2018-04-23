package main.java.server;

import main.java.client.Car;
import main.java.utilities.CarDTO;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class ThreadCommunicationQueues
{
    private static AbstractQueue<Object> redCarMessages = new ConcurrentLinkedQueue<>();
    private static AbstractQueue<Object> greenCarMessages = new ConcurrentLinkedQueue<>();

    /**
     * Add a Car data transmission update object to the shared server queue
     * @param threadNumber
     * @param carTransaction
     */
    public synchronized static void enqueue(int threadNumber, Object carTransaction)
    {
        if (threadNumber == 1) {
            redCarMessages.add(carTransaction);
        }
        else {
            greenCarMessages.add(carTransaction);
        }
    }

    /**
     * Remote the most recent Car data transmission update object from the shared server queue
     * @param threadNumber
     * @return CarDTO Most recent Car transformation update
     */
    public synchronized static Object dequeue(int threadNumber)
    {
        if (threadNumber == 1) {
            if (greenCarMessages.size() > 1) {
                return greenCarMessages.poll();
            }

            return greenCarMessages.peek();
        }
        else {
            if (redCarMessages.size() > 1) {
                return redCarMessages.poll();
            }

            return redCarMessages.peek();
        }
    }

    /**
     * Determine whether either shared DTO queue is empty
     * @return
     */
    public synchronized static boolean isEitherQueueEmpty()
    {
        if (redCarMessages.isEmpty() || greenCarMessages.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}
