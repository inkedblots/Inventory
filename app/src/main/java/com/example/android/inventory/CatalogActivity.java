package com.example.android.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.inventory.data.InventoryContract;
import com.example.android.inventory.data.InventoryDbHelper;

import java.util.ArrayList;

/**
 * Displays list of inventory that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {
    public ArrayList<CatalogList> ListDatabase = new ArrayList<>();
    RecyclerView recyclerView;

    /**
     * Database helper that will provide us access to the database
     */
    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        ListDatabase.add(new CatalogList("Matthew", "News App Pt. 1", "1",
                1, "Ahead", "555-555-5555"));
        ListDatabase.add(new CatalogList("Olivia", "Tour Guide", "2", 1,
                "Ahead", "555-555-5555"));
        ListDatabase.add(new CatalogList("Chris", "News", "3",
                1, "Ahead", "555-555-5555"));

        LinearLayoutManager linearLayoutManager = new
                LinearLayoutManager(this);

        CatalogAdapter listAdapter = new
                CatalogAdapter() {
                    @Override
                    public void onBindViewHolder(@NonNull CatalogAdapter.ListHolder holder, int position) {

                    }
                };
        listAdapter.Adapter();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter);
    }

    public class CatalogList {
        private String ProductName;
        private String ProductDesc;
        private String Price;
        private int Quantity;
        private String SupplierName;
        private String SupplierPhone;

        //This is how your object is created and also the order in which items should be
        // added to the ArrayList

        public CatalogList(String ProductName, String ProductDesc, String Price, int Quantity,
                           String SupplierName, String SupplierPhone) {
            this.ProductName = ProductName;
            this.ProductDesc = ProductDesc;
            this.Price = Price;
            this.Quantity = Quantity;
            this.SupplierName = SupplierName;
            this.SupplierPhone = SupplierPhone;

        }
        //These are your GETTERS to return the selected items
        public String getProductName() {
            return ProductName;
        }

        public String getProductDesc() {
            return ProductDesc;
        }

        public String getPrice() {
            return Price;
        }

        public int getQuantity() {
            return Quantity;
        }

        public String getSupplierName() {
            return SupplierName;
        }

        public String getSupplierPhone() {
            return SupplierPhone;
        }
        // To access our database, we instantiate our subclass of SQLiteOpenHelper and pass the
        // context, which is the current activity.
        mDbHelper = new InventoryDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the inventory database.
     */
    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper and pass the
        // context, which is the current activity.
        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InventoryContract.InventoryEntry._ID,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC,
                InventoryContract.InventoryEntry.COLUMN_PRICE,
                InventoryContract.InventoryEntry.COLUMN_QUANTITY,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME,
                InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE
        };

        Cursor cursor = db.query(
                InventoryContract.InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = findViewById(R.id.productName);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The inventory table contains <number of rows in Cursor> inventory.
            // _id - name - desc - price
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The inventory table contains " + cursor.getCount() + " products.\n\n");
            displayView.append(InventoryContract.InventoryEntry._ID + " - " +
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME + " - " +
                    InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC + " - " +
                    InventoryContract.InventoryEntry.COLUMN_PRICE + "\n");


            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME);
            int descColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC);
            int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDesc = cursor.getString(descColumnIndex);
                String currentPrice = cursor.getString(priceColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDesc + " - " +
                        currentPrice));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded product data into the database. For debugging purposes only.
     */
    private void insertProduct() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys, and product attributes
        // are the values.
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME, "Product Name");
        values.put(InventoryContract.InventoryEntry.COLUMN_PRODUCT_DESC, "Product Desc");
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE, 0.00);
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, 2);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME, "Supplier");
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE, "Phone");

        long newRowId = db.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New row ID " + newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertProduct();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
