package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import model.Part;
import model.Product;
import model.Inventory;


/**This declares the class for the controller which manages all the functions of the modify product screen.
 *
 */
public class ModifyProductController implements Initializable {

    /**establishes gui elements for modify part screen
     *
     */
    @FXML private TableView<Part> allPartTable;
    @FXML private TableColumn<Part, Integer> allPartIDColumn;
    @FXML private TableColumn<Part, Integer> allPartInventoryColumn;
    @FXML private TableColumn<Part,String> allPartNameColumn;
    @FXML private TableColumn<Part,Double> allPartPriceColumn;
    @FXML private TableView<Part> associatedPartTable;
    @FXML private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML private TableColumn<Part,String> associatedPartNameColumn;
    @FXML private TableColumn<Part,Double> associatedPartPriceColumn;
    @FXML private TextField ProductIDTextField;
    @FXML private TextField ProductInventoryTextField;
    @FXML private TextField ProductMaxTextField;
    @FXML private TextField ProductMinTextField;
    @FXML private TextField ProductNameTextField;
    @FXML private TextField ProductPriceTextField;
    @FXML private TextField searchAllPartTextField;

    /**creates associated part list
     *
     */
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**sets an inventory for pushing data about associated parts
     *
     */
    Inventory inventory = new Inventory();
    /**declare selected product variable; used for passing information in from main menu
     *
     */
    private Product productSelected;

    /**initializes the program but getting data about the product being modified, disabling interactivity,
     * setting up both tables, and prepopulating the current product details into the text fields
     * @param location
     * @param resources
     */
    @Override
    public void initialize (URL location, ResourceBundle resources) {
        //get data about selected product
        productSelected = MainMenuController.getProductToModify();
        //disables part ID field
        disableInteractivity();
        //set up all parts table
        setAllPartTableView();
        //set up associated parts table, pulls in data from select product
        setAssociatedPartsTable(productSelected,inventory);
        //sets text field entries based on selected product data
        setTextFields(productSelected);
    }

    /**adds list from all-part list to associated part list
     *
     * @param event
     */
    @FXML
    void addAssociatePart(MouseEvent event) {
        //gets item from all part table
        Part partSelected = allPartTable.getSelectionModel().getSelectedItem();
        //if no part selected, returns error
        if (partSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Part Failure");
            alert.setHeaderText("Part Not Added");
            alert.setContentText("Please select the part to be added and try again.");
            alert.showAndWait();
        } else {
            //adds part to associated parts
            associatedParts.add(partSelected);
            associatedPartTable.setItems(associatedParts);
        }
    }

    /**searches when user types in search box -- each keystroke initiates search. if backspaced until textfield is blank, table will show all parts again
     *
     * @param kEvent
     */
    @FXML
    void allPartSearch(KeyEvent kEvent) {
        ObservableList<Part> AllParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        //gets user entered text from text field
        String partSearchString = searchAllPartTextField.getText();
        //makes part names and user entry all lower cases, trims extraneous spaces, compares two values to find matches
        for (Part part : AllParts) {
            if (String.valueOf(part.getId()).contains(partSearchString.trim()) ||
                    part.getName().toLowerCase().contains(partSearchString.toLowerCase().trim())) {
                foundPart.add(part);
            }
        }
        //sets table to show searched parts
        allPartTable.setItems(foundPart);
        //if user hits enter and there are no parts found, returns an error and returns text field to blank
        if (kEvent.getCode() == KeyCode.ENTER) {
            if (foundPart.size() == 0) {
                searchAllPartTextField.setText("");
                deselect(searchAllPartTextField);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("FAILURE");
                alert.setHeaderText("Product Not Found in Search");
                alert.setContentText("Check your entry and try again.");
                alert.showAndWait();
            }
        }
    }

    /**verifies user wants to cancel modify product after clicking cancel
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelModifyProductChanges(MouseEvent event) throws IOException {
        //confirmation alert advising changes will be lost if the user continues
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRM");
        alert.setHeaderText("Please confirm cancellation.");
        alert.setContentText("Are you sure you want to cancel? All changes will be lost");
        Optional<ButtonType> result = alert.showAndWait();
        //if user confirms, returns them to home page
        if (result.isPresent() && result.get() == ButtonType.OK) {
            URL mainMenuURL = Paths.get("src/main/java/view/MainMenu.fxml").toUri().toURL();
            Parent mainMenuParent = FXMLLoader.load(mainMenuURL);
            Scene mainMenuScene = new Scene(mainMenuParent);
            Stage mainMenuStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.show();
        }
    }

    /**removes items from associated part list when remove associated part button clicked
     *
     * @param event
     */
    @FXML
    void removeAssociatedPart(MouseEvent event) {
        //gets user selected part from associate parts table
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();

        //alerts if user did not make a selection
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FAILURE");
            alert.setHeaderText("Product Deletion Failure");
            alert.setContentText("Please select a product to be deleted.");
            alert.showAndWait();
        } else {
            //confirms if user wants to delete product
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRM");
            alert.setHeaderText("Please confirm part removal.");
            alert.setContentText("Are you sure you want to remove associated part " + selectedPart.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedPartTable.getItems().remove(selectedPart);
            }
        }
    }

    /**saves changes to modified product
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void saveModifyProductChanges(MouseEvent event) throws IOException {
        //calls method to verify user entered data
        if (productVerified()) {
            //gets user entered data from text fields and verifies data type entered
            int id = Integer.parseInt(ProductIDTextField.getText());
            String name = ProductNameTextField.getText();
            Double price = Double.parseDouble(ProductPriceTextField.getText());
            int stock = Integer.parseInt(ProductInventoryTextField.getText());
            int max = Integer.parseInt(ProductMaxTextField.getText());
            int min = Integer.parseInt(ProductMinTextField.getText());
            Product updatedProduct = new Product(id, name, price, stock, min, max);
            //gets associated parts
            for (Part part : associatedParts) {
                updatedProduct.addAssociatedPart(part);
            }
            //adds updated product to main menu product table
            Inventory.addProduct(updatedProduct);
            //deletes old version of product
            Inventory.deleteProduct(productSelected);
            //displays success message and returns user to main menu
            modifySuccess(name, event);
        } else {
            //specific errors are generated by productVerified; this is a general else statement alternative if productVerified returns false
            System.out.println("Product Modify Failure! See alert pop-up for details.");
        }
    }

    /**sets text fields with data from part select on main menu
     *
     * @param selectedProduct
     */
    public void setTextFields(Product selectedProduct) {
        ProductNameTextField.setText(selectedProduct.getName());
        ProductIDTextField.setText(Integer.toString(selectedProduct.getId()));
        ProductPriceTextField.setText(Double.toString(selectedProduct.getPrice()));
        ProductInventoryTextField.setText(Integer.toString(selectedProduct.getStock()));
        ProductMaxTextField.setText(Integer.toString(selectedProduct.getMax()));
        ProductMinTextField.setText(Integer.toString(selectedProduct.getMin()));
    }

    /**sets up all part table view
     *
     */
    public void setAllPartTableView() {
        allPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        allPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //shows two decimal places in price column
        allPartPriceColumn.setCellFactory(tc -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", price.floatValue()));
                }
            }
        });
        //updates all part table with values
        allPartTable.setItems(Inventory.getAllParts());
        allPartTable.getSortOrder().setAll(allPartIDColumn);
    }

    /**sets up associated part table
     *
     * @param selectedProduct
     * @param inventory
     */
    public void setAssociatedPartsTable(Product selectedProduct, Inventory inventory) {
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //show two decimal places in price column
        associatedPartPriceColumn.setCellFactory(tc -> new TableCell<Part, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", price.floatValue()));
                }
            }
        });
        //updates values in associated parts table
        this.associatedParts = selectedProduct.getAllAssociatedParts();
        associatedPartTable.setItems(associatedParts);
    }

    /**disables interactivity for product ID text field
     *
     */
    public void disableInteractivity() {
        ProductIDTextField.setEditable(false);
        ProductIDTextField.setMouseTransparent(true);
    }

    /**deselects text field if user search fails so they can clear the error by hitting enter without repeatedly receiving the same error
     *
     * @param textField
     */
    private void deselect(TextField textField) {
        Platform.runLater(() -> {
            if (textField.getText().length() > 0 &&
                    textField.selectionProperty().get().getEnd() == 0) {
                deselect(textField);
            }else{
                textField.selectEnd();
                textField.deselect();
            }
        });
    }

    /**validates user data entered in Add Product form
     *
     * @return true if product data validated, false if not
     */
    public boolean productVerified() {
        //initializes a variable to use as a flag for boolean return value
        boolean productVerifyFlag = false;
        try {
            //confirms that submissions are present and of the correct data type
            String name = ProductNameTextField.getText();
            Double price = Double.parseDouble(ProductPriceTextField.getText());
            int stock = Integer.parseInt(ProductInventoryTextField.getText());
            int max = Integer.parseInt(ProductMaxTextField.getText());
            int min = Integer.parseInt(ProductMinTextField.getText());
            //verifies product name is not empty; returns error if not
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("FAILURE");
                alert.setHeaderText("Product Not Modified");
                alert.setContentText("Please enter a product name to proceed.");
                alert.showAndWait();
                productVerifyFlag = false;
            } else {
                //verifies min is not less than zero and not more than max; returns error if not
                if (min < 0 || min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("FAILURE");
                    alert.setHeaderText("Product Not Modified");
                    alert.setContentText("Min value must be less than Max value but no less than zero.");
                    alert.showAndWait();
                    productVerifyFlag = false;
                } else {
                    //verifies stock level is between min and max values; returns error if not
                    if (stock < min || stock > max) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("FAILURE");
                        alert.setHeaderText("Product Not Modified");
                        alert.setContentText("Inventory stock cannot be greater than Max value or less than Min value.");
                        alert.showAndWait();
                        productVerifyFlag = false;
                    } else {
                        //verifies price is greater than $0; returns error if not
                        if (price < .01) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("FAILURE");
                            alert.setHeaderText("Product Not Modified");
                            alert.setContentText("Please enter a price of .01 or greater.");
                            alert.showAndWait();
                            productVerifyFlag = false;

                        } else {
                            //sets flag to true if all validation checks passed
                            productVerifyFlag = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            //returns general error for other missing/invalid entries
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FAILURE");
            alert.setHeaderText("Product Not Modified");
            alert.setContentText("One or more entries on the form is missing or has an invalid format.");
            alert.showAndWait();
            productVerifyFlag = false;
        }
        return productVerifyFlag;}

    /**called if modification is successful. Alerts user that modification was successful, then returns them to main menu
     *
     * @param name
     * @param event
     * @throws IOException
     */
    public void modifySuccess(String name, MouseEvent event) throws IOException {
        //alert pop up
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("Product Added");
        alert.setContentText(name + " has been added successfully.");
        alert.showAndWait();
        //return to main menu
        URL mainMenuURL = Paths.get("src/main/java/view/MainMenu.fxml").toUri().toURL();
        Parent mainMenuParent = FXMLLoader.load(mainMenuURL);
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage mainMenuStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }
}
