/*
 * Copyright 2011 Thomas Niederberger
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.zoecontroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Joystick extends View implements Runnable 
{
    private OnJoystickChangeListener listener;
    public long repeatInterval;
    private Thread t;
    
    private int padding = 10;
    private int joyStickBaseColor;
    private int buttonColor;
    private int buttonRadius = 30;
    private int joystickRadius = 100;
    private double centerX = 0;
    private double centerY = 0;
    private int joystickX=0;
    private int joystickY=0;
    private boolean isFirstDraw = true;
    
    public boolean isVerticalStyle = true;
    
    public int joystickMotionState = MotionEvent.ACTION_UP;
    
    Paint joystickPaint;
    
    RadialGradient buttonGradient;
    LinearGradient joystickBaseGradientForVerticalStyle;
    LinearGradient joystickBaseGradientForHorizontalStyle;
    
    public Joystick(Context context) {
        super(context);
    }

    public Joystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        joyStickBaseColor = Color.BLACK;
        buttonColor = Color.RED;
        buttonRadius = 30;
        
        joystickPaint = new Paint();
        
        joystickBaseGradientForVerticalStyle = new LinearGradient(0, 0, 20, 0, Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR);
        
        joystickBaseGradientForHorizontalStyle = new LinearGradient(0, 20, 0, 0, Color.WHITE, Color.BLACK, Shader.TileMode.MIRROR);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
    	return 2*padding + buttonRadius + 2*joystickRadius;
    }

    private int measureHeight(int measureSpec) {
    	return 2*padding + buttonRadius + 2*joystickRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        centerX = (getWidth())/2;
        centerY = (getHeight())/2;
       
        joystickPaint.setStyle(Paint.Style.FILL);        
        joystickPaint.setColor(joyStickBaseColor);
        joystickPaint.setAlpha(80);
        
        if (this.isVerticalStyle)
        {
        	joystickPaint.setShader(joystickBaseGradientForVerticalStyle);
        	canvas.drawRect((float)centerX - buttonRadius/2, 0, (float)centerY + buttonRadius/2, 2*padding + buttonRadius + 2*joystickRadius, joystickPaint);
        }
        else
        {
        	joystickPaint.setShader(joystickBaseGradientForHorizontalStyle);
        	canvas.drawRect(0, (float)centerY - buttonRadius/2, 2*padding + buttonRadius + 2*joystickRadius, (float)centerX + buttonRadius/2, joystickPaint);
        }
   
        joystickPaint.setColor(buttonColor);
        joystickPaint.setStyle(Paint.Style.FILL);
        
        if (isFirstDraw)
        {
        	joystickX = (int) centerX;
    		joystickY = (int) centerY;
    		
    		buttonGradient = new RadialGradient((int)joystickX + 5, (int)joystickY + 10, buttonRadius, Color.WHITE,
                    Color.BLACK, android.graphics.Shader.TileMode.CLAMP);
    		
    		joystickPaint.setShader(buttonGradient);
    		
        	canvas.drawCircle(joystickX, joystickY, buttonRadius, joystickPaint);
        	isFirstDraw = false;
        }
        else
        {
        	
        	buttonGradient = new RadialGradient((int)joystickX + 5, (int)joystickY + 10, buttonRadius, Color.WHITE,
                    Color.BLACK, android.graphics.Shader.TileMode.CLAMP);
    		
        	joystickPaint.setShader(buttonGradient);
        	
        	canvas.drawCircle(joystickX, joystickY, buttonRadius, joystickPaint);
        	
        	//The value has to be converted into milliseconds
        	repeatInterval = 1000 * Integer.parseInt(ZCPreference.preferences.getString(Constants.commandRateKey, Constants.basRateOfCommand));
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	
    	joystickMotionState = event.getAction();
    	
    	if (this.isVerticalStyle)
    	{
    		joystickY = (int) event.getY();
    	}
    	else
    	{
    		joystickX = (int) event.getX();
    	}
    	
        double abs = Math.sqrt((joystickX-centerX)*(joystickX-centerX) + (joystickY-centerY)*(joystickY-centerY));
        
        if (abs > joystickRadius) 
        {
        	if (this.isVerticalStyle)
        	{
        		joystickY = (int) ((joystickY-centerY)*joystickRadius/abs + centerY);
        	}
        	else
        	{
        		joystickX = (int) ((joystickX-centerX)*joystickRadius/abs + centerX);
        	}
        }
    	invalidate();
    	if (event.getAction() == MotionEvent.ACTION_UP) {
    		joystickX = (int) centerX;
    		joystickY = (int) centerY;
    		t.interrupt();
    		listener.onStateChanged(joystickMotionState, isVerticalStyle);
    	}
    	if (listener != null && event.getAction() == MotionEvent.ACTION_DOWN) {
    		if (t != null && t.isAlive()) {
    			t.interrupt();
    		}
    		t = new Thread(this);
    		t.start();
    	}
    	return true;
    }
    
    public double getXValue() {
    	return (joystickX-centerX)/joystickRadius;
    	
    }
    
    public double getYValue() {
    	return (centerY-joystickY)/joystickRadius;
    }   

    public void setOnJoystickChangeListener(OnJoystickChangeListener listener) {
    	this.listener = listener;
    }
    
    public static interface OnJoystickChangeListener {
    	public void onStateChanged(int event, boolean style);
    }
    

    @Override
    public void run() {
    	while (!Thread.interrupted()) {
    		post(new Runnable() {
                public void run() {
                	listener.onStateChanged(joystickMotionState, isVerticalStyle);
                }
            });
    		try {
				Thread.sleep(repeatInterval);
			} catch (InterruptedException e) {
				break;
			}
    	}
    }
}

