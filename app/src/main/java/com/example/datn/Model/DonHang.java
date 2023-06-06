package com.example.datn.Model;

import java.util.Date;

public class DonHang {

    private String diaChiGiaoHang;
    private String phuongThucThanhToan;
    private float tongTienThanhToan;
    private int trangThaiDH;
    private int maSVMua;
    private int maSVBan;
    private String maDH;
    private Date ngayGiaoDich;

    public DonHang(){}

    public String getDiaChiGiaoHang() {
        return diaChiGiaoHang;
    }

    public void setDiaChiGiaoHang(String diaChiGiaoHang) {
        this.diaChiGiaoHang = diaChiGiaoHang;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public float getTongTienThanhToan() {
        return tongTienThanhToan;
    }

    public void setTongTienThanhToan(float tongTienThanhToan) {
        this.tongTienThanhToan = tongTienThanhToan;
    }

    public int getTrangThaiDH() {
        return trangThaiDH;
    }

    public void setTrangThaiDH(int trangThaiDH) {
        this.trangThaiDH = trangThaiDH;
    }

    public int getMaSVMua() {
        return maSVMua;
    }

    public void setMaSVMua(int maSVMua) {
        this.maSVMua = maSVMua;
    }

    public int getMaSVBan() {
        return maSVBan;
    }

    public void setMaSVBan(int maSVBan) {
        this.maSVBan = maSVBan;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public DonHang(String diaChiGiaoHang, String phuongThucThanhToan, float tongTienThanhToan, int trangThaiDH, int maSVMua, int maSVBan, String maDH, Date ngayGiaoDich) {
        this.diaChiGiaoHang = diaChiGiaoHang;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tongTienThanhToan = tongTienThanhToan;
        this.trangThaiDH = trangThaiDH;
        this.maSVMua = maSVMua;
        this.maSVBan = maSVBan;
        this.maDH = maDH;
        this.ngayGiaoDich = ngayGiaoDich;
    }
}
