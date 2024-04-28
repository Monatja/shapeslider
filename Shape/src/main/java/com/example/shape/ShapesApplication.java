package com.example.shape;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class ShapesApplication extends Application {

    private Pane shapePane;
    private Shape[] shapes;
    private int currentShapeIndex = 0;
    private Color currentColor;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        shapePane = new Pane();
        root.setCenter(shapePane);

        createShapes();
        createButtons(root);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shapes viewer");
        primaryStage.show();
    }

    private void createShapes() {
        Polygon triangle = new Polygon(200, 100, 300, 300, 100, 300);
        triangle.setFill(Color.BLACK);
        setDraggable(triangle);

        Rectangle rectangle = new Rectangle(50, 50, 200, 200);
        rectangle.setFill(Color.GRAY);
        setDraggable(rectangle);

        Circle circle = new Circle(200, 200, 100);
        circle.setFill(Color.BROWN);
        setDraggable(circle);

        shapes = new Shape[]{triangle, rectangle, circle};
        shapePane.getChildren().addAll(shapes);
        for (int i = 1; i < shapes.length; i++) {
            shapes[i].setVisible(false);
        }
    }

    private void createButtons(BorderPane root) {
        Button previousBtn = new Button("Previous");
        previousBtn.setStyle("-fx-background-color: #4169E1;");
        previousBtn.setOnAction(e -> switchToPreviousShape());

        Button changeBgBtn = new Button("Change Background");
        changeBgBtn.setStyle("-fx-background-color: #4169E1;");
        changeBgBtn.setOnAction(e -> changeBackgroundColor());

        Button nextBtn = new Button("Next");
        nextBtn.setStyle("-fx-background-color: #4169E1;");
        nextBtn.setOnAction(e -> switchToNextShape());

        Pane buttonPane = new Pane();
        buttonPane.getChildren().addAll(previousBtn, changeBgBtn, nextBtn);
        buttonPane.setPrefHeight(40);
        buttonPane.setLayoutY(360);
        root.getChildren().add(buttonPane);

        double totalBtnWidth = previousBtn.prefWidth(-1) + changeBgBtn.prefWidth(-1) + nextBtn.prefWidth(-1);
        double btnSpacing = (600 - totalBtnWidth) / 4;

        double currentX = btnSpacing;
        for (int i = 0; i < buttonPane.getChildren().size(); i++) {
            Button btn = (Button) buttonPane.getChildren().get(i);
            btn.setLayoutX(currentX);
            currentX += btn.prefWidth(-1) + btnSpacing;
        }
    }


    private void setDraggable(Shape shape) {
        shape.setOnMousePressed(e -> {
            orgSceneX = e.getSceneX();
            orgSceneY = e.getSceneY();
            orgTranslateX = ((Shape) (e.getSource())).getTranslateX();
            orgTranslateY = ((Shape) (e.getSource())).getTranslateY();
        });

        shape.setOnMouseDragged(e -> {
            double offsetX = e.getSceneX() - orgSceneX;
            double offsetY = e.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
            ((Shape) (e.getSource())).setTranslateX(newTranslateX);
            ((Shape) (e.getSource())).setTranslateY(newTranslateY);
        });
    }

    private void switchToPreviousShape() {
        shapes[currentShapeIndex].setVisible(false);
        currentShapeIndex = (currentShapeIndex - 1 + shapes.length) % shapes.length;
        shapes[currentShapeIndex].setVisible(true);
    }

    private void changeBackgroundColor() {
        currentColor = generateRandomColor();
        shapePane.setStyle("-fx-background-color: #" + currentColor.toString().substring(2));
    }
    private void switchToNextShape() {
        shapes[currentShapeIndex].setVisible(false);
        currentShapeIndex = (currentShapeIndex + 1) % shapes.length;
        shapes[currentShapeIndex].setVisible(true);
    }

    private Color generateRandomColor() {
        double red = Math.random();
        double green = Math.random();
        double blue = Math.random();
        return new Color(red, green, blue, 1);
    }
}
