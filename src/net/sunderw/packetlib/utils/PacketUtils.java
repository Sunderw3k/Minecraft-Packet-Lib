package net.sunderw.packetlib.utils;

import net.sunderw.packetlib.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PacketUtils {

    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    /**
     * Writes the String <code>value</code> to the <code>out</code> stream
     * @param out The output stream to write to
     * @param value The value to write
     * @throws IOException When writing the bytes has failed
     */
    public static void writeString(DataOutputStream out, String value) throws IOException {
        writeVarInt(out, value.length());
        out.writeBytes(value);
    }

    /**
     * Writes the VarInt <code>value</code> to the <code>out</code> stream
     * @param out The output stream to write to
     * @param value The value to write
     * @throws IOException When writing the bytes has failed
     */
    public static void writeVarInt(DataOutputStream out, int value) throws IOException {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                out.writeByte(value);
                return;
            }

            out.writeByte((value & SEGMENT_BITS) | CONTINUE_BIT);
            value >>>= 7;
        }
    }

    /**
     * Reads a String from the <code>out</code> stream
     * @param in The input stream to read from
     * @throws IOException When writing the bytes has failed
     */
    public static String readString(DataInputStream in) throws IOException {
        int length = PacketUtils.readVarInt(in);
        return new String(in.readNBytes(length), StandardCharsets.UTF_8);
    }

    /**
     * Reads a VarInt from the <code>out</code> stream
     * @param in The input stream to read from
     * @throws IOException When writing the bytes has failed
     */
    public static int readVarInt(DataInputStream in) throws IOException {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = in.readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    /**
     * Writes a full uncompressed packet <code>packet</code> into the output stream <code>out</code>
     * @param out The output stream to write to
     * @param packet The packet to write
     * @throws IOException When writing the bytes has failed
     */
    public static <T extends Packet> void sendPacketUncompressed(DataOutputStream out, T packet) throws IOException {
        writeVarInt(out, packet.getBuffer().toByteArray().length);
        out.write(packet.getBuffer().toByteArray());
        out.flush();
    }

    /**
     * Writes a full compressed <code>packet</code> into the output stream <code>out</code>
     * @param out The output stream to write to
     * @param packet The packet to write
     * @throws IOException When writing the bytes has failed
     */
    public static <T extends Packet> void sendPacketCompressed(DataOutputStream out, T packet) throws IOException {
        if (packet.getBuffer().toByteArray().length <= 256) {
            writeVarInt(out, packet.getBuffer().toByteArray().length + 1);
            writeVarInt(out, 0);
            out.write(packet.getBuffer().toByteArray());
            out.flush();
        } else {
            throw new RuntimeException("Packet too long (WIP)");
        }
    }
}
