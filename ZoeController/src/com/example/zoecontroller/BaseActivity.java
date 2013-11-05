package com.example.zoecontroller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.zoecontroller.Joystick.OnJoystickChangeListener;
import com.example.zoecontroller.SocketConnection.SocketConnectionStateChangeListener;

public class BaseActivity extends Activity implements OnJoystickChangeListener, SocketConnectionStateChangeListener, Runnable{

	int windowwidth;
    int windowheight;
    
    double lastKnownSpeed;
    double lastKnownRadius;
    
    long repeatInterval;
    
    SocketConnection socket;
    
    MenuItem connectItem;
    
    Joystick speedJoystick;
    Joystick directionJoystick;
    
    Thread joystickMovement;
    
    boolean joystickMovementState;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		
		System.out.println("Activity.........");
		
		ZCPreference.preferences = PreferenceManager.getDefaultSharedPreferences(this);
		ZCPreference.resource = getResources();
		
		speedJoystick = (Joystick)findViewById(R.id.joystickView1);
		speedJoystick.setOnJoystickChangeListener(this);
		
		directionJoystick = (Joystick)findViewById(R.id.joystickView2);
		directionJoystick.isVerticalStyle = false;
		directionJoystick.setOnJoystickChangeListener(this);
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		
		System.out.println("Inside destroy");
		
		try
		{
			socket.disconnect();
		}
		catch(Exception e)
		{
			System.out.println("Problem in terminating connection from destroy");
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base, menu);
		
		connectItem = menu.findItem(R.id.item2);
		
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
	{		
		if (item.getTitle().equals(ZCPreference.getStringFor(R.string.action_battery)))
		{
			Toast.makeText(this, "Unavailable for now", Toast.LENGTH_SHORT).show();
		}
		else if (item.getTitle().equals(ZCPreference.getStringFor(R.string.action_connect)))
		{
			socket = new SocketConnection();
			socket.setSocketConnectionStateChangeListener(this);
			socket.connect();
		}
		else if (item.getTitle().equals(ZCPreference.getStringFor(R.string.action_settings)))
		{
			Intent intent = new Intent(BaseActivity.this,Settings.class);

            startActivity(intent);
		}
		
	    return super.onOptionsItemSelected(item);
	}
	
	public void configureBtnPressed(View view)
	{
		ConfigureParametersDialog configure = new ConfigureParametersDialog(this);
		configure.show();
	}

	public void dismissBtnPressed(View view)
	{
		System.out.println("Dismiss pressed");
	}
	
	public void onStateChanged(int event, boolean style)
	{
		System.out.println("State changed");
		
		if (joystickMovement == null)
		{
			System.out.println("Thread started");
			
			//The value has to be converted into milliseconds
        	repeatInterval = 1000 * Integer.parseInt(ZCPreference.preferences.getString(Constants.commandRateKey, Constants.basRateOfCommand));
        	
			joystickMovement = new Thread(this);
			joystickMovement.start();
			
			joystickMovementState = true;
		}
		
		if (speedJoystick.joystickMotionState == MotionEvent.ACTION_UP && directionJoystick.joystickMotionState == MotionEvent.ACTION_UP)
		{
			System.out.println("Thread stopped");
			
			joystickMovementState = false;
			
			driveParameters(speedJoystick.getYValue(), directionJoystick.getXValue());
			
			try
			{
				joystickMovement = null;
			}
			catch (Exception e)
			{
				
			}
		}
	}
	
	@Override
    public void run() {
    	while (joystickMovementState) {
    		try {
    			System.out.println("Post drive parameters");
    			driveParameters(speedJoystick.getYValue(), directionJoystick.getXValue());
				Thread.sleep(repeatInterval);
			} catch (InterruptedException e) {
				break;
			}
    	}
    }
	
	public void driveParameters(double speed, double radius)
	{
		if (socket == null)
		{
			return;
		}
		
		lastKnownRadius = radius;
		lastKnownSpeed = speed;
		
		sendDriveCommand();
	}
	
	private void sendDriveCommand()
	{
		if (lastKnownRadius == 0 && lastKnownSpeed == 0)
		{
			socket.sendMessage(JSONBuilder.messageForDrive(0, 0));
			return;
		}
		
		String maxspeed = ZCPreference.preferences.getString(Constants.speedKey, Constants.maxRange);
		int speed = Integer.parseInt(maxspeed);
		
		String maxradius = ZCPreference.preferences.getString(Constants.maxRadiusKey, Constants.maximumRange);
		String minradius = ZCPreference.preferences.getString(Constants.minRadiusKey, Constants.minRange);
		int radius = Integer.parseInt(maxradius) - Integer.parseInt(minradius);
		
		double radiusParam = Integer.parseInt(minradius) + (1 - Math.abs(lastKnownRadius)) * radius;
		
		if (lastKnownRadius < 0)
		{
			radiusParam *= -1;
		}
		
		System.out.println("Speed is " + (lastKnownSpeed * speed)/100.0 + "Radius is" + lastKnownRadius * radius);
		
		//Toast.makeText(this, "Speed is " + lastKnownSpeed * speed + "Radius is" + lastKnownRadius * radius, Toast.LENGTH_SHORT).show();
		
		socket.sendMessage(JSONBuilder.messageForDrive(lastKnownSpeed * speed, radiusParam));
	}
	
	public void socketStateChange(Constants.socketConnectionState state)
	{
		if (state == Constants.socketConnectionState.CLOSED)
		{
			connectItem.setIcon(R.drawable.icon_disconnected);
			//Toast.makeText(this, "Connection closed", Toast.LENGTH_SHORT).show();
		}
		else if (state == Constants.socketConnectionState.ESTABLISHED)
		{
			connectItem.setIcon(R.drawable.icon_connected);
			//Toast.makeText(this, "Connection established", Toast.LENGTH_SHORT).show();
		}
		else if (state == Constants.socketConnectionState.FAILED)
		{
			connectItem.setIcon(R.drawable.icon_disconnected);
			//Toast.makeText(this, "Connection failed", Toast.LENGTH_SHORT).show();
		}
		else if (state == Constants.socketConnectionState.NO_CONNECTION)
		{
			connectItem.setIcon(R.drawable.icon_disconnected);
			//Toast.makeText(this, "Unable to connect to server", Toast.LENGTH_SHORT).show();
		}
	}
}
