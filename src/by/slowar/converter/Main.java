package by.slowar.converter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class Main extends FragmentActivity 
{
	ViewPager pager;
	PagerAdapter adapter;
	private ViewPager mPager;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main);
		
		mPager = (ViewPager) findViewById(R.id.pager);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setDrawFullUnderline(true);
	    
        TitleAdapter titleAdapter = new TitleAdapter(getSupportFragmentManager());
        mPager.setAdapter(titleAdapter);
        mPager.setCurrentItem(0);
	}
	
	public class TitleAdapter extends FragmentPagerAdapter 
	{
		private final String titles[] = getResources().getStringArray(R.array.data); 
	    private final Fragment frags[] = new Fragment[titles.length];
	 
	    public TitleAdapter(FragmentManager fm) 
	    {
	        super(fm);
	        frags[0] = new Converter((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE), getPreferences(MODE_PRIVATE));
	        frags[1] = new Calculator();
	    }
	 
	    @Override
	    public CharSequence getPageTitle(int position) 
	    {
	        return titles[position];
	    }
	 
	    @Override
	    public Fragment getItem(int position) 
	    {
	        return frags[position];
	    }
	 
	    @Override
	    public int getCount() 
	    {
	        return frags.length;
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(R.string.settings);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent intent = new Intent(this, Settings.class);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}
}