package mobile.team.reservations;

import java.util.Random;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MagicEightBallActivity extends Activity implements SensorEventListener {
	private SensorManager sensorMgr;

	private boolean isShaken = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onResume() {
		super.onResume();
		sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onPause() {
		super.onPause();
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
		if (acceleration >= 2) {
			if (isShaken) {
				String[] responses = getResources().getStringArray(R.array.answers);
				Random generator = new Random();
				TextView messageTV = (TextView) findViewById(R.id.responseTextView);
				TextView helpTV = (TextView) findViewById(R.id.helpTextView);
				
				messageTV.setText(responses[generator.nextInt(responses.length)]);
				helpTV.setText(R.string.help);
				isShaken = false;
			} else {
				TextView messageTV = (TextView) findViewById(R.id.responseTextView);
				TextView helpTV = (TextView) findViewById(R.id.helpTextView);
				
				helpTV.setText("");
				messageTV.setText(R.string.defaultTxt);
				isShaken = true;
			}
		}
	}
}




