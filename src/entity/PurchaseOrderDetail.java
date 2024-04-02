package entity;

import java.util.Objects;

/**
 *
 * @author Như Tâm
 */
public class PurchaseOrderDetail {

    /* Hằng báo lỗi*/
    public static final String QUANTITY_ERROR = "Số lượng không được bé hơn 0";
    public static final String ORDERID_EMPTY = "Đơn hàng không được rỗng";
    public static final String COSTPRICE_ERROR = "Giá nhập không được bé hơn 0";

    private int quantity;
    private double costPrice;
    private double lineTotal;
    private PurchaseOrder purchaseOrder;
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseOrderDetail(PurchaseOrder purchaseOrder, Product product, int quantity, double costPrice) throws Exception {
        setPurchaseOrder(purchaseOrder);
        setProduct(product);
        setQuantity(quantity);
        setCostPrice(costPrice);
        setLineTotal();
    }

    public PurchaseOrderDetail(PurchaseOrder purchaseOrder, Product product, int quantity, double costPrice, double lineTotal) throws Exception {
        this.purchaseOrder = purchaseOrder;
        this.product = product;
        this.quantity = quantity;
        this.costPrice = costPrice;
        this.lineTotal = lineTotal;
    }

    public PurchaseOrderDetail(PurchaseOrder purchaseOrder, Product product) throws Exception {
        setPurchaseOrder(purchaseOrder);
        setProduct(product);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity < 0) {
            throw new Exception(QUANTITY_ERROR);
        }
        this.quantity = quantity;
        setLineTotal();
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) throws Exception {
        if (costPrice < 0) {
            throw new Exception(COSTPRICE_ERROR);
        }
        this.costPrice = costPrice;

    }

    public double getLineTotal() {
        return lineTotal;
    }

    /*Tổng = giá trị * số lượng*/
    private void setLineTotal() {
        this.lineTotal = quantity * costPrice;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) throws Exception {
        if (purchaseOrder == null) {
            throw new Exception(ORDERID_EMPTY);
        }
        this.purchaseOrder = purchaseOrder;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.purchaseOrder);
        hash = 43 * hash + Objects.hashCode(this.product);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PurchaseOrderDetail other = (PurchaseOrderDetail) obj;
        if (!Objects.equals(this.purchaseOrder, other.purchaseOrder)) {
            return false;
        }
        return Objects.equals(this.product, other.product);
    }

    @Override
    public String toString() {
        return purchaseOrder + "," + quantity + "," + costPrice + "," + lineTotal;
    }

}
