package com.example.abhishek.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameText.getText().toString();
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whippedCream_check_box);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolate.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
//        displayMessage(orderSummary);

        String subject = getString(R.string.subject, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Creates the order summary
     *
     * @param name            takes in the name of the user
     * @param price           is the amount user needs to pay based on coffee cups ordered
     * @param hasWhippedCream is the boolean value if the whipped cream is ordered
     * @param hasChocolate    is boolean value if chocolate topping is selected
     * @return summary as String with Name, Quantity, Toppings Added and Total price
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        return getString(R.string.order_Summary, name, hasWhippedCream, hasChocolate, quantity, NumberFormat.getCurrencyInstance().format(price));
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is true if whipped cream is added to Rs. 1/topping
     * @param hasChocolate    is true if the Chocolate as topping is added to Rs. 2/topping
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = quantity * 5;
        if (hasWhippedCream) {
            price += (quantity * 1);
        }
        if (hasChocolate) {
            price += (quantity * 2);
        }
        return price;
    }

    /**
     * This method increases the value of quantity by 1
     */
    public void increment(View view) {

        //Check if the quantity does not goes beyond 100
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.maxCoffeeWarning), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method increases the value of quantity by 1
     */
    public void decrement(View view) {
        //Check if the quantity does not goes below 1
        if (quantity <= 1) {
            Toast.makeText(this, getString(R.string.minCoffeeWarning), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }

}