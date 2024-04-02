package gui;

import bus.PromotionManagament_BUS;
import com.formdev.flatlaf.FlatClientProperties;
import entity.Product;
import entity.ProductPromotionDetail;
import entity.Promotion;
import enums.DiscountType;
import enums.PromotionType;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raven.toast.Notifications;
import utilities.SVGIcon;

/**
 *
 * @author Như Tâm
 */
public class ProductPromotionManagament_GUI extends javax.swing.JPanel {

    private PromotionManagament_BUS bus;
    private Promotion currentPromotion;
    private DefaultComboBoxModel cmbModel_type;
    private DefaultComboBoxModel cmbModel_status;
    private DefaultTableModel tblModel_productPromotion;
    private ArrayList<ProductPromotionDetail> cart;
    private DefaultTableModel tblModel_inforProductPromotion;
    DecimalFormat price = new DecimalFormat("###,### VND");

    /**
     * Creates new form PromotionFPManagament_GUI
     */
    public ProductPromotionManagament_GUI() {
        initComponents();
        init();
    }
    
    private void init() {
        bus = new PromotionManagament_BUS();
        cart = new ArrayList<>();
        //model
        tblModel_inforProductPromotion = new DefaultTableModel(new String[]{"Mã khuyến mãi", "Loại", "Giảm giá", "Trạng thái"}, 0);
        tbl_inforProductPromo.setModel(tblModel_inforProductPromotion);
        tbl_inforProductPromo.getSelectionModel().addListSelectionListener((e) -> {
            int rowIndex = tbl_inforProductPromo.getSelectedRow();
            if(rowIndex == -1)
                return;
            
            String promotionID = tblModel_inforProductPromotion.getValueAt(rowIndex, 0).toString();
            this.currentPromotion = bus.getPromotion(promotionID);
            renderCurrentPromotion();
        });
        
        tblModel_productPromotion = new DefaultTableModel(new String[] {"Mã sản phẩm", "Tên sản phẩm"}, 0);
        tbl_productPromo.setModel(tblModel_productPromotion);
    
        //combobox
        cmbModel_type = new DefaultComboBoxModel(new String[] {"Loại", "Số tiền", "Phần trăm"});
        cmb_typePromo.setModel(cmbModel_type);
        cmbModel_status = new DefaultComboBoxModel(new String[] {"Trạng thái", "Đang diễn ra", "Đã kết thúc"});
        cmb_statusPromo.setModel(cmbModel_status);
        
        renderPromotionTables(bus.getAllPromotionForProduct());
    }
    private void renderCurrentPromotion() {
        txt_promotionID.setText(currentPromotion.getPromotionID());
        if(currentPromotion.getTypeDiscount().getValue() == 1) {
            rdb_price.setSelected(true);
            txt_discountPromo.setText(price.format(currentPromotion.getDiscount()));
        }
        else {
            rdb_percent.setSelected(true);
            txt_discountPromo.setText(currentPromotion.getDiscount()+"");
        }
        
        chooseStartDate.setDate(currentPromotion.getStartedDate());
        chooseEndDate.setDate(currentPromotion.getEndedDate());
        renderProductPromotionTables(currentPromotion.getListDetail());
    }
    
    private void renderProductPromotionTables(ArrayList<ProductPromotionDetail> listDetail) {
        tblModel_productPromotion.setRowCount(0);
        for (ProductPromotionDetail productPromotionDetail : listDetail) {
            Product product = bus.getProduct(productPromotionDetail.getProduct().getProductID());
            String[] newRow = {product.getProductID(), product.getName()};
            tblModel_productPromotion.addRow(newRow);
        }
    };
    
    private void renderPromotionTables(ArrayList<Promotion> allPromotionForCustomer) {
        tblModel_inforProductPromotion.setRowCount(0);
        String status, type, discount;
        
        for (Promotion promotion : allPromotionForCustomer) {
            if(promotion.getEndedDate().after(java.sql.Date.valueOf(LocalDate.now())))
                status = "Còn hạn";
            else
                status = "Hết hạn";
            if(promotion.getTypeDiscount().getValue() == 1) {
                type = "Tiền";
                discount = price.format(promotion.getDiscount());
            }
            else {
                type = "Phần trăm";
                discount = promotion.getDiscount()+"%";
            }
            String[] newRow = {promotion.getPromotionID(), type, discount, status};
            tblModel_inforProductPromotion.addRow(newRow);
        }
    }

    private void renderPromotionInfor() {
        txt_searchProduct.setText("");
        txt_promotionID.setText("");
        rdb_percent.setSelected(true);
        txt_discountPromo.setText("");
        chooseStartDate.setDate(java.sql.Date.valueOf(LocalDate.now()));
        chooseEndDate.setDate(java.sql.Date.valueOf(LocalDate.now()));
        tblModel_productPromotion.setRowCount(0);
    }

    private Promotion getNewValue() throws Exception {
        double discount = Double.parseDouble(txt_discountPromo.getText());
        int type;
        if(rdb_price.isSelected() == true)
            type = 1;
        else
            type = 0;
        Date startedDate = chooseStartDate.getDate();
        Date endedDate = chooseEndDate.getDate();
        String promotionID = bus.generateID(PromotionType.PRODUCT, DiscountType.fromInt(type), endedDate);
        Promotion promotion = new Promotion(promotionID, startedDate, endedDate, PromotionType.PRODUCT, DiscountType.fromInt(type), discount);
        return promotion;
    }

    private void removeProductPromotion(String promotionID) {
        if(bus.removePromotion(promotionID)) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đã gỡ khuyến mãi " + promotionID);
            renderPromotionTables(bus.getAllPromotionForProduct());
        }
        else
            Notifications.getInstance().show(Notifications.Type.ERROR, "Gỡ không thành công");
    }
    
    private void removeProductPromotionOther(Promotion pm) {
        if(bus.removeProductPromotionOther(pm)) {
            Notifications.getInstance().show(Notifications.Type.SUCCESS, "Đã gỡ khuyến mãi " + pm.getPromotionID());
            renderPromotionTables(bus.getAllPromotionForProduct());
        }
        else
            Notifications.getInstance().show(Notifications.Type.ERROR, "Gỡ không thành công");
    }
    private void renderNewProductPromotionTables(ArrayList<ProductPromotionDetail> detail) {
        tblModel_productPromotion.setRowCount(0);
        for (ProductPromotionDetail item : detail) {
            Object[] newRow = new Object[]{item.getProduct().getProductID(), item.getProduct().getName()};
            tblModel_productPromotion.addRow(newRow);
        }
    }
    private void handleAddItem(Product product) {
        //Kiểm tra xem trong danh sách sản phẩm khuyến mãi đã có sản phẩm đó hay chưa
        for (ProductPromotionDetail productPromotionDetail : cart) {
            if(productPromotionDetail.getProduct().getProductID().equals(product.getProductID())) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Sản phẩm đã được thêm");
                return;
            }
    }
        //Nếu chưa có thì thêm mới vào cart
        addItemToCart(product);
    }
    private void addItemToCart(Product product) {
        if (product == null) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Không tìm thấy sản phẩm có mã " + product.getProductID());
        } else {
            try {
        //Thêm vào khuyến mãi
                ProductPromotionDetail newProductPromotionDetail = new ProductPromotionDetail(product);
                cart.add(newProductPromotionDetail);
                renderNewProductPromotionTables(cart);
                txt_searchProduct.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
                Notifications.getInstance().show(Notifications.Type.ERROR, "Có lỗi xảy ra khi thêm sản phẩm " + product.getProductID());
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        group_typeDiscount = new javax.swing.ButtonGroup();
        pnl_searchPromotion = new javax.swing.JPanel();
        pnl_txtSearchProduct = new javax.swing.JPanel();
        txt_searchProduct = new javax.swing.JTextField();
        pnl_buttonSearchProduct = new javax.swing.JPanel();
        btn_addProduct = new javax.swing.JButton();
        pnl_filterPromo = new javax.swing.JPanel();
        cmb_typePromo = new javax.swing.JComboBox<>();
        cmb_statusPromo = new javax.swing.JComboBox<>();
        btn_searchFilterPromo = new javax.swing.JButton();
        btn_refresh = new javax.swing.JButton();
        slp_promotion = new javax.swing.JSplitPane();
        pnl_listPromotion = new javax.swing.JPanel();
        pnl_promotionInfor = new javax.swing.JPanel();
        src_inforProductPromo = new javax.swing.JScrollPane();
        tbl_inforProductPromo = new javax.swing.JTable();
        pnl_promotionNew = new javax.swing.JPanel();
        pnl_inforPromo = new javax.swing.JPanel();
        pnl_txtInforPromo = new javax.swing.JPanel();
        pnl_promoID = new javax.swing.JPanel();
        lbl_promotionID = new javax.swing.JLabel();
        txt_promotionID = new javax.swing.JTextField();
        pnl_typePromo = new javax.swing.JPanel();
        lbl_typePromo = new javax.swing.JLabel();
        pnl_rdbTypePromo = new javax.swing.JPanel();
        rdb_percent = new javax.swing.JRadioButton();
        rdb_price = new javax.swing.JRadioButton();
        pnl_discountPromo = new javax.swing.JPanel();
        lbl_discountPromo = new javax.swing.JLabel();
        txt_discountPromo = new javax.swing.JTextField();
        pnl_startDatePromo = new javax.swing.JPanel();
        lbl_startDatePromo = new javax.swing.JLabel();
        pnl_chooseStartDate = new javax.swing.JPanel();
        chooseStartDate = new com.toedter.calendar.JDateChooser();
        pnl_endDatePromo = new javax.swing.JPanel();
        lbl_endDatePromo = new javax.swing.JLabel();
        pnl_chooseDateEnd = new javax.swing.JPanel();
        chooseEndDate = new com.toedter.calendar.JDateChooser();
        pnl_categoryProduct = new javax.swing.JPanel();
        lbl_categoryProduct = new javax.swing.JLabel();
        scr_productPromo = new javax.swing.JScrollPane();
        tbl_productPromo = new javax.swing.JTable();
        pnl_buttonPromo = new javax.swing.JPanel();
        btn_clearValue = new javax.swing.JButton();
        btn_removePromo = new javax.swing.JButton();
        btn_createPromo = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnl_searchPromotion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnl_searchPromotion.setMaximumSize(new java.awt.Dimension(900, 50));
        pnl_searchPromotion.setMinimumSize(new java.awt.Dimension(500, 27));
        pnl_searchPromotion.setPreferredSize(new java.awt.Dimension(700, 50));
        pnl_searchPromotion.setLayout(new javax.swing.BoxLayout(pnl_searchPromotion, javax.swing.BoxLayout.X_AXIS));

        pnl_txtSearchProduct.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnl_txtSearchProduct.setLayout(new javax.swing.BoxLayout(pnl_txtSearchProduct, javax.swing.BoxLayout.LINE_AXIS));

        txt_searchProduct.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã sản phẩm");
        txt_searchProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_searchProductKeyPressed(evt);
            }
        });
        pnl_txtSearchProduct.add(txt_searchProduct);

        pnl_searchPromotion.add(pnl_txtSearchProduct);

        pnl_buttonSearchProduct.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pnl_buttonSearchProduct.setMaximumSize(new java.awt.Dimension(100, 2147483647));
        pnl_buttonSearchProduct.setPreferredSize(new java.awt.Dimension(100, 23));
        pnl_buttonSearchProduct.setLayout(new java.awt.BorderLayout());

        btn_addProduct.setText("Thêm");
        btn_addProduct.setMaximumSize(new java.awt.Dimension(39, 23));
        btn_addProduct.setMinimumSize(new java.awt.Dimension(39, 23));
        btn_addProduct.setPreferredSize(new java.awt.Dimension(39, 23));
        btn_addProduct.putClientProperty(FlatClientProperties.STYLE,""
            + "background:$Menu.background;"
            + "foreground:$Menu.foreground;");
        btn_addProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addProductActionPerformed(evt);
            }
        });
        pnl_buttonSearchProduct.add(btn_addProduct, java.awt.BorderLayout.CENTER);

        pnl_searchPromotion.add(pnl_buttonSearchProduct);

        pnl_filterPromo.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        pnl_filterPromo.setMaximumSize(new java.awt.Dimension(500, 50));
        pnl_filterPromo.setMinimumSize(new java.awt.Dimension(300, 32));
        pnl_filterPromo.setPreferredSize(new java.awt.Dimension(400, 50));
        pnl_filterPromo.setLayout(new java.awt.GridLayout(1, 0));

        cmb_typePromo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Loại" }));
        cmb_typePromo.setMaximumSize(new java.awt.Dimension(32767, 30));
        cmb_typePromo.setPreferredSize(new java.awt.Dimension(90, 30));
        pnl_filterPromo.add(cmb_typePromo);

        cmb_statusPromo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trạng thái", "Đang diễn ra", "Đã kết thúc" }));
        cmb_statusPromo.setMaximumSize(new java.awt.Dimension(32767, 30));
        cmb_statusPromo.setPreferredSize(new java.awt.Dimension(110, 30));
        pnl_filterPromo.add(cmb_statusPromo);

        btn_searchFilterPromo.setText("Lọc");
        btn_searchFilterPromo.setActionCommand("");
        btn_searchFilterPromo.setMaximumSize(new java.awt.Dimension(72, 40));
        btn_searchFilterPromo.setPreferredSize(new java.awt.Dimension(72, 40));
        btn_searchFilterPromo.setIcon(SVGIcon.getSVGIcon("imgs/public/filter.svg"));
        btn_searchFilterPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchFilterPromoActionPerformed(evt);
            }
        });
        pnl_filterPromo.add(btn_searchFilterPromo);

        btn_refresh.setIcon(SVGIcon.getSVGIcon("imgs/public/refresh.svg"));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        pnl_filterPromo.add(btn_refresh);

        pnl_searchPromotion.add(pnl_filterPromo);

        add(pnl_searchPromotion, java.awt.BorderLayout.NORTH);

        slp_promotion.setResizeWeight(0.5);

        pnl_listPromotion.setMaximumSize(new java.awt.Dimension(1000, 2147483647));
        pnl_listPromotion.setPreferredSize(new java.awt.Dimension(800, 432));
        pnl_listPromotion.setLayout(new java.awt.BorderLayout());

        pnl_promotionInfor.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách khuyến mãi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(71, 118, 185)), javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3))); // NOI18N
        pnl_promotionInfor.setMaximumSize(new java.awt.Dimension(900, 2147483647));
        pnl_promotionInfor.setMinimumSize(new java.awt.Dimension(700, 16));
        pnl_promotionInfor.setPreferredSize(new java.awt.Dimension(700, 402));
        pnl_promotionInfor.setLayout(new java.awt.BorderLayout());

        src_inforProductPromo.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        src_inforProductPromo.setPreferredSize(new java.awt.Dimension(452, 300));

        tbl_inforProductPromo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã KM", "Loại KM", "Giảm giá", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_inforProductPromo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        src_inforProductPromo.setViewportView(tbl_inforProductPromo);

        pnl_promotionInfor.add(src_inforProductPromo, java.awt.BorderLayout.CENTER);

        pnl_listPromotion.add(pnl_promotionInfor, java.awt.BorderLayout.CENTER);

        slp_promotion.setLeftComponent(pnl_listPromotion);

        pnl_promotionNew.setAlignmentX(0.0F);
        pnl_promotionNew.setAlignmentY(0.0F);
        pnl_promotionNew.setMaximumSize(new java.awt.Dimension(500, 32767));
        pnl_promotionNew.setMinimumSize(new java.awt.Dimension(450, 192));
        pnl_promotionNew.setPreferredSize(new java.awt.Dimension(500, 190));
        pnl_promotionNew.setLayout(new javax.swing.BoxLayout(pnl_promotionNew, javax.swing.BoxLayout.Y_AXIS));

        pnl_inforPromo.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin khuyến mãi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(71, 118, 185)), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5))); // NOI18N
        pnl_inforPromo.setMinimumSize(new java.awt.Dimension(400, 263));
        pnl_inforPromo.setLayout(new java.awt.BorderLayout());

        pnl_txtInforPromo.setLayout(new javax.swing.BoxLayout(pnl_txtInforPromo, javax.swing.BoxLayout.Y_AXIS));

        pnl_promoID.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_promoID.setMaximumSize(new java.awt.Dimension(2147483647, 50));
        pnl_promoID.setMinimumSize(new java.awt.Dimension(151, 20));
        pnl_promoID.setLayout(new javax.swing.BoxLayout(pnl_promoID, javax.swing.BoxLayout.X_AXIS));

        lbl_promotionID.setText("Mã khuyến mãi:");
        lbl_promotionID.setMaximumSize(new java.awt.Dimension(105, 16));
        lbl_promotionID.setMinimumSize(new java.awt.Dimension(77, 16));
        lbl_promotionID.setPreferredSize(new java.awt.Dimension(105, 16));
        pnl_promoID.add(lbl_promotionID);

        txt_promotionID.setEditable(false);
        txt_promotionID.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        txt_promotionID.setMinimumSize(new java.awt.Dimension(64, 30));
        txt_promotionID.setPreferredSize(new java.awt.Dimension(71, 40));
        txt_promotionID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_promotionIDActionPerformed(evt);
            }
        });
        pnl_promoID.add(txt_promotionID);

        pnl_txtInforPromo.add(pnl_promoID);

        pnl_typePromo.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_typePromo.setLayout(new javax.swing.BoxLayout(pnl_typePromo, javax.swing.BoxLayout.X_AXIS));

        lbl_typePromo.setText("Loại khuyến mãi:");
        lbl_typePromo.setMaximumSize(new java.awt.Dimension(105, 16));
        lbl_typePromo.setMinimumSize(new java.awt.Dimension(77, 16));
        lbl_typePromo.setPreferredSize(new java.awt.Dimension(105, 16));
        pnl_typePromo.add(lbl_typePromo);

        pnl_rdbTypePromo.setMaximumSize(new java.awt.Dimension(32767, 40));
        pnl_rdbTypePromo.setPreferredSize(new java.awt.Dimension(100, 40));
        pnl_rdbTypePromo.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        group_typeDiscount.add(rdb_percent);
        rdb_percent.setText("Phần trăm");
        pnl_rdbTypePromo.add(rdb_percent);

        group_typeDiscount.add(rdb_price);
        rdb_price.setText("Tiền");
        rdb_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdb_priceActionPerformed(evt);
            }
        });
        pnl_rdbTypePromo.add(rdb_price);

        pnl_typePromo.add(pnl_rdbTypePromo);

        pnl_txtInforPromo.add(pnl_typePromo);

        pnl_discountPromo.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_discountPromo.setLayout(new javax.swing.BoxLayout(pnl_discountPromo, javax.swing.BoxLayout.X_AXIS));

        lbl_discountPromo.setText("Giảm giá:");
        lbl_discountPromo.setMaximumSize(new java.awt.Dimension(105, 16));
        lbl_discountPromo.setMinimumSize(new java.awt.Dimension(77, 16));
        lbl_discountPromo.setPreferredSize(new java.awt.Dimension(105, 16));
        pnl_discountPromo.add(lbl_discountPromo);

        txt_discountPromo.setMaximumSize(new java.awt.Dimension(2147483647, 40));
        txt_discountPromo.setMinimumSize(new java.awt.Dimension(64, 30));
        txt_discountPromo.setPreferredSize(new java.awt.Dimension(64, 40));
        pnl_discountPromo.add(txt_discountPromo);

        pnl_txtInforPromo.add(pnl_discountPromo);

        pnl_startDatePromo.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_startDatePromo.setLayout(new javax.swing.BoxLayout(pnl_startDatePromo, javax.swing.BoxLayout.X_AXIS));

        lbl_startDatePromo.setText("Ngày bắt đầu:");
        lbl_startDatePromo.setMaximumSize(new java.awt.Dimension(105, 16));
        lbl_startDatePromo.setMinimumSize(new java.awt.Dimension(77, 16));
        lbl_startDatePromo.setPreferredSize(new java.awt.Dimension(105, 16));
        pnl_startDatePromo.add(lbl_startDatePromo);

        pnl_chooseStartDate.setMaximumSize(new java.awt.Dimension(32767, 40));
        pnl_chooseStartDate.setPreferredSize(new java.awt.Dimension(100, 40));
        pnl_chooseStartDate.setLayout(new java.awt.GridLayout(1, 0));
        pnl_chooseStartDate.add(chooseStartDate);

        pnl_startDatePromo.add(pnl_chooseStartDate);

        pnl_txtInforPromo.add(pnl_startDatePromo);

        pnl_endDatePromo.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_endDatePromo.setLayout(new javax.swing.BoxLayout(pnl_endDatePromo, javax.swing.BoxLayout.X_AXIS));

        lbl_endDatePromo.setText("Ngày kết thúc:");
        lbl_endDatePromo.setMaximumSize(new java.awt.Dimension(105, 16));
        lbl_endDatePromo.setPreferredSize(new java.awt.Dimension(105, 16));
        pnl_endDatePromo.add(lbl_endDatePromo);

        pnl_chooseDateEnd.setMaximumSize(new java.awt.Dimension(32767, 40));
        pnl_chooseDateEnd.setPreferredSize(new java.awt.Dimension(10, 40));
        pnl_chooseDateEnd.setLayout(new java.awt.GridLayout(1, 0));
        pnl_chooseDateEnd.add(chooseEndDate);

        pnl_endDatePromo.add(pnl_chooseDateEnd);

        pnl_txtInforPromo.add(pnl_endDatePromo);

        pnl_categoryProduct.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnl_categoryProduct.setLayout(new java.awt.BorderLayout(0, 10));

        lbl_categoryProduct.setText("Nhóm sản phẩm:");
        lbl_categoryProduct.setMaximumSize(new java.awt.Dimension(105, 16));
        lbl_categoryProduct.setMinimumSize(new java.awt.Dimension(77, 16));
        lbl_categoryProduct.setPreferredSize(new java.awt.Dimension(105, 16));
        pnl_categoryProduct.add(lbl_categoryProduct, java.awt.BorderLayout.NORTH);

        tbl_productPromo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_productPromo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scr_productPromo.setViewportView(tbl_productPromo);

        pnl_categoryProduct.add(scr_productPromo, java.awt.BorderLayout.CENTER);

        pnl_txtInforPromo.add(pnl_categoryProduct);

        pnl_inforPromo.add(pnl_txtInforPromo, java.awt.BorderLayout.CENTER);

        pnl_buttonPromo.setMinimumSize(new java.awt.Dimension(100, 50));
        pnl_buttonPromo.setPreferredSize(new java.awt.Dimension(1261, 50));
        pnl_buttonPromo.setLayout(new java.awt.GridLayout(1, 0));

        btn_clearValue.setText("Xóa trắng");
        btn_clearValue.setToolTipText("Xóa trắng");
        btn_clearValue.setIcon(SVGIcon.getSVGIcon("imgs/public/clear.svg"));
        btn_clearValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearValueActionPerformed(evt);
            }
        });
        pnl_buttonPromo.add(btn_clearValue);

        btn_removePromo.setText("Dừng KM");
        btn_removePromo.setIcon(SVGIcon.getSVGIcon("imgs/public/update.svg"));
        btn_removePromo.setActionCommand("");
        btn_removePromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removePromoActionPerformed(evt);
            }
        });
        pnl_buttonPromo.add(btn_removePromo);

        btn_createPromo.setText("Tạo mới");
        btn_createPromo.setIcon(SVGIcon.getPrimarySVGIcon("imgs/public/add.svg"));
        btn_createPromo.setPreferredSize(new java.awt.Dimension(79, 50));
        btn_createPromo.putClientProperty(FlatClientProperties.STYLE,""
            + "background:$Menu.background;"
            + "foreground:$Menu.foreground;");
        btn_createPromo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_createPromoActionPerformed(evt);
            }
        });
        pnl_buttonPromo.add(btn_createPromo);

        pnl_inforPromo.add(pnl_buttonPromo, java.awt.BorderLayout.SOUTH);

        pnl_promotionNew.add(pnl_inforPromo);

        slp_promotion.setRightComponent(pnl_promotionNew);

        add(slp_promotion, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_searchProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchProductKeyPressed
        String searchQuery = txt_searchProduct.getText();
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (searchQuery.isBlank()) {
                Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng điền mã sản phẩm");
                return;
            }
            Product product = bus.searchProductById(searchQuery);
            if(product == null) {
                Notifications.getInstance().show(Notifications.Type.WARNING, "Không tồn tại sản phẩm " + searchQuery);
                return;
            }
            handleAddItem(product);
        }

    }//GEN-LAST:event_txt_searchProductKeyPressed

    private void btn_addProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addProductActionPerformed
        String searchQuery = txt_searchProduct.getText();
        if (searchQuery.isBlank()) {
            Notifications.getInstance().show(Notifications.Type.INFO, "Vui lòng điền mã sản phẩm");
            return;
        }
        Product product = bus.searchProductById(searchQuery);
        if(product == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Không tồn tại sản phẩm " + searchQuery);
            return;
        }
        handleAddItem(product);
    }//GEN-LAST:event_btn_addProductActionPerformed

    private void btn_searchFilterPromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchFilterPromoActionPerformed
        int type = cmb_typePromo.getSelectedIndex();
        int status = cmb_statusPromo.getSelectedIndex();
        renderPromotionTables(bus.filterForProduct(type, status));
    }//GEN-LAST:event_btn_searchFilterPromoActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        renderPromotionTables(bus.getAllPromotionForProduct());
        renderPromotionInfor();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void txt_promotionIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_promotionIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_promotionIDActionPerformed

    private void rdb_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdb_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdb_priceActionPerformed

    private void btn_createPromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_createPromoActionPerformed
        if(txt_discountPromo.getText().isBlank()) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng nhập thông tin");
            return;
        }
        try {
            Promotion newPromotion = getNewValue();
            if(bus.addNewPromotion(newPromotion)) {
                Notifications.getInstance().show(Notifications.Type.SUCCESS, "Thêm thành công");
                bus.createProductPromotionDetail(newPromotion, cart);
                renderPromotionTables(bus.getAllPromotionForProduct());
                renderPromotionInfor();
            }
            else
                Notifications.getInstance().show(Notifications.Type.ERROR, "Thêm không thành công");
        } catch (Exception ex) {
            Notifications.getInstance().show(Notifications.Type.WARNING, ex.getMessage());
        }

    }//GEN-LAST:event_btn_createPromoActionPerformed

    private void btn_removePromoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removePromoActionPerformed
        if(currentPromotion == null) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Vui lòng chọn khuyến mãi để gỡ");
            return;
        }
        String promotionID = txt_promotionID.getText();
        Promotion pm = bus.getPromotion(promotionID);
        if(pm.getEndedDate().before(java.sql.Date.valueOf(LocalDate.now()))) {
            Notifications.getInstance().show(Notifications.Type.WARNING, "Khuyến mãi đã hết hạn");
        }
        if(pm.getStartedDate().after(java.sql.Date.valueOf(LocalDate.now()))) {
            if (JOptionPane.showConfirmDialog(null,
            "Bạn thật sự muốn gỡ khuyến mãi " + promotionID + " không? Vẫn chưa đến hạn diễn ra khuyến mãi!", "Gỡ khuyến mãi?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    // Xoá
                    removeProductPromotionOther(pm);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                renderPromotionTables(bus.getAllPromotionForProduct());
                renderPromotionInfor();
            }
            else
                Notifications.getInstance().show(Notifications.Type.INFO, "Đã huỷ thao tác!");
        }
        else {
            if (JOptionPane.showConfirmDialog(null,
            "Bạn thật sự muốn gỡ khuyến mãi " + promotionID + " không?", "Gỡ khuyến mãi?",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                try {
                    // Xoá
                    removeProductPromotion(promotionID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                renderPromotionTables(bus.getAllPromotionForProduct());
                renderPromotionInfor();
            }
            else
                Notifications.getInstance().show(Notifications.Type.INFO, "Đã huỷ thao tác!");
        }
    }//GEN-LAST:event_btn_removePromoActionPerformed

    private void btn_clearValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearValueActionPerformed
        renderPromotionTables(bus.getAllPromotionForProduct());
        renderPromotionInfor();
    }//GEN-LAST:event_btn_clearValueActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addProduct;
    private javax.swing.JButton btn_clearValue;
    private javax.swing.JButton btn_createPromo;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_removePromo;
    private javax.swing.JButton btn_searchFilterPromo;
    private com.toedter.calendar.JDateChooser chooseEndDate;
    private com.toedter.calendar.JDateChooser chooseStartDate;
    private javax.swing.JComboBox<String> cmb_statusPromo;
    private javax.swing.JComboBox<String> cmb_typePromo;
    private javax.swing.ButtonGroup group_typeDiscount;
    private javax.swing.JLabel lbl_categoryProduct;
    private javax.swing.JLabel lbl_discountPromo;
    private javax.swing.JLabel lbl_endDatePromo;
    private javax.swing.JLabel lbl_promotionID;
    private javax.swing.JLabel lbl_startDatePromo;
    private javax.swing.JLabel lbl_typePromo;
    private javax.swing.JPanel pnl_buttonPromo;
    private javax.swing.JPanel pnl_buttonSearchProduct;
    private javax.swing.JPanel pnl_categoryProduct;
    private javax.swing.JPanel pnl_chooseDateEnd;
    private javax.swing.JPanel pnl_chooseStartDate;
    private javax.swing.JPanel pnl_discountPromo;
    private javax.swing.JPanel pnl_endDatePromo;
    private javax.swing.JPanel pnl_filterPromo;
    private javax.swing.JPanel pnl_inforPromo;
    private javax.swing.JPanel pnl_listPromotion;
    private javax.swing.JPanel pnl_promoID;
    private javax.swing.JPanel pnl_promotionInfor;
    private javax.swing.JPanel pnl_promotionNew;
    private javax.swing.JPanel pnl_rdbTypePromo;
    private javax.swing.JPanel pnl_searchPromotion;
    private javax.swing.JPanel pnl_startDatePromo;
    private javax.swing.JPanel pnl_txtInforPromo;
    private javax.swing.JPanel pnl_txtSearchProduct;
    private javax.swing.JPanel pnl_typePromo;
    private javax.swing.JRadioButton rdb_percent;
    private javax.swing.JRadioButton rdb_price;
    private javax.swing.JScrollPane scr_productPromo;
    private javax.swing.JSplitPane slp_promotion;
    private javax.swing.JScrollPane src_inforProductPromo;
    private javax.swing.JTable tbl_inforProductPromo;
    private javax.swing.JTable tbl_productPromo;
    private javax.swing.JTextField txt_discountPromo;
    private javax.swing.JTextField txt_promotionID;
    private javax.swing.JTextField txt_searchProduct;
    // End of variables declaration//GEN-END:variables

}
