package net.sunderw.packetlib.packets;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static net.sunderw.packetlib.utils.PacketUtils.CONTINUE_BIT;
import static net.sunderw.packetlib.utils.PacketUtils.SEGMENT_BITS;

public class PacketInputStream extends DataInputStream {
    public PacketInputStream(InputStream in) {
        super(in);
    }

    /**
     * Reads a String from the <code>out</code> stream
     * @return The read value
     * @throws IOException When reading the bytes has failed
     */
    public String readString() throws IOException {
        int length = this.readVarInt();
        return new String(in.readNBytes(length), StandardCharsets.UTF_8);
    }

    /**
     * Reads a VarInt from the <code>out</code> stream
     * @return The read value
     * @throws IOException When reading the bytes has failed
     */
    public int readVarInt() throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = this.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }
}
