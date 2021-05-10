package com.example.a09_01_nhom10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.a09_01_nhom10.model.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity
    {
    private Button btAdd,btAll,btGet,btUpdate,btDelete;
    private EditText txtId,txtName,txtMark;
    private RadioButton male,female;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private SQLiteStudentHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
       btUpdate.setEnabled(false);
       btDelete.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        sqLiteHelper=new SQLiteStudentHelper(this);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=txtName.getText().toString();
                boolean g=false;
                if(male.isChecked()){
                    g=true;
                }
                try{
                    double m=Double.parseDouble(txtMark.getText().toString());
                    Student s=new Student(name,g,m);
                    sqLiteHelper.addStudent(s);
                }catch(NumberFormatException e){
                    System.out.println(e);
                }
            }
        });
        btAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Student> list=sqLiteHelper.getAll();
                adapter.setStudents(list);
                recyclerView.setAdapter(adapter);
            }
        });
        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int id=Integer.parseInt(txtId.getText().toString());
                    Student s=sqLiteHelper.getStudentById(id);
                    if(s==null){
                        Toast.makeText(getApplicationContext(),"Khong co",Toast.LENGTH_SHORT).show();
                    }else{
                        txtName.setText(s.getName());
                        txtMark.setText(s.getMark()+"");
                        if(s.isGender()){
                            male.setChecked(true);
                        }else{
                            female.setChecked(true);
                        }
                        btDelete.setEnabled(true);
                        btUpdate.setEnabled(true);
                        btAdd.setEnabled(false);
                        txtId.setEnabled(false);
                    }
                }catch(NumberFormatException e){
                    System.out.println(e);
                }
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int id=Integer.parseInt(txtId.getText().toString());
                    String n=txtName.getText().toString();
                    boolean g=false;
                    if(male.isChecked()){
                        g=true;
                    }
                    double m=Double.parseDouble(txtMark.getText().toString());
                    Student s=new Student(id,n,g,m);
                    sqLiteHelper.update(s);
                    btDelete.setEnabled(false);
                    btUpdate.setEnabled(false);
                    btAdd.setEnabled(true);
                    txtId.setEnabled(true);
                }catch(NumberFormatException e){
                    System.out.println(e);
                }
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int id=Integer.parseInt(txtId.getText().toString());
                    sqLiteHelper.delete(id);
                    btDelete.setEnabled(false);
                    btUpdate.setEnabled(false);
                    btAdd.setEnabled(true);
                    txtId.setEnabled(true);
                }catch(NumberFormatException e){
                    System.out.println(e);
                }
            }
        });

    }

    private void initView() {
        btAdd=findViewById(R.id.btAdd);
        btAll=findViewById(R.id.btAll);
        btGet=findViewById(R.id.btGet);
        btUpdate=findViewById(R.id.btUpdate);
        btDelete=findViewById(R.id.btDelete);

        txtId=findViewById(R.id.stID);
        txtName=findViewById(R.id.stName);
        txtMark=findViewById(R.id.stMark);

        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        recyclerView=findViewById(R.id.recyclerView);
    }
}
