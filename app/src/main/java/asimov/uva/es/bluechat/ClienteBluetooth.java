package asimov.uva.es.bluechat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Hilo encargado de conectarse con el servidor
 * @author David Robles Gallardo
 * @author Silvia Arias Herguedas
 * @author Hector Del Campo Pando
 * @author Alberto Gutierrez Perez
 */
public class ClienteBluetooth extends Thread {

    /**
     * Socket cliente que realiza la conexión con el servidor
     */
    private final BluetoothSocket socket;

    /**
     * Identificador único y universal
     */
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    /**
     * Mensaje predefinido de saludo que envia el cliente una vez se conecta a un servidor
     */
    private final String MENSAJECLIENTE = "Saludos desde el cliente";


    private final String ERROR = "ERROR";
    private final String TAG = "BLUETOOTH";

    /**
     * Busca el socket del servidor
     * @param mac La dirección MAC del servidor
     */
    public ClienteBluetooth(String mac){
        BluetoothDevice dispositivo = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mac);
        BluetoothSocket tmpSocket = null;
        Log.d(TAG,"CREADO CLIENTE");
        try{
            tmpSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(MY_UUID);
        }catch (IOException e){
            Log.d(ERROR,"Error preparando el socket cliente");
        }
        socket = tmpSocket;
    }

    /**
     * Conecta con el servidor
     */
    @Override
    public void run(){
       //TODO cancelar la conexion dado que hace que los envios vayan lentos

        try {
            socket.connect();
        }catch (IOException e){
            Log.d(ERROR,"Error conectando con el servidor");
            cerrar();
        }

        ConexionBluetooth nuevaConexionBluetooth = new ConexionBluetooth(socket);
        nuevaConexionBluetooth.start();

        //Enviamos un mensaje predefinido para comprobar que la conexion funciona
        nuevaConexionBluetooth.enviar(MENSAJECLIENTE.getBytes());
    }

    /**
     * Cierra el socket asociado a la conexión
     */
    public void cerrar(){
        try{
            socket.close();
        }catch (IOException e){
            Log.d(ERROR, "Error cerrando el socket cliente");
        }
    }
}