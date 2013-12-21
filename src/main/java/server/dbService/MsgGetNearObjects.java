package server.dbService;

import json.JSONArray;
import json.JSONObject;
import server.frontend.FrontendImpl;
import server.frontend.MsgDBResponse;
import server.gameMechanics.GameMechanicsImpl;
import server.gameMechanics.MsgToGameMechanics;
import server.msgsystem.Address;
import utils.GeoPoint;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 20.12.13
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class MsgGetNearObjects extends MsgToDbService {

    private Integer regId;
    private GeoPoint nwPoint;
    private GeoPoint esPoint;

    public MsgGetNearObjects(Address from, Address to, GeoPoint nwPoint, GeoPoint esPoint, Integer regId) {
        super(from, to);
        this.nwPoint = nwPoint;
        this.esPoint = esPoint;
    }

    @Override
    protected void exec(DBServiceImpl dbService) {
        List<String> data = null;
        String meets = FrontendImpl.getJSONByList(dbService.getMeetsInLocation(this.nwPoint, this.esPoint, "meet_id,title"));
        String place = FrontendImpl.getJSONByList(dbService.getPlacesInLocation(this.nwPoint, this.esPoint, "place_id, title"));
        String users = FrontendImpl.getJSONByList(dbService.getUsersInLocation(this.nwPoint, this.esPoint, "user.id, name"));

        String res = FrontendImpl.concatJSON(new String[]{meets, place, users});
        dbService.getMessageSystem().sendMessage(new MsgDBResponse(getTo(), getFrom(), regId, res));
    }

}
