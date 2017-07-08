package net.nafana.tarrinheartbeat;

import net.nafana.tarrinheartbeat.interfaces.ITarrinHeartbeatEvent;
import net.nafana.tarrinheartbeat.tasks.Task;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/* TestClass to demonstrate use.
*
* On Callback the particular task that calledback to us returns itself,
* this allows us to track the id. Realtime use would probably be to include an BukkitEventHandler and call
* the event you want based on if the correct task id comes up. This pipeline effectively shows us what's
* coming through the callbacks, we get access to each of them, and then determine what to do with the information.
*
* */
public class TestClass implements ITarrinHeartbeatEvent{

    @Override
    public void callEvent(Task task) {

        Bukkit.broadcastMessage(ChatColor.RED + "[Tarrin Heartbeat] The thread with the id of: " + ChatColor.GRAY + task.getTaskID() +
                " has caused a callback event.");
        Bukkit.broadcastMessage(ChatColor.YELLOW + "The next tick this event will be called on is... " + task.getNextActivationPoint());

    }

}
