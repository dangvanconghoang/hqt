/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ChiTietVe;
import DAL.ChiTietVeDAO;
import Model.DatVeMayBay;
import DAL.DatVeMayBayDAO;
import DAL.SanBayDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import fxsampler.Sample;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author hoangdang
 */
public class DatVeMayBayController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox<String> cbBoxKhoiHanh;
    @FXML
    private JFXComboBox<String> cbBoxDiemDen;
    @FXML
    private JFXDatePicker dPNgayDi;
     @FXML
    private JFXDatePicker dPNgayVe;
    @FXML
    private JFXComboBox<Integer> cbBoxTreEm;
    @FXML
    private JFXComboBox<Integer> cbBoxNguoiLon;

    @FXML
    private JFXButton btnTimChuyenBay;
    
    ObservableList cbBoxSoLuongVeList = FXCollections.observableArrayList(0,1,2,3 );
    ObservableList cbBoxLoaiGheList = FXCollections.observableArrayList("Thương Gia","Phổ Thông" );
    ObservableList<String> listSanBays = FXCollections.observableArrayList();
    
    private DatVeMayBayDAO datVeDAO;
    private AnchorPane rootPane;

    @FXML
    public void handleTimChuyenBay(ActionEvent event) throws IOException{ 
            if ( cbBoxKhoiHanh.getValue() ==  null  ||  cbBoxDiemDen.getValue() ==  null ||  dPNgayDi.getValue() ==  null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi rồi ");
                alert.setContentText("phải điền dủ thông tin :) ");
                alert.showAndWait();
            }
            LocalDate now = LocalDate.now();
          if ( dPNgayDi.getValue().isBefore(now) ) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Lỗi rồi ");
              alert.setContentText("Ngày di khong hop le  :) ");
              alert.showAndWait();
          }
                if (  cbBoxKhoiHanh.getValue().equals(cbBoxDiemDen.getValue()) ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi rồi ");
                    alert.setContentText("Điểm  đi và điểm đến không được trùng nhau :) ");
                    alert.showAndWait();
            }
          else{
            DatVeMayBay dvmb = new DatVeMayBay();
            dvmb.setDiemKhoiHanh(cbBoxKhoiHanh.getSelectionModel().getSelectedItem());
            dvmb.setDiemDen(cbBoxDiemDen.getSelectionModel().getSelectedItem());
            dvmb.setNgay(dPNgayDi.getValue());
            dvmb.setSLNguoiLon(cbBoxNguoiLon.getValue());
            dvmb.setSLTreEM(cbBoxTreEm.getValue());
            dvmb.setLoaiVe(1);
              
            String DiemDi = dvmb.getDiemKhoiHanh();
            String DiemDen = dvmb.getDiemDen();
            LocalDate ngaydi = dvmb.getNgay();
            int LoaiVe = dvmb.getLoaiVe();
            int SoNL = dvmb.getSLNguoiLon();
            int SoTreE = dvmb.getSLTreEM();
            LocalDate ngayve = this.dPNgayVe.getValue();
            
      
     
            AnchorPane paneDanhSachChuyenBay = new AnchorPane();

      
            if (ngayve==null) {
                FXMLLoader fXMLLoader = MainController.getMainController().createPage(paneDanhSachChuyenBay, "/View/DanhSachChuyenBay.fxml");
                fXMLLoader.<DanhSachChuyenBayController>getController().ChuyenDuLieu(DiemDi, DiemDen, ngaydi, SoNL, SoTreE, false, null);
                GeneralFuntion.FitChildContent(paneDanhSachChuyenBay);

            } else {
               
                if ( ngayve.isBefore(ngaydi) ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi rồi ");
                    alert.setContentText("Ngày về phải lớn hơn này đi  :) ");
                    alert.showAndWait();
                }
//                LocalDate now = LocalDate.now();
//                if ( ngaydi.isBefore(now) ) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Lỗi rồi ");
//                    alert.setContentText("Ngày di khong hop le  :) ");
//                    alert.showAndWait();
//                }
                else {
                    FXMLLoader fXMLLoader = MainController.getMainController().createPage(paneDanhSachChuyenBay, "/View/DanhSachChuyenBay.fxml");
                    fXMLLoader.<DanhSachChuyenBayController>getController().ChuyenDuLieu(DiemDi, DiemDen, ngaydi, SoNL, SoTreE, false, ngayve);
                    GeneralFuntion.FitChildContent(paneDanhSachChuyenBay);
                }
            }
            
          }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbBoxNguoiLon.setItems(cbBoxSoLuongVeList);
        cbBoxTreEm.setItems(cbBoxSoLuongVeList);
        cbBoxNguoiLon.setPromptText("Người lớn");
        cbBoxTreEm.setPromptText("Trẻ em");
        try {
            LoadData();
        } catch (SQLException ex) {
            Logger.getLogger(DatVeMayBayController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void LoadData()throws SQLException{
        datVeDAO = new DatVeMayBayDAO();
        listSanBays = datVeDAO.getDiaDiemSanBay();
        cbBoxKhoiHanh.setItems(SanBayDAO.getThanhPho());
        cbBoxDiemDen.setItems(SanBayDAO.getThanhPho());
    }
    
   
  
}
