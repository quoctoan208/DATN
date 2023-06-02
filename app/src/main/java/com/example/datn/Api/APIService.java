package com.example.datn.Api;

import com.example.datn.Model.AnhSP;
import com.example.datn.Model.DonHang;
import com.example.datn.Model.GioHang;
import com.example.datn.Model.NguoiDung;
import com.example.datn.Model.SanPham;
import com.example.datn.Model.TheLoai;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface APIService {

    Gson gson = new GsonBuilder().setLenient().setDateFormat("dd/MM/yyyy").create();
    APIService apiService = new Retrofit.Builder().baseUrl("https://newbrasshen62.conveyor.cloud")
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST("/api/tbl_TAIKHOAN")
    Call<NguoiDung> PostNGUOIDUNG(@Body NguoiDung nguoiDung);
   @GET("/api/tbl_TAIKHOAN/{id}")
    Call<NguoiDung> GetNGUOIDUNG(@Path("id") int id);
    @GET("/api/tbl_TAIKHOAN/{id}")
    Call<List<NguoiDung>> GetNGUOIDUNGDONHANG(@Path("id") int id);

    @GET("/api/tbl_TAIKHOAN/KiemTra/{masv}/{matkhau}")
    Call<String> Kiemtra(@Path("masv") int masv, @Path("matkhau") String matkhau);


    @GET("/api/tbl_SANPHAM")
    Call<List<SanPham>> getSanPham();

    @GET("/api/tbl_SANPHAM_XETDUYET")
    Call<List<SanPham>> getSanPhamxetduyet();

    @GET("/api/tbl_SANPHAM/{id}")
    Call<SanPham> GetSANPHAM(@Path("id") String id);

    @GET("/api/tbl_SANPHAM/SPTL/{mtl}")
    Call<List<SanPham>> SPTL(@Path("mtl") String mtl);

    @GET("/api/tbl_SANPHAM/TimKiemSP/{Tensp}")
    Call<List<SanPham>> TimKiemSP(@Path("Tensp") String Tensp);

    @GET("/api/tbl_ANHSP/{id}")
    Call<AnhSP> GetAnhSP(@Path("id") String id);

    @POST("/api/tbl_SANPHAM")
    Call<Integer>PostSANPHAM(@Body SanPham sanPham);
    @POST("/api/tbl_ANHSP")
    Call<List<AnhSP>>PostANHSANPHAM(@Body AnhSP anhSP);

    @PUT("/api/tbl_SANPHAM/{id}")
    Call<SanPham> putSANPHAM(@Path("id") String id, @Body SanPham sanPham);
    @PUT("/api/tbl_ANHSP/{id}")
    Call<AnhSP> putANHSANPHAM(@Path("id") String id, @Body AnhSP anhSP);
    @GET("/api/tbl_THELOAI")
    Call<List<TheLoai>> getTheLoai();

    @GET("/api/tbl_GIOHANG/{masv}")
    Call<List<GioHang>> Getgiohang(@Path("masv") int maSV);

    @POST("/api/tbl_GIOHANG")
    Call<List<GioHang>> PostGIOHANG(@Body GioHang GioHang);

    @DELETE("/api/tbl_GIOHANG/{id}")
    Call<GioHang> DeleteGIOHANG(@Path("id") int id);
    @PUT("/api/tbl_GIOHANG/{id}")
    Call<GioHang> PutGIOHANG(@Path("id") int id, @Body GioHang GioHang);

//    @POST("api/CHITIETDONHANGs")
//    Call<ChiTietDonHang>PostCHITIETDONHANG(@Body ChiTietDonHang ChiTietDonHang);
    @POST("/api/DONHANGs")
    Call<DonHang>PostDONHANG(@Body DonHang donHang);
    @PUT("/api/DONHANGs/{id}")
    Call<List<DonHang>> PutDONHANG(@Path("id") int id, @Body DonHang donHang);
    @GET("/api/DONHANGs/{usename}/{check}")
    Call<List<DonHang>>Getalldonhang(@Path("usename")int usename,@Path("check")int check);
}