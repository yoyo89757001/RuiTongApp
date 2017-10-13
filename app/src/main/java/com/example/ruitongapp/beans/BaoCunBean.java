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
    @Generated(hash = 1822532978)
    public BaoCunBean(@NotNull Long id, String zhanghao, String mima) {
        this.id = id;
        this.zhanghao = zhanghao;
        this.mima = mima;
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

}
