package bgb.com.simplexalgorithm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ResultsActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

		Simplex	s = Solution.s;
	    String objFunc = "";
	    double[] x = s.primal();
		double[] c = s.c;
	    String solution = "Solution is ";
	    double optimalValue = 0;
	    for (int i = 0; i < x.length; i++) {
		    optimalValue +=  c[i]*x[i];
		    objFunc += c[i]+ "x"+i;
		    Log.e("Primal: ", "x[" + i + "] = " + x[i]);
		    solution += "x" + i + " = " + x[i] +", ";
	    }

	    solution = solution.substring(0,solution.length()-1);

	    double[] y = s.dual();
	    for (int j = 0; j < y.length; j++)
		    Log.e("Dual: ","y[" + j + "] = " + y[j]);

	   // Cheers(solution);
	   // Cheers("Optimal: " + optimalValue);

	    // Set objective function
	    TextView tv_objFunc = (TextView) findViewById(R.id.resultObjFunc);
	    tv_objFunc.setText(objFunc);

	    // Set solution
		TextView tv_solution = (TextView) findViewById(R.id.solutionValues);
	    tv_solution.setText(solution + " " + optimalValue);

    }
}
