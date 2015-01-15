package pt.mobilesgmc.sinaisVitais;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilegsmc.R;

import java.util.ArrayList;

/**
 * Created by Nicolau on 13/01/15.
 */
public class AdapterSinaisVitais extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ParentSinaisVitais> listaParent;
    private TextView hora;
    private EditText tamin;
    private ChildSinaisVitais child;
    public AdapterSinaisVitais(Context context, ArrayList<ParentSinaisVitais> listaParent) {
        this.context = context;
        this.listaParent = listaParent;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ChildSinaisVitais> listaChild = ((ParentSinaisVitais) listaParent
                .get(groupPosition)).getChildren();
        return listaChild.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {



           child = (ChildSinaisVitais) getChild(groupPosition,
                    childPosition);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_sinais_vitais, null);

            hora = (TextView) convertView.findViewById(R.id.textView_Hora);
            hora.setText(child.getHora());
            tamin = (EditText) convertView.findViewById(R.id.editText_TaMin);
            tamin.setText(child.getTaMin());
            tamin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                child.setTaMin(s.toString());
                Log.i("tamin",child.getTaMin());

            }
        });
        hora.setClickable(true);
        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.setHora(childPosition+"");
                Log.i("child" , childPosition+"");
                Log.i("child_",child.getHora());
            }
        });
        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child.setHora(childPosition+"");
                notifyDataSetChanged();
                hora.setText(child.getHora());
                Log.i("child" , childPosition+"");
                Log.i("child_",child.getHora());

            }
        });*/
         //TEXTVIEW e ECT


        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
            return listaParent.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listaParent.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
            return listaParent.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        ParentSinaisVitais p = (ParentSinaisVitais) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_sinais_vitais, null);
        }

        TextView txtBloco = (TextView) convertView.findViewById(R.id.txtview_DadosIntra_Anestesia);
        txtBloco.setText(p.getSinaisVitais());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
