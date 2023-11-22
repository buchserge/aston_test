package dao.inter;

import java.util.Optional;

public interface GenericDao<T, PK> {

    boolean save(T t);

    Optional<T> readById(PK id);

    boolean update(T o);

    boolean deleteById(PK id);

    boolean delete(T t);
}
