package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**This declares the class for the controller which manages all the functions of the about screen.
 *
 */
public class AboutController implements Initializable {

    /**establishes a text area for data below to appear in
     *
     */
    @FXML private TextArea aboutTextField;

    /**
     *prevents user from editing the about text area
     */
    public void initialize (URL location, ResourceBundle resources) {
        aboutTextField.setEditable(false);
    }

    /**populates text about future enhancements
     *
     * @param event
     */
    @FXML
    void futureEnhancementsClicked(MouseEvent event) {
        aboutTextField.setText("In the future, as the business grows its inventory, this application may be insufficient for tracking inventory. One area with room for improvement is the min / max stock fields. The information from those fields isn't easily \n" +
                "visible except in the add / modify part/product screens. Additional functionality could be built into the software to display a list of parts / products where the stock-on-hand / inventory level is within X-number of units of the \n"+
                "existing minimum allowable stock level. The 'X' number of units would be a user-defined integer. This additional functionality would be able to guide decisions surrounding the purchasing or creation of parts / products.");
    }

    /**populates assignment requirement text
     *
     * @param event
     */
    @FXML
    void assignmentDetailsClicked(MouseEvent event) {
        aboutTextField.setText("Below are the requirements for the C482 Final Assignment:\n\n" +
                "I.  User Interface\n" +
                "A.  Create a JavaFX application with a graphical user interface (GUI) based on the attached “Software 1 GUI Mock-Up.” You may use JavaFX with or without FXML to create your GUI, or you may use Scene Builder \nto create your FXML file; use of Swing is not permitted. The user interface (UI) should closely match the organization of the GUI layout and contain all UI components (buttons, text fields, etc.) in each of the following \nGUI mock-up forms:\n" +
                "1.  Main form\n" +
                "2.  Add Part form\n" +
                "3.  Modify Part form\n" +
                "4.  Add Product form\n" +
                "5.  Modify Product form\n" +
                "\n" +
                "Note: You may use one FXML file for forms with an identical UI component structure. You may also use a single window that can be switched to a different menu, or a new window can be launched for each form. As of \nJDK 11, JavaFX is no longer included in the JDK API but is available as an SDK or module.\n" +
                "\n" +
                "B.  Provide Javadoc comments for each class member throughout the code, and include a detailed description of the following in your comments:\n" +
                "• a logical or runtime error that you corrected in the code and how it was corrected\n" +
                "• a future enhancement that would extend the functionality of the application if it were to be updated\n" +
                "\n" +
                "Note: For these comments to accurately export to the Javadoc comments, please add the logical and runtime error comments in the method header declaration comments where the error that was corrected occurred, \nand include the future enhancement comments in the comments of the main class. Please start these comments with “RUNTIME ERROR” or “FUTURE ENHANCEMENT” as applicable.\n" +
                "\n" +
                "II.  Application\n" +
                "C.  Create classes with data and logic that map to the UML class diagram and include the supplied Part class provided in the attached “Part.java.” Do not alter the provided class. Include all the classes and members \nas shown in the UML diagram. Your code should demonstrate the following:\n" +
                "•   inheritance\n" +
                "•   abstract and concrete classes\n" +
                "•   instance and static variables\n" +
                "•   instance and static methods\n" +
                "\n" +
                "D.  Add the following functionalities to the Main form:\n" +
                "1.  The Parts pane\n" +
                "•   The Add button under the Parts TableView opens the Add Part form.\n" +
                "•   The Modify button under the Parts TableView opens the Modify Part form.\n" +
                "•   The Delete button under the Parts TableView deletes the selected part from the Parts TableView or displays a descriptive error message in the UI or in a dialog box if a part is not deleted.\n" +
                "•   When the user searches for parts by ID or name (partial or full name) using the text field, the application displays matching results in the Parts TableView. (Including a search button is optional.) If the part or parts \nare found, the application highlights a single part or filters multiple parts. If the part is not found, the application displays an error message in the UI or in a dialog box.\n" +
                "•   If the search field is set to empty, the table should be repopulated with all available parts.\n" +
                "2.  The Products pane\n" +
                "•   The Add button under the Products TableView opens the Add Product form.\n" +
                "•   The Modify button under the Products TableView opens the Modify Product form.\n" +
                "•   The Delete button under the Products TableView deletes the selected product (if appropriate) from the Products TableView or displays a descriptive error message in the UI or in a dialog box if a product is not deleted.\n" +
                "•   When the user searches for products by ID or name (partial or full name) using the text field, the application displays matching results in the Products TableView. (Including a search button is optional.) If a product or \nproducts are found, the application highlights a single product or products or filters multiple products. If a product or products are not found, the application displays an error message in the UI or in a dialog box.\n" +
                "•   If the search field is set to empty, the table should be repopulated with all available products.\n" +
                "\n" +
                "Note: A product’s associated parts can exist independent of current inventory of parts. You are not required to display sample data upon launching your application. You do not need to save your data to a database \nor a file; data for this application is nonpersistent and will reside in computer memory while in use.\n" +
                "\n" +
                "3.  Exit button\n" +
                "• The Exit button closes the application.\n" +
                "\n" +
                "E.  Add the listed functionalities to the following parts forms:\n" +
                "1.  The Add Part form\n" +
                "• The In-House and Outsourced radio buttons switch the bottom label to the correct value (Machine ID or Company Name).\n" +
                "•   The application auto-generates a unique part ID. The part IDs can be, but do not need to be, contiguous.\n" +
                "-   The part ID text field must be disabled.\n" +
                "•   The user should be able to enter a part name, inventory level or stock, a price, maximum and minimum values, and company name or machine ID values into active text fields.\n" +
                "•   After saving the data, users are automatically redirected to the Main form.\n" +
                "•   Canceling or exiting this form redirects users to the Main form.\n" +
                "2.  The Modify Part form\n" +
                "• The text fields populate with the data from the chosen part.\n" +
                "• The In-House and Outsourced radio buttons switch the bottom label to the correct value (Machine ID or Company Name) and swap In-House parts and Outsourced parts. When new objects need to be created after \nthe Save button is clicked, the part ID should be retained.\n" +
                "• The user can modify data values in the text fields sent from the Main form except the part ID.\n" +
                "• After saving modifications to the part, the user is automatically redirected to the Main form.\n" +
                "• Canceling or exiting this form redirects users to the Main form.\n" +
                "\n" +
                "F.  Add the following functionalities to the following product forms:\n" +
                "1.  The Add Product form\n" +
                "•   The application auto-generates a unique product ID. The product IDs can be, but do not need to be, contiguous.\n" +
                "•   The product ID text field must be disabled and cannot be edited or changed.\n" +
                "•   The user should be able to enter a product name, inventory level or stock, a price, and maximum and minimum values.\n" +
                "•   The user can search for parts (top table) by ID or name (partial or full name). If the part or parts are found, the application highlights a single part or filters multiple parts. If the part or parts are not found, the application \ndisplays an error message in the UI or in a dialog box.\n" +
                "•   If the search field is set to empty, the table should be repopulated with all available parts.\n" +
                "•   The top table should be identical to the Parts TableView in the Main form.\n" +
                "•   The user can select a part from the top table. The user then clicks the Add button, and the part is copied to the bottom table. (This associates one or more parts with a product.)\n" +
                "•   The Remove Associated Part button removes a selected part from the bottom table. (This dissociates or removes a part from a product.)\n" +
                "•   After saving the data, the user is automatically redirected to the Main form.\n" +
                "•   Canceling or exiting this form redirects users to the Main form.\n" +
                "\n" +
                "Note: When a product is deleted, so can its associated parts without affecting the part inventory. The Remove Associated Part button removes a selected part from the bottom table. (This dissociates or removes \na part from a product.)\n" +
                "\n" +
                "2.  The Modify Product form\n" +
                "•   The text fields populate with the data from the chosen product, and the bottom TableView populates with the associated parts.\n" +
                "•   The user can search for parts (top table) by ID or name (partial or full name). If the part or parts are found, the application highlights a single part or filters multiple parts.\n" +
                "•   If the part is not found, the application displays an error message in the UI or a dialog box.\n" +
                "•   If the search text field is set to empty, the table should be repopulated with all available parts.\n" +
                "•   The top table should be identical to the Parts TableView in the Main form.\n" +
                "•   The user may modify or change data values.\n" +
                "-   The product ID text field must be disabled and cannot be edited or changed.\n" +
                "•   The user can select a part from the top table. The user then clicks the Add button, and the part is copied to the bottom table. (This associates one or more parts with a product.)\n" +
                "•   The user may associate zero, one, or more parts with a product.\n" +
                "•   The user may remove or disassociate a part from a product.\n" +
                "•   After saving modifications to the product, the user is automatically redirected to the Main form.\n" +
                "•   Canceling or exiting this form redirects users to the Main form.\n" +
                "\n" +
                "Note: The Remove Associated Part button removes a selected part from the bottom table. (This dissociates or removes a part from a product.)\n" +
                "\n" +
                "G.  Write code to implement input validation and logical error checks using a dialog box or message in the UI displaying a descriptive error message for each of the following circumstances:\n" +
                "•   Min should be less than Max; and Inv should be between those two values.\n" +
                "•   The user should not delete a product that has a part associated with it.\n" +
                "•   The application confirms the “Delete” and “Remove” actions.\n" +
                "•   The application will not crash when inappropriate user data is entered in the forms; instead, error messages should be generated.\n" +
                "\n" +
                "H.  Provide a folder containing Javadoc files that were generated from the IDE or via the command prompt from part B. In a comment above the main method header declaration, please specify where this folder is \nlocated.\n" +
                "\n" +
                "I.  Demonstrate professional communication in the content and presentation of your submission.");
    }

    /**populates text about a runtime error encountered during programming
     *
     * @param event
     */
    @FXML
    void runtimeErrorClicked(MouseEvent event) {
        aboutTextField.setText("My runtime error notation can be viewed in the MainMenuController for this assignment. I encountered a runtime error when attempting to operate the 'addPart' mouse event in the main screen of this application. I used the \n" +
                "absolute file path to access the 'AddPart.fxml' file (file:///Users/ellie/Desktop/New/src/main/java/view/AddPart.fxml) rather than the content root path (/src/main/java/view/AddPart.fxml). My code compiled correctly, but \n" +
                "when I attempted to click the 'Add' part button, I received an error:\n\n '/Users/ellie/Desktop/New/file:/Users/ellie/Desktop/New/src/main/java/view/AddPart.fxml (No such file or directory)'.\n\n" +
                "Based on the runtime error, I determined that I only needed to include the path to the content root, not the absolute path. After replacing the absolute path with the content root, I rebuilt and reran the application. Thanks to \n" +
                "the updated file path, the 'Add' part button could be used successfully to open the 'Add Part' form.");
    }

    /**populates data about the program's author
     *
     * @param event
     */
    @FXML
    void studentInfoClicked(MouseEvent event) {
        aboutTextField.setText("Ellie Ruess\nStudent ID: 000375111\nMajor: Computer Science\nStudent Mentor: Rebecca Wagoner");
    }
}
