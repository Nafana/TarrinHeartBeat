package net.nafana.tarrinheartbeat.tasks;

import net.nafana.tarrinheartbeat.enums.CallbackType;
import net.nafana.tarrinheartbeat.helpers.TaskHelper;

public class Task extends TaskHelper{

    /* Task object fields.
    *
    * CallbackType: Let's us know how we need to execute this task.
    * TaskID: Let's us differentiate different tasks, we could have multiple tasks with the same type.
    * PeriodActivationPoint: The period for the task, how often we want it to run (this is the limiting agent)
    *   as this depends on the speed of the heartbeat in the TaskManager instentient.
    * RepetitionActivationPoint: The times needed to repeat this task.
    * NextActivationPoint: The next tick that we need to execute this task at.
    * RepetitionValue: How many times have we already repeated this task?
    *
    * */
    private final CallbackType t;
    private final byte taskID;
    private final int periodActivationPoint;
    private final int repetitionActivationPoint;
    private int nextActivationPoint;
    private int repetitionValue;

    /* Arguments regarding the Task object instantiation
    *
    * In the first option for this method call, we need all the information to
    * instantiate a copy.
    *
    * The repetition value is set to 0, since there were no instances of this task returning
    * a callback yet.
    * We also generate an id for this task.
    *
    * */
    public Task (CallbackType callbackType, int period, int timesToRepeat) {

        this.t = callbackType;
        this.periodActivationPoint = period;
        this.repetitionActivationPoint = timesToRepeat;
        this.repetitionValue = 0;

        taskID = this.assignTaskID();
    }

    /* Arguments regarding the Task object instantiation
    *
    * With this version of the method call we can safely ignore the timesToRepeat
    * parameter since we hint at the fact that the callback has to be of type
    * RECURRING, or SINGULAR. Just in case someone decides to use this for a COUNTER
    * type, we make sure to handle it.
    *
    * See: Previous method call explanation for more detail.
    *
    * */
    public Task (CallbackType callbackType, int period) {

        if (callbackType == CallbackType.COUNTED)
            this.repetitionActivationPoint = 1;
        else this.repetitionActivationPoint = -1;

        this.t = callbackType;
        this.periodActivationPoint = period;
        this.repetitionValue = 0;

        taskID = this.assignTaskID();
    }

    /*
    *  Returns the callback type.
    * */
    public CallbackType getCallbackType() {
        return t;
    }

    /*
    *  Returns the task ID.
    * */
    public byte getTaskID() {
        return taskID;
    }

    /*
    *  Returns the repetition value.
    * */
    public int getRepetitionValue() {
        return repetitionValue;
    }

    /*
    *  Returns the repetition activation point.
    * */
    public int getRepetitionActivationPoint() {
        return repetitionActivationPoint;
    }

    /*
    *  Returns the period activation point.
    * */
    public int getPeriodActivationPoint() {
        return periodActivationPoint;
    }

    /*
    *  Returns the next activation point.
    * */
    public int getNextActivationPoint() {
        return nextActivationPoint;
    }

    /*
    * Sets the next activation point
    * */
    public void setNextActivationPoint(int nextActivationPoint) {
        this.nextActivationPoint = nextActivationPoint;
    }

    /*
    * Sets the repetition value.
    * */
    public void setRepetitionValue(int repetitionValue) {
        this.repetitionValue = repetitionValue;
    }
}
