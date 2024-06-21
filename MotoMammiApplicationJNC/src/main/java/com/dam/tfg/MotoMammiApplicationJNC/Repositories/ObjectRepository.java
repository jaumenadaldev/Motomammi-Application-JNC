package com.dam.tfg.MotoMammiApplicationJNC.Repositories;

import java.util.List;

public interface ObjectRepository<T> {
    public void storeList(List<T> t);

    public void store(T t);

    public List<T> retrieve(String pSource, String date);

    public T search(String codExternal, String codProv, String pSource);

    public List<T> searchList(String codExternal, String codProv);

    public T delete(int id);

    public T update(T t);
}