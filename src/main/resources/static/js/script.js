function getData(path, successCallback) {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let data = JSON.parse(this.responseText);
            successCallback(data);
        }
    }
    request.open("GET", path, true);
    request.send();
}

function drawSummaryCharts(summaryData) {

    let minPvPower = []
    let avgPvPower = []
    let maxPvPower = []
    let minLoadPower = [];
    let avgLoadPower = [];
    let maxLoadPower = [];
    let minBatteryVolt = [];
    let avgBatteryVolt = [];
    let maxBatteryVolt = [];

    summaryData.forEach(function (element) {
        minPvPower.push({x: element.end, y: element.minSolarPower});
        avgPvPower.push({x: element.end, y: element.avgSolarPower});
        maxPvPower.push({x: element.end, y: element.maxSolarPower});
        minLoadPower.push({x: element.end, y: -element.minLoadPower});
        avgLoadPower.push({x: element.end, y: -element.avgLoadPower});
        maxLoadPower.push({x: element.end, y: -element.maxLoadPower});
        minBatteryVolt.push({x: element.end, y: element.minBatteryVoltage});
        avgBatteryVolt.push({x: element.end, y: element.avgBatteryVoltage});
        maxBatteryVolt.push({x: element.end, y: element.maxBatteryVoltage});
    });

    console.log(summaryData);

    let ctx = document.getElementById('powerChart').getContext('2d');
    let powerChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [moment().toDate(), moment().add(-1, 'd').toDate()],
            datasets: [
                {
                    label: 'Min PV Power',
                    data: minPvPower,
                    fill: false,
                    borderColor: 'rgba(0, 0, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(250, 120, 0, 0.3)',
                    pointHoverRadius: 15
                },
                {
                    label: 'Avg PV Power',
                    data: avgPvPower,
                    fill: false,
                    borderColor: 'rgba(250, 120, 0, 1)',
                    borderWidth: 3,
                    pointRadius: 1,
                    pointHoverRadius: 15
                },
                {
                    label: 'Max PV Power',
                    data: maxPvPower,
                    fill: 0,
                    borderColor: 'rgba(0, 0, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(250, 120, 0, 0.3)',
                    pointHoverRadius: 15
                },
                {
                    label: 'Min Load Power',
                    data: minLoadPower,
                    fill: false,
                    borderColor: 'rgba(0, 0, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(120, 0, 250, 0.3)',
                },
                {
                    label: 'Avg Load Power',
                    data: avgLoadPower,
                    fill: false,
                    borderColor: 'rgba(120, 0, 250, 1)',
                    borderWidth: 3,
                    pointRadius: 1,
                },
                {
                    label: 'Max Load Power',
                    data: maxLoadPower,
                    fill: 3,
                    borderColor: 'rgba(0, 0, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(120, 0, 250, 0.3)',
                },
            ]
        },
        options: {
            scales: {
                xAxes: [{
                    type: 'time',
                    time: {
                        unit: 'hour'
                    }
                }],
                yAxes: [{
                    type: 'linear',
                    display: true,
                    position: 'left',
                    id: 'y-axis-1',
                    ticks: {beginAtZero: true},
                    scaleLabel: {
                        display: true,
                        labelString: 'Power in Watts'
                    }
                }],
            }
        }
    });


    ctx = document.getElementById('batteryChart').getContext('2d');
    let batteryChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [moment().toDate(), moment().add(-1, 'd').toDate()],
            datasets: [
                {
                    label: 'Min Battery Voltage',
                    data: minBatteryVolt,
                    fill: false,
                    borderColor: 'rgba(0, 0, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(0, 250, 120, 0.3)',
                    yAxisID: 'y-axis-1'
                },
                {
                    label: 'Avg Battery Voltage',
                    data: avgBatteryVolt,
                    fill: false,
                    borderColor: 'rgba(0, 250, 120, 1)',
                    borderWidth: 3,
                    pointRadius: 1,
                    yAxisID: 'y-axis-1'
                },
                {
                    label: 'Max Battery Voltage',
                    data: maxBatteryVolt,
                    fill: 0,
                    borderColor: 'rgba(0, 0, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(0, 250, 120, 0.3)',
                    yAxisID: 'y-axis-1'
                },
            ]
        },
        options: {
            scales: {
                xAxes: [{
                    type: 'time',
                    time: {
                        unit: 'hour'
                    }
                }],
                yAxes: [{
                    type: 'linear',
                    display: true,
                    position: 'left',
                    id: 'y-axis-1',
                    ticks: {suggestedMin: 11.5},
                    scaleLabel: {
                        display: true,
                        labelString: 'Battery Voltage'
                    }
                }],

            }
        }
    });
}

function drawPowerGauges(powerData) {
    let solarGauge = new JustGage({
        id: "solar_gauge",
        value: powerData.solarPower,
        gaugeColor: "#555",
        valueFontColor: "#bbb",
        levelColors: ["#0d0"],
        decimals: 2,
        symbol: "W",
        hideMinMax: true,
        min: 0,
        max: 700,
        label: "Solar Power"
    });

    let batteryGauge = new JustGage({
        id: "battery_gauge",
        value: powerData.batteryVoltage,
        gaugeColor: "#555",
        valueFontColor: "#bbb",
        decimals: 2,
        symbol: "V",
        hideMinMax: true,
        min: 10,
        max: 16,
        customSectors: {
            ranges: [{
                color: "#d00",
                lo: 10,
                hi: 11
            }, {
                color: "#dd0",
                lo: 11,
                hi: 11.5
            }, {
                color: "#0d0",
                lo: 11.5,
                hi: 14.5
            }, {
                color: "#dd0",
                lo: 14.5,
                hi: 15
            }, {
                color: "#d00",
                lo: 15,
                hi: 16
            }]
        },
        label: "Battery Voltage"
    });

    let loadGauge = new JustGage({
        id: "load_gauge",
        value: powerData.loadPower,
        gaugeColor: "#555",
        valueFontColor: "#bbb",
        decimals: 2,
        symbol: "W",
        hideMinMax: true,
        min: 0,
        max: 400,
        customSectors: {
            ranges: [{
                color: "#0d0",
                lo: 0,
                hi: 200
            }, {
                color: "#dd0",
                lo: 200,
                hi: 300
            }, {
                color: "#d00",
                lo: 300,
                hi: 400
            }]
        },
        label: "Load Power"
    });
}

Chart.defaults.global.defaultFontColor = '#bbb';

getData("/summary/day/2020-04-13", drawSummaryCharts);
getData("/powerData", drawPowerGauges);

