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
        stat.setString(3, transport.type)
        stat.setString(4, transport.departure)
        stat.setString(5,transport.destination)
        stat.setString(6, transport.duration)
        stat.setString(7, transport.memo)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    List<Transport> list(int id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, plan_id,plan_date, type, departure, destination, duration, memo
FROM transports"""
        def stat = conn.prepareStatement(sql)
        def rs = stat.executeQuery()
        List<Transport> list = []
        while (rs.next()) {
            def transport = new Transport()
            transport.id = rs.getInt("id")
            transport.plan_id = rs.getInt("plan_id")
            transport.plan_date = rs.getDate("plan_date")
            transport.type = rs.getString("type")
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

    void delete(int id) {
        def conn = dataSource.getConnection()
        def sql = """
DELETE FROM transports WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, id)
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
        stat.setString(3, transport.type)
        stat.setString(4, transport.departure)
        stat.setString(5,transport.destination)
        stat.setString(6, transport.duration)
        stat.setString(7, transport.memo)
        stat.setInt(8, transport.id)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    Transport get(int id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT id, plan_id, plan_date, type, departure, destination, duration, memo FROM transports WHERE id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, id)
        def rs = stat.executeQuery()
        rs.next()
        def transport = new Transport()
        transport.id = rs.getInt("id")
        transport.plan_id = rs.getInt("plan_id")
        transport.plan_date = rs.getDate("plan_date")
        transport.type = rs.getString("type")
        transport.departure = rs.getString("departure")
        transport.destination = rs.getString("destination")
        transport.duration = rs.getString("duration")
        transport.memo =rs.getString("memo")
        rs.close()
        stat.close()
        conn.close()
        transport
    }
}