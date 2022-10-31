package net.sunderw.packetlib.packets;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.sunderw.packetlib.packets.client.login.C00PacketLogin;
import net.sunderw.packetlib.packets.client.status.C00PacketRequest;
import net.sunderw.packetlib.packets.client.status.C01PacketPing;
import net.sunderw.packetlib.packets.server.login.S00PacketDisconnect;
import net.sunderw.packetlib.packets.server.login.S01PacketEncryptionRequest;
import net.sunderw.packetlib.packets.server.status.S00PacketResponse;
import net.sunderw.packetlib.packets.server.status.S01PacketPong;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {
    public static final Map<Integer, BiMap<?, ?>> CLIENT = new HashMap<>();
    public static final BiMap<Integer, Class<? extends Packet>> C_STATUS = HashBiMap.create();
    public static final BiMap<Integer, Class<? extends Packet>> C_LOGIN = HashBiMap.create();
    public static final BiMap<Integer, Class<? extends Packet>> C_PLAY = HashBiMap.create();

    public static final Map<Integer, BiMap<?, ?>> SERVER = new HashMap<>();
    public static final BiMap<Integer, Class<? extends Packet>> S_STATUS = HashBiMap.create();
    public static final BiMap<Integer, Class<? extends Packet>> S_LOGIN = HashBiMap.create();
    public static final BiMap<Integer, Class<? extends Packet>> S_PLAY = HashBiMap.create();

    static {
        C_STATUS.put(0x00, C00PacketRequest.class);
        C_STATUS.put(0x01, C01PacketPing.class);

        C_LOGIN.put(0x00, C00PacketLogin.class);

        C_STATUS.put(0x00, C00PacketLogin.class);

        CLIENT.put(1, C_STATUS);
        CLIENT.put(2, C_LOGIN);
        CLIENT.put(3, C_PLAY);


        S_STATUS.put(0x00, S00PacketResponse.class);
        S_STATUS.put(0x01, S01PacketPong.class);

        S_LOGIN.put(0x00, S00PacketDisconnect.class);
        S_LOGIN.put(0x01, S01PacketEncryptionRequest.class);

        SERVER.put(1, S_STATUS);
        SERVER.put(2, S_LOGIN);
        SERVER.put(3, S_PLAY);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Packet> Class<T> getPacketByID(int packetId, int connectionState, boolean fromServer) {
        if (fromServer) {
            return ((Class<T>) SERVER.get(connectionState).get(packetId));
        } else {
            return ((Class<T>) CLIENT.get(connectionState).get(packetId));
        }
    }
}
