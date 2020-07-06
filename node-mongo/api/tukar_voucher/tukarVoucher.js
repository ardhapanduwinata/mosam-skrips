const express = require("express");
const router = express.Router();
const mongooose = require("mongoose");

const Tukar = require("./m-tukarVoucher");
const Voucher = require("../voucher/m-voucher");
const Tabungan = require("../tabungan/m-tabungan");

router.get("/", (req, res, next) => {
  Tukar.find()
    .exec()
    .then((docs) => {
      const response = {
        count: docs.length,
        TransaksiTukarVoucher: docs.map((doc) => {
          return {
            _id: doc._id,
            user: doc.user,
            jmlVoucher: doc.jmlVoucher,
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
    user: req.body.user,
    date: req.body.date,
    jmlVoucher: req.body.jmlVoucher,
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
            jmlbotolA: doc.jmlbotolA - result.jmlbotolA,
            jmlbotolB: doc.jmlbotolB - result.jmlbotolB,
            jmlgelas: doc.jmlgelas - result.jmlgelas,
          };

          Tabungan.updateMany({
              user: req.body.user
            }, {
              $set: jmlbar
            })
            .then((hasil) => {
              console.log(hasil);
              res.status(201).json({
                message: "Data Transaksi tukar Voucher telah tersimpan!",
                jmlVoucher: req.body.jmlVoucher,
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
  const user = req.params.user;
  Tu.find({
      user: user,
    })
    .exec()
    .then((doc) => {
      console.log("from database ", doc);
      const response = {
        count: doc.length,
        TransaksiTukarVoucher: doc.map((doc) => {
          return {
            _id: doc._id,
            user: doc.user,
            date: doc.date,
            jmlVoucher: doc.jmlVoucher,
            jmlbotolAtertukar: doc.jmlbotolA,
            jmlbotolBtertukar: doc.jmlbotolB,
            jmlgelastertukar: doc.jmlgelas,
          };
        }),
      };
    })
    .catch((err) => {
      console.log(err);
      res.status(500).json({
        error: err,
      });
    });
});

router.delete("/:idTransaksi", (req, res, next) => {
  const id = req.params.idTransaksi;
  Tukar.deleteOne({
      _id: id,
    })
    .exec()
    .then((result) => {
      res.status(200).json({
        message: "Tansaksi Tukar Voucher terhapus",
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