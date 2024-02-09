package com.rider.troopadelivery.troopa;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.rider.troopadelivery.troopa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import yuku.ambilwarna.AmbilWarnaDialog;


public class contact_activity<PERMISSION_REQUEST_READ_FOLDERS, PERMISSIONS> extends AppCompatActivity {
   // private EditText select_date;

    RequestQueue queue;
    EditText user_location;
    EditText user_email;
    EditText user_phone;
    EditText userpassword;
    EditText usercpassword;

    EditText license_plate, driver_license,expirydate;



    EditText company;
    EditText code;

    EditText machine_manufacturer;
    EditText engine_size;
    EditText name;
    EditText licenseplate;

    EditText bike_color;
    EditText bank_name;
    EditText account_name;
    EditText account_number;


    LinearLayout first_list;
    LinearLayout first_list_option;
    LinearLayout second_list;
    LinearLayout third_list;
    LinearLayout forth_list;
    LinearLayout fift_list;

    LinearLayout tabbtn1;
    LinearLayout tabbtn2;
    LinearLayout tabbtn3;
    LinearLayout tabbtn4;
    LinearLayout tabbtn5;

    LinearLayout profile_photp_tap,driver_license_tap,permit_tap,permitline;


    ImageView tab1_btn_img;
    ImageView tab2_btn_img;
    ImageView tab3_btn_img;
    ImageView tab4_btn_img;
    ImageView tab5_btn_img;

    TextView profiletext,driverLicensetext,permittext,uploadmsg,color_sms;


    String selectedcompanyoption;
    String profilePhotopath;
    String driverlicense;
    String permit;
    String thirdDelivery;
    String user_phone_str;
    String user_email_str;
    String user_location_str;
    String name_str;
    String password_str;
    String machine_manufacturer_str;
    String engine_size_str;
    String license_plate_str;
    String  driver_license_str;
    String  expiry_str;
    String company_str;
    String code_str;
    String bank_name_str;
    String account_name_str;
    String account_number_str;
    String phone_for_firebase;

    Button submit_second_list;
    Button  submitfirstlistoption;
    Button submit_first_list;
    Button submitthirdlist;
    Button submitforthlist;
    Button submitfiftlist;

    Button default_color;
    Button change_color;

    VolleyMultipartRequest.DataPart permitimg;

    private View mColorPreview;
    private int mDefaultColor;

    private String validPermit;


    DatePickerDialog datePickerDialog;

    private RadioGroup radioGroup;
    RadioButton yes_button, no_button,no_valid_permit,yes_valid_permit;

    boolean isAllFieldsChecked = false;
    boolean isAllFieldsCheckedOptions = false;
    boolean isAllFieldsCheckedSecond = false;
    boolean isAllFieldsCheckedThird = false;
    boolean isAllFieldsCheckedForth = false;
    boolean isAllFieldsCheckedFifth = false;
    private ProgressBar loadingPB;
    private ProgressBar loadingPB2;

    Bitmap   permittextPhotoBitmap;
    Bitmap   driverlicensePhotoBitmap;
    Bitmap  profilePhotoBitmap;

    long  permittextimagename;
    long  driverlicenseimagename;
    long  profilePhotoimagename;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    private DatabaseReference databaseReference;

    String output="";






    ActivityResultLauncher<String> profilePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri



                    if(uri !=null) {
                        try {
                            Context context= contact_activity.this;
                            String path =RealPathUtil.getRealPath(context,uri);
                            Random rand = new Random();

                            profilePhotoBitmap=pathToBitmap(path,getFileName(path));
                            profilePhotoimagename = rand.nextInt(20);
                            profilePhotopath = path;
                            profiletext.setText(getFileName(path));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });


    ActivityResultLauncher<String> Driverslicense = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri

                    if(uri !=null) {
                        try {
                            Context context= contact_activity.this;
                            String path =RealPathUtil.getRealPath(context,uri);
                            Random rand = new Random();

                            driverlicensePhotoBitmap=pathToBitmap(path,getFileName(path));

                            driverlicenseimagename =rand.nextInt(20);

                            driverlicense = path;


                            driverLicensetext.setText(getFileName(path));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

    ActivityResultLauncher<String> Permits = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri

                    if(uri !=null) {
                        try {
                            Context context= contact_activity.this;
                            String path =RealPathUtil.getRealPath(context,uri);
                            Random rand = new Random();

                             permit=path;
                             permittextimagename = rand.nextInt(20);
                            permittextPhotoBitmap=pathToBitmap(path,getFileName(path));
                            permittext.setText(getFileName(path));




                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                }
            });



    private Bitmap pathToBitmap(String path,String imageName) {
        File sd = Environment.getExternalStorageDirectory();
        File image = new File(path);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);


        return bitmap;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.contact_activity);







        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        loadingPB = findViewById(R.id.idLoadingPB);
        loadingPB2 = findViewById(R.id.idLoadingPB2);



        default_color= findViewById(R.id.default_color);
        change_color = findViewById(R.id.change_color);
        mColorPreview = findViewById(R.id.preview_selected_color);

        color_sms = findViewById(R.id.colorsms);

        yes_button = (RadioButton) findViewById(R.id.yescheckbox);
        no_button = (RadioButton) findViewById(R.id.nocheckbox);

        yes_valid_permit = (RadioButton) findViewById(R.id.yesvalidpermit);
        no_valid_permit = (RadioButton) findViewById(R.id.novalidpermit);


        profiletext=findViewById(R.id.profiletext);
        driverLicensetext=findViewById(R.id.driverLicensetext);
        permittext=findViewById(R.id.permittext);
        uploadmsg=findViewById(R.id.uploadmsg);

        first_list=findViewById(R.id.firstList);
        first_list_option=findViewById(R.id.firstList_option);
        second_list=findViewById(R.id.secondList);
        third_list=findViewById(R.id.ThirdList);
        forth_list=findViewById(R.id.ForthList);
        fift_list=findViewById(R.id.fiftlist);


        driver_license_tap=findViewById(R.id.driver_license_tap);
        permit_tap=findViewById(R.id.permit_tap);
        profile_photp_tap=findViewById(R.id.profile_photo_tap);

        permitline=findViewById(R.id.permitline);

        mDefaultColor = 0;
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


        profile_photp_tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    } else {
                        imageChooserProfile();
                    }
                }

            }
        });

        driver_license_tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    } else {
                        imageChooserdriverlicense();
                    }
                }



            }
        });

        permit_tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    } else {
                        imageChooserPermit();
                    }
                }


            }
        });
        tabbtn1=findViewById(R.id.tab1);
        tabbtn2=findViewById(R.id.tab2);
        tabbtn3=findViewById(R.id.tab3);
        tabbtn4=findViewById(R.id.tab4);
        tabbtn5=findViewById(R.id.tab5);

        tab1_btn_img=findViewById(R.id.tab1img);
        tab2_btn_img=findViewById(R.id.tab2img);
        tab3_btn_img=findViewById(R.id.tab3img);
        tab4_btn_img=findViewById(R.id.tab4img);
        tab5_btn_img=findViewById(R.id.tab5img);


        first_list_option.setVisibility(View.GONE);
        second_list.setVisibility(View.GONE);
        third_list.setVisibility(View.GONE);
        forth_list.setVisibility(View.GONE);
        fift_list.setVisibility(View.GONE);

        submit_first_list=findViewById(R.id.submitfirstlist);
        submit_second_list=findViewById(R.id.submitsecondlist);
        submitfirstlistoption=findViewById(R.id.submitfirstlistoption);
        submitthirdlist=findViewById(R.id.submitthirdlist);
        submitforthlist=findViewById(R.id.submitforthlist);
        submitfiftlist=findViewById(R.id.submitfiftlist);



        expirydate=(EditText) findViewById(R.id.date);

        expirydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(contact_activity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                               expirydate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
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




        user_phone=findViewById(R.id.userphone);
        user_phone_str=user_phone.getText().toString();

        phone_for_firebase=user_phone.getText().toString();

        user_email=findViewById(R.id.useremail);
        user_email_str=user_email.getText().toString();

        user_location=findViewById(R.id.location);
        user_location_str=user_location.getText().toString();

        userpassword=findViewById(R.id.userpassword);
        usercpassword=findViewById(R.id.usercpassword);



        name=findViewById(R.id.name);
        name_str=name.getText().toString();

        machine_manufacturer=findViewById(R.id.machine_manufacturer);
        machine_manufacturer_str=machine_manufacturer.getText().toString();

        engine_size=findViewById(R.id.engine_size);
        engine_size_str=engine_size.getText().toString();

        license_plate=findViewById(R.id.license_plate);
        license_plate_str=license_plate.getText().toString();

        driver_license=findViewById(R.id.driver_license);
         driver_license_str= driver_license.getText().toString();

        expiry_str= expirydate.getText().toString();



        company=findViewById(R.id.company);
        company_str=company.getText().toString();

        code=findViewById(R.id.code);
        code_str=code.getText().toString();





        //bike_color=findViewById(R.id.bike_color);
        //String bike_color_str=bike_color.getText().toString();

        bank_name=findViewById(R.id.bank_name);
        bank_name_str= bank_name.getText().toString();

        account_name=findViewById(R.id.account_name);
        account_name_str= account_name.getText().toString();

        account_number=findViewById(R.id.account_number);
        account_number_str= account_number.getText().toString();




        submitfirstlistoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllFieldsOptions();

                if(isAllFieldsChecked) {

                    second_list.setVisibility(View.VISIBLE);
                    first_list.setVisibility(View.INVISIBLE);
                    first_list_option.setVisibility(View.INVISIBLE);
                    tabbtn1.setBackgroundResource(R.drawable.small_btn);
                    tabbtn2.setBackgroundResource(R.drawable.round_button);
                    tab1_btn_img.setImageResource(R.drawable.ic_baseline_alternate_email_24_black);
                    tab2_btn_img.setImageResource(R.drawable.ic_baseline_contact_mail_24_white);
                }
            }
        });

        submitforthlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldsChecked = CheckAllFieldsForth();

                if(isAllFieldsChecked) {

                    second_list.setVisibility(View.INVISIBLE);
                    first_list.setVisibility(View.INVISIBLE);
                    third_list.setVisibility(View.INVISIBLE);
                    forth_list.setVisibility(View.INVISIBLE);
                    fift_list.setVisibility(View.VISIBLE);

                    tabbtn1.setBackgroundResource(R.drawable.small_btn);
                    tabbtn2.setBackgroundResource(R.drawable.small_btn);
                    tabbtn3.setBackgroundResource(R.drawable.small_btn);
                    tabbtn4.setBackgroundResource(R.drawable.small_btn);
                    tabbtn5.setBackgroundResource(R.drawable.round_button);

                    tab1_btn_img.setImageResource(R.drawable.ic_baseline_alternate_email_24_black);
                    tab2_btn_img.setImageResource(R.drawable.ic_baseline_contact_mail_24);
                    tab3_btn_img.setImageResource(R.drawable.ic_baseline_content_paste_24);
                    tab4_btn_img.setImageResource(R.drawable.ic_baseline_file_upload_24_black);
                    tab5_btn_img.setImageResource(R.drawable.ic_baseline_payment_24_white);



                }
            }
        });

        submit_first_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                isAllFieldsChecked = CheckAllFields();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2

                if (isAllFieldsChecked) {
                   // Toast.makeText(contact_activity.this, "phone "+user_phone.getText().toString(), Toast.LENGTH_LONG).show();

                    loadingPB2.setVisibility(View.VISIBLE);

                    String URL = "https://www.troopa.org/api/ifemailexist/+234"+user_phone.getText().toString();


                    queue = Volley.newRequestQueue(contact_activity.this);
                    StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject obj = null;
                            try {
                                obj = new JSONObject(response);
                                if (obj.getString("error").equals("exist")) {
                                    loadingPB2.setVisibility(View.GONE);
                                    user_phone.setError("This phone already exist");


                                }else {

                                    loadingPB2.setVisibility(View.GONE);
                                    if (yes_button.isChecked()) {
                                        thirdDelivery="yes";
                                        selectedcompanyoption = yes_button.getText().toString();
                                        first_list_option.setVisibility(View.VISIBLE);
                                        first_list.setVisibility(View.INVISIBLE);
                                    }else {


                                        thirdDelivery="no";



                                        //Toast.makeText(contact_activity.this, "All input "+thirdDelivery, Toast.LENGTH_LONG).show();

                                        second_list.setVisibility(View.VISIBLE);
                                        first_list.setVisibility(View.INVISIBLE);
                                        tabbtn1.setBackgroundResource(R.drawable.small_btn);
                                        tabbtn2.setBackgroundResource(R.drawable.round_button);
                                        tab1_btn_img.setImageResource(R.drawable.ic_baseline_alternate_email_24_black);
                                        tab2_btn_img.setImageResource(R.drawable.ic_baseline_contact_mail_24_white);

                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error",error.toString());
                        }
                    });
                    queue.add(request);





                }




            }
        });


        submit_second_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                isAllFieldsChecked= CheckAllFieldsSecond();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {

                    second_list.setVisibility(View.INVISIBLE);
                    first_list.setVisibility(View.INVISIBLE);
                    third_list.setVisibility(View.VISIBLE);

                    tabbtn1.setBackgroundResource(R.drawable.small_btn);
                    tabbtn2.setBackgroundResource(R.drawable.small_btn);
                    tabbtn3.setBackgroundResource(R.drawable.round_button);


                    tab1_btn_img.setImageResource(R.drawable.ic_baseline_alternate_email_24_black);
                    tab2_btn_img.setImageResource(R.drawable.ic_baseline_contact_mail_24);
                    tab3_btn_img.setImageResource(R.drawable.ic_baseline_content_paste_24_white);


                }




            }
        });


        submitthirdlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                isAllFieldsChecked = CheckAllFieldsThird();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {

                   if(yes_valid_permit.isChecked() && !no_valid_permit.isChecked()){

                       validPermit="yes";
                   }
                    if(!yes_valid_permit.isChecked() && no_valid_permit.isChecked()){

                        validPermit="no";
                        permitline.setVisibility(View.INVISIBLE);
                   }



                    second_list.setVisibility(View.INVISIBLE);
                    first_list.setVisibility(View.INVISIBLE);
                    third_list.setVisibility(View.INVISIBLE);
                    forth_list.setVisibility(View.VISIBLE);

                    tabbtn1.setBackgroundResource(R.drawable.small_btn);
                    tabbtn2.setBackgroundResource(R.drawable.small_btn);
                    tabbtn3.setBackgroundResource(R.drawable.small_btn);
                    tabbtn4.setBackgroundResource(R.drawable.round_button);


                    tab1_btn_img.setImageResource(R.drawable.ic_baseline_alternate_email_24_black);
                    tab2_btn_img.setImageResource(R.drawable.ic_baseline_contact_mail_24);
                    tab3_btn_img.setImageResource(R.drawable.ic_baseline_content_paste_24);
                    tab4_btn_img.setImageResource(R.drawable.ic_baseline_file_upload_24_white);


                }




            }
        });


        submitfiftlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                isAllFieldsChecked= CheckAllFieldsFifth();

                // the boolean variable turns to be true then
                // only the user must be proceed to the activity2
                if (isAllFieldsChecked) {

                    user_phone_str="+234"+user_phone.getText().toString();
                    user_email_str=user_email.getText().toString();
                    user_location_str=user_location.getText().toString();
                    name_str=name.getText().toString();
                    password_str=userpassword.getText().toString();
                    machine_manufacturer_str=machine_manufacturer.getText().toString();
                    engine_size_str=engine_size.getText().toString();
                    license_plate_str=license_plate.getText().toString();
                    driver_license_str= driver_license.getText().toString();
                    expiry_str= expirydate.getText().toString();

                    if(thirdDelivery.equals("yes")) {
                        company_str = company.getText().toString();
                        code_str = code.getText().toString();
                    }
                    if(thirdDelivery.equals("no")) {
                        company_str = "nil";
                        code_str = "nil";
                    }



                    bank_name_str= bank_name.getText().toString();
                    account_name_str= account_name.getText().toString();
                    account_number_str= account_number.getText().toString();


  //Toast.makeText(contact_activity.this, "All input "+mDefaultColor, Toast.LENGTH_LONG).show();

                       submit_registration(user_phone_str,user_email_str,user_location_str,thirdDelivery,password_str,name_str,machine_manufacturer_str,engine_size_str,license_plate_str,mDefaultColor,driver_license_str,expiry_str,validPermit, company_str, code_str,bank_name_str,account_name_str,account_number_str,profilePhotoBitmap,driverlicensePhotoBitmap,permittextPhotoBitmap);

                   // Intent i = new Intent(contact_activity.this,success_registration.class);
                   // startActivity(i);


                }




            }
        });

       // select_date = findViewById(R.id.selectdate);

       // final Calendar calendar = Calendar.getInstance();
       // final int year = calendar.get(Calendar.YEAR);
       // final int month = calendar.get(Calendar.MONTH);
       // final int day = calendar.get(Calendar.DAY_OF_MONTH);

      //  select_date.setOnClickListener(new View.OnClickListener() {
       //     @Override
         //   public void onClick(View v) {

          //      DatePickerDialog dialog = new DatePickerDialog(contact_activity.this, new DatePickerDialog.OnDateSetListener() {
          //          @Override
          //          public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

           //             month = month+1;
              //          String date = dayOfMonth+"/"+month+"/"+year;
              //          select_date.setText(date);

           //         }
            //    },year, month,day);
            //    dialog.show();

         //   }
       // });




    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private boolean CheckAllFieldsOptions() {


        if ( company.length() == 0) {
            company.setError("Enter 3DL Company name");
            return false;
        }

        if (code.length() == 0) {
            code.setError("Enter company code");
            return false;
        }

        // after all validation return true.
        return true;
    }

    private boolean CheckAllFields() {



        if ( user_email.length() == 0) {
            user_email.setError("email is required");
            return false;
        }

        if (user_phone.length() == 0) {
            user_phone.setError("Phone is required");
            return false;
        }
        if ( user_location.length() == 0) {
            user_location.setError("Enter your location");
            return false;
        }
        if ( !yes_button.isChecked() && !no_button.isChecked()) {

            yes_button.setError("Are you from a 3DL?");
            return false;
        }
        if(user_phone.getText().charAt(0)=='0'){

            user_phone.setError("Please enter phone number in +2348012345 format");
            return false;

        }
        if (userpassword.length() == 0) {
            userpassword.setError("Enter a password you can remember");
            return false;
        }

        if (usercpassword.length() == 0) {
            usercpassword.setError("Please confirm your password");
            return false;
        }
        if(!userpassword.getText().toString().equals(usercpassword.getText().toString())) {

            usercpassword.setError("Both passwords does not match");
            return false;
        }



        // after all validation return true.
        return true;
    }

    private boolean CheckAllFieldsSecond() {
        if ( name.length() == 0) {
            name.setError("Please enter your name");
            return false;
        }
        if ( machine_manufacturer.length() == 0) {
            machine_manufacturer.setError("Enter name of machine manufacturer");
            return false;
        }

        if ( engine_size.length() == 0) {
            engine_size.setError("Enter your engine size");
            return false;
        }
        if ( license_plate.length() == 0) {
            license_plate.setError("your license plate number is required");
            return false;
        }

        if (mDefaultColor == 0) {
            color_sms.setError("Choose bike color");
            return false;
        }


        // after all validation return true.
        return true;
    }

    private boolean CheckAllFieldsThird() {


        if (driver_license.length() == 0) {
            driver_license.setError("Enter drivers license");
            return false;
        }
        if ( expirydate.length() == 0) {
            expirydate.setError("Enter your license expiry date");
            return false;
        }
        if ( !yes_valid_permit.isChecked() && !no_valid_permit.isChecked()) {

            yes_valid_permit.setError("Please identify if you have a valid permit");
            return false;
        }


        // after all validation return true.
        return true;
    }


    private boolean CheckAllFieldsFifth() {


        if ( bank_name.length() == 0) {
            bank_name.setError("Type in your bank name");
            return false;
        }

        if (account_name.length() == 0) {
            account_name.setError("Type in your account name");
            return false;
        }
        if ( account_number.length() == 0) {
            account_number.setError("Type in your account number");
            return false;
        }


        // after all validation return true.
        return true;
    }


    private boolean CheckAllFieldsForth() {


        if (profilePhotopath==null || driverlicense==null && validPermit.equals("yes")) {
            uploadmsg.setError("Please upload all required files");
            return false;
        }
        if (profilePhotopath==null || driverlicense==null && validPermit.equals("no")) {
            uploadmsg.setError("Please upload all required files");
            return false;
        }
        // after all validation return true.
        return true;
    }

    public void imageChooserProfile() {

        profilePhoto.launch("image/*");
    }

    public void imageChooserdriverlicense(){

        Driverslicense.launch("image/*");
    }

    public void imageChooserPermit(){

        Permits.launch("image/*");
    }
    // this function is triggered when user
    // selects the image from the imageChooser

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

    public static String getFileName(String path) {
        try {

            return path != null ? path.substring(path.lastIndexOf("/") + 1) : "unknown";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "unknown";
    }




    private void submit_registration(String user_phone_str,String user_email_str,String user_location_str,String thirdDelivery,String password_str,String name_str,String machine_manufacturer_str,String engine_size_str,String license_plate_str,int mDefaultColor,String  driver_license_str,String  expiry_str,String  validPermit, String company_str, String code_str,String bank_name_str,String account_name_str,String account_number_str,Bitmap profilePhotoBitmap,Bitmap driverlicensePhotoBitmap, Bitmap permittextPhotoBitmap) {
        // url to post our data
        String url = "https://www.troopa.org/api/registration";

        loadingPB.setVisibility(View.VISIBLE);
// Creating string request with post method.
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        // Hiding the progress dialog after all task complete.
                        loadingPB.setVisibility(View.GONE);
                        String resultResponse = new String(response.data);

                        //Toast.makeText(contact_activity.this,resultResponse, Toast.LENGTH_LONG).show();
                        // Showing response message coming from server.
                      if(resultResponse.equals("ok")) {

                         // Toast.makeText(contact_activity.this,user_phone_str, Toast.LENGTH_LONG).show();

                         // writeNewRider(user_phone_str,0.0,0.0);

                           Intent i = new Intent(contact_activity.this,success_registration.class);
                         startActivity(i);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        loadingPB.setVisibility(View.GONE);
                        // Showing error message if something goes wrong.
                        Toast.makeText(contact_activity.this,error.getMessage(), Toast.LENGTH_LONG).show();




                    }
                }){

            @RequiresApi(api = Build.VERSION_CODES.O)
            protected Map<String, String> getParams() throws AuthFailureError {



                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params
                params.put("email", user_email_str);
                params.put("phone", user_phone_str);
                params.put("address", user_location_str);
                params.put("3delivery", thirdDelivery);
                params.put("password",password_str);
                params.put("name", name_str);
                params.put("machine_manufacture", machine_manufacturer_str);
                params.put("engine_size",engine_size_str);
                params.put("license_plate",license_plate_str);
                params.put("bike_color", String.valueOf(mDefaultColor));
                params.put("driver_license", driver_license_str);
                params.put("expiry_date", expiry_str);
                params.put("valid_permit", validPermit);
                params.put("bankName", bank_name_str);
                params.put("accountName", account_name_str);
                params.put("accountNumber", account_number_str);
                params.put("company", company_str);
                params.put("companyCode", code_str);


                return params;
            }

            /*
             *pass files using below method
             * */
            @Override

            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                if(validPermit.equals("no")) {
                    params.put("picture", new DataPart(profilePhotoimagename + ".png", getFileDataFromDrawable(profilePhotoBitmap)));
                    params.put("driver_license_pic", new DataPart(driverlicenseimagename + ".png", getFileDataFromDrawable(driverlicensePhotoBitmap)));
                  }else{
                    params.put("picture", new DataPart(profilePhotoimagename + ".png", getFileDataFromDrawable(profilePhotoBitmap)));
                    params.put("driver_license_pic", new DataPart(driverlicenseimagename + ".png", getFileDataFromDrawable(driverlicensePhotoBitmap)));
                    params.put("permit_pic", new DataPart(permittextimagename + ".png", getFileDataFromDrawable(permittextPhotoBitmap)));

                }


                return params;
            }

        };



        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rQueue = Volley.newRequestQueue(contact_activity.this);
        rQueue.add(volleyMultipartRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getStringImage(Bitmap bm){



        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.getEncoder().encodeToString(byteFormat);

        return imgString;


    }





    }



