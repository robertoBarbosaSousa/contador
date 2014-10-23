package com.barbosa.contadordecervas.activity.view;

public interface UnicaView {

    void atualizaValores( Integer qtdeCervas, String formataValorDivida );

    void salvaDados( Integer qtdeCervas, float preco );

    void atualizaPreco( String precoUnitario );

    void mostrarMensagem( int ind );

}
