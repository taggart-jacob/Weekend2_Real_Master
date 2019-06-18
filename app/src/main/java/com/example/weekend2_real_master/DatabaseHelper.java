package com.example.weekend2_real_master;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static com.example.weekend2_real_master.DatabaseContract.DATABASE_NAME;
import static com.example.weekend2_real_master.DatabaseContract.FIELD_AGE;
import static com.example.weekend2_real_master.DatabaseContract.FIELD_NAME;
import static com.example.weekend2_real_master.DatabaseContract.FIELD_PROFESSION;
import static com.example.weekend2_real_master.DatabaseContract.TABLE_NAME;
import static com.example.weekend2_real_master.DatabaseContract.whereClauseForUpdate;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseContract.getCreateTableStatement());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //insert values into db
    public void insertCeleb(Celebrity celebrityInsert){
        //create content value which holds key value pairs, key
        //being the column in the db, and value being the value associated with that column
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAME, celebrityInsert.getName());
        contentValues.put(FIELD_AGE, celebrityInsert.getAge());
        contentValues.put(FIELD_PROFESSION, celebrityInsert.getProfession());
        //logs
        Log.d(TAG, "updateMusic: Adding " + celebrityInsert + " to " + TABLE_NAME);
        //Need to get a writable db
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();

        //insert into the database
        writeableDatabase.insert(TABLE_NAME, null, contentValues);
        writeableDatabase.close();
    }

    //queries for all Celebrity records in the table
    public ArrayList<Celebrity> queryForAllCelebRecords(){
        //the list to return is initialized
        ArrayList<Celebrity> returnCelebrityList = null;
        //assigns and initializes a readableDB
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        //initializes and assigns a cursor to reflect all celebs
        Cursor cursor = readableDatabase.rawQuery(DatabaseContract.getSelectAllCelebItems(), null);
        //if the cursor finds any matches (it definitely will unless the whole table gets deleted
        if (cursor.moveToFirst()){
            //instantiates the above initialized celebList as a new arrayList
            returnCelebrityList = new ArrayList<>();
            //assigns variables to the values from the tables
            do {
                Celebrity returnCelebrity;
                String nameFromDB = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
                String ageFromDB = cursor.getString(cursor.getColumnIndex(FIELD_AGE));
                String professionFromDB = cursor.getString(cursor.getColumnIndex(FIELD_PROFESSION));
                returnCelebrity = new Celebrity(nameFromDB, ageFromDB, professionFromDB);
                returnCelebrityList.add(returnCelebrity);
            } while(cursor.moveToNext());  //while the cursor continues to find more entries
        }
        //closes the database readable to prevent data leaks
        readableDatabase.close();
        //returns the list of celebrities
        return returnCelebrityList;
    }

    public Celebrity queryForOneMusicRecord(String name){
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        Celebrity returnCeleb = null;

        Cursor cursor = readableDatabase.rawQuery(DatabaseContract.getSelectOneCelebItem(name), null);

        if (cursor.moveToFirst()){
            String nameFromDB = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
            String ageFromDB = cursor.getString(cursor.getColumnIndex(FIELD_AGE));
            String professionFromDB = cursor.getString(cursor.getColumnIndex(FIELD_PROFESSION));
            returnCeleb = new Celebrity(nameFromDB, ageFromDB, professionFromDB);
        }

        readableDatabase.close();
        return returnCeleb;
    }

    //updates the database when a new celebrity object is added or when a row is changed in some manner
    public void updateCelebrity(Celebrity celeb){
        //gets the writeable database and instantiates it
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //initializes the change of field(s)
        contentValues.put(FIELD_NAME, celeb.getName());
        contentValues.put(FIELD_AGE, celeb.getAge());
        contentValues.put(FIELD_PROFESSION, celeb.getProfession());

        //updates by bringing in the content values retrieved above, checking to see where an update is so
        //it doesn't update anything not being asked to be updated
        writableDatabase.update(TABLE_NAME, contentValues, whereClauseForUpdate(celeb.getName()), null);
        writableDatabase.close();
    }

    public void deleteCelebrity(String name){
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.delete(TABLE_NAME, whereClauseForUpdate(name), null);
        writableDatabase.close();
    }
}
