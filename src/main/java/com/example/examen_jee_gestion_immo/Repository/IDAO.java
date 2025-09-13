package com.example.examen_jee_gestion_immo.Repository;

import java.util.List;

public interface IDAO <T >{

    List<T>getAll();
    T getById(int id);
    void add(T t);
    void update(T t);
    void delete(int id);
    List<T> search(String libelle);
}
