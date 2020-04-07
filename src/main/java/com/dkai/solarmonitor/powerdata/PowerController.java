package com.dkai.solarmonitor.powerdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/power")
public class PowerController {

    @Autowired
    private PowerDataService powerDataService;

    @GetMapping("/{id}")
    public PowerData get(@PathParam("id") long id) {
        return powerDataService.read(id);
    }

    @PostMapping
    public PowerData postPowerData(@RequestBody PowerDataDto powerData) {
        return powerDataService.savePowerData(powerData);
    }
}
