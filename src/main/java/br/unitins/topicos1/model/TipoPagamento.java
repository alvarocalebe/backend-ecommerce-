package br.unitins.topicos1.model;

public enum TipoPagamento {

    AGUARDANDO_SELECIONAR_PAGAMENTO("Aguardando selecionar Pagamento"),
    CARTAO_DE_CREDITO("Cartão de Crédito"),
    CARTAO_DE_DEBITO("Cartão de Débito"),
    BOLETO_BANCARIO("Boleto Bancário"),
    TRANSFERENCIA_BANCARIA("Transferência Bancária"),
    PIX("Pix");

    private final String nome;

    TipoPagamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
