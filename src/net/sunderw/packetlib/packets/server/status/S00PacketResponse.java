package net.sunderw.packetlib.packets.server.status;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.streams.PacketInputStream;

public final class S00PacketResponse extends Packet {

    public String json;

    public S00PacketResponse(PacketInputStream stream) {
        super(0x00, stream);
    }

    public S00PacketResponse(String json) {
        super(0x00);

        this.json = json;

        write();
    }

    @Override
    protected void write() {
        try {
            stream.writeByte(id);

            stream.writeString(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(PacketInputStream stream) {
        try {
            this.json = stream.readString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
