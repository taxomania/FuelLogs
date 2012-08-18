package uk.co.taxomania.apps.fuellog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddLogDialog extends Dialog {
    private final View v;
    private final EditText litres;
    private final EditText price;
    private final EditText mileage;

    public AddLogDialog(final Context context) {
        super(context);
        v = View.inflate(context, R.layout.dialog_add_log, null);
        litres = ((EditText) v.findViewById(R.id.litres));
        litres.setHint("Litres");
        price = ((EditText) v.findViewById(R.id.price));
        price.setHint("Price per litre");
        mileage = ((EditText) v.findViewById(R.id.mileage));
        mileage.setHint("Odometer");
        setTitle("Add log");
    }

    public double getLitres() {
        try {return Double.parseDouble(litres.getText().toString());}
        catch(NumberFormatException e){return 0;}
    }

    public int getMileage() {
        try{return Integer.parseInt(mileage.getText().toString());}
        catch(NumberFormatException e){return 0;}
    }

    public double getPrice() {
        try{return Double.parseDouble(price.getText().toString());}
        catch(NumberFormatException e){return 0;}
    }

    public void setButton(final View.OnClickListener listener) {
        v.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(v);
    }
}
