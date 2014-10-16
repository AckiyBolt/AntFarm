package ua.bolt.farm;

import sun.misc.ThreadGroupUtils;
import ua.bolt.farm.ant.DefaultAnt;
import ua.bolt.farm.ant.MovementLogger;
import ua.bolt.farm.field.Coordinates;
import ua.bolt.farm.field.Field;
import ua.bolt.farm.util.FieldBuilder;
import ua.bolt.farm.view.FieldPrinter;

import java.util.ArrayList;

public class Main {

    private static final int FIELD_SIZE = 51;

    private static Coordinates start  = new Coordinates(FIELD_SIZE/2-1, 3);
    //private static Coordinates target = new Coordinates(FIELD_SIZE/2-1, FIELD_SIZE/2-1);
    private static Coordinates target = new Coordinates(FIELD_SIZE/2-1, 49);



    public static void main (String ... args) throws Exception {

        Field field = buildField();
        ArrayList<DefaultAnt> ants = new ArrayList<DefaultAnt>();
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (int i = 0; i < 10; i++) {
            DefaultAnt ant = new DefaultAnt("murashka" + i, field);
            ants.add(ant);

            Thread thread = new Thread(ant);
            threads.add(thread);

            thread.start();
        }

        for (Thread thread : threads)
            thread.join();

        MovementLogger[] logers = new MovementLogger[ants.size()];
        for (int i = 0; i < ants.size(); i++) {
            logers[i] = ants.get(i).getMovementLogger();
        }

        new FieldPrinter().printMovement(field, logers);
    }

    private static Field buildField() {
        FieldBuilder builder = new FieldBuilder(FIELD_SIZE);
        builder
                .setStartCoordinates(start)
                .setTargetCoordinates(target);

        return builder.build();
    }
}
