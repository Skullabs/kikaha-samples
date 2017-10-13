package kikaha.samples.websockets.chat;

import io.undertow.websockets.core.CloseMessage;
import kikaha.core.modules.websocket.WebSocketSession;
import kikaha.urouting.api.*;

import javax.inject.Singleton;

@Singleton
@WebSocket( "chat/{room-id}" )
public class ChatRoomResource {

    @OnMessage
    public void onMessage( WebSocketSession session, String message ) {
        session.broadcast(
            createMessage(session).setAction( ChatMessage.Action.SEND )
                .setMessage( message )
        );
    }

    @OnOpen
    public void onOpen( WebSocketSession session ) {
        session.broadcast(
            createMessage(session).setAction( ChatMessage.Action.JOINED )
                .setMessage( "just joined the room" )
        );
    }

    @OnClose
    public void onClose(WebSocketSession session, CloseMessage cm ) {
        session.broadcast(
            createMessage(session).setAction( ChatMessage.Action.LEFT )
                .setMessage( "just left the room" )
        );
    }

    static ChatMessage createMessage( WebSocketSession session ){
        return new ChatMessage().setUser( session.userPrincipal().getName() );
    }
}
