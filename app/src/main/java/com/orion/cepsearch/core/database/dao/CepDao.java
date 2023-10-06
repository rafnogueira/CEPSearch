package com.orion.cepsearch.core.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.orion.cepsearch.core.model.local.Cep;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface CepDao {

    @Insert
    Completable insert(Cep cep);

    @Query("SELECT * FROM cep")
    Single<List<Cep>> getAllCep();

    @Delete
    Completable deleteCep(Cep cep);

}
