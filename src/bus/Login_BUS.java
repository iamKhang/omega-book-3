
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bus;

import dao.Account_DAO;
import dao.Employee_DAO;
import entity.Account;
import entity.Employee;
import java.util.regex.Pattern;
import utilities.PasswordHash;

/**
 *
 * @author Hoàng Khang
 */
public class Login_BUS {

    private final Account_DAO accountDAO = new Account_DAO();
    private final Employee_DAO employeeDAO = new Employee_DAO();

    public Employee login(String id, String password) throws Exception {
        Account acc = accountDAO.getOne(id);
        if (!employeeDAO.getOne(id).isStatus()) {
            throw new Exception("Tài khoản đã bị vô hiệu hóa doa nhân viên đã thôi việc!");

        }
        if (acc == null) {
            throw new Exception("Tài khoản không tồn tại!");
        } else if (!PasswordHash.comparePasswords(password, acc.getPassWord())) {
            throw new Exception("Mật khẩu không chính xác!");
        } else {
            return employeeDAO.getOne(acc.getEmployee().getEmployeeID());
        }
    }

    public boolean changePassword(String id, String pass, String passNew) throws Exception {

//        System.out.println(PasswordHash.hashPassword(pass));
        pass = PasswordHash.hashPassword(pass);
        String passOld = accountDAO.getOne(id).getPassWord();
//        System.out.println("Pass csdl: "+passOld);
        if (!pass.equals(passOld)) {
            throw new Exception("Mật khẩu không chính xác");
        }
        Account acc = accountDAO.getOne(id);
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
        if (!Pattern.matches(regex, passNew)) {
            throw new Exception("Mật khẩu phải ít nhất có 8 kí tự, bao gồm chữ hoa, chữ thường và số");
        }
        String passNewHash = PasswordHash.hashPassword(passNew);
        if (acc == null) {
            throw new Exception("Tài khoản không tồn tại!");
        } else if (acc.getPassWord().equals(passNewHash)) {
            throw new Exception("Mật khẩu không chính xác!");
        }
        return accountDAO.updatePass(acc.getEmployee().getEmployeeID(), passNewHash);
    }

}
