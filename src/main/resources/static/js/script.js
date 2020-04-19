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

function drawPowerHistory(powerDataList) {
    // Something is not working with this sorting, it is completely screwed up. Therefore, we rely on the data to be
    // returned sorted by the backend (which simply does it the right way)...
    // powerDataList = powerDataList.sort((a, b) => new Date(a.dateTime).getDate() - new Date(b.dateTime).getDate() );
    drawPvHistory(powerDataList);
    drawBatteryHistory(powerDataList);
    drawLoadHistory(powerDataList);
}

function drawPvHistory(powerDataList) {
    let values = [];
    powerDataList.forEach(function (powerData) {
        values.push({x: moment.utc(powerData.dateTime), y: powerData.solarPower})
    });
    new Chart(document.getElementById('solarPowerChart').getContext('2d'),
        {
            type: 'line',
            data: {
                labels: [moment().toDate(), moment().add(-1, 'd').toDate()],
                datasets: [{
                    label: 'PV Power',
                    data: values,
                    fill: "start",
                    borderColor: 'rgba(100, 100, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(0, 250, 120, 0.3)'
                }]
            },
            options: {
                legend: {display: false},
                scales: {
                    xAxes: [{type: 'time', time: {unit: 'hour'}}],
                    yAxes: [{type: 'linear', display: true}]
                }
            }
        });
}

function drawBatteryHistory(powerDataList) {
    let values = [];
    powerDataList.forEach(function (powerData) {
        values.push({x: moment.utc(powerData.dateTime), y: powerData.batteryVoltage})
    });
    new Chart(document.getElementById('batteryVoltageChart').getContext('2d'),
        {
            type: 'line',
            data: {
                labels: [moment().toDate(), moment().add(-1, 'd').toDate()],
                datasets: [{
                    label: 'Battery Voltage',
                    data: values,
                    fill: 'start',
                    borderColor: 'rgba(0, 0, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(0, 250, 120, 0.3)'
                }]
            },
            options: {
                legend: {display: false},
                scales: {
                    xAxes: [{type: 'time', time: {unit: 'hour'}}],
                    yAxes: [{type: 'linear', display: true}]
                }
            }
        });
}

function drawLoadHistory(powerDataList) {
    let values = [];
    powerDataList.forEach(function (powerData) {
        values.push({x: moment.utc(powerData.dateTime), y: powerData.loadPower})
    });
    new Chart(document.getElementById('loadPowerChart').getContext('2d'),
        {
            type: 'line',
            data: {
                labels: [moment().toDate(), moment().add(-1, 'd').toDate()],
                datasets: [{
                    label: 'Load Power',
                    data: values,
                    fill: 'start',
                    borderColor: 'rgba(0, 0, 0, 0)',
                    borderWidth: 0,
                    pointRadius: 1,
                    backgroundColor: 'rgba(0, 250, 120, 0.3)'
                }]
            },
            options: {
                legend: {display: false},
                scales: {
                    xAxes: [{
                        type: 'time',
                        time: {
                            unit: 'hour',
                            displayFormats: {hour: 'H:mm'},
                            // parser: function (utcMoment) {
                            //     return moment(utcMoment).utcOffset('+0100');
                            // }
                        }
                    }],
                    yAxes: [{type: 'linear', display: true}]
                }
            }
        });
}

Chart.defaults.global.defaultFontColor = '#bbb';

loadAndDisplayData("/powerData", drawPowerGauges);
loadAndDisplayData("/powerData/last24Hours", drawPowerHistory);

