package cidade;

public abstract class EstacaoTransferencia {
    protected String nome;

    public EstacaoTransferencia(String nome) {
        this.nome = nome;
    }

    public abstract void receberCaminhaoPequeno(CaminhaoPequeno caminhao);
    public abstract void descarregarParaCaminhaoGrande(CaminhaoGrande caminhao);
}
