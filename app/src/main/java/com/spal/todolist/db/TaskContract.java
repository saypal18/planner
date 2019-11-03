package com.spal.todolist.db;

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.spal.todolist.db";
    public static final int DB_VERSION = 7;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String COL_TASK_ID = "id";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_DESCR = "descr";
        public static final String COL_TASK_DATE = "date";
        //public static final String COL_TASK_PARENT = "parent";

        public static final String TABLE2 = "subtasks";
        public static final String COL_TASK_SUBTASKTITLE = "subtasktitle";
        public static final String COL_TASK_SUBTASKDESCR = "subtaskdescr";
        public static final String COL_TASK_SUBTASKDATE = "subtaskdate";
        public static final String COL_TASK_SUBTASKPARENT = "subtaskparent";



    }
}