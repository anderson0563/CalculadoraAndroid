package br.santos.anderson.calculadora;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class Calculadora extends Service {

    public final IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        Calculadora getService(){
            return Calculadora.this;
        }
    }

    public Calculadora() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public double fazerOperacao(String operando1, String operacao, String operando2){
        double a = Double.parseDouble(operando1);
        double b = Double.parseDouble(operando2);
        double resultado=0;

        System.out.println(operando1);
        System.out.println(operando2);
        System.out.println(operacao);
        switch(operacao.charAt(0)){
            case '+': resultado = a+b; break;
            case '-': resultado = a-b; break;
            case 'X': resultado = a*b; break;
            case '/': if(b!=0)
                        resultado = a/b;
                      else
                        resultado = 0;
                      break;
        }
        return resultado;
    }
}