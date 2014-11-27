package ua.bolt.farm.ant;

import ua.bolt.farm.field.Coordinates;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class MovementLogger {

    private LinkedHashSet<Coordinates> currentLog;
    private LinkedHashSet<LinkedHashSet<Coordinates>> logs;

    public MovementLogger() {
        logs = new LinkedHashSet<LinkedHashSet<Coordinates>>();
        makeNewSession();
    }

    public boolean containsInCurrentSession(Coordinates coordinates) {
        return currentLog.contains(coordinates);
    }

    public void log(Coordinates coordinates) {
        this.currentLog.add(coordinates);
    }

    public boolean isEmpty() {
        return currentLog.size() == 0;
    }

    public void makeNewSession() {
        currentLog = new LinkedHashSet<Coordinates>();
        logs.add(currentLog);
    }

    public int size() {
        return currentLog.size();
    }

    public Set<Coordinates> getLastSession() {
        return Collections.unmodifiableSet(logs.iterator().next());
    }
}
