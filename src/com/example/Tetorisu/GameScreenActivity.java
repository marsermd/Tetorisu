package com.example.Tetorisu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Filter;

public class GameScreenActivity extends Activity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */

    private static final int SWIPE_MIN_DISTANCE = 100;
    private static final int SWIPE_MAX_OFF_PATH = 300;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private GestureDetector gestureDetector;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TimerTask updateTask = new TimerTask() {
            @Override
            public void run() {
                WorldLogic.getInstance().update();
            }
        };
        Timer updater = new Timer();
        updater.schedule(updateTask, 0, 500);

        gestureDetector = new GestureDetector(this, new Controller());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        GameView gameView = (GameView)findViewById(R.id.gameView);
        gameView.setOnClickListener(this);
        gameView.setOnTouchListener(gestureListener);
    }

    @Override
    public void onClick(View view) {
        Filter f = (Filter) view.getTag();
    }
    public static Controllable controlled = null;
    class Controller extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (controlled == null)
                return false;

            if (Math.abs(e2.getY() - e1.getY()) > Math.abs(e1.getX() - e2.getX())) {
                if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    controlled.controlUp();
                }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    controlled.controlDown();
                }
            } else {
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    controlled.controlLeft();
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    controlled.controlRight();
                }
            }


            return false;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
    }
}
