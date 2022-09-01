package net.sunderw.packetlib.packets.client.login;

import net.sunderw.packetlib.packets.client.status.C00PacketRequest;
import net.sunderw.packetlib.utils.PacketUtils;
import net.sunderw.packetlib.packets.Packet;

import java.io.DataInputStream;

public final class C00PacketLogin extends Packet {

    private String username;

    public C00PacketLogin(DataInputStream stream) {
        super(0x00, stream);
    }

    public C00PacketLogin(String username) {
        super(0x00);

        this.username = username;

        write();
    }

    @Override
    protected void write() {
        try {
            stream.writeByte(id);

            PacketUtils.writeString(stream, username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(DataInputStream stream) {
        try {
            this.username = PacketUtils.readString(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
