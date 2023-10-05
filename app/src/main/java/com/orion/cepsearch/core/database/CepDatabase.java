package com.orion.cepsearch.core.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.orion.cepsearch.core.database.dao.CepDao;
import com.orion.cepsearch.core.model.local.Cep;


@Database(entities = {Cep.class},version = 1, exportSchema = false)
public abstract class CepDatabase extends RoomDatabase {
    public abstract CepDao getCepDAo();


}

