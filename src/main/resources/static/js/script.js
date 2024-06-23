function loadAndDisplayData(path, displayCallback) {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let data = JSON.parse(this.responseText);
            displayCallback(data);
        }
    }
    request.open("GET", path, true);
    request.send();
}

function drawHistory(powerDataList) {
    let solarPowers = powerDataList.map(p => [luxon.DateTime.fromISO(p.dateTime, {zone: "utc"}).toJSDate().getTime(), p.solarPower])
    console.log(solarPowers)
    let solarVoltages = powerDataList.map(p => [luxon.DateTime.fromISO(p.dateTime, {zone: "utc"}).toJSDate().getTime(), p.solarVoltage])
    let solarCurrents = powerDataList.map(p => [luxon.DateTime.fromISO(p.dateTime, {zone: "utc"}).toJSDate().getTime(), p.solarCurrent])

    let batteryVoltages = powerDataList.map(p => [luxon.DateTime.fromISO(p.dateTime, {zone: "utc"}).toJSDate().getTime(), p.batteryVoltage])

    let collectedEnergy = powerDataList.map(p => [luxon.DateTime.fromISO(p.dateTime, {zone: "utc"}).toJSDate().getTime(), p.generatedEnergyToday])

    Highcharts.setOptions({
        time: {
            timezone: 'Europe/Berlin'
        }
    });

    Highcharts.chart('powerHistory', {
        chart: {
            backgroundColor: "none"
        },
        legend: {
            enabled: false
        },
        title: {
            text: ""
        },
        xAxis: [{
            type: "datetime",
            gridLineWidth: 1,
            gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
            labels: {
                style: {
                    color: "#ccc"
                }
            },
            lineWidth: 0
        }],
        yAxis: [{
            height: "25%",
            title: {
                text: "PV Power",
                style: {
                    color: "#ccc"
                }
            },
            offset: 0,
            gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
            labels: {
                style: {
                    color: "#ccc"
                }
            },
            plotLines: [{
                color: "#ccc",
                width: 1,
                value: 0
            }]
        }, {
            height: "19%",
            top: "27%",
            title: {
                text: "PV Voltage",
                style: {
                    color: "#ccc"
                }
            },
            offset: 0,
            gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
            labels: {
                style: {
                    color: "#ccc"
                }
            },
            plotLines: [{
                color: "#ccc",
                width: 1,
                value: 0
            }]
        }, {
            height: "19%",
            top: "27%",
            title: {
                text: "PV Current",
                style: {
                    color: "#ccc"
                }
            },
            opposite: true,
            offset: 0,
            gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
            labels: {
                style: {
                    color: "#ccc"
                }
            },
            plotLines: [{
                color: "#ccc",
                width: 1,
                value: 0
            }]
        }, {
            height: "25%",
            top: "48%",
            title: {
                text: "Battery Voltage",
                style: {
                    color: "#ccc"
                }
            },
            offset: 0,
            min: 25,
            max: 28,
            gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
            labels: {
                style: {
                    color: "#ccc"
                }
            },
            plotLines: [{
                color: "#ccc",
                width: 1,
                value: 25
            }]
        }, {
            height: "25%",
            top: "75%",
            title: {
                text: "Collected Energy",
                style: {
                    color: "#ccc"
                }
            },
            offset: 0,
            gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
            labels: {
                style: {
                    color: "#ccc"
                }
            },
            plotLines: [{
                color: "#ccc",
                width: 1,
                value: 0
            }]
        }],
        series: [{
            type: "area",
            yAxis: 0,
            data: solarPowers,
            color: "rgb(255, 199, 132)",
            fillColor: "rgba(255, 199, 132, 0.2)",
        }, {
            type: "line",
            yAxis: 1,
            data: solarVoltages,
            lineWidth: 1,
        }, {
            type: "line",
            yAxis: 2,
            data: solarCurrents,
            lineWidth: 1,
        }, {
            type: "area",
            yAxis: 3,
            data: batteryVoltages,
            color: "rgb(255, 99, 132)",
            fillColor: "rgba(255, 99, 132, 0.2)",
        }, {
            type: "area",
            yAxis: 4,
            data: collectedEnergy,
            color: "hsl(205, 20%, 55%)",
            fillColor: "hsla(205, 20%, 55%, 0.2)",
        }]
    })
}

function showPowerDataSummary(powerData) {
    let numberFormat = new Intl.NumberFormat('de-DE', {minimumFractionDigits: 2, maximumFractionDigits: 2});
    document.getElementById("currentPvPower").textContent = numberFormat.format(powerData.solarPower);
    document.getElementById("generatedEnergy").textContent = numberFormat.format(powerData.generatedEnergyToday);
    document.getElementById("currentBatteryVoltage").textContent = numberFormat.format(powerData.batteryVoltage);
    document.getElementById("maximumBatteryVoltage").textContent = numberFormat.format(powerData.maximumBatteryVoltageToday);
    document.getElementById("minimumBatteryVoltage").textContent = numberFormat.format(powerData.minimumBatteryVoltageToday);
}

loadAndDisplayData("/powerData/last24Hours", drawHistory);
loadAndDisplayData("/powerData", showPowerDataSummary);