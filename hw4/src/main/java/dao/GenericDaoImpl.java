package dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public abstract class GenericDaoImpl<T, PK> implements dao.inter.GenericDao<T, PK> {

    private final SessionFactory sessionFactory;
    private final Class<T> type;

    GenericDaoImpl(Class<T> type) {
        this.type = type;
        HibernateUtil.init();
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }


    public Set<T> getAll() {
        Session session = getSession();
        try (session) {
            Transaction tr = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cr = cb.createQuery(type);
            Root<T> root = cr.from(type);
            cr.select(root);
            Query<T> query = session.createQuery(cr);
            List<T> resultList = query.getResultList();
            tr.commit();
            return new HashSet<>(resultList);
        }catch(Exception e){
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
        return null;
        }
    }

    @Override
    public boolean save(T t) {
        Session session = getSession();
        try (session) {
            Transaction tr = session.beginTransaction();
            session.persist(t);
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return false;
        }
        return true;
    }

    @Override
    public Optional<T> readById(PK id) {
        T t;
        Session session = getSession();
        try (session) {
            Transaction tr = session.beginTransaction();
            t = session.get(type, id);
            tr.commit();
        }
        return Optional.ofNullable(t);
    }

    public boolean update(T o) {
        Session session = getSession();
        try (session) {
            Transaction tr = session.beginTransaction();
            session.merge(o);
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return false;
        }
        return true;

    }

    @Override
    public boolean deleteById(PK id) {
        Session session = getSession();
        try (session) {
            Transaction tr = session.getTransaction();
            tr.begin();
            T t = session.get(type, id);
            session.remove(t);
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return false;
        }
        return true;

    }

    @Override
    public boolean delete(T t) {

        Session session = getSession();
        try (session) {
            Transaction tr = session.getTransaction();
            tr.begin();
            session.remove(t);
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return false;
        }
        return true;

    }
}
