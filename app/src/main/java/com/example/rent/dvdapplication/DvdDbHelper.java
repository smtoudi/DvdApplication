package com.example.rent.dvdapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by RENT on 2017-02-13.
 */

public class DvdDbHelper extends OrmLiteSqliteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "dvd_db.db";
    private Dao<DvdEntity,Long> dvdDao;



    private void addStartDataToDb(){

        DvdEntity d1 = new DvdEntity("Shawshank Redemption",1994,"drama","https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1_SY1000_CR0,0,672,1000_AL_.jpg",1,"bvfhjks bla bla vgfjsljfkdljsnlkjfd bla");
        DvdEntity d2 = new DvdEntity("Bladerunner",1982,"Sci-fi","http://film.org.pl/wp-content/uploads/2013/11/01090935.jpeg",1,"bvfhjks bla bla vgfjsljfkdljsnlkjfd bla");
        DvdEntity d3 = new DvdEntity("A Clockwork Orange",1971,"Sci-fi","https://i.guim.co.uk/img/media/ac707ddab36726ac4a185b5d64a8b7df867db9b5/0_0_1530_2025/master/1530.jpg?w=300&q=55&auto=format&usm=12&fit=max&s=2151f843879a76918c5488e1a1cd0493",3,"bvfhjks bla bla vgfjsljfkdljsnlkjfd bla");

        try {
            getDvdEntityDao();
            dvdDao.create(d1);
            dvdDao.create(d2);
            dvdDao.create(d3);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public DvdDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource,DvdEntity.class);
            addStartDataToDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource,DvdEntity.class,true);
            onCreate(database);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<DvdEntity,Long> getDvdEntityDao(){
        if (dvdDao==null){
            try {
                dvdDao =getDao(DvdEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return dvdDao;
    }
}
