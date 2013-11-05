package com.example.zoecontroller;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class ConfigureParametersDialog extends AlertDialog implements
android.view.View.OnClickListener {
	
	private final String maxmiumSpeed = "Maximum Speed - ";
	private final String time = "Time - ";
	private final String rateOfCommand = "Rate of command - ";
	private final String rateOfImage = "Rate of image - ";
	
	private final String speedUnit = "cm/s";
	private final String unit = "s";
	
	private SeekBar seekBar1;
	private SeekBar seekBar2;
	private SeekBar seekBar3;
	private SeekBar seekBar4;
	
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	
	private int minimum1;
	private int minimum2;
	private int minimum3;
	private int minimum4;
	
	private int maximum1;
	private int maximum2;
	private int maximum3;
	private int maximum4;
	
	public ConfigureParametersDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		LayoutInflater inflater = this.getLayoutInflater();
		View layout = inflater.inflate(R.layout.dialog_configureparameters, null);
		
		this.setView(layout);
	}

	public ConfigureParametersDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public ConfigureParametersDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);

		((Button)findViewById(R.id.button1)).setOnClickListener(this);
		
		getCurrentValues();
		setCurrentValues();
		setMaximumBoundary();
     }
	
	@Override
	public void onClick(View view) 
	{
		String speed = Integer.toString((seekBar1).getProgress());
		ZCPreference.setValueForString(Constants.speedKey, speed);
		
		String time = Integer.toString((seekBar2).getProgress());
		ZCPreference.setValueForString(Constants.timeKey, time);
		
		String rateOfCommand = Integer.toString((seekBar3).getProgress());
		ZCPreference.setValueForString(Constants.commandRateKey, rateOfCommand);
		
		String rateOfImage = Integer.toString((seekBar4).getProgress());
		ZCPreference.setValueForString(Constants.imageRateKey, rateOfImage);
		
		System.out.println("Dismissed");
		
	    dismiss();
	}
	
	private void getCurrentValues()
	{
		seekBar1 = (SeekBar)findViewById(R.id.seekBar1);
		seekBar2 = (SeekBar)findViewById(R.id.seekBar2);
		seekBar3 = (SeekBar)findViewById(R.id.seekBar3);
		seekBar4 = (SeekBar)findViewById(R.id.seekBar4);
		
		textView1 = (TextView)findViewById(R.id.textView1);
		textView2 = (TextView)findViewById(R.id.textView2);
		textView3 = (TextView)findViewById(R.id.textView3);
		textView4 = (TextView)findViewById(R.id.textView4);
		
		minimum1 = Integer.parseInt(ZCPreference.preferences.getString(Constants.minSpeedKey, Constants.minRange));
		minimum2 = Integer.parseInt(ZCPreference.preferences.getString(Constants.minTimeKey, Constants.minRange));
		minimum3 = Integer.parseInt(ZCPreference.preferences.getString(Constants.minCommandRateKey, Constants.minRange));
		minimum4 = Integer.parseInt(ZCPreference.preferences.getString(Constants.minImageRateKey, Constants.minRange));
		
		maximum1 = Integer.parseInt(ZCPreference.preferences.getString(Constants.maxSpeedKey, Constants.maxRange));
		maximum2 = Integer.parseInt(ZCPreference.preferences.getString(Constants.maxTimeKey, Constants.maxRange));
		maximum3 = Integer.parseInt(ZCPreference.preferences.getString(Constants.maxCommandRateKey, Constants.maxRange));
		maximum4 = Integer.parseInt(ZCPreference.preferences.getString(Constants.maxImageRateKey, Constants.maxRange));
	}
	
	private void setCurrentValues()
	{		
		String maxspeed = ZCPreference.preferences.getString(Constants.speedKey, Constants.maxRange);
		(seekBar1).setProgress(Integer.parseInt(maxspeed));
		
		setListenerForSpeed();
		
		(textView1).append(" " + (maxspeed) + speedUnit);

		String maxtime = ZCPreference.preferences.getString(Constants.timeKey, Constants.baseTime);
		(seekBar2).setProgress(Integer.parseInt(maxtime));
		
		setListenerForTime();
		
		(textView2).append(" " + (maxtime) + unit);

		String maxrateOfCommand = ZCPreference.preferences.getString(Constants.commandRateKey, Constants.basRateOfCommand);
		(seekBar3).setProgress(Integer.parseInt(maxrateOfCommand));
		
		setListenerForRateOfCommand();
		
		(textView3).append(" " + (maxrateOfCommand) + unit);

		String maxrateOfImage = ZCPreference.preferences.getString(Constants.imageRateKey, Constants.baseRateOfImage);
		(seekBar4).setProgress(Integer.parseInt(maxrateOfImage));
		
		setListenerForRateOfImage();
		
		(textView4).append(" " + (maxrateOfImage) + unit);
	}
	
	private void setMaximumBoundary()
	{
		(seekBar1).setMax(maximum1 - minimum1);

		(seekBar2).setMax(maximum2 - minimum2);
		
		(seekBar3).setMax(maximum3 - minimum3);

		(seekBar4).setMax(maximum4 - minimum4);
	}
	
	private void setListenerForSpeed()
	{
		(seekBar1).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

		    @Override
		    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        (textView1).setText(maxmiumSpeed + (progress + minimum1) + speedUnit);
		    }

		    @Override
		    public void onStartTrackingTouch(SeekBar seekBar) {

		    }

		    @Override
		    public void onStopTrackingTouch(SeekBar seekBar) {

		    }
		});
	}
	
	private void setListenerForTime()
	{
		(seekBar2).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

		    @Override
		    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        (textView2).setText(time + (progress + minimum2) + unit);
		    }

		    @Override
		    public void onStartTrackingTouch(SeekBar seekBar) {

		    }

		    @Override
		    public void onStopTrackingTouch(SeekBar seekBar) {

		    }
		});
	}
	
	private void setListenerForRateOfCommand()
	{
		(seekBar3).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

		    @Override
		    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        (textView3).setText(rateOfCommand + (progress + minimum3) + unit);
		    }

		    @Override
		    public void onStartTrackingTouch(SeekBar seekBar) {

		    }

		    @Override
		    public void onStopTrackingTouch(SeekBar seekBar) {

		    }
		});
	}
	
	private void setListenerForRateOfImage()
	{
		(seekBar4).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

		    @Override
		    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        (textView4).setText(rateOfImage + (progress + minimum4) + unit);
		    }

		    @Override
		    public void onStartTrackingTouch(SeekBar seekBar) {

		    }

		    @Override
		    public void onStopTrackingTouch(SeekBar seekBar) {

		    }
		});
	}
}

