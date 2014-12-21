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
    RadioGroup rg_inputMode;
    RadioGroup rg_minMax;

	final String TAG = "InputActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Initialize();

        eqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "OnClickListener/eqButton");

                if(numVar.getText().toString().matches("")) {
	                numVar.setError("Missing number of variables!");
	            } else if(numCon.getText().toString().matches("")) {
	                numCon.setError("Missing number of constraints!");
                } else {
	                radioButton.setVisibility(View.VISIBLE);
                }
            }
        });

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

	            Intent i = new Intent();

	            // Get input mode radio selection
	            int inputMode = rg_inputMode.getCheckedRadioButtonId();

	            if(inputMode == -1) {
	                Log.e(TAG,"inputMode/No selection");
		            Cheers("Please choose an input mode.");
	            } else if (inputMode == R.id.rb_tableauMatrix) {
		            Log.i(TAG,"inputMode/Maximum");
		            i = new Intent(getApplicationContext(), MatrixInput.class);
	            } else if (inputMode == R.id.rb_equationInput) {
		            Log.i(TAG,"inputMode/Minimum");
		            i = new Intent(getApplicationContext(), MatrixInput.class);
	            } else {
					Cheers("An unknown error has occurred");
	            }

	            // Get solve mode radio selection
				int solveMode = rg_minMax.getCheckedRadioButtonId();

	            if(solveMode == -1)  {
		            Log.e(TAG,"solveMode/No selection");
		            Cheers("Please choose a solve mode.");
	            } else if (solveMode == R.id.rb_max) {
		            Log.i(TAG,"inputMode/Maximum");
		            i.putExtra("solveMode","max");
	            } else if (solveMode == R.id.rb_min) {
		            Log.i(TAG,"inputMode/Minimum");
		            i.putExtra("solveMode","min");
	            } else {
		            Cheers("An unknown error has occurred");
	            }

	            startActivity(i);
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
        /*int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void Initialize(){
        eqButton = (FloatingActionButton) findViewById(R.id.fab);
        numVar = (MaterialEditText) findViewById(R.id.et_num_variables);
        numCon = (MaterialEditText) findViewById(R.id.et_num_constraints);
        radioButton = (LinearLayout) findViewById(R.id.modeSelection);
        inputButton = (Button) findViewById(R.id.goToInputsButton);
        rg_inputMode = (RadioGroup) findViewById(R.id.matoreqGroup);
        rg_minMax = (RadioGroup) findViewById(R.id.minmaxGroup);
    }

    public void Cheers(String message){
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }
}
