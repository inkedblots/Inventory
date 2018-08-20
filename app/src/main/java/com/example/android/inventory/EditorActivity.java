package com.example.android.inventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventory.data.InventoryContract;
import com.example.android.inventory.data.InventoryDbHelper;

/**
 * Allows user to create a new product or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter product name
     */
    private EditText mProductNameEditText;

    /**
     * EditText field to enter product name
     */
    private EditText mProductDescEditText;

    /**
     * EditText field to enter price
     */
    private EditText mPriceEditText;

    /**
     * EditText field to enter quantity
     */
    private EditText mQuantityEditText;

    /**
     * EditText field to enter supplier name
     */
    private EditText mSupplierNameEditText;

    /**
     * EditText field to enter supplier phone number
     */
    private EditText mPhoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mProductNameEditText = findViewById(R.id.edit_product_name);
        mProductDescEditText = findViewById(R.id.edit_product_desc);
        mPriceEditText = findViewById(R.id.edit_price);
        mQuantityEditText = findViewById(R.id.edit_quantity);
        mSupplierNameEditText = findViewById(R.id.edit_supplier_name);
        mPhoneNumberEditText = findViewById(R.id.edit_supplier_phone);
    }

    private void insertProduct() {
        String nameString = mProductNameEditText.getText().toString().trim();
        String descString = mProductDescEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String phoneNumberString = mPhoneNumberEditText.getText().toString().trim();

        // Create database helper
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and product attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC, descString);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, priceString);
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantity);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, phoneNumberString);

        // Insert a new row for a product in the database, returning the ID of that new row.
        long newRowId = db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Product saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save to db
                insertProduct();
                //Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}