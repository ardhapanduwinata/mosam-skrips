const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Tukar = require("./m-tukarTiket");
const Tiket = require("../tiket/m-tiket");
const Tabungan = require("../tabungan/m-tabungan");

router.get("/", (req, res, next) => {
  Tukar.find()
    .exec()
    .then((docs) => {
      const response = {
        count: docs.length,
        TransaksiTukarTiket: docs.map((doc) => {
          return {
            _id: doc._id,
            user: doc.user,
            jmlTiket: doc.jmlTiket,
            date: doc.date,
            jmlbotolAtertukar: doc.jmlbotolA,
            jmlbotolBtertukar: doc.jmlbotolB,
            jmlgelastertukar: doc.jmlgelas,
          };
        }),
      };
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
  const tukar = new Tukar({
    _id: new mongooose.Types.ObjectId(),
    user: mongooose.Types.ObjectId(req.body.user),
    date: req.body.date,
    jmlTiket: req.body.jmlTiket,
    jmlbotolA: req.body.jmlbotolA,
    jmlbotolB: req.body.jmlbotolB,
    jmlgelas: req.body.jmlgelas,
  });

  tukar
    .save()
    .then((result) => {
      Tabungan.findOne({
          user: req.body.user,
        })
        .exec()
        .then((doc) => {
          const jmlbar = {
            jmlbotolA: doc.jmlbotolA - req.body.jmlbotolA,
            jmlbotolB: doc.jmlbotolB - req.body.jmlbotolB,
            jmlgelas: doc.jmlgelas - req.body.jmlgelas,
          };

          Tabungan.updateMany({
              user: req.body.user
            }, {
              $set: jmlbar
            })
            .then((hasil) => {
              console.log(hasil);
              res.status(201).json({
                message: "Data Transaksi tukar Tiket telah tersimpan!",
                jmlTiket: req.body.jmlTiket,
                jmlbotolA: req.body.jmlbotolA,
                jmlbotolB: req.body.jmlbotolB,
                jmlgelas: req.body.jmlgelas
              });
            })
            .catch((err) =>
              res.status(500).json({
                error: err,
                iniErrorKe: 1,
              })
            );
        });
    })
    .catch((err) => {
      res.status(500).json({
        error: err,
        iniErrorKe: 2,
      });
    });
});

router.get("/:user", (req, res, next) => {
  const user = mongooose.Types.ObjectId(req.params.user);
  Tukar.find({
      user: user,
    })
    .exec()
    .then((doc) => {
      console.log("from database ", doc);
      const response = {
        count: doc.length,
        TransaksiTukarTiket: doc.map((doc) => {
          return {
            _id: doc._id,
            user: doc.user,
            date: doc.date,
            jmlTiket: doc.jmlTiket,
            jmlbotolAtertukar: doc.jmlbotolA,
            jmlbotolBtertukar: doc.jmlbotolB,
            jmlgelastertukar: doc.jmlgelas,
          };
        }),
      };
      res.status(200).json(response);
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({
        error: err,
      });
    });
});

router.delete("/:user", (req, res, next) => {
  const user = req.params.user;
  Tukar.deleteMany({
      user: mongooose.Types.ObjectId(user),
    })
    .exec()
    .then((result) => {
      res.status(200).json({
        message: "Tansaksi Tukar Tiket user tersebut terhapus",
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