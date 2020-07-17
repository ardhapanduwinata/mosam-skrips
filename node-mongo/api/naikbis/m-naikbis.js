const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    _id: mongoose.Schema.Types.ObjectId,
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Users",
        required: true
    },
    date: {
        type: String,
        required: true
    },
    terminal: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Terminal",
        required: true
    }
});

module.exports = mongoose.model('transaksiNaikBis', userSchema);