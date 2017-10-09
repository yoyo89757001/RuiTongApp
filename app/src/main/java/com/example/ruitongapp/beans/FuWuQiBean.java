package com.example.ruitongapp.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/10/9.
 */

@Entity
public class FuWuQiBean {
    @Id
    @NotNull
    private Long id;
    private String dizhi;
    private boolean isTrue;
    @Generated(hash = 1738880655)
    public FuWuQiBean(@NotNull Long id, String dizhi, boolean isTrue) {
        this.id = id;
        this.dizhi = dizhi;
        this.isTrue = isTrue;
    }
    @Generated(hash = 520302377)
    public FuWuQiBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDizhi() {
        return this.dizhi;
    }
    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }
    public boolean getIsTrue() {
        return this.isTrue;
    }
    public void setIsTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }

}
