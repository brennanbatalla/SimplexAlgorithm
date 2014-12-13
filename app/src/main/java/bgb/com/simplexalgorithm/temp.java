package bgb.com.simplexalgorithm;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class simplexmain extends ActionBarActivity {


	EditText nConstraints;
	EditText nVariables;
	Button equGenBUTTON;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simplexmain);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

		setSupportActionBar(toolbar);
		//toolbar.setTitle("Simplex");

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(simplexmain.this,"FAB",Toast.LENGTH_SHORT).show();
			}
		});

		initialize();
		//Simplex now = new Simplex();

		equGenBUTTON.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.w("Next_Button", "You got here");
				//equGen();


				if (!nVariables.getText().toString().matches("") && Integer.parseInt(nVariables.getText().toString()) <= 6) {
					int variables = Integer.parseInt(nVariables.getText().toString());
					ObjFuncGen(getApplicationContext(), variables);
				} else if (nVariables.getText().toString().matches("")) {

					cheers("Please Input number of Variables");
				} else {
					cheers("Variables must be less than 7");
				}
			}
		});

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_simplexmain, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }
*/
		return super.onOptionsItemSelected(item);
	}


	public void ObjFuncGen(Context context, int variables){

		List<EditText> etArray = new ArrayList<>();

		for(int i = 1; i <= variables; i++) {

			LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.EditTextGen);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			EditText et = new EditText(context);
			et.setLayoutParams(lp);
			et.setSingleLine(false);
			et.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
			mLinearLayout.addView(et);
			etArray.add(et);  // Add to list



			TextView tv = new TextView(context);
			tv.setLayoutParams(lp);
			tv.setInputType(InputType.TYPE_CLASS_TEXT);
			mLinearLayout.addView(tv);

			switch(i){
				case 0:
					tv.setText("a1");
					break;
				case 1:
					tv.setText("a2");
					break;
				case 2:
					tv.setText("a3");
					break;
				case 3:
					tv.setText("a4");
					break;
				case 4:
					tv.setText("a5");
					break;
				case 5:
					tv.setText("a6");
					break;
				case 6:
					tv.setText("b");
					break;
				default:
					cheers("How did you get here?");
			}


			tv.setTextColor(Color.BLACK);

		}

	}

	public void initialize() {
		nConstraints = (EditText) findViewById(R.id.nConstraints);  // scscsdvsv
		nVariables = (EditText) findViewById(R.id.nVariables);
		equGenBUTTON = (Button) findViewById(R.id.equGen);

	}

	public void cheers(String message){
		Toast.makeText(this, message,
				Toast.LENGTH_LONG).show();
	}

	public void equGen(){

// i cant get it here

	}



}



/*        public void test(double[][] A, double[] b, double[] c) {
            Simplex lp = new Simplex(A, b, c);
            cheers("value = " + lp.value());
            double[] x = lp.primal();
            for (int i = 0; i < x.length; i++)
                cheers("x[" + i + "] = " + x[i]);
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
                    {  5.0, 15.0 },
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
            cheers("--------------------------------");

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
            StdOut.println(lp.value());
        }*/
