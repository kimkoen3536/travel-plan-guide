package kke.travel

import org.springframework.stereotype.Repository

import javax.annotation.Resource
import javax.sql.DataSource

/**
 * Created by K.eun on 2014-12-16.
 */
@Repository
class FavoritesDao {
    @Resource
    DataSource dataSource;

    void add(Favorites favorites) {
        def conn = dataSource.getConnection()
        def sql = """
INSERT INTO likes (user_id, plan_id, f_user_id)
VALUES (?, ?, ?)"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, favorites.user_id)
        stat.setInt(2, favorites.favor_plan_id)
        System.out.println("favor_user_id : " + favorites.favor_user_id);
        stat.setInt(3, favorites.favor_user_id)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    void delete(int user_id, int f_plan_id, int f_user_id) {
        def conn = dataSource.getConnection()
        def sql = """
DELETE FROM likes WHERE user_id = ? AND plan_id = ? AND f_user_id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, user_id)
        stat.setInt(2, f_plan_id)
        stat.setInt(3, f_user_id)
        stat.executeUpdate()
        stat.close()
        conn.close()
    }

    Favorites get(int user_id, int f_plan_id, int f_user_id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT user_id, plan_id, f_user_id FROM likes WHERE user_id = ? AND plan_id = ? AND f_user_id = ?"""
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, user_id)
        stat.setInt(2, f_plan_id)
        stat.setInt(3, f_user_id)
        def rs = stat.executeQuery()
        rs.next()
        def favorites = new Favorites()
        favorites.user_id = rs.getInt("user_id")
        favorites.favor_plan_id = rs.getInt("plan_id")
        favorites.favor_user_id = rs.getInt("f_user_id")
        rs.close()
        stat.close()
        conn.close()
        favorites
    }


    List<Plan> list(int user_id) {
        def conn = dataSource.getConnection()
        def sql = """
SELECT plan.id, plan.user_id, plan.title, plan.location, plan.start_date, plan.end_date, plan.is_public, plan.account_name, plan.num_likes
FROM likes like, plans plan WHERE like.user_id = ? and like.f_user_id = plan.user_id and like.plan_id = plan.id """
        def stat = conn.prepareStatement(sql)
        stat.setInt(1, user_id)
        def rs = stat.executeQuery()
        List<Plan> list = []
        while (rs.next()) {
            def plan = new Plan()
            plan.id = rs.getInt("id")
            plan.user_id = rs.getInt("user_id");
            plan.title = rs.getString("title")
            plan.location = rs.getString("location")
            plan.startDate = rs.getDate("start_date")
            plan.endDate = rs.getDate("end_date")
            plan.public_ = rs.getBoolean("is_public")
            plan.accountName = rs.getString("account_name")
            plan.numLikes = rs.getInt("num_likes")
            list.add(plan)
        }
        rs.close()
        stat.close()
        conn.close()
        list
    }
}