package com.dkai.solarmonitor.powerdata

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PowerDataSummarySpec extends Specification {

    @LocalServerPort
    private int port

    def 'Test that adding power data and fetching the summary for that day returns the just added data'() {
        given: 'A rest client talking to the local server'
        def client = new RESTClient("http://localhost:$port", ContentType.JSON)

        when: 'different data is added for different days'
        client.post(path: '/power',
                body: powerData(1586607377, "01041012EB00951C3B0000051B02291C3B0000F516"))

        and: 'the summary for April 11 2020 is fetched'
        def result = client.get(path: '/summary/day/2020-04-11') as HttpResponseDecorator

        then: 'The result status code is OK'
        result != null
        result.status == 200
        result.responseData != null
        result.responseData.size() == 1
        result.responseData[0].id == 1
        result.responseData[0].solarVoltage == new BigDecimal("48.43")
        result.responseData[0].solarCurrent == new BigDecimal("1.49")
        result.responseData[0].solarPower == new BigDecimal("72.27")
        result.responseData[0].batteryVoltage == new BigDecimal("13.07")
        result.responseData[0].batteryCurrent == new BigDecimal("5.53")
        result.responseData[0].batteryPower == new BigDecimal("72.27")
    }

    static powerData(long secondsSince1970, String currentPowerData) {
        """{
                "secondsSince1970": $secondsSince1970,
                "currentPowerData": "$currentPowerData",
                "historicalPowerData": "" 
            }"""
    }
}
