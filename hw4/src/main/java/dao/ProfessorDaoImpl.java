package dao;

import dao.inter.ProfessorDao;
import model.Professor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;
import java.util.Optional;


public class ProfessorDaoImpl extends GenericDaoImpl<Professor, String> implements ProfessorDao {


    public ProfessorDaoImpl(Class<Professor> type) {
        super(type);
    }


    @Override
    public Optional<Professor> readById(String id) {
        return super.readById(id);
    }

    @Override
    public boolean deleteById(String id) {
        return super.deleteById(id);
    }


    // n+1 solved using left join with criteria
    @Override
    public Optional<Professor> getById(String professorId) {
        HibernateUtil.init();
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Professor professor;
        Session session = sessionFactory.openSession();
        try (session) {

            Transaction tr = session.beginTransaction();
            professor = session.createQuery("select p from Professor p left join fetch p.professorCourses where p.id =:id", Professor.class)
                    .setParameter("id", professorId).getSingleResult();
            tr.commit();
        } catch (Exception e) {
            if (session.getTransaction().getStatus().canRollback()) {
                session.getTransaction().rollback();
            }
            return Optional.empty();

        }
        return Optional.ofNullable(professor);
    }
}