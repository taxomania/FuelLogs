package uk.co.taxomania.apps.fuellog;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;

final class Log implements Parcelable {
    private final double litres;
    private final double price;
    private final int mileage;
    private final Calendar date = Calendar.getInstance();

    Log(final double litres, final double price, final int mileage) {
        this(litres, price, mileage, System.currentTimeMillis());
    } // Log(double, double, int)

    Log(final double litres, final double price, final int mileage, final long timestamp) {
        this.litres = litres;
        this.price = price;
        this.mileage = mileage;
        date.setTimeInMillis(timestamp);
    } // Log(double, double, int, long)

    public double getLitres() {
        return litres;
    } // getLitres()

    public int getMileage() {
        return mileage;
    } // getMileage()

    public double getPrice() {
        return price;
    } // getPrice()

    public long getTime() {
        return date.getTimeInMillis();
    } // getTime()

    @Override
    public String toString() {
        return date.get(Calendar.DATE) + "/" + date.get(Calendar.MONTH) + "/"
                + date.get(Calendar.YEAR);
    } // toString()

    @Override
    public int describeContents() {
        return 0;
    } // describeContents()

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeDouble(litres);
        dest.writeDouble(price);
        dest.writeInt(mileage);
        dest.writeLong(date.getTimeInMillis());
    } // writeToParcel(Parcel, int)

    Log(final Parcel in) {
        this(in.readDouble(), in.readDouble(), in.readInt(), in.readLong());
    } // Log(Parcel)

    public static final Parcelable.Creator<Log> CREATOR = new Parcelable.Creator<Log>() {
        public Log createFromParcel(final Parcel in) {
            return new Log(in);
        } // createFromParcel(Parcel)

        public Log[] newArray(final int size) {
            return new Log[size];
        } // newArray(int)
    };
} // class Log
