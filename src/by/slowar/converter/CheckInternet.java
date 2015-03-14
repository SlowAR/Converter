package by.slowar.converter;

import java.text.SimpleDateFormat;
import java.sql.Date;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternet extends Activity
{
	SharedPreferences prefs;
	Converter converter;
	
	public CheckInternet(Converter converter)
	{
		this.converter = converter;
	}
	
	public boolean connet(ConnectivityManager cm) 
	{
	    if (cm == null)
	    {
	    	return false;
	    }
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    if (netInfo == null)
	    {
	    	return false;
	    }
	    for (NetworkInfo ni : netInfo)
	    {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	        {
	            if (ni.isConnected()) 
	            {
					return true;
	            }
	        }
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	        {
	            if (ni.isConnected()) 
	            {
					return true;
	            }
	        }
	    }
		return false;
	}
	
	public void dateUp(boolean connected, SharedPreferences prefs, String valName)
	{
		if(connected)
		{
			StringBuilder sb = new StringBuilder();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy");
			sb.append(dateFormat.format(new Date(System.currentTimeMillis())));
			
			sb.append("; ");
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			sb.append(timeFormat.format(new Date(System.currentTimeMillis())));
			
			Editor edit = prefs.edit();
			edit.putString(valName, "" + converter.kurs);
			edit.putString("T" + valName, sb.toString());
			edit.commit();
			
			converter.etone.setEnabled(true);
			converter.tvupd.setText(sb.toString());
		}
		else
		{
			if(prefs.contains(valName) && prefs.contains("T" + valName))
			{
				converter.etone.setEnabled(true);
				converter.jsonRes.setText(prefs.getString(valName, ""));
				converter.kurs = Double.parseDouble(prefs.getString(valName, ""));
				converter.tvupd.setText(prefs.getString("T" + valName, ""));
			}
			else
			{
				converter.etone.setEnabled(false);
				converter.jsonRes.setText("");
				converter.tvupd.setText(R.string.nodata);
			}
		}
	}
}