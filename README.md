# TarrinHeartBeat
---
The timings system for the "Fall Of Tarrin" gamemode, mainly uploaded to serve as a guide for future developers involved in the project.

_It is recommended that this project is used as a library rather than a plugin. However for the sake of demonstrating the uses it is build as is. The developers of Tarrin should understand that the timings system will be used as a timings manager to dictate when certain callbacks need to happen. This makes all timers linked into a single manager, instead of having each class have it's own scheduled task running._

_NOTE: Nothing here is optimized yet, this aims to be a simple guide for developers that are apart of the "Fall Of Tarrin" project._

##### Guide For Developers
---
 To start using the TarrinHeartbeat timings system, you'll need to follow the following steps.
 1. **Create** a class that implements `ITarrinHeartbeatEvent`
 2. **Implement** the needed method "`callEvent`" for the interface, then override it.
    _This is the method that dictates how you deal with the callbacks. The task parameter is the task that callsback, you       can check the id of the task to determine which one is calling back in particular._
 3. **Create** a `TaskManager` instance in whichever class you plan to keep all your task managers in.
    _The TaskManager will take two parameters. It will take an object of the initial class you created. It will also take a      beat period, which you should set depending on the type of TaskManager you want. If you need more percision,  you might      want to go lower **(5-10)**. If you rather have a timer that doesn't check as often, you want to go with a **20** tick      and above beat period._
 4. **Run** the taskmanager with a delay of 0, and use the same beat period as the one you used to instantiate the TaskManager.

##### Detailed Instructions
---

Following the instructions above, I've simply created a class that implements the `ITarrinHeartbeatEvent`. When any task returns a callback, this method will broadcast to the server that a callback has been made.

```java
public class TestClass implements ITarrinHeartbeatEvent{

    @Override
    public void callEvent(Task task) {
        Bukkit.broadcastMessage(ChatColor.RED + "[Tarrin Heartbeat] We received a task callback!");
    }
}
```

I created a new instance of the `TaskManager`, and passed in the TestClass object as a parameter along side the beat period which I made out to be 5 seconds. This means that every 5/20, or 1/4 game ticks, the TaskManager deals with its payload.

```java
    private TaskManager primaryManager = new TaskManager(new TestClass(), 5);
    
    public void onEnable () {
        primaryManager.runTaskTimer(this, 0, primaryManager.getBeatPeriod());
    }
```

To finish off, I added a simple task that would broadcast a message once it got run. Notice that the last two parameters are set to -1. Since the task is singular, a period nor a repeat time is needed for this. This will get ignored by the TaskManager anyways, so might as well not worry about it.

```java
    private TaskManager primaryManager = new TaskManager(new TestClass(), 5);
    
    public void onEnable () {
        primaryManager.runTaskTimer(this, 0, primaryManager.getBeatPeriod());
        primaryManager.addTask(new Task(CallbackType.SINGULAR, -1, -1));
    }
```

This is the final result of our callback.
>![alt text](http://i.imgur.com/IO6fY5Y.png "Logo Title Text 1")


##### Information about Usage
---

The timings system considers three possible enums as the callback types.
* **`SINGULAR`** - _Used when a task needs to be run once._
* **`COUNTED`**  - _Used when a task needs to be run a given amount of times._
* **`RECURRING`**- _Used when the task needs to run throughout the duration of server uptime._

It is advised to run 2-3 `TaskManager` instances, each TaskManager should take their own role as a most important, normal, and least important task manager. For instance if you need to check the player's hunger every so often _(just pretend there is no event that tracks updating for it)_, that can be run on the least important task manager to make sure you don't have something updating every game tick.

