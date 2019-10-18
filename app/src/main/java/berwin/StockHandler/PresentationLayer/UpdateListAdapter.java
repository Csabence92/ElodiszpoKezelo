package berwin.StockHandler.PresentationLayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import berwin.StockHandler.Others.VersionContoller;
import berwin.StockHandler.R;

public class UpdateListAdapter extends ArrayAdapter {
    private Context mContext;
    private int resource;
    private ArrayList<VersionContoller> versionContollerArrayList;
    private LayoutInflater inflater;
    public UpdateListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VersionContoller> versionContollerArrayList) {
        super(context, resource, versionContollerArrayList);
        this.mContext = context;
        this.resource = resource;
        this.versionContollerArrayList = versionContollerArrayList;
        this.inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @SuppressLint({"ViewHolder", "SetTextI18n"})
    public View getView(final int position, View convertView, ViewGroup parents){
        convertView = this.inflater.inflate(this.resource,null);
        TextView versionTextView = convertView.findViewById(R.id.update_version_name_textview);
        if (versionContollerArrayList.size() > 0) {
            versionTextView.setText(this.mContext.getResources().getString(R.string.ver) + versionContollerArrayList.get(position).getVersionName());
        }else{
            String noData = "Nem kaptuk meg az adatokat";
            versionTextView.setText(noData);
        }
        return convertView;

    }
}
