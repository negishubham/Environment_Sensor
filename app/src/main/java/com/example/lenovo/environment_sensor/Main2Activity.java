package com.example.lenovo.environment_sensor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Main2Activity extends AppCompatActivity implements SensorEventListener{
    private GraphView mGraphLight,mGraphPre,mGraphHum,mGraphTemp;
    private SensorManager mSensorManager;
    private Sensor mPressure,mLight,mHumidity,mTemp;
    private double graphLastAccelXValue = 5d;
    private LineGraphSeries L,P,T,H;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mLight=mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mTemp=mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        mHumidity=mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mHumidity,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mTemp,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mLight,SensorManager.SENSOR_DELAY_NORMAL);

        mGraphLight = initGraph(R.id.graphView1, "Light");
        mGraphTemp = initGraph(R.id.graphView2, "Temperature");
        mGraphHum = initGraph(R.id.graphView3, "Humidity");
        mGraphPre = initGraph(R.id.graphView4, "Pressure");
        L = initSeries(Color.BLUE, "LIGHT");
        P = initSeries(Color.BLUE, "PRESSURE");
        T = initSeries(Color.BLUE, "TEMPERATURE");
        H = initSeries(Color.BLUE, "HUMIDITY");
        mGraphHum.addSeries(H);
        mGraphLight.addSeries(L);
        mGraphTemp.addSeries(T);
        mGraphPre.addSeries(P);

        Button cpoButton1=(Button)findViewById(R.id.Back);
        cpoButton1.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent i = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(i);
                    }
                }
        );
    }

    public LineGraphSeries<DataPoint> initSeries(int color, String title) {
        LineGraphSeries<DataPoint> series;
        series = new LineGraphSeries<>();
        series.setDrawDataPoints(false);
        series.setDrawBackground(false);
        series.setColor(color);
        series.setTitle(title);
        return series;
    }

    public GraphView initGraph(int id, String title) {
        GraphView graph = (GraphView) findViewById(id);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(5);
        graph.getGridLabelRenderer().setLabelVerticalWidth(100);
        graph.setTitle(title);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        return graph;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_LIGHT){
            graphLastAccelXValue += 0.15d;
            L.appendData(new DataPoint(graphLastAccelXValue, event.values[0]), true, 33);
            }
        if(event.sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY){
            graphLastAccelXValue += 0.15d;
            H.appendData(new DataPoint(graphLastAccelXValue, event.values[0]), true, 33);
        }
        if(event.sensor.getType()==Sensor.TYPE_TEMPERATURE){
            graphLastAccelXValue += 0.15d;
            T.appendData(new DataPoint(graphLastAccelXValue, event.values[0]), true, 33);
        }
        if(event.sensor.getType()==Sensor.TYPE_PRESSURE){
            graphLastAccelXValue += 0.15d;
            P.appendData(new DataPoint(graphLastAccelXValue, event.values[0]), true, 33);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
