package ws.uabc.com.serviciosweb;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button miBoton= (Button) findViewById(R.id.miboton);

        miBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TareaConsulta tareaConsulta=new TareaConsulta();
                tareaConsulta.execute();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    private class TareaConsulta extends AsyncTask<String,Integer,Boolean>{


        @Override
        protected Boolean doInBackground(String... strings) {

            boolean resultado=true;

            String NAMESPACE="http://store.ws.com/";
            String URL="http://192.168.50.151:8080/ejemplo/Store";
            String METHOD_NAME="suma";
            String SOAP_ACTION=NAMESPACE+"Store/sumaRequest";

            SoapObject request=new SoapObject(NAMESPACE,METHOD_NAME);
            request.addProperty("a",1);
            request.addProperty("b",2);

            SoapSerializationEnvelope envelope=
           new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION,envelope);
                SoapPrimitive respuestaSOAP= (SoapPrimitive) envelope.getResponse();

                if(respuestaSOAP.getValue()!=null) {
                    System.err.println("Yep tengo uno respuesta");
                    System.err.println("Respusta: " +   respuestaSOAP.getValue());



                }else{

                    System.err.println("No tengo valores");

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }


            return resultado;
        }
    }




}
