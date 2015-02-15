package by.slowar.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

public class Currencies extends Activity
{
	Converter converter;
	CheckInternet chnet;
	SharedPreferences prefs;
	String link;
	String value1 = "USD";
	String value2 = "EUR";
	
	ArrayAdapter<String> adapter;
	ArrayList<String> curr = new ArrayList<String>();
	
	public Currencies(Converter converter, SharedPreferences prefs)
	{
		this.converter = converter;
		this.prefs = prefs;
		chnet = new CheckInternet(converter);
	}
	
	public void getCurrency(String value1, String value2)
	{
		this.value1 = value1;
		this.value2 = value2;
		new GetCurrency().execute();
	}
	
    public void getList()
	{
		new CurrencyList().execute();
	}
    
	//https://www.google.com/finance/converter?a=1000&from=USD&to=RUB
	private class GetCurrency extends AsyncTask<Void, Void, Void>
    {
		String result;
		
        @Override
        protected void onPreExecute()
        {
        	super.onPreExecute();
        	link = "https://www.google.com/finance/converter?a=";
        	link = link + "1" + "&from=" + value1 + "&to=" + value2;
        }
        
        @Override
        protected Void doInBackground(Void... params)
        {
			try
			{
				Document doc = Jsoup.connect(link).get();
				result = doc.select("span[class=bld]").text();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			return null;
        }
 
        @Override
        protected void onPostExecute(Void res) 
        {
        	try
        	{
        		String currency = "";
            	for(int i = 0; i < result.length(); i++)
            	{
            		if(Character.isDigit(result.charAt(i)) || result.charAt(i) == '.')
                	{
                		currency = currency + result.charAt(i);
                	}
            	}
            	
            	if(currency == "")
            	{
            		converter.kurs = 1;
            		converter.jsonRes.setText("" + converter.kurs);
            		chnet.dateUp(true, prefs, converter.spin1.getSelectedItem().toString().substring(0, 3) + converter.spin2.getSelectedItem().toString().substring(0, 3));
            	}
            	else
            	{
            		converter.kurs = Double.parseDouble(currency);
            		converter.jsonRes.setText(currency);
            		chnet.dateUp(true, prefs, converter.spin1.getSelectedItem().toString().substring(0, 3) + converter.spin2.getSelectedItem().toString().substring(0, 3));
            	}
            	
            	converter.loadingData.dismiss();
        	}
        	catch(NullPointerException e)
        	{
        		new GetCurrency().execute();
        	}
        }
    }
	
	private class CurrencyList extends AsyncTask<Void, Void, String>
    {
        HttpURLConnection openUrl = null;
        BufferedReader reader = null;
        String resultJson = " ";
        
        @Override
        protected void onPreExecute()
        {
        	super.onPreExecute();
        	converter.setupDialog();
        }
        
        @Override
        protected String doInBackground(Void... params)
        {
        	try 
            {
                URL url = new URL("https://raw.githubusercontent.com/mhs/world-currencies/master/currencies.json");
 
                openUrl = (HttpURLConnection) url.openConnection();
                openUrl.setRequestMethod("GET");
                openUrl.connect();
 
                InputStream inputStream = openUrl.getInputStream();
                StringBuffer buffer = new StringBuffer();
 
                reader = new BufferedReader(new InputStreamReader(inputStream));
 
                String line;
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
 
                resultJson = buffer.toString();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        	
    		return resultJson;
        }
 
        @Override
        protected void onPostExecute(String strJson) 
        {
            super.onPostExecute(strJson);
            
            JSONObject jobj = null;
            String currLine = "";
 
            try 
            {
            	JSONArray jaray = new JSONArray(strJson);
            	for(int i = 0; i<jaray.length(); i++)
            	{
            		StringBuilder sb = new StringBuilder();
            		jobj = jaray.getJSONObject(i);
            		currLine = jobj.getString("cc");
            		sb.append(currLine);
            		sb.append(", ");
            		currLine = jobj.getString("name");
            		sb.append(currLine);
            		curr.add(sb.toString());
            	}
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            
            adapter = new ArrayAdapter<String>(converter.getActivity(), android.R.layout.simple_list_item_1, curr);
            converter.spin1.setAdapter(adapter);
    		converter.spin2.setAdapter(adapter);
    		
    		String list = "";
    		Editor edit = prefs.edit();
    		for(int i = 0; i < curr.size()-1; i++)
    		{
    			//list = list + curr.get(i).substring(0, 3);
    			list = list + curr.get(i);
    			list = list + '$';
    		}
    		Log.d("Crashing", list);
    		edit.putString("List", list);
    		edit.putString("ListSize", "" + curr.size());
			edit.commit();
			converter.listGeted = true;
			
	        if(prefs.contains("spin1") && prefs.contains("spin2"))
	        {
	        	converter.spin1.setSelection(Integer.parseInt(prefs.getString("spin1","")));
	            converter.spin2.setSelection(Integer.parseInt(prefs.getString("spin2","")));
	        }
	        
	        new GetCurrency().execute();
        }
    }
}