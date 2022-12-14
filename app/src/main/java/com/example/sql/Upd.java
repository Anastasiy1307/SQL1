package com.example.sql;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;

public class Upd extends AppCompatActivity {

    ImageView imageView;
    EditText Name, Power;
    mask mask;
    Connection connection;
        View v;
    String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mask=getIntent().getParcelableExtra("Auto");

        imageView=findViewById(R.id.image_base);

        Name= findViewById(R.id.UpMark);
        Name.setText(mask.getName());

        Power=findViewById(R.id.UpPower);
        Power.setText(mask.getPower());

        imageView.setImageBitmap(getImgBitmap(mask.getImage()));
        v =findViewById(com.google.android.material.R.id.ghost_view);
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(Upd.this.getResources(),
                R.drawable.gluxo);
    }

    public void onClickChooseImage(View view)
    {
        mask.getImage();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                imageView.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            image=Base64.getEncoder().encodeToString(bytes);
            return image;
        }
        return "";
    }
    public void Perehod()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClickUpdate(View v){

        AlertDialog.Builder builder=new AlertDialog.Builder(Upd.this);
        builder.setTitle("??????????????????")
                .setMessage("???? ?????????????? ?????? ???????????? ???????????????? ????????????")
                .setCancelable(false)
                .setPositiveButton("????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (Name.getText().length()==0|| Power.getText().length()==0 )
                        {
                            Toast.makeText(Upd.this, "???????? ???? ???????????????????? ???????????????????????? ????????", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            String query="";
                            ConSQL connectionHelper = new ConSQL();
                            connection = connectionHelper.conclass();
                            if (connection != null) {
                                if(image=="")
                                {
                                    query = "UPDATE Auto Set Name = '" + Name.getText() + "', Power = '" + Power.getText() + "' WHERE ID= "+mask.getID()+"";

                                }
                                else
                                {
                                    query = "UPDATE Auto Set Name = '" + Name.getText() + "', Power = '" + Power.getText() + "', Img ='" + image + "' WHERE ID= "+mask.getID()+"";
                                }
                                Statement statement = connection.createStatement();
                                Toast.makeText(Upd.this, "???????????? ????????????????", Toast.LENGTH_SHORT).show();
                                statement.executeQuery(query);

                            }
                        }
                        catch (Exception ex)
                        {

                        }
                        Perehod();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }

    public void onClickDelete(View v){

        AlertDialog.Builder builder=new AlertDialog.Builder(Upd.this);
        builder.setTitle("??????????????")
                .setMessage("???????????????")
                .setCancelable(false)
                .setPositiveButton("????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            ConSQL connectionHelper = new ConSQL();
                            connection = connectionHelper.conclass();
                            if (connection != null) {
                                String query = "DELETE FROM  Auto  WHERE ID= "+mask.getID()+"";
                                Statement statement = connection.createStatement();
                                statement.executeQuery(query);
                            }
                        }

                        catch (Exception ex)
                        {

                        }
                        Perehod();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void onClickBack(View v) {
        switch (v.getId()) {
            case R.id.Back:
                Perehod();
                break;
        }
    }
}