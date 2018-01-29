package com.example.zzbmi.readapp.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.zzbmi.readapp.db.ExcelBean;

import com.example.zzbmi.readapp.db.ExcelBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig excelBeanDaoConfig;

    private final ExcelBeanDao excelBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        excelBeanDaoConfig = daoConfigMap.get(ExcelBeanDao.class).clone();
        excelBeanDaoConfig.initIdentityScope(type);

        excelBeanDao = new ExcelBeanDao(excelBeanDaoConfig, this);

        registerDao(ExcelBean.class, excelBeanDao);
    }
    
    public void clear() {
        excelBeanDaoConfig.clearIdentityScope();
    }

    public ExcelBeanDao getExcelBeanDao() {
        return excelBeanDao;
    }

}
