package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.viewexplosion.ExplosionField;
import com.example.administrator.viewexplosion.factory.FlyawayFactory;

public class FlyActivity extends AppCompatActivity {

    private RelativeLayout mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly);
        mContent = (RelativeLayout) findViewById(R.id.activity_fly);
        final ExplosionField explosionField = new ExplosionField(this, new FlyawayFactory());
        explosionField.addListener(findViewById(R.id.iv_icon));
        explosionField.setSrc(R.drawable.wb);
        ImageView ivIcon = (ImageView) findViewById(R.id.iv_icon);

        final ExplosionField view = new ExplosionField(FlyActivity.this, new FlyawayFactory());
        view.setSrc(R.drawable.p1);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);


        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                view.explode();
                explosionField.explode(v);
            }
        });


    }
}
