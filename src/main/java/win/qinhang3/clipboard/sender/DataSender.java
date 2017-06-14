package win.qinhang3.clipboard.sender;

import java.io.Writer;

/**
 * Created by hang on 2017/6/14.
 */
public interface DataSender {
    String getHash();
    void send(Writer writer);
}
