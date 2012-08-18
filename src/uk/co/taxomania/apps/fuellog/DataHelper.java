package uk.co.taxomania.apps.fuellog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class DataHelper {
    static final class Logs implements BaseColumns {
        private static final String TABLE_NAME = "Logs";
        private static final String LITRES = "litres";
        private static final String PRICE = "price";
        private static final String MILEAGE = "mileage";
        static final String TIME = "time";
    } // class Logs

    private final Context mContext;

    private volatile SQLiteDatabase mDb = null;

    private static DataHelper sDataHelper = null;

    public static synchronized DataHelper getInstance(final Context context) {
        if (sDataHelper == null) {
            sDataHelper = new DataHelper(context);
        } // if
        return sDataHelper;
    } // getInstance(Context)

    private DataHelper(final Context context) {
        mContext = context;
        mDb = new OpenHelper(mContext).getWritableDatabase();
    } // DataHelper(Context)

    public synchronized Cursor selectAll() {
        return mDb.query(Logs.TABLE_NAME, new String[] { Logs._ID, Logs.TIME }, null, null, null,
                null, Logs._ID + " DESC");
    } // selectAll()

    public synchronized Log selectLog(final long id) {
        if (id == -1) return null;
        final Cursor c = mDb.query(Logs.TABLE_NAME, new String[] { Logs.LITRES, Logs.PRICE,
                Logs.MILEAGE }, Logs._ID + "=?", new String[] { Long.toString(id) }, null, null,
                null);

        if (c.moveToFirst()) { return new Log(c.getDouble(0), c.getDouble(1), c.getInt(2), -1L); }
        return null;
    } // selectLog(long)

    public synchronized void insert(final Log log) {
        final ContentValues cv = new ContentValues(4);
        cv.put(Logs.LITRES, log.getLitres());
        cv.put(Logs.PRICE, log.getPrice());
        cv.put(Logs.MILEAGE, log.getMileage());
        cv.put(Logs.TIME, log.getTime());

        mDb.beginTransaction();
        try {
            mDb.insert(Logs.TABLE_NAME, null, cv);
            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        } // finally
    } // insert(String, int, int)

    private static final class OpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "fuel_logs.db";
        private static final int DATABASE_VERSION = 1;

        OpenHelper(final Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        } // OpenHelper(Context)

        @Override
        public void onCreate(final SQLiteDatabase db) {
            // @formatter:off
            db.execSQL("CREATE TABLE " + Logs.TABLE_NAME + " (" 
                    + Logs._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
                    + Logs.LITRES + " INTEGER, "
                    + Logs.PRICE + " INTEGER, "
                    + Logs.MILEAGE + " INTEGER, "
                    + Logs.TIME + " INTEGER)");
            // @formatter:on
        } // onCreate(SQLiteDatabase)

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            android.util.Log
                    .w("Example", "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + Logs.TABLE_NAME);
            onCreate(db);
        } // onUpgrade(SQLiteDatabase, int, int)
    } // OpenHelper
}
