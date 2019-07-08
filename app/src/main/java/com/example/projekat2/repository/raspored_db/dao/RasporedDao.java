package com.example.projekat2.repository.raspored_db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.projekat2.repository.raspored_db.entity.RasporedEntity;

import java.util.List;

@Dao
public interface RasporedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRasporeds(List<RasporedEntity> rasporeds);

    @Query("DELETE FROM raspored")
    void deleteAll();

    @Query("SELECT * FROM raspored")
    LiveData<List<RasporedEntity>> getAllRasporeds();

    @Query("SELECT * FROM raspored " +
            " WHERE dan LIKE :dan || '%' " +
            "AND grupe LIKE '%' || :grupa || '%' "+
            "AND (predmet LIKE :predmet  || '%'  OR nastavnik LIKE :predmet  || '%')")
    LiveData<List<RasporedEntity>> getFilteredRasporeds(String predmet, String dan, String grupa);


    @Query("SELECT * FROM raspored " +
            "WHERE predmet LIKE :filter || '%' " +
            "OR nastavnik LIKE :filter || '%' " +
            "OR ucionica LIKE :filter")
    LiveData<List<RasporedEntity>> getFilteredRasporeds1(String filter);
}
