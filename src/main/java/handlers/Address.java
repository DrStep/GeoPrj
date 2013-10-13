package handlers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 13.10.13
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */
public class Address {
    private static AtomicInteger abonentIdCreator = new AtomicInteger();
    final private int abonentId;

    Address() {
        this.abonentId = abonentIdCreator.incrementAndGet();
    }

    public int hashCode() {
        return abonentId;
    }
}
