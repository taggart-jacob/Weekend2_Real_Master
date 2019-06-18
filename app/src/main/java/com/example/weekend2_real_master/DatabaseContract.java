package com.example.weekend2_real_master;

import java.util.Locale;

public class DatabaseContract {
    public static final String DATABASE_NAME = "db_database_name";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "celebrity_table";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_AGE = "age";
    public static final String FIELD_PROFESSION = "profession";

    public static String getCreateTableStatement(){
        return String.format
                (Locale.US, "CREATE TABLE %s(%s TEXT PRIMARY_KEY, %s TEXT, %s TEXT)",
                        TABLE_NAME,FIELD_NAME, FIELD_AGE, FIELD_PROFESSION);
    }

    public static String getSelectAllCelebItems(){
        return String.format(Locale.US, "SELECT * FROM %s", TABLE_NAME);
    }

    public static String getSelectOneCelebItem(String name){
        return String.format(Locale.US, "SELECT * FROM %s WHERE %s = \"%s\"", TABLE_NAME, FIELD_NAME, name);
    }

    public static String whereClauseForUpdate(String name){
        return String.format(Locale.US, "WHERE %s = %s", FIELD_NAME, name);
    }

}
