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
    drawBatteryHistory(powerDataList);
    drawPowerHistory(powerDataList)
}

function drawBatteryHistory(powerDataList) {
    let labels = powerDataList.map(powerData => luxon.DateTime.fromISO(powerData.dateTime, {zone: "utc"}).setZone('Europe/Berlin'))
    let voltages = powerDataList.map(powerData => powerData.batteryVoltage)
    new Chart(
        document.getElementById('batteryHistory'), {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Battery Voltage',
                    borderColor: 'rgb(255, 99, 132)',
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderWidth: 2,
                    fill: true,
                    data: voltages,
                    pointStyle: 'none'
                }]
            },
            options: chartOptions()
        }
    );
}

function drawPowerHistory(powerDataList) {
    let labels = powerDataList.map(powerData => luxon.DateTime.fromISO(powerData.dateTime, {zone: "utc"}).setZone('Europe/Berlin'))
    let solarPowers = powerDataList.map(powerData => powerData.solarPower)
    new Chart(
        document.getElementById('powerHistory'), {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'PV Power',
                    borderColor: 'rgb(255, 199, 132)',
                    backgroundColor: 'rgba(255, 199, 132, 0.2)',
                    fill: true,
                    borderWidth: 2,
                    data: solarPowers,
                    pointStyle: 'none'
                }]
            },
            options: chartOptions()
        }
    )
}

function drawEnergyHistory(summary) {
    let labels = summary.dailySummary.map(dailySummary => luxon.DateTime.fromObject({
        year: dailySummary.year,
        month: dailySummary.month,
        day: dailySummary.day
    }).setZone('Europe/Berlin'))
    let energies = summary.dailySummary.map(dailySummary => dailySummary.energyThisDay)
    let options = chartOptions()
    options.scales.x.time.unit = "day"
    options.scales.x.time.displayFormats = {day: "dd.MM."}
    console.log(labels)
    console.log(energies)
    new Chart(
        document.getElementById('energyHistory'), {
            type: 'bar',
            data: {
                labels: labels, datasets: [{
                    label: 'Collected Energy',
                    borderColor: 'hsl(205, 20%, 55%)',
                    backgroundColor: 'hsla(205, 20%, 55%, 0.4)',
                    fill: true,
                    borderWidth: 2,
                    data: energies,
                    pointStyle: 'none'
                }]
            },
            options: options
        }
    )
}

function chartOptions() {
    return {
        datasets: {
            line: {
                borderJoinStyle: "bevel",
            }
        },
        elements: {
            point: {
                borderWidth: 0,
                radius: 0,
                hitRadius: 10,
                hoverRadius: 10,
                color: 'rgba(0,0,0,0)',
                backgroundColor: 'rgba(0,0,0,0)'
            }
        },
        plugins: {
            legend: {
                display: false
            }
        },
        animation: false,
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
                },
                ticks: {
                    // display: false
                }
            },
            y: {
                grid: {
                    color: 'hsla(0, 0%, 40%, 0.3)',
                    borderColor: 'hsl(0, 0%, 40%)',
                },
            }
        }
    }
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
loadAndDisplayData("/powerData/summarized", drawEnergyHistory)