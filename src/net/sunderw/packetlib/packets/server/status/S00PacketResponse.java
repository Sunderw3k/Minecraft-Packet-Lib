package net.sunderw.packetlib.packets.server.status;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.utils.PacketUtils;

import java.io.DataInputStream;

public final class S00PacketResponse extends Packet {

    private String json;

    public S00PacketResponse(DataInputStream stream) {
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

            PacketUtils.writeString(stream, json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(DataInputStream stream) {
        try {
            this.json = PacketUtils.readString(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
