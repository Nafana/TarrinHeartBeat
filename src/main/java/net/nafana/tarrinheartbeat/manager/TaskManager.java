package net.nafana.tarrinheartbeat.manager;

import net.nafana.tarrinheartbeat.enums.CallbackType;
import net.nafana.tarrinheartbeat.interfaces.ITarrinHeartbeatEvent;
import net.nafana.tarrinheartbeat.tasks.Task;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

/* The TaskManager class
*
* This class extends BukkitRunnable since it's handled more appropriately in game than
* running YOUR OWN threads. This class however is made for the purpose of tracking and schdueling
* our own events differently.
*
* */
public final class TaskManager extends BukkitRunnable{

    /* TaskManager object fields
    *
    *  HeartBeat: The current tick that the task manager is on.
    *  NextHeartBeat: Is the current tick with the addition of the game beat period.
    *  BeatPeriod: After how many game ticks do we want to run our task manager check?
    *  e: Reference to the ITarrinHeartbeatEvent interface.
    *  tasks: Each task manager is responsible for keeping its own tasks controlled. It
    *   stores them in an array list.
    *
    * */
    private int heartBeat;
    private int nextHeartBeat;
    private final int beatPeriod;
    private ITarrinHeartbeatEvent e;
    private ArrayList<Task> tasks; // Each task should utilize the internal counter manually!

    /* Arguments regarding the TaskManager instantiation.
    *
    * The arguments taken are a reference to the ITarrinHeartbeatEvent interface,
    * this let's us code a custom task handle. The beat period, after how many game ticks,
    * do we want the heart to beat?
    *
    * */
    public TaskManager(ITarrinHeartbeatEvent event, int beatPeriod) {

        this.tasks = new ArrayList<Task>();
        this.heartBeat = 0;
        this.beatPeriod = beatPeriod;
        this.nextHeartBeat = beatPeriod;

        this.e = event;
    }

    /* The main run method for the BukkitRunnable class.
    *
    * This is where all the magic happens. The heart beat ticks every tick,
    * then we decide if it's the proper time for our heart to tick? If our heart ticks,
    * we proceed with determining if it needs to do tasks at this given moment.
    *
    * */
    @Override
    public void run() {

        heartBeat++;

        //Checking if the proper time has come to call the next beat.
        if (heartBeat == nextHeartBeat) {

            // Setting the next beat to the appropriate value.
            nextHeartBeat = heartBeat + beatPeriod;

            // Checking if the task list is empty, let's just return!
            if (this.tasks.isEmpty()){
                return;
            }

            // Loops through all the tasks in our pipeline.
            for (int i = 0; i < this.tasks.size(); i++){

                Task t = this.tasks.get(i);

                if (t.getNextActivationPoint() <= heartBeat) {

                    // This is a task that never stops! We just need to edit the time it should next be activated, and the times its been repeated.
                    if (t.getCallbackType() == CallbackType.RECURRING) {

                        t.setNextActivationPoint(heartBeat + t.getPeriodActivationPoint());
                        t.setRepetitionValue(t.getRepetitionValue() + 1);

                    }
                    // This task only happens a certain amount of times
                    else if (t.getCallbackType() == CallbackType.COUNTED) {

                        t.setNextActivationPoint(heartBeat + t.getPeriodActivationPoint());
                        t.setRepetitionValue(t.getRepetitionValue() + 1);

                        if (t.getRepetitionValue() >= t.getRepetitionActivationPoint()) {

                            tasks.remove(t);

                        }

                    }
                    // This task only happens once! Let's remove it...
                    else {

                        tasks.remove(t);

                    }

                    // This uses the ITarrinHeartbeatEvent reference to make the callback.
                    this.e.callEvent(t);

                }

            }

        }

    }


    /* Adds a task to the pipeline.
    *
    *  This uses the task object itself to find itself in the tasks array list.
    *
    * */
    public void addTask (Task task) {

        if(tasks == null)
            return;

        task.setNextActivationPoint(heartBeat);
        this.tasks.add(task);

    }

    /* Cancels a task from the pipeline.
    *
    * Cancels the task using the object itself.
    *
    * */
    public void cancelTask (Task task) {

        if(tasks == null)
            return;

        this.tasks.remove(task);

    }

    /* Cancels a task from the pipeline.
    *
    * Cancels the task using the object ID.
    *
    * */
    public void cancelTask (byte taskID) {

        for (int i = 0; i < tasks.size(); i++) {

            if (tasks.get(i).getTaskID() == taskID) {

                cancelTask(tasks.get(i));
                break;

            }

        }

    }

    /*
    *  Gets the main heart beat counter.
    * */
    public int getHeartBeat() {
        return heartBeat;
    }

    /*
    *  Gets the next heart beat in terms of game ticks.
    * */
    public int getNextHeartBeat() {
        return nextHeartBeat;
    }

    /*
    *  Gets the TaskManager beat period.
    * */
    public int getBeatPeriod() {
        return beatPeriod;
    }

    /*
    *  EMERGENCY: This should only be called during a critical failure,
    *  this cancels the TaskManager as the runnable. Killing all tasks,
    *  and everything to do with it.
    * */
    public void emergencyShutDown () {
        this.cancel();
    }

}
