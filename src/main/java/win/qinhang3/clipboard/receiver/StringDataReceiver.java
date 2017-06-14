package win.qinhang3.clipboard.receiver;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import win.qinhang3.clipboard.util.ClipboardUtil;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

/**
 * Created by hang on 2017/6/14.
 */
public class StringDataReceiver implements DataReceiver {
    private Scanner scanner;

    public StringDataReceiver(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String receive() {
        Integer length = scanner.nextInt();
        Integer c = 0;
        String str = scanner.nextLine();
        String s = ClipboardUtil.getObject(str, String.class);
        return null;
    }
}
