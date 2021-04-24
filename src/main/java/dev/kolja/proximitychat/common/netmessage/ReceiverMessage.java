package dev.kolja.proximitychat.common.netmessage;

import net.minecraft.network.PacketBuffer;

import java.util.LinkedList;
import java.util.List;

public class ReceiverMessage {
    private List<String> list;

    public ReceiverMessage(List<String> list) {
        this.list = list;
    }

    public List<String> read() {
        return list;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(list.size());
        for(String s : list) {
            String[] parts = s.split("\\.");
            for(String part : parts) {
                int iPart = Integer.parseInt(part);
                buffer.writeInt(iPart);
            }
        }
    }

    public void removeSelf() {
        list.remove(0);
    }

    public static ReceiverMessage decode(PacketBuffer buffer) {
        List<String> list = new LinkedList<>();
        int size = buffer.readInt();
        for(int i = 0; i < size; i++) {
            String ip = "";
            ip += buffer.readInt() + ".";
            ip += buffer.readInt() + ".";
            ip += buffer.readInt() + ".";
            ip += buffer.readInt();
            list.add(ip);
        }
        return new ReceiverMessage(list);
    }
}
