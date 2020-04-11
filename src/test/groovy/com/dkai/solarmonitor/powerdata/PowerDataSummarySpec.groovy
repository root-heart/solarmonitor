package com.dkai.solarmonitor.powerdata

import groovyx.net.http.RESTClient
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PowerDataSummarySpec extends Specification {

    @LocalServerPort
    private int port

    def ''() {
        expect:
        def client = new RESTClient("http://localhost:$port")
        def result = client.get(path: '/summary/day/2020-04-11')
        result.status == 200
    }
}
