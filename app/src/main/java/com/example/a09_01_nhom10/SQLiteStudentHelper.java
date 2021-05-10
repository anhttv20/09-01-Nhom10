package com.example.a09_01_nhom10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.a09_01_nhom10.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SQLiteStudentHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="StudentDB.db";
    private static final int DATABSE_VERSION=1;

    public SQLiteStudentHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE student(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "gender BOOLEAN," +
                "mark REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    //add
    public void addStudent(Student s){
        String sql="INSERT INTO student(name,gender,mark) VALUES(?,?,?)";
        String[] args={s.getName(),
                       Boolean.toString(s.isGender()),
                       Double.toString(s.getMark())};
        SQLiteDatabase statement=getWritableDatabase();
        statement.execSQL(sql,args);
    }
    // get all
    public List<Student> getAll(){
        List<Student> list=new ArrayList<>();
        SQLiteDatabase statement=getReadableDatabase();
        Cursor rs=statement.query("student",null,
                null,null,null,
                null,null);
        while((rs!=null && rs.moveToNext())){
            int id=rs.getInt(0);
            String name=rs.getString(1);
            boolean g=rs.getString(2).equals("true");
            double m=rs.getDouble(3);
            list.add(new Student(id,name,g,m));
        }
        return list;
    }
    //getBy id
    public Student getStudentById(int id){
        String whereClause="id =?";
        String[] whereArgs={String.valueOf(id)};
        SQLiteDatabase st=getReadableDatabase();
        Cursor rs=st.query("student",null,whereClause,
                whereArgs,null,null,null);
        if(rs.moveToNext()){
            String n=rs.getString(1);
            boolean g=rs.getInt(2)==1;
            double m=rs.getDouble(3);
            return new Student(id,n,g,m);
        }
        return null;
    }
    //update
    public int update(Student s){
        ContentValues v=new ContentValues();
        v.put("name",s.getName());
        v.put("gender",s.isGender());
        v.put("mark",s.getMark());
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(s.getId())};
        SQLiteDatabase st=getWritableDatabase();
        return st.update("student",v,whereClause,whereArgs);
    }
    //delete
    public int delete(int id){
        String whereClause="id=?";
        String[] whereArgs={String.valueOf(id)};
        SQLiteDatabase st=getWritableDatabase();
        return st.delete("student",whereClause,whereArgs);
    }

}
