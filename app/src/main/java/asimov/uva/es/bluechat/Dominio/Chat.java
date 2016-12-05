package asimov.uva.es.bluechat.Dominio;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase representativa de un chat
 * @author David Robles Gallardo
 * @author Silvia Arias Herguedas
 * @author Hector Del Campo Pando
 * @author Alberto Gutierrez Perez
 */
public class Chat implements Parcelable{

    /**
     * {@link Contacto} con el que el que se establece el chat
     */
    private Contacto par;

    /**
     * Historial de mensajes del chat
     */
    private List<Mensaje> historial =  new ArrayList<>();

    /**
     * Inicializa el chat
     * @param contacto El contacto con el cual se establece el chat
     */
    public Chat(Contacto contacto) {
        par = contacto;
    }

    protected Chat(Parcel in) {
        par = in.readParcelable(Contacto.class.getClassLoader());
        in.readList(historial, Mensaje.class.getClassLoader());
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    /**
     * Consigue todos los chats disponibles
     * @return chats La lista de chats
     */
    public static List<Chat> getChats() {
        List<Chat> chats = new ArrayList<>();

        chats.add(new Chat(new Contacto("Carlos", "AA:BB:CC:DD:EE", "")));
        chats.add(new Chat(new Contacto("Ana","AA:BB:CC:DD:EE", "")));
        chats.add(new Chat(new Contacto("Diego","AA:BB:CC:DD:EE", "")));

        return chats;
    }

    public static Chat getChat(Contacto contacto) {
        //TODO David, silvia, borrad esto y que devuelva la informacion correspondiente de la BBDD
        Chat chat = new Chat(contacto);
        List<Mensaje> historial = new ArrayList<>();
        historial.add(new Mensaje("Hola!", contacto));
        historial.add(new Mensaje("¿Que tal?", contacto));
        historial.add(new Mensaje("Genial, gracias!", contacto));
        chat.setHistorial(historial);
        return chat;
    }

    /**
     * Establece el valor para el contacto con el que se establece el chat
     */
    public void setPar(Contacto par) {
        this.par = par;
    }

    /**
     * Establece el valor por defecto para el historial de mensajes
     */
    public void setHistorial(List<Mensaje> historial) {
        this.historial = historial;
    }

    /**
     * Devuelve el historial de mensajes para el chat
     * @return historial El historial de mensajes
     */
    public List<Mensaje> getHistorial() {
        return historial;
    }

    /**
     * Obtiene el contacto con el que se ha establecido el chat
     * @return {@link Contacto} con el que se ha establecido el chat
     */
    public Contacto getPar() {
        return par;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(par, 0);
        dest.writeList(historial);
    }
}
