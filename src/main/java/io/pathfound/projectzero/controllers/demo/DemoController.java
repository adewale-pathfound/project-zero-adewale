package io.pathfound.projectzero.controllers.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author: adewaleijalana
 * @email: adewaleijalana@gmail.com
 * @date: 2/9/24
 */
@RestController
@RequestMapping("/api")
public class DemoController {

  @GetMapping("/greeting")
  public ResponseEntity<String> greetings(@RequestParam("name") String name) {
    String greeting = "Hello " + name;
    return new ResponseEntity<>(greeting, HttpStatus.OK);
  }
}
