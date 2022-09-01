package net.sunderw.packetlib.packets.status;

import net.sunderw.packetlib.packets.Packet;

public final class C00PacketRequest extends Packet {

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
}
