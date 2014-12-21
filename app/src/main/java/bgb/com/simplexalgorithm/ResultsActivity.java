package bgb.com.simplexalgorithm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ResultsActivity extends ActionBarActivity {

    ArrayList<Double> xValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);


        int xLength = (Integer) getIntent().getExtras().get("xLength");
       for(int i = 0; i <= xLength; i++){

           Double xValue = (Double) getIntent().getExtras().get("x"+i);
           xValues.add(xValue);
           Log.e("xValues", xValue.toString());

       }
        Double optimum = (Double) getIntent().getExtras().get("optimal");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
}
