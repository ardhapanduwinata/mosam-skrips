const express = require("express");
const app = express();
const morgan = require("morgan");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
// const multer = require("multer");
// const upload = multer();

const productRoutes = require("./api/products/products");
const orderRoutes = require("./api/orders/orders");
const loginRoutes = require("./api/users/login");
const registerRoutes = require("./api/users/register");
const updateProfile = require("./api/users/updateProfile");
const tabungan = require("./api/tabungan/tabungan");
const nabung = require("./api/transaksi_nabung/nabung");
const tiket = require("./api/tiket/tiket");
const tukartiket = require("./api/tukar_tiket/tukartiket");
const poin = require("./api/poin/poin");
const misi = require("./api/misi/misi");
const jalanimisi = require("./api/jalanimisi/jalanimisi");
const terminal = require("./api/terminal/terminal");
const histori = require("./api/history/history");
const naikbis = require("./api/naikbis/naikbis");
const lencana = require("./api/lencana/lencana");

mongoose.connect(
    "mongodb+srv://root:" +
    process.env.MONGO_ATLAS_PW +
    "@cluster0-lekam.mongodb.net/test?retryWrites=true&w=majority", {
        useNewUrlParser: true,
        useUnifiedTopology: true,
        useFindAndModify: true
    }
);
mongoose.Promise = global.Promise;

app.use(morgan("dev"));
app.use(express.urlencoded({
    extended: false
}));
app.use(express.json());
// app.use(upload.array());
// app.use(express.static("public"));

app.use((req, res, next) => {
    res.header("Access-Control-Allow-Origin", "*");
    res.header(
        "Access-Control-Allow-Headers",
        "Origin, X-Requested-With, Accept, Authorization"
    );
    if (req.method === "OPTIONS") {
        res.header("Access-Control-Allow-Methods", "PUT, POST, PATCH, DELETE, GET");
        return res.status(200).json({});
    }
    next();
});

app.use("/products", productRoutes);
app.use("/orders", orderRoutes);
app.use("/login", loginRoutes);
app.use("/register", registerRoutes);
app.use("/updateProfile", updateProfile);
app.use("/tabungan", tabungan);
app.use("/nabung", nabung);
app.use("/tiket", tiket);
app.use("/tukartiket", tukartiket);
app.use("/poin", poin);
app.use("/misi", misi);
app.use("/jalanimisi", jalanimisi);
app.use("/terminal", terminal);
app.use("/histori", histori);
app.use("/naikbis", naikbis);
app.use("/lencana", lencana);

app.use((req, res, next) => {
    const error = new Error("Not Found");
    error.status = 404;
    next(error);
});

app.use((error, req, res, next) => {
    res.status(error.status || 500);
    res.json({
        error: {
            message: error.message
        }
    });
});

module.exports = app;