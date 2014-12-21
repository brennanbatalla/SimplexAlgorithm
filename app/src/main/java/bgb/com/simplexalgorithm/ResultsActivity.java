package bgb.com.simplexalgorithm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ResultsActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

		Simplex	s = (Simplex)getIntent().getParcelableExtra("Solution");
	    double[] x = s.primal();
		double[] c = s.c;
	    String solution = "Solution is ";
	    double optimalValue = 0;
	    for (int i = 0; i < x.length; i++) {
		    optimalValue +=  c[i]*x[i];
		    Log.e("Primal: ", "x[" + i + "] = " + x[i]);
		    solution += "x" + i + " = " + x[i] +", ";
	    }

	    solution = solution.substring(0,solution.length()-1);

	    double[] y = s.dual();
	    for (int j = 0; j < y.length; j++)
		    Log.e("Dual: ","y[" + j + "] = " + y[j]);

	   // Cheers(solution);
	   // Cheers("Optimal: " + optimalValue);
		TextView tv_solution = (TextView) findViewById(R.id.solution);
	    tv_solution.setText("solution" + " " + optimalValue);
    }
}
