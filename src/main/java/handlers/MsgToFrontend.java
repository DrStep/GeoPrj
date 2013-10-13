package handlers;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 13.10.13
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class MsgToFrontend extends Msg {

    MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof Frontend) {
            exec((Frontend) abonent);
        }
    }

    abstract void exec(Frontend frontend);
}
