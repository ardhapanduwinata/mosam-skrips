const express = require("express");
const app = express();
const morgan = require("morgan");
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
// const multer = require("multer");
// const upload = multer();

const productRoutes = require("./api/routes/products");
const orderRoutes = require("./api/routes/orders");
const nyobaRoutes = require("./api/routes/nyoba")

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