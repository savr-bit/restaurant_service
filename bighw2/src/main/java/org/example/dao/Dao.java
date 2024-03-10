package org.example.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    public void save(T obj);

    public void update(T obj);

    public T findById(Integer Id);

    public List<T> getAll();

    public void delete(T obj);

}
