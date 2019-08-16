package com.chinuthor.picatune;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Objects;

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

    //DBHELPER - FETCH A STUDENT IN THE DB TABLE
    public User login(String username, String password) {
        User user = null;
        //INITIALIZE DATABASE OBJECT AS READABLE
        SQLiteDatabase db = getReadableDatabase();
        //CREATE DATA BASE SQL QUERY
        String getUser = "SELECT * FROM " + DatabaseUtil.USERS_TABLE_NAME + " WHERE " + DatabaseUtil.USERS_COLUMN_USERNAME + "=?" + " AND " + DatabaseUtil.USERS_COLUMN_PASSWORD + "=?";
        //DECLARE CURSOR FOR DATABASE RETREIVAL
        Cursor cursor = null;
        //EXECUTE DATABASE QUERY
        cursor = db.rawQuery(getUser, new String[]{String.valueOf(username), String.valueOf(password)});

        if (cursor.getCount() == 1) {//RETURNED ONE (1) USER
            cursor.moveToFirst();
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_ID)));
            user.setFirstName(cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_FIRST_NAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_LAST_NAME)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_USERNAME)));
            user.setAdmin(cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_IS_ADMIN)) > 0);
            user.setRatingPrompted(cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_RATING_PROMPTED)) > 0);
            user.setAppRating(cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_APP_RATING)));
            user = updateUserLoginCount(user);
        }

        cursor.close();
        db.close();
        return user;
    }

    public User getUser(String username) {
        User user = null;
        //INITIALIZE DATABASE OBJECT AS READABLE
        SQLiteDatabase db = getReadableDatabase();
        //CREATE DATA BASE SQL QUERY
        String getUser = "SELECT * FROM " + DatabaseUtil.USERS_TABLE_NAME + " WHERE " + DatabaseUtil.USERS_COLUMN_USERNAME + "=?";
        //DECLARE CURSOR FOR DATABASE RETREIVAL
        Cursor cursor = null;
        //EXECUTE DATABASE QUERY
        cursor = db.rawQuery(getUser, new String[]{username});

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_ID)));
            user.setFirstName(cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_FIRST_NAME)));
            user.setLastName(cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_LAST_NAME)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_USERNAME)));
            user.setAdmin(cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_IS_ADMIN)) > 0);
            user.setRatingPrompted(cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_RATING_PROMPTED)) > 0);
            user.setAppRating(cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_APP_RATING)));
        }

        user = getUserSongs(user);

        cursor.close();
        db.close();
        return user;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        //INITIALIZE DATABASE OBJECT AS READABLE
        SQLiteDatabase db = getReadableDatabase();
        //CREATE DATA BASE SQL QUERY
        String getUser = "SELECT * FROM " + DatabaseUtil.USERS_TABLE_NAME;
        //DECLARE CURSOR FOR DATABASE RETREIVAL
        Cursor cursor = null;
        //EXECUTE DATABASE QUERY
        cursor = db.rawQuery(getUser, new String[]{});

        if (cursor.getCount() > 0) {
            //PREPARE CURSOR ON RECORD
            cursor.moveToFirst();
            //INITIALIZE USER LIST
            do {//LOOP THROUGH CURSOR RECORDS AND ADD EACH USER TO USER LIST
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_LAST_NAME));
                String username = cursor.getString(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_USERNAME));
                boolean isAdmin = cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_IS_ADMIN)) > 0;
                boolean ratingPrompted = cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_RATING_PROMPTED)) > 0;
                int loginCount = cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_LOGIN_COUNT));
                int appRating = cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USERS_COLUMN_APP_RATING));
                userList.add(new User(id, firstName, lastName, username, isAdmin, ratingPrompted, loginCount, appRating));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userList;
    }

    public User addUser(User newUser) {

        if (getUser(Objects.requireNonNull(newUser).getUsername()) != null) {
            return null;
        } else {
            //INITIALIZE DATABASE OBJECT AS WRITABLE
            SQLiteDatabase db = getWritableDatabase();

            //DECLARE AND INITIALIZE CONTENT VALUES; PREPARATIONS FOR INSERTION
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseUtil.USERS_COLUMN_FIRST_NAME, newUser.getFirstName());
            contentValues.put(DatabaseUtil.USERS_COLUMN_LAST_NAME, newUser.getLastName());
            contentValues.put(DatabaseUtil.USERS_COLUMN_USERNAME, newUser.getUsername());
            contentValues.put(DatabaseUtil.USERS_COLUMN_PASSWORD, newUser.getPassword());
            contentValues.put(DatabaseUtil.USERS_COLUMN_IS_ADMIN, false);
            contentValues.put(DatabaseUtil.USERS_COLUMN_LOGIN_COUNT, 1);
            contentValues.put(DatabaseUtil.USERS_COLUMN_RATING_PROMPTED, false);
            contentValues.put(DatabaseUtil.USERS_COLUMN_APP_RATING, 0);

            long idNo = db.insert(DatabaseUtil.USERS_TABLE_NAME, null, contentValues);

            if (idNo > 0) {
                //ASSIGN USER OBJECT ID NUMBER
                newUser = getUser(newUser.getUsername());

                if (newUser != null)
                    newUser = addUserPlaylist(newUser);

                db.close();
                //RETURN USER
                return newUser;
            } else {
                db.close();
                return null;//RETURN NULL - NO USER FOUND
            }
        }
    }

    public User getUserSongs(User user) {
        ArrayList<User> userList = null;
        //INITIALIZE DATABASE OBJECT AS READABLE
        SQLiteDatabase db = getReadableDatabase();
        //CREATE DATA BASE SQL QUERY
        String getUserSongs = "SELECT * FROM " + DatabaseUtil.USER_SONGS_TABLE_NAME + " WHERE " + DatabaseUtil.USER_SONGS_COLUMN_USER_ID + "=?";
        //DECLARE CURSOR FOR DATABASE RETREIVAL
        Cursor cursor = null;
        //EXECUTE DATABASE QUERY
        cursor = db.rawQuery(getUserSongs, new String[]{String.valueOf(user.getId())});

        if (cursor != null && cursor.getCount() > 0) {
            user.getSongList().clear();
            //PREPARE CURSOR ON RECORD
            cursor.moveToFirst();
            //INITIALIZE USER LIST
            userList = new ArrayList<User>();
            do {//LOOP THROUGH CURSOR RECORDS AND ADD EACH SONG TO USER SONG LIST
                Song song = new Song();
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USER_SONGS_COLUMN_ID));
                boolean onPlayList = cursor.getInt(cursor.getColumnIndex(DatabaseUtil.USER_SONGS_COLUMN_ON_PLAYLIST)) > 0;
                song.setId(id);
                song.setOnPlayList(onPlayList);
                user.getSongList().add(song);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return user;
    }

    public User submitAppRating(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtil.USERS_COLUMN_APP_RATING, user.getAppRating());
        if (user.getAppRating() > 0) {
            contentValues.put(DatabaseUtil.USERS_COLUMN_RATING_PROMPTED, true);
        }
        db.update(DatabaseUtil.USERS_TABLE_NAME, contentValues, DatabaseUtil.USERS_COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});

        db.close();
        return user;
    }

    public User addToPlaylist(User user, int songId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtil.USER_SONGS_COLUMN_ON_PLAYLIST, true);

        //RETURN (BOOLEAN) IF RECORDS WAS UPDATED
        if (db.update(DatabaseUtil.USER_SONGS_TABLE_NAME, contentValues, DatabaseUtil.USER_SONGS_COLUMN_ID + "=?", new String[]{String.valueOf(songId)}) > 0) {
            for (Song song : user.getSongList()) {
                if (song.getId() == songId) {
                    song.setOnPlayList(true);
                    break;
                }
            }
        }
        user = upDatePlaylistCount(user);

        db.close();
        return user;
    }

    public User updatePlaylistName(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtil.PLAYLIST_COLUMN_PLAYLIST_NAME, user.getPlaylist().getPlaylistName());
        db.update(DatabaseUtil.PLAYLIST_TABLE_NAME, contentValues, DatabaseUtil.PLAYLIST_COLUMN_ID + "=?", new String[]{String.valueOf(user.getPlaylist().getId())});

        db.close();
        return user;
    }

    public User removeFromPlaylist(User user, int songId) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtil.USER_SONGS_COLUMN_ON_PLAYLIST, false);

        //RETURN (BOOLEAN) IF RECORDS WAS UPDATED
        if (db.update(DatabaseUtil.USER_SONGS_TABLE_NAME, contentValues, DatabaseUtil.USER_SONGS_COLUMN_ID + "=?", new String[]{String.valueOf(songId)}) > 0) {
            for (Song song : user.getSongList()) {
                if (song.getId() == songId) {
                    song.setOnPlayList(false);
                    break;
                }
            }
        }
        user = upDatePlaylistCount(user);
        db.close();
        return user;
    }

    public User addSongToLibrary(User user, int songId) {
        //INITIALIZE DATABASE OBJECT AS WRITABLE
        SQLiteDatabase db = getWritableDatabase();

        //DECLARE AND INITIALIZE CONTENT VALUES; PREPARATIONS FOR INSERTION
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtil.USER_SONGS_COLUMN_USER_ID, user.getId());
        contentValues.put(DatabaseUtil.USER_SONGS_COLUMN_ID, songId);
        contentValues.put(DatabaseUtil.USER_SONGS_COLUMN_ON_PLAYLIST, false);
        db.insert(DatabaseUtil.USER_SONGS_TABLE_NAME, null, contentValues);

//        Song newSong = new Song();
//        newSong.setId(songId);
//        newSong.setOnPlayList(false);
//        user.getSongList().add(newSong);

        db.close();
        return user;
    }

    private User upDatePlaylistCount(User user) {
        SQLiteDatabase db = getWritableDatabase();
        //RECALUCULATE NUMBER OF TRACK ON PLAYLIST
        int onListCounter = 0;
        user.getPlaylist().setSongCount(0);
        for (Song song : user.getSongList()) {
            if (song.isOnPlayList()) {
                onListCounter++;
            }
        }
        //SET RECALCULATED NUMBER OF TACKS ON PLAYLIST
        user.getPlaylist().setSongCount(onListCounter);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtil.PLAYLIST_COLUMN_SONG_COUNT, user.getPlaylist().getSongCount());
        db.update(DatabaseUtil.PLAYLIST_TABLE_NAME, contentValues, DatabaseUtil.PLAYLIST_COLUMN_ID + "=?", new String[]{String.valueOf(user.getPlaylist().getId())});

        db.close();
        return user;
    }

    private User addUserPlaylist(User user) {
        SQLiteDatabase db = getWritableDatabase();

        user.getPlaylist();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtil.PLAYLIST_COLUMN_PLAYLIST_NAME, user.getPlaylist().getPlaylistName());
        contentValues.put(DatabaseUtil.PLAYLIST_COLUMN_SONG_COUNT, 0);
        contentValues.put(DatabaseUtil.PLAYLIST_COLUMN_USER_ID, user.getId());
        contentValues.put(DatabaseUtil.PLAYLIST_COLUMN_DURATION, 0);
        long insertId = db.insert(DatabaseUtil.PLAYLIST_TABLE_NAME, null, contentValues);
        if (insertId > 0) {
            user.getPlaylist().setId(Integer.valueOf(String.valueOf(insertId)));
        }
        db.close();
        return user;
    }

    private User updateUserLoginCount(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseUtil.USERS_COLUMN_LOGIN_COUNT, user.getLoginCount() + 1);
        db.update(DatabaseUtil.USERS_TABLE_NAME, contentValues, DatabaseUtil.USERS_COLUMN_ID + "=?", new String[]{String.valueOf(user.getId())});

        db.close();
        return user;
    }








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
