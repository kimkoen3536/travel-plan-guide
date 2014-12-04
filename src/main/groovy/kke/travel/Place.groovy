package kke.travel

import java.sql.Blob

/**
 * Created by K.eun on 2014-12-03.
 */
class Place {
    int id;

    int plan_id;

    Date plan_date;

    String name;

    String address;

    String road_address;

    int map_x;

    int map_y;

    String type;

    Byte[] picture;

    String memo;
}
