package com.barbosa.contadordecervas.presenter;

import java.text.NumberFormat;

import com.barbosa.contadordecervas.activity.view.UnicaView;

public class UnicaPresenter {

    private static final int DIVISOR_MENSAGENS = 5;

    private static final int MAXIMNO_MENSAGENS = 10;

    private UnicaView view;

    private Integer qtdeCervas;

    private Double divida;

    private Double precoUnitario;

    public void init( UnicaView view, Integer qtdeCervas, float precoUnitario ) {
        this.view = view;
        this.qtdeCervas = qtdeCervas;
        this.precoUnitario = Double.valueOf( precoUnitario );
        this.view.atualizaPreco( this.formataValor( this.precoUnitario ).replace( "R$", "" ).replace( ",", "." ) );
        this.calcularPrejuizo();
    }

    private void calcularPrejuizo() {
        this.divida = this.qtdeCervas * this.precoUnitario;
        this.view.atualizaValores( this.qtdeCervas, this.formataValorDivida() );
        this.view.salvaDados( this.qtdeCervas, this.precoUnitario.floatValue() );
    }

    public void reset() {
        this.qtdeCervas = 0;
        this.divida = 0d;
        this.precoUnitario = 1d;
        this.calcularPrejuizo();
        this.view.atualizaPreco( this.formataValor( this.precoUnitario ).replace( "R$", "" ).replace( ",", "." ) );
    }

    private String formataValorDivida() {
        String divida = this.formataValor( this.divida );
        double dezPerc = ( this.divida / 100 ) * 10;
        divida += " + 10% = " + this.formataValor( dezPerc + this.divida );
        return divida;
    }

    private String formataValor( Double valor ) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format( valor );
    }

    public void maisUma() {
        this.qtdeCervas++;
        if ( ( this.qtdeCervas % UnicaPresenter.DIVISOR_MENSAGENS ) == 0 ) {
            int ind = this.qtdeCervas / UnicaPresenter.DIVISOR_MENSAGENS;
            if ( ind <= UnicaPresenter.MAXIMNO_MENSAGENS ) {
                this.view.mostrarMensagem( ind );
            }
        }

        this.calcularPrejuizo();
    }

    public void menosUma() {
        if ( this.qtdeCervas > 0 ) {
            this.qtdeCervas--;
        }
        this.calcularPrejuizo();
    }

    public void mudouPreco( String fromTela ) {
        this.precoUnitario = Double.valueOf( fromTela );
        this.calcularPrejuizo();

    }

}
