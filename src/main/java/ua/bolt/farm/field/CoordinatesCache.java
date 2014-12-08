package ua.bolt.farm.field;

import ua.bolt.farm.field.entity.Coordinates;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ackiybolt on 08.12.14.
 */
public enum CoordinatesCache {

    INSTANCE;

    private static final int INITIAL_CAPACITY = 1000;
    // <x,<y, coordinate>
    private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Coordinates>> cache;

    CoordinatesCache() {
        this.cache = new ConcurrentHashMap<>(INITIAL_CAPACITY);
    }

    public Coordinates createOrGet(Integer x, Integer y) {

        ConcurrentHashMap<Integer, Coordinates> row = cache.get(x);

        Coordinates result = row == null ? createAndAddCoordinate(x, y) : row.get(y);

        return result == null ? createAndAddCoordinate(x, y) : result;
    }

    private synchronized Coordinates createAndAddCoordinate(Integer x, Integer y) {

        Coordinates result = new Coordinates(x, y);

        ConcurrentHashMap<Integer, Coordinates> row = cache.get(x);

        if (row == null) {
            row = new ConcurrentHashMap<Integer, Coordinates>(INITIAL_CAPACITY);
            cache.put(x, row);
        }

        row.put(y, result);

        return result;
    }

}
