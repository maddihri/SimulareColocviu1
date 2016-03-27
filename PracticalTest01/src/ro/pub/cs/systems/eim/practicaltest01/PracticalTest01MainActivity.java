package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends Activity {

	private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
	private int serviceStatus = Constants.SERVICE_STOPPED;
	private IntentFilter intentFilter = new IntentFilter();
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_main);
		MyListener listener = new MyListener();
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(listener);
		btn = (Button) findViewById(R.id.navigator);
		btn.setOnClickListener(listener);

		for (int index = 0; index < Constants.actionTypes.length; index++) {
			intentFilter.addAction(Constants.actionTypes[index]);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
			Toast.makeText(this,
					"The activity returned with result " + resultCode,
					Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(messageBroadcastReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		unregisterReceiver(messageBroadcastReceiver);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		Intent intent = new Intent(this, PracticalTest01Service.class);
		stopService(intent);
		super.onDestroy();
	}

	private class MyListener implements Button.OnClickListener {

		@Override
		public void onClick(View v) {
			Log.d("[onClick]", "The button was pressed Initial!");
			EditText number1 = (EditText) findViewById(R.id.editText1);
			EditText number2 = (EditText) findViewById(R.id.editText2);
			switch (v.getId()) {
			case R.id.button1:
				number1.setText(String.valueOf(Integer.parseInt(number1
						.getText().toString()) + 1));
				break;
			case R.id.button2:
				number2.setText(String.valueOf(Integer.parseInt(number2
						.getText().toString()) + 1));
				break;
			case R.id.navigator:
				Intent intent = new Intent(
						"ro.pub.cs.systems.eim.intent.action.PracticalTest01SecondaryActivity");
				intent.putExtra("number_of_clicks", String.valueOf(Integer
						.parseInt(number1.getText().toString())
						+ Integer.parseInt(number2.getText().toString())));
				startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
				break;
			default:
				break;
			}

			int leftNumberOfClicks = Integer.parseInt(number1.getText()
					.toString());
			int rightNumberOfClicks = Integer.parseInt(number2.getText()
					.toString());
			
			if (leftNumberOfClicks + rightNumberOfClicks > Constants.NUMBER_OF_CLICKS_THRESHOLD
					&& serviceStatus == Constants.SERVICE_STOPPED) {
				Log.d("[onClick]", "Se apeleaza serviciul!");
				Intent intent = new Intent(getApplicationContext(),
						PracticalTest01Service.class);
				intent.putExtra("firstNumber", leftNumberOfClicks);
				intent.putExtra("secondNumber", rightNumberOfClicks);
				getApplicationContext().startService(intent);
				serviceStatus = Constants.SERVICE_STARTED;
			}
			
			Log.d("[onClick]", "The button was pressed final!");

		}

	}

	private class MessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("[Message]", intent.getStringExtra("message"));
		}
	}
}
