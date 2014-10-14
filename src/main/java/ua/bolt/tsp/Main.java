package ua.bolt.tsp;

import ua.bolt.tsp.model.field.Cell;
import ua.bolt.tsp.model.field.Coordinates;
import ua.bolt.tsp.model.field.Field;
import ua.bolt.tsp.model.field.Move;
import ua.bolt.tsp.util.FieldBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static final int FIELD_SIZE = 51;

    private static Coordinates start  = new Coordinates(FIELD_SIZE/2-1, 3);
    //private static Coordinates target = new Coordinates(FIELD_SIZE/2-1, FIELD_SIZE/2-1);
    private static Coordinates target = new Coordinates(FIELD_SIZE/2-1, 49);

    private static Random RND = new Random(System.nanoTime());



    public static void main (String ... args) throws Exception {

        FieldBuilder builder = new FieldBuilder(FIELD_SIZE);
        builder
                .setStartCoordinates(start)
                .setTargetCoordinates(target);

        Field field = builder.build();

        Coordinates currentPosition = start;
        List<Cell> nearestToCurrentPosition = null;

        int turnCounter = 0;

        try {
            while (!currentPosition.equals(target)) {

                field.logMove(currentPosition, Move.STAY);
//                System.out.println("Turn: " + ++turnCounter);
//                System.out.println("Current pos is : " + currentPosition);

                nearestToCurrentPosition = field.getNearest(currentPosition);
                Coordinates next = decideWhereToMove(nearestToCurrentPosition);

                currentPosition = next;
                Thread.sleep(50);
            }
        } catch (NullPointerException ex){
            System.out.println("Wasted...");
        }

//        field.printSmell();
//        System.out.println();
        field.printPath();
    }

    public static Coordinates decideWhereToMove (List<Cell> nearestToCurrentPosition) {

        Coordinates result = null;

        List<Cell> variants = rateNextMoves(nearestToCurrentPosition);

        result = variants.get(genRnd(variants.size())).coordinates;

        return result;
    }

    public static List<Cell> rateNextMoves (List<Cell> nearestToCurrentPosition) {

        List<Cell> result = new ArrayList<Cell>();
        Integer lowestSmell = Integer.MAX_VALUE;

        for (Cell cell : nearestToCurrentPosition) {

            if (cell.smell < lowestSmell) {
                result.clear();
                lowestSmell = cell.smell;
            }

            if (cell.smell.equals(lowestSmell))
                result.add(cell);
        }

//        System.out.println("Candidates: " + result);

        return result;
    }

    public static int genRnd (int max) {
        return RND.nextInt(max);
    }
}
