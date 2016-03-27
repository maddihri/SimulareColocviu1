package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Activity; 
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01SecondaryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_secondary);
		EditText numberOfClicksView = (EditText)findViewById(R.id.editText1);
		 
	    Intent intent = getIntent();
	    if (intent != null) {
	      String numberOfClicks = intent.getStringExtra("number_of_clicks");
	      if (numberOfClicks != null) {
	    	  numberOfClicksView.setText(numberOfClicks);
	      }
	    }
	    MyListener listener = new MyListener();
	    Button buttonOk = (Button)findViewById(R.id.ok);
	    buttonOk.setOnClickListener(listener);
	    Button buttonCancel = (Button)findViewById(R.id.cancel);
	    buttonCancel.setOnClickListener(listener);      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_secondary, menu);
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

	private class MyListener implements Button.OnClickListener {
		 
	    @Override
	    public void onClick(View view) {
	      switch(view.getId()) {
	        case R.id.ok:
	          setResult(RESULT_OK, new Intent());
	          break;
	        case R.id.cancel:
	          setResult(RESULT_CANCELED, new Intent());
	          break;
	      }
          finish();
	    }
	  }  
}
