package model;

/**This declares the in-house parts class.
 *
 */
public class InHouse extends Part {

    /**declares machine id variable
     *
     */
    private int machineId;

    /**declares in-house parts object
     *
     * @param id id number
     * @param name part name
     * @param price part price
     * @param stock stock level
     * @param min minimum allowable stock
     * @param max maximum allowable stock
     * @param machineId machine ID for parts made in-house
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**set machine ID for an in-house part
     *
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**fetches machine ID for an in-house part
     *
     * @return
     */
    public int getMachineId() {
        return machineId;
    }

}
