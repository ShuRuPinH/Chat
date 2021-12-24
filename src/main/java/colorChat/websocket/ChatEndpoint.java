package colorChat.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import colorChat.model.ChatText;
import colorChat.model.Message;
import colorChat.model.News;


@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {
    private Session session;
    private boolean timer = false;
    private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username ) throws Exception, EncodeException {
      /*  if (users.containsKey(session) || users.containsValue(username)){
            sysMes("такой ник уже есть");
            return;
        }*/
        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), username);

        Message message = new Message();
        message.setFrom(username);
        message.setSystem(true);
        message.setContent("connected,  session #"+session.hashCode());

        message.setContent(ChatText.put(message));
        broadcast(message);


    }

    @OnMessage
    public void onMessage(Session session, Message message) throws Exception, EncodeException {
        message.setFrom(users.get(session.getId()));

        if (message.getFrom().equals("X22B14B") && message.getContent().equals("#22B14B")) {
            News.start();

            return;
        }
        if (message.getFrom().equals("X22B14B") && message.getContent().equals("getText")) {
            ChatText.getText();
            return;
        }

        message.setContent(ChatText.put(message));
        broadcast(message);

    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setSystem(true);
        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                        .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sysMes(String mess) {
        Message message = new Message();
        message.setFrom("Xff9600");
        message.setSystem(false);
        message.setContent( ChatText.anek(mess));
        try {
            broadcast(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
    }

}
