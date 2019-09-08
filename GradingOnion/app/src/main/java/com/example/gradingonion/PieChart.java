package com.example.gradingonion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PieChart extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    ArrayList<PieEntry> defectCount=new ArrayList<>();

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pie_chart);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try
        {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();



        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("defects");

        Intent intent=getIntent();
        String trayNumber=intent.getStringExtra("Tray");
        int lastDot = trayNumber.lastIndexOf('.');
        String number = trayNumber.substring(0,lastDot);
        Log.d("String Trim",number);
        String tray="image"+number;

        databaseReference.child(tray).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String sunburn=dataSnapshot.child("/sun burn").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(sunburn),"Sunburn"));

                String halfcut=dataSnapshot.child("/half cut").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(halfcut),"Half-Cut"));

                String smutEffected=dataSnapshot.child("/smut effected").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(smutEffected),"Smut Effected"));

                String doubleOnion=dataSnapshot.child("/double onion").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(doubleOnion),"Double Onion"));

                String neck=dataSnapshot.child("/neck").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(neck),"Neck"));

                String rotten=dataSnapshot.child("/rotten").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(rotten),"Rotten"));

                String sprouting=dataSnapshot.child("/sprouting").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(sprouting),"Sprouting"));

                String tip=dataSnapshot.child("/tip").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(tip),"Tip"));

                String withoutSkin=dataSnapshot.child("/without skin").getValue().toString();
                defectCount.add(new PieEntry(Float.parseFloat(withoutSkin),"Without Skin"));

                com.github.mikephil.charting.charts.PieChart pieChart=findViewById(R.id.piechart);
                PieDataSet dataSet = new PieDataSet(defectCount, "Number Of Defects");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieChart.getDescription().setText("Defects Encountered");
                PieData pieData=new PieData(dataSet);
                pieChart.setData(pieData);
                pieChart.setDrawHoleEnabled(true);
                pieChart.setTransparentCircleRadius(30f);
                pieChart.setHoleRadius(30f);
                pieChart.animateXY(1400, 1400);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button capture=findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PieChart.this,CameraActivity.class);
                startActivity(intent);
            }
        });

    }
}
