
package web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import util.DatabaseConnection;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.math.BigDecimal;
import java.util.*;

public class WebServer {

    private HttpServer server;

    public static void main(String[] args) throws Exception {
        WebServer app = new WebServer();
        app.start();
    }

    public void start() throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
server = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0);
        String[] pages = {
            "/", "/farmers.html", "/land.html",
            "/crop.html", "/fertilizer.html",
            "/market.html", "/reports.html"
        };

        for (String page : pages) {
            server.createContext(page, this::page);
        }

        String[] routes = {
            "/getFarmers", "/addFarmer", "/updateFarmer", "/deleteFarmer",
            "/getPreviousCrops", "/addPreviousCrop", "/deletePreviousCrop",
            "/getLands", "/addLand", "/updateLand", "/deleteLand",
            "/getSoilTests", "/addSoilTest", "/updateSoilTest", "/deleteSoilTest",
            "/getCrops", "/addCrop", "/updateCrop", "/deleteCrop",
            "/recommendCrops", "/getRecommendations",
            "/getFertilizers", "/addFertilizer", "/updateFertilizer", "/deleteFertilizer",
            "/getCultivation", "/addCultivation", "/updateCultivation", "/deleteCultivation",
            "/getMarkets", "/addMarket", "/updateMarket", "/deleteMarket",
            "/getPrices", "/addPrice", "/updatePrice", "/deletePrice",
            "/getReports"
        };

        for (String route : routes) {
            server.createContext(route, this::api);
        }

        server.start();

        System.out.println("==================================");
        System.out.println(" Farmer Advisory Web Server");
        System.out.println(" http://localhost:8080");
        System.out.println("==================================");
    }

    // ---------------- PAGE ROUTING ----------------

    private void page(HttpExchange e) throws IOException {
        String path = e.getRequestURI().getPath();

        if (path.equals("/crop.html")) {
            send(e, 200, cropPage(), "text/html");
            return;
        }
        if (path.equals("/fertilizer.html")) {
            send(e, 200, fertilizerPage(), "text/html");
            return;
        }
        if (path.equals("/market.html")) {
            send(e, 200, marketPage(), "text/html");
            return;
        }
        if (path.equals("/reports.html")) {
            send(e, 200, reportsPage(), "text/html");
            return;
        }

        String file = path.equals("/") ? "index.html" :
            path.substring(1);

        Path p = Path.of("src", "web", file);

        if (!Files.exists(p)) {
            send(e, 404, "Page not found: " + file, "text/plain");
            return;
        }

        send(e, 200, Files.readString(p), "text/html");
    }

    // ---------------- API ROUTING ----------------

    private void api(HttpExchange e) throws IOException {
        String path = e.getRequestURI().getPath();

        try {
            switch (path) {
                case "/getFarmers" -> query(e,
                    "SELECT farmer_id AS farmerId,name,age,gender,phone,village,district,land_ownership AS landOwnership FROM farmers ORDER BY farmer_id");

                case "/addFarmer" -> execute(e,
                    "INSERT INTO farmers(name,age,gender,phone,village,district,land_ownership) VALUES(?,?,?,?,?,?,?)",
                    "name","age","gender","phone","village","district","landOwnership");

                case "/updateFarmer" -> execute(e,
                    "UPDATE farmers SET name=?,age=?,gender=?,phone=?,village=?,district=?,land_ownership=? WHERE farmer_id=?",
                    "name","age","gender","phone","village","district","landOwnership","farmerId");

                case "/deleteFarmer" -> deleteByQuery(e, "farmers", "farmer_id");

                case "/getPreviousCrops" -> query(e,
                    "SELECT p.record_id AS recordId,p.farmer_id AS farmerId,f.name AS farmerName,p.land_id AS landId,p.crop_id AS cropId,c.crop_name AS cropName,p.season,p.year,p.yield FROM previous_crops p LEFT JOIN farmers f ON p.farmer_id=f.farmer_id LEFT JOIN crops c ON p.crop_id=c.crop_id ORDER BY p.record_id DESC");

                case "/addPreviousCrop" -> execute(e,
                    "INSERT INTO previous_crops(farmer_id,land_id,crop_id,season,year,yield) VALUES(?,?,?,?,?,?)",
                    "farmerId","landId","cropId","season","year","yield");

                case "/deletePreviousCrop" -> deleteByQuery(e, "previous_crops", "record_id");

                case "/getLands" -> query(e,
                    "SELECT land_id AS landId,farmer_id AS farmerId,area,soil_type AS soilType,irrigation,water_availability AS waterAvailability,location,temperature FROM lands ORDER BY land_id");

                case "/addLand" -> execute(e,
                    "INSERT INTO lands(farmer_id,area,soil_type,irrigation,water_availability,location,temperature) VALUES(?,?,?,?,?,?,?)",
                    "farmerId","area","soilType","irrigation","waterAvailability","location","temperature");

                case "/updateLand" -> execute(e,
                    "UPDATE lands SET farmer_id=?,area=?,soil_type=?,irrigation=?,water_availability=?,location=?,temperature=? WHERE land_id=?",
                    "farmerId","area","soilType","irrigation","waterAvailability","location","temperature","landId");

                case "/deleteLand" -> deleteByQuery(e, "lands", "land_id");

                case "/getSoilTests" -> query(e,
                    "SELECT test_id AS testId,land_id AS landId,nitrogen,phosphorus,potassium,ph,test_date AS testDate FROM soil_tests ORDER BY test_id DESC");

                case "/addSoilTest" -> execute(e,
                    "INSERT INTO soil_tests(land_id,nitrogen,phosphorus,potassium,ph,test_date) VALUES(?,?,?,?,?,?)",
                    "landId","nitrogen","phosphorus","potassium","ph","testDate");

                case "/updateSoilTest" -> execute(e,
                    "UPDATE soil_tests SET land_id=?,nitrogen=?,phosphorus=?,potassium=?,ph=?,test_date=? WHERE test_id=?",
                    "landId","nitrogen","phosphorus","potassium","ph","testDate","testId");

                case "/deleteSoilTest" -> deleteByQuery(e, "soil_tests", "test_id");

                // CROPS AND ADVISORY
                case "/getCrops" -> query(e,
                    "SELECT crop_id AS cropId,crop_name AS cropName,season,duration_days AS durationDays,expected_yield AS expectedYield,min_temperature AS minTemperature,max_temperature AS maxTemperature FROM crops ORDER BY crop_id");

                case "/addCrop" -> execute(e,
                    "INSERT INTO crops(crop_name,season,duration_days,expected_yield,min_temperature,max_temperature) VALUES(?,?,?,?,?,?)",
                    "cropName","season","durationDays","expectedYield","minTemperature","maxTemperature");

                case "/updateCrop" -> execute(e,
                    "UPDATE crops SET crop_name=?,season=?,duration_days=?,expected_yield=?,min_temperature=?,max_temperature=? WHERE crop_id=?",
                    "cropName","season","durationDays","expectedYield","minTemperature","maxTemperature","cropId");

                case "/deleteCrop" -> deleteByQuery(e, "crops", "crop_id");

                case "/recommendCrops" -> recommend(e);

                case "/getRecommendations" -> query(e,
                    "SELECT r.recommendation_id AS recommendationId,r.farmer_id AS farmerId,r.land_id AS landId,c.crop_name AS cropName,r.suitability,r.reason,r.recommendation_date AS recommendationDate FROM crop_recommendations r JOIN crops c ON r.crop_id=c.crop_id ORDER BY r.recommendation_id DESC");

                // FERTILIZER
                case "/getFertilizers" -> query(e,
                    "SELECT f.fertilizer_id AS fertilizerId,f.fertilizer_name AS fertilizerName,f.quantity,f.application_stage AS applicationStage,f.crop_id AS cropId,c.crop_name AS cropName FROM fertilizers f LEFT JOIN crops c ON f.crop_id=c.crop_id ORDER BY f.fertilizer_id DESC");

                case "/addFertilizer" -> execute(e,
                    "INSERT INTO fertilizers(fertilizer_name,quantity,application_stage,crop_id) VALUES(?,?,?,?)",
                    "fertilizerName","quantity","applicationStage","cropId");

                case "/updateFertilizer" -> execute(e,
                    "UPDATE fertilizers SET fertilizer_name=?,quantity=?,application_stage=?,crop_id=? WHERE fertilizer_id=?",
                    "fertilizerName","quantity","applicationStage","cropId","fertilizerId");

                case "/deleteFertilizer" -> deleteByQuery(e, "fertilizers", "fertilizer_id");

                // CULTIVATION
                case "/getCultivation" -> query(e,
                    "SELECT v.cultivation_id AS cultivationId,v.crop_id AS cropId,c.crop_name AS cropName,v.cultivation_method AS cultivationMethod,v.pest_control AS pestControl,v.harvest_duration AS harvestDuration FROM cultivation v LEFT JOIN crops c ON v.crop_id=c.crop_id ORDER BY v.cultivation_id DESC");

                case "/addCultivation" -> execute(e,
                    "INSERT INTO cultivation(crop_id,cultivation_method,pest_control,harvest_duration) VALUES(?,?,?,?)",
                    "cropId","cultivationMethod","pestControl","harvestDuration");

                case "/updateCultivation" -> execute(e,
                    "UPDATE cultivation SET crop_id=?,cultivation_method=?,pest_control=?,harvest_duration=? WHERE cultivation_id=?",
                    "cropId","cultivationMethod","pestControl","harvestDuration","cultivationId");

                case "/deleteCultivation" -> deleteByQuery(e, "cultivation", "cultivation_id");

                // MARKETS
                case "/getMarkets" -> query(e,
                    "SELECT market_id AS marketId,market_name AS marketName,location,contact FROM markets ORDER BY market_id");

                case "/addMarket" -> execute(e,
                    "INSERT INTO markets(market_name,location,contact) VALUES(?,?,?)",
                    "marketName","location","contact");

                case "/updateMarket" -> execute(e,
                    "UPDATE markets SET market_name=?,location=?,contact=? WHERE market_id=?",
                    "marketName","location","contact","marketId");

                case "/deleteMarket" -> deleteByQuery(e, "markets", "market_id");

                // PRICES
                case "/getPrices" -> query(e,
                    "SELECT p.price_id AS priceId,p.market_id AS marketId,m.market_name AS marketName,m.location,c.crop_name AS cropName,p.crop_id AS cropId,p.price_per_unit AS pricePerUnit,p.unit,p.date_updated AS dateUpdated FROM crop_prices p JOIN markets m ON p.market_id=m.market_id JOIN crops c ON p.crop_id=c.crop_id ORDER BY p.date_updated DESC");

                case "/addPrice" -> execute(e,
                    "INSERT INTO crop_prices(market_id,crop_id,price_per_unit,unit,date_updated) VALUES(?,?,?,?,?)",
                    "marketId","cropId","pricePerUnit","unit","dateUpdated");

                case "/updatePrice" -> execute(e,
                    "UPDATE crop_prices SET market_id=?,crop_id=?,price_per_unit=?,unit=?,date_updated=? WHERE price_id=?",
                    "marketId","cropId","pricePerUnit","unit","dateUpdated","priceId");

                case "/deletePrice" -> deleteByQuery(e, "crop_prices", "price_id");

                case "/getReports" -> reports(e);

                default -> send(e, 404, "Unknown endpoint", "text/plain");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            send(e, 500, "Error: " + ex.getMessage(), "text/plain");
        }
    }

    // ---------------- DATABASE HELPERS ----------------

    private Map<String,String> form(HttpExchange e) throws IOException {
        String body = new String(
            e.getRequestBody().readAllBytes(),
            StandardCharsets.UTF_8
        );

        Map<String,String> map = new LinkedHashMap<>();

        if (!body.isBlank()) {
            for (String pair : body.split("&")) {
                String[] p = pair.split("=", 2);
                map.put(
                    URLDecoder.decode(p[0], StandardCharsets.UTF_8),
                    p.length > 1
                        ? URLDecoder.decode(p[1], StandardCharsets.UTF_8)
                        : ""
                );
            }
        }
        return map;
    }

    private void query(HttpExchange e, String sql) throws Exception {
        if (!e.getRequestMethod().equalsIgnoreCase("GET")) {
            send(e, 405, "GET required", "text/plain");
            return;
        }

        List<Map<String,Object>> rows = new ArrayList<>();

        try (
            Connection c = DatabaseConnection.getConnection();
            PreparedStatement p = c.prepareStatement(sql);
            ResultSet r = p.executeQuery()
        ) {
            ResultSetMetaData md = r.getMetaData();

            while (r.next()) {
                Map<String,Object> row = new LinkedHashMap<>();

                for (int i = 1; i <= md.getColumnCount(); i++) {
                    Object value = r.getObject(i);
                    if (value instanceof java.sql.Date d) value = d.toString();                    row.put(md.getColumnLabel(i), value);
                }
                rows.add(row);
            }
        }

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < rows.size(); i++) {
            if (i > 0) json.append(",");
            json.append(jsonObject(rows.get(i)));
        }
        json.append("]");

        send(e, 200, json.toString(), "application/json");
    }

    private void execute(
        HttpExchange e, String sql, String... fields
    ) throws Exception {
        if (!e.getRequestMethod().equalsIgnoreCase("POST")) {
            send(e, 405, "POST required", "text/plain");
            return;
        }

        Map<String,String> data = form(e);

        try (
            Connection c = DatabaseConnection.getConnection();
            PreparedStatement p = c.prepareStatement(sql)
        ) {
            for (int i = 0; i < fields.length; i++) {
                String key = fields[i];
                String value = data.get(key);

                if (key.equals("age") || key.equals("durationDays") ||
                    key.equals("year") || key.equals("cropId") ||
                    key.equals("farmerId") || key.equals("landId") ||
                    key.equals("marketId") || key.equals("fertilizerId") ||
                    key.equals("cultivationId") || key.equals("priceId") ||
                    key.equals("testId") || key.equals("harvestDuration")) {

                    if (value == null || value.isBlank()) {
                        p.setNull(i + 1, Types.INTEGER);
                    } else {
                        p.setInt(i + 1, Integer.parseInt(value));
                    }
                } else if (key.equals("area") || key.equals("yield") ||
                    key.equals("expectedYield") || key.equals("minTemperature") ||
                    key.equals("maxTemperature") || key.equals("temperature") ||
                    key.equals("nitrogen") || key.equals("phosphorus") ||
                    key.equals("potassium") || key.equals("ph") ||
                    key.equals("pricePerUnit")) {

                    if (value == null || value.isBlank()) {
                        p.setNull(i + 1, Types.DECIMAL);
                    } else {
                        p.setBigDecimal(i + 1, new BigDecimal(value));
                    }
                } else if (key.equals("testDate") || key.equals("dateUpdated")) {
                    if (value == null || value.isBlank()) {
                        p.setNull(i + 1, Types.DATE);
                    } else {
                        p.setDate(i + 1, java.sql.Date.valueOf(value));
                    }
                } else {
                    p.setString(i + 1, value);
                }
            }

            int changed = p.executeUpdate();

            send(e, 200,
                changed > 0 ? "Saved successfully!" : "No matching record was changed.",
                "text/plain");
        }
    }

    private void deleteByQuery(
        HttpExchange e, String table, String idColumn
    ) throws Exception {
        if (!e.getRequestMethod().equalsIgnoreCase("POST")) {
            send(e, 405, "POST required", "text/plain");
            return;
        }

        Map<String,String> q = queryParams(e.getRequestURI().getRawQuery());
        String id = q.get("id");

        if (id == null || id.isBlank()) {
            send(e, 400, "Missing id", "text/plain");
            return;
        }

        // Table and column names are constants defined by the server.
        String sql = "DELETE FROM " + table + " WHERE " + idColumn + "=?";

        try (
            Connection c = DatabaseConnection.getConnection();
            PreparedStatement p = c.prepareStatement(sql)
        ) {
            p.setInt(1, Integer.parseInt(id));
            int changed = p.executeUpdate();

            send(e, changed > 0 ? 200 : 404,
                changed > 0 ? "Deleted successfully!" : "Record not found.",
                "text/plain");
        }
    }

    private Map<String,String> queryParams(String raw) {
        Map<String,String> result = new HashMap<>();

        if (raw != null) {
            for (String part : raw.split("&")) {
                String[] p = part.split("=", 2);
                result.put(
                    URLDecoder.decode(p[0], StandardCharsets.UTF_8),
                    p.length > 1
                        ? URLDecoder.decode(p[1], StandardCharsets.UTF_8)
                        : ""
                );
            }
        }
        return result;
    }

    private String jsonObject(Map<String,Object> row) {
        StringBuilder b = new StringBuilder("{");
        boolean first = true;

        for (Map.Entry<String,Object> entry : row.entrySet()) {
            if (!first) b.append(",");
            first = false;

            b.append(json(entry.getKey())).append(":");
            Object value = entry.getValue();

            if (value == null) b.append("null");
            else if (value instanceof Number || value instanceof Boolean) {
                b.append(value);
            } else {
                b.append(json(value.toString()));
            }
        }
        return b.append("}").toString();
    }

    private String json(String s) {
        if (s == null) return "null";

        StringBuilder b = new StringBuilder("\"");
        for (char ch : s.toCharArray()) {
            switch (ch) {
                case '"' -> b.append("\\\"");
                case '\\' -> b.append("\\\\");
                case '\n' -> b.append("\\n");
                case '\r' -> b.append("\\r");
                case '\t' -> b.append("\\t");
                default -> {
                    if (ch < 32) b.append(String.format("\\u%04x", (int) ch));
                    else b.append(ch);
                }
            }
        }
        return b.append("\"").toString();
    }

    private void send(
        HttpExchange e, int status, String text, String contentType
    ) throws IOException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        e.getResponseHeaders().set(
            "Content-Type", contentType + "; charset=UTF-8"
        );
        e.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = e.getResponseBody()) {
            out.write(bytes);
        }
    }

    // ---------------- CROP ADVISORY ----------------

    private void recommend(HttpExchange e) throws Exception {
        if (!e.getRequestMethod().equalsIgnoreCase("POST")) {
            send(e, 405, "POST required", "text/plain");
            return;
        }

        Map<String,String> data = form(e);
        int farmerId = Integer.parseInt(data.get("farmerId"));
        int landId = Integer.parseInt(data.get("landId"));
        String season = data.getOrDefault("season", "");

        String soil;
        String irrigation;
        String water;
        BigDecimal temperature;
        BigDecimal area;

        try (
            Connection c = DatabaseConnection.getConnection();
            PreparedStatement p = c.prepareStatement(
                "SELECT soil_type,irrigation,water_availability,temperature,area,farmer_id FROM lands WHERE land_id=?"
            )
        ) {
            p.setInt(1, landId);

            try (ResultSet r = p.executeQuery()) {
                if (!r.next()) {
                    send(e, 404, "Land not found.", "text/plain");
                    return;
                }
                if (r.getInt("farmer_id") != farmerId) {
                    send(e, 400, "This land does not belong to the selected farmer.", "text/plain");
                    return;
                }

                soil = Objects.toString(r.getString("soil_type"), "");
                irrigation = Objects.toString(r.getString("irrigation"), "");
                water = Objects.toString(r.getString("water_availability"), "");
                temperature = r.getBigDecimal("temperature");
                area = r.getBigDecimal("area");
            }
        }

        try (
            Connection c = DatabaseConnection.getConnection();
            PreparedStatement p = c.prepareStatement(
                "SELECT crop_id,crop_name,season,duration_days,expected_yield,min_temperature,max_temperature FROM crops"
            );
            ResultSet r = p.executeQuery()
        ) {
            while (r.next()) {
                int cropId = r.getInt("crop_id");
                String cropName = r.getString("crop_name");
                String cropSeason = r.getString("season");
                BigDecimal min = r.getBigDecimal("min_temperature");
                BigDecimal max = r.getBigDecimal("max_temperature");

                int score = 0;
                List<String> reasons = new ArrayList<>();

                if (season.equalsIgnoreCase(Objects.toString(cropSeason, ""))) {
                    score++;
                    reasons.add("season matches");
                }

                if (temperature != null &&
                    (min == null || temperature.compareTo(min) >= 0) &&
                    (max == null || temperature.compareTo(max) <= 0)) {
                    score++;
                    reasons.add("temperature is within the crop range");
                }

                String cn = cropName.toLowerCase(Locale.ROOT);
                String st = soil.toLowerCase(Locale.ROOT);

                if ((cn.contains("rice") && (st.contains("clay") || st.contains("alluvial") || st.contains("red"))) ||
                    (cn.contains("cotton") && st.contains("black")) ||
                    (cn.contains("maize") && (st.contains("loam") || st.contains("red"))) ||
                    (cn.contains("groundnut") && (st.contains("sandy") || st.contains("red"))) ||
                    st.contains("loam")) {
                    score++;
                    reasons.add("soil appears compatible");
                }

                if (!water.equalsIgnoreCase("poor") &&
                    !water.equalsIgnoreCase("low") &&
                    !water.isBlank()) {
                    score++;
                    reasons.add("water availability is recorded as " + water);
                }

                if (!irrigation.isBlank()) {
                    score++;
                    reasons.add("irrigation facility is recorded");
                }

                String suitability;
                if (score >= 4) suitability = "Highly Suitable";
                else if (score == 3) suitability = "Suitable";
                else if (score == 2) suitability = "Moderately Suitable";
                else suitability = "Not Recommended";

                String reason = reasons.isEmpty()
                    ? "Insufficient matching conditions; check crop and land details."
                    : String.join("; ", reasons) + ".";

                try (PreparedStatement insert = c.prepareStatement(
                    "INSERT INTO crop_recommendations(farmer_id,land_id,crop_id,suitability,reason,recommendation_date) VALUES(?,?,?,?,?,CURRENT_DATE)"
                )) {
                    insert.setInt(1, farmerId);
                    insert.setInt(2, landId);
                    insert.setInt(3, cropId);
                    insert.setString(4, suitability);
                    insert.setString(5, reason);
                    insert.executeUpdate();
                }
            }
        }

        send(e, 200, "Recommendations generated. View the results below.", "text/plain");
    }

    // ---------------- REPORTS ----------------

    private void reports(HttpExchange e) throws Exception {
        if (!e.getRequestMethod().equalsIgnoreCase("GET")) {
            send(e, 405, "GET required", "text/plain");
            return;
        }

        Map<String,Object> result = new LinkedHashMap<>();

        try (Connection c = DatabaseConnection.getConnection()) {
            result.put("totalFarmers", scalar(c, "SELECT COUNT(*) FROM farmers"));
            result.put("totalLands", scalar(c, "SELECT COUNT(*) FROM lands"));
            result.put("totalArea", scalar(c, "SELECT COALESCE(SUM(area),0) FROM lands"));
            result.put("totalCrops", scalar(c, "SELECT COUNT(*) FROM crops"));
            result.put("totalMarkets", scalar(c, "SELECT COUNT(*) FROM markets"));
            result.put("totalPriceRecords", scalar(c, "SELECT COUNT(*) FROM crop_prices"));
            result.put("averagePrice", scalar(c, "SELECT COALESCE(AVG(price_per_unit),0) FROM crop_prices"));
            result.put("totalSoilTests", scalar(c, "SELECT COUNT(*) FROM soil_tests"));
            result.put("totalRecommendations", scalar(c, "SELECT COUNT(*) FROM crop_recommendations"));
        }

        send(e, 200, jsonObject(result), "application/json");
    }

    private Object scalar(Connection c, String sql) throws SQLException {
        try (PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            r.next();
            return r.getObject(1);
        }
    }

    // ---------------- SIMPLE WEB PAGES ----------------

    private String layout(String title, String body, String script) {
        return """
            <!DOCTYPE html><html><head><meta charset="UTF-8">
            <meta name="viewport" content="width=device-width,initial-scale=1">
            <title>""" + title + """
            </title><style>
            body{font-family:Arial,sans-serif;margin:0;background:#f2f7f2;color:#222}
            header{background:#2e7d32;color:white;padding:22px;text-align:center}
            main{width:94%;max-width:1200px;margin:22px auto}
            section{background:white;padding:20px;margin-bottom:20px;border-radius:10px;box-shadow:0 2px 7px #ddd}
            h2{color:#2e7d32}label{display:block;font-weight:bold;margin-top:10px}
            input,select,textarea{box-sizing:border-box;width:100%;padding:10px;margin-top:5px;border:1px solid #bbb;border-radius:5px}
            button{background:#2e7d32;color:white;padding:9px 13px;border:0;border-radius:5px;margin:4px;cursor:pointer}
            button.delete{background:#c62828}button.edit{background:#ef6c00}
            table{border-collapse:collapse;width:100%;margin-top:15px;overflow:auto}
            th,td{border:1px solid #ccc;padding:8px;text-align:left}
            th{background:#2e7d32;color:white}
            .scroll{overflow-x:auto}.cards{display:grid;grid-template-columns:repeat(auto-fit,minmax(180px,1fr));gap:12px}
            .stat{background:#e8f5e9;padding:18px;border-radius:8px}
            .back{color:#2e7d32;font-weight:bold}
            </style></head><body><header><h1>🌱 """ + title + """
            </h1><p>Farmer Crop Advisory & Market Information System</p></header>
            <main>""" + body + """
            <p><a class="back" href="/">← Back to Main Menu</a></p>
            </main><script>""" + script + """
            </script></body></html>
            """;
    }

    private String cropPage() {
        String body = """
            <section><h2>Add / Update Crop</h2>
            <form id="cropForm"><input type="hidden" id="cropId">
            <label>Crop Name</label><input id="cropName" required>
            <label>Season</label><input id="season" placeholder="Kharif / Rabi" required>
            <label>Duration (days)</label><input id="durationDays" type="number" required>
            <label>Expected Yield</label><input id="expectedYield" type="number" step="0.01">
            <label>Minimum Temperature</label><input id="minTemperature" type="number" step="0.01">
            <label>Maximum Temperature</label><input id="maxTemperature" type="number" step="0.01">
            <button>Save Crop</button><button type="button" onclick="cancelCrop()">Cancel</button>
            </form></section>
            <section><h2>Crop Records</h2><input id="cropSearch" placeholder="Search crop..." oninput="loadCrops()">
            <div class="scroll"><table><thead><tr><th>ID</th><th>Name</th><th>Season</th><th>Days</th><th>Yield</th><th>Temp Range</th><th>Actions</th></tr></thead><tbody id="crops"></tbody></table></div></section>
            <section><h2>Generate Crop Advisory</h2>
            <form id="recommendForm">
            <label>Farmer ID</label><input id="recFarmer" type="number" required>
            <label>Land ID</label><input id="recLand" type="number" required>
            <label>Season</label><input id="recSeason" required placeholder="Kharif">
            <button>Generate Recommendations</button></form>
            <p id="recMessage"></p></section>
            <section><h2>Crop Recommendations</h2>
            <button onclick="loadRecommendations()">Refresh</button>
            <div class="scroll"><table><thead><tr><th>ID</th><th>Farmer</th><th>Land</th><th>Crop</th><th>Suitability</th><th>Reason</th><th>Date</th></tr></thead><tbody id="recommendations"></tbody></table></div></section>
            """;

        String script = """
            async function request(url,options){const r=await fetch(url,options);const t=await r.text();if(!r.ok)throw Error(t);return t}
            function formData(form){return new URLSearchParams(new FormData(form))}
            async function loadCrops(){try{const a=await (await fetch('/getCrops')).json();const s=document.getElementById('cropSearch').value.toLowerCase();document.getElementById('crops').innerHTML=a.filter(x=>String(x.cropName).toLowerCase().includes(s)).map(x=>`<tr><td>${x.cropId}</td><td>${x.cropName}</td><td>${x.season??''}</td><td>${x.durationDays??''}</td><td>${x.expectedYield??''}</td><td>${x.minTemperature??''} - ${x.maxTemperature??''}</td><td><button class="edit" onclick="editCrop(${x.cropId})">Update</button><button class="delete" onclick="delCrop(${x.cropId})">Delete</button></td></tr>`).join('');window.cropRows=a}catch(e){alert(e.message)}}
            function editCrop(id){const x=window.cropRows.find(x=>x.cropId===id);for(const k of ['cropId','cropName','season','durationDays','expectedYield','minTemperature','maxTemperature'])document.getElementById(k).value=x[k]??'';window.scrollTo(0,0)}
            function cancelCrop(){document.getElementById('cropForm').reset();document.getElementById('cropId').value=''}
            document.getElementById('cropForm').onsubmit=async e=>{e.preventDefault();try{const id=document.getElementById('cropId').value;const d=formData(e.target);await request(id?'/updateCrop':'/addCrop',{method:'POST',body:d});alert('Crop saved');cancelCrop();loadCrops()}catch(x){alert(x.message)}}
            async function delCrop(id){if(!confirm('Delete this crop?'))return;try{alert(await request('/deleteCrop?id='+id,{method:'POST'}));loadCrops();loadRecommendations()}catch(e){alert(e.message)}}
            document.getElementById('recommendForm').onsubmit=async e=>{e.preventDefault();try{document.getElementById('recMessage').innerText=await request('/recommendCrops',{method:'POST',body:formData(e.target)});loadRecommendations()}catch(x){alert(x.message)}}
            async function loadRecommendations(){try{const a=await (await fetch('/getRecommendations')).json();document.getElementById('recommendations').innerHTML=a.map(x=>`<tr><td>${x.recommendationId}</td><td>${x.farmerId}</td><td>${x.landId}</td><td>${x.cropName}</td><td>${x.suitability}</td><td>${x.reason??''}</td><td>${x.recommendationDate??''}</td></tr>`).join('')}catch(e){alert(e.message)}}
            loadCrops();loadRecommendations();
            """;

        return layout("Crop Advisory", body, script);
    }

    private String fertilizerPage() {
        String body = """
            <section><h2>Fertilizer Information</h2>
            <form id="fertForm"><input type="hidden" id="fertilizerId">
            <label>Fertilizer Name</label><input id="fertilizerName" required>
            <label>Quantity / Acre</label><input id="quantity" placeholder="50 kg per acre">
            <label>Application Stage</label><input id="applicationStage" placeholder="Basal application">
            <label>Crop ID</label><input id="cropId" type="number" required>
            <button>Save Fertilizer</button><button type="button" onclick="cancelF()">Cancel</button></form></section>
            <section><h2>Fertilizer Records</h2><div class="scroll"><table><thead><tr><th>ID</th><th>Name</th><th>Quantity</th><th>Stage</th><th>Crop</th><th>Actions</th></tr></thead><tbody id="fertRows"></tbody></table></div></section>
            <section><h2>Cultivation Guidance</h2>
            <form id="cultForm"><input type="hidden" id="cultivationId">
            <label>Crop ID</label><input id="cultCropId" type="number" required>
            <label>Cultivation Method</label><input id="cultivationMethod" required>
            <label>Pest Control Guidance</label><textarea id="pestControl" rows="3"></textarea>
            <label>Harvest Duration (days)</label><input id="harvestDuration" type="number">
            <button>Save Cultivation Guidance</button><button type="button" onclick="cancelC()">Cancel</button></form></section>
            <section><h2>Cultivation Records</h2><div class="scroll"><table><thead><tr><th>ID</th><th>Crop</th><th>Method</th><th>Pest Control</th><th>Harvest Days</th><th>Actions</th></tr></thead><tbody id="cultRows"></tbody></table></div></section>
            """;

        String script = """
            async function req(url,options){const r=await fetch(url,options);const t=await r.text();if(!r.ok)throw Error(t);return t}
            function fd(f){return new URLSearchParams(new FormData(f))}
            async function loadF(){const a=await (await fetch('/getFertilizers')).json();window.frows=a;document.getElementById('fertRows').innerHTML=a.map(x=>`<tr><td>${x.fertilizerId}</td><td>${x.fertilizerName}</td><td>${x.quantity??''}</td><td>${x.applicationStage??''}</td><td>${x.cropName??x.cropId??''}</td><td><button class="edit" onclick="editF(${x.fertilizerId})">Update</button><button class="delete" onclick="delF(${x.fertilizerId})">Delete</button></td></tr>`).join('')}
            function editF(id){const x=window.frows.find(x=>x.fertilizerId===id);for(const k of ['fertilizerId','fertilizerName','quantity','applicationStage','cropId'])document.getElementById(k).value=x[k]??''}
            function cancelF(){document.getElementById('fertForm').reset();document.getElementById('fertilizerId').value=''}
            document.getElementById('fertForm').onsubmit=async e=>{e.preventDefault();try{const id=document.getElementById('fertilizerId').value;alert(await req(id?'/updateFertilizer':'/addFertilizer',{method:'POST',body:fd(e.target)}));cancelF();loadF()}catch(x){alert(x.message)}}
            async function delF(id){if(!confirm('Delete fertilizer record?'))return;try{alert(await req('/deleteFertilizer?id='+id,{method:'POST'}));loadF()}catch(e){alert(e.message)}}
            async function loadC(){const a=await (await fetch('/getCultivation')).json();window.crows=a;document.getElementById('cultRows').innerHTML=a.map(x=>`<tr><td>${x.cultivationId}</td><td>${x.cropName??x.cropId}</td><td>${x.cultivationMethod??''}</td><td>${x.pestControl??''}</td><td>${x.harvestDuration??''}</td><td><button class="edit" onclick="editC(${x.cultivationId})">Update</button><button class="delete" onclick="delC(${x.cultivationId})">Delete</button></td></tr>`).join('')}
            function editC(id){const x=window.crows.find(x=>x.cultivationId===id);for(const [a,b] of [['cultivationId','cultivationId'],['cultCropId','cropId'],['cultivationMethod','cultivationMethod'],['pestControl','pestControl'],['harvestDuration','harvestDuration']])document.getElementById(a).value=x[b]??''}
            function cancelC(){document.getElementById('cultForm').reset();document.getElementById('cultivationId').value=''}
            document.getElementById('cultForm').onsubmit=async e=>{e.preventDefault();try{const id=document.getElementById('cultivationId').value;const d=fd(e.target);d.set('cropId',document.getElementById('cultCropId').value);alert(await req(id?'/updateCultivation':'/addCultivation',{method:'POST',body:d}));cancelC();loadC()}catch(x){alert(x.message)}}
            async function delC(id){if(!confirm('Delete cultivation record?'))return;try{alert(await req('/deleteCultivation?id='+id,{method:'POST'}));loadC()}catch(e){alert(e.message)}}
            loadF().catch(e=>alert(e.message));loadC().catch(e=>alert(e.message));
            """;

        return layout("Fertilizer & Cultivation", body, script);
    }

    private String marketPage() {
        String body = """
            <section><h2>Add / Update Market</h2>
            <form id="marketForm"><input type="hidden" id="marketId">
            <label>Market Name</label><input id="marketName" required>
            <label>Location</label><input id="location">
            <label>Contact</label><input id="contact">
            <button>Save Market</button><button type="button" onclick="cancelM()">Cancel</button></form></section>
            <section><h2>Markets</h2><div class="scroll"><table><thead><tr><th>ID</th><th>Name</th><th>Location</th><th>Contact</th><th>Actions</th></tr></thead><tbody id="markets"></tbody></table></div></section>
            <section><h2>Add / Update Crop Price</h2>
            <form id="priceForm"><input type="hidden" id="priceId">
            <label>Market ID</label><input id="marketIdPrice" type="number" required>
            <label>Crop ID</label><input id="cropId" type="number" required>
            <label>Price per Unit</label><input id="pricePerUnit" type="number" step="0.01" required>
            <label>Unit</label><input id="unit" placeholder="per quintal" required>
            <label>Date Updated</label><input id="dateUpdated" type="date" required>
            <button>Save Price</button><button type="button" onclick="cancelP()">Cancel</button></form></section>
            <section><h2>Market Prices</h2>
            <label>Search by Crop</label><input id="priceSearch" placeholder="Enter crop name" oninput="loadPrices()">
            <div class="scroll"><table><thead><tr><th>ID</th><th>Crop</th><th>Market</th><th>Location</th><th>Price</th><th>Unit</th><th>Date</th><th>Actions</th></tr></thead><tbody id="prices"></tbody></table></div>
            <h2>Price Comparison</h2><p>Search a crop name above to compare its recorded prices across markets.</p></section>
            """;

        String script = """
            async function req(url,options){const r=await fetch(url,options);const t=await r.text();if(!r.ok)throw Error(t);return t}
            function fd(f){return new URLSearchParams(new FormData(f))}
            async function loadM(){const a=await (await fetch('/getMarkets')).json();window.mrows=a;document.getElementById('markets').innerHTML=a.map(x=>`<tr><td>${x.marketId}</td><td>${x.marketName}</td><td>${x.location??''}</td><td>${x.contact??''}</td><td><button class="edit" onclick="editM(${x.marketId})">Update</button><button class="delete" onclick="delM(${x.marketId})">Delete</button></td></tr>`).join('')}
            function editM(id){const x=window.mrows.find(x=>x.marketId===id);for(const k of ['marketId','marketName','location','contact'])document.getElementById(k).value=x[k]??''}
            function cancelM(){document.getElementById('marketForm').reset();document.getElementById('marketId').value=''}
            document.getElementById('marketForm').onsubmit=async e=>{e.preventDefault();try{const id=document.getElementById('marketId').value;alert(await req(id?'/updateMarket':'/addMarket',{method:'POST',body:fd(e.target)}));cancelM();loadM()}catch(x){alert(x.message)}}
            async function delM(id){if(!confirm('Delete this market?'))return;try{alert(await req('/deleteMarket?id='+id,{method:'POST'}));loadM()}catch(e){alert(e.message)}}
            async function loadPrices(){const a=await (await fetch('/getPrices')).json();window.prows=a;const s=document.getElementById('priceSearch').value.toLowerCase();document.getElementById('prices').innerHTML=a.filter(x=>String(x.cropName).toLowerCase().includes(s)).map(x=>`<tr><td>${x.priceId}</td><td>${x.cropName}</td><td>${x.marketName}</td><td>${x.location??''}</td><td>${x.pricePerUnit}</td><td>${x.unit??''}</td><td>${x.dateUpdated??''}</td><td><button class="edit" onclick="editP(${x.priceId})">Update</button><button class="delete" onclick="delP(${x.priceId})">Delete</button></td></tr>`).join('')}
            function editP(id){const x=window.prows.find(x=>x.priceId===id);document.getElementById('priceId').value=x.priceId;document.getElementById('marketIdPrice').value=x.marketId;document.getElementById('cropId').value=x.cropId;document.getElementById('pricePerUnit').value=x.pricePerUnit;document.getElementById('unit').value=x.unit??'';document.getElementById('dateUpdated').value=String(x.dateUpdated??'').substring(0,10)}
            function cancelP(){document.getElementById('priceForm').reset();document.getElementById('priceId').value=''}
            document.getElementById('priceForm').onsubmit=async e=>{e.preventDefault();try{const id=document.getElementById('priceId').value;const d=fd(e.target);d.set('marketId',document.getElementById('marketIdPrice').value);alert(await req(id?'/updatePrice':'/addPrice',{method:'POST',body:d}));cancelP();loadPrices()}catch(x){alert(x.message)}}
            async function delP(id){if(!confirm('Delete this price record?'))return;try{alert(await req('/deletePrice?id='+id,{method:'POST'}));loadPrices()}catch(e){alert(e.message)}}
            loadM().catch(e=>alert(e.message));loadPrices().catch(e=>alert(e.message));
            """;

        return layout("Market Information & Prices", body, script);
    }

    private String reportsPage() {
        String body = """
            <section><h2>Project Summary</h2>
            <button onclick="loadReport()">Refresh Reports</button>
            <div class="cards" id="cards"></div></section>
            <section><h2>How to read the report</h2>
            <p>These values are calculated from the current records in your MySQL database. Add farmers, lands, crops, markets and prices in their respective modules, then refresh this page.</p>
            </section>
            """;

        String script = """
            async function loadReport(){try{const r=await fetch('/getReports');if(!r.ok)throw Error(await r.text());const x=await r.json();const items=[['Total Farmers',x.totalFarmers],['Registered Lands',x.totalLands],['Total Land Area (acres)',x.totalArea],['Available Crops',x.totalCrops],['Markets',x.totalMarkets],['Price Records',x.totalPriceRecords],['Average Price',x.averagePrice],['Soil Tests',x.totalSoilTests],['Recommendations',x.totalRecommendations]];document.getElementById('cards').innerHTML=items.map(a=>`<div class="stat"><strong>${a[0]}</strong><h2>${a[1]??0}</h2></div>`).join('')}catch(e){alert('Could not load reports: '+e.message)}}
            loadReport();
            """;

        return layout("Reports", body, script);
    }
}
