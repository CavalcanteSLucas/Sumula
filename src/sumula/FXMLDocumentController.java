package sumula;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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

/**
 *
 * @author Lucas Cavalcante
 */
public class FXMLDocumentController implements Initializable {    
    // Importar elementos de tela **********************************************
    /* Botões */
    @FXML private Button btnConfTimes;
    @FXML private Button btnStart;
    @FXML private Button btnStop;
    @FXML private Button btnPedirTempo;
    
    /* Textos */
    @FXML private Text txtMinuto;
    @FXML private Text txtSegundo;
    @FXML private Text txtTimeA;
    @FXML private Text txtTimeB;
    @FXML private Text txtTempPedido1T;
    @FXML private Text txtTempPedido2T;
    
    /* Caixas de texto */
    @FXML private TextField edtTimeA;
    @FXML private TextField edtTimeB;
    @FXML private TextField edtJogador1TA;
    
    /* Outros */
    @FXML private MenuItem menuNovaSumula;
    @FXML private Pane painelNovaSumula;
    @FXML private Pane painelDigTimes;
    
    
    // Variáveis ***************************************************************
    Timer cronometro;
    TimerTask tarefa;
    int segundo = 0;
    int minuto = 0;
    boolean pediuTempo = false;
    
    // Eventos *****************************************************************
    /* Botão Nova Súmula */
    @FXML
    private void novaSumula(){
        painelDigTimes.setVisible(true);
    }
    
    /* Botão para confirmar times */
    @FXML
    private void clicouConfTimes(){
        painelDigTimes.setVisible(false);
        painelNovaSumula.setVisible(true);
        txtTimeA.setText(edtTimeA.getText());
        txtTimeB.setText(edtTimeB.getText());
    }
    
    // Cronômetro **************************************************************
    /* Botão para iniciar cronômetro */
    @FXML
    private void clicouStart(ActionEvent event) {
        if(btnStop.getText().equals("Zerar")) btnStop.setText("Parar");
        
        btnStart.setDisable(true); // Desabilita o botão iniciar
        btnStop.setDisable(false); // Habilita o botão parar / zerar
        
        cronometro = new Timer();
        tarefa = new TimerTask() {
            @Override
            public void run() {
                segundo++;
                if(segundo == 60){ // Acrescenta um minuto
                    minuto = Integer.parseInt(txtMinuto.getText()) + 1;
                    if(minuto < 10){
                        txtMinuto.setText("0" + minuto);
                    }else{
                        txtMinuto.setText(Integer.toString(minuto));
                    }
                    segundo = 0;
                }
                
                if(segundo < 10){ // Acrescenta um segundo
                    txtSegundo.setText("0" + segundo);
                }else{
                    txtSegundo.setText(Integer.toString(segundo));
                }
            }
        }; // Fim evento tarefa
        int milissegundo = 1000;
        cronometro.schedule(tarefa, milissegundo, milissegundo);
    } // Fim botão start
    
    /* Botão para parar ou zerar cronômetro */
    @FXML
    void clicouStop(ActionEvent event) {
        btnStart.setDisable(false);
        
        if(btnStop.getText().equals("Parar")){
            btnStart.setText("Retomar"); // Muda o texto do botão para retomar
            btnStop.setText("Zerar"); // Transforma o botão parar em zerar
            cronometro.cancel();
            cronometro = null;
        }else if(btnStop.getText().equals("Zerar")){
            btnStart.setText("Iniciar"); // Muda o texto do botão para iniciar
            btnStop.setText("Parar"); // Transforma o botão zerar em parar
            txtSegundo.setText("00");
            txtMinuto.setText("00");
            btnStop.setDisable(true);
            segundo = 0;
            minuto = 0;
            cronometro = null;
        }
    }
    
    // *************************************************************************
    /* Botão para pedir tempo */
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
        
    }
}
