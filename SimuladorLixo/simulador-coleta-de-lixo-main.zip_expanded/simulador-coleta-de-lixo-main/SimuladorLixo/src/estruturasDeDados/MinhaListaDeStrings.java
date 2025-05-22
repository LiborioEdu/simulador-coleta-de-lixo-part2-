package estruturasDeDados;

class No {
    public String valor;
    public No proximo;

    public No(String valor) {
        this.valor = valor;
        this.proximo = null;
    }
}

public class MinhaListaDeStrings {
    private No inicio;
    private int tamanho;

    public void adicionar(String valor) {
        No novo = new No(valor);
        if (inicio == null) {
            inicio = novo;
        } else {
            No atual = inicio;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
        tamanho++;
    }

    public int tamanho() {
        return tamanho;
    }

    public String get(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        No atual = inicio;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.valor;
    }

    public MinhaListaDeStrings copiar() {
        MinhaListaDeStrings copia = new MinhaListaDeStrings();
        No atual = inicio;
        while (atual != null) {
            copia.adicionar(atual.valor);
            atual = atual.proximo;
        }
        return copia;
    }
}
