const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    user: {
        type: String,
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
    terminal: {
        type: String
    }
});

module.exports = mongoose.model('transaksiNaikBis', userSchema);