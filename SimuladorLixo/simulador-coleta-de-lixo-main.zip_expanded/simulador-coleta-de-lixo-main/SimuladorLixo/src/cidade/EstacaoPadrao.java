package cidade;

import application.Simulacao;
import estruturasDeDados.Fila;
import estruturasDeDados.Lista;

public class EstacaoPadrao extends EstacaoTransferencia{

    private int lixoArmazenado;
    private Fila filaCaminhoesPequenos;
    private Lista listaCaminhoesGrandes;
    
    private int tempoMaximoEspera = 30; // minutos (pode ser configurável)
    private int tempoEsperaAtual = 0;

    public EstacaoPadrao(String nome) {
        super(nome);
        this.lixoArmazenado = 0;
        this.filaCaminhoesPequenos = new Fila(); 
        this.listaCaminhoesGrandes = new Lista();
    }

    @Override
    public void receberCaminhaoPequeno(CaminhaoPequeno caminhao) {
    	filaCaminhoesPequenos.enqueue(caminhao);
        System.out.println("Caminhão pequeno chegou à estação " + nome + " após " + caminhao.getTempoDeViagem() + " minutos de viagem.");
    }
    
    public void processarFila() {
        boolean caminhoesEsperando = filaCaminhoesPequenos.tamanho() > 0;
        
        if (caminhoesEsperando) {
            tempoEsperaAtual++; // Incrementa o tempo de espera
            
            System.out.println("Caminhões pequenos esperando há " + tempoEsperaAtual + 
                             " minutos (limite: " + tempoMaximoEspera + " minutos)");
            
            // Verificar se excedeu o tempo máximo
            if (tempoEsperaAtual >= tempoMaximoEspera) {
                System.out.println("ALERTA: Tempo de espera excedido! Acionando caminhão grande adicional.");
                CaminhaoGrande novoCaminhao = new CaminhaoGrandePadrao();
                listaCaminhoesGrandes.add(novoCaminhao, 0);
                tempoEsperaAtual = 0; // Reseta o contador
            }
        } else {
            tempoEsperaAtual = 0; // Reseta se não há caminhões esperando
        }

        // Processamento normal da fila
        while (filaCaminhoesPequenos.tamanho() > 0) {
            CaminhaoPequeno pequeno = filaCaminhoesPequenos.verProximoDaFila();
            if (pequeno != null) {
                int descarregado = pequeno.descarregar();
                lixoArmazenado += descarregado;
                filaCaminhoesPequenos.dequeue();
                
                System.out.println("Caminhão pequeno ID " + pequeno.getId() + 
                                 " descarregou " + descarregado + "kg na estação " + nome);
            }
        }
    }
    


    @Override
    public void descarregarParaCaminhaoGrande(CaminhaoGrande novoCaminhao) {
        CaminhaoGrande caminhaoAtual = listaCaminhoesGrandes.verProximoDaLista();

        if (caminhaoAtual == null) {
            caminhaoAtual = novoCaminhao;
            listaCaminhoesGrandes.add(caminhaoAtual, 0);
        }

        caminhaoAtual.carregar(lixoArmazenado);
        System.out.println("Estação " + nome + " carregou caminhão grande com " + lixoArmazenado + "kg.");
        lixoArmazenado = 0;

        if (caminhaoAtual.prontoParaPartir()) {
        	System.out.println("Caminhão grande está cheio e será enviado ao aterro com " + caminhaoAtual.getCargaAtual() + "kg.");
            caminhaoAtual.descarregar();
            listaCaminhoesGrandes.remove(0);
        }
    }
    
    public void enviarCaminhoesGrandesParaAterro() {
        CaminhaoGrande caminhao = listaCaminhoesGrandes.verProximoDaLista();

        if (caminhao != null && caminhao.getCargaAtual() > 0) {
            System.out.println("Caminhão grande enviado para o aterro (tolerância atingida) com " + caminhao.getCargaAtual() + "kg.");
            caminhao.descarregar();
            listaCaminhoesGrandes.remove(0);
        }
    }
    
    public void configurarTempoMaximoEspera(int minutos) {
        this.tempoMaximoEspera = minutos;
    }
}
