package bgb.com.simplexalgorithm;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EquationInput extends ActionBarActivity {

	MaterialEditText et_constraints, et_objFunction;
	List<EditText> constraintsStringList;
	LinearLayout mLinearLayout;
	LinearLayout.LayoutParams lp;
	Button btn_solve;

    String solveMode;
	double[]   c;
	double[][] A;
	double[]   b;
	int numVariables;
	int numConstraints;
	boolean VALID_INPUTS;
	Simplex s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equationInput);

        // Set up inputs and buttons
        Initialize();

        numVariables =  Integer.parseInt(getIntent().getExtras().get("numVar").toString());  // Get number of variables from input activity
        numConstraints =  Integer.parseInt(getIntent().getExtras().get("numCon").toString()); // Get number of constraints from input activity
        solveMode = getIntent().getExtras().get("solveMode").toString();  // will be used to find min or max.

        generateInputs();

		btn_solve.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkInputs();
				if(VALID_INPUTS) {
					solveProblem();
					Intent i = new Intent(getApplicationContext(),ResultsActivity.class);
					Bundle mBundle = new Bundle();
					mBundle.putParcelable("Solution", (Parcelable)s);
					i.putExtras(mBundle);
					startActivity(i);


				}
			}
		});
    }

	private void Initialize() {

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_objFunction = (MaterialEditText) findViewById(R.id.et_objectiveFunction);
		btn_solve = (Button) findViewById(R.id.solveButton);
		constraintsStringList = new ArrayList<>();
		mLinearLayout = (LinearLayout) findViewById(R.id.functionLayout);
		lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	}

	private void generateInputs() {
		Log.i("SimplexMain", "onClick/generateInputs");

			btn_solve.setVisibility(View.VISIBLE);
			displayFunctionInputs(numConstraints);

	}

	private void checkInputs() {
		if(et_objFunction.getText().toString().matches("")) {
			VALID_INPUTS = false;
			Cheers("Objective function cannot be empty!");
		} else {
			for(int i = 0; i < numConstraints; i++) {
				if(constraintsStringList.get(i).getText().toString().matches("")) {
					Cheers("Constraint inputs cannot be empty!");
					VALID_INPUTS = false;
					break;
				} else {
					for(int j = 0; j < numVariables; j++) {
						if(!constraintsStringList.get(i).getText().toString().contains("x"+j)) {
							VALID_INPUTS = false;
							Cheers("Constraint " + i + " is missing x" + j + "!");
						}
					}
				}
			}
		}
	}

	private void solveProblem() {
		Log.i("SimplexMain", "onClick/solveButton");

		// Get and convert objective function to string array elements
		String strObjFunction = et_objFunction.getText().toString();

		// Remove x1...xn from objective function
		for(int i = 0; i < numVariables; i++) {
			strObjFunction = strObjFunction.replace("x"+i,"");
		}

		strObjFunction = strObjFunction.replace("+-","-");
		strObjFunction = strObjFunction.replace("-","+-");
		String[] strArrayObjFunc = strObjFunction.split("\\+");

		Log.i("SimplexMain", "Coefficients: " + Arrays.toString(strArrayObjFunc));

		// Objective Function
		c = new double[numVariables];

		// Constraint coefficients
		A = new double[numConstraints][numVariables];

		//Constraint equals
		b = new double[numConstraints];

		for (int i = 0; i < strArrayObjFunc.length; i++) {
			c[i] = Double.parseDouble(strArrayObjFunc[i]);
		}

		String[] constraints = new String[numConstraints];
		String[] constraintsCoeff = new String[numConstraints];
		String[] constraintsEquals = new String[numConstraints];

		for (int i = 0; i < constraintsStringList.size(); i++) {
			constraints[i] = constraintsStringList.get(i).getText().toString();
			constraints[i] = constraints[i].replace("+-","-");
			constraints[i] = constraints[i].replace("-", "+-");
			if(constraints[i].startsWith("+")) {
				constraints[i] = constraints[i].substring(1);
			}

			for(int j = 0; j <numVariables; j++) {
				constraints[i] = constraints[i].replace("x"+j,"");
			}

			constraintsCoeff[i] = constraints[i].split("=")[0];
			constraintsEquals[i] = constraints[i].split("=")[1];

			if(constraintsEquals[i].startsWith("+")) {
				constraintsEquals[i] = constraintsEquals[i].substring(1);
			}
		}

		Log.i("SimplexMain", " ConstraintsCoeff: " + Arrays.toString(constraintsCoeff));
		Log.i("SimplexMain", "ConstraintsEquals: " + Arrays.toString(constraintsEquals));

		String temp[];
		for(int i = 0; i < numConstraints; i++) {
			temp = constraintsCoeff[i].split("\\+");
			b[i] =  Double.parseDouble(constraintsEquals[i]);
			for(int j = 0; j < numVariables; j++) {
				A[i][j] =  Double.parseDouble(temp[j]);
			}
		}

		Log.i("SimplexMain", "C:" + Arrays.toString(c));
		Log.i("SimplexMain", "b:" + Arrays.toString(b));
		for(int i = 0; i < numConstraints; i++) {
			Log.i("SimplexMain", "A[" + i + "]:" + Arrays.toString(A[i]));
		}

		s = new Simplex(A,b,c);

		double[] x = s.primal();

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

		Cheers(solution);
		Cheers("Optimal: " + optimalValue);
	}

	public void displayFunctionInputs(int c){

		if (mLinearLayout.getChildCount() > 1) {
			mLinearLayout.removeViews(1,mLinearLayout.getChildCount()-1);
		}

		for(int i = 1; i <= c; i++) {

			et_constraints = new MaterialEditText(new ContextThemeWrapper(EquationInput.this, R.style.constraintEditText));
			et_constraints.setLayoutParams(lp);
			et_constraints.setHint("Constraint " + i);
			mLinearLayout.addView(et_constraints);

			constraintsStringList.add(et_constraints);

		}

	}


	public void Cheers(String message){
		Toast.makeText(this, message,
				Toast.LENGTH_LONG).show();
	}

}



/*        public void test(double[][] A, double[] b, double[] c) {
            Simplex lp = new Simplex(A, b, c);
            Cheers("value = " + lp.optimalValueue());
            double[] x = lp.primal();
            for (int i = 0; i < x.length; i++)
                Cheers("x[" + i + "] = " + x[i]);
            double[] y = lp.dual();
            for (int j = 0; j < y.length; j++)
                StdOut.println("y[" + j + "] = " + y[j]);
        }

        public static void test1() {
            double[][] A = {
                    { -1,  1,  0 },
                    {  1,  4,  0 },
                    {  2,  1,  0 },
                    {  3, -4,  0 },
                    {  0,  0,  1 },
            };
            double[] c = { 1, 1, 1 };
            double[] b = { 5, 45, 27, 24, 4 };
            test(A, b, c);
        }


        // x0 = 12, x1 = 28, opt = 800
        public static void test2() {
            double[] c = {  13.0,  23.0 };
            double[] b = { 480.0, 160.0, 1190.0 };
            double[][] A = {
                    constraints[i] = constraints[i].replace("+-","-");
			constraints[i] = constraints[i].replace("-", "+-");{  5.0, 15.0 },
                    {  4.0,  4.0 },
                    { 35.0, 20.0 },
            };
            test(A, b, c);
        }

        // unbounded
        public static void test3() {
            double[] c = { 2.0, 3.0, -1.0, -12.0 };
            double[] b = {  3.0,   2.0 };
            double[][] A = {
                    { -2.0, -9.0,  1.0,  9.0 },
                    {  1.0,  1.0, -1.0, -2.0 },
            };
            test(A, b, c);
        }

        // degenerate - cycles if you choose most positive objective function coefficient
        public static void test4() {
            double[] c = { 10.0, -57.0, -9.0, -24.0 };
            double[] b = {  0.0,   0.0,  1.0 };
            double[][] A = {
                    { 0.5, -5.5, -2.5, 9.0 },
                    { 0.5, -1.5, -0.5, 1.0 },
                    { 1.0,  0.0,  0.0, 0.0 },
            };
            test(A, b, c);
        }



        // test client
        public static void main(String[] args) {

            try                           { test1();             }
            catch (ArithmeticException e) { e.printStackTrace(); }
            Cheers("--------------------------------");

            try                           { test2();             }
            catch (ArithmeticException e) { e.printStackTrace(); }
            StdOut.println("--------------------------------");

            try                           { test3();             }
            catch (ArithmeticException e) { e.printStackTrace(); }
            StdOut.println("--------------------------------");

            try                           { test4();             }
            catch (ArithmeticException e) { e.printStackTrace(); }
            StdOut.println("--------------------------------");


            int M = Integer.parseInt(args[0]);
            int N = Integer.parseInt(args[1]);
            double[] c = new double[N];
            double[] b = new double[M];
            double[][] A = new double[M][N];
            for (int j = 0; j < N; j++)
                c[j] = StdRandom.uniform(1000);
            for (int i = 0; i < M; i++)
                b[i] = StdRandom.uniform(1000);
            for (int i = 0; i < M; i++)
                for (int j = 0; j < N; j++)
                    A[i][j] = StdRandom.uniform(100);
            Simplex lp = new Simplex(A, b, c);
            StdOut.println(lp.optimalValueue());
        }*/