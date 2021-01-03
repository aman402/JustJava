/*
  IMPORTANT: Make sure you are using the correct package name.
  This example uses the package name:
  package com.example.android.justjava
  If you get an error when copying this code into Android studio, update it to match teh package name found
  in the project's AndroidManifest.xml file. **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.text.NumberFormat;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/*
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int numberOfCoffees = 0;
    String message = "";

    /*
     * This method is called when the order button is clicked. It sets the price in the PriceTextView
     */

    public void submitOrder(View view) {
        CheckBox wCream = findViewById(R.id.whipped_cream);
        CheckBox chocolate = findViewById(R.id.chocolate);
        EditText nameETV = findViewById(R.id.name_text_view);
        String name = nameETV.getText().toString();
        if (!name.isEmpty()) {
            int totalPrice = calculatePrice(wCream.isChecked(), chocolate.isChecked());
            String totalAmount = NumberFormat.getCurrencyInstance(new Locale("en", "in")).format(totalPrice);
            createOrderSummary(totalAmount, name, wCream.isChecked(), chocolate.isChecked());
            displayOrderSummary(message);
            enableEmail();
        } else {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
    }

    // this function calculates the total price of the order

    private int calculatePrice(boolean cream, boolean chocolate) {
        int price = numberOfCoffees * 5;
        if (cream)
            price += numberOfCoffees;
        if (chocolate)
            price += 2 * numberOfCoffees;
        return price;
    }

    // This function creates a Order summary and stores it in the global variable "message"

    private void createOrderSummary(String totalAmount, String name, boolean wCream, boolean chocolate) {
        message = "Name : " + name;
        message += "\nQuantity : " + numberOfCoffees;
        message += "\nAdd Whipped Cream? " + wCream;
        message += "\nAdd Chocolate? " + chocolate;
        message += "\nTotal : " + totalAmount;
        message += "\nThank you";
    }

    /*
     * Displays the final order summary in the orderSummaryTV
     * @param (String message), takes in the final orderSummary and
     */

    private void displayOrderSummary(String message) {
        TextView orderSummaryTV = findViewById(R.id.orderSummaryTV);
        orderSummaryTV.setText(message);
    }

    /*
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantityChange() {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        String qnt = "" + numberOfCoffees;
        quantityTextView.setText(qnt);
    }
    /* This method increments the value of the quantity text view

     */

    public void increment(View view) {
        if (numberOfCoffees < 10) {
            numberOfCoffees = numberOfCoffees + 1;
            displayQuantityChange();
            disableEmail();
        } else
            Toast.makeText(this, "Not more than 10 coffees allowed", Toast.LENGTH_SHORT).show();
    }

    /* This method decrements the value of the quantity text view

     */
    public void decrement(View view) {
        if (numberOfCoffees > 0) {
            numberOfCoffees = numberOfCoffees - 1;
            displayQuantityChange();
            disableEmail();
        } else
            Toast.makeText(this, "Not less than 0 coffees allowed", Toast.LENGTH_SHORT).show();
    }

    // This function enables the EMAIL button

    private void enableEmail() {
        Button emailButton = findViewById(R.id.xml_email_button);
        emailButton.setEnabled(true);
    }

    // This function disables the EMAIL button

    private void disableEmail() {
        Button emailButton = findViewById(R.id.xml_email_button);
        emailButton.setEnabled(false);
    }

    //This functions handles all the email INTENT sending work to android system

    public void sendEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Summary of your coffee order");
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            Toast.makeText(this, "No email application found in device", Toast.LENGTH_SHORT).show();
        }
    }
}
