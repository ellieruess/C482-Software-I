package model;

/**This declares outsource parts class.
 *
 */
public class Outsourced extends Part {

    /** declares company name variable
     *
     */
    private String companyName;

    /**outsource parts object
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**sets company name for the manufacturer of a part
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** fetches company name for a part
     *
     * @return company name for the manufacturer of a specific part
     */
    public String getCompanyName() {
        return companyName;
    }
}