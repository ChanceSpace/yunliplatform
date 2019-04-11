package com.yajun.green.repository;

import com.yajun.green.common.security.LoginInfo;
import com.yajun.green.security.SecurityUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:26
 */
@Repository("userDao")
public class UserDaoImpl extends HibernateEntityObjectDao implements UserDao {
}
