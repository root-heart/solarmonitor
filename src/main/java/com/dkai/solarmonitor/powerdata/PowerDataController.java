package com.dkai.solarmonitor.powerdata;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/powerData")
@RequiredArgsConstructor
public class PowerDataController {

    private final PowerDataService powerDataService;

    private final ChargeControllerRegisterConverter chargeControllerRegisterConverter;

    @GetMapping
    public PowerData getCurrentPowerData() {
        return powerDataService.getCurrentPowerData();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NonNull Long id) {
        powerDataService.delete(id);
    }

    @PostMapping
    public PowerData postPowerData(@RequestBody ChargeControllerRegistersDto dto) {
        return powerDataService.savePowerData(chargeControllerRegisterConverter.convert(dto));
    }

}
