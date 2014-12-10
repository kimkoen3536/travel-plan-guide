package kke.travel

import org.springframework.stereotype.Repository

import javax.annotation.Resource
import javax.sql.DataSource

/**
 * Created by K.eun on 2014-12-03.
 */
@Repository
class TransportDao {

    @Resource
    DataSource dataSource;

    void add(Transport transport) {
        def conn = dataSource.getConnection()
        def sql = """
INSERT INTO transports (plan_id, plan_date, type, departure, destination, duration, memo)
VALUES (?,?, ?, ?, ?, ?, ? )"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1,transport.plan_id)
        stat.setDate(2, new java.sql.Date(transport.plan_date.time))
        stat.setString(3, transport.ttype)
        stat.setString(4, transport.departure)
        stat.setString(5,transport.destination)
        stat.setString(6, transport.duration)
        stat.setString(7, transport.memo)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    List<Transport> list(int tid) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, plan_id,plan_date, type, departure, destination, duration, memo
FROM transports"""
        def stat = conn.prepareStatement(sql)
        def rs = stat.executeQuery()
        List<Transport> list = []
        while (rs.next()) {
            def transport = new Transport()
            transport.tid = rs.getInt("id")
            transport.plan_id = rs.getInt("plan_id")
            transport.plan_date = rs.getDate("plan_date")
            transport.ttype = rs.getString("type")
            transport.departure = rs.getString("departure")
            transport.destination = rs.getString("destination")
            transport.duration = rs.getString("duration")
            transport.memo = rs.getString("memo")
            list.add(transport)
        }
        rs.close()
        stat.close()
        conn.close()
        list
    }

    void delete(int tid) {
        def conn = dataSource.getConnection()
        def sql = """
DELETE FROM transports WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, tid)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    void edit(Transport transport) {
        def conn = dataSource.getConnection()
        def sql = """
UPDATE transports SET plan_id = ? , plan_date = ?, type = ?, departure = ?, destination = ? , duration = ? , memo = ?
WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1,transport.plan_id)
        stat.setDate(2, new java.sql.Date(transport.plan_date.time))
        stat.setString(3, transport.ttype)
        stat.setString(4, transport.departure)
        stat.setString(5,transport.destination)
        stat.setString(6, transport.duration)
        stat.setString(7, transport.memo)
        stat.setInt(8, transport.tid)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    Transport get(int plan_id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT a.id as tid, a.plan_id, a.plan_date as tplan_date, a.type as ttype, a.departure, a.destination, a.duration, b.id as pid,b.plan_date as pplan_date, b.name, b.address, b.map_x, b.map_y, b.type as ptype FROM transports a, places b WHERE b.plan_id = ? and b.plan_id = a.plan_id"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1,plan_id)
        def rs = stat.executeQuery()
        rs.next()
        def transport = new Transport()
        def place = new Place()
        place.plan_date=rs.getDate("pplan_date")
        place.pid=rs.getInt("pid")
        place.name = rs.getString("name")
        place.address = rs.getString("address")
        place.map_x = rs.getInt("map_x")
        place.map_y = rs.getInt("map_y")
        place.ptype = rs.getString("ptype")
        transport.tid = rs.getInt("tid")
        transport.plan_id = rs.getInt("plan_id")
        transport.plan_date = rs.getDate("tplan_date")
        transport.ttype = rs.getString("ttype")
        transport.departure = rs.getString("departure")
        transport.destination = rs.getString("destination")
        transport.duration = rs.getString("duration")
        rs.close()
        stat.close()
        conn.close()
        transport
    }
}