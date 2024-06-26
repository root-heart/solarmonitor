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

function drawOverviewChart(powerDataList) {
    let solarPowers = powerDataList.map(p => [luxon.DateTime.fromISO(p.dateTime, {zone: "utc"}).toJSDate().getTime(), p.solarPower])
    let batteryVoltages = powerDataList.map(p => [luxon.DateTime.fromISO(p.dateTime, {zone: "utc"}).toJSDate().getTime(), p.batteryVoltage])
    let collectedEnergy = powerDataList.map(p => [luxon.DateTime.fromISO(p.dateTime, {zone: "utc"}).toJSDate().getTime(), p.generatedEnergyToday])
    let maxEnergy = Math.max(...collectedEnergy.map(e => e[1]))

    let energyYAxis = {
        height: "32%",
        top: "68%",
        title: {
            text: "Collected Energy",
            style: {color: "#ccc"}
        },
        offset: 0,
        gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
        labels: {
            style: {color: "#ccc"}
        },
        tickInterval: maxEnergy < 2 ? 0.25 : 0.5,
        plotLines: [{
            color: "#ccc",
            width: 1,
            value: 0
        }]
    };
    let baterryStatusYAxis = {
        height: "32%",
        top: "34%",
        title: {
            text: "Battery Voltage",
            style: {color: "#ccc"}
        },
        offset: 0,
        min: 25,
        max: 28,
        gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
        labels: {
            style: {color: "#ccc"}
        },
        plotLines: [{
            color: "#ccc",
            width: 1,
            value: 25
        }]
    };
    let powerYAxis = {
        height: "32%",
        title: {
            text: "PV Power",
            style: {color: "#ccc"}
        },
        offset: 0,
        gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
        labels: {
            style: {color: "#ccc"}
        },
        plotLines: [{
            color: "#ccc",
            width: 1,
            value: 0
        }]
    };
    let powerSeries = {
        type: "area",
        yAxis: 0,
        name: "PV Power",
        data: solarPowers,
        color: "hsl(30, 100%, 70%)",
        fillColor: "hsla(30, 100%, 70%, 0.2)",
        animation: false,
    };
    let batteryStatusSeries = {
        type: "area",
        yAxis: 1,
        name: "Battery Voltage",
        data: batteryVoltages,
        color: "hsl(350, 100%, 70%)",
        fillColor: "hsla(350, 100%, 70%,0.2)",
        animation: false,
    };
    let energySeries = {
        type: "area",
        yAxis: 2,
        name: "Collected Energy",
        data: collectedEnergy,
        color: "hsl(205, 100%, 70%)",
        fillColor: "hsla(205, 100%, 70%, 0.2)",
        animation: false,
    };

    Highcharts.chart('overviewChart', {
        chart: {backgroundColor: "none"},
        legend: {enabled: false},
        title: {text: ""},
        xAxis: [{
            type: "datetime",
            gridLineWidth: 1,
            gridLineColor: 'hsla(0, 0%, 40%, 0.3)',
            labels: {style: {color: "#ccc"}},
            tickLength: 0,
            lineWidth: 0
        }],
        yAxis: [powerYAxis, baterryStatusYAxis, energyYAxis],
        series: [powerSeries, batteryStatusSeries, energySeries]
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

loadAndDisplayData("/powerData/last24Hours", drawOverviewChart);
loadAndDisplayData("/powerData", showPowerDataSummary);

Highcharts.setOptions({
    time: {
        timezone: 'Europe/Berlin'
    }
});

