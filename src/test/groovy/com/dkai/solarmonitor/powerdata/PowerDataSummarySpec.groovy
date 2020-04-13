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
        client.post(path: '/power', body: powerData)

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

    static String powerData =
        """{
                "secondsSince1970": 1586607377,
                "registers3100To3107": "01041012EB00951C3B0000051B02291C3B0000F516",
                "registers310cTo3111": "01040C04FD002101A5000009790AF82927",
                "registers3300To330b": "010418159E0002052C04C4001A0000039C00000776000013D400006335",
                "registers330cTo3313": "010410000E000003FF000008B800001707000061E3"
            }"""
}
