/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Model.ThanhToan;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hoangdang
 */
public class ThanhToanDAO {
    public void LuuThongTinVeVoDB(ThanhToan tt)throws SQLException{
            String macb = tt.getMaCBString();
            int sv = tt.getSove();
            int loaiVe = tt.getLoaiVeInt();
            double tienve = tt.getTienve();
            String tenhk = tt.getKhachhangString();
            String MaKhachHang = tt.getMaKhachHang();
            String sdt = tt.getSdt();
            String email = tt.getEmail();
            String sql = "exec insertVe "+"'"+macb+"'"+","+tt.getSove()+","+tt.getsoveTE()+","+loaiVe+",N'"+tenhk+"','"+tt.getMaKhachHang()+"','"+tt.getSdt()+"','"+tt.getEmail()+"',"+tt.getHanhLy()+","+tt.getgiaNL()+","+tt.getgiaTE();
            System.out.println(sql);
            try{
            int rs;
            rs = DBConnect.dbExcuteUpdate(sql);
   
            }catch (SQLException e){
                System.err.println("Can't insert ticket!");
            }
    }
    
}
