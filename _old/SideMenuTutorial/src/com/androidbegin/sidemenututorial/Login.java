package com.androidbegin.sidemenututorial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Login extends Activity
{
	ProgressDialog progDialog;
	String data = "";
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		// Get the view from drawer_main.xml
		setContentView(R.layout.login);
	}
	
	public void onLoginClicked(View v) 
	{
		final FetchDatabase getdb = new FetchDatabase();
		progDialog = ProgressDialog.show(this, "Carregando Informa��o",
	            "Recolhendo os Menus, Pratos, Sobremesas, Bebidas...", true);
		//progDialog.setCancelable(true);
		
	    new Thread() {
	        public void run() {
	            try 
	            {
	                data = getdb.getDataFromDB();
	                //System.out.println(data);
	                sleep(100);
	                runOnUiThread(new Runnable() {
	                	@Override
	                    public void run() {
	                        Common.users = FetchDatabase.parseJSON(data);
	                    }
	                });	
	            } 
	            catch (Exception e) 
	            {
	        		Toast.makeText(Login.this, "Loading failed. Check internet connections", Toast.LENGTH_LONG).show();
	            }
	            if(Common.infoLoaded)
	            {
	            	Intent intent = new Intent(Login.this, Menu.class);
	            	startActivity(intent);  
	            }
	            progDialog.dismiss();
	        }
	    }.start();
	    Toast.makeText(Login.this, "Check-in falhou. \nVerifique liga��o � rede", Toast.LENGTH_LONG).show();
	}
	
}