package net.sunderw.packetlib.packets.client.login;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.streams.PacketInputStream;

public final class C00PacketLogin extends Packet {

    public String username;

    public C00PacketLogin(PacketInputStream stream) {
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

            stream.writeString(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(PacketInputStream stream) {
        try {
            this.username = stream.readString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
