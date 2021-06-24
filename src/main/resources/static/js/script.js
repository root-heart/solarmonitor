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
                data: overallPower
            },
            {
                label: 'Solar Power',
                borderColor: 'hsl(0, 0%, 35%)',
                backgroundColor: 'hsl(0, 0%, 35%)',
                borderWidth: 1,
                data: solarPower
            },
            {

                label: 'Load Power',
                borderColor: 'hsl(0, 0%, 35%)',
                backgroundColor: 'hsl(0, 0%, 35%)',
                borderWidth: 1,
                data: loadPower
            }
        ]
    };

    const energyData = {
        labels: labels,
        datasets: [
            {
                label: 'Overall Energy',
                borderColor: 'hsl(205, 20%, 55%)',
                backgroundColor: 'hsla(205, 20%, 55%, 0.4)',
                borderWidth: 2,
                data: overallEnergy,
                fill: true
            },
            {
                label: 'Generated Energy',
                borderColor: 'hsl(0, 0%, 35%)',
                backgroundColor: 'hsl(0, 0%, 35%)',
                borderWidth: 1,
                data: generatedEnergy
            },
            {
                label: 'Consumed Energy',
                borderColor: 'hsl(0, 0%, 35%)',
                backgroundColor: 'hsl(0, 0%, 35%)',
                borderWidth: 1,
                data: usedEnergy
            }
        ]
    };

    const batteryVoltageData = {
        labels: labels,
        datasets: [
            {
                label: 'Battery Voltage',
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderWidth: 2,
                data: batteryVoltage,
                fill: true
            }
        ]
    };

    const options = {
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
                    color: '#5B5B59',
                    borderColor: '#5B5B59',
                }
            },
            y: {
                grid: {
                    color: '#5B5B59',
                    borderColor: '#5B5B59',
                },
                title: {
                    display: true,
                    text: 'insert text here'
                },
                afterFit: function (axes) {
                    axes.width = 69;
                },
            }
        }
    };


    options.scales.y.title.text = 'Solar Power (+) / Load Power (-) in Watts';
    let powerChart = new Chart(
        document.getElementById('powerChart'),
        {
            type: 'line',
            data: powerData,
            options: options
        }
    );

    options.scales.y.title.text = 'Generated Energy (+) / Consumed Energy (-) in kWh';
    let energyChart = new Chart(
        document.getElementById('energyChart'),
        {
            type: 'line',
            data: energyData,
            options: options
        }
    );

    options.scales.y.title.text = 'Battery Voltage';
    let batteryVoltageChart = new Chart(
        document.getElementById('batteryVoltageChart'),
        {
            type: 'line',
            data: batteryVoltageData,
            options: options
        }
    );
}

function showPowerDataSummary(powerData) {
    document.getElementById("currentPvPower").textContent = powerData.solarPower;
    document.getElementById("currentLoadPower").textContent = powerData.loadPower;
    document.getElementById("currentOverallPower").textContent = powerData.solarPower - powerData.loadPower;

    document.getElementById("generatedEnergy").textContent = powerData.generatedEnergyToday;
    document.getElementById("consumedEnergy").textContent = powerData.consumedEnergyToday;
    document.getElementById("overallEnergy").textContent = powerData.generatedEnergyToday - powerData.consumedEnergyToday;

    document.getElementById("currentBatteryVoltage").textContent = powerData.batteryVoltage;
    document.getElementById("maximumBatteryVoltage").textContent = powerData.maximumBatteryVoltageToday;
    document.getElementById("minimumBatteryVoltage").textContent = powerData.minimumBatteryVoltageToday;
}

loadAndDisplayData("/powerData/last24Hours", drawPowerHistory);
loadAndDisplayData("/powerData", showPowerDataSummary);

