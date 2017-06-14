package win.qinhang3.clipboard.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import win.qinhang3.clipboard.receiver.DataReceiver;
import win.qinhang3.clipboard.receiver.StringDataReceiver;
import win.qinhang3.clipboard.sender.DataSender;
import win.qinhang3.clipboard.sender.StringDataSender;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by hang on 2017/6/14.
 */
public class ClipboardUtil {

//    public static void setSystemClipboard(String refContent){
//        String vc = refContent.trim();
//        StringSelection ss = new StringSelection(vc);
//
//        Clipboard sysClb=null;
//        sysClb = Toolkit.getDefaultToolkit().getSystemClipboard();
//        sysClb.setContents(ss,null);
//        sysClb.setContents();
//
//        StringSelection ss1 = new ClipboardTransferable();
//
//
//        //Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);  //跟上面三行代码效果相同
//    }


    public static Transferable getSystemClipboard(){//获取系统剪切板的文本内容[如果系统剪切板复制的内容是文本]
        Clipboard sysClb=null;
        sysClb = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = sysClb.getContents(null);
        return t;
    }

    public static DataSender getDataSener(Transferable transferable){
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)){
            return new StringDataSender(transferable);
        }
        return null;
    }

    public static DataReceiver getDataReceiver(Scanner scanner){
        if (!scanner.hasNext()){
            return null;
        }
        String type = scanner.nextLine();
        if (type.equals("String")){
            return new StringDataReceiver(scanner);
        }
        return null;
    }

    public static <T> T getObject(String s, Class<T> clazz){
        try {
            byte[] bytes = Hex.decodeHex(s.toCharArray());
            try(ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
            ) {
                Object o = ois.readObject();
                return clazz.cast(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
