package net.sunderw.packetlib.packets.server.login;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.streams.PacketInputStream;

public class S00PacketDisconnect extends Packet {
    public String reason;

    public S00PacketDisconnect(String reason) {
        super(0x00);

        this.reason = reason;

        write();
    }

    @Override
    protected void write() {
        try {
            stream.writeByte(id);

            stream.writeString(reason);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(PacketInputStream stream) {
        try {
            this.reason = stream.readString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
