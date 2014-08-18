package kke.travel

import org.springframework.stereotype.Repository

import javax.annotation.Resource
import javax.sql.DataSource

@Repository
class TestDao {
    @Resource
    DataSource dataSource

    int test() {
        def conn = dataSource.getConnection()
        def stat = conn.prepareStatement("SELECT 1+1")
        def rs = stat.executeQuery()
        rs.next()
        def result = rs.getInt(1)
        return result
    }
}
