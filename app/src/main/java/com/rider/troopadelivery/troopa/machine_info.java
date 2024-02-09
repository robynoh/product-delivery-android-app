package com.rider.troopadelivery.troopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rider.troopadelivery.troopa.R;

import yuku.ambilwarna.AmbilWarnaDialog;

public class machine_info extends AppCompatActivity {
    private View mColorPreview;
    private int mDefaultColor;

    Button default_color;
    Button change_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_info);

        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Motor Bike");


        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);

        String machine_manufacture = sharedPreferences.getString("machine_manufacture", "");
        String engine_size= sharedPreferences.getString("engine_size", "");
        String bike_color = sharedPreferences.getString("bike_color", "");
        String license = sharedPreferences.getString("license", "");

        change_color = findViewById(R.id.change_color);
        default_color= findViewById(R.id.default_color);
        TextView machine_name= findViewById(R.id.machine_name);

        EditText machineManufacture= findViewById(R.id.machineManufacturer);
        EditText engineSize= findViewById(R.id.engineSize);
        EditText licensePlate= findViewById(R.id.licensePlate);
        mColorPreview = findViewById(R.id.preview_selected_color);

        machineManufacture.setText(machine_manufacture);
        engineSize.setText(engine_size);
        licensePlate.setText(license);

        mDefaultColor=Integer.parseInt(bike_color);
        mColorPreview.setBackgroundColor(mDefaultColor);

        //Toast.makeText(machine_info.this,bike_color, Toast.LENGTH_LONG).show();
        machine_name.setText(machine_manufacture);


       // mDefaultColor = 0;
        change_color.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // to make code look cleaner the color
                        // picker dialog functionality are
                        // handled in openColorPickerDialogue()
                        // function
                        openColorPickerDialogue();
                    }
                });

        default_color.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // as the mDefaultColor is the global
                        // variable its value will be changed as
                        // soon as ok button is clicked from the
                        // color picker dialog.
                        mColorPreview.setBackgroundColor(0);
                    }
                });
    }




    public void openColorPickerDialogue() {

        // the AmbilWarnaDialog callback needs 3 parameters
        // one is the context, second is default color,
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, mDefaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // leave this function body as
                        // blank, as the dialog
                        // automatically closes when
                        // clicked on cancel button
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        mDefaultColor = color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        mColorPreview.setBackgroundColor(mDefaultColor);
                    }
                });
        colorPickerDialogue.show();
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}