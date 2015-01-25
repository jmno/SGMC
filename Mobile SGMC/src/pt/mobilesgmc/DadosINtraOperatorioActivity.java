package pt.mobilesgmc;

import com.example.mobilegsmc.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class DadosINtraOperatorioActivity extends Activity {
	private TableLayout tableSinaisVitais;
	private TableRow tableRowSinaisVitais;
	private TableLayout tableMedicacaoAdministrada;
	private TableRow tableRowMedicacaoAdministrada;
	private TableLayout tableBalancoHidrico;
	private TableRow tableRowBalancoHidrico;
	private TableLayout tableDrenagemVesical;
	private TableRow tableRowDrenagemVesical;
	private TableLayout tableDrenagemNasogastrica;
	private TableRow tableRowDrenagemNasogastrica;
	private TextView texto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_intra_operatorio);
		
		tableSinaisVitais = (TableLayout) findViewById(R.id.table_layout_sinais_vitais);
		tableMedicacaoAdministrada = (TableLayout) findViewById(R.id.table_layout_medicacao_administrada);
		tableBalancoHidrico = (TableLayout) findViewById(R.id.tablelayout_balanco_hidrico);
		tableDrenagemVesical = (TableLayout) findViewById(R.id.tablelayout_drenagem_vesical);
		tableDrenagemNasogastrica = (TableLayout) findViewById(R.id.tablelayout_drenagem_nasogastrica);
		texto = (TextView) findViewById(R.id.txtview_sinaisVitais);
		texto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addTableRowSinaisVitais();
				addTableRowMedicacaoAdministrada();
				addTableRowBalancoHidrico();
				addTableRowDrenagemVesical();
				addTableRowDrenagemNasogastrica();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		return super.onOptionsItemSelected(item);
	}
	
	public void addTableRowSinaisVitais()
	{
		tableRowSinaisVitais = new TableRow(DadosINtraOperatorioActivity.this);
		tableRowSinaisVitais = (TableRow) LayoutInflater.from(DadosINtraOperatorioActivity.this).inflate(R.layout.layout_row_sinais_vitais,null);

		tableSinaisVitais.addView(tableRowSinaisVitais);
	}
	public void addTableRowMedicacaoAdministrada()
	{
		tableRowMedicacaoAdministrada = new TableRow(DadosINtraOperatorioActivity.this);
		tableRowMedicacaoAdministrada = (TableRow) LayoutInflater.from(DadosINtraOperatorioActivity.this).inflate(R.layout.layout_row_medicacao_administrada, null);
		
		tableMedicacaoAdministrada.addView(tableRowMedicacaoAdministrada);
	}
	public void addTableRowBalancoHidrico()
	{
		tableRowBalancoHidrico = new TableRow(DadosINtraOperatorioActivity.this);
		tableRowBalancoHidrico = (TableRow) LayoutInflater.from(DadosINtraOperatorioActivity.this).inflate(R.layout.layout_row_balanco_hidrico,null);
		
		tableBalancoHidrico.addView(tableRowBalancoHidrico); 
	}
	public void addTableRowDrenagemVesical()
	{
		tableRowDrenagemVesical = new TableRow(DadosINtraOperatorioActivity.this);
		tableRowDrenagemVesical = (TableRow) LayoutInflater.from(DadosINtraOperatorioActivity.this).inflate(R.layout.layout_row_drenagem_vesical,null); 
		
		tableDrenagemVesical.addView(tableRowDrenagemVesical);
	}
	public void addTableRowDrenagemNasogastrica()
	{
		tableRowDrenagemNasogastrica = new TableRow(DadosINtraOperatorioActivity.this);
		tableRowDrenagemNasogastrica = (TableRow) LayoutInflater.from(DadosINtraOperatorioActivity.this).inflate(R.layout.layout_row_drenagem_nasogastrica,null); 
		
		tableDrenagemNasogastrica.addView(tableRowDrenagemNasogastrica);
		
	}
	
}
