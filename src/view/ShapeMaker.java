package view;

import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import model.Grid;

import java.awt.Point;
import java.util.ResourceBundle;

/**
 * @author Samuel Chan, William Francis, Sahil Gupta
 * @purpose In the grid, actually constructs and visually displays the shapes
 * @assumptions Called in SimulationViewer to create and display the grid (setup once)
 * @dependencies SimulationViewer to actually create an instance of the class
 */
public class ShapeMaker {
    private int squareNum;
    private int hexagonNum;
    private int triangleNum;
    private static final int SQUARE_SIDES = 4;
    private static final int HEXAGON_SIDES = 6;
    private static final int TRIANGLE_SIDES = 3;
    private ResourceBundle myResources;
    private int gridWidth;
    private Grid myGrid;
    private int myCellDiameter;
    private int myCellShape;
    private int[] rowConstantArr;
    private int[] multConstantArr;


    // Constructor that based on the resources file given, loads properties, and constructs shapes
    public ShapeMaker(ResourceBundle resources, Grid grid) {
        myResources = resources;
        myGrid = grid;
        loadProperties();
        myCellDiameter = gridWidth / grid.getNumCols();
        myCellShape = grid.getShape();
    }

    // Used to load props from config file
    private void loadProperties() {
        gridWidth = Integer.parseInt(myResources.getString("GRIDWIDTH"));
        squareNum = Integer.parseInt(myResources.getString("SQUARENUM"));
        hexagonNum = Integer.parseInt(myResources.getString("HEXAGONNUM"));
        triangleNum = Integer.parseInt(myResources.getString("TRIANGLENUM"));

        var rowConstant0 = Integer.parseInt(myResources.getString("ROWCONSTANT0"));
        var rowConstant1 = Integer.parseInt(myResources.getString("ROWCONSTANT1"));
        var rowConstant2 = Integer.parseInt(myResources.getString("ROWCONSTANT2"));
        var rowConstant3 = Integer.parseInt(myResources.getString("ROWCONSTANT3"));
        var rowConstant4 = Integer.parseInt(myResources.getString("ROWCONSTANT4"));
        var rowConstant5 = Integer.parseInt(myResources.getString("ROWCONSTANT5"));
        rowConstantArr = new int[] {rowConstant0, rowConstant1, rowConstant2, rowConstant3, rowConstant4, rowConstant5};

        var multConstant0 = Integer.parseInt(myResources.getString("MULTCONSTANT0"));
        var multConstant1 = Integer.parseInt(myResources.getString("MULTCONSTANT1"));
        var multConstant2 = Integer.parseInt(myResources.getString("MULTCONSTANT2"));
        var multConstant3 = Integer.parseInt(myResources.getString("MULTCONSTANT3"));
        var multConstant4 = Integer.parseInt(myResources.getString("MULTCONSTANT4"));
        var multConstant5 = Integer.parseInt(myResources.getString("MULTCONSTANT5"));
        var multConstant6 = Integer.parseInt(myResources.getString("MULTCONSTANT6"));
        multConstantArr = new int[] {multConstant0, multConstant1, multConstant2, multConstant3, multConstant4, multConstant5, multConstant6};

    }

    private Point findCenter(int r, int c, int shape) {
        var radius = myCellDiameter/2;
        int x,y;
        x = y = -1;
        if (shape == triangleNum){
            y = r * radius + radius;
            x = c * radius + radius;
            if (isOdd(c)) {
                if (isEven(r)){
                    y-= radius/2;
                } else {
                    y += radius/2;
                }
            }
            if (r > 1) {
                int i = findCenterHelper();
                y+=(r*i);
            }
            if (isOdd(r)&&r>2){
                y-=radius/2;
            }
        }
        else if (shape== hexagonNum){
            y = r * myCellDiameter + radius;
            x = c * myCellDiameter + radius;
            if (isOdd(r)){
                x+=radius;
            }
        }else if (shape== squareNum){
            y = r * myCellDiameter + radius;
            x = c * myCellDiameter + radius;
        }
        return new Point(x, y);
    }

    //this is a duct tape method to deal with triangle's bizarre interactions with this ShapeMaker implementations
    private int findCenterHelper() {
        var numRows = myGrid.getNumRows();
        for (int i = 0; i < rowConstantArr.length; i++){
            if (numRows>rowConstantArr[i]){
                return multConstantArr[i];
            }
        }
        return multConstantArr[6];
    }

    /**
     * Actually creates the shape needed given the cell
     * @param r
     * @param c
     * @return Polygon
     */
    public javafx.scene.shape.Polygon makeShape(int r, int c) {
        Point center = findCenter(r, c, myCellShape);
        int x = center.x;
        int y = center.y;
        var poly = new javafx.scene.shape.Polygon();

        int sides = getSideNum();

        setPolygonSides(poly, x, y, myCellDiameter/2, sides);
        if (sides==TRIANGLE_SIDES) {
            if (isOdd(c) && (isEven(r)) || ((isEven(c)) &&(isOdd(r)))){
                poly.getTransforms().add(new Rotate(180, x, y));
            }
            poly.getTransforms().add(new Rotate(180, x, y));
        }
        else if (sides==SQUARE_SIDES) {
            poly.getTransforms().add(new Rotate(45, x, y));
        }
        return poly;
    }

    private int getSideNum() {
        if (myCellShape == triangleNum){
            return TRIANGLE_SIDES;
        }else if (myCellShape == hexagonNum) {
            return HEXAGON_SIDES;
        }else if (myCellShape == squareNum){
            return SQUARE_SIDES;
        }
        return -1;
    }

    //taken from: https://stackoverflow.com/questions/49550428/on-javafx-is-there-any-way-to-draw-a-regular-polygon-without-knowing-the-coordi
    private void setPolygonSides(Polygon polygon, double centerX, double centerY, double radius, int sides) {
        polygon.getPoints().clear();
        final double angleStep = Math.PI * 2 / sides;
        double angle = 0; // assumes one point is located directly beneath the center point
        for (int i = 0; i < sides; i++, angle += angleStep) {
            polygon.getPoints().addAll(
                    Math.sin(angle) * radius + centerX, // x coordinate of the corner
                    Math.cos(angle) * radius + centerY // y coordinate of the corner
            );
        }
    }

    private boolean isEven(int x){
        return (x%2==0);
    }

    private boolean isOdd(int x){
        return (x%2==1);
    }
}
