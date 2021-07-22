package com.armin_mehraeen.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;



public class NumberSelector extends LinearLayout implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener {

    Context context;
    View rootView;
    ImageView btnInc;
    ImageView btnDes;
    TextView valueText;
    Boolean btnIncPressed = false;
    Boolean btnDesPressed = false;

    int maxValue = 10;
    int minValue = 0;
    private static final int TIME_INTERVAL = 200;
    android.os.Handler handler;

    public NumberSelector(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NumberSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public int getValue(){
        String value = valueText.getText().toString().trim();
        return Integer.parseInt(value);
    }

    public void setValue(int value){
        if (value > maxValue)
            valueText.setText(String.valueOf(maxValue));
        else if (value < minValue)
            valueText.setText(String.valueOf(minValue));
        else
            valueText.setText(String.valueOf(value));
    }

    private void init() {
        rootView = inflate(context,R.layout.number_selector_layout,this);
        btnDes = rootView.findViewById(R.id.btn_des);
        btnInc = rootView.findViewById(R.id.btn_inc);
        valueText = rootView.findViewById(R.id.txt_value);
        handler = new Handler();

        btnInc.setOnClickListener(this);
        btnDes.setOnClickListener(this);

        btnDes.setOnLongClickListener(this);
        btnInc.setOnLongClickListener(this);

        btnInc.setOnTouchListener(this);
        btnDes.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_des){
            valueDes();
        }else if (v.getId() == R.id.btn_inc){
            valueInc();
        }
    }

    private void valueInc() {
        int value = getValue();
        value = value + 1;
        setValue(value);
    }

    private void valueDes() {
        int value = getValue();
        value = value - 1;
        setValue(value);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_CANCEL | event.getAction() == MotionEvent.ACTION_UP){
            if (v.getId() == R.id.btn_des){
                btnDesPressed = false;
            }else if (v.getId() == R.id.btn_inc){
                btnIncPressed = false;
            }
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.btn_inc){
            btnIncPressed = true;
            handler.postDelayed(new autoInc(),TIME_INTERVAL);
        }
        else if (v.getId() == R.id.btn_des){
            btnDesPressed = true;
            handler.postDelayed(new autoDes(),TIME_INTERVAL);
        }
        return false;
    }

    private class autoDes implements Runnable{

        @Override
        public void run() {
            if (btnDesPressed){
                valueDes();
                handler.postDelayed(this,TIME_INTERVAL);
            }
        }
    }

    private class autoInc implements Runnable{

        @Override
        public void run() {
            if (btnIncPressed){
                valueInc();
                handler.postDelayed(this,TIME_INTERVAL);
            }
        }
    }
}
