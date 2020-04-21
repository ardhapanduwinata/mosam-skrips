const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Product = require("./m-product");

router.get("/", (req, res, next) => {
    Product.find()
        .select('name price _id')
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                products: docs.map(doc => {
                    return {
                        name: doc.name,
                        price: doc.price,
                        _id: doc._id,
                        request: {
                            type: 'GET',
                            url: 'http://localhost:3000/products/' + doc._id
                        }
                    }
                })
            }
            // if(docs.length >= 0) {
            res.status(200).json(response);
            // } else {
            //     res.status(404).json({
            //         message: 'No entries found'
            //     })
            // }
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                error: err,
            });
        });
});

router.post("/", (req, res, next) => {
    const product = new Product({
        _id: new mongooose.Types.ObjectId(),
        name: req.body.name,
        price: req.body.price,
    });
    product
        .save()
        .then((result) => {
            console.log(result);
            res.status(201).json({
                message: "Created product success",
                createdProduct: {
                    name: result.name,
                    price: result.price,
                    _id: result._id,
                    request1: {
                        title: "Show this single data",
                        type: "GET",
                        url: "http://localhost:3000/products/" + result._id,
                    },
                    request2: {
                        title: "Show all data",
                        type: "GET",
                        url: "http://localhost:3000/products/",
                    },
                },
            });
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                error: err,
            });
        });
});

router.get("/:productId", (req, res, next) => {
    const id = req.params.productId;
    Product.findById(id)
        .select("name price _id")
        .exec()
        .then((doc) => {
            console.log("From database", doc);
            if (doc) {
                res.status(200).json({
                    product: doc,
                    request1: {
                        title: "Get all data",
                        type: "GET",
                        url: "http://localhost:3000/products/",
                    },
                    request2: {
                        title: "Update this data",
                        type: "PATCH",
                        url: "http://localhost:3000/products/" + id,
                    },
                    request3: {
                        title: "Delete this data",
                        type: "DELETE",
                        url: "http://localhost:3000/products/" + id,
                    },
                });
            } else {
                res.status(404).json({
                    message: "No valid entry found provided ID",
                });
            }
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                error: err,
            });
        });
});

router.patch("/:productId", (req, res, next) => {
    const id = req.params.productId;
    Product.updateMany({
            _id: req.params.productId,
        }, {
            $set: req.body,
        })
        .exec()
        .then((result) => {
            res.status(200).json({
                message: "Product updated",
                request: "GET",
                url: "http://localhost:3000/products/" + id,
            });
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                // error: err,
            });
        });
});

router.delete("/:productId", (req, res, next) => {
    const id = req.params.productId;
    Product.deleteOne({
            _id: id,
        })
        .exec()
        .then((result) => {
            res.status(200).json({
                message: "Product deleted",
                request: {
                    type: "POST",
                    url: "http://localhost:3000/products/",
                    body: {
                        name: "String",
                        price: "Number",
                    },
                },
            });
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                erros: err,
            });
        });
});

module.exports = router;