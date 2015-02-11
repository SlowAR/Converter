package by.slowar.converter;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;

public class Main extends FragmentActivity 
{
	PagerAdapter adapter;
	ViewPager mPager;
	Main main;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main);
		
		main = this;
		
		mPager = (ViewPager) findViewById(R.id.pager);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setDrawFullUnderline(true);
	    
        TitleAdapter titleAdapter = new TitleAdapter(getSupportFragmentManager());
        mPager.setAdapter(titleAdapter);
        mPager.setCurrentItem(0);
        
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
			@Override
			public void onPageSelected(int arg0) 
			{
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		    	imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
	}
	
	public class TitleAdapter extends FragmentPagerAdapter 
	{
		private final String titles[] = getResources().getStringArray(R.array.data); 
	    private final Fragment frags[] = new Fragment[titles.length];
	 
	    public TitleAdapter(FragmentManager fm) 
	    {
	        super(fm);
	        frags[0] = new Converter((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE), getPreferences(MODE_PRIVATE), main);
	        frags[1] = new Calculator(main);
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
}