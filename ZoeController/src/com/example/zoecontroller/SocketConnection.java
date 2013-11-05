package com.example.zoecontroller;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class SocketConnection {

	//Socket connection elements
	Socket socket;
	BufferedReader input;
	PrintWriter output;		

	private SocketConnectionStateChangeListener listener;

	//Constant values
	public static final String serverIPKey = "serverIP";
	public static final String portKey = "port";

	@SuppressLint("HandlerLeak")
	Handler connectionHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if (msg.arg1 == 1)
			{
				listener.socketStateChange(Constants.socketConnectionState.ESTABLISHED);
			}
			else
			{
				listener.socketStateChange(Constants.socketConnectionState.FAILED);
			}

		}
	};

	public boolean isConnectionAvailable()
	{
		return (socket == null || !socket.isConnected() || socket.isClosed());
	}

	public void connect()
	{

		System.out.println("Connect pressed");

		if (socket == null || !socket.isConnected() || socket.isClosed()) 
		{
			Thread thread = new Thread(new Runnable(){
				@Override
				public void run() {
					try
					{
						String IPAddress = ZCPreference.preferences.getString(serverIPKey, Constants.defaultServer);
						String port = ZCPreference.preferences.getString(portKey, Constants.defaultport);

						System.out.println("Attempting to connect to "+ IPAddress + port);

						socket = new Socket();
						socket.connect(new InetSocketAddress(IPAddress, Integer.parseInt(port)), 15000);

						Message msg = new Message();
						msg.arg1 = 1;

						connectionHandler.sendMessage(msg);

						input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

						output = new PrintWriter(socket.getOutputStream());

						new SocketReader().execute(input);
					}
					catch (Exception e)
					{
						Message msg = new Message();
						msg.arg1 = 0;

						connectionHandler.sendMessage(msg);

						System.out.println("Connection problem "+ e);
						e.printStackTrace();
					}
				}
			});

			thread.start(); 
		}
		else
		{
			System.out.println("Connection already established \n");
		}
	}
	
	public void disconnect()
	{
		if (socket != null)
		{
		try
		{
			socket.close();
		}
		catch (Exception e)
		{
			
		}
		}
	}

	public void sendMessage(JSONObject message)
	{
		if (socket == null || !socket.isConnected() || socket.isClosed())
		{
			listener.socketStateChange(Constants.socketConnectionState.NO_CONNECTION);
			System.out.println("Please establish connection first to send message. Press connect button.\n");
		}
		else
		{
			try
			{
				System.out.println("Message " + message);

				output.println(message);
				output.flush();
			}
			catch (Exception e)
			{
				System.out.println("Message writing problem "+ e);
				e.printStackTrace();
			}
		}
	}

	private class SocketReader extends AsyncTask<BufferedReader, String, String> 
	{
		/* To update text view as and when receiving input from socket */

		protected void onProgressUpdate(String... values) 
		{
			if (values[0] != null)
			{
				System.out.println(values[0]);
				System.out.println("\n");
			}
			else
			{
				listener.socketStateChange(Constants.socketConnectionState.CLOSED);

				System.out.println("Connection with server is lost");
				System.out.println("\n");

				try
				{
					socket.close();
				}
				catch (Exception e)
				{

				}
			}

			System.out.println("UI updated");
		}

		/* Just implemented and no use as of now */

		protected void onPostExecute(String result) 
		{
			System.out.println("Result obtained");    	
		}

		/* To read data from input from socket continuously in the background*/

		@Override
		protected String doInBackground(BufferedReader... arg0) {
			// TODO Auto-generated method stub
			try 
			{
				System.out.println("Waiting for response");

				while(true)
				{
					String msgFromInput = arg0[0].readLine();
					publishProgress(msgFromInput);

					if (msgFromInput == null)
					{
						return null;
					}
				}
			}
			catch (IOException e) 
			{
				System.out.println("Inside AsyncTask background");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	public void setSocketConnectionStateChangeListener(SocketConnectionStateChangeListener listener) {
		this.listener = listener;
	}

	public static interface SocketConnectionStateChangeListener {
		public void socketStateChange(Constants.socketConnectionState state);
	}
}
