package com.yajun.green.repository;

import com.yajun.green.common.domain.EntityBase;
import com.yajun.green.common.utils.CHListUtils;
import com.yajun.green.common.utils.CHStringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:24
 */
public class HibernateEntityObjectDao extends HibernateDaoSupport implements EntityObjectDao {

    @Autowired
    public final void setEntityManagerFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public void saveOrUpdate(EntityBase entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void persist(EntityBase entity) {
        getHibernateTemplate().persist(entity);
        getHibernateTemplate().flush();
    }

    public void delete(EntityBase entity) {
        getHibernateTemplate().delete(entity);
        getHibernateTemplate().flush();
    }


    @SuppressWarnings("unchecked")
	public <T extends EntityBase> EntityBase findByUuid(String uuid, Class<T> clazz) {
        List<T> list = getHibernateTemplate().find("from " + clazz.getName() + " where uuid = ?", new Object[]{uuid});
        return CHListUtils.hasElement(list) ? list.get(0) : null;
    }

    public <T extends EntityBase> List<T> findByUuids(String[] uuids, Class<T> clazz) {
        if (uuids.length > 0) {
            return getHibernateTemplate().find("from " + clazz.getName() + " do where do.uuid in (" + CHStringUtils.convertListToSQLIn(uuids) + ")");
        }
        return new ArrayList<T>();
    }

    public void saveAll(List list) {
        getHibernateTemplate().saveOrUpdateAll(list);
    }

    public <T extends EntityBase> List<T> findByAll( Class<T> clazz) {
        return getHibernateTemplate().find("from " + clazz.getName());
    }
}
