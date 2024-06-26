/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author KienTran
 */
@Entity
public final class OrderDetail implements Serializable{
    private static final long serialVersionUID = -8146874889849915391L;
	private static final String QUANTITY_ERROR = "Số lượng sản phẩm không được nhỏ hơn 1 !";
    private static final String PRICE_ERROR = "Giá bán phải lớn hơn giá nhập, không được rỗng và lớn hơn 0 !";
    private static final String ORDER_ERROR = "Hoá đơn không được rỗng !";
    private static final String PRODUCT_ERROR = "Sản phẩm không được rỗng !";
    
    @Id
    @ManyToOne
    @JoinColumn(name="orderID")
    private Bill order;
    @Id
    @ManyToOne
    @JoinColumn(name="productID")
    private Product product;
    private int quantity;
    private double price;
    private double lineTotal;
    private double VAT;
    private double seasonalDiscount = 0;

    public OrderDetail() {
    }

    public OrderDetail(Bill order, Product product, int quantity, double price, double VAT, double seasonalDiscount) throws Exception {
        setOrder(order);
        setProduct(product);
        setQuantity(quantity);
        setPrice(price);
        setLineTotal();
        setSeasonalDiscount(seasonalDiscount);
        setVAT(VAT);
    }

    public OrderDetail(Bill order, Product product, int quantity, double price, double lineTotal, double VAT, double seasonalDiscount) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.lineTotal = lineTotal;
        this.VAT = VAT;
        this.seasonalDiscount = seasonalDiscount;
    }

    public double getLineTotal() {
        return lineTotal;
    }

    public Bill getOrder() {
        return order;
    }

    public double getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSeasonalDiscount() {
        return seasonalDiscount;
    }

    public double getVAT() {
        return VAT;
    }

    private void setLineTotal() {
        this.lineTotal = this.price * this.quantity - this.seasonalDiscount;
    }

    public void setOrder(Bill order) throws Exception {
        if (order != null) {
            this.order = order;
        } else {
            throw new Exception(ORDER_ERROR);
        }

    }

    public void setPrice(double price) throws Exception {
        if (price > 0) {
            this.price = price;
            setLineTotal();
        } else {
            throw new Exception(PRICE_ERROR);
        }
    }

    public void setProduct(Product product) throws Exception {
        if (product != null) {
            this.product = product;
        } else {
            throw new Exception(PRODUCT_ERROR);
        }

    }

    public void setQuantity(int quantity) throws Exception {
        if (quantity > 0) {
            this.quantity = quantity;
            setLineTotal();
        } else {
            throw new Exception(QUANTITY_ERROR);
        }
    }

    public void setSeasonalDiscount(double seasonalDiscount) {
        this.seasonalDiscount = seasonalDiscount;
        setLineTotal();
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "QUANTITY_ERROR=" + QUANTITY_ERROR + ", PRICE_ERROR=" + PRICE_ERROR + ", ORDER_ERROR=" + ORDER_ERROR + ", PRODUCT_ERROR=" + PRODUCT_ERROR + ", order=" + order + ", product=" + product + ", quantity=" + quantity + ", price=" + price + ", lineTotal=" + lineTotal + '}';
    }

}
