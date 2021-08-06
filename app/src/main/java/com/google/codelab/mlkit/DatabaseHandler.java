package com.google.codelab.mlkit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE="CREATE TABLE "+Util.TABLE_NAME+"("+Util.KEY_ID+" INTEGER PRIMARY KEY,"+Util.KEY_OFFICIAL_ID+" INTEGER,"+
                Util.KEY_FNAME+" TEXT,"+Util.KEY_LNAME+" TEXT,"+Util.KEY_EMAIL+" TEXT UNIQUE,"+Util.KEY_SEC_QUE_ID+" INTEGER,"+Util.KEY_SEC_QUE+" TEXT,"+Util.KEY_PASSWORD+" TEXT" +")";
        //create table tablename(id integer primary key,offid integer ,fname text,lname text,email text unique,secQUE text,password text);

        String CREATE_OFFENDER_TABLE="CREATE TABLE "+Util.TABLE_NAME2+"("+Util.OFF_ID+" INTEGER PRIMARY KEY,"+
                Util.OFF_FNAME+" TEXT,"+Util.OFF_LNAME+" TEXT,"+Util.OFF_CNUM+ " TEXT,"+Util.OFF_STATE+" TEXT,"+Util.OFF_NUMPLA+" TEXT,"+Util.OFF_EMAIL+" TEXT UNIQUE" +")";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_OFFENDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE = String.valueOf(R.string.db_drop);

        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});
        db.execSQL("DROP TABLE IF EXISTS OFFENDERS");
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.KEY_OFFICIAL_ID,user.getOffID());
        contentValues.put(Util.KEY_FNAME,user.getFname());
        contentValues.put(Util.KEY_LNAME,user.getLname());
        contentValues.put(Util.KEY_EMAIL,user.getEmailId());
        contentValues.put(Util.KEY_SEC_QUE_ID,user.getSecQue());
        contentValues.put(Util.KEY_SEC_QUE,user.getSecQue());
        contentValues.put(Util.KEY_PASSWORD,user.getPwd());

        db.insert(Util.TABLE_NAME,null,contentValues);
        db.close();
    }

    public void addOffender(Offender off){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(Util.OFF_FNAME,off.getOf_fname());
        contentValues.put(Util.OFF_LNAME,off.getOf_lname());
        contentValues.put(Util.OFF_FNAME,off.getOf_fname());
        contentValues.put(Util.OFF_CNUM,off.getOf_carnum());
        contentValues.put(Util.OFF_STATE,off.getOf_state());
        contentValues.put(Util.OFF_NUMPLA,off.getOf_numplate());
        contentValues.put(Util.OFF_EMAIL,off.getOf_mail());

        db.insert(Util.TABLE_NAME2,null,contentValues);
        db.close();
    }

    public User getUser(int id){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Util.TABLE_NAME,new String[]{Util.KEY_EMAIL,Util.KEY_PASSWORD,Util.KEY_FNAME},
                Util.KEY_ID+"=?",new String[]{String.valueOf(id)},
                null,null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        User user=new User();
        assert cursor != null;
        user.setEmailId(cursor.getString(0));
        user.setPwd(cursor.getString(1));
        user.setFname(cursor.getString(2));

        return user;
    }

    public User getUserByEmail(String mail){
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Util.TABLE_NAME,new String[]{Util.KEY_FNAME,Util.KEY_LNAME},
                Util.KEY_ID+"=?",new String[]{mail},
                null,null,null,null);

        if(cursor!=null){
            cursor.moveToFirst();
        }

        User user=new User();
        assert cursor != null;
        user.setFname(cursor.getString(0));
        user.setLname(cursor.getString(1));
        user.setEmailId(mail);

        return user;
    }

    public List<User> getAllCustomers(){
        List<User> customerList=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();

        String selectAll="SELECT * FROM "+Util.TABLE_NAME;
        Cursor cursor=db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            do{
                User user=new User();
                user.setOffID(cursor.getInt(1));
                user.setFname(cursor.getString(2));
                user.setLname(cursor.getString(3));
                user.setEmailId(cursor.getString(4));
                user.setSecQueID(cursor.getInt(5));
                user.setSecQue(cursor.getString(6));
                user.setPwd(cursor.getString(7));

                customerList.add(user);
            }while (cursor.moveToNext());
        }
        return customerList;
    }

    public List<Offender> getAllOffender(){
        List<Offender> offenderList=new ArrayList<>();

        SQLiteDatabase db=this.getReadableDatabase();

        String selectAll="SELECT * FROM "+Util.TABLE_NAME2;
        Cursor cursor=db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            do{
                Offender offender=new Offender();
                offender.setOf_fname(cursor.getString(1));
                offender.setOf_lname(cursor.getString(2));
                offender.setOf_carnum(cursor.getString(3));
                offender.setOf_state(cursor.getString(4));
                offender.setOf_numplate(cursor.getString(5));
                offender.setOf_mail(cursor.getString(6));

                offenderList.add(offender);
            }while (cursor.moveToNext());
        }
        return offenderList;

//        "CREATE TABLE "+Util.TABLE_NAME2+"("+Util.OFF_ID+" INTEGER PRIMARY KEY,"+
//                Util.OFF_FNAME+" TEXT,"+Util.OFF_LNAME+" TEXT,"+Util.OFF_CNUM+ " TEXT,"+Util.OFF_STATE+" TEXT,"+Util.OFF_NUMPLA+" TEXT,"+Util.KEY_EMAIL+" TEXT UNIQUE" +")"
    }

    public void changeUserPassword(String Email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(Util.KEY_PASSWORD,password);
        db.update(Util.TABLE_NAME,cv,Util.KEY_EMAIL+"=?",new String[]{Email});
    }
    public void deleteRecord(User customer){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Util.TABLE_NAME,Util.KEY_ID+"=?",new String[]{String.valueOf(customer.getId())});
        db.close();
    }

    public void deleteAllOffenders(){
        SQLiteDatabase db = this. getWritableDatabase();
        db. execSQL("DELETE FROM "+Util.TABLE_NAME2);
    }


}
