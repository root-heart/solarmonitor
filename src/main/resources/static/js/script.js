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

function gaugeOptions(min, max, title, value) {
    return {
        donut: true,
        donutWidth: 40,
        startAngle: 300,
        showLabel: false,
        total: 360 / 150 * (max - min),
        plugins: [
            Chartist.plugins.fillDonut({
                items: [{
                    content: '<span class="title">' + title + '</span><span class="value">' + value + '</span>',
                    position: 'left top'
                }]
            })
        ]
    }
}

function drawPvGauge(powerData) {
    let data = {
        series: [powerData.solarPower, 600 - powerData.solarPower]
    };
    new Chartist.Pie("#solar_gauge", data, gaugeOptions(0, 600, "PV Power", powerData.solarPower + "W"));
}

function drawBatteryGauge(powerData) {
    let data = {
        series: [powerData.batteryVoltage - 10, 15 - powerData.batteryVoltage]
    };
    new Chartist.Pie("#battery_gauge", data, gaugeOptions(10, 15, "Battery Voltage", powerData.batteryVoltage + "V"));
}

function drawLoadGauge(powerData) {
    let data = {
        series: [powerData.loadPower, 300 - powerData.loadPower]
    };
    new Chartist.Pie("#load_gauge", data, gaugeOptions(0, 300, "Load Power", powerData.loadPower + "W"));
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

