package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

// Represents a log of task list events. We use the Singleton Design Pattern to ensure that there is only
// one EventLog in the task list and that the task List has global access
// to the single instance of the EventLog.
public class EventLog implements Iterable<Event> {
    //The only EventLog in the Task List (Singleton Design Pattern)
    private static EventLog theLog;
    private Collection<Event> events;


    //EFFECTS: Creates a new Event ArrayList, prevents external construction
    private EventLog() {
        events = new ArrayList<Event>();
    }

    //EFFECTS: Gets instance of EventLog and creates it, if it doesn't exist already
    // (Singleton Design Pattern)
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }
        return theLog;
    }

    //MODIFIES: this
    //EFFECTS: Adds an event to the event log
    public void logEvent(Event e) {
        events.add(e);

    }

    //MODIFIES: this
    //EFFECTS: Clears event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event Log Cleared"));
    }

    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}