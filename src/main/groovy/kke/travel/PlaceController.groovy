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
 * Created by K.eun on 2014-12-04.
 */
@Controller
@RequestMapping("place")
class PlaceController {
    private Logger logger = LoggerFactory.getLogger(PlaceController)
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd")


    @Resource
    private PlaceDao placeDao;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    def add(Model model, @RequestBody String jsonString) {
        def mapper = new ObjectMapper()
        def place = mapper.readValue(jsonString,Place)
        placeDao.add(place);
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        def view = new MappingJackson2JsonView()
        return view
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    def list(Model model) {
        logger.debug("aaa bbb ccc ddd eee fff ggg")
        def places = placeDao.list(0)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        model.addAttribute("places", places)
        return new MappingJackson2JsonView()
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    def delete(Model model, @RequestBody String jsonString) {

        def mapper = new ObjectMapper()
        def tree = mapper.readTree(jsonString)
        def id = tree.get("id").asInt()
        placeDao.delete(id)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        return new MappingJackson2JsonView()

    }

    @RequestMapping(value = "delete2", method = RequestMethod.POST)
    def delete2(Model model, @RequestBody String jsonString) {

        def mapper = new ObjectMapper()
        def tree = mapper.readTree(jsonString)
        def id = tree.get("id").asInt()
        placeDao.delete2(id)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        return new MappingJackson2JsonView()

    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    def edit(Model model, @RequestBody String jsonString) {

        def mapper = new ObjectMapper()
        def place = mapper.readValue(jsonString, Place)
        placeDao.edit(place)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "ok")
        return new MappingJackson2JsonView()
    }

    @RequestMapping(value = "get",method = RequestMethod.GET)
    def get(Model model,@RequestParam int plan_id, @RequestParam String plan_date) {
        logger.debug("plan_id  : " + plan_id);
        logger.debug("plan_date : " + plan_date);
        println("plan_date : " + plan_date);
        def place = placeDao.get(plan_id, plan_date);
        model.addAttribute("success",true)
        model.addAttribute("code",200)
        model.addAttribute("message","OK")
        model.addAttribute("place",place)
        return  new MappingJackson2JsonView()

    }

    @RequestMapping(value = "get2",method = RequestMethod.GET)
    def get2(Model model,@RequestParam int id) {
        def place = placeDao.get2(id)
        model.addAttribute("success",true)
        model.addAttribute("code",200)
        model.addAttribute("message","OK")
        model.addAttribute("place",place)
        return  new MappingJackson2JsonView()

    }

}


