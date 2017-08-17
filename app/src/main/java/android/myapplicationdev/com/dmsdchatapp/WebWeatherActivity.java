package android.myapplicationdev.com.dmsdchatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class WebWeatherActivity extends AppCompatActivity {

   private TextView tvWeather;
    private EditText etText;
    private Button btnAdd;
    private ListView lv;
    private String name = "";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference messagePOJOReference, userRef;
    private ArrayList<Message> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_weather);
        tvWeather = (TextView)findViewById(R.id.tvWeather);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String url = "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast";
        HttpRequest request = new HttpRequest(url);
        request.setMethod("GET");
        request.setAPIKey("api-key", "qxGiGvBoYdBLFZn3k7OaU2fei42sFQPk");
        request.execute();
        try {
            String jsonString = request.getResponse();
            System.out.println(">>" + jsonString);
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArr = jsonObject.getJSONArray("items");
            JSONArray jsonWeather = jsonArr.getJSONObject(0).getJSONArray("forecasts");

            for (int i = 0; i < jsonWeather.length(); i++) {
                JSONObject jsonObj = jsonWeather.getJSONObject(i);
                String area = jsonObj.getString("area");
                if(area.equalsIgnoreCase("woodlands")) {
                    String forecast = jsonObj.getString("forecast");
                    Log.i("forecast", forecast);
                    tvWeather.setText("Weather Forecast @ " + area + ": " + forecast);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        btnAdd = (Button)findViewById(R.id.btnAdd);
        etText = (EditText)findViewById(R.id.etText);
        messagePOJOReference = firebaseDatabase.getReference("messages");
        userRef = firebaseDatabase.getReference("profiles/" + firebaseUser.getUid());
        lv = (ListView)findViewById(R.id.lv);
        al = new ArrayList<>();

        messagePOJOReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                al.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Message task = postSnapshot.getValue(Message.class);
                    al.add(new Message(task.getMessageText(), task.getMessageTime(), task.getMessageUser()));
                }
                MessageAdapter aa = new MessageAdapter(WebWeatherActivity.this, R.layout.listview, al);
                lv.setAdapter(aa);

                aa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WWA", "Database error occurred", databaseError.toException());
            }
        });
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("WWA", "Database error occurred", databaseError.toException());
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                String time = "" + android.text.format.DateFormat.format("dd-MM-yyyy (HH:mm:ss)", date);
                Message task = new Message(etText.getText().toString(), time, name);
                messagePOJOReference.push().setValue(task);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            firebaseAuth.signOut();

            Intent i = new Intent(getBaseContext(), MainActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
