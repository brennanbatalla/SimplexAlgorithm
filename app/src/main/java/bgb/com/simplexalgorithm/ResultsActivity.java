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
	    String constraints = "";
	    String solution = "";
	    double optimalValue = 0;

	    double[] x = s.primal();
		double[] c = s.c;
	    double[][] A = s.A;
	    double[] b = s.b;
	    for (int i = 0; i < x.length; i++) {
		    optimalValue +=  c[i]*x[i];
		    objFunc += c[i]+ "x" + i + "+";
		    Log.e("Primal: ", "x[" + i + "] = " + x[i]);
		    solution += "x" + i + " = " + x[i] +"\n";
	    }

	    objFunc = objFunc.substring(0, objFunc.length()-1);

	    int numVar = x.length;
	    int numCon = b.length;

	    for(int i = 0; i < numCon; i++) {
		    for (int j = 0; j < numVar; j++) {
				constraints += A[i][j] + "x" + i + "+";
		    }
		    constraints = constraints.substring(0,constraints.length()-1);
		    constraints += " <= " +  b[i] + "\n";
	    }

	    double[] y = s.dual();
	    for (int j = 0; j < y.length; j++)
		    Log.e("Dual: ","y[" + j + "] = " + y[j]);

	   // Cheers(solution);
	   // Cheers("Optimal: " + optimalValue);

	    // Set objective function
	    TextView tv_objFunc = (TextView) findViewById(R.id.resultObjFunc);
	    tv_objFunc.setText(objFunc);

		// Set contraints
	    TextView tv_constraints = (TextView) findViewById(R.id.constraints);
		tv_constraints.setText(constraints);

	    // Set solution
		TextView tv_solution = (TextView) findViewById(R.id.solutionValues);
	    tv_solution.setText(solution);

	    // Set optimal value
	    TextView tv_numericOptVal = (TextView) findViewById(R.id.numericOptimalValue);
	    tv_numericOptVal.setText(String.valueOf(optimalValue));
    }
}
