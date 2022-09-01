package net.sunderw.packetlib.packets.client.play;

import net.sunderw.packetlib.packets.client.status.C01PacketPing;
import net.sunderw.packetlib.utils.PacketUtils;
import net.sunderw.packetlib.packets.Packet;

import java.io.DataInputStream;

public final class C01PacketChat extends Packet {

    private String message;

    public C01PacketChat(DataInputStream stream) {
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

            PacketUtils.writeString(stream, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(DataInputStream stream) {
        try {
           this.message = PacketUtils.readString(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
