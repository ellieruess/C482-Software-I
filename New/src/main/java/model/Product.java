package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This declares the product class.
 *
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**creates Product object
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**declares list of parts associated with specific products
     *
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**get ID number for product
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**set ID number for product
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**get product name
     *
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**set name for product
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**get price for product
     *
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the price for a product
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**get stock level for product
     *
     * @return stock level
     */
    public int getStock() {
        return stock;
    }

    /**sets stock level for product
     *
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** gets minimum allowable stock level for product
     *
     * @return
     */
    public int getMin() {
        return min;
    }

    /**set minimum allowable stock level for product
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**get maximum allowable stock level for product
     *
     * @return maximum allowable stock level
     */
    public int getMax() {
        return max;
    }

    /**set max allowable stock level for product
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**delete an associated part from a product's associated part list
     *
     * @param selectedAssociatedPart
     * @return true if deleted, false if not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        try {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**add associated part to a product's associated part list
     *
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**get a list of all parts associated with a product
     *
     * @return list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}



