package handlers;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 13.10.13
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class Msg {
    private Address from;
    private Address to;

    Msg(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    protected Address getTo() {
        return this.to;
    }

    protected Address getFrom() {
        return  this.from;
    }

    abstract void exec(Abonent abonent);
}
