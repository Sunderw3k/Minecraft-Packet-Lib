package net.sunderw.packetlib.packets.client.play;

import net.sunderw.packetlib.streams.PacketInputStream;
import net.sunderw.packetlib.packets.Packet;

public final class C01PacketChat extends Packet {

    public String message;

    public C01PacketChat(PacketInputStream stream) {
        super(0x01, stream);
    }

    public C01PacketChat(String message) {
        super(0x01);

        this.message = message;

        write();
    }

    @Override
    protected void write() {
        try {
            stream.writeByte(id);

            stream.writeString(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(PacketInputStream stream) {
        try {
           this.message = stream.readString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
