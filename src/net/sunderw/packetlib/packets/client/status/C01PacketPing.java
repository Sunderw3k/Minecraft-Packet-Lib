package net.sunderw.packetlib.packets.client.status;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.utils.PacketUtils;

import java.io.DataInputStream;

public final class C01PacketPing extends Packet {
    private long time;

    public C01PacketPing(DataInputStream stream) {
        super(0x01, stream);
    }

    public C01PacketPing(long time) {
        super(0x01);

        this.time = time;

        write();
    }

    @Override
    protected void write() {
        try {
            stream.writeByte(id);

            stream.writeLong(time);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(DataInputStream stream) {
        try {
            this.time = stream.readLong();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
