const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true,
    },
    date: {
        type: String,
        required: true
    },
    jmlVoucher: {
        type: Number,
        default: 0
    },
    jmlbotolA: {
        type: Number,
    },
    jmlbotolB: {
        type: Number,
    },
    jmlgelas: {
        type: Number,
    },
});

module.exports = mongoose.model('transaksiTukarTiket', userSchema);