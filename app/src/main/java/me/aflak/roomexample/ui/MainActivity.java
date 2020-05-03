package me.aflak.roomexample.ui;

import android.content.DialogInterface;
import android.os.AsyncTask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.roomexample.MyApp;
import me.aflak.roomexample.R;
import me.aflak.roomexample.adapter.MyAdapter;
import me.aflak.roomexample.database.UserDao;
import me.aflak.roomexample.entity.User;

public class MainActivity extends AppCompatActivity {
//    @BindView(R.id.first_name) TextView firstName;
//    @BindView(R.id.last_name) TextView lastName;
//    @BindView(R.id.age) TextView age;
    @BindView(R.id.resyclerview) RecyclerView recyclerView;
    @BindView(R.id.insertdata) Button button;

    @Inject UserDao userDao;

    private MyAdapter adapter;
    List<User>myUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        MyApp.app().appComponent().inject(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntryBox();

            }
        });


        // Room requests need to be done on a working thread
        new Request().execute();
    }

    private void showEntryBox() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view= LayoutInflater.from(this).inflate(R.layout.entry_box,null);

        final EditText etname=(EditText)view.findViewById(R.id.etname);
        final EditText etemail=(EditText)view.findViewById(R.id.etemail);
        final EditText etcity=(EditText)view.findViewById(R.id.etcity);

        builder.setView(view);
        builder.setNegativeButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String name=etname.getText().toString();
                final String email=etemail.getText().toString();
                final String city=etcity.getText().toString();
                final Integer age = Integer.valueOf(city);

                User myUserData=new User();
                myUserData.setFirstName(name);
                myUserData.setLastName(email);
                myUserData.setAge(age);
                userDao.insert(myUserData);
//                MainActivity.myDatabase.myDao().AddData(myUserData);
//                getdata();

                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

    private void getdata(List<User> users){
//        List<User> myUsers=new ArrayList<>();
        User myUser=new User();
//        myUsers=MainActivity.myDatabase.myDao().getMyData();
        userDao.all();

        adapter=new MyAdapter(users);
        recyclerView.setAdapter(adapter);

    }

//    private void displayUser(final User user){
//        uid.setText(String.format(Locale.getDefault(),"uid : %d", user.getUid()));
//        firstName.setText(String.format("first_name : %s", user.getFirstName()));
//        lastName.setText(String.format("last_name : %s", user.getLastName()));
//        age.setText(String.format(Locale.getDefault(),"age : %d", user.getAge()));
//    }

    private class Request extends AsyncTask<Void,Void,List<User>>{
        @Override
        protected List<User> doInBackground(Void... voids) {
            showEntryBox();
            return userDao.all();
//            if(userDao.count()==0){
////                User user = new User("Ade", "Guntur", 18);
////                userDao.insert(user);
////
////                showEntryBox();
//                return userDao.all();
//            }
//            else{
//                return userDao.all().get(0);
//            }
        }

        @Override
        protected void onPostExecute(List<User> user) {
         getdata(user);
        }
    }
}
