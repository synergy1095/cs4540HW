package com.sargent.mark.todolist.data;

import android.provider.BaseColumns;

import com.sargent.mark.todolist.MainActivity;

/**
 * Created by mark on 7/4/17.
 */

public class Contract {
    //added color variable to have easily accessible color variable
    public static int primaryColor;
    public static class TABLE_TODO implements BaseColumns{
        public static final String TABLE_NAME = "todoitems";

        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DUE_DATE = "duedate";
        public static final String COLUMN_NAME_FINISHED = "finished";
        public static final String COLUMN_NAME_CATEGORY = "category";
    }
}
