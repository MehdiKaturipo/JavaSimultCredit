package ma.myBank.dao;

import java.util.List;

public interface IDao <T,ID>{
    T findById(ID id);
    List<T> findAll();
    T save(T t);
    T update(T t);
    Boolean delete(T t);
    Boolean deleteById(ID id);
}
