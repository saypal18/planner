package com.spal.todolist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spal.todolist.db.TaskContract;
import com.spal.todolist.db.TaskDbHelper;

import java.util.ArrayList;

public class SubTaskDetail extends AppCompatActivity {
    private static final String TAG = "SubTaskDetail";
    private TaskDbHelper mHelper;
    private String thisparent;
    //private ListView mTaskListView;
    //private ArrayAdapter<String> mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mDescView;
    private MyAdapter2 miAdapter;


    private void updateUI1() {
        ArrayList<Model> taskList1 = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TaskContract.TaskEntry.TABLE2 + " WHERE subtaskparent LIKE " + " '" + thisparent + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {

                taskList1.add(new Model(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));

            } while (cursor.moveToNext());


            MyAdapter2 adapter = new MyAdapter2(this, taskList1);
            mRecyclerView.setAdapter(adapter);

        }

        cursor.close();
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String parent = intent.getStringExtra("stparentname");
        thisparent=parent;
        setTitle(thisparent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_subtask);

        mHelper = new TaskDbHelper(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM subtasks WHERE subtasktitle LIKE " + " '" + thisparent + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        String descript = cursor.getString(1);
        mDescView = findViewById(R.id.subtaskdetail_descr);
        mDescView.setText(descript);
        //mTaskListView = (ListView) findViewById(R.id.list_todo);
        //SQLiteDatabase db = mHelper.getReadableDatabase();
        /*Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);*/
        /*while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
        }*/
        //cursor.close();
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                showChangeLangDialog();
                Log.d(TAG, "Add a new task");
                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_layout2, null);
        dialogBuilder.setView(dialogView);

        //final EditText edt = (EditText) dialogView.findViewById(R.id.dialog_name);
        final EditText edt2 = (EditText) dialogView.findViewById(R.id.dialog_descr);
        //final EditText edt3 = (EditText) dialogView.findViewById(R.id.dialog_date);
        String already = mDescView.getText().toString();
        edt2.setText(already);
        dialogBuilder.setTitle("Edit Subtask Description");
        dialogBuilder.setMessage("Enter Subtask Description");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //String task = String.valueOf(edt.getText()).toString();
                String descr = String.valueOf(edt2.getText());
                //String date = String.valueOf(edt3.getText());
                if (!descr.equals("")) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(TaskContract.TaskEntry.COL_TASK_SUBTASKTITLE, thisparent);
                    values.put(TaskContract.TaskEntry.COL_TASK_SUBTASKDESCR, descr);
                    //values.put(TaskContract.TaskEntry.COL_TASK_SUBTASKDATE, date);
                    //values.put(TaskContract.TaskEntry.COL_TASK_SUBTASKPARENT, thisparent);

                    db.insertWithOnConflict(TaskContract.TaskEntry.TABLE2,
                            null,
                            values,
                            SQLiteDatabase.CONFLICT_REPLACE);
                    db.close();

                    mDescView.setText(descr);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You must enter the description",
                            Toast.LENGTH_SHORT);

                    toast.show();

                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showChangeLangDialog1(String taskname, String taskdescr, String taskdate) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        //final EditText edt = (EditText) dialogView.findViewById(R.id.dialog_name);
        final EditText edt2 = (EditText) dialogView.findViewById(R.id.dialog_descr);
        //final EditText edt3 = (EditText) dialogView.findViewById(R.id.dialog_date);

        //edt.setText(taskname);
        edt2.setText(taskdescr);
        //edt3.setText(taskdate);

        dialogBuilder.setTitle("Edit Subtask Description");
        dialogBuilder.setMessage("Enter Subtask Description");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //String task = String.valueOf(edt.getText()).toString();
                String descr = String.valueOf(edt2.getText());
                //String date = String.valueOf(edt3.getText());
                if (!descr.equals(""))
                {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    //values.put(TaskContract.TaskEntry.COL_TASK_SUBTASKTITLE, task);
                    values.put(TaskContract.TaskEntry.COL_TASK_SUBTASKDESCR, descr);
                    //values.put(TaskContract.TaskEntry.COL_TASK_SUBTASKDATE, date);
                    values.put(TaskContract.TaskEntry.COL_TASK_SUBTASKPARENT, thisparent);

                    db.insertWithOnConflict(TaskContract.TaskEntry.TABLE2,
                            null,
                            values,
                            SQLiteDatabase.CONFLICT_REPLACE);
                    db.close();
                    updateUI1();
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "You must enter the description",
                            Toast.LENGTH_SHORT);

                    toast.show();

                }

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }



    public void editTask(View view) {
        View parent = (View) view.getParent();
        View parent2 = (View) parent.getParent();
        TextView taskTextView = (TextView) parent2.findViewById(R.id.task_title);
        TextView taskDescView = (TextView) parent.findViewById(R.id.task_descr);
        TextView taskDateView = (TextView) parent2.findViewById(R.id.task_date);

        String task = String.valueOf(taskTextView.getText());
        String taskdescr = String.valueOf(taskDescView.getText());
        String taskdate = String.valueOf(taskDateView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.delete(TaskContract.TaskEntry.TABLE2,
                TaskContract.TaskEntry.COL_TASK_SUBTASKDESCR + " = ?",
                new String[]{taskdescr});


        db.close();

        showChangeLangDialog1(task, taskdescr, taskdate);

    }


    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) view.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI1();
    }


}

