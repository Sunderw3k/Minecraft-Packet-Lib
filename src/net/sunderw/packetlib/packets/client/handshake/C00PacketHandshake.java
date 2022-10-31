package net.sunderw.packetlib.packets.client.handshake;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.streams.PacketInputStream;

public final class C00PacketHandshake extends Packet {

    public int version, port, state;
    public String hostname;

    public C00PacketHandshake(PacketInputStream stream) {
        super(0x00, stream);
    }
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

            stream.writeVarInt(version);
            stream.writeString(hostname);
            stream.writeShort(port);
            stream.writeVarInt(state);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(PacketInputStream stream) {
        try {
            this.version = stream.readVarInt();
            this.hostname = stream.readString();
            this.port = stream.readShort();
            this.state = stream.readVarInt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}