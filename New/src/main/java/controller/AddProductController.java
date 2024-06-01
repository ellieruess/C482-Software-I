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
import model.Product;
import model.Part;
import model.Inventory;
import static model.Inventory.AllProducts;

/**This declares the class for the controller which manages all the functions of the add product screen.
 *
 */
public class AddProductController implements Initializable {

    /**establishes gui elements for the add product screen
     *
     */
    @FXML private TextField ProductIDTextField;
    @FXML private TextField ProductInventoryTextField;
    @FXML private TextField ProductMaxTextField;
    @FXML private TextField ProductMinTextField;
    @FXML private TextField ProductNameTextField;
    @FXML private TextField ProductPriceTextField;
    @FXML private TextField searchAllPartTextField;
    @FXML private TableView<Part> allPartTable;
    @FXML private TableColumn<Part, Integer> allPartIDColumn;
    @FXML private TableColumn<Part, Integer> allPartInventoryColumn;
    @FXML private TableColumn<Part, String> allPartNameColumn;
    @FXML private TableColumn<Part, Double> allPartPriceColumn;
    @FXML private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;
    @FXML private TableView<Part> associatedPartTable;
    @FXML private Button addAssociatedPartButton;
    @FXML private Button removeAssociatedPartButton;

    /**creates observable list of associated parts and an inventory for temporary storage
     *
     */
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    Inventory inventory = new Inventory();

    /**sets up all parts and associated parts tables and blocks user from interacting with ID field
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize (URL location, ResourceBundle resources) {

        //call methods to set up tables
        setAllPartTableView();
        setAssociatedPartTable();

        //call method to prevent user from interacting with ID field
        disableInteractivity();
    }

    /**removes associated part from associated part list if user clicks "remove associated part"
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

    /**save added product when user clicks "save"
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void saveAddProduct(MouseEvent event) throws IOException {
        //confirms field entries are valid before saving
        if (productVerified()) {
            int id = 0;
            String name = ProductNameTextField.getText();
            Double price = Double.parseDouble(ProductPriceTextField.getText());
            int stock = Integer.parseInt(ProductInventoryTextField.getText());
            int max = Integer.parseInt(ProductMaxTextField.getText());
            int min = Integer.parseInt(ProductMinTextField.getText());
            Product newProduct = new Product(id, name, price, stock, min, max);
            for (Part part : associatedParts) {
                newProduct.addAssociatedPart(part);
            }
            newProduct.setId(incrementProductID());
            //saves new part to inventory
            inventory.addProduct(newProduct);
            //alerts user to successful part add and returns them to the main menu
            addSuccess(name, event);
        } else {
            //specific errors are generated by productVerified; this is a general else statement alternative if productVerified returns false
            System.out.println("Add product Failure! See pop-up alert for details.");
        }
    }

    /**adds part from All Part table to Associated Part table when user selects "add"
     *
     * @param event
     */
    @FXML
    void addAssociatedPart(MouseEvent event) {
        //gets part from all parts table
        Part partSelected = allPartTable.getSelectionModel().getSelectedItem();
        //if no part is selected, alerts user to add part failure
        if (partSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Add Failure");
            alert.setHeaderText("No Part Selected");
            alert.setContentText("Please select a part to add to the associated part list.");
            alert.showAndWait();
        } else {
            //adds part to associated part table
            associatedParts.add(partSelected);
            associatedPartTable.setItems(associatedParts);
        }
    }

    /**cancels add product and returns user to main menu when user clicks "cancel"
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelAddProduct(MouseEvent event) throws IOException {
        //Asks user to confirm that they want to cancel adding product
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRM");
        alert.setHeaderText("Please confirm cancellation.");
        alert.setContentText("Are you sure you want to cancel? All changes will be lost");
        //waits for response
        Optional<ButtonType> result = alert.showAndWait();
        //if user confirms, returns them to main menu
        if (result.isPresent() && result.get() == ButtonType.OK) {
            URL mainMenuURL = Paths.get("src/main/java/view/MainMenu.fxml").toUri().toURL();
            Parent mainMenuParent = FXMLLoader.load(mainMenuURL);
            Scene mainMenuScene = new Scene(mainMenuParent);
            Stage mainMenuStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.show();
        }
    }

    /**searches when user types in search box -- each keystroke initiates search; if backspaced until textfield is blank, table will show all parts again
     *
     * @param kEvent
     */
    @FXML
    void allPartSearch(KeyEvent kEvent) {
        ObservableList<Part> AllParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        //gets text from search text field
        String partSearchString = searchAllPartTextField.getText();
        //matches text to part names and ids; converts to lower-case and removes beginning/ending spaces to improve results
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
                alert.setHeaderText("Part Not Found in Search");
                alert.setContentText("Check your entry and try again.");
                alert.showAndWait();
            }
        }
    }

    /**sets up all-parts table and sets values
     *
     */
    public void setAllPartTableView() {
        //sets up table
        allPartIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        allPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //show two decimal places in price column incl trailing zeroes
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
        //sets values
        allPartTable.setItems(Inventory.getAllParts());
        allPartTable.getSortOrder().setAll(allPartIDColumn);
    }

    /**sets up associated part table
     *
     */
    public void setAssociatedPartTable() {
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
    }

    /**validates user data entered in Add Product form
     *
     * @return
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
                alert.setHeaderText("Product Not Added");
                alert.setContentText("Please enter a product name to proceed.");
                alert.showAndWait();
                productVerifyFlag = false;
            } else {
                //verifies min is not less than zero and not more than max; returns error if not
                if (min < 0 || min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("FAILURE");
                    alert.setHeaderText("Product Not Added");
                    alert.setContentText("Min value must be less than Max value but no less than zero.");
                    alert.showAndWait();
                    productVerifyFlag = false;
                } else {
                    //verifies stock level is between min and max values; returns error if not
                    if (stock < min || stock > max) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("FAILURE");
                        alert.setHeaderText("Product Not Added");
                        alert.setContentText("Inventory stock cannot be greater than Max value or less than Min value.");
                        alert.showAndWait();
                        productVerifyFlag = false;
                    } else {
                        //verifies price is greater than $0; returns error if not
                        if (price < .01) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("FAILURE");
                            alert.setHeaderText("Product Not Added");
                            alert.setContentText("Please enter a price of .01 or greater.");
                            alert.showAndWait();
                            productVerifyFlag = false;
                            //sets flag to true if all validation checks passed
                        } else {
                            System.out.println("Product verification completed successfully!");
                            productVerifyFlag = true;
                        }
                    }
                }
            }
            //returns general error for other missing/invalid entries
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FAILURE");
            alert.setHeaderText("Product Not Added");
            alert.setContentText("One or more entries on the form is missing or has an invalid format.");
            alert.showAndWait();
            productVerifyFlag = false;
        }
        return productVerifyFlag;}

    /**alert user that product was added successfully
     *
     * @param name
     * @param event
     * @throws IOException
     */
    public void addSuccess(String name, MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("Product Added");
        alert.setContentText(name + " has been added successfully.");
        alert.showAndWait();
        URL mainMenuURL = Paths.get("src/main/java/view/MainMenu.fxml").toUri().toURL();
        Parent mainMenuParent = FXMLLoader.load(mainMenuURL);
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage mainMenuStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }

    /**disable Product ID TextField
     *
     */
    public void disableInteractivity() {
        ProductIDTextField.setEditable(false);
        ProductIDTextField.setMouseTransparent(true);
    }

    /**deselects search field after a failed search; allows user to hit "enter" in response to the error message without continually receiving errors
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

    /**finds highest product ID currently in product table
     *
     * @return highest current ID number for products on file
     */
    public static int findMaxProductID() {
        return AllProducts.stream()
                .mapToInt(Product::getId) // Assuming getValue returns the integer property
                .max()
                .orElse(0); // Return 0 if list is empty
    }

    /**adds one to max product ID to use as ID for new products
     *
     * @return highest ID number + 1
     */
    public static int incrementProductID() {
        int newID = findMaxProductID();
        newID++;
        return newID;
    }
}
