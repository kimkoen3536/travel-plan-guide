package kke.travel

import com.fasterxml.jackson.databind.ObjectMapper
import oracle.jrockit.jfr.events.RequestableEventEnvironment
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

import javax.annotation.Resource
import java.text.DateFormat
import java.text.SimpleDateFormat


/**
 * Created by K.eun on 2014-12-16.
 */

@Controller
@RequestMapping("favorites")
class FavoritesController {
    private Logger logger = LoggerFactory.getLogger(FavoritesController)
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd")

    @Resource
    private FavoritesDao favoritesDao;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    def add(Model model, @RequestBody String jsonString) {
        def mapper = new ObjectMapper()
        def Favorites = mapper.readValue(jsonString,Favorites)
        favoritesDao.add(Favorites);
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        def view = new MappingJackson2JsonView()
        return view
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    def delete(Model model, @RequestBody String jsonString) {

        def mapper = new ObjectMapper()
        def tree = mapper.readTree(jsonString)
        def  user_id = tree.get("user_id").asInt()
        def  f_plan_id = tree.get("favor_plan_id").asInt()
        def  f_user_id = tree.get("favor_user_id").asInt()
        favoritesDao.delete(user_id, f_plan_id, f_user_id)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        return new MappingJackson2JsonView()

    }

    @RequestMapping(value = "get",method = RequestMethod.GET)
    def get(Model model,@RequestParam int user_id, @RequestParam int favor_plan_id, @RequestParam int favor_user_id) {
        def favorites = favoritesDao.get(user_id, favor_plan_id, favor_user_id)
        model.addAttribute("success",true)
        model.addAttribute("code",200)
        model.addAttribute("message","OK")
        model.addAttribute("favorites",favorites)
        return  new MappingJackson2JsonView()
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    def list(Model model, @RequestParam int user_id) {
        def favorites = favoritesDao.list(user_id)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        model.addAttribute("favorites", favorites)
        return new MappingJackson2JsonView()
    }

    @RequestMapping(value = "delete2", method = RequestMethod.POST)
    def delete2(Model model, @RequestBody String jsonString) {

        def mapper = new ObjectMapper()
        def tree = mapper.readTree(jsonString)
        def  plan_id = tree.get("plan_id").asInt()
        favoritesDao.delete2(plan_id)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        return new MappingJackson2JsonView()

    }
}
