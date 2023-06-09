package com.example.datn.Model;

public class TaiKhoan {
    private int sDT;
    private String matKhau;
    private String hoVaTen;
    private String diaChi;
    private String gioiTinh;
    private int maLop;
    private int maSV;
    private float danhGia;

    public TaiKhoan(){}

    public int getsDT() {
        return sDT;
    }

    public void setsDT(int sDT) {
        this.sDT = sDT;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getMaLop() {
        return maLop;
    }

    public void setMaLop(int maLop) {
        this.maLop = maLop;
    }

    public int getMaSV() {
        return maSV;
    }

    public void setMaSV(int maSV) {
        this.maSV = maSV;
    }

    public float getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(float danhGia) {
        this.danhGia = danhGia;
    }

    public TaiKhoan(int sDT, String matKhau, String hoVaTen, String diaChi, String gioiTinh, int maLop, int maSV, float danhGia) {
        this.sDT = sDT;
        this.matKhau = matKhau;
        this.hoVaTen = hoVaTen;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.maLop = maLop;
        this.maSV = maSV;
        this.danhGia = danhGia;
    }
}
