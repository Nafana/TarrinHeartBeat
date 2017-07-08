package net.nafana.tarrinheartbeat;

import net.nafana.tarrinheartbeat.enums.CallbackType;
import net.nafana.tarrinheartbeat.manager.TaskManager;
import net.nafana.tarrinheartbeat.tasks.Task;
import org.bukkit.plugin.java.JavaPlugin;

public class TarrinHeartbeatMain extends JavaPlugin {


    /* Declares an instance of the TaskManager class
    *
    *  PARAMETERS: Instance of the ITarrinHeartbeatEvent interface, beatPeriod
    *  1. Let's us know which class will be responding to the callbacks.
    *  2. Time in ticks for each beat period - 1 tick = 1/20 seconds
    *
    *  Notes: It's fine to make this instance static however, a class that controls all your task managers is a better practice.
    *  You'll be able to use a simple getter without the risk of an external class harming it (if set to public).
    * */
    private static TaskManager primaryManager = new TaskManager(new TestClass(), 5);

    public void onEnable () {


        /* Sets the task timer to run instantly with a beat period, then we add a task!
        *
        *  RunTaskTimer PARAMETERS: Plugin instance, delay to start, period.
        *  AddTask PARAMETERS: New Instance of a task
        *  1. The task accepts the callback type as the first parameters.
        *  2. It accepts the period, since our callback in singular however we do not need a period,
        *  it only runs once!
        *  3. Times to repeat is best used with a COUNTED callback, this tracks how many times we
        *  should be repeating the task.
        *
        * */
        primaryManager.runTaskTimer(this, 0, primaryManager.getBeatPeriod());
        primaryManager.addTask(new Task(CallbackType.SINGULAR, -1, -1));
        primaryManager.addTask(new Task(CallbackType.COUNTED, 2, 3));
        primaryManager.addTask(new Task(CallbackType.SINGULAR, 5, -1));

    }

    public void onDisable () {



    }

}
