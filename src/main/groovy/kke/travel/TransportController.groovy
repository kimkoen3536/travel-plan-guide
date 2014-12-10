package kke.travel

import com.fasterxml.jackson.databind.ObjectMapper
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

@Controller
@RequestMapping("transport")
class TransportController {
    private Logger logger = LoggerFactory.getLogger(TransportController)
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd")


    @Resource
    private TransportDao transportDao;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    def add(Model model, @RequestBody String jsonString) {
        def mapper = new ObjectMapper()
        def transport = mapper.readValue(jsonString,Transport)
        transportDao.add(transport);
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        def view = new MappingJackson2JsonView()
        return view
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    def list(Model model) {
        logger.debug("aaa bbb ccc ddd eee fff ggg")
        def transports = transportDao.list(0)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        model.addAttribute("transports", transports)
        return new MappingJackson2JsonView()
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    def delete(Model model, @RequestBody String jsonString) {

        def mapper = new ObjectMapper()
        def tree = mapper.readTree(jsonString)
        def id = tree.get("id").asInt()
        transportDao.delete(id)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        return new MappingJackson2JsonView()

    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    def edit(Model model, @RequestBody String jsonString) {

        def mapper = new ObjectMapper()
        def plan = mapper.readValue(jsonString, Plan)
        transportDao.edit(plan)
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "ok")
        return new MappingJackson2JsonView()
    }

    @RequestMapping(value = "get",method = RequestMethod.GET)
    def get(Model model,@RequestParam int plan_id) {
        def transport = transportDao.get(plan_id)

        model.addAttribute("success",true)
        model.addAttribute("code",200)
        model.addAttribute("message","OK")
        model.addAttribute("transport",transport)
        return  new MappingJackson2JsonView()

    }

}