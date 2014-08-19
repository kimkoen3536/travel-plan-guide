package kke.travel

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

import javax.annotation.Resource
import java.text.DateFormat
import java.text.SimpleDateFormat

@Controller
@RequestMapping("plan")
class PlanController {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd")

    @Resource
    private PlanDao planDao;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    def add(Model model, @RequestBody String jsonString) {
        def mapper = new ObjectMapper()
        def tree = mapper.readTree(jsonString)
        def plan = new Plan();
        plan.title = tree.get("title").asText("")
        plan.location = tree.get("location").asText("")
        plan.startDate = df.parse(tree.get("start_date").asText("2000-01-01"))
        plan.endDate = df.parse(tree.get("end_date").asText("2000-01-02"))
        plan.public_ = tree.get("is_public").asBoolean()
        planDao.add(plan);
        model.addAttribute("success", true)
        model.addAttribute("code", 200)
        model.addAttribute("message", "OK")
        def view = new MappingJackson2JsonView()
        return view
    }
}
