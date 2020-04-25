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

function gaugeOptions(min, max, title, currentValue, otherValues) {
    let content =
        '<div style="text-align: center; bottom: 0; left: 0; right: 0; position: absolute">' +
        '<span class="title">' + title + '</span><br/>' +
        '<span class="value">' + currentValue + '</span>' +
        '</div>' +
        '<div style="text-align: left; position: absolute; left: 10%; bottom: 2%">';
    otherValues.forEach(otherValue => content += '<span>' + otherValue + '</span><br/>');
    content += '</div>';
    return {
        donut: true,
        donutWidth: 40,
        startAngle: 300,
        showLabel: false,
        total: 360 / 150 * (max - min),
        plugins: [
            Chartist.plugins.fillDonut({
                items: [{
                    content: content,
                    position: 'custom'
                }]
            })
        ]
    }
}

function drawPvGauge(powerData) {
    let data = {
        series: [powerData.solarPower, 600 - powerData.solarPower]
    };
    let otherValues = [
        "Today: " + powerData.generatedEnergyToday + "kWh",
        "This month: "  + powerData.generatedEnergyThisMonth + "kWh"
    ];
    new Chartist.Pie("#solar_gauge",
        data,
        gaugeOptions(0, 600, "PV Power", powerData.solarPower + "W", otherValues));
}

function drawBatteryGauge(powerData) {
    let data = {
        series: [powerData.batteryVoltage - 10, 15 - powerData.batteryVoltage]
    };
    let otherValues = [
        "Min: " + powerData.minimumBatteryVoltageToday + "V",
        "Max: " + powerData.maximumBatteryVoltageToday + "V"
    ];
    new Chartist.Pie("#battery_gauge",
        data,
        gaugeOptions(10, 15, "Battery Voltage", powerData.batteryVoltage + "V", otherValues));
}

function drawLoadGauge(powerData) {
    let data = {
        series: [powerData.loadPower, 300 - powerData.loadPower]
    };
    let otherValues = [
        "Today: " + powerData.consumedEnergyToday + "kWh",
        "This month: "  + powerData.consumedEnergyThisMonth + "kWh"
    ];
    new Chartist.Pie("#load_gauge", data, gaugeOptions(0, 300, "Load Power", powerData.loadPower + "W", otherValues));
}

function drawPowerGauges(powerData) {
    drawPvGauge(powerData);
    drawBatteryGauge(powerData);
    drawLoadGauge(powerData);
}

function drawPowerHistory(powerDataList) {
    // Something is not working with this sorting, it is completely screwed up. Therefore, we rely on the data to be
    // returned sorted by the backend (which simply does it the right way)...
    // powerDataList = powerDataList.sort((a, b) => new Date(a.dateTime).getDate() - new Date(b.dateTime).getDate() );
    drawPvHistory(powerDataList);
    drawBatteryHistory(powerDataList);
    drawLoadHistory(powerDataList);
}

function historyChartOptions() {
    let ticks = [];
    let tick = moment().startOf("hour");
    for (let i = 0; i < 11; i++) {
        ticks.push(tick);
        tick = moment(tick).add(-2, "hour");
    }

    let options = {};
    options.axisX = {
        type: Chartist.FixedScaleAxis,
        ticks: ticks,
        labelInterpolationFnc: function (value) {
            return moment(value).format('HH:mm');
        }
    };
    options.showLine = true;
    options.showPoint = false;
    options.showArea = true;
    options.chartPadding = {
        top: 20,
        right: 18,
        bottom: -5,
        left: 3
    };
    return options;
}

function drawPvHistory(powerDataList) {
    let values = [];
    powerDataList.forEach(function (powerData) {
        values.push({x: moment.utc(powerData.dateTime), y: powerData.solarPower})
    });
    let data = {series: [{name: 'solar power', data: values}]};
    new Chartist.Line('#solarPowerChart', data, historyChartOptions());
}

function drawBatteryHistory(powerDataList) {
    let values = [];
    powerDataList.forEach(function (powerData) {
        values.push({x: moment.utc(powerData.dateTime), y: powerData.batteryVoltage})
    });
    let data = {series: [{name: 'Battery Voltage', data: values}]};
    new Chartist.Line('#batteryVoltageChart', data, historyChartOptions());
}

function drawLoadHistory(powerDataList) {
    let values = [];
    powerDataList.forEach(function (powerData) {
        values.push({x: moment.utc(powerData.dateTime), y: powerData.loadPower})
    });
    let data = {series: [{name: 'Battery Voltage', data: values}]};
    new Chartist.Line('#loadPowerChart', data, historyChartOptions());
}

loadAndDisplayData("/powerData", drawPowerGauges);
loadAndDisplayData("/powerData/last24Hours", drawPowerHistory);

