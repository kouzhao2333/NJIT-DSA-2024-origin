package oy.tol.tra;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class DailyTasks {

   private QueueInterface<String> dailyTaskQueue = null;
   private Timer timer = null;
   private static final int TASK_DELAY_IN_SECONDS = 1 * 1000;

   private DailyTasks() {
   }

   public static void main(String[] args) {
      DailyTasks tasks = new DailyTasks();
      tasks.run();
   }

   private void run() {
      try {
         dailyTaskQueue = new QueueImplementation<>();
         readTasks();
         timer = new Timer();
         timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               try {
                  executeTask();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         }, TASK_DELAY_IN_SECONDS, TASK_DELAY_IN_SECONDS);
      } catch (IOException e) {
         e.printStackTrace();
         System.out.println("Something went wrong :( " + e.getLocalizedMessage());
      }
   }

   private void readTasks() throws IOException {
      String tasks;
      tasks = new String(getClass().getClassLoader().getResourceAsStream("DailyTasks.txt").readAllBytes());
      String[] allTasks = tasks.split("\\r?\\n");
      for (String task : allTasks) {
         dailyTaskQueue.enqueue(task);
      }
      System.out.println("Number of tasks in the queue: " + dailyTaskQueue.size());
   }
   private void executeTask() throws IOException {
      if (!dailyTaskQueue.isEmpty()) {
         System.out.println(dailyTaskQueue.element());
         dailyTaskQueue.dequeue();
      } else {
         timer.cancel();
      }
   }
}
