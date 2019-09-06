package com.example.gradingonion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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

    ArrayList trayLabels = new ArrayList();
    final ArrayList<BarEntry> sunburn_list = new ArrayList<>();
    final ArrayList<BarEntry> halfcut_list = new ArrayList<>();

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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long number=dataSnapshot.getChildrenCount();
                Log.d("Number of Trays",String.valueOf(number));
                String tray="image";
                for(int i =0; i<number; i++)
                {
                    String trayNumber=tray+String.valueOf(i+1);
                    trayLabels.add(trayNumber);
                    String sunburn=dataSnapshot.child("/"+trayNumber+"/sunburn").getValue().toString();
                    String halfcut=dataSnapshot.child("/"+trayNumber+"/halfcut").getValue().toString();
                    Log.d("Tray Number",trayNumber);
                    Log.d("Sunburn",sunburn);
                    Log.d("Half Cut",halfcut);

                    sunburn_list.add(new BarEntry(2*i+1,Float.parseFloat(sunburn)));
                    Log.d("Sunburn List Add",sunburn_list.toString());
                    halfcut_list.add(new BarEntry(2*i,Float.parseFloat(halfcut)));

                }
                BarChart barChart = (BarChart) findViewById(R.id.barchart);

                float groupSpace = 0.06f;
                float barSpace = 0.02f;
                float barWidth = 0.45f;

                barChart.setDrawBarShadow(false);
                barChart.setDrawValueAboveBar(true);
                barChart.setMaxVisibleValueCount(50);
                barChart.setPinchZoom(true);
                barChart.setDrawGridBackground(true);


                BarDataSet sunburnDataset=new BarDataSet(sunburn_list,"Sunburn Dataset");
                sunburnDataset.setColors(COLORFUL_COLORS);
                BarDataSet halfcutDataset=new BarDataSet(halfcut_list,"Halfcut Dataset");
                halfcutDataset.setColors(COLORFUL_COLORS);

                Log.d("Sunburn ",sunburn_list.toString());

                barChart.getDescription().setText("Defects Encountered  ");

                BarData data=new BarData(sunburnDataset,halfcutDataset);
                barChart.setFitBars(true);
                data.setBarWidth(0.3f);
                barChart.setData(data);
                barChart.animateY(3000);



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
