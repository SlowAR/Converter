package by.slowar.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Converter extends Fragment implements OnClickListener
{	
	TextView jsonRes, tvupd;
	Button convertbtn;
	Button swap1, swap2;
	Button btnRefresh;
	EditText etone, ettwo;
	Spinner spin1, spin2;
	Currencies currencies;
	ProgressDialog loadingData;
	
	boolean listGeted = false;
	BigDecimal firstValue, secondValue;
	double kurs = 0.82;
	String first, second, temp;
	ConnectivityManager cm;
	CheckInternet chnet;
	SharedPreferences prefs;
	
	ArrayList<String> curr = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	
	public Converter(ConnectivityManager cm, SharedPreferences prefs)
	{
		this.cm = cm;
		this.prefs = prefs;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
    	chnet = new CheckInternet(this);
    	loadingData = new ProgressDialog(getActivity());
    	
        View view = inflater.inflate(R.layout.converter, container, false);
        jsonRes = (TextView) view.findViewById(R.id.jsonRes);
        tvupd = (TextView) view.findViewById(R.id.tvupd);
        convertbtn = (Button) view.findViewById(R.id.convertbtn);
        swap1 = (Button) view.findViewById(R.id.swap1);
        swap2 = (Button) view.findViewById(R.id.swap2);
        btnRefresh = (Button) view.findViewById(R.id.btnRefresh);
        etone = (EditText) view.findViewById(R.id.etone);
        ettwo = (EditText) view.findViewById(R.id.ettwo);
        spin1 = (Spinner) view.findViewById(R.id.spinner1);
        spin2 = (Spinner) view.findViewById(R.id.spinner2);
        
        convertbtn.setOnClickListener(this);
        swap1.setOnClickListener(this);
        swap2.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        
        currencies = new Currencies(this, prefs);
        
//        if(chnet.connet(cm))
//        	if(!listGeted)
//        		currencies.getList();
//		else
//		{
//			if(!listGeted)
//        		loadingDataOffline();
//        	if(!spin1.getSelectedItem().toString().isEmpty() && !spin2.getSelectedItem().toString().isEmpty())
//        		chnet.dateUp(false, prefs, spin1.getSelectedItem().toString().substring(0, 3) + spin2.getSelectedItem().toString().substring(0, 3));
//		}
        
        refresh();
        
        spin1.setOnItemSelectedListener(new OnItemSelectedListener()
        {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3)
			{
				try
				{
					((TextView) parent.getChildAt(0)).setText(spin1.getSelectedItem().toString().substring(0, 3));
				}
				catch(NullPointerException e){}
				
		        if(chnet.connet(cm))
		        	currencies.getCurrency(spin1.getSelectedItem().toString().substring(0, 3), spin2.getSelectedItem().toString().substring(0, 3));
		        else
		        {
		        	try
		        	{
		        		jsonRes.setText(prefs.getString(spin1.getSelectedItem().toString().substring(0, 3) + spin2.getSelectedItem().toString().substring(0, 3), ""));
		        		tvupd.setText(prefs.getString("T" + spin1.getSelectedItem().toString().substring(0, 3) + spin2.getSelectedItem().toString().substring(0, 3), ""));
			        	kurs = Double.parseDouble(jsonRes.getText().toString());
		        	}
		        	catch(Exception e)
		        	{
		        		jsonRes.setText("");
		        		tvupd.setText(R.string.nodata);
		        	}
		        }
		        if(listGeted)
		        {
		        	Editor edit = prefs.edit();
					edit.putString("spin1", ""+spin1.getSelectedItemPosition());
					edit.commit();
		        }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0){}
		});
        
        spin2.setOnItemSelectedListener(new OnItemSelectedListener()
        {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3)
			{
				try
				{
					((TextView) parent.getChildAt(0)).setText(spin2.getSelectedItem().toString().substring(0, 3));
				}
				catch(NullPointerException e){}
				
		        if(chnet.connet(cm))
		        	currencies.getCurrency(spin1.getSelectedItem().toString().substring(0, 3), spin2.getSelectedItem().toString().substring(0, 3));
		        else
		        {
		        	try
		        	{
		        		jsonRes.setText(prefs.getString(spin1.getSelectedItem().toString().substring(0, 3) + spin2.getSelectedItem().toString().substring(0, 3), ""));
			        	tvupd.setText(prefs.getString("T" + spin1.getSelectedItem().toString().substring(0, 3) + spin2.getSelectedItem().toString().substring(0, 3), ""));
			        	kurs = Double.parseDouble(jsonRes.getText().toString());
		        	}
		        	catch(Exception e)
		        	{
		        		jsonRes.setText("");
		        		tvupd.setText(R.string.nodata);
		        	}
		        }
		        if(listGeted)
		        {
		        	Editor edit = prefs.edit();
					edit.putString("spin2", ""+spin2.getSelectedItemPosition());
					edit.commit();
		        }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0){}
		});
        
        //refresh();

        return view;
    }
	
    public void setupDialog()
    {
    	loadingData.setTitle(R.string.loadingTitle);
    	loadingData.show();
    }

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.convertbtn:
			try
			{
				firstValue = new BigDecimal(etone.getText().toString());
				secondValue = new BigDecimal(kurs);
				firstValue = firstValue.multiply(secondValue);
				Log.d("Error", "1");
				if(firstValue.compareTo(firstValue.setScale(0, RoundingMode.UP)) == 0)
					ettwo.setText("" + firstValue.setScale(0, RoundingMode.UP));
				else
					ettwo.setText("" + firstValue.setScale(6, RoundingMode.UP).stripTrailingZeros());
			}
			catch(Exception e)
			{
				Log.d("Error", "" + e);
				Toast.makeText(getActivity(), R.string.errorvalues, Toast.LENGTH_LONG).show();
			}
			break;
			
		case R.id.swap1:
			first = etone.getText().toString();
			second = ettwo.getText().toString();
			temp = first;
			first = second;
			second = temp;
			etone.setText(first);
			ettwo.setText(second);
			break;
			
		case R.id.swap2:
			int temp = spin1.getSelectedItemPosition();
			spin1.setSelection(spin2.getSelectedItemPosition());
			spin2.setSelection(temp);
			break;
			
		case R.id.btnRefresh:
			refresh();
			break;
		}
	}
	
	public void refresh()
	{
		jsonRes.setText("loading");
		try
		{
	        if(chnet.connet(cm))
	        {
	        	if(!listGeted)
	        		currencies.getList();
	        	else
	        		currencies.getCurrency(spin1.getSelectedItem().toString().substring(0, 3), spin2.getSelectedItem().toString().substring(0, 3));
	        }
			else
			{
				Toast.makeText(getActivity(), R.string.noInternet, Toast.LENGTH_LONG).show();
	        	if(!listGeted)
	        		loadingDataOffline();
	        	if(!spin1.getSelectedItem().toString().isEmpty() && !spin2.getSelectedItem().toString().isEmpty())
	        		chnet.dateUp(false, prefs, spin1.getSelectedItem().toString().substring(0, 3) + spin2.getSelectedItem().toString().substring(0, 3));
			}
		}
		catch(Exception e){}
	}
	
	private void loadingDataOffline()
	{
		int listSize = 0;
		try
		{
			listSize = Integer.parseInt(prefs.getString("ListSize", ""));
		}
		catch(Exception e){}
		
		int prev = 0;
		int next = 0;
		String list = prefs.getString("List", "");
		char[] listch = list.toCharArray();
		for(int i = 0; i < listSize-1; i++)
		{
			while(true)
			{
				if(listch[next] == '$')
    			{
					curr.add(list.substring(prev, next));
					next = next + 1;
					prev = next;
					break;
    			}
				else
					next++;
			}
		}
		
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, curr);
		spin1.setAdapter(adapter);
		spin2.setAdapter(adapter);
		listGeted = true;
		
        if(prefs.contains("spin1") && prefs.contains("spin2"))
        {
        	spin1.setSelection(Integer.parseInt(prefs.getString("spin1","")));
            spin2.setSelection(Integer.parseInt(prefs.getString("spin2","")));
        }
	}
}