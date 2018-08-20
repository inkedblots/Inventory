package com.example.android.inventory.data;

import android.provider.BaseColumns;

public final class InventoryContract {

    // To prevent someone from accidentally instantiating the contract class,
    private InventoryContract() {
    }

    /**
     * Inner class that defines constant values for the database table.
     * Each entry in the table represents a single item.
     */
    public static final class InventoryEntry implements BaseColumns {

        /**
         * Name of database table
         */
        public final static String TABLE_NAME = "inventory";

        /**
         * Unique ID number for the product (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of product.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME = "name";

        /**
         * Name of product.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_DESC = "product_desc";

        /**
         * Price of product.
         * <p>
         * Type: TEXT
         */
          public final static String COLUMN_PRICE = "price";

        /**
         * Quantity of product
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_QUANTITY = "quantity";

        /**
         * Supplier name.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME = "supplier_name";

        /**
         * Supplier phone number.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone";
    }
}