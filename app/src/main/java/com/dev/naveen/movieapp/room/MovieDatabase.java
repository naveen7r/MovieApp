package com.dev.naveen.movieapp.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.dev.naveen.movieapp.dto.ResultsItem;

/**
 * Created by Naveen on 6/2/2018.
 */

@Database(entities = {ResultsItem.class,
        com.dev.naveen.movieapp.dto.trailers.ResultsItem.class,
        com.dev.naveen.movieapp.dto.reviews.ResultsItem.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess() ;

    private static MovieDatabase INSTANCE;

    static MovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movies_db")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}