package kke.travel

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

import javax.annotation.Resource

@Controller
class TestController {
    @Resource
    TestDao testDao

    @RequestMapping("/hello")
    def hello(Model model) {
        def result = testDao.test()
        model.addAttribute("result", result)
        return "hello"
    }
}
