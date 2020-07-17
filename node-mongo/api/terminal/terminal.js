const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Terminal = require("./m-terminal");

router.get("/", (req, res, next) => {
    Terminal.find()
        .exec()
        .then(docs => {
            const response = {
                count: docs.length,
                terminal: docs.map(doc => {
                    return {
                        id: doc._id,
                        namaTerminal: doc.namaTerminal,
                        alamatTerminal: doc.alamatTerminal
                    }
                })
            }
            res.status(200).json(response);
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                erros: err,
            });
        });
});

router.post("/", (req, res, next) => {

    const terminal = new Terminal({
        _id: new mongooose.Types.ObjectId(),
        namaTerminal: req.body.namaTerminal,
        alamatTerminal: req.body.alamatTerminal
    })

    terminal.save()
        .then(result => {
            console.log("from database: ", result)
            res.status(200).json({
                message: "Terminal baru telah ditambahkan!",
                id: result._id,
                namaTerminal: result.namaTerminal,
                alamatTerminal: result.alamatTerminal
            })
        })
        .catch(err => {
            res.status(500).json({
                error: err,
                iniErrorKe: 3
            })
        })
});

router.get("/:id", (req, res, next) => {
    const id = req.params.id;
    Terminal.findById(id)
        .then(doc => {
            console.log("from database ", doc);
            res.status(200).json({
                // id: doc._id,
                namaTerminal: doc.namaTerminal,
                alamatTerminal: doc.alamatTerminal
            });
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                message: "Data Terminal tidak ditemukan",
            });
        })
})

router.delete("/:id", (req, res, next) => {
    const id = req.params.id;
    Terminal.deleteOne({
            _id: id
        })
        .exec()
        .then(result => {
            res.status(200).json({
                message: "Terminal telah Terhapus"
            })
        })
        .catch((err) => {
            console.log(err);
            res.status(500).json({
                erros: err,
            });
        })
});

module.exports = router;