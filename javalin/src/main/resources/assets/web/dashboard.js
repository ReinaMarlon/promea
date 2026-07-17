    const palette = ["#2563eb", "#14b8a6", "#7c3aed", "#f59e0b", "#ef4444", "#10b981"];
    const charts = [];

    async function loadDashboard() {
        const dashboardName = window.location.pathname.split("/").pop();
        const response = await fetch(`/api/dashboard/${encodeURIComponent(dashboardName)}`);

        if (!response.ok) {
            document.querySelector(".shell").innerHTML = '<div class="empty">Dashboard not found.</div>';
            return;
        }

        const dashboard = await response.json();
        renderDashboard(dashboard);
        scheduleRefresh(dashboard);
    }

    function renderDashboard(dashboard) {
        const metrics = dashboard.metrics ?? [];
        const chartDefinitions = dashboard.charts ?? [];
        const tables = dashboard.tables ?? [];
        const refresh = dashboard.metadata?.refreshIntervalSeconds;

        document.title = `${dashboard.name} - Promea`;
        text("dashboard-title", dashboard.name);
        text("dashboard-description", dashboard.metadata?.description ?? "Embedded analytics dashboard powered by Promea.");
        text("component-count", metrics.length + chartDefinitions.length + tables.length);
        text("refresh-value", refresh ? `${refresh}s` : "Manual");
        text("metrics-count", countLabel(metrics.length, "metric"));
        text("charts-count", countLabel(chartDefinitions.length, "chart"));
        text("tables-count", countLabel(tables.length, "table"));

        renderMetrics(metrics);
        renderCharts(chartDefinitions);
        renderTables(tables);
    }

    function scheduleRefresh(dashboard) {
        const refresh = dashboard.metadata?.refreshIntervalSeconds;

        if (!refresh) {
            return;
        }

        window.setTimeout(loadDashboard, refresh * 1000);
        text("refresh-status", `Auto refresh every ${refresh}s`);
    }

    function renderMetrics(metrics) {
        const container = document.getElementById("metrics");

        if (!metrics.length) {
            container.innerHTML = '<div class="empty">No metrics registered.</div>';
            return;
        }

        container.innerHTML = metrics.map(metric => `
            <article class="card">
                <p class="metric-title">${escapeHtml(metric.name)}</p>
                <div class="metric-value">${escapeHtml(formatValue(metric.value))}</div>
                <p class="metric-description">${escapeHtml(metric.description ?? "")}</p>
            </article>
        `).join("");
    }

    function renderCharts(chartDefinitions) {
        const container = document.getElementById("charts");
        charts.forEach(chart => chart.destroy());
        charts.length = 0;

        if (!chartDefinitions.length) {
            container.innerHTML = '<div class="empty">No charts registered.</div>';
            return;
        }

        container.innerHTML = "";

        chartDefinitions.forEach((chart, index) => {
            const wrapper = document.createElement("article");
            wrapper.className = "panel";
            wrapper.innerHTML = `
                <h3>${escapeHtml(chart.name)}</h3>
                <div class="chart-frame">
                    <canvas id="chart-${index}"></canvas>
                </div>
            `;
            container.appendChild(wrapper);

            const labels = Object.keys(chart.data ?? {});
            const values = Object.values(chart.data ?? {});
            const canvas = document.getElementById(`chart-${index}`);
            charts.push(new Chart(canvas, {
                type: chart.type.toLowerCase(),
                data: {
                    labels,
                    datasets: [{
                        label: chart.name,
                        data: values,
                        borderColor: palette[index % palette.length],
                        backgroundColor: labels.map((_, itemIndex) => palette[itemIndex % palette.length] + "33"),
                        borderWidth: 2,
                        tension: 0.35,
                        fill: chart.type.toLowerCase() === "line"
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: { display: false }
                    },
                    scales: {
                        x: { grid: { display: false } },
                        y: { beginAtZero: true, grid: { color: "rgba(148, 163, 184, 0.18)" } }
                    }
                }
            }));
        });
    }

    function renderTables(tables) {
        const container = document.getElementById("tables");

        if (!tables.length) {
            container.innerHTML = '<div class="empty">No tables registered.</div>';
            return;
        }

        container.innerHTML = tables.map(table => {
            const rows = Array.isArray(table.data) ? table.data : [];

            if (!rows.length) {
                return `<article class="panel"><h3>${escapeHtml(table.name)}</h3><div class="empty">No rows.</div></article>`;
            }

            const columns = Object.keys(rows[0]);
            return `
                <article class="panel">
                    <h3>${escapeHtml(table.name)}</h3>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>${columns.map(column => `<th>${escapeHtml(column)}</th>`).join("")}</tr>
                            </thead>
                            <tbody>
                                ${rows.map(row => `
                                    <tr>${columns.map(column => `<td>${escapeHtml(formatValue(row[column]))}</td>`).join("")}</tr>
                                `).join("")}
                            </tbody>
                        </table>
                    </div>
                </article>
            `;
        }).join("");
    }

    function countLabel(value, noun) {
        return `${value} ${noun}${value === 1 ? "" : "s"}`;
    }

    function formatValue(value) {
        if (value === null || value === undefined) {
            return "";
        }

        if (typeof value === "object") {
            return JSON.stringify(value);
        }

        return String(value);
    }

    function text(id, value) {
        document.getElementById(id).textContent = value;
    }

    function escapeHtml(value) {
        return String(value)
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll('"', "&quot;")
            .replaceAll("'", "&#039;");
    }

    loadDashboard();