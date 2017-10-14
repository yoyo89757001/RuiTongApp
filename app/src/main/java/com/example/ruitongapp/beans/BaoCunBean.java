package com.example.ruitongapp.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/10/13.
 */
@Entity
public class BaoCunBean {
    @Id
    @NotNull
    private Long id;
    private String zhanghao;
    private String mima;
    private String sid;
    private String token;
    private String dizhi;
    @Generated(hash = 948468976)
    public BaoCunBean(@NotNull Long id, String zhanghao, String mima, String sid,
            String token, String dizhi) {
        this.id = id;
        this.zhanghao = zhanghao;
        this.mima = mima;
        this.sid = sid;
        this.token = token;
        this.dizhi = dizhi;
    }
    @Generated(hash = 1469853663)
    public BaoCunBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getZhanghao() {
        return this.zhanghao;
    }
    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }
    public String getMima() {
        return this.mima;
    }
    public void setMima(String mima) {
        this.mima = mima;
    }
    public String getSid() {
        return this.sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getDizhi() {
        return this.dizhi;
    }
    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }

    


}
