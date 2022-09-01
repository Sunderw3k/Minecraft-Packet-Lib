package net.sunderw.packetlib.packets.play;

import net.sunderw.packetlib.utils.PacketUtils;
import net.sunderw.packetlib.packets.Packet;

public final class C01PacketChat extends Packet {

    private final String message;

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
}
