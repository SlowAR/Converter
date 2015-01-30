package by.slowar.converter;

import java.util.Locale;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Settings extends Activity implements OnClickListener
{
	Button brestart;
	RadioGroup rg;
	RadioButton rben, rbru;
	
	private Locale locale;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		brestart = (Button) findViewById(R.id.brestart);
		rg = (RadioGroup) findViewById(R.id.rg);
		rben = (RadioButton) findViewById(R.id.rben);
		rbru = (RadioButton) findViewById(R.id.rbru);
		
		brestart.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.brestart:
			if(rben.isChecked())
				setLocale("english");
			else if(rbru.isChecked())
				setLocale("russian");
			break;
		}
	}
	
	private void setLocale(String lang)
	{
		Toast.makeText(this, R.string.restart, Toast.LENGTH_SHORT).show();
		locale = new Locale(lang);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        
        prefs = getPreferences(MODE_PRIVATE);
		Editor edit = prefs.edit();
		edit.putString("Locale", lang);
		edit.commit();
	}
}
