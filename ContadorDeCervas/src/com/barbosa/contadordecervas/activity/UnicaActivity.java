package com.barbosa.contadordecervas.activity;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.barbosa.contadordecervas.R;
import com.barbosa.contadordecervas.activity.view.UnicaView;
import com.barbosa.contadordecervas.presenter.UnicaPresenter;

@ContentView( R.layout.activity_unica )
public class UnicaActivity extends RoboActivity implements UnicaView {

    private static final String KEY_PRECO = "SHARED_PRECO";

    private static final String KEY_QTDE = "SHARED_QTDE";

    @InjectView( R.id.preco )
    private EditText preco;

    @InjectView( R.id.menosUma )
    private Button menosUma;

    @InjectView( R.id.maisUma )
    private Button maisUma;

    @InjectView( R.id.contador )
    private TextView contador;

    @InjectView( R.id.total )
    private TextView total;

    @InjectView( R.id.reset )
    private Button reset;

    @Inject
    private UnicaPresenter presenter;

    private SharedPreferences sharedPref;

    private AlertDialog resetCof;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        this.setContentView( R.layout.activity_unica );
        this.presenter.init( this, this.getPreferences().getInt( UnicaActivity.KEY_QTDE, 0 ), this.getPreferences()
                .getFloat( UnicaActivity.KEY_PRECO, 1f ) );
        this.maisUma.setOnClickListener( this.clickMaisUma );
        this.menosUma.setOnClickListener( this.clickMenosUma );
        this.preco.setOnKeyListener( this.click );
        this.reset.setOnClickListener( this.resetClick );
        this.buildResetDialog();
    }

    private void buildResetDialog() {
        this.resetCof = new AlertDialog.Builder( this ).create();
        this.resetCof.setIcon( android.R.drawable.ic_dialog_alert );
        this.resetCof.setTitle( "Mais Já?!?!?!" );
        this.resetCof.setMessage( "Saideira, pé na bunda, expulsadeira ou mamãe já chamou?" );
        this.resetCof.setButton( DialogInterface.BUTTON_POSITIVE, "CHEGA!!!", new DialogInterface.OnClickListener() {

            @Override
            public void onClick( DialogInterface dialog, int buttonId ) {
                UnicaActivity.this.presenter.reset();
                UnicaActivity.this.resetCof.dismiss();
                UnicaActivity.this.finish();
            }
        } );
        this.resetCof.setButton( DialogInterface.BUTTON_NEGATIVE, "Vou tomar Mais Uma!!!!",
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick( DialogInterface dialog, int buttonId ) {
                    UnicaActivity.this.resetCof.dismiss();
                }
            } );
    }

    private OnClickListener resetClick = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            UnicaActivity.this.resetCof.show();
        }
    };

    private OnKeyListener click = new OnKeyListener() {

        @Override
        public boolean onKey( View v, int keyCode, KeyEvent keyEvent ) {
            if ( ( keyEvent.getAction() == KeyEvent.ACTION_DOWN ) && ( keyCode == KeyEvent.KEYCODE_ENTER ) ) {
                UnicaActivity.this.presenter.mudouPreco( UnicaActivity.this.preco.getEditableText().toString() );
                UnicaActivity.this.preco.clearFocus();
                return true;
            }
            return false;
        }
    };

    private OnClickListener clickMaisUma = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            UnicaActivity.this.presenter.maisUma();
        }
    };

    private OnClickListener clickMenosUma = new OnClickListener() {

        @Override
        public void onClick( View v ) {
            UnicaActivity.this.presenter.menosUma();
        }
    };

    @Override
    public void atualizaValores( Integer qtdeCervas, String divida ) {
        this.contador.setText( qtdeCervas.toString() );
        this.total.setText( divida );
    }

    private SharedPreferences getPreferences() {
        if ( this.sharedPref == null ) {
            this.sharedPref = PreferenceManager.getDefaultSharedPreferences( this );
        }
        return this.sharedPref;
    }

    @Override
    public void salvaDados( Integer qtdeCervas, float preco ) {
        SharedPreferences.Editor editor = this.getPreferences().edit();
        editor.putFloat( UnicaActivity.KEY_PRECO, preco );
        editor.putInt( UnicaActivity.KEY_QTDE, qtdeCervas );
        editor.commit();
    }

    @Override
    public void atualizaPreco( String precoUnitario ) {
        this.preco.setText( precoUnitario );
    }

    @Override
    public void mostrarMensagem( int ind ) {

        Toast.makeText( this, this.getResources().getStringArray( R.array.mensagens )[ind], Toast.LENGTH_LONG ).show();
    }
}
