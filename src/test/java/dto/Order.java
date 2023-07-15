package dto;

public class Order {

    String status;
    Integer courierId;
    String customerName;
    String customerPhone;
    String comment;
    int id;


    public Order(int courierId, String customerName, String customerPhone, String comment, int id) {
        this.status = "OPEN";
        this.courierId = courierId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = comment;
        this.id = id;
    }

    public Order() {
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCourierId(Integer courierId) {
        this.courierId = courierId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getComment() {
        return comment;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public int getId() {
        return id;
    }


}
