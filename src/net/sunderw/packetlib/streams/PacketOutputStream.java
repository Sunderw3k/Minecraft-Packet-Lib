package net.sunderw.packetlib.streams;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static net.sunderw.packetlib.utils.PacketUtils.CONTINUE_BIT;
import static net.sunderw.packetlib.utils.PacketUtils.SEGMENT_BITS;

public class PacketOutputStream extends DataOutputStream {

    public PacketOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * Writes the String <code>value</code> to the <code>out</code> stream
     * @param value The value to write
     * @throws IOException When writing the bytes has failed
     */
    public void writeString(String value) throws IOException {
        this.writeVarInt(value.length());
        this.writeBytes(value);
    }

    /**
     * Writes the VarInt <code>value</code> to the <code>out</code> stream
     * @param value The value to write
     * @throws IOException When writing the bytes has failed
     */
    public void writeVarInt(int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                this.writeByte(value);
                return;
            }

            this.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);
            value >>>= 7;
        }
    }
}
