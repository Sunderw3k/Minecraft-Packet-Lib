package net.sunderw.packetlib.utils;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.packets.PacketRegistry;
import net.sunderw.packetlib.streams.PacketInputStream;
import net.sunderw.packetlib.streams.PacketOutputStream;

import java.io.IOException;

public class PacketUtils {

    public static final int SEGMENT_BITS = 0x7F;
    public static final int CONTINUE_BIT = 0x80;

    /**
     * Writes a full uncompressed packet <code>packet</code> into the output stream <code>out</code>
     * @param out The output stream to write to
     * @param packet The packet to write
     * @throws IOException When writing the bytes has failed
     */
    public static <T extends Packet> void sendPacketUncompressed(PacketOutputStream out, T packet) throws IOException {
        out.writeVarInt(packet.getBuffer().toByteArray().length);
        out.write(packet.getBuffer().toByteArray());
        out.flush();
    }

    /**
     * Writes a full compressed <code>packet</code> into the output stream <code>out</code>
     * @param out The output stream to write to
     * @param packet The packet to write
     * @throws IOException When writing the bytes has failed
     */
    public static <T extends Packet> void sendPacketCompressed(PacketOutputStream out, T packet) throws IOException {
        if (packet.getBuffer().toByteArray().length <= 256) {
            out.writeVarInt(packet.getBuffer().toByteArray().length + 1);
            out.writeVarInt(0);
            out.write(packet.getBuffer().toByteArray());
            out.flush();
        } else {
            throw new RuntimeException("Packet too long (WIP)");
        }
    }

    /**
     * Reads an uncompressed <code>packet</code> from the input stream <code>in</code>
     * @param in The input stream to read from
     * @param connectionState The server connection state (1 = STATUS, 2 = LOGIN, 3 = PLAY)
     * @param fromServer Whether the packet is from the server
     * @return The recieved packet
     * @throws IOException When reading the bytes has failed
     */
    public static <T extends Packet> T readPacketUncompressed(PacketInputStream in, int connectionState, boolean fromServer) throws IOException {
        int size = in.readVarInt();
        int packetId = in.readVarInt();

        try {
            //noinspection unchecked
            return (T) PacketRegistry.getPacketByID(packetId, connectionState, fromServer).getDeclaredConstructor(PacketInputStream.class).newInstance(in);
        } catch (Exception e) {
            return null;
        }
    }
}
