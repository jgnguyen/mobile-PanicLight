package mobile.team.reservations;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class PanicLightActivity extends Activity implements SensorEventListener {
	private SensorManager sensorMgr;
	private long updateTime;
	private Camera cam;

	private boolean isLight = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		updateTime = System.currentTimeMillis();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		cam = Camera.open();
		sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onPause() {
		super.onPause();
		cam.release();
		sensorMgr.unregisterListener(this);
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			panicDetection(event);
		}
	}
	
	private void panicDetection(SensorEvent event) {
		float[] accelerometerVals = event.values;
		
		float x = accelerometerVals[0];
	    float y = accelerometerVals[1];
	    float z = accelerometerVals[2];
	    
	    float acceleration = (x*x + y*y + z*z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	    long currTime = System.currentTimeMillis();
	    if (acceleration >= 2) {
	    	if (currTime - updateTime > 200) {
	    		updateTime = currTime;
	    		android.hardware.Camera.Parameters p = cam.getParameters();
	    		if (!isLight) {
		    		p.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
		    		cam.setParameters(p);
		    		cam.startPreview();
		    		isLight = true;
	    		} else {
	    			p.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
		    		cam.setParameters(p);
		    		cam.stopPreview();
		    		isLight = false;
	    		}
	    	}
	    }
	}
}

	
	
	
