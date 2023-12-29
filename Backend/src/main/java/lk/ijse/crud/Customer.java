package lk.ijse.crud;

/**
 * @author : savindaJ
 * @date : 12/29/2023
 * @since : 0.1.0
 **/
public class Customer {
    private String cusId;
    private String name;
    private String address;
    private Double salary;

    public Customer(String cusId, String name, String address, Double salary) {
        this.cusId = cusId;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }
}
