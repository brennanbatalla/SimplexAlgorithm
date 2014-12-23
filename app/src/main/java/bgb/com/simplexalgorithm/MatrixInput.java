package bgb.com.simplexalgorithm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;


public class MatrixInput extends ActionBarActivity {


    FloatingActionButton eqButton;
    Button solve;
    double[]   c;
    double[][] A;
    double[]   b;
    int numVar;
    int numCon;
    Simplex s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_input);

        initialize();

        final int numVar =  Integer.parseInt(getIntent().getExtras().get("numVar").toString());  // Get number of variables from input activity
        final int numCon =  Integer.parseInt(getIntent().getExtras().get("numCon").toString()); // Get number of constraints from input activity

	    EquationGenerator(numVar, numCon);

        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean VALID;

                VALID = validateInputs(numVar, numCon);

                if(VALID) {
                    solveProblem();
                    Solution.s = s;
                    Intent i = new Intent(getApplicationContext(),ResultsActivity.class);
                    i.putExtra("solveMode", getIntent().getStringExtra("solveMode"));
                    startActivity(i);
                }


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_matrix, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //ActionBar bar = getActionBar();
        //int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_bar:
                return true;
            case android.R.id.home:
                Log.e("Home Button", "Clicking Home");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initialize(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eqButton = (FloatingActionButton) findViewById(R.id.fab);
        solve = (Button) findViewById(R.id.solveButton);
          }


    public void EquationGenerator(int numcolumns, int numrow){

        TableRow.LayoutParams editMarginParams = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
        editMarginParams.setMargins(5, 5, 5, 5);
        if(android.os.Build.VERSION.SDK_INT >= 17){
            editMarginParams.setMarginStart(5);
            editMarginParams.setMarginEnd(5);
        }

        TableLayout table = (TableLayout) findViewById(R.id.functionLayout);
        for (int i = 0; i < numrow+1; i++) {
            TableRow row = new TableRow(this);


            //row.setPadding(5,0,5,0);
            for (int j = 0; j < numcolumns+1; j++) {
                MaterialEditText cell = new MaterialEditText(this);
                cell.setHint("x"+j);
                cell.setPaddings(10, 10, 10, 10);
                cell.setLayoutParams(editMarginParams);
                cell.setHelperText("x"+j);
                row.addView(cell);
            }

            table.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

        }

        TableRow last = (TableRow) table.getChildAt(0);
        EditText Lastet = (EditText) last.getChildAt(numcolumns);
        Lastet.setText("2");                                       // Setting random number value
        Lastet.setVisibility(View.INVISIBLE);                      // for error checking later

    }


    public boolean validateInputs(int numCol, int numRow){
        boolean validate = true;
        double[][] inputs = new double[numRow][numCol];

        TableLayout tblLayout = (TableLayout)findViewById(R.id.functionLayout);

        // Run through the matrix inputs
        for (int i = 0; i<= numRow;i++){
            TableRow inputRow = (TableRow) tblLayout.getChildAt(i);
            for (int j = 0; j <= numCol;j++ ) {
                EditText et = (EditText) inputRow.getChildAt(j);

                if (!et.getText().toString().matches("")) {      // Checking for non-input
                    try {
                         Double.parseDouble(et.getText().toString());
                    }catch(NumberFormatException e){            // Checking for non number input
                        Log.e("row" + i + "" + "col" + j + "not a number", et.getText().toString());
                        cheers("'" + et.getText().toString() + "'" + " is not a number.");
                        validate = false;
                        break;
                    }
            }
            else{
                Log.e("row" + i + "" + "col" + j +" null", et.getText().toString());
                validate = false;
                cheers("Missing an input.");
                break;
                }

            }
        }


        return validate;
    }


    private void solveProblem() {
        Log.i("SimplexMain", "onClick/solveButton");

        int numCol = numVar;
        int numRow = numCon;

        // Objective Function
        c = new double[numCol];

        // Constraint coefficients
        A = new double[numRow][numCol];

        //Constraint equals
        b = new double[numRow];



        TableLayout tblLayout = (TableLayout)findViewById(R.id.functionLayout);
        TableRow objRow = (TableRow) tblLayout.getChildAt(0); // Here get row id depending on number of row

        //Get Obj function
           for(int i =0; i<= numCol-1;i++) {
            EditText et = (EditText) objRow.getChildAt(i);
            Log.e("C/objfunct", et.getText().toString());
             c[i] = Double.parseDouble(et.getText().toString());
        }
            // Get A matrix
        for (int i = 1; i<= numRow;i++){
            TableRow consCol = (TableRow) tblLayout.getChildAt(i);
            for (int j = 0; j < numCol;j++ ){
                EditText et = (EditText) consCol.getChildAt(j);
                A[i-1][j] =  Double.parseDouble(et.getText().toString());
                Log.e("A/constraints", et.getText().toString());
            }
        }

                //Get B matrix
        for (int i = 1; i<= numRow; i++){
            TableRow consCol = (TableRow) tblLayout.getChildAt(i);
            EditText et = (EditText) consCol.getChildAt(numCol);
            Log.e("b/soltions", et.getText().toString());
            b[i-1] =  Double.parseDouble(et.getText().toString());

        }
                //Log.e("C",c.toString());

        s = new Simplex(A,b,c);

    }

    public void cheers(String message){
        Toast.makeText(getApplicationContext(),message,
                Toast.LENGTH_LONG).show();
    }


}
