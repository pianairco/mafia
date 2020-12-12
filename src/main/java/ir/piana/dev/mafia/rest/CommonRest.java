package ir.piana.dev.mafia.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import ir.piana.dev.mafia.model.RoleType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
@RequestMapping("common")
public class CommonRest {
    @GetMapping(value = "role-type/list")
    public ResponseEntity handleResource(HttpServletRequest request,
                                         @RequestHeader("locale") String locale) {
        String[] s = locale.split("_");
        ResourceBundle commonMessages;
        if(s.length == 2)
            commonMessages = ResourceBundle.getBundle("/common", new Locale(s[0], s[1]));
        else
            commonMessages = ResourceBundle.getBundle("/common", new Locale(s[0]));
        return ResponseEntity.ok(commonMessages.getString(RoleType.Detective.getKey()));
    }

}
