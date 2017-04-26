package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.viewexplosion.ExplosionField;
import com.example.administrator.viewexplosion.factory.FlyawayFactory;

public class FlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly);
        final ExplosionField explosionField = new ExplosionField(this, new FlyawayFactory());
        explosionField.addListener(findViewById(R.id.iv_icon));
        explosionField.setSrc(R.drawable.wb);
        ImageView ivIcon = (ImageView) findViewById(R.id.iv_icon);
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                explosionField.explode(v);
            }
        });
    }
}
