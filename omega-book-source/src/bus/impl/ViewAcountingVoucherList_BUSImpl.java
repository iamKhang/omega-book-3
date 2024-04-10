/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus.impl;

import dao.AcountingVoucher_DAO;
import dao.CashCountSheet_DAO;
import entity.AcountingVoucher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import bus.ViewAccoutingVoucherList_BUS;

/**
 *
 * @author Hoàng Khang
 */
public class ViewAcountingVoucherList_BUSImpl implements ViewAccoutingVoucherList_BUS{

    private AcountingVoucher_DAO acountingVoucher_DAO = new AcountingVoucher_DAO();
    private CashCountSheet_DAO cashCountSheet_DAO = new CashCountSheet_DAO();

    public AcountingVoucher getOne(String acountingVoucherID) {
        return acountingVoucher_DAO.getOne(acountingVoucherID);
    }

    public ArrayList<AcountingVoucher> getAll() {
        ArrayList<AcountingVoucher> list = new ArrayList<>();
        for (AcountingVoucher acountingVoucher : acountingVoucher_DAO.getAll()) {
            acountingVoucher.setCashCountSheet(cashCountSheet_DAO.getOne(acountingVoucher.getCashCountSheet().getCashCountSheetID()));
            list.add(acountingVoucher);
        }
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    public ArrayList<AcountingVoucher> getByDate(Date start, Date end) {
        ArrayList<AcountingVoucher> list = getAll();
        return list;
    }
}
