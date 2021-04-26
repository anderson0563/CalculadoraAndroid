package br.santos.anderson.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    TextView numero;
    int fase;
    String operando1, operacao, operando2;
    private Calculadora calculadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numero = (TextView)findViewById(R.id.numero);
        fase=0;
        operando1=operando2=operacao="";
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, Calculadora.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    public void onClickButton(View v){
        Button btn = (Button)v;
        String b = btn.getText().toString();
        double n;
        switch(b.charAt(0)){
            case '.':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': if(fase==0)
                        numero.setText(numero.getText().toString() + b);
                      else {
                            numero.setText(b);
                            fase=0;
                       }
                      break;
            case 'A': numero.setText("");
                      operando1=operando2=operacao="";
                      break;
            case '%': n = Double.parseDouble(numero.getText().toString());
                      n = n / 100;
                      numero.setText(String.valueOf(n));
                      break;
            case '+': if(b.length()>1)
                        if(b.charAt(1)=='/'){
                            n = Double.parseDouble(numero.getText().toString());
                            n = n * (-1);
                            numero.setText(String.valueOf(n));
                            break;
                        }
            case '-':
            case 'X':
            case '/':

                      if(operando1.length()>0) {
                          if(fase==2){
                              operacao=b;
                              fase=1;
                          }else{
                              operando2 = numero.getText().toString();
                              operando1 = String.valueOf(calculadora.fazerOperacao(operando1, operacao, operando2));
                              numero.setText(operando1);
                          }

                      }else{
                          operando1 = numero.getText().toString();
                      }
                      operacao = b;
                      fase=1;
                      break;
            case '=':
                operando2 = numero.getText().toString();
                operando1 = String.valueOf(calculadora.fazerOperacao(operando1, operacao, operando2));
                numero.setText(operando1);
                fase=2;
        }


    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Calculadora.MyBinder calc = (Calculadora.MyBinder) iBinder;
        calculadora = calc.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        calculadora = null;
    }
}