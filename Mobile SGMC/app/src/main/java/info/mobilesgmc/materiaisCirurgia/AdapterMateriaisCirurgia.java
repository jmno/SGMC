package info.mobilesgmc.materiaisCirurgia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import info.mobilesgmc.MaterialActivity;
import info.mobilesgmc.modelo.ProdutosCirurgia;
import info.nicolau.mobilegsmc.R;

/**
 * Created by Nicolau on 13/01/15.
 **/
public class AdapterMateriaisCirurgia extends ArrayAdapter<ProdutosCirurgia> {

    private final Context context;
    private final ArrayList<ProdutosCirurgia> itemsArrayList;
    private NumberPicker np;


    public AdapterMateriaisCirurgia(Context context, ArrayList<ProdutosCirurgia> itemsArrayList) {
        super(context, R.layout.child_produtos_da_cirurgia, itemsArrayList);
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.child_produtos_da_cirurgia, parent, false);

        // 3. Get the two text view from the rowView
        TextView textView_Nome = (TextView) rowView.findViewById(R.id.textview_ProdutosNome);
        TextView textView_Quantidade = (TextView) rowView.findViewById(R.id.textView_ProdutosQuantidade);
        LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.linearLayout_ProdutoCirurgia);
        final CheckBox checkBox_preparado = (CheckBox) rowView.findViewById(R.id.checkBox_ProdutoPreparado);


        // 4. Set the text for textView

        textView_Nome.setText(itemsArrayList.get(position).getNomeProduto());
        textView_Quantidade.setText("Qtd: " +itemsArrayList.get(position).getQuantidade());
        checkBox_preparado.setChecked(itemsArrayList.get(position).getUtilizado());


        checkBox_preparado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsArrayList.get(position).setUtilizado(checkBox_preparado.isChecked());
            }
        });

        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editarOuApagarProduto(position);
                return false;
            }
        });


                // 5. retrn rowView
        return rowView;
    }


    public void editarOuApagarProduto(final int position)
    {
        final ProdutosCirurgia p = (ProdutosCirurgia) itemsArrayList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        np = new NumberPicker(context);
        builder.setIcon(R.drawable.ic_launcher);


        builder.setTitle("Editar / Apagar ?");
        builder.setMessage(p.getNomeProduto())
                .setCancelable(true)
                .setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);

                        alert.setTitle("Escolha a quantidade: ");


                        String[] nums = new String[100];
                        for (int i = 0; i < nums.length; i++)
                            nums[i] = Integer.toString(i);

                        np.setMinValue(1);
                        np.setMaxValue(nums.length - 1);
                        np.setWrapSelectorWheel(false);
                        np.setDisplayedValues(nums);
                        np.setValue(p.getQuantidade() + 1);

                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int valor = np.getValue() - 1;
                                itemsArrayList.get(position).setQuantidade(valor);
                                MaterialActivity.atualizaProdutos();
                            }
                        });

                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Cancel.
                            }
                        });

                        alert.setView(np);
                        alert.show();
                    }
                })
                .setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        itemsArrayList.remove(position);
                        MaterialActivity.atualizaProdutos();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
