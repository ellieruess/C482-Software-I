package controller;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import model.*;


/**This declares the class for the controller which manages all the functions of the main menu.
 *
 */
public class MainMenuController implements Initializable {

    /**declares variables for user selected part/product from tables; used to push part/product data to modify part / modify product
     *
     */
    private static Part partToModify;
    private static Product productToModify;

    /**establishes gui elements for main menu
     *
     */
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField partSearch;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TextField productSearch;

    /**sets part and product tables
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize (URL location, ResourceBundle resources){
        updatePartTableView();
        updateProductTableView();
    }

    /**allows user to click  about item button in menu bar displays text doc about the projec
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void aboutItemClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL aboutPath = Paths.get("src/main/java/view/About.fxml").toUri().toURL();
        loader.setLocation(new URL(String.valueOf(aboutPath)));
        AnchorPane aboutAnchorPane = loader.load();

        Scene scene = new Scene(aboutAnchorPane);
        Stage aboutStage = new Stage();
        aboutStage.setScene(scene);
        aboutStage.setTitle("About This Project");
        aboutStage.show();
    }

    /**ends program when user clicks end
     *
     * @param event
     */
    @FXML
    void exitApplication(MouseEvent event) {
        System.exit(0);
    }

    /**
    RUNTIME ERROR
    I encountered a runtime error when attempting to operate the 'addPart' mouse event in the main screen of this application. I used the
    absolute file path to access the 'AddPart.fxml' file (file:///Users/ellie/Desktop/New/src/main/java/view/AddPart.fxml)
    rather than the content root path. The program compiled successfully, but when I attempted to click the 'Add' part button
    within the application, I received an error:

    '/Users/ellie/Desktop/New/file:/Users/ellie/Desktop/New/src/main/java/view/AddPart.fxml (No such file or directory)'.

    Based on the runtime error, I determined that I only needed to include the path to the content root (src/main/java/view/AddPart.fxml),
    not the absolute path. After replacing the absolute path with the content root, I rebuilt and reran the application.
    Thanks to the updated file path, the 'Add' part button could be used successfully to open the 'Add Part' form.
     */

    /**Opens add part screen when user clicks add button
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void addPart(MouseEvent event) throws IOException {
        URL addPartURL = Paths.get("src/main/java/view/AddPart.fxml").toUri().toURL();
        Parent addPartParent = FXMLLoader.load(addPartURL);
        Scene addPartScene = new Scene(addPartParent);
        Stage addPartStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    /**modify part screen opened
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void modifyPart(MouseEvent event) throws IOException {
        partToModify = partTableView.getSelectionModel().getSelectedItem();

        if (partToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FAILURE");
            alert.setHeaderText("Product Modification Failed");
            alert.setContentText("Please select a product to modify and try again.");
            alert.showAndWait();
        } else {
            URL modifyPartURL = Paths.get("src/main/java/view/ModifyPart.fxml").toUri().toURL();
            Parent modifyPartParent = FXMLLoader.load(modifyPartURL);
            Scene modifyPartScene = new Scene(modifyPartParent);
            Stage modifyPartStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            modifyPartStage.setScene(modifyPartScene);
            modifyPartStage.show();
        }
    }

    /**delete a part from table
     *
     * @param event
     */
    @FXML
    private void deletePart(MouseEvent event) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        //throws error if user does not select a part to be deleted
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FAILURE");
            alert.setHeaderText("Part Deletion Failure");
            alert.setContentText("Please select a part to be deleted.");
            alert.showAndWait();
        } else {
            //confirms user wants to delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("CONFIRM");
            alert.setHeaderText("Please confirm deletion.");
            alert.setContentText("Are you sure you want to delete part " + selectedPart.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            //if user confirms, deletes part
            if (result.isPresent() && result.get() == ButtonType.OK) {
                partTableView.getItems().remove(selectedPart);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("SUCCESS");
                alert2.setHeaderText("Part Deletion Successful");
                alert2.setContentText(selectedPart.getName() + " has been deleted!");
                alert2.showAndWait();
            }
        }
    }

    /**search for a part; search initiates after each keystroke
     *
     * @param kEvent
     */
    @FXML
    void partSearching(KeyEvent kEvent) {
        ObservableList<Part> AllParts = Inventory.getAllParts();
        ObservableList<Part> foundPart = FXCollections.observableArrayList();
        //gets text from search text field
        String partSearchString = partSearch.getText();
        //converts part names and text field entry to all lowercase, removes leading or ending spaces and compares to find matches between the two
        for (Part part : AllParts) {
            if (String.valueOf(part.getId()).contains(partSearchString.trim()) ||
                    part.getName().toLowerCase().contains(partSearchString.toLowerCase().trim())) {
                foundPart.add(part);
            }
        }
        //populates table with search results
        partTableView.setItems(foundPart);
        //returns error if user clicks enter and there are no results, then clears / deselects text field
        if (kEvent.getCode() == KeyCode.ENTER) {
            if (foundPart.size() == 0) {
                partSearch.setText("");
                deselect(partSearch);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("FAILURE");
                alert.setHeaderText("Part Search Failed");
                alert.setContentText("Check your entry and try again.");
                alert.showAndWait();
            }
        }
    }

    /**add product screen opened
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void addProduct(MouseEvent event) throws IOException {
        URL addProductURL = Paths.get("src/main/java/view/AddProduct.fxml").toUri().toURL();
        Parent addProductParent = FXMLLoader.load(addProductURL);
        Scene addProductScene = new Scene(addProductParent);
        Stage addProductStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        addProductStage.setScene(addProductScene);
        addProductStage.show();
    }

    /**modify product screen opened
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void modifyProduct(MouseEvent event) throws IOException {
        //gets selected product to be modified
        productToModify = productTableView.getSelectionModel().getSelectedItem();

        //alert that modify failed if user did not make a selection
        if (productToModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FAILURE");
            alert.setHeaderText("Product Modification Failed");
            alert.setContentText("Please select a product to modify and try again.");
            alert.showAndWait();

        } else {
            //opens modify product table
            URL modifyProductURL = Paths.get("src/main/java/view/ModifyProduct.fxml").toUri().toURL();
            Parent modifyProductParent = FXMLLoader.load(modifyProductURL);
            Scene modifyProductScene = new Scene(modifyProductParent);
            Stage modifyProductStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            modifyProductStage.setScene(modifyProductScene);
            modifyProductStage.show();
        }
    }

    /**delete product from table
     *
     * @param event
     */
    @FXML
        void deleteProduct (MouseEvent event) {
        //gets user-selected item from product table
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        //alerts if user did not make a selection
        if (selectedProduct == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("FAILURE");
                alert.setHeaderText("Product Deletion Failure");
                alert.setContentText("Please select a product to be deleted.");
            alert.showAndWait();
            } else {
            //confirms if user wants to delete product
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("CONFIRM");
                alert.setHeaderText("Please confirm deletion.");
                alert.setContentText("Are you sure you want to delete product " + selectedProduct.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    //if user selects okay, gets associated parts for product
                    ObservableList<Part> partsAssociated = selectedProduct.getAllAssociatedParts();
                    //if not associated parts, allows deletion of product and puts notice of success. Otherwise, throws error
                    if (partsAssociated.size() == 0) {
                        Inventory.deleteProduct(selectedProduct);
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("SUCCESS");
                        alert2.setHeaderText("Product Deletion Successful");
                        alert2.setContentText(selectedProduct.getName() + " has been deleted!");
                        alert2.showAndWait();
                    } else {
                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setTitle("FAILURE");
                        alert3.setHeaderText("Product Deletion Failed");
                        alert3.setContentText(selectedProduct.getName() + " was not deleted because the product has one or more parts associated with it. Remove the association and try again.");
                        alert3.showAndWait();
                    }
                }
            }
        }


    /**search for a product - search initiates after each key stroke
     *
     * @param kEvent
     */
    @FXML
        void productSearching (KeyEvent kEvent) {
            ObservableList<Product> AllProducts = Inventory.getAllProducts();
            ObservableList<Product> foundProduct = FXCollections.observableArrayList();
        //gets user entered data from text field
        String productSearchString = productSearch.getText();
            //makes product names and user entered text all lowercase, removes leading/trailing spaces and matches values
            for (Product product : AllProducts) {
                if (String.valueOf(product.getId()).contains(productSearchString.trim()) ||
                        product.getName().toLowerCase().contains(productSearchString.toLowerCase().trim())) {
                    foundProduct.add(product);
                }
            }
        //populates table with search results
        productTableView.setItems(foundProduct);
        //if user selects enter and there is no match, sets text back to blank and deselects search field, then throws error
        if (kEvent.getCode() == KeyCode.ENTER) {
                if (foundProduct.size() == 0) {
                    productSearch.setText("");
                    deselect(productSearch);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("FAILURE");
                    alert.setHeaderText("Product Search Failed");
                    alert.setContentText("Check your entry and try again.");
                    alert.showAndWait();
                }
            }
        }

    /**set up parts table
     *
     */
    public void updatePartTableView() {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        //show two decimal places in price column
        partPriceColumn.setCellFactory(tc -> new TableCell<Part, Double>() {
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
        //updates part table value
        partTableView.setItems(Inventory.getAllParts());
        partTableView.getSortOrder().setAll(partIDColumn);
    }

    /**set up products table
     *
     */
    public void updateProductTableView() {
        productIDColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        //show two decimal places in price column
        productPriceColumn.setCellFactory(tc -> new TableCell<Product, Double>() {
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
        //updates values in products table
        productTableView.setItems(Inventory.getAllProducts());
        productTableView.getSortOrder().setAll(productIDColumn);
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

    /**getter used to pass info about a selected part to modify part screen
     *
     * @return details about part being modified
     */
    public static Part getPartToModify() {
        return partToModify;
    }

    /**getter used to pass info about a selected product to modify part screen
     *
     * @return details about product being modified
     */
    public static Product getProductToModify() {
        return productToModify;
    }
}


