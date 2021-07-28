package com.gokisoft.c1907l;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gokisoft.c1907l.adapter.FoodAdapter;
import com.gokisoft.c1907l.models.Food;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FoodActivity extends AppCompatActivity {
    ListView listView;
    List<Food> dataList = new ArrayList<>();
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        requestPermission(this);

        listView = findViewById(R.id.as_listview);

//        readData();

        //Fake data
//        for(int i=0;i<20;i++) {
//            dataList.add(new Food("Thumbnail 1", "Title " + i, "Noi Dung " + i));
//        }
//        dataList.add(new Food("https://img.dominos.vn/Veggie-mania.jpg", "Title 1", "Noi Dung 1"));
//        dataList.add(new Food("https://www.delonghi.com/Global/recipes/multifry/pizza_fresca.jpg", "Title 2", "Noi Dung 1"));
//        dataList.add(new Food("https://www.crushpixel.com/big-static15/preview4/delicious-fresh-pizza-served-on-1979091.jpg", "Title 3", "Noi Dung 1"));
//        dataList.add(new Food("https://www.crushpixel.com/big-static15/preview4/delicious-fresh-pizza-served-on-1979091.jpg", "Title 4", "Noi Dung 1"));
//        dataList.add(new Food("https://donluciano.pizza/147-large_default/pizza-fresca.jpg", "Title 5", "Noi Dung 1"));
//        dataList.add(new Food("https://donluciano.pizza/147-large_default/pizza-fresca.jpg", "Title 6", "Noi Dung 1"));
//        dataList.add(new Food("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/delish-homemade-pizza-horizontal-1542312378.png", "Title 7", "Noi Dung 1"));
//        dataList.add(new Food("http://images6.fanpop.com/image/photos/42800000/Pizza-pizza-42846199-1280-720.jpg", "Title 8", "Noi Dung 1"));

        adapter = new FoodAdapter(this, dataList);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = dataList.get(position);

                Intent i = new Intent(FoodActivity.this, FoodDetailActivity.class);
                i.putExtra("thisFood", food);
//                i.putExtra("thumbnail", food.getThumbnail());
//                i.putExtra("title", food.getTitle());
//                i.putExtra("description", food.getDescription());
                startActivity(i);
            }
        });

        loadDataFromInternet();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.food_menu_context, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        int position = info.position;

        switch (item.getItemId()) {
            case R.id.menu_fmc_edit_food:
                showSubjectDialog(position);
                break;
            case R.id.menu_fmc_remove_food:
                dataList.remove(position);

                saveData();

                adapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_fm_add_food:
                showSubjectDialog(-1);
                break;
            case R.id.menu_fm_exit:
//                startActivity(new Intent(FoodActivity.this, FoodActivity2.class));
//                overridePendingTransition(R.anim.slide_out_bottom, R.anim.slide_in_bottom);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showSubjectDialog(final int position) {
        //B1: Convert layout xml -> view
        View v = getLayoutInflater().inflate(R.layout.dialog_food_editor, null);

        final EditText titleTxt = v.findViewById(R.id.dfe_title);
        final EditText desTxt = v.findViewById(R.id.dfe_description);
        final EditText thumbnailTxt = v.findViewById(R.id.dfe_thumbnail);
        if(position >= 0) {
            titleTxt.setText(dataList.get(position).getTitle());
            desTxt.setText(dataList.get(position).getDescription());
            thumbnailTxt.setText(dataList.get(position).getThumbnail());
        } else {
            //fake data
            thumbnailTxt.setText("https://produits.bienmanger.com/34491-0w600h600_Corona_Extra_Mexican_Blonde_Beer.jpg");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(v);
        builder.setTitle("Subject Editor")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = titleTxt.getText().toString();
                        String des = desTxt.getText().toString();
                        String thumbnail = thumbnailTxt.getText().toString();
                        if(position >= 0) {
                            dataList.get(position).setTitle(title);
                            dataList.get(position).setThumbnail(thumbnail);
                            dataList.get(position).setDescription(des);
                        } else {
                            Food f = new Food(thumbnail, title, des);

                            dataList.add(f);
                        }

                        saveData();

                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    void loadDataFromInternet() {
        OkHttpClient client = new OkHttpClient();

        String url = "https://raw.githubusercontent.com/tranvandiep/C1907L-AAHN-Android/master/data/food.txt";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.d(FoodActivity.class.getName(), json);
                if(json != null && !json.isEmpty()) {
                    //Chuyen json string to List trong Java
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Food>>() {}.getType();

                    dataList = gson.fromJson(json, listType);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.setDataList(dataList);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

    void readData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("foods", MODE_PRIVATE);
//
//        String json = sharedPreferences.getString("foodList", null);
        String json = "";

        FileInputStream fis = null;

        try {
            //C1. Doc du lieu tu Internal Storage
//            fis = openFileInput("food.txt");
            //C2. Doc du lieu tu External Storage
//            File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File folder = new File("/sdcard/" + Environment.DIRECTORY_DOWNLOADS);
            if(!folder.exists()) {
                folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            }

            File file = new File(folder, "datafoods.txt");
            fis = new FileInputStream(file);

            int code;

            while((code = fis.read()) != -1) {
                json += String.valueOf((char) code);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(FoodActivity.class.getName(), json);
        if(json != null && !json.isEmpty()) {
            //Chuyen json string to List trong Java
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Food>>() {}.getType();

            dataList = gson.fromJson(json, listType);
        }
    }

    void saveData() {
        //Chuyen List ve json string
        Gson gson = new Gson();
        String json = gson.toJson(dataList);

        Log.d(FoodActivity.class.getName(), json);

        FileOutputStream fos = null;
        try {
            //C1. Internal Storage
//            fos = openFileOutput("food.txt", MODE_PRIVATE);
            //C2. External Storage
//            File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File folder = new File("/sdcard/" + Environment.DIRECTORY_DOWNLOADS);
            if(!folder.exists()) {
                folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            }
//            if (android.os.Build.VERSION.SDK_INT >= 30) {
//                folder = Environment.getStorageDirectory();
//            } else {
//                folder = Environment.getRootDirectory();
//            }
            File file = new File(folder, "datafoods.txt");
            Log.d(FoodActivity.class.getName(), file.getAbsolutePath());

            fos = new FileOutputStream(file);

            byte[] data = json.getBytes("utf8");
            fos.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
        //Khoi tao SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("foods", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Luu du lieu
        editor.putString("foodList", json);

        //Dong ket noi toi SharedPreferences
        editor.commit();
         */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "The app was allowed to write to your storage!", Toast.LENGTH_LONG).show();
                    // Reload the activity with permission granted or use the features what required the permission
                } else {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public static final int REQUEST_WRITE_STORAGE = 112;

    private void requestPermission(Activity context) {
        boolean hasPermission = (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        } else {
            // You are allowed to write external storage:
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/new_folder";
//            File storageDir = new File(path);
//            if (!storageDir.exists() && !storageDir.mkdirs()) {
//                // This should never happen - log handled exception!
//            }
        }
    }
}
