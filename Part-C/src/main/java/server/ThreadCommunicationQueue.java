package main.java.server;

import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadCommunicationQueue
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
            return greenCarMessages.poll();
        }
        else {
            return redCarMessages.poll();
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
