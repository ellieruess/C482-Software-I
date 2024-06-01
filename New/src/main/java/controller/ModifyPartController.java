package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;

/**This declares the class for the controller which manages all the functions of the modify part screen.
 *
 */
public class ModifyPartController implements Initializable {

    /**establishes gui elements for modify part screen
     *
     */
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourceRadioButton;
    @FXML private TextField PartIDTextField;
    @FXML private TextField PartInventoryTextField;
    @FXML private TextField PartMaxTextField;
    @FXML private TextField PartMinTextField;
    @FXML private TextField PartNameTextField;
    @FXML private TextField PartPriceTextField;
    @FXML private Label partMachineIDCompanyNameLabel;
    @FXML private TextField MachineIDCompanyNameTextField;

    /**declare selected part variable; used for passing information in from main menu
     *
     */
    private Part partSelected;

    /**pass in part info from main menu; changes label between machine ID and company name based on in house/outsource; prepopulates text fields based on passed through data
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        partSelected = MainMenuController.
                getPartToModify();

        setTextFields(partSelected);
    }

    /**changes Company Name label to Machine ID when In House selected
     *
     * @param event
     */
    @FXML
    void changeCompanyNameLabel(MouseEvent event) {
        partMachineIDCompanyNameLabel.setText("Machine ID");
    }

    /**changes Machine ID label to Company Name when Outsourced selected
     *
     * @param event
     */
    @FXML
    void changeMachineIDLabel(MouseEvent event) {
        partMachineIDCompanyNameLabel.setText("Company Name");
    }

    /**verify data entered in Modify Part form
     *
     * @return true if data entered is validated, false if not
     */
    public boolean partVerified() {
        boolean partVerifyFlag = false;
        try {
            //verifies that user entered data in modify part form is present/valid
            int machineID;
            String companyName = MachineIDCompanyNameTextField.getText();
            String name = PartNameTextField.getText();
            Double price = Double.parseDouble(PartPriceTextField.getText());
            int stock = Integer.parseInt(PartInventoryTextField.getText());
            int max = Integer.parseInt(PartMaxTextField.getText());
            int min = Integer.parseInt(PartMinTextField.getText());
            //verifies part name is present; throws error if not
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("FAILURE");
                alert.setHeaderText("Part Not Modified");
                alert.setContentText("Please enter a part name to proceed.");
                alert.showAndWait();
                partVerifyFlag = false;
            } else {
                //verifies min is less than max + greater than zero; throws error if not
                if (min < 0 || min > max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("FAILURE");
                    alert.setHeaderText("Part Not Modified");
                    alert.setContentText("Please choose a Min value less than Max value but no less than zero.");
                    alert.showAndWait();
                    partVerifyFlag = false;
                } else {
                    //verifies stock is greater than min, less than max; throws error if not
                    if (stock < min || stock > max) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("FAILURE");
                        alert.setHeaderText("Part Not Modified");
                        alert.setContentText("Inventory stock cannot be greater than Max value or less than Min value.");
                        alert.showAndWait();
                        partVerifyFlag = false;
                    } else {
                        //verifies price is greater than $0; throws error if not
                        if (price < .01) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("FAILURE");
                            alert.setHeaderText("Part Not Modified");
                            alert.setContentText("Please enter a price of .01 or greater.");
                            alert.showAndWait();
                            partVerifyFlag = false;
                        } else {
                            //verifies machine ID is integer if in-house is selected; throws error if not; sets flag to true if machine ID is valid, representing that data is valid
                            if (inHouseRadioButton.isSelected()) {
                                try {
                                    machineID = Integer.parseInt(MachineIDCompanyNameTextField.getText());
                                    partVerifyFlag = true;
                                } catch (Exception e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("FAILURE");
                                    alert.setHeaderText("Part Not Modified");
                                    alert.setContentText("Please enter Machine ID as an integer.");
                                    alert.showAndWait();
                                    partVerifyFlag = false;
                                }
                            } else {
                                //verifies company name is present if outsource is selected; throws error if not; sets flag to true if company name is valid, representing that data is valid
                                if (outsourceRadioButton.isSelected()) {
                                    if (companyName.isEmpty()) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("FAILURE");
                                        alert.setHeaderText("Part Not Modified");
                                        alert.setContentText("Please enter a Company ID.");
                                        alert.showAndWait();
                                        partVerifyFlag = false;
                                    } else {
                                        partVerifyFlag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //throws error if invalid data found in first step of this function
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FAILURE");
            alert.setHeaderText("Part Not Modified");
            alert.setContentText("One or more entries on the form is missing or has an invalid format.");
            alert.showAndWait();
            partVerifyFlag = false;
        }
        //returns true if valid data, false if not
        return partVerifyFlag;
    }

    /**method used if modify part is successful; creates success alert and returns user to main page
     *
     * @param name
     * @param event
     * @throws IOException
     */
    public void modifySuccess(String name, MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("Part Modified");
        alert.setContentText(name + " has been modified successfully.");
        alert.showAndWait();
        URL mainMenuURL = Paths.get("src/main/java/view/MainMenu.fxml").toUri().toURL();
        Parent mainMenuParent = FXMLLoader.load(mainMenuURL);
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage mainMenuStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        mainMenuStage.setScene(mainMenuScene);
        mainMenuStage.show();
    }

    /**uses partVerified to validate user data, then stores modified part under a new variable and deletes old version
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void saveModifyPartChanges(MouseEvent event) throws IOException {
        if (partVerified()) {
            int id = Integer.parseInt(PartIDTextField.getText());
            String name = PartNameTextField.getText();
            Double price = Double.parseDouble(PartPriceTextField.getText());
            int stock = Integer.parseInt(PartInventoryTextField.getText());
            int max = Integer.parseInt(PartMaxTextField.getText());
            int min = Integer.parseInt(PartMinTextField.getText());
            if (inHouseRadioButton.isSelected()) {
                int machineID;
                machineID = Integer.parseInt(MachineIDCompanyNameTextField.getText());
                InHouse modifiedInHousePart = new InHouse(id, name, price, stock, min, max, machineID);
                Inventory.addPart(modifiedInHousePart);
                Inventory.deletePart(partSelected);
                modifySuccess(name, event);
            } else {
                if (outsourceRadioButton.isSelected()) {
                    String companyName = MachineIDCompanyNameTextField.getText();
                    Outsourced modifiedOutsourcePart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(modifiedOutsourcePart);
                    modifySuccess(name, event);
                }
            }
            //specific errors are generated by productVerified; this is a general else statement alternative if productVerified returns false
        } else {
            System.out.println("Part Modification Failure! See pop-up alert for details.");
        }
    }

    /**confirms user wants to cancel if cancel button is clicked; throws alert that unsaved changes will be lost; returns user to main screen
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void cancelModifyPartChanges(MouseEvent event) throws IOException {
        //confirmation alert thrown
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRM");
        alert.setHeaderText("Please confirm cancellation.");
        alert.setContentText("Are you sure you want to cancel? All changes will be lost");
        Optional<ButtonType> result = alert.showAndWait();
        //if confirmed by user, pushes user to main screen
        if (result.isPresent() && result.get() == ButtonType.OK) {
            URL mainMenuURL = Paths.get("src/main/java/view/MainMenu.fxml").toUri().toURL();
            Parent mainMenuParent = FXMLLoader.load(mainMenuURL);
            Scene mainMenuScene = new Scene(mainMenuParent);
            Stage mainMenuStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            mainMenuStage.setScene(mainMenuScene);
            mainMenuStage.show();
        }
    }

    /**set text fields on modify screen based on data from part selected on main menu
     *
     * @param partSelected
     */
    public void setTextFields(Part partSelected) {
        if (partSelected instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            partMachineIDCompanyNameLabel.setText("Machine ID");
            MachineIDCompanyNameTextField.setText(String.valueOf(((InHouse) partSelected).getMachineId()));
        }

        if (partSelected instanceof Outsourced) {
            outsourceRadioButton.setSelected(true);
            partMachineIDCompanyNameLabel.setText("Company Name");
            MachineIDCompanyNameTextField.setText(((Outsourced) partSelected).getCompanyName());
        }

        PartIDTextField.setText(String.valueOf(partSelected.getId()));
        PartNameTextField.setText(partSelected.getName());
        PartInventoryTextField.setText(String.valueOf(partSelected.getStock()));
        PartPriceTextField.setText(String.valueOf(partSelected.getPrice()));
        PartMaxTextField.setText(String.valueOf(partSelected.getMax()));
        PartMinTextField.setText(String.valueOf(partSelected.getMin()));
        PartIDTextField.setEditable(false);
        PartIDTextField.setMouseTransparent(true);
    }
}


