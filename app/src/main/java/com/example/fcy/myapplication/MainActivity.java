package com.example.fcy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fcy.myapplication.database.DirectoryDAOImp;
import com.example.fcy.myapplication.upload.UploadActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button uploadButton;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        uploadButton = (Button) findViewById(R.id.uploadButton);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });

        refreshButton = (Button) findViewById(R.id.refreshButton);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refreshCommand = "refresh";
                SendCommand sendCommand = new SendCommand(refreshCommand);
                Thread sendCommandThread = new Thread(sendCommand);
                sendCommandThread.start();
            }
        });

        final ListView list1 = (ListView) findViewById(R.id.main_list);
        final ArrayList<DirectoryInfo> arr2 = new ArrayList<>();
        DirectoryInfo directoryInfo1 = new DirectoryInfo(1,"doc1",0,0,0,"2017-2-11 12:00:00","abc","abc");
        DirectoryInfo directoryInfo2 = new DirectoryInfo(2,"doc2",0,2,100,"2017-2-11 12:02:00","abc","abc");
        DirectoryInfo directoryInfo3 = new DirectoryInfo(3,"doc3",1,2,100,"2017-2-11 12:02:00","abc","abc");
        DirectoryInfo directoryInfo4 = new DirectoryInfo(4,"doc4",1,2,100,"2017-2-11 12:02:00","abc","abc");
        DirectoryInfo directoryInfo5 = new DirectoryInfo(5,"doc5",1,2,100,"2017-2-11 12:02:00","abc","abc");

        arr2.add(directoryInfo1);
        arr2.add(directoryInfo2);
        arr2.add(directoryInfo3);
        arr2.add(directoryInfo4);
        arr2.add(directoryInfo5);

        final DirectoryDAOImp directoryDAOImp = new DirectoryDAOImp(this);
        for (DirectoryInfo directoryInfo : arr2) {
            directoryDAOImp.insertDirectory(directoryInfo);
        }

        DocumentInfoAdapter adapter1 = new DocumentInfoAdapter(this,R.layout.array_item,R.id.directory_name,arr2);
        list1.setAdapter(adapter1);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DirectoryInfo directoryInfo = arr2.get(position);
                int type = directoryInfo.getM_file_type();
                switch (type) {
                    case 0:{
                        ArrayList<DirectoryInfo> tempArr = new ArrayList<>();
                        tempArr = directoryDAOImp.getSpecDirectorys(1);
                        DocumentInfoAdapter adapter2 = new DocumentInfoAdapter(MainActivity.this,R.layout.array_item,R.id.directory_name,tempArr);
                        list1.setAdapter(adapter2);

                        //Toast.makeText(MainActivity.this,"click document",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2:{
                        Toast.makeText(MainActivity.this,"click video",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:{
                        Toast.makeText(MainActivity.this,"click invalid",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                Toast.makeText(getApplicationContext(),"I'm the toast",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
