package us.mifeng.rongyundemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.rong.imkit.RongIM;

public class MainActivity extends AppCompatActivity {
    private Button mBtn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.mBtn1);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(MainActivity.this, "222222", "title");
            }
        });
    }

}
