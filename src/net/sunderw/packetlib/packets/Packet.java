package net.sunderw.packetlib.packets;

import net.sunderw.packetlib.packets.server.status.S00PacketResponse;
import net.sunderw.packetlib.utils.PacketUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {

    protected final int id;
    protected final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    protected final DataOutputStream stream = new DataOutputStream(buffer);

    public Packet(int id, DataInputStream stream) {
        this(id);

        read(stream);
    }
    public Packet(int id) {
        this.id = id;
    }

    public DataOutputStream getStream() {
        return stream;
    }
    public ByteArrayOutputStream getBuffer() {
        return buffer;
    }

    @SuppressWarnings("unused")
    protected abstract void write();

    @SuppressWarnings("unused")
    protected abstract void read(DataInputStream stream);
}
