package com.example.kasparsfisers.coffe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btnInc, btnDec;
    CheckBox creamCheck, chocoCheck;
    EditText nameField;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.buttonOrder);
        btn1.setOnClickListener(this);
        btnInc = (Button) findViewById(R.id.btnInc);
        btnInc.setOnClickListener(this);
        btnDec = (Button) findViewById(R.id.btnDec);
        btnDec.setOnClickListener(this);
        creamCheck = (CheckBox) findViewById(R.id.creamChB);
        chocoCheck = (CheckBox) findViewById(R.id.chocolateChB);
        nameField = (EditText) findViewById(R.id.nameField);
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantityTextView);
        quantityTextView.setText("" + number);
    }


    private int calculatePrice(boolean cream, boolean choco) {
        int basePrice = 5;
        if (cream) {
            basePrice += 1;
        }
        if (choco) {
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    private void increment() {
        if (quantity == 100) {
            Toast.makeText(this, R.string.maxLimit, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    private void decrement() {
        if (quantity == 1) {
            Toast.makeText(this, R.string.minLimit, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    private String createOrderSummary(String name, boolean cream, boolean choco) {
        String priceMessage = getString(R.string.order_name,name);
        priceMessage += "\n" +
                getString(R.string.cream,cream);
        priceMessage += "\n" +
                getString(R.string.choco,choco);
        priceMessage += "\n" +
                getString(R.string.order_quantity,quantity);
        priceMessage += "\n" +
                getString(R.string.total) + calculatePrice(cream, choco);
        priceMessage += "\n" +
                getString(R.string.thanks);
        return priceMessage;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonOrder) {
            String name = nameField.getText().toString();
            String Msg = createOrderSummary(name, creamCheck.isChecked(), chocoCheck.isChecked());

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "kasparsfisers@inbox.lv");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject,name));
            intent.putExtra(Intent.EXTRA_TEXT, Msg);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        }


        if (v.getId() == R.id.btnInc) {
            increment();
        }

        if (v.getId() == R.id.btnDec) {
            decrement();
        }
    }
}