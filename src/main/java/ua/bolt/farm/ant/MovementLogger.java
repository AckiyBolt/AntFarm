package ua.bolt.farm.ant;

import ua.bolt.farm.field.entity.Coordinates;

import java.util.*;

public class MovementLogger {

    private String ownerName;
    private LinkedHashSet<Coordinates> currentLog;
    private LinkedHashSet<LinkedHashSet<Coordinates>> logs;

    public MovementLogger(String ownerName) {
        this.logs = new LinkedHashSet<LinkedHashSet<Coordinates>>();
        this.ownerName = ownerName;
        makeNewSession();
    }

    public boolean containsInCurrentSession(Coordinates coordinates) {
        return currentLog.contains(coordinates);
    }

    public void log(Coordinates coordinates) {
        this.currentLog.add(coordinates);
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

    public Iterator<Coordinates> iterator() {

        LinkedList<Coordinates> result = new LinkedList<>();
        Iterator<LinkedHashSet<Coordinates>> logsIt = logs.iterator();

        while (logsIt.hasNext()) {
            Iterator<Coordinates> it = logsIt.next().iterator();
            while(it.hasNext())
                result.add(it.next());
        }

        return result.iterator();
    }

    public String getOwnerName() {
        return ownerName;
    }
}
