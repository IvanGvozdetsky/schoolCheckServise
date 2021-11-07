package checkService;

import checkService.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/schools")
public class CheckingController {

    private int counter = 4;
    private List<Map<String, String>> schools = new ArrayList<Map<String, String>>() {{
        add( new HashMap<String, String>() {{ put("id", "1"); put("text", "МБОУ СОШ №1 им. Б. Куликова");}});
        add( new HashMap<String, String>() {{ put("id", "2"); put("text", "МБОУ СОШ №2 им. А. Араканцева");}});
        add( new HashMap<String, String>() {{ put("id", "3"); put("text", "3-я СОШ");}});
    }};

    @GetMapping("")
    public String viewAll(){
        return String.format(schools.toString());
    }

    @GetMapping("{id}")
    public Map<String, String> getSchool(@PathVariable String id){
        return getSchoolById(id);
    }

    private Map<String, String> getSchoolById(String id) {
        return schools.stream()
                .filter(school -> school.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/main")
    public String main() {
        return String.format("Main page");
    }

    @PostMapping("/create")
    public Map<String, String> createSchool(@RequestBody Map<String, String> newSchool){
        newSchool.put("id", String.valueOf(counter++));
        schools.add(newSchool);
        
        return  newSchool;
    }

    @PutMapping("{id}")
    public Map<String, String> updateSchool(@PathVariable String id, @RequestBody Map<String, String> school){
        Map<String, String> existingSchool = getSchoolById(id);

        existingSchool.putAll(school);
        existingSchool.put("id", id);

        return existingSchool;
    }

    @DeleteMapping("{id}")
    public void deleteSchool(@PathVariable String id){
        Map<String, String> schoolToDelete = getSchoolById(id);
        schools.remove(schoolToDelete);
    }
}