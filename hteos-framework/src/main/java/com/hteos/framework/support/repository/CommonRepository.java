package com.hteos.framework.support.repository;

import java.util.List;

import com.hteos.framework.support.bean.PageResult;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hteos.framework.support.entity.BaseEntity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class CommonRepository {

	private SessionFactory sessionFactory;

	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory factory) {
		if(factory.unwrap(SessionFactory.class) == null){
			throw new NullPointerException("factory is not a hibernate factory");
		}
		this.sessionFactory = factory.unwrap(SessionFactory.class);
	}


	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(BaseEntity record) {
		this.getSession().save(record);
	}

	public <T> T load(Class<?> clazz, String id) {
		return (T) this.getSession().load(clazz, id);
	}

	public <T extends BaseEntity> T get(Class<?> clazz, String id) {
		return (T) this.getSession().get(clazz, id);
	}
	
	public void saveOrUpdate(BaseEntity record) {
		this.getSession().saveOrUpdate(record);
	}

	public void update(BaseEntity record) {
		this.getSession().update(record);
	}

	public void delete(BaseEntity record) {
		this.getSession().delete(record);
	}

	public <T> List<T> find(String hql, Object... parameters) {
		Query query = this.getSession().createQuery(hql);
		int i = 0;
		for (Object object : parameters) {
			query.setParameter(i, object);
			i++;
		}
		return query.list();
	}

	public <T> List<T> find(String hql, Integer limit, Object... parameters) {
		Query query = this.getSession().createQuery(hql);
		int i = 0;
		for (Object object : parameters) {
			query.setParameter(i, object);
			i++;
		}
		query.setMaxResults(limit);
		return query.list();
	}

	public <T> T unique(String hql, Object... parameters) {
		this.getSession().createCriteria(getClass());
		Query query = this.getSession().createQuery(hql);
		int i = 0;
		for (Object object : parameters) {
			query.setParameter(i, object);
			i++;
		}
		return (T) query.uniqueResult();
	}

	public void execute(String hql, Object... parameters) {
		Query query = this.getSession().createQuery(hql);
		int i = 0;
		for (Object object : parameters) {
			query.setParameter(i, object);
			i++;
		}
		query.executeUpdate();
	}

	public <T> List<T> find(Class<T> clazz) {
        CriteriaQuery<T> criteriaQuery = this.getSession().getCriteriaBuilder().createQuery(clazz);
        return this.getSession().createQuery(criteriaQuery).getResultList();
	}

	public <T> PageResult<T> findByPage(DetachedCriteria dc, int size, int page) {
		PageResult<T> pageResult = new PageResult<T>();
		Criteria criteria = dc.getExecutableCriteria(getSession());
		//先把Projection和OrderBy条件取出来,清空两者来执行Count操作
		CriteriaImpl impl = (CriteriaImpl) criteria;
		Projection projection = impl.getProjection();  
		Long total = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		criteria.setProjection(projection);
		criteria.setFirstResult((page - 1) * size);
		criteria.setMaxResults(size);
		List<T> list = criteria.list();
		pageResult.setRows(list);
		pageResult.setTotal(total);
		return pageResult;
	}

	public Long count(Criteria criteria) {
		Long total = (Long) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
		return total;
	}

	public <T> T unique(Class<?> clazz, String pro, String value) {
		Criteria criteria = this.getSession().createCriteria(clazz);
		criteria.add(Restrictions.eq(pro, value));
		return (T) criteria.uniqueResult();
	}

}
