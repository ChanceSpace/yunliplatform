package com.yajun.green.repository;

import com.yajun.green.common.domain.EntityBase;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:23
 */
public interface EntityObjectDao {

    void saveOrUpdate(EntityBase entity);

    void persist(EntityBase entity);

    void saveAll(List list);

    void delete(EntityBase entity);

    <T extends EntityBase> EntityBase findByUuid(String uuid, Class<T> clazz);

    <T extends EntityBase> List<T> findByUuids(String[] uuids, Class<T> clazz);

    <T extends EntityBase> List<T> findByAll(Class<T> clazz);
}
