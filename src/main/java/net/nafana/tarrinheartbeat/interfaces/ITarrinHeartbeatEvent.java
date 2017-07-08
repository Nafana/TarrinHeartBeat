package net.nafana.tarrinheartbeat.interfaces;

import net.nafana.tarrinheartbeat.tasks.Task;

/* The main interface for the heart beat event system.
*
*  This makes users include the callEvent method, which allows us to make
*  any callback response that we want! As long as the method in another class that implements
*  ITarrinHeartbeatEvent overrides it.
*
* */
public interface ITarrinHeartbeatEvent {

    // Method called when event is called, we give it the parameter of the task
    // so that we can determine what task called ended up making the callback!
    void callEvent(Task task);

}
