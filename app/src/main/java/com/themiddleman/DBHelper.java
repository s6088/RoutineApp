package com.themiddleman;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "routine.db";
    public static String DB_PATH;

    private File dbFile;
    private Context context;
    private SQLiteDatabase myDataBase;

    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.context = context;
        dbFile = context.getDatabasePath(DB_NAME);
        DB_PATH = dbFile.toString();
    }

    public void copyDataBase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0)myOutput.write(buffer, 0, length);
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public  boolean checkDatabase (){
        return dbFile.exists();
    }


    public synchronized void openDataBase() throws SQLException {
        myDataBase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDatabase() {
        if(myDataBase != null) myDataBase.close();
        super.close();

    }


    public List<MyClass> getListClasses (int year, int semister, int day) {
        List<MyClass> classes = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM classes WHERE  year = ? AND semister = ? AND day = ? ORDER BY hr ASC, min ASC", new String[]{ year+"", semister+"", day+"" });

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyClass myClass = new MyClass();
            myClass.setId( cursor.getInt(0) );
            myClass.setCourse_name( cursor.getString(1) );
            myClass.setCourse_teacher( cursor.getInt(2) );
            myClass.setRoom( cursor.getString(3) );
            myClass.setYear( cursor.getInt(4) );
            myClass.setSemister( cursor.getInt(5) );
            myClass.setDay( cursor.getInt(6) );
            myClass.setHr( cursor.getInt(7) );
            myClass.setMin( cursor.getInt(8) );
            if(myClass!=null)classes.add(myClass);
            cursor.moveToNext();
        }
        cursor.close();
        return classes;
    }

//    public int getPlacesByName(String name) {
//        int ans = -1;
//        Cursor cursor = myDataBase.rawQuery("SELECT id FROM places WHERE name = ?", new String[]{name});
//        if(cursor.getCount() < 1)return -1;
//        cursor.moveToFirst();
//        ans = cursor.getInt(0);
//        cursor.close();
//        return ans;
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
