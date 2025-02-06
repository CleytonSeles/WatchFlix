package controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/hello")
    fun sayHello(): String {
        return "Olá, WatchFlix!"
    }
}
