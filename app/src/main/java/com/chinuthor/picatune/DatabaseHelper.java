package com.chinuthor.picatune;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //CONSTRUCTOR
    public DatabaseHelper(Context context) {
        super(context, DatabaseUtil.DB_NAME, null, DatabaseUtil.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //EXECUTE QUERY
        db.execSQL(DatabaseUtil.SQL_CREATE_USERS_TABLE);
        db.execSQL(DatabaseUtil.SQL_CREATE_USER_SONGS_TABLE);
        db.execSQL(DatabaseUtil.SQL_CREATE_LOGIN_HISTORY_TABLE);
        db.execSQL(DatabaseUtil.SQL_CREATE_PLAYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*//DBHELPER - FETCH A STUDENT IN THE DB TABLE
    public User LOGIN_USER(String username, String password){

        //INITIALIZE DATABASE OBJECT AS READABLE
        SQLiteDatabase db = getReadableDatabase();

        //PREPARE QUERY FOR FETCHING SINGLE STUDENT
        String getOne = "SELECT * FROM "+DatabaseUtil.USERS_TABLE_NAME+" WHERE "+DatabaseUtil.USERS_COLUMN_USERNAME+"=?";

        //PREPARE QUERY FOR FETCHING MULTIPLE STUDENT
        String getAll = "SELECT * FROM "+TABLE_NAME;

        //DECLARE CURSOR / STUDENT
        Cursor cursor = null;
        Student student = null;
        //DECLARE ARRAY LIST OF STUDENTS
        ArrayList<Student> studentList = null;

        //CONDITION : ID FOR STUDENT PASSED IN PARAMETERS : TRUE
        if(id>0){
            //RETURNED CURSOR OBJECT FROM DATABASE RAW QUERY EXECUTION
            cursor = db.rawQuery(getOne,new String[]{String.valueOf(id)});

            //CONDITION : CURSOR HAS ONE ROW : TRUE
            if(cursor.getCount()==1){

                //STUDENT / STUDENT LIST OBJECT INITIALIZED
                student = new Student();
                studentList = new ArrayList<Student>();

                //PREPARE CURSOR ON RECORD
                cursor.moveToNext();
                //EXTRACT FIELDS FROM CURSOR TO OBJECT
                student.firstName = cursor.getString(1);
                student.lastName = cursor.getString(2);
                student.grade = cursor.getDouble(3);
                student.idNumber = cursor.getString(0);
                //ADD STUDENT OBJECT TO STUDENT LIST
                studentList.add(student);
            }
        }
        else{//CONDITION : ID FOR STUDENT PASSED IN PARAMETERS : FALSE
            //RETURNED CURSOR OBJECT FROM DATABASE RAW QUERY EXECUTION
            cursor = db.rawQuery(getAll,null);
            //CONDITION : CURSOR CONSIST OF 1 OR MORE RECORDS : TRUE
            if(cursor.getCount()>0){
                //PREPARE CURSOR ON RECORD
                cursor.moveToFirst();
                //INITIALIZE STUDENT LIST
                studentList = new ArrayList<Student>();
                do{//LOOP THROUGH CURSOR RECORDS AND ADD EACH STUDENT TO STUDENT LIST
                    studentList.add(new Student(cursor.getString(1),cursor.getString(2),cursor.getDouble(3),cursor.getString(0)));
                }while(cursor.moveToNext());
            }
        }
        //CLOSE CURSOR
        cursor.close();
        //RETURN STUDENT LIST
        return studentList;
    }*/

    /*//DBHELPER - ADD A STUDENT IN THE DB TABLE
    public Student addStudent(Student student){

        //INITIALIZE DATABASE OBJECT AS WRITABLE
        SQLiteDatabase db = getWritableDatabase();

        //DECLARE AND INITIALIZE CONTENT VALUES; PREPARATIONS FOR INSERTION
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FNAME, student.firstName);
        contentValues.put(COLUMN_LNAME,student.lastName);
        contentValues.put(COLUMN_MARKS, student.grade);

        //INSERT STUDENT INTO DB AND RETURN PRIMARY KEY (ID)
        long idNo = db.insert(TABLE_NAME, null, contentValues);

        //CONDITION : SELECRT QUERY RETURNS A STUDENT
        if(idNo>0) {
            //ASSIGN STUDENT OBJECT ID NUMBER
            student.idNumber = String.valueOf(idNo);
            //RETURN STUDENT
            return student;
        }
        else
            return null;//RETURN NULL - NO STUDENT FOUND
    }

    //DBHELPER - FETCH A STUDENT IN THE DB TABLE
    public ArrayList<Student> selectStudent(int id){

        //INITIALIZE DATABASE OBJECT AS READABLE
        SQLiteDatabase db = getReadableDatabase();

        //PREPARE QUERY FOR FETCHING SINGLE STUDENT
        String getOne = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+"=?";

        //PREPARE QUERY FOR FETCHING MULTIPLE STUDENT
        String getAll = "SELECT * FROM "+TABLE_NAME;

        //DECLARE CURSOR / STUDENT
        Cursor cursor = null;
        Student student = null;
        //DECLARE ARRAY LIST OF STUDENTS
        ArrayList<Student> studentList = null;

        //CONDITION : ID FOR STUDENT PASSED IN PARAMETERS : TRUE
        if(id>0){
            //RETURNED CURSOR OBJECT FROM DATABASE RAW QUERY EXECUTION
            cursor = db.rawQuery(getOne,new String[]{String.valueOf(id)});

            //CONDITION : CURSOR HAS ONE ROW : TRUE
            if(cursor.getCount()==1){

                //STUDENT / STUDENT LIST OBJECT INITIALIZED
                student = new Student();
                studentList = new ArrayList<Student>();

                //PREPARE CURSOR ON RECORD
                cursor.moveToNext();
                //EXTRACT FIELDS FROM CURSOR TO OBJECT
                student.firstName = cursor.getString(1);
                student.lastName = cursor.getString(2);
                student.grade = cursor.getDouble(3);
                student.idNumber = cursor.getString(0);
                //ADD STUDENT OBJECT TO STUDENT LIST
                studentList.add(student);
            }
        }
        else{//CONDITION : ID FOR STUDENT PASSED IN PARAMETERS : FALSE
            //RETURNED CURSOR OBJECT FROM DATABASE RAW QUERY EXECUTION
            cursor = db.rawQuery(getAll,null);
            //CONDITION : CURSOR CONSIST OF 1 OR MORE RECORDS : TRUE
            if(cursor.getCount()>0){
                //PREPARE CURSOR ON RECORD
                cursor.moveToFirst();
                //INITIALIZE STUDENT LIST
                studentList = new ArrayList<Student>();
                do{//LOOP THROUGH CURSOR RECORDS AND ADD EACH STUDENT TO STUDENT LIST
                    studentList.add(new Student(cursor.getString(1),cursor.getString(2),cursor.getDouble(3),cursor.getString(0)));
                }while(cursor.moveToNext());
            }
        }
        //CLOSE CURSOR
        cursor.close();
        //RETURN STUDENT LIST
        return studentList;
    }

    //DBHELPER - UPDATE A STUDENT IN THE DB TABLE
    public Boolean updateStudent(Student student){

        //INITIALIZE DATABASE OBJECT AS READABLE
        SQLiteDatabase db = getReadableDatabase();

        //DECLARE AND INITIALIZE CONTENT VALUES; PREPARATIONS FOR UPDATE
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FNAME, student.firstName);
        contentValues.put(COLUMN_LNAME, student.lastName);
        contentValues.put(COLUMN_MARKS, String.valueOf(student.grade));

        //RETURN (BOOLEAN) IF RECORDS WAS UPDATED
        return db.update(TABLE_NAME, contentValues, COLUMN_ID+"=?", new String[]{String.valueOf(student.idNumber)}) > 0;
    }

    //DBHELPER - DELETE ALL STUDENTS IN THE DB TABLE
    public void deleteAll(){

        //INITIALIZE DATABASE OBJECT AS WRITABLE
       SQLiteDatabase db = getWritableDatabase();
       //EXECUTE DELETE QUERY
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }

    //DBHELPER - DELETE A STUDENT IN THE DB TABLE
    public Boolean deleteStudent(Student student){

        //INITIALIZE DATABASE OBJECT AS WRITABLE
        SQLiteDatabase db = getWritableDatabase();

        //RETURN (BOOLEAN) IF RECORDS DELETED
        return db.delete(TABLE_NAME, COLUMN_ID+"=?", new String[]{String.valueOf(student.idNumber)}) > 0;
    }*/
}
