package net.sunderw.packetlib.packets.client.handshake;

import net.sunderw.packetlib.Variables;
import net.sunderw.packetlib.utils.PacketUtils;
import net.sunderw.packetlib.packets.Packet;

import java.io.DataInputStream;

public final class C00PacketHandshake extends Packet {

    private int version, port, state;
    private String hostname;

    public C00PacketHandshake(int version, String hostname, int port, int state) {

        super(0x00);

        this.version = version;
        this.hostname = hostname;
        this.port = port;
        this.state = state;

        write();
    }

    @Override
    protected void write() {
        try {
            stream.writeByte(id);

            PacketUtils.writeVarInt(stream, version);
            PacketUtils.writeString(stream, hostname);
            stream.writeShort(port);
            PacketUtils.writeVarInt(stream, state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(DataInputStream stream) {
        try {
            this.version = PacketUtils.readVarInt(stream);
            this.hostname = PacketUtils.readString(stream);
            this.port = stream.readShort();
            this.state = PacketUtils.readVarInt(stream);

            Variables.state = state;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
