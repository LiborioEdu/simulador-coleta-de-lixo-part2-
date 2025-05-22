package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GerenciadorDeLogs {
    private static final String ARQUIVO_LOGS = "logs_simulacao.txt";
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    public static void registrarLog(String mensagem) {
        // Registrar no arquivo
        try (FileWriter fw = new FileWriter(ARQUIVO_LOGS, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            String dataHora = LocalDateTime.now().format(FORMATO_DATA);
            out.println("[" + dataHora + "] " + mensagem);
            
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de logs: " + e.getMessage());
        }
    }
    
    public static void limparLogsAnteriores() {
        try (FileWriter fw = new FileWriter(ARQUIVO_LOGS, false);
             PrintWriter out = new PrintWriter(fw)) {
            
            out.print(""); // Limpa o arquivo
            
        } catch (IOException e) {
            System.err.println("Erro ao limpar arquivo de logs: " + e.getMessage());
        }
    }
}
