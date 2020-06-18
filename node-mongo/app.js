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

// app.use((req, res, next) => {
//    console.log('ini jalan gak?');
//     res.status(200).json({
//         message: 'It works!'
//     });
// });

module.exports = app;