package com.spal.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    public TaskDbHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COL_TASK_DESC + "TEXT NOT NULL );";*/

        String createTable = "CREATE TABLE tasks ( title, descr, date, id INTEGER PRIMARY KEY );";

        db.execSQL(createTable);

        String fillTable = "INSERT INTO tasks ( title, descr, date ) VALUES ('Acads', 'padhai ki baatein', '2019-12-31');";

        db.execSQL(fillTable);

        String fillTable1 = "INSERT INTO tasks ( title, descr, date ) VALUES ('Hobbies', '<3', 'null');";

        db.execSQL(fillTable1);

        String fillTable2 = "INSERT INTO tasks ( title, descr, date ) VALUES ('Self improvement', 'Reading List, blogs, exercise, etc.', '2019-12-30');";

        db.execSQL(fillTable2);

        String fillTable3 = "INSERT INTO tasks ( title, descr, date ) VALUES ('Research', 'pet projects', 'null');";

        db.execSQL(fillTable3);


        String createTable2 = "CREATE TABLE subtasks ( subtasktitle, subtaskdescr, subtaskdate, subtaskparent, subtaskparentid INTEGER , FOREIGN KEY (subtaskparentid) REFERENCES tasks (id) ON UPDATE CASCADE ON DELETE CASCADE );";
        db.execSQL(createTable2);
        String fill2Table1 = "INSERT INTO subtasks ( subtasktitle, subtaskdescr, subtaskdate, subtaskparent, subtaskparentid ) VALUES ('Exercise', 'someday?', '2021-2-29', 'Self improvement', 3);";
        db.execSQL(fill2Table1);
        String fill2Table2 = "INSERT INTO subtasks ( subtasktitle, subtaskdescr, subtaskdate, subtaskparent, subtaskparentid ) VALUES ('Reading list', 'My bucket list:\\nHear the Wind Sing\\nThe Fountainhead\\nAtlas Shrugged\\nA prisoner of birth', 'null', 'Self improvement', 3);";
        db.execSQL(fill2Table2);
        String fill2Table3 = "INSERT INTO subtasks ( subtasktitle, subtaskdescr, subtaskdate, subtaskparent, subtaskparentid ) VALUES ('Origami', 'cranes and tigers', '2019-10-29', 'Hobbies', 2);";
        db.execSQL(fill2Table3);
        String fill2Table4 = "INSERT INTO subtasks ( subtasktitle, subtaskdescr, subtaskdate, subtaskparent, subtaskparentid ) VALUES ('Drum Practice!', 'Aim:\\nHallowed be thy name,\\nAcid Rain (LTE)', '2019-10-29', 'Hobbies', 2);";
        db.execSQL(fill2Table4);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }
}
