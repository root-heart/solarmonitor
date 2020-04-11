package com.dkai.solarmonitor.powerdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/power")
public class PowerController {

    @Autowired
    private PowerDataService powerDataService;

    @GetMapping("/{id}")
    public PowerData get(@PathVariable Long id) {
        return powerDataService.read(id);
    }

    @PostMapping
    public PowerData postPowerData(@RequestBody PowerDataDto powerData) {
        return powerDataService.savePowerData(powerData);
    }

}
