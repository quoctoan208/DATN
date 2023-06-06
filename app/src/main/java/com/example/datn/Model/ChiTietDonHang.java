package com.example.datn.Model;

public class ChiTietDonHang {
    private String tenSP;
    private float donGia;
    private int soLuong;
    private float tongTienThanhToan;
    private String maSP;
    private String maDH;

    public ChiTietDonHang(){}

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
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

    public ChiTietDonHang( String tenSP, float donGia, int soLuong, float tongTienThanhToan, String maSP, String maDH) {
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.tongTienThanhToan = tongTienThanhToan;
        this.maSP = maSP;
        this.maDH = maDH;
    }
}
