package com.connal.dyscalculaid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    int count = 0;
    static double total = 0;
    static ArrayList money = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getMoney();
    }


    protected void onStart(){

        super.onStart();
        if(money.size() == 0) {
            total = 0;
            getMoney();
        }
        else{
            ArrayList newMoney = new ArrayList(money);
            total = 0;
            money = new ArrayList();
            for(int i = 0; i < newMoney.size(); i++){
                refreshMoney(newMoney.get(i));
            }
        }
    }
    protected void onResume(){

        super.onResume();
      //  getMoney();
    }

    protected void onDestroy(){


        super.onDestroy();
        writeMoney();
    }

    protected void onPause(){

        super.onPause();
        writeMoney();
    }

    protected  void onStop(){

        super.onStop();
        writeMoney();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMoney(View view){

        Object tag =  view.getTag();
        ImageView next = new ImageView(this);
        count += Operations.getPixels(tag.toString());
        LinearLayout layout = (LinearLayout) findViewById(Operations.getWallet(count));

        int pic = Operations.getPic(tag.toString());
        next.setImageResource(pic);
        next.setTag(tag);
        layout.addView(next);
        TextView display = (TextView) findViewById(R.id.display);
        total += Operations.getValue(tag.toString());
        money.add(tag);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String disp = "Current total: " + (format.format(total));
        display.setText(disp);

    }

    public void refreshMoney(Object tag){

        ImageView next = new ImageView(this);
        count += Operations.getPixels(tag.toString());
        LinearLayout layout = (LinearLayout) findViewById(Operations.getWallet(count));

        int pic = Operations.getPic(tag.toString());
        next.setImageResource(pic);
        next.setTag(tag);
        layout.addView(next);
        TextView display = (TextView) findViewById(R.id.display);
        total += Operations.getValue(tag.toString());
        money.add(tag);
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String disp = "Current total: " + (format.format(total));
        display.setText(disp);
    }

    public void delete(View view){

        LinearLayout layout = (LinearLayout) findViewById(Operations.getWallet(count));
        int child = layout.getChildCount() - 1;
        if(child != -1) {

            View last = layout.getChildAt(child);
            Object tag = last.getTag();
            count -= Operations.getPixels(tag.toString());

            TextView display = (TextView) findViewById(R.id.display);
            total -= Operations.getValue(tag.toString());
            NumberFormat f = NumberFormat.getCurrencyInstance();
            String disp = "Current total: " + (f.format(total));
            display.setText(disp);
            money.remove(money.size() - 1);
            layout.removeView(last);
        }
    }

    public void clear(View view) {

        money = new ArrayList();
        count = 0;
        total = 0;
        LinearLayout layout = (LinearLayout) findViewById(R.id.wallet1);
        layout.removeAllViewsInLayout();

        layout = (LinearLayout) findViewById(R.id.wallet2);
        layout.removeAllViewsInLayout();
        layout = (LinearLayout) findViewById(R.id.wallet3);
        layout.removeAllViewsInLayout();
        layout = (LinearLayout) findViewById(R.id.wallet4);
        layout.removeAllViewsInLayout();
        layout = (LinearLayout) findViewById(R.id.wallet5);
        layout.removeAllViewsInLayout();

        TextView display = (TextView) findViewById(R.id.display);
        NumberFormat f = NumberFormat.getCurrencyInstance();
        String disp = "Current total: " + (f.format(total));
        display.setText(disp);
    }

    public void getMoney(){

        ArrayList newMoney;
        //money = new ArrayList();
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        String prevMoney = sp.getString("savedMoney", "");
        count = 0;
        total = 0;

        if (!prevMoney.equals("")) {

            newMoney = Operations.convertToMoney(prevMoney, this);
            for(int i = 0; i < newMoney.size(); i++)
                refreshMoney(newMoney.get(i));

           //TextView display = (TextView) findViewById(R.id.display);
            //display.setText(prevMoney);
        }
    }

    public void writeMoney(){

        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString("savedMoney", money.toString());
        spe.commit();

    }

    public void goToCheckout(View view){

        Intent intent = new Intent(this, Checkout.class);
        writeMoney();
        startActivity(intent);
    }
}