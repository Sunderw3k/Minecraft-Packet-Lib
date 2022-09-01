package net.sunderw.packetlib.packets.server.status;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.utils.PacketUtils;

import java.io.DataInputStream;

public final class S01PacketPong extends Packet {

    private long time;

    public S01PacketPong(DataInputStream stream) {
        super(0x01, stream);
    }

    public S01PacketPong(long time) {
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
