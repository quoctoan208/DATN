package com.example.datn.Model;

public class GioHang {
    int IDGIOHANG,maSV;
    String maSP;
    int soLuong,tongTien;

    public int getIDGIOHANG() {
        return IDGIOHANG;
    }

    public void setIDGIOHANG(int IDGIOHANG) {
        this.IDGIOHANG = IDGIOHANG;
    }

    public int getMaSV() {
        return maSV;
    }

    public void setMaSV(int maSV) {
        this.maSV = maSV;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public GioHang(int IDGIOHANG, int maSV, String maSP, int soLuong, int tongTien) {
        this.IDGIOHANG = IDGIOHANG;
        this.maSV = maSV;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }
}
