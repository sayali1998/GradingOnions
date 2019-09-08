package com.example.gradingonion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS;
import static com.github.mikephil.charting.utils.ColorTemplate.COLOR_NONE;
import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    ArrayList<String> trayLabels = new ArrayList<>();
    ArrayList<BarEntry> trayDefects = new ArrayList<>();

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try
        {
            this.getSupportActionBar().hide();

        }
        catch (NullPointerException e){}

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("defects");
        final BarChart barChart = (BarChart) findViewById(R.id.barchart);
        Button capture=findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long number=dataSnapshot.getChildrenCount();
                Log.d("Number of Trays",String.valueOf(number));
                String tray="image";
                for(int i =0; i<number-1; i++)
                {
                    int total=0;
                    String trayNumber=tray+String.valueOf(i+1);
                    trayLabels.add("Tray"+i+1);
                    String sunburn=dataSnapshot.child("/"+trayNumber+"/sun burn").getValue().toString();
                    String halfcut=dataSnapshot.child("/"+trayNumber+"/half cut").getValue().toString();
                    String smutEffected=dataSnapshot.child("/"+trayNumber+"/smut effected").getValue().toString();
                    String doubleOnion=dataSnapshot.child("/"+trayNumber+"/double onion").getValue().toString();
                    String neck=dataSnapshot.child("/"+trayNumber+"/neck").getValue().toString();
                    String rotten=dataSnapshot.child("/"+trayNumber+"/rotten").getValue().toString();
                    String sprouting=dataSnapshot.child("/"+trayNumber+"/sprouting").getValue().toString();
                    String tip=dataSnapshot.child("/"+trayNumber+"/tip").getValue().toString();
                    String withoutSkin=dataSnapshot.child("/"+trayNumber+"/without skin").getValue().toString();
                    total= Integer.parseInt(sunburn)+Integer.parseInt(halfcut)+Integer.parseInt(smutEffected)
                            +Integer.parseInt(doubleOnion)+Integer.parseInt(neck)+Integer.parseInt(rotten)
                            +Integer.parseInt(sprouting)+Integer.parseInt(tip)+Integer.parseInt(withoutSkin);
                    Log.d("Tray Number",trayNumber);
                    Log.d("Total ",String.valueOf(total));
                    trayDefects.add(new BarEntry(i+1,Float.parseFloat(String.valueOf(total))));


                }

                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.setMaxVisibleValueCount(50);
                barChart.setPinchZoom(true);
                barChart.setDrawGridBackground(true);

                barChart.getDescription().setText("Defects Encountered  ");

                BarDataSet trayData=new BarDataSet(trayDefects,"Total Defects");

                BarData data = new BarData(trayData);
                barChart.setData(data);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(trayLabels));

                barChart.setFitBars(true);
                data.setBarWidth(0.1f);
                barChart.setData(data);
                barChart.animateY(3000);

                barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        String selectedTray=String.valueOf(e.getX());
                        Log.d("Bar chart", selectedTray);
                        Intent intent=new Intent(MainActivity.this, PieChart.class);
                        intent.putExtra("Tray",selectedTray);
                        startActivity(intent);
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
