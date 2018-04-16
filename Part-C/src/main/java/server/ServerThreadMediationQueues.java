package main.java.server;

import main.java.utilities.CarDTO;

import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerThreadMediationQueues
{
    private static AbstractQueue<CarDTO> redCarTransforms = new ConcurrentLinkedQueue<>();
    private static AbstractQueue<CarDTO> greenCarTransforms = new ConcurrentLinkedQueue<>();

    public static void enqueue(int threadNumber, CarDTO carTransaction)
    {
        if (threadNumber == 1) {
            redCarTransforms.add(carTransaction);
        }
        else {
            greenCarTransforms.add(carTransaction);
        }
    }

    public static CarDTO dequeue(int threadNumber)
    {
        if (threadNumber == 1) {
            return greenCarTransforms.poll();
        }
        else {
            return redCarTransforms.poll();
        }
    }

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
