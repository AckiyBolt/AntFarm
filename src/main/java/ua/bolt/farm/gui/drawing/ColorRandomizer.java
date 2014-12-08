package ua.bolt.farm.gui.drawing;

import javafx.scene.paint.Color;

import java.util.*;

/**
 * Created by ackiybolt on 08.12.14.
 */
public class ColorRandomizer {

    private static final double COLOR_LIMIT = 0.3;
    private static final double GRADATION_STEP = 0.2;

    private Queue<Color> colors;

    public ColorRandomizer () {
        LinkedList<Color> colors = new LinkedList<>();

        generateOneChColors(colors);
        generateTwoChColors(colors);

        Collections.shuffle(colors, new Random(System.nanoTime()));

        this.colors = colors;
    }

    public Color getRandomColor() {

        Color chosenColor = colors.poll();
        colors.add(chosenColor);

        return chosenColor;
    }

    private void generateOneChColors(List<Color> colors) {

        for (Chanel ch: Chanel.values()) {
            switch (ch) {
                case RED:
                    for (double i = 1.0; i >= COLOR_LIMIT; i = i - GRADATION_STEP)
                        colors.add(Color.color(i, 0.0, 0.0));
                    break;
                case GREEN:
                    for (double i = 1.0; i >= COLOR_LIMIT; i = i - GRADATION_STEP)
                        colors.add(Color.color(0.0, i, 0.0));
                    break;
                case BLUE:
                    for (double i = 1.0; i >= COLOR_LIMIT; i = i - GRADATION_STEP)
                        colors.add(Color.color(0.0, 0.0, i));
                    break;
            }
        }
    }

    private void generateTwoChColors(List<Color> colors) {
        for (Chanel ch: Chanel.values()) {
            switch (ch) {
                case RED:
                    for (double i = 1.0; i >= COLOR_LIMIT; i = i - GRADATION_STEP)
                        colors.add(Color.color(i, i, 0.0));
                    break;
                case GREEN:
                    for (double i = 1.0; i >= COLOR_LIMIT; i = i - GRADATION_STEP)
                        colors.add(Color.color(0.0, i, i));
                    break;
                case BLUE:
                    for (double i = 1.0; i >= COLOR_LIMIT; i = i - GRADATION_STEP)
                        colors.add(Color.color(i, 0.0, i));
                    break;
            }
        }
    }

    private enum Chanel {
        RED,
        GREEN,
        BLUE;
    }
}
