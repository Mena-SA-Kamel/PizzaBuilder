// Pizza Builder App
// Mena S.A. Kamel, MESc Candidate, ECE, Western University, Canada
// mkamel9@uwo.ca


package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.awt.*;

// Main flow:
// 1. Create Stage (Window)
// 2. Rename the window
// 3. Create a GridPane Object to facilitate the creating of a layout
// 4. Define the components on the window
// 5. Add the components to the layout
// 6. Create a scene with the layout we created
// 7. link the scene to the stage
// 8. show the stage to display the window

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Rename the stage
        primaryStage.setTitle("Pizza Maker");

        // Creating a GridPane object (layout)
        GridPane grid = new GridPane();

        // Setting the padding around the window - 10 pixels all around
        grid.setPadding(new Insets(10,10,10,10));

        // Creating Labels
        Label nameLabel = new Label("Customer Name:");
        Label couponCode = new Label("Coupon Code:");

        // Creating Text Fields
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        TextField couponField = new TextField();
        couponField.setPromptText("Enter a valid coupon code");

        // Creating Buttons
        Button startButton = new Button();
        startButton.setText("Create Your Favourite Pizza!");

        // Defining actions upon click
        startButton.setOnAction(e -> {
            Label customerName = new Label(nameField.getText());
            if (validateCoupon(couponField)){
                createPizza(customerName.getText());
            }
        });

        // Add all components to the layout
        grid.getChildren().addAll(nameLabel, nameField, couponCode, couponField ,startButton);

        // Set constraints - Defining location of the components relative to each other
        grid.setConstraints(nameLabel, 0,0);
        grid.setConstraints(couponCode, 0,1);
        grid.setConstraints(nameField, 1,0);
        grid.setConstraints(couponField, 1,1);
        grid.setConstraints(startButton, 5,5);

        // Setting the vertical and horizontal gaps between the components
        grid.setVgap(10);
        grid.setHgap(10);

        // Setting the scene
        Scene scene = new Scene(grid);

        // Linking scene to the stage
        primaryStage.setScene(scene);

        // Showing the window
        primaryStage.show();
    }

    // Validates the coupon code field
    private boolean validateCoupon(TextField couponCode){
        if (!couponCode.getText().isEmpty()) {
            try {
                int couponNumber = Integer.parseInt(couponCode.getText());
                return true;
            } catch (NumberFormatException e) {
                //display error
                display("Invalid Coupon Code", "Please enter a valid coupon code");
                return false;
            }
        }else{
            return true;
        }
    }

    private void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label displayMessage = new Label();
        displayMessage.setText(message);

        Button closeButton = new Button();
        closeButton.setText("Close");
        closeButton.setOnAction(e -> window.close());

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(10);

         //set constraints
        grid.setConstraints(displayMessage, 0,0);
        grid.setConstraints(closeButton, 0,1);

        grid.getChildren().addAll(displayMessage, closeButton);
        grid.setAlignment(Pos.CENTER);
        grid.setMinWidth(400);
        Scene alertScene = new Scene(grid);
        window.setScene(alertScene);
        window.show();

    }

    private void createPizza(String customerName){
        Stage createPizzaWindow = new Stage();
        createPizzaWindow.setTitle("Create Your Own Pizza");
        createPizzaWindow.setMinWidth(1000);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(20);
        grid.setHgap(20);

        Label welcomeMsg = new Label("Hello " + customerName + " , Prices start at $10 plus $1 per topping");

        // Pizza Size Drop Down Menu
        Label sizeLabel = new Label("Choose a Size:");
        ChoiceBox<String> pizzaSize = new ChoiceBox<>();
        pizzaSize.getItems().addAll("Individual", "Small", "Medium", "Large");
        //Set a default value
        pizzaSize.setValue("Medium");

        // Dough
        Label doughLabel = new Label("Choose your Dough Type:");
        ToggleGroup group = new ToggleGroup();
        RadioButton normal = new RadioButton("Normal");
        RadioButton gluten = new RadioButton("Gluten Free");
        RadioButton wheat = new RadioButton("Whole Wheat");
        normal.setToggleGroup(group);
        gluten.setToggleGroup(group);
        wheat.setToggleGroup(group);

        // Toppings
        Label toppingsLabel = new Label("Choose your Toppings:");
        CheckBox tomato = new CheckBox("Tomatoes");
        CheckBox onion = new CheckBox("Onions");
        CheckBox olive = new CheckBox("Olives");

        // Place Order Button
        Button placeOrderButton = new Button();
        placeOrderButton.setText("Place Order");
        placeOrderButton.setOnAction(e -> createPizzaWindow.close());

        // Total Price Button
        Button getTotal = new Button();
        getTotal.setText("Get Order Total");
        CheckBox[] toppings = {tomato, onion, olive};
        getTotal.setOnAction(e -> {
            int totalPrice = getOrderTotal(toppings);
            display("Order Total", "The order total is: $" + totalPrice);

        });

        // Setting the window constraints
        grid.setConstraints(welcomeMsg, 0,0);
        grid.setConstraints(sizeLabel, 0,2);
        grid.setConstraints(pizzaSize, 1,2);
        grid.setConstraints(doughLabel, 0,3);
        grid.setConstraints(normal, 1,4);
        grid.setConstraints(gluten, 2,4);
        grid.setConstraints(wheat, 3,4);

        grid.setConstraints(toppingsLabel, 0,5);
        grid.setConstraints(tomato, 1,6);
        grid.setConstraints(onion, 2,6);
        grid.setConstraints(olive, 3,6);

        grid.setConstraints(placeOrderButton, 10,10);
        grid.setConstraints(getTotal, 1,10);


        grid.getChildren().addAll(welcomeMsg, sizeLabel, pizzaSize, doughLabel, normal, gluten, wheat, toppingsLabel,
                tomato, olive, onion, placeOrderButton, getTotal);

        Scene pizzaCreatorScene = new Scene(grid);
        createPizzaWindow.setScene(pizzaCreatorScene);
        createPizzaWindow.show();
    }

    private int getOrderTotal(CheckBox[] toppings){
        int numToppings = 0;
        for (CheckBox topping : toppings){
            if (topping.isSelected()){
                numToppings += 1;
            }
        }
        return 10 + numToppings;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
