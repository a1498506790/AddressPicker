package com.github.addresspicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private AddressDialogFragment mAddressDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddressDialogFragment = AddressDialogFragment.newInstance();

        findViewById(R.id.btn_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddressDialogFragment.show(getSupportFragmentManager());
            }
        });
    }


}
