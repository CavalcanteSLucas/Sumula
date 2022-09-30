/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package sumula;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sumula.model.Relogio;

/**
 *
 * @author Lucas Cavalcante
 */
public class FXMLDocumentController implements Initializable {    
    // Importar elementos de tela
    @FXML private MenuItem menuNovaSumula;
    @FXML private Pane painelNovaSumula;
    @FXML private Pane painelDigTimes;
    @FXML private TextField edtTimeA;
    @FXML private TextField edtTimeB;
    @FXML private Button btnConfTimes;
    @FXML private Button btnStart;
    @FXML private Text txtMinuto;
    @FXML private Text txtSegundo;
    @FXML private Text txtTimeA;
    @FXML private Text txtTimeB;
    @FXML private Button btnPedirTempo;
    @FXML private Text txtTempPedido1T;
    @FXML private Text txtTempPedido2T;
    @FXML private TextField edtJogador1TA;
    
    // Variáveis
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    int min = 0;
    boolean pediuTempo = false;
    Relogio stopwatch1;
    
    // Butões
    @FXML
    private void novaSumula(){
        painelDigTimes.setVisible(true);
    }
    
    @FXML
    private void clicouConfTimes(){
        painelDigTimes.setVisible(false);
        painelNovaSumula.setVisible(true);
        txtTimeA.setText(edtTimeA.getText());
        txtTimeB.setText(edtTimeB.getText());
    }
    
    @FXML
    private void clicouStart(ActionEvent event) {
        stopwatch1.start();
        
        for(;stopwatch1.getStopWatchRunning() == true;){
            System.out.println("Elapsed time in milliseconds: "
                    + stopwatch1.getElapsedMilliseconds());

            System.out.println("Elapsed time in seconds: "
                    + stopwatch1.getElapsedSeconds());

            System.out.println("Elapsed time in minutes: "
                    + stopwatch1.getElapsedMinutes());

            System.out.println("Elapsed time in hours: "
                    + stopwatch1.getElapsedHours());
        }

        
        final Runnable runnable = new Runnable() {
            int countdownStarter = 0;

            public void run() {
                countdownStarter++;

                if (countdownStarter == 60) {
                    min = Integer.parseInt(txtMinuto.getText()) + 1;
                    if(min < 10){
                        txtMinuto.setText("0" + Integer.toString(min));
                    }else{
                        txtMinuto.setText(Integer.toString(min));
                    }
                    countdownStarter = 0;
                }
                if(countdownStarter < 10){
                    txtSegundo.setText("0" + Integer.toString(countdownStarter));
                }else{
                    txtSegundo.setText(Integer.toString(countdownStarter));
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
    }
    
    @FXML
    void clicouStop(ActionEvent event) {
        stopwatch1.stop();
        
        scheduler.shutdown();
        
    }
    
    @FXML
    void clicouPedirTempo(ActionEvent event) {
        pediuTempo = true;
        if(pediuTempo){
            txtTempPedido1T.setText("1° T - " + txtMinuto.getText() + " : " + txtSegundo.getText());
            btnPedirTempo.setDisable(true);
        }
    }
    
    // Classe inicializar
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stopwatch1 = new Relogio();
        
        
        
        /*stopwatch1.start();
        Fibonacci(45);
        stopwatch1.stop();*/


        
    }
    
    /*private static BigInteger Fibonacci(int n) {
        if (n < 2)
            return BigInteger.ONE;
        else
            return Fibonacci(n - 1).add(Fibonacci(n - 2)); 
    }*/
}
