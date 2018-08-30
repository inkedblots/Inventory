package com.example.android.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

    // To prevent someone from accidentally instantiating the contract class,
    private InventoryContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.android.inventory";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_INVENTORY = "inventory";

    /**
     * Inner class that defines constant values for the database table.
     * Each entry in the table represents a single item.
     */
    public static final class InventoryEntry implements BaseColumns {

        /** The content URI to access the inventory data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);

        /** The MIME type of the {@link #CONTENT_URI} for a list of products. */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /** The MIME type of the {@link #CONTENT_URI} for a single product. */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INVENTORY;

        /** Name of database table */
        public final static String TABLE_NAME = "inventory";

        /** Unique ID number for the product (only for use in the database table). */
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_PRODUCT_NAME = "product";
        public final static String COLUMN_PRODUCT_DESC = "description";
        public final static String COLUMN_PRICE = "price";
        public final static String COLUMN_QUANTITY = "quantity";
        public final static String COLUMN_SUPPLIER_NAME = "supplier";
        public final static String COLUMN_SUPPLIER_PHONE = "phone";
    }
}