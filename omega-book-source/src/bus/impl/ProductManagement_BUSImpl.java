/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import bus.ProductManagement_BUS;
import dao.Brand_DAO;
import dao.Product_DAO;
import entity.Book;
import entity.Brand;
import entity.Product;
import entity.Stationery;
import enums.BookCategory;
import enums.StationeryType;
import enums.Type;

/**
 *
 * @author thanhcanhit
 */
public class ProductManagement_BUSImpl extends UnicastRemoteObject implements ProductManagement_BUS{

    public ProductManagement_BUSImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -215476132767080495L;
	private final Product_DAO productDAO = new Product_DAO();
    private final Brand_DAO brandDAO = new Brand_DAO();

    public ArrayList<Product> getDataOfPage(int page) throws RemoteException{
        return (ArrayList<Product>) productDAO.getPage(page);
    }

    public Product getProduct(String ID) throws RemoteException{
        return productDAO.getOne(ID);
    }

    public int getLastPage() throws RemoteException{
//       Tổng số sản phẩm / số sản phẩm 1 trang sau đó làm tròn lên
        int result = (int) Math.ceil(Double.valueOf(productDAO.getLength()) / 50);
        return result;
    }

    public ArrayList<Product> searchById(String searchQuery) throws RemoteException{
        return productDAO.findById(searchQuery);
    }

    public boolean updateProduct(String id, Product product) throws RemoteException{
        return productDAO.update(id, product);
    }

    public boolean createProduct(Product productWithoutId) throws RemoteException{
        String id = "SP";
//        SPabccxxxx. Trong đó: 
//      - a: là 1 nếu là sách, 2 nếu là văn phòng phẩm và các sản phẩm khác là 3
//      - b: 0 văn phòng phẩm 1 sách trong nước, 2 sách ngoại văn
//        cc: loại sản phẩm
//      - xxxx: là 4 số nguyên dương
        id += productWithoutId.getType().getValue();
        if (productWithoutId.getType() == Type.STATIONERY) {
            Stationery stationeryInstant = (Stationery) (productWithoutId);
            id += 0;
            id += String.format("%02d", stationeryInstant.getStationeryType().getValue());
        } else {
            Book bookInstant = (Book) (productWithoutId);
            id += bookInstant.getBookOrigin().getValue();
            id += String.format("%02d", bookInstant.getBookCategory().getValue());
        }

        String finalID = productDAO.generateID(id);
        try {
            productWithoutId.setProductID(finalID);
        } catch (Exception ex) {
            Logger.getLogger(ProductManagement_BUSImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        productDAO.create(productWithoutId);
        return true;
    }

    public ArrayList<Product> filter(String name, Boolean isEmpty, int type, int detailType) throws RemoteException{
//      Nếu không lọc loại sản phẩm
        if (type == 0) {
            return productDAO.filter(name, isEmpty, null, null, null);
        }

//      Nếu không lọc loại chi tiết sản phẩm      
        if (detailType == 0) {
            return productDAO.filter(name, isEmpty, Type.fromInt(type), null, null);
        }

//      Có loại sản phẩm và loại chi tiết
        if (Type.fromInt(type) == Type.BOOK) {
            return productDAO.filter(name, isEmpty, Type.BOOK, BookCategory.fromInt(detailType), null);
        } else {
            return productDAO.filter(name, isEmpty, Type.STATIONERY, null, StationeryType.fromInt(detailType));
        }

    }

    public ArrayList<Brand> getAllBrand() throws RemoteException{
        return brandDAO.getAll();
    }

    public ArrayList<Product> filter(int type, int detailType) throws RemoteException{
        ArrayList<Product> list = getAll();
        ArrayList<Product> result = new ArrayList<>();

        if (type == 1) {
            for (Product product : list) {
                if (product.getType() == Type.BOOK) {
                    Book book = (Book) product;
                    if (book.getBookCategory() == BookCategory.fromInt(detailType)) {
                        result.add(book);
                    }
                }
            }
            return result;
        } else {
            for (Product product : list) {
                if (product.getType() == Type.STATIONERY) {
                    Stationery stationery = (Stationery) product;
                    if (stationery.getStationeryType() == StationeryType.fromInt(detailType)) {
                        result.add(stationery);
                    }
                }
            }
            return result;
        }
//        if (detailType == 0) {
//            return productDAO.filter("", true, Type.fromInt(type), null, null);
//        }
//        
////      Có loại sản phẩm và loại chi tiết
//        if (Type.fromInt(type) == Type.BOOK) {
//            return productDAO.filter("", true, Type.BOOK, BookCategory.fromInt(detailType), null);
//        } else {
//            return productDAO.filter("", true, Type.STATIONERY, null, StationeryType.fromInt(detailType));
//        }
    }

    public ArrayList<Product> getAll() throws RemoteException{
        return productDAO.getAll();
    }

}
