package ua.bolt.farm.ant;

import ua.bolt.farm.field.Coordinates;

import java.util.LinkedHashSet;

public class MovementLogger {

    private LinkedHashSet<Coordinates> currentLog;
    private LinkedHashSet<LinkedHashSet<Coordinates>> logs;

    public MovementLogger() {
        currentLog = new LinkedHashSet<Coordinates>();
        logs = new LinkedHashSet<LinkedHashSet<Coordinates>>();
    }

    public boolean contains (Coordinates coordinates) {
        return currentLog.contains(coordinates);
    }

    public void log(Coordinates coordinates) {
        this.currentLog.add(coordinates);
    }

    public boolean isEmpty() {
        return currentLog.size() == 0;
    }

    public void incrementVersion() {
        logs.add(currentLog);
        currentLog = new LinkedHashSet<Coordinates>();
    }
}
