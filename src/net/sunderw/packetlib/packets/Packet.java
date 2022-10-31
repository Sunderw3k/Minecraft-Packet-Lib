package net.sunderw.packetlib.packets;


import net.sunderw.packetlib.streams.PacketInputStream;
import net.sunderw.packetlib.streams.PacketOutputStream;

import java.io.ByteArrayOutputStream;

public abstract class Packet {

    public final int id;
    protected final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    protected final PacketOutputStream stream = new PacketOutputStream(buffer);

    public Packet(int id, PacketInputStream stream) {
        this(id);

        read(stream);
    }
    public Packet(int id) {
        this.id = id;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public PacketOutputStream getStream() {
        return stream;
    }
    public ByteArrayOutputStream getBuffer() {
        return buffer;
    }

    protected abstract void write();

    protected abstract void read(PacketInputStream stream);
}
