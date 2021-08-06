package com.gokisoft.c1907l;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.gokisoft.c1907l.models.APIMessage;
import com.gokisoft.c1907l.models.Food;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIFoodActivity extends AppCompatActivity {
    ListView listView;
    List<Food> dataList = new ArrayList<>();
    FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        listView = findViewById(R.id.as_listview);

        adapter = new FoodAdapter(this, dataList);

        listView.setAdapter(adapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = dataList.get(position);

                Intent i = new Intent(APIFoodActivity.this, FoodDetailActivity.class);
                i.putExtra("thisFood", food);
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
                Food food = dataList.remove(position);

                //TODO: API - Xoa du lieu Food tren Server
                deleteFood(food);

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

                            //TODO: API Update
                            updateFood(dataList.get(position));
                        } else {
                            Food f = new Food(thumbnail, title, des);
                            dataList.add(f);

                            //TODO: API Insert
                            addFood(f);
                        }

                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    void addFood(final Food food) {
        OkHttpClient client = new OkHttpClient();

        String url = Config.API_FOOD;

        RequestBody body = new FormBody.Builder()
                .add("action", "add")
                .add("title", food.getTitle())
                .add("thumbnail", food.getThumbnail())
                .add("description", food.getDescription())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if(json != null && !json.isEmpty()) {
                    //Chuyen json string to List trong Java
                    Gson gson = new Gson();
                    Type listType = new TypeToken<APIMessage>() {}.getType();

                    final APIMessage msg = gson.fromJson(json, listType);

                    Log.d(APIFoodActivity.class.getName(), msg.toString());

                    food.set_id(msg.getId());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(APIFoodActivity.this, msg.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    void updateFood(Food food) {
        OkHttpClient client = new OkHttpClient();

        String url = Config.API_FOOD;

        RequestBody body = new FormBody.Builder()
                .add("action", "update")
                .add("id", ""+food.get_id())
                .add("title", food.getTitle())
                .add("thumbnail", food.getThumbnail())
                .add("description", food.getDescription())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                showApiMsg(json);
            }
        });
    }

    void deleteFood(Food food) {
        OkHttpClient client = new OkHttpClient();

        String url = Config.API_FOOD;

        RequestBody body = new FormBody.Builder()
                .add("action", "delete")
                .add("id", ""+food.get_id())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                showApiMsg(json);
            }
        });
    }

    void showApiMsg(String json) {
        if(json != null && !json.isEmpty()) {
            //Chuyen json string to List trong Java
            Gson gson = new Gson();
            Type listType = new TypeToken<APIMessage>() {}.getType();

            final APIMessage msg = gson.fromJson(json, listType);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(APIFoodActivity.this, msg.getMsg(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    void loadDataFromInternet() {
        //TODO: API Lay Danh Sach Foods.
        OkHttpClient client = new OkHttpClient();

        String url = Config.API_FOOD;

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
                Log.d(APIFoodActivity.class.getName(), json);
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
}
