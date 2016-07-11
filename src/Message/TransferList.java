package Message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class TransferList {

    private List<Message> res = new ArrayList<Message>();

    public TransferList() {}

    @XmlElement
    public List<Message> getRes() {
        return res;
    }

    public void setRes(List<Message> res) {
        this.res = res;
    }
}
