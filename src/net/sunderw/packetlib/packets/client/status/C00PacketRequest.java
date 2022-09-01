package net.sunderw.packetlib.packets.client.status;

import net.sunderw.packetlib.packets.Packet;

import java.io.DataInputStream;

public final class C00PacketRequest extends Packet {

    public C00PacketRequest(DataInputStream stream) {
        super(0x00, stream);
    }

    public C00PacketRequest() {
        super(0x00);

        write();
    }

    @Override
    protected void write() {
        try {
            stream.writeByte(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(DataInputStream stream) {

    }
}
