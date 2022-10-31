package net.sunderw.packetlib.packets.server.login;

import net.sunderw.packetlib.packets.Packet;
import net.sunderw.packetlib.streams.PacketInputStream;

public class S01PacketEncryptionRequest extends Packet {

    public String serverId;
    public int keyLength;
    public byte[] key;
    public int verifyTokenLength;
    public byte[] verifyToken;

    public S01PacketEncryptionRequest(PacketInputStream stream) {
        super(0x00, stream);
    }

    public S01PacketEncryptionRequest(String serverId, int keyLength, byte[] key, int verifyTokenLength, byte[] verifyToken) {
        super(0x00);

        this.serverId = serverId;
        this.keyLength = keyLength;
        this.key = key;
        this.verifyTokenLength = verifyTokenLength;
        this.verifyToken = verifyToken;

        write();
    }

    @Override
    protected void write() {
        try {
            stream.writeByte(id);

            stream.writeString(serverId);
            stream.writeVarInt(keyLength);
            stream.write(key);
            stream.writeVarInt(verifyTokenLength);
            stream.write(verifyToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void read(PacketInputStream stream) {
        try {
            this.serverId = stream.readString();
            this.keyLength = stream.readVarInt();
            this.key = stream.readNBytes(keyLength);
            this.verifyTokenLength = stream.readVarInt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
