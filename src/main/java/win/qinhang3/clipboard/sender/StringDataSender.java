package win.qinhang3.clipboard.sender;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.net.Socket;

/**
 * Created by hang on 2017/6/14.
 */
public class StringDataSender implements DataSender{

    private Transferable transferable;
    private byte[] data;

    public StringDataSender(Transferable transferable) {
        this.transferable = transferable;
    }

    @Override
    public String getHash() {
        if (data == null){
            getData();
        }
        return DigestUtils.md5Hex(data);
    }

    @Override
    public void send(Writer writer) {
        if (data == null){
            getData();
        }
        try {
            writer.write("String");
            writer.write("\n");
            writer.write(new String(Hex.encodeHex(data)));
            writer.write("\n");
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getData(){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();ObjectOutputStream oos = new ObjectOutputStream(baos)){
            String string = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            System.out.println("current String:" + string);
            oos.writeObject(string);
            oos.flush();
            data = baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
