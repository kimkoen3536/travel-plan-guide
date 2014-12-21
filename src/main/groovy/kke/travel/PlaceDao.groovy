package kke.travel

//import javafx.scene.input.DataFormat
import org.springframework.stereotype.Repository

import javax.annotation.Resource
import javax.sql.DataSource
import java.text.SimpleDateFormat

/**
 * Created by K.eun on 2014-12-03.
 */
@Repository
class PlaceDao {

        @Resource
        DataSource dataSource;

        void add(Place place) {
            def conn = dataSource.getConnection()
            def sql = """
INSERT INTO places (plan_id, plan_date, name, address,road_address, map_x, map_y, type, picture, memo)
VALUES (?,? ,?, ?, ?, ?, ?, ?, ?, ?)"""
            def stat = conn.prepareStatement(sql)
            stat.setInt(1,place.plan_id)
            System.out.println(" date1 :::::: " + place.plan_date);
            System.out.println(" date2 :::::: " + place.plan_date.time);
            stat.setDate(2, new java.sql.Date(place.plan_date.time))
            stat.setString(3, place.name)
            stat.setString(4, place.address)
            stat.setString(5,place.road_address)
            stat.setInt(6, place.map_x)
            stat.setInt(7, place.map_y)
            stat.setString(8,place.type)
            stat.setBytes(9,place.picture)
            stat.setString(10,place.memo)
            stat.executeUpdate()
            stat.close()
            conn.close()
        }

        List<Place> list(int id) {
            def conn = dataSource.getConnection()
            def sql = """
SELECT id, plan_id, plan_date, name, address, road_address, map_x, map_y, type, picture,memo
FROM places"""
            def stat = conn.prepareStatement(sql)
            def rs = stat.executeQuery()
            List<Place> list = []
            while (rs.next()) {
                def place = new Place()
                place.id = rs.getInt("id")
                place.plan_id = rs.getInt("plan_id")
                place.plan_date = rs.getDate("plan_date")
                place.name = rs.getString("name")
                place.address = rs.getString("address")
                place.road_address = rs.getString("road_address")
                place.map_x = rs.getInt("map_x")
                place.map_y = rs.getInt("map_y")
                place.type = rs.getString("type")
                place.picture = rs.getBytes("picture")
                place.memo = rs.getString("memo")
                list.add(place)
            }
            rs.close()
            stat.close()
            conn.close()
            list
        }

        void delete(int id) {
            def conn = dataSource.getConnection()
            def sql = """
DELETE FROM places WHERE id = ?"""
            def stat = conn.prepareStatement(sql)
            stat.setInt(1, id)
            stat.executeUpdate()
            stat.close()
            conn.close()
        }

        void edit(Place place) {
            def conn = dataSource.getConnection()
            def sql = """
UPDATE places SET plan_id = ? , plan_date = ? , name = ?, address = ?, road_address = ? , map_x = ? , map_y = ? , type = ? ,
 picture = ? , memo = ?
WHERE id = ?"""
            def stat = conn.prepareStatement(sql)
            stat.setInt(1,place.plan_id)
            stat.setDate(2, new java.sql.Date(place.plan_date.time))
            stat.setString(3, place.name)
            stat.setString(4, place.address)
            stat.setString(5,place.road_address)
            stat.setInt(6, place.map_x)
            stat.setInt(7, place.map_y)
            stat.setString(8,place.type)
            stat.setBytes(9,place.picture)
            stat.setString(10,place.memo)
            stat.setInt(11, place.id)
            stat.executeUpdate()
            stat.close()
            conn.close()
        }

        List<Place> get(int plan_id, String get_plan_date) {
            def conn = dataSource.getConnection()
            def sql = """
SELECT id, plan_id, plan_date, name, address, road_address, map_x, map_y, type FROM places WHERE plan_id = ? and plan_date = ?"""
            def stat = conn.prepareStatement(sql)

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(get_plan_date)

            stat.setInt(1, plan_id)
            stat.setLong(2, date.time)
            def rs = stat.executeQuery()
            List<Place> places = []
            while(rs.next()) {
                def place = new Place()
                place.id = rs.getInt("id")
                place.plan_id = rs.getInt("plan_id")
                place.plan_date = rs.getDate("plan_date")
                place.name = rs.getString("name")
                place.address = rs.getString("address")
                place.road_address = rs.getString("road_address")
                place.map_x = rs.getInt("map_x")
                place.map_y = rs.getInt("map_y")
                place.type = rs.getString("type")
                places.add(place)
            }
            rs.close()
            stat.close()
            conn.close()
            places
        }

    Place get2(int id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, plan_id, plan_date, name, address, road_address, map_x, map_y, type, memo, picture FROM places WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, id)
        def rs = stat.executeQuery()
        rs.next()
            def place = new Place()
            place.id = rs.getInt("id")
            place.plan_id = rs.getInt("plan_id")
            place.plan_date = rs.getDate("plan_date")
            place.name = rs.getString("name")
            place.address = rs.getString("address")
            place.road_address = rs.getString("road_address")
            place.map_x = rs.getInt("map_x")
            place.map_y = rs.getInt("map_y")
            place.type = rs.getString("type")
            place.memo = rs.getString("memo")
            place.picture = rs.getBytes("picture")


        rs.close()
        stat.close()
        conn.close()
        place
    }

    void delete2(int id) {
        def conn = dataSource.getConnection()
        def sql = """
DELETE FROM places WHERE plan_id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, id)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }
}

