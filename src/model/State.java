package model;

import javafx.scene.paint.Color;
import java.util.Objects;

public class State {

    private String name;
    private int type;
    private Color color;
    private String image;
    private double prob;
    private int amount;

    /**
     * this class represents the states of cells with various characteristics
     * @param name the name of the state
     * @param type the type of the state (an integer representing each type of state)
     * @param color the color of the state
     * @param image the image, if applicable, to use for the state
     * @param prob the probability of a state being selected if cells are set based on probability
     * @param amount amount of a certain state if cells are set based on the number of each state
     */

    public State (String name, int type, Color color, String image, Double prob, int amount) {
        this.color = color;
        this.name = name;
        this.type = type;
        this.image = image;
        this.prob = prob;
        this.amount = amount;
    }

    /**
     * getter method to get the name of the state
     * @return the name of the state
     */

    public String getStateName() {
        return name;
    }

    /**
     * get the type of the state
     * @return int representing the type
     */

    public int getType() {
        return type;
    }

    /**
     * get the color of the state
     * @return the color of the state
     */

    public Color getColor() {
        return color;
    }

    /**
     * get the image of the state
     * @return the image
     */

    public String getImage() {
        return image;
    }

    /**
     * get the probability of a state occurring if the cells are set using probabilistic
     * @return
     */

    public double getProb() {
        return this.prob;
    }

    /**
     * get the amount of each cell state if the cells are set using totalsum
     * @return
     */

    public int getAmount() {
        return this.amount;
    }

    /**
     * convert the cell characteristics to a string (for error checking)
     * @return
     */

    @Override
    public String toString() {
        return "State{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", color=" + color +
                '}';
    }

    /**
     * get the hashcode of a state
     * @return
     */

    @Override
    public int hashCode() {
        return Objects.hash(name, type, color);
    }

    /**
     * check if two states are equals
     * @param o another state
     * @return a boolean representing whether they are equal or not
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        State state = (State) o;
        return type == state.type &&
                Objects.equals(name, state.name) &&
                Objects.equals(color, state.color) &&
                Objects.equals(image, state.image);
    }
}
