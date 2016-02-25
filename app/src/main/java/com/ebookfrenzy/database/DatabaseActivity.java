package com.ebookfrenzy.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText quantityBox;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        updateButton = (Button) findViewById(R.id.updateButton);
        quantityBox =
                (EditText) findViewById(R.id.productQuantity);
    }

    public void newProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        int quantity =
                Integer.parseInt(quantityBox.getText().toString());

        Product product =
                new Product(productBox.getText().toString(), quantity);
        if (getProduct(productBox.getText().toString()) != null) {
            idView.setText("Already Exists!");
        } else {
            dbHandler.addProduct(product);
            productBox.setText("");
            quantityBox.setText("");}
            idView.setText("");
    }

    public void lookupProduct (View view) {
        Product product = getProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));
            quantityBox.setText(String.valueOf(product.getQuantity()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void updateProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        Product product = getProduct(productBox.getText().toString());
        if (product != null) {
            product.setQuantity(Integer.parseInt(quantityBox.getText().toString()));
            dbHandler.updateProduct(product);
            idView.setText(String.valueOf(product.getID()) + " Updated!");
        } else {
            idView.setText("Product not found!");
        }
    }
    private Product getProduct(String productName) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
         return dbHandler.findProduct(productName);
    }

    public void removeProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteProduct(
                productBox.getText().toString());

        if (result)
        {
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
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
