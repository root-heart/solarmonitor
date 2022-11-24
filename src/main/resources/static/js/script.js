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

function drawPowerHistory(powerDataList) {
    // Something is not working with this sorting, it is completely screwed up. Therefore, we rely on the data to be
    // returned sorted by the backend (which simply does it the right way)...
    // powerDataList = powerDataList.sort((a, b) => new Date(a.dateTime).getDate() - new Date(b.dateTime).getDate() );
    drawBatteryHistory(powerDataList);
}

function drawBatteryHistory(powerDataList) {
    Chart.defaults.elements.point = "triangle";

    let batteryVoltage = [];
    let solarVoltage = [];
    let solarPower = [];
    let loadPower = [];
    let overallPower = [];
    let labels = [];
    let generatedEnergy = [];
    let usedEnergy = [];
    let overallEnergy = [];
    powerDataList.forEach(function (powerData) {
        let time = luxon.DateTime.fromISO(powerData.dateTime, {zone: "utc"});
        labels.push(time.setZone('Europe/Berlin'));
        batteryVoltage.push(powerData.batteryVoltage);
        solarVoltage.push(powerData.solarVoltage);
        solarPower.push(powerData.solarPower);
        loadPower.push(-powerData.loadPower);
        overallPower.push(powerData.solarPower - powerData.loadPower);
        generatedEnergy.push(powerData.generatedEnergyToday);
        usedEnergy.push(-powerData.consumedEnergyToday);
        overallEnergy.push(powerData.generatedEnergyToday - powerData.consumedEnergyToday);
    });

    const powerData = {
        labels: labels,
        datasets: [
            {
                label: 'Overall Power',
                borderColor: 'rgb(255, 199, 132)',
                backgroundColor: 'rgba(255, 199, 132, 0.2)',
                borderWidth: 2,
                fill: true,
                data: overallPower,
                yAxisID: 'power'
            },
            {
                label: 'Solar Power',
                borderColor: 'hsl(0, 0%, 35%)',
                backgroundColor: 'hsl(0, 0%, 35%)',
                borderWidth: 1,
                data: solarPower,
                yAxisID: 'power'
            },
            {

                label: 'Load Power',
                borderColor: 'hsl(0, 0%, 35%)',
                backgroundColor: 'hsl(0, 0%, 35%)',
                borderWidth: 1,
                data: loadPower,
                yAxisID: 'power'
            },
            {
                label: 'Overall Energy',
                borderColor: 'hsl(205, 20%, 55%)',
                backgroundColor: 'hsla(205, 20%, 55%, 0.4)',
                borderWidth: 2,
                data: overallEnergy,
                fill: true,
                yAxisID: 'energy'
            },
            {
                label: 'Generated Energy',
                borderColor: 'hsl(0, 0%, 35%)',
                backgroundColor: 'hsl(0, 0%, 35%)',
                borderWidth: 1,
                data: generatedEnergy,
                yAxisID: 'energy'
            },
            {
                label: 'Consumed Energy',
                borderColor: 'hsl(0, 0%, 35%)',
                backgroundColor: 'hsl(0, 0%, 35%)',
                borderWidth: 1,
                data: usedEnergy,
                yAxisID: 'energy'
            },
            {
                label: 'Battery Voltage',
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderWidth: 2,
                data: batteryVoltage,
                fill: true,
                yAxisID: 'voltage'
            }
        ]
    };

    const options = {
        datasets: {
            line: {
                borderJoinStyle: "bevel",
            }
        },
        animation: false,
        maintainAspectRatio: false,
        plugins: {legend: {display: false}},
        scales: {
            x: {
                display: true,
                type: 'time',
                time: {
                    unit: 'hour',
                    displayFormats: {
                        hour: 'HH:mm'
                    }
                },
                grid: {
                    color: 'hsla(0, 0%, 40%, 0.3)',
                    borderColor: 'hsl(0, 0%, 40%)',
                }
            },
            voltage: {
                grid: {
                    color: 'hsla(0, 0%, 40%, 0.3)',
                    borderColor: 'hsl(0, 0%, 40%)',
                },
                title: {
                    display: true,
                    text: 'Battery Voltage'
                },
                stack: 'stack',
                position: 'left'
                // afterFit: function (axes) {
                //     axes.width = 69;
                // },
            },
            energy: {
                grid: {
                    color: 'hsla(0, 0%, 40%, 0.3)',
                    borderColor: 'hsl(0, 0%, 40%)',
                },
                title: {
                    display: true,
                    text: 'Collected (+) / Consumed (-) Energy in kWh'
                },
                stack: 'stack',
                position: 'left',
                offset: true
            },
            power: {
                grid: {
                    color: 'hsla(0, 0%, 40%, 0.3)',
                    borderColor: 'hsl(0, 0%, 40%)',
                },
                title: {
                    display: true,
                    text: 'PV (+) / Load (-) Power in Watts'
                },
                stack: 'stack',
                position: 'left',
                offset: true
            }
        }
    };

    new Chart(
        document.getElementById('chart'),
        {
            type: 'line',
            data: powerData,
            options: options
        }
    );
}

function showPowerDataSummary(powerData) {
    let numberFormat = new Intl.NumberFormat('de-DE', {minimumFractionDigits: 2, maximumFractionDigits: 2});
    document.getElementById("currentPvPower").textContent = numberFormat.format(powerData.solarPower);
    document.getElementById("currentLoadPower").textContent = numberFormat.format(powerData.loadPower);
    document.getElementById("currentOverallPower").textContent = numberFormat.format(powerData.solarPower - powerData.loadPower);

    document.getElementById("generatedEnergy").textContent = numberFormat.format(powerData.generatedEnergyToday);
    document.getElementById("consumedEnergy").textContent = numberFormat.format(powerData.consumedEnergyToday);
    document.getElementById("overallEnergy").textContent = numberFormat.format(powerData.generatedEnergyToday - powerData.consumedEnergyToday);

    document.getElementById("currentBatteryVoltage").textContent = numberFormat.format(powerData.batteryVoltage);
    document.getElementById("maximumBatteryVoltage").textContent = numberFormat.format(powerData.maximumBatteryVoltageToday);
    document.getElementById("minimumBatteryVoltage").textContent = numberFormat.format(powerData.minimumBatteryVoltageToday);
}

function drawSummaryChart(summaryData) {
    new Chart(document.getElementById("dailySummaryChart"), {
        type: 'bar',
        data: {
            labels: summaryData.dailySummary.map(d => luxon.DateTime.utc(d.year, d.month, d.day)),
            datasets: [
                {
                    label: "energy",
                    data: summaryData.dailySummary.map(d => d.energyThisDay),
                    borderColor: 'hsl(205, 20%, 55%)',
                    backgroundColor: 'hsl(205, 20%, 55%)'
                }
            ]
        },
        options: {
            plugins: {legend: {display: false}},
            scales: {
                x: {
                    display: true,
                    type: 'time',
                    time: {
                        unit: 'day',
                        displayFormats: {
                            day: 'dd.MM.'
                        }
                    },
                    grid: {
                        color: 'hsla(0, 0%, 40%, 0.3)',
                        borderColor: 'hsl(0, 0%, 40%)',
                    },
                },
                y: {
                    grid: {
                        color: 'hsla(0, 0%, 40%, 0.3)',
                        borderColor: 'hsl(0, 0%, 40%)',
                    },
                }
            }
        }
    })

    new Chart(document.getElementById("monthlySummaryChart"), {
        type: 'bar',
        data: {
            labels: summaryData.monthlySummary.map(d => luxon.DateTime.utc(d.year, d.month)),
            datasets: [
                {
                    label: "energy",
                    data: summaryData.monthlySummary.map(d => d.energyThisMonth),
                    borderColor: 'hsl(205, 20%, 55%)',
                    backgroundColor: 'hsl(205, 20%, 55%)'
                }
            ]
        },
        options: {
            plugins: {legend: {display: false}},
            scales: {
                x: {
                    display: true,
                    type: 'time',
                    time: {
                        unit: 'month',
                        displayFormats: {
                            day: 'MMMM'
                        }
                    },
                    ticks: {
                        maxRotation: 0,
                    },
                    grid: {
                        color: 'hsla(0, 0%, 40%, 0.3)',
                        borderColor: 'hsl(0, 0%, 40%)',
                    },
                },
                y: {
                    grid: {
                        color: 'hsla(0, 0%, 40%, 0.3)',
                        borderColor: 'hsl(0, 0%, 40%)',
                    },
                }
            }
        }
    })
}

loadAndDisplayData("/powerData/last24Hours", drawPowerHistory);
loadAndDisplayData("/powerData", showPowerDataSummary);

loadAndDisplayData("/powerData/summarized", drawSummaryChart)