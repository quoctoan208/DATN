package com.example.datn.Model;

public class ChiTietDonHang {
    private String id;
    private int soLuong;
    private float tongTienThanhToan;
    private String maSP;
    private String maDH;

    public ChiTietDonHang(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getTongTienThanhToan() {
        return tongTienThanhToan;
    }

    public void setTongTienThanhToan(float tongTienThanhToan) {
        this.tongTienThanhToan = tongTienThanhToan;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public ChiTietDonHang(String id, int soLuong, float tongTienThanhToan, String maSP, String maDH) {
        this.id = id;
        this.soLuong = soLuong;
        this.tongTienThanhToan = tongTienThanhToan;
        this.maSP = maSP;
        this.maDH = maDH;
    }
}
