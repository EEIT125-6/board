package orange.dao;

import java.sql.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import orange.model.CommentBean;
import orange.util.HibernateUtils;



public class CommentDAO {

	SessionFactory factory = HibernateUtils.getSessionFactory();
	
	
	public CommentBean selectComment(String name) {
		CommentBean cb = null;
		Session session = factory.getCurrentSession();
		cb = session.get(CommentBean.class, name);
		return cb;
	}
	
	public int updateComment(CommentBean cb) {
		int count = 0;
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(cb);
		count++;
		return count;
		
		
	}
	
	public int deleteComment(String name) {
		String hql = "DELETE FROM Board WHERE name = :boardname";
		int result = 0;
		Session session=factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("boardname", name);
			result = query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw new RuntimeException(e);
		}
		return result;	

	}
	
	public int insertComment(CommentBean cb){
		int count =0;
		Session session = factory.getCurrentSession();
		session.save(cb);
		count++;
		
		return count;
		
		
		
		
		
	}
}

		
