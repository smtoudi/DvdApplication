package com.example.rent.dvdapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.List;

public class DvdDetailActivity extends AppCompatActivity {

    private long dvdId;
    private TextView title;
    private TextView genre;
    private TextView year;
    private TextView description;
    private ImageView imageView;
    private  DvdEntity  entity;
    private TextView amount;

    private Button plusButton;
    private Button minusButton;
    private Button deleteButton;
    private Dao<DvdEntity, Long> dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvd_detail);

        dvdId = getIntent().getExtras().getLong(MainActivity.DVD_ID);

        this.title = (TextView) findViewById(R.id.activity_detail_title);
        this.genre = (TextView) findViewById(R.id.activity_detail_genre);
        this.year = (TextView) findViewById(R.id.activity_detail_year);
        this.description = (TextView) findViewById(R.id.activity_detail_description);
        this.imageView = (ImageView) findViewById(R.id.activity_detail_imageview);
        this.amount = (TextView) findViewById(R.id.activity_dvd_detail_amount);
        this.plusButton = (Button) findViewById(R.id.activity_dvd_detail_plus);
        this.minusButton = (Button) findViewById(R.id.activity_dvd_detail_minus);
        this.deleteButton = (Button) findViewById(R.id.activity_dvd_detail_deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteButtonClicked();
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlusMinusButtonClicked("plus");
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlusMinusButtonClicked("minus");
            }
        });

        DvdDbHelper dvdDbHelper = new DvdDbHelper(this);
        dao = dvdDbHelper.getDvdEntityDao();
        try {
            entity = dao.queryForId(dvdId);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        setViews();
    }

    private void onDeleteButtonClicked() {

        try {
            dao.delete(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finish();
    }

    private void onPlusMinusButtonClicked(String type) {

        if(type.equals("plus")){

            entity.setAmount(entity.getAmount()+1);
        }

        else{
            if(entity.getAmount()!=0) entity.setAmount(entity.getAmount()-1);

        }

        try {
            dao.createOrUpdate(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        amount.setText(entity.getAmount()+"");
    }



    private void setViews(){

        if(entity!=null){
            this.title.setText(entity.getTitle());
            this.genre.setText(entity.getGenre());
            this.year.setText(entity.getYear()+"");
            this.description.setText(entity.getDescription());

            if(entity.getAmount()==0) this.amount.setText("out of stock");
            else this.amount.setText(entity.getAmount()+"");

            Picasso.with(this)
                    .load(entity.getPhotoUrl())
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }

    }
}
