package pt.mobilesgmc;

import pt.mobilesgmc.modelo.OnSwipeTouchListener;
import pt.mobilesgmc.view.viewgroup.FlyOutContainer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobilegsmc.R;

public class HomeActivity extends Activity {

	FlyOutContainer root;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		
		
		
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(R.layout.activity_home, null);
		 Display display = getWindowManager().getDefaultDisplay();
		    DisplayMetrics outMetrics = new DisplayMetrics ();
		    display.getMetrics(outMetrics);

		    float density  = getResources().getDisplayMetrics().density;
		    float dpWidth  = outMetrics.widthPixels / density;
		    int margin = (80*(int)dpWidth)/100;
		root.setMargin(margin);
		this.setContentView(root);
		
		
		
		root.setOnTouchListener(new OnSwipeTouchListener(this){
			public void onSwipeTop() {
		      //  Toast.makeText(SampleActivity.this, "top", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeRight() {
		    	String estado = root.getState().toString();
		    	if(estado.equals("CLOSED"))
		    	toggleMenu(findViewById(R.layout.activity_home));
		      //  Toast.makeText(SampleActivity.this, "right", Toast.LENGTH_SHORT).show();
		    }
		    public void onSwipeLeft() {
		    	String estado = root.getState().toString();
		    	if(estado.equals("OPEN"))
		    	toggleMenu(findViewById(R.layout.activity_home));
		    }
		    public void onSwipeBottom() {
		       // Toast.makeText(SampleActivity.this, "bottom", Toast.LENGTH_SHORT).show();
		    }

		public boolean onTouch(View v, MotionEvent event) {
		    return gestureDetector.onTouchEvent(event);
		}
			
			
		});
		
		Button btnEquipa = (Button) root.findViewById(R.id.buttonEquipa);
		btnEquipa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent utentes = new Intent(getBaseContext(),
						EquipaCirurgica.class);
				toggleMenu(findViewById(R.layout.activity_home));
				startActivity(utentes);
				
			}
		});
		Button btnUtentes = (Button) root.findViewById(R.id.buttonUtentes);
		btnUtentes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent utentes = new Intent(getBaseContext(),
						UtentesActivity.class);
				toggleMenu(findViewById(R.layout.activity_home));
				startActivity(utentes);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
	
	public void toggleMenu(View v){
		this.root.toggleMenu();
	}
	
	

}
