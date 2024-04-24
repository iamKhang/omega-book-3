/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatClientProperties;

import bus.BrandManagement_BUS;
import entity.Brand;
//import entity.Bill;
//import entity.PurchaseOrder;
import entity.PurchaseOrderDetail;
import main.Application;
import raven.toast.Notifications;
import utilities.RMIService;
import utilities.SVGIcon;

/**
 *
 * @author KienTran
 */
public class BrandManagement_GUI extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4021084556288869754L;

	/**
	 * Creates new form Sales_GUI
	 */
	private BrandManagement_BUS bus;

//    //
//    private PurchaseOrder purchaseOrder;
	@SuppressWarnings("unused")
	private ArrayList<PurchaseOrderDetail> cart;
	private DefaultTableModel tblModel_cart;
//    private DefaultComboBoxModel cmbModel_suplier;

	public BrandManagement_GUI() {
		initComponents();
		try {
			init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() throws RemoteException {
		try {
			bus = (BrandManagement_BUS) Naming.lookup(RMIService.brandBus);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//        table
		cart = new ArrayList<>();
		tblModel_cart = new DefaultTableModel(new String[] { "Mã thương hiệu", "Tên thương hiệu", "Thành phố" }, 0);
		tbl_brand.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
			int rowIndex = tbl_brand.getSelectedRow();
			if (rowIndex != -1) {
				String id = tblModel_cart.getValueAt(rowIndex, 0).toString();
				try {
					txt_brandID.setText(id);
					txt_brandName.setText(tblModel_cart.getValueAt(rowIndex, 1).toString());
					txa_country.setText(tblModel_cart.getValueAt(rowIndex, 2).toString());
				} catch (Exception ex) {
					Logger.getLogger(OrderManagement_GUI.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
			return;

		});

		tbl_brand.setModel(tblModel_cart);
		renderSupplierTable(bus.getALLBrand());

	}

	private void renderSupplierTable(ArrayList<Brand> list) {
		tblModel_cart.setRowCount(0);

		for (Brand brand : list) {
			Object[] newRow = new Object[] { brand.getBrandID(), brand.getName(), brand.getCountry() };

			tblModel_cart.addRow(newRow);
		}
	}

	private void rerender() throws RemoteException {
		Application.showForm(new BrandManagement_GUI());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	@SuppressWarnings("deprecation")
	private void initComponents() {

		splitPane_main = new javax.swing.JSplitPane();
		pnl_left = new javax.swing.JPanel();
		pnl_header = new javax.swing.JPanel();
		txt_search = new javax.swing.JTextField();
		btn_search = new javax.swing.JButton();
		pnl_cart = new javax.swing.JPanel();
		scr_cart = new javax.swing.JScrollPane();
		tbl_brand = new javax.swing.JTable();
		pnl_right = new javax.swing.JPanel();
		pnl_info = new javax.swing.JPanel();
		pnl_orderInfo = new javax.swing.JPanel();
		pnl_orderID = new javax.swing.JPanel();
		lbl_brandID = new javax.swing.JLabel();
		txt_brandID = new javax.swing.JTextField();
		pnl_orderDate = new javax.swing.JPanel();
		lbl_brandName = new javax.swing.JLabel();
		txt_brandName = new javax.swing.JTextField();
		pnl_orderCustomerGive = new javax.swing.JPanel();
		pnl_container = new javax.swing.JPanel();
		lbl_country = new javax.swing.JLabel();
		scr_description = new javax.swing.JScrollPane();
		txa_country = new javax.swing.JTextArea();
		pnl_btnGroup = new javax.swing.JPanel();
		btn_clear = new javax.swing.JButton();
		btn_update = new javax.swing.JButton();
		btn_create = new javax.swing.JButton();

		setLayout(new java.awt.GridLayout(1, 0));

		splitPane_main.setResizeWeight(0.7);
		splitPane_main.setMinimumSize(new java.awt.Dimension(1305, 768));

		pnl_left.setMinimumSize(new java.awt.Dimension(700, 59));
		pnl_left.setPreferredSize(new java.awt.Dimension(900, 768));
		pnl_left.setLayout(new java.awt.BorderLayout());

		pnl_header.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(71, 118, 185))); // NOI18N
		pnl_header.setPreferredSize(new java.awt.Dimension(1366, 60));
		pnl_header.setLayout(new javax.swing.BoxLayout(pnl_header, javax.swing.BoxLayout.LINE_AXIS));

		txt_search.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mã thương hiệu");
		txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txt_searchKeyPressed(evt);
			}
		});
		pnl_header.add(txt_search);

		btn_search.setText("Tìm kiếm");
		btn_search.setMaximumSize(new java.awt.Dimension(100, 50));
		btn_search.setMinimumSize(new java.awt.Dimension(100, 50));
		btn_search.setPreferredSize(new java.awt.Dimension(100, 50));
		btn_search.putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Menu.background;" + "foreground:$Menu.foreground;");
		btn_search.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_searchActionPerformed(evt);
			}
		});
		pnl_header.add(btn_search);

		pnl_left.add(pnl_header, java.awt.BorderLayout.NORTH);

		pnl_cart.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách nhà cung cấp",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(71, 118, 185))); // NOI18N
		pnl_cart.setLayout(new java.awt.BorderLayout());

		tbl_brand.setAutoCreateRowSorter(true);
		tbl_brand.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		tbl_brand.setShowGrid(false);
		scr_cart.setViewportView(tbl_brand);

		pnl_cart.add(scr_cart, java.awt.BorderLayout.CENTER);

		pnl_left.add(pnl_cart, java.awt.BorderLayout.CENTER);

		splitPane_main.setLeftComponent(pnl_left);

		pnl_right.setPreferredSize(new java.awt.Dimension(400, 768));
		pnl_right.setLayout(new java.awt.BorderLayout());

		pnl_info.setLayout(new javax.swing.BoxLayout(pnl_info, javax.swing.BoxLayout.Y_AXIS));

		pnl_orderInfo
				.setBorder(javax.swing.BorderFactory.createCompoundBorder(
						javax.swing.BorderFactory.createTitledBorder(null, "Thông tin đơn nhập",
								javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
								javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14),
								new java.awt.Color(71, 118, 185)),
						javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5))); // NOI18N
		pnl_orderInfo.setMaximumSize(new java.awt.Dimension(2147483647, 300));
		pnl_orderInfo.setPreferredSize(new java.awt.Dimension(500, 400));
		pnl_orderInfo.setLayout(new javax.swing.BoxLayout(pnl_orderInfo, javax.swing.BoxLayout.Y_AXIS));

		pnl_orderID.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
		pnl_orderID.setMaximumSize(new java.awt.Dimension(2147483647, 40));
		pnl_orderID.setPreferredSize(new java.awt.Dimension(561, 40));
		pnl_orderID.setLayout(new javax.swing.BoxLayout(pnl_orderID, javax.swing.BoxLayout.LINE_AXIS));

		lbl_brandID.setText("Mã thương hiệu: ");
		lbl_brandID.setPreferredSize(new java.awt.Dimension(130, 40));
		pnl_orderID.add(lbl_brandID);

		txt_brandID.setEnabled(false);
		txt_brandID.setPreferredSize(new java.awt.Dimension(64, 40));
		pnl_orderID.add(txt_brandID);

		pnl_orderInfo.add(pnl_orderID);

		pnl_orderDate.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
		pnl_orderDate.setMaximumSize(new java.awt.Dimension(2147483647, 40));
		pnl_orderDate.setPreferredSize(new java.awt.Dimension(561, 40));
		pnl_orderDate.setLayout(new javax.swing.BoxLayout(pnl_orderDate, javax.swing.BoxLayout.LINE_AXIS));

		lbl_brandName.setText("Tên thương hiệu: ");
		lbl_brandName.setPreferredSize(new java.awt.Dimension(130, 40));
		pnl_orderDate.add(lbl_brandName);

		txt_brandName.setPreferredSize(new java.awt.Dimension(64, 40));
		txt_brandName.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txt_brandNameActionPerformed(evt);
			}
		});
		pnl_orderDate.add(txt_brandName);

		pnl_orderInfo.add(pnl_orderDate);

		pnl_orderCustomerGive.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
		pnl_orderCustomerGive.setMaximumSize(new java.awt.Dimension(32767, 120));
		pnl_orderCustomerGive.setPreferredSize(new java.awt.Dimension(561, 100));
		pnl_orderCustomerGive.setLayout(new javax.swing.BoxLayout(pnl_orderCustomerGive, javax.swing.BoxLayout.Y_AXIS));

		pnl_container.setLayout(new java.awt.GridLayout(1, 0));

		lbl_country.setText("Nơi sản xuất:");
		lbl_country.setToolTipText("");
		lbl_country.setPreferredSize(new java.awt.Dimension(130, 40));
		pnl_container.add(lbl_country);

		pnl_orderCustomerGive.add(pnl_container);

		txa_country.setColumns(20);
		txa_country.setRows(10);
		txa_country.setTabSize(4);
		txa_country.setWrapStyleWord(true);
		txa_country.setMinimumSize(new java.awt.Dimension(13, 200));
		txa_country.setPreferredSize(new java.awt.Dimension(232, 200));
		scr_description.setViewportView(txa_country);

		pnl_orderCustomerGive.add(scr_description);

		pnl_orderInfo.add(pnl_orderCustomerGive);

		pnl_info.add(pnl_orderInfo);

		pnl_right.add(pnl_info, java.awt.BorderLayout.CENTER);

		pnl_btnGroup.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
		pnl_btnGroup.setPreferredSize(new java.awt.Dimension(281, 60));
		pnl_btnGroup.setLayout(new java.awt.GridLayout(1, 2, 5, 5));

		btn_clear.setText("Xóa trắng");
		btn_clear.setIcon(SVGIcon.getSVGIcon("resources/imgs/public/clear.svg"));
		btn_clear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_clearActionPerformed(evt);
			}
		});
		pnl_btnGroup.add(btn_clear);

		btn_update.setText("Cập nhật");
		btn_update.setToolTipText("");
		btn_update.setIcon(SVGIcon.getSVGIcon("resources/imgs/public/update.svg"));
		btn_update.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_updateActionPerformed(evt);
			}
		});
		pnl_btnGroup.add(btn_update);

		btn_create.setLabel("Thêm mới");
		btn_create.putClientProperty(FlatClientProperties.STYLE,
				"" + "background:$Menu.background;" + "foreground:$Menu.foreground;");
		btn_create.setIcon(SVGIcon.getPrimarySVGIcon("resources/imgs/public/add.svg"));
		btn_create.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_createActionPerformed(evt);
			}
		});
		pnl_btnGroup.add(btn_create);

		pnl_right.add(pnl_btnGroup, java.awt.BorderLayout.SOUTH);

		splitPane_main.setRightComponent(pnl_right);

		add(splitPane_main);
	}// </editor-fold>//GEN-END:initComponents

	private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_searchActionPerformed
		if (txt_search.getText().trim().length() > 0) {
			try {
				renderSupplierTable(bus.search(txt_search.getText()));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}// GEN-LAST:event_btn_searchActionPerformed

	private void btn_createActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_createActionPerformed
		if (txt_brandName.getText().trim().length() > 0) {
			if (txa_country.getText().trim().length() > 0) {
				if (txt_brandID.getText().trim().length() <= 0) {
					try {
						bus.create(txt_brandName.getText(), txa_country.getText());
						Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
								"Thêm thương hiệu thành công ");
						rerender();
					} catch (Exception ex) {
						Logger.getLogger(BrandManagement_GUI.class.getName()).log(Level.SEVERE, null, ex);
					}
				} else {
					Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
							"Mã thương hiệu đã tồn tại, hãy bấm nút huỷ để thêm mới !");
					return;
				}

			}
		} else {
			Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
					"Thêm thất bại !");
			return;
		}

	}// GEN-LAST:event_btn_createActionPerformed

	private void txt_searchKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txt_searchKeyPressed

	}// GEN-LAST:event_txt_searchKeyPressed

	private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_clearActionPerformed
		txt_search.setText("");
		txt_brandID.setText("");
		txt_brandName.setText("");
		txa_country.setText("");
		try {
			rerender();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Notifications.getInstance().show(Notifications.Type.INFO, "Đã xoá trắng");

	}// GEN-LAST:event_btn_clearActionPerformed

	private void txt_brandNameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txt_brandNameActionPerformed

	}// GEN-LAST:event_txt_brandNameActionPerformed

	private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btn_updateActionPerformed
		// TODO add your handling code here:

		if (txt_brandName.getText().trim().length() > 0) {
			if (txa_country.getText().trim().length() > 0) {
				if (txt_brandID.getText().trim().length() > 0) {
					try {
						Brand brand = new Brand(txt_brandID.getText(), txt_brandName.getText(), txa_country.getText());

						bus.update(brand, txt_brandID.getText());
						Notifications.getInstance().show(Notifications.Type.INFO, Notifications.Location.TOP_CENTER,
								"Cập nhật thành công");
						rerender();
					} catch (Exception ex) {
						Logger.getLogger(BrandManagement_GUI.class.getName()).log(Level.SEVERE, null, ex);
					}
				}

			}
		} else {
			Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER,
					"Cập nhật thất bại !");
			return;
		}
	}// GEN-LAST:event_btn_updateActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btn_clear;
	private javax.swing.JButton btn_create;
	private javax.swing.JButton btn_search;
	private javax.swing.JButton btn_update;
	private javax.swing.JLabel lbl_brandID;
	private javax.swing.JLabel lbl_brandName;
	private javax.swing.JLabel lbl_country;
	private javax.swing.JPanel pnl_btnGroup;
	private javax.swing.JPanel pnl_cart;
	private javax.swing.JPanel pnl_container;
	private javax.swing.JPanel pnl_header;
	private javax.swing.JPanel pnl_info;
	private javax.swing.JPanel pnl_left;
	private javax.swing.JPanel pnl_orderCustomerGive;
	private javax.swing.JPanel pnl_orderDate;
	private javax.swing.JPanel pnl_orderID;
	private javax.swing.JPanel pnl_orderInfo;
	private javax.swing.JPanel pnl_right;
	private javax.swing.JScrollPane scr_cart;
	private javax.swing.JScrollPane scr_description;
	private javax.swing.JSplitPane splitPane_main;
	private javax.swing.JTable tbl_brand;
	private javax.swing.JTextArea txa_country;
	private javax.swing.JTextField txt_brandID;
	private javax.swing.JTextField txt_brandName;
	private javax.swing.JTextField txt_search;
	// End of variables declaration//GEN-END:variables
}
