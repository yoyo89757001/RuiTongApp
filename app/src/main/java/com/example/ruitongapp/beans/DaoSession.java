package com.example.ruitongapp.beans;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.ruitongapp.beans.BaoCunBean;
import com.example.ruitongapp.beans.BenDiYuanGong;
import com.example.ruitongapp.beans.FuWuQiBean;

import com.example.ruitongapp.beans.BaoCunBeanDao;
import com.example.ruitongapp.beans.BenDiYuanGongDao;
import com.example.ruitongapp.beans.FuWuQiBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */

public class DaoSession extends AbstractDaoSession {

    private final DaoConfig baoCunBeanDaoConfig;
    private final DaoConfig benDiYuanGongDaoConfig;
    private final DaoConfig fuWuQiBeanDaoConfig;

    private final BaoCunBeanDao baoCunBeanDao;
    private final BenDiYuanGongDao benDiYuanGongDao;
    private final FuWuQiBeanDao fuWuQiBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        baoCunBeanDaoConfig = daoConfigMap.get(BaoCunBeanDao.class).clone();
        baoCunBeanDaoConfig.initIdentityScope(type);

        benDiYuanGongDaoConfig = daoConfigMap.get(BenDiYuanGongDao.class).clone();
        benDiYuanGongDaoConfig.initIdentityScope(type);

        fuWuQiBeanDaoConfig = daoConfigMap.get(FuWuQiBeanDao.class).clone();
        fuWuQiBeanDaoConfig.initIdentityScope(type);

        baoCunBeanDao = new BaoCunBeanDao(baoCunBeanDaoConfig, this);
        benDiYuanGongDao = new BenDiYuanGongDao(benDiYuanGongDaoConfig, this);
        fuWuQiBeanDao = new FuWuQiBeanDao(fuWuQiBeanDaoConfig, this);

        registerDao(BaoCunBean.class, baoCunBeanDao);
        registerDao(BenDiYuanGong.class, benDiYuanGongDao);
        registerDao(FuWuQiBean.class, fuWuQiBeanDao);
    }
    
    public void clear() {
        baoCunBeanDaoConfig.clearIdentityScope();
        benDiYuanGongDaoConfig.clearIdentityScope();
        fuWuQiBeanDaoConfig.clearIdentityScope();
    }

    public BaoCunBeanDao getBaoCunBeanDao() {
        return baoCunBeanDao;
    }

    public BenDiYuanGongDao getBenDiYuanGongDao() {
        return benDiYuanGongDao;
    }

    public FuWuQiBeanDao getFuWuQiBeanDao() {
        return fuWuQiBeanDao;
    }

}
