package bgb.com.simplexalgorithm;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;


public class MatrixActivity extends ActionBarActivity {

    MaterialEditText numVar;
    MaterialEditText numConst;
    FloatingActionButton eqButton;
    Button solve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        initialize();

        eqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Buttonlistener", "HERE");
                int numrow = Integer.parseInt(numVar.getText().toString());
                int numcol = Integer.parseInt(numConst.getText().toString());
                objFunction(numrow,numcol);
                solve.setVisibility(View.VISIBLE);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initialize(){

        numConst = (MaterialEditText) findViewById(R.id.et_num_constraints);
        numVar = (MaterialEditText)  findViewById(R.id.et_num_variables);
        eqButton = (FloatingActionButton) findViewById(R.id.fab);
        solve = (Button) findViewById(R.id.solveButton);
          }


    public void objFunction(int numcolumns, int numrow){

        TableRow.LayoutParams editMarginParams = new TableRow.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
        editMarginParams.setMargins(5, 5, 5, 5);
        if(Integer.valueOf(android.os.Build.VERSION.SDK) >= 17){
            editMarginParams.setMarginStart(5);
            editMarginParams.setMarginEnd(5);
        }

        TableLayout table = (TableLayout) findViewById(R.id.functionLayout);
        for (int i = 0; i < numrow; i++) {
            TableRow row = new TableRow(this);


            //row.setPadding(5,0,5,0);
            for (int j = 0; j < numcolumns+1; j++) {
                MaterialEditText cell = new MaterialEditText(this);
                cell.setHint("x"+j);
                cell.setPaddings(10,10,10,10);
                cell.setLayoutParams(editMarginParams);
                cell.setHelperText("x"+j);
                row.addView(cell);
            }

            table.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

        }
    }




















        public void cheers(String message){
            Toast.makeText(getApplicationContext(),message,
                    Toast.LENGTH_LONG).show();
        }


}
