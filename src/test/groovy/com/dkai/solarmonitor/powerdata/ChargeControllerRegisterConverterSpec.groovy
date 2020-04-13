package com.dkai.solarmonitor.powerdata

import spock.lang.Specification

class ChargeControllerRegisterConverterSpec extends Specification {
    def 'Test that converting registers 3100 to 3107 works as expected'() {
        given:
        def dto = new ChargeControllerRegistersDto(
                registers3100To3107: "01041012EB00951C3B0000051B02291C3B0000F516"
        )

        when:
        def data = new ChargeControllerRegisterConverter().convert(dto)

        then:
        data.solarVoltage == new BigDecimal("48.43")
        data.solarCurrent == new BigDecimal("1.49")
        data.solarPower == new BigDecimal("72.27")
        data.batteryVoltage == new BigDecimal("13.07")
        data.batteryCurrent == new BigDecimal("5.53")
        data.batteryPower == new BigDecimal("72.27")
    }

    def 'Test that converting registers 310c to 3111 works as expected'() {
        given:
        def dto = new ChargeControllerRegistersDto(
                registers310cTo3111: "01040C04FD002101A5000009790AF82927"
        )

        when:
        def data = new ChargeControllerRegisterConverter().convert(dto)

        then:
        data.loadVoltage == new BigDecimal("12.77")
        data.loadCurrent == new BigDecimal("0.33")
        data.loadPower == new BigDecimal("4.21")
    }

    def 'Test that converting registers 3300 to 330b works as expected'() {
        given:
        def dto = new ChargeControllerRegistersDto(
                registers3300To330b: "010418159E0002052C04C4001A0000039C00000776000013D400006335"
        )

        when:
        def data = new ChargeControllerRegisterConverter().convert(dto)

        then:
        data.maximumInputVoltageToday == new BigDecimal("55.34")
        data.minimumInputVoltageToday == new BigDecimal("0.02")
        data.maximumBatteryVoltageToday == new BigDecimal("13.24")
        data.minimumBatteryVoltageToday == new BigDecimal("12.2")
        data.consumedEnergyToday == new BigDecimal("0.26")
        data.consumedEnergyThisMonth == new BigDecimal("9.24")
        data.consumedEnergyThisYear == new BigDecimal("19.1")
        data.totalConsumedEnergy == new BigDecimal("50.76")
    }

    def 'Test that converting registers 330c to 3313 works as expected'() {
        given:
        def dto = new ChargeControllerRegistersDto(
                registers330cTo3313: "010410000E000003FF000008B800001707000061E3"
        )

        when:
        def data = new ChargeControllerRegisterConverter().convert(dto)

        then:
        data.generatedEnergyToday == new BigDecimal("0.14")
        data.generatedEnergyThisMonth == new BigDecimal("10.23")
        data.generatedEnergyThisYear == new BigDecimal("22.32")
        data.totalGeneratedEnergy == new BigDecimal("58.95")
    }
}
