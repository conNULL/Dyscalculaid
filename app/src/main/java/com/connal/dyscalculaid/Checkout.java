package com.connal.dyscalculaid;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;


public class Checkout extends ActionBarActivity {

    static ArrayList given = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkout, menu);
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

    public void addToDisplay(View view){
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        TextView display = (TextView) findViewById(R.id.display);
        Button pressedButton = (Button) view;
        String dtext, btext;
        int i;

        dtext = (String)display.getText();
        if(dtext.equals("You do not have enough to pay for that!"))
            dtext = "$0.00";
        dtext = dtext.substring(dtext.indexOf("$") + 1, dtext.length());
        dtext = dtext.replace(",", "");
        double price = Double.parseDouble(dtext);
        double added = Double.parseDouble((String)pressedButton.getText());

        price *=10;
        added /= 100;

        price += added;
        //dtext = "$" + String.valueOf(price);
       // display.setText(dtext);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        dtext = (format.format(price));
        display.setText(dtext);
    }

    public void delete(View view){

        TextView display = (TextView) findViewById(R.id.display);
        Button pressedButton = (Button) view;
        String dtext, btext;
        int i;

        dtext = (String)display.getText();
        if(dtext.equals("You do not have enough to pay for that!"))
            dtext = "$0.00";
        dtext = dtext.substring(dtext.indexOf("$") + 1, dtext.length() - 1);
        dtext = dtext.replace(",", "");
        double price = Double.parseDouble(dtext);

        price /= 10;
        //display.setText(dtext);

        NumberFormat format = NumberFormat.getCurrencyInstance();
        dtext = (format.format(price));
        display.setText(dtext);

    }
    public void clear(View view){

        TextView display = (TextView) findViewById(R.id.display);
        given = new ArrayList();
        display.setText("$0.00");
    }

    public void findcb(View view){

        TextView display = (TextView) findViewById(R.id.display);
        String dtext = (String)display.getText();
        if(dtext.equals("You do not have enough to pay for that!")) {
            dtext = "$0.00";
        }else {
            dtext = dtext.substring(dtext.indexOf("$") + 1, dtext.length());
            dtext = dtext.replace(",", "");
            double price = Double.parseDouble(dtext);

            getcb(price, view);
        }
    }

    public void getcb(double price, View view){

        TextView display = (TextView) findViewById(R.id.display);
       // ArrayList given = new ArrayList();
        double paid = 0;
        Object next;
        if(MainActivity.total < price){

            display.setText("You do not have enough to pay for that!");
        } else if(view.getId() == R.id.checkouti){

                while (price > paid) {

                next = getNext(price - paid);
                paid += give(next);

            }

            //display.setText(given.toString());
            Intent intent = new Intent(this, Change.class);
            startActivity(intent);
        } else if(view.getId() == R.id.checkouto){

            while (price > paid) {

                next = checkcb(0);
                paid += give(next);

            }

            paid = clean(price, paid);
            Intent intent = new Intent(this, Change.class);
            startActivity(intent);
        }
    }

    public double clean(double price, double paid){


        Object last = null;
        int i = 0;

        while(paid  > price){

            last = given.get(0);
            paid -= Operations.getValue((String) last);
            given.remove(0);
            MainActivity.money.add(last);
        }

        if(paid < price) {
            given.add(last);
            paid += Operations.getValue((String) last);
        }

        return paid;


    }

    public Object checkcb(int s){

        Object[] cb = {"n", "d", "q", "loonie", "toonie", "five", "ten", "twenty", "fifty", "hun"};

        for(int i = s; i < cb.length; i++){

            if(MainActivity.money.contains(cb[i])){
                return cb[i];
            }
        }

        return null;
    }

    public Object getLargest(){
        Object[] cb = {"n", "d", "q", "loonie", "toonie", "five", "ten", "twenty", "fifty", "hun"};

        for(int i = cb.length - 1; i > -1; i--){

            if(MainActivity.money.contains(cb[i]))
                return cb[i];
        }
        return null;
    }

    public Object getNext(double price){

        Object next = null;

        if(price > 50 && next == null)
            next =  checkcb(9);
        if(price > 20 && next == null)
            next = checkcb(8);
        if(price > 10 && next == null)
            next = checkcb(7);
        if(price > 5 && next == null)
            next = checkcb(6);
        if(price > 2 && next == null)
            next = checkcb(5);
        if(price > 1 && next == null)
            next = checkcb(4);
        if(price > 0.25 && next == null)
            next = checkcb(3);
        if(price > 0.1 && next == null)
            next = checkcb(2);
        if(price > 0.05 && next == null)
            next = checkcb(1);
        if(next == null)
            next = checkcb(0);

        return next;
    }


    public double give(Object cb){

        MainActivity.money.remove(cb);
        given.add(cb);
        return Operations.getValue((String) cb);

    }


}
