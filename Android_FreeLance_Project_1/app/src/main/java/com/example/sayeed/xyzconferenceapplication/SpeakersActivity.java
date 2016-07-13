package com.example.sayeed.xyzconferenceapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SpeakersActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_speakers);

        try {
            db = new FeedReaderDbHelper(SpeakersActivity.this).getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Write to database
        db.execSQL("INSERT INTO CHART (FIRSTNAME, LASTNAME, AFFILIATION, EMAIL, BIO)" +
                "   VALUES ('Syrus', 'Kamali', 'Ebay', 'syrus@kamali.com', 'A fantastic speaker'); ");
        db.execSQL("INSERT INTO CHART (FIRSTNAME, LASTNAME, AFFILIATION, EMAIL, BIO)" +
                "   VALUES ('Przemyslaw', 'Pawluk', 'Microsoft', 'Przemyslaw@gmail.com', 'A fantastic speaker from microsoft'); ");

        // Retrieve from database
        List<Speaker> speakers = retrieveSpeakers();


        // put into a list view
        final ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, speakers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                // retrieve theListView item
                Speaker s = (Speaker) parent.getItemAtPosition(position);
                s.setVerbose(!s.verbose());
                listView.invalidateViews();
            }
        });
    }

    private class FeedReaderDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;

        public static final String DATABASE_NAME = "database.db";

        public FeedReaderDbHelper (Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate (SQLiteDatabase db) {
            db.execSQL("CREATE TABLE CHART (FIRSTNAME TEXT, LASTNAME TEXT, AFFILIATION TEXT, EMAIL TEXT, BIO TEXT);");
        }

        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private List<Speaker> retrieveSpeakers () {
        List<Speaker> speakerList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM CHART";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Speaker speaker = new Speaker(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                speakerList.add(speaker);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return speakerList;
    }
}