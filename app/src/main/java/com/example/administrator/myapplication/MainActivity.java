package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.administrator.viewexplosion.ExplosionField;
import com.example.administrator.viewexplosion.factory.BooleanFactory;
import com.example.administrator.viewexplosion.factory.ExplodeParticleFactory;
import com.example.administrator.viewexplosion.factory.FlyawayFactory;
import com.example.administrator.viewexplosion.factory.VerticalAscentFactory;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        StellarMap stellarMap = new StellarMap(this);
//        // 1.设置内部的TextView距离四周的内边距
//        int padding = 15;
//        stellarMap.setInnerPadding(padding, padding, padding, padding);
//        stellarMap.setAdapter(new StellarMap.Adapter() {
//            @Override
//            public int getGroupCount() {
//                return 0;
//            }
//
//            @Override
//            public int getCount(int i) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int i, int i1, View view) {
//                return null;
//            }
//
//            @Override
//            public int getNextGroupOnPan(int i, float v) {
//                return 0;
//            }
//
//            @Override
//            public int getNextGroupOnZoom(int i, boolean b) {
//                return 0;
//            }
//        });
//        // 设置默认显示第几组的数据
//        stellarMap.setGroup(0, true);// 这里默认显示第0组
//        // 设置x和y方向上的显示的密度
//        stellarMap.setRegularity(15, 15);// 如果值设置的过大，有可能造成子View摆放比较稀疏

        ExplosionField explosionField = new ExplosionField(this,new BooleanFactory());
        explosionField.addListener(findViewById(R.id.text));
        explosionField.addListener(findViewById(R.id.layout1));

        ExplosionField explosionField2 = new ExplosionField(this,new FlyawayFactory());
        explosionField2.addListener(findViewById(R.id.text2));
        explosionField2.addListener(findViewById(R.id.layout2));

        ExplosionField explosionField4 = new ExplosionField(this,new ExplodeParticleFactory());
        explosionField4.addListener(findViewById(R.id.text3));
        explosionField4.addListener(findViewById(R.id.layout3));

        ExplosionField explosionField5 = new ExplosionField(this,new VerticalAscentFactory());
        explosionField5.addListener(findViewById(R.id.text4));
        explosionField5.addListener(findViewById(R.id.layout4));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}