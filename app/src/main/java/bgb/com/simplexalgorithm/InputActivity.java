package bgb.com.simplexalgorithm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;


public class InputActivity extends ActionBarActivity {

    FloatingActionButton eqButton;
    MaterialEditText numVar;
    MaterialEditText numCon;
    LinearLayout radioButton;
    Button inputButton;
    RadioGroup matoreq;
    RadioGroup minormaxG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        initialize();

        eqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("InputButtonlistener", "HERE");

                if(numVar.getText().toString().matches("")) {
	                numVar.setError("Missing number of variables!");
	            } else if(numCon.getText().toString().matches("")) {
	                numCon.setError("Missing number of constraints!");
                } else {
	                int numCol = Integer.parseInt(numVar.getText().toString());
	                int numRow = Integer.parseInt(numCon.getText().toString());
	                radioButton.setVisibility(View.VISIBLE);
                }
            }
        });

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean matoreqq = MATorEQ();
                boolean minormax = minORmax();

//                if(matoreqq)
//                Log.e("InputActivityPlusButton", "True");
//                else
//                    Log.e("InputActivityPlusButton", "False");
//
//                if(minormax)
//                    Log.e("InputActivityPlusButton", "min");
//                else
//                    Log.e("InputActivityPlusButton", "max");


                    // Matrix Input and Min
                if(matoreqq == true && minormax == true){

                    Intent myIntent = new Intent(getApplicationContext(), MatrixInput.class);
                    myIntent.putExtra("numVar", numVar.getText().toString());
                    myIntent.putExtra("numCon", numCon.getText().toString());
                    myIntent.putExtra("minOrmax", minormax);


                    startActivity(myIntent);
                    Log.e("InputActivityPlusButton", "Started New Activity?");
                }
                // Matrix Input and Max
                else if(matoreqq == true && minormax == false){

                    Intent myIntent = new Intent(getApplicationContext(), MatrixInput.class);
                    myIntent.putExtra("numVar", numVar.getText().toString());
                    myIntent.putExtra("numCon", numCon.getText().toString());
                    myIntent.putExtra("minOrmax", minormax);
                    startActivity(myIntent);
                }

                // Equation Input and Min
                else if(matoreqq == false && minormax == true){

                    Intent myIntent = new Intent(getApplicationContext(), EquationInput.class);
                    myIntent.putExtra("numVar", numVar.getText().toString());
                    myIntent.putExtra("numCon", numCon.getText().toString());
                    myIntent.putExtra("minOrmax", minormax);
                    startActivity(myIntent);

                }
                // Equation Input and Max
                else{
                    Intent myIntent = new Intent(getApplicationContext(), EquationInput.class);
                    myIntent.putExtra("numVar", numVar.getText().toString());
                    myIntent.putExtra("numCon", numCon.getText().toString());
                    myIntent.putExtra("minOrmax", minormax);
                    startActivity(myIntent);

                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
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

    public void initialize(){
        eqButton = (FloatingActionButton) findViewById(R.id.fab);
        numVar = (MaterialEditText) findViewById(R.id.et_num_variables);
        numCon = (MaterialEditText) findViewById(R.id.et_num_constraints);
        radioButton = (LinearLayout) findViewById(R.id.modeSelection);
        inputButton = (Button) findViewById(R.id.goToInputsButton);
        matoreq = (RadioGroup) findViewById(R.id.matoreqGroup);
        minormaxG = (RadioGroup) findViewById(R.id.minmaxGroup);

    }

    public boolean MATorEQ(){
        boolean tf = false;
        int id = matoreq.getCheckedRadioButtonId();
        RadioButton RB = (RadioButton)findViewById(id);

            Log.e("MatEqRG", RB.getText().toString());


        if(RB.getText().toString().matches("Matrix Input"))
            tf = true;
        else
        tf = false;

    return tf;
    }


    public boolean minORmax() {
        boolean tf = false;
        int id = minormaxG.getCheckedRadioButtonId();
        RadioButton RB = (RadioButton)findViewById(id);

        Log.e("minORmax", RB.getText().toString());


        if(RB.getText().toString().matches("Minimum"))
            tf = true;
        else
            tf = false;

        return tf;

    }

    public void cheers(String message){

        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();

    }












}
