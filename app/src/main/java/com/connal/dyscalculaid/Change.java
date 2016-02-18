package com.connal.dyscalculaid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;


public class Change extends ActionBarActivity {

        int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        count = 0;
        giveChange();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change, menu);
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

    public void giveChange(){

        LinearLayout layout = (LinearLayout) findViewById(R.id.change1);
        layout.removeAllViews();
        layout = (LinearLayout) findViewById(R.id.change2);
        layout.removeAllViews();
        layout = (LinearLayout) findViewById(R.id.change3);
        layout.removeAllViews();
        layout = (LinearLayout) findViewById(R.id.change4);
        layout.removeAllViews();
        layout = (LinearLayout) findViewById(R.id.change5);
        layout.removeAllViews();
        count = 0;


        while(Checkout.given.size() != 0){
            loadcb(Checkout.given.get(0));
            Checkout.given.remove(0);
        }


    }

    public void loadcb(Object tag){

        ImageButton next = new ImageButton(this);
        count += Operations.getPixels((String)tag);
        LinearLayout layout = (LinearLayout) findViewById(Operations.getPay(count));

        int pic = Operations.getPic(tag.toString());
        next.setImageResource(pic);
        next.setTag(tag);
        layout.addView(next);
    }
}
