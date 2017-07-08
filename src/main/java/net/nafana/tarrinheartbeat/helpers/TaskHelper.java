package net.nafana.tarrinheartbeat.helpers;

public abstract class TaskHelper {

    /* Task counter contains the total amount of tasks that are registered
    *
    * This does not yet contain an error check for a value that is greater than,
    * 256 (since it gets casted to a byte to fill the role of a task id).
    *
    * Note: If you're registering over 256 processes to run off of these callbacks,
    * you probably want to change your approach.
    *
    * */
    private int taskCounter = 0;


    /* Assigns the task ID to a task
    *
    * This mainly gets used by the Task object to assign a new id to a new task,
    * it keeps aware of the other tasks before it.
    *
    * */
    protected byte assignTaskID () {

        taskCounter++;
        return (byte)(taskCounter - 128);

    }

}
