package ruess;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;
import java.net.URL;
import java.nio.file.Paths;

/**JAVADOC LOCATION
Javadoc folder is located at "New / src / main / java / javadoc"
 */

/**
FUTURE ENHANCEMENT
In the future, as the business grows its inventory, this application may be insufficient for tracking
inventory. One area with room for improvement is the min / max stock fields. The information from those
fields isn't easily visible except in the add / modify part/product screens. Additional functionality
could be built into the software to display a list of parts / products where the stock-on-hand / inventory
level is within X-number of units of the existing minimum allowable stock level. The 'X' number of units
would be a user-defined integer. This additional functionality would be able to guide decisions surrounding
the purchasing or creation of parts / products.
 */

/** This class declares the Scheduling system application.
 *
 */
public class Main extends Application {

    /** retrieves input from the console
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**starts program
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL mainMenuPath = Paths.get("src/main/java/view/MainMenu.fxml").toUri().toURL();
        loader.setLocation(new URL(String.valueOf(mainMenuPath)));
        AnchorPane mainMenuAnchorPane = loader.load();

        Scene scene = new Scene(mainMenuAnchorPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ellie Ruess - Software I - Inventory Management System");
        primaryStage.show();

        //call method to populate tables
        populateTables();
    }

    /**method to populate tables
     *
     */
    public void populateTables () {
        //add in house part test data
        Inventory.addPart(new InHouse(1, "HDMI Cable", 2.13, 41, 10, 50, 19));
        Inventory.addPart(new InHouse(2, "Wireless Mouse", 15.40, 13, 5, 25, 11));
        Inventory.addPart(new InHouse(3, "Bluetooth Adapter", 14.89, 9, 2, 15, 82));
        Inventory.addPart(new InHouse(4, "Tower PC Case", 159.97, 7, 1, 10, 37));
        Inventory.addPart(new InHouse(5, "Power Supply Unit", 26.68, 4, 1, 10, 59));
        Inventory.addPart(new InHouse(6, "Wireless Keyboard", 22.17, 14, 5, 25, 12));
        Inventory.addPart(new InHouse(7, "PC Fan", 52.24, 2, 1, 15, 39));
        Inventory.addPart(new InHouse(8, "USB-C Headset", 24.19, 12, 1, 35, 13));
        Inventory.addPart(new InHouse(9, "USB 3.0 PCI-e Expansion Card", 34.91, 16, 10, 50, 20));

        //add outsource part test data
        Inventory.addPart(new Outsourced(10, "UltraWide Curved Monitor", 249.99, 6, 2, 20, "LG"));
        Inventory.addPart(new Outsourced(11, "Mechanical Keyboard", 49.95, 2, 1, 5, "SteelSeries"));
        Inventory.addPart(new Outsourced(12, "GeForce RTX 4060 Graphics Card", 55.99, 9, 1, 10, "NVIDIA"));
        Inventory.addPart(new Outsourced(13, "Ryzen 5 4500 Processor", 15.47, 1, 1, 10, "AMD"));
        Inventory.addPart(new Outsourced(14, "DDR4 64GB (2 x 32GB) RAM", 199.00, 11, 5, 15, "Corsair"));
        Inventory.addPart(new Outsourced(15, "1TB Solid State Drive", 114.99, 3, 1, 10, "Samsung"));
        Inventory.addPart(new Outsourced(16, "Monitor", 110.95, 18, 5, 30, "Asus"));
        Inventory.addPart(new Outsourced(17, "Radeon HD 8490 Graphics Card", 39.90, 15, 5, 30, "AMD"));
        Inventory.addPart(new Outsourced(18, "DDR4 8GB RAM", 21.99, 8, 5, 20, "Kingston"));
        Inventory.addPart(new Outsourced(19, "Cooling PC Case", 159.99, 3, 1, 10, "NZXT"));

        //add product test data
        Inventory.addProduct(new Product(1, "Gaming PC Setup", 2099.00, 0, 0, 2));
        Inventory.addProduct(new Product(2, "Basic PC Setup", 329.99, 6, 1, 12));
        Inventory.addProduct(new Product(3, "Keyboard / Mouse Bundle", 50.00, 12, 1, 15));
        Inventory.addProduct(new Product(4, "Apple MacBook Pro", 1999.00, 1, 1, 5));
        Inventory.addProduct(new Product(5, "Business PC Setup", 799.00, 3, 1, 10));
        Inventory.addProduct(new Product(6, "Power Cable", 49.00, 9, 1, 15));
        Inventory.addProduct(new Product(7, "4-Port USB 3.0 Hub", 14.99, 16, 10, 50));
        Inventory.addProduct(new Product(8, "Raspberry Pi 5", 79.99, 8, 1, 10));
        Inventory.addProduct(new Product(9, "Home PC Setup", 599.00, 4, 1, 10));
        Inventory.addProduct(new Product(10, "Motherboard, PSU, PCI-e Card Bundle", 125.99, 1, 1, 5));
        Inventory.addProduct(new Product(11, "Cooling Bundle", 250.00, 2, 1, 5));
    }
}




