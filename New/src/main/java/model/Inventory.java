package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**This declares the inventory class
 *
 */
public class Inventory {

    /**declares parts and products lists
     *
     */

    public static ObservableList<Part> AllParts = FXCollections.observableArrayList();
    public static ObservableList<Product> AllProducts = FXCollections.observableArrayList();

    /**add a new part
     *
     * @param newPart
     */
    public static void addPart(Part newPart) {
        AllParts.add(newPart);
    }

    /**add a new product
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        AllProducts.add(newProduct);
    }

    /**look up a part by id#
     *
     * @param partId
     * @return part associated with the id#
     */
    public static Part lookupPart(int partId) {
        for (Part part : AllParts) {
            if (partId == part.getId()) {
                return part;
            }
        }
        return null;
    }

    /**look up a product by id#
     *
     * @param productId
     * @return product associated with the id#
     */
    public static Product lookupProduct(int productId) {
        for (Product product : AllProducts) {
            if (productId == product.getId()) {
                return product;
            }
        }
        return null;
    }

    /**look up a part by name
     *
     * @param partName
     * @return part with a name that matches the string partName
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchPart = FXCollections.observableArrayList();

        for (Part part : AllParts) {
            if (part.getName().equals(partName)) {
                searchPart.add(part);
            }
        }
        return searchPart;
    }

    /**look up a product by name
     *
     * @param productName
     * @return product with a name that matches the string partName
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchProduct = FXCollections.observableArrayList();

        for (Product product : AllProducts) {
            if (product.getName().equals(productName)) {
                searchProduct.add(product);
            }
        }
        return searchProduct;
    }

    /**update a part
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        Inventory.getAllParts().set(index, selectedPart);
    }

    /**update a product
     *
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        Inventory.getAllProducts().set(index, selectedProduct);
    }

    /**delete a part
     *
     * @param selectedPart
     * @return true - successful part deletion, false - part deletion failure
     */
    public static boolean deletePart(Part selectedPart) {
        if (AllParts.contains(selectedPart)) {
            AllParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**delete a product
     *
     * @param selectedProduct
     * @return true (selected product deleted) or false (not deleted)
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (AllProducts.contains(selectedProduct)) {
            AllProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**get part list
     *
     * @return list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return AllParts;
    }

    /**get product list
     *
     * @return list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return AllProducts;
    }
}